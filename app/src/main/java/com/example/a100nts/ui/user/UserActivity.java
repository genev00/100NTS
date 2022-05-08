package com.example.a100nts.ui.user;

import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;
import static com.example.a100nts.common.Constants.MAX_DISTANCE;
import static com.example.a100nts.data.login.LoginRepository.getLoggedUser;
import static com.example.a100nts.data.login.LoginRepository.setLoggedUser;
import static com.example.a100nts.ui.sites.SitesActivity.setIsRankingEnabled;
import static com.example.a100nts.ui.sites.SitesActivity.setIsUserSitesView;
import static com.example.a100nts.utils.ActivityHolder.setActivity;
import static com.example.a100nts.utils.RestService.getSites;
import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.a100nts.R;
import com.example.a100nts.data.login.LoginRepository;
import com.example.a100nts.databinding.ActivityUserBinding;
import com.example.a100nts.entities.Site;
import com.example.a100nts.entities.UserUI;
import com.example.a100nts.ui.login.LoginActivity;
import com.example.a100nts.ui.sites.SitesActivity;
import com.example.a100nts.utils.RestService;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnTokenCanceledListener;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    static ActivityUserBinding bindingCache;
    private ActivityUserBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setActivity(this);

        binding.firstName.setText(getLoggedUser().getFirstName());

        setUpButtons();
        checkForLocation();
    }

    private void setUpButtons() {
        binding.firstName.setOnClickListener(l -> {
            Intent editUser = new Intent(this, UserEditActivity.class);
            startActivity(editUser);
            bindingCache = binding;
        });

        binding.buttonExit.setOnClickListener(l -> {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        });

        binding.buttonViewAllSites.setOnClickListener(l -> {
            setIsRankingEnabled(false);
            setIsUserSitesView(false);
            Intent viewAllSites = new Intent(this, SitesActivity.class);
            startActivity(viewAllSites);
        });

        binding.buttonViewTopSites.setOnClickListener(l -> {
            setIsRankingEnabled(true);
            setIsUserSitesView(false);
            Intent viewAllSites = new Intent(this, SitesActivity.class);
            startActivity(viewAllSites);
        });

        binding.buttonViewTopUsers.setOnClickListener(l -> {
            if (hasLocationAccess()) {
                Intent viewTopUsers = new Intent(this, UserRankingActivity.class);
                startActivity(viewTopUsers);
            } else {
                showLocationDeniedToast();
            }
        });

        binding.buttonViewMySites.setOnClickListener(l -> {
            if (hasLocationAccess()) {
                setIsRankingEnabled(false);
                setIsUserSitesView(true);
                Intent viewAllSites = new Intent(this, SitesActivity.class);
                startActivity(viewAllSites);
            } else {
                showLocationDeniedToast();
            }
        });
    }

    private void checkForLocation() {
        if (hasLocationAccess()) {
            getLocation();
        } else {
            showLocationDeniedToast();
        }
    }

    private boolean hasLocationAccess() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        return ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
                && locationManager.isProviderEnabled(GPS_PROVIDER)
                && locationManager.isProviderEnabled(NETWORK_PROVIDER);
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        LocationServices.getFusedLocationProviderClient(this).getCurrentLocation(PRIORITY_HIGH_ACCURACY, new CancellationToken() {
            @Override
            public boolean isCancellationRequested() {
                return false;
            }

            @NonNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                return null;
            }
        }).addOnSuccessListener(this::checkForVisitingSites);
    }

    private void checkForVisitingSites(Location location) {
        UserUI user = getLoggedUser();
        Site[] sitesArray = getSites();
        if (sitesArray == null) {
            finish();
            System.exit(1);
        }
        List<Long> restList = getVisitedSitesIds(location, user, sitesArray);
        if (!restList.isEmpty()) {
            user.getVisitedSites().addAll(restList);
            UserUI updated = RestService.visitSites(
                    user.getId(), restList.toArray(new Long[0])
            );
            if (updated == null) {
                finish();
                System.exit(1);
            }
            setLoggedUser(updated);
        }
    }

    private List<Long> getVisitedSitesIds(Location location, UserUI user, Site[] sitesArray) {
        return Arrays.stream(sitesArray)
                .filter(s -> !user.getVisitedSites().contains(s.getId()))
                .filter(s -> {
                    float[] result = new float[1];
                    Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                            s.getLatitude(), s.getLongitude(), result);
                    return result[0] < MAX_DISTANCE;
                })
                .map(Site::getId)
                .collect(Collectors.toList());
    }

    private void showLocationDeniedToast() {
        Toast.makeText(getApplicationContext(), R.string.location_not_granted, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void finish() {
        LoginRepository.getInstance().logout();
        super.finish();
    }

}
