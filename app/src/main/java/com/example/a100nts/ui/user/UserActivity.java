package com.example.a100nts.ui.user;

import static com.example.a100nts.data.login.LoginRepository.getLoggedUser;
import static com.example.a100nts.ui.sites.SitesActivity.setIsRankingEnabled;
import static com.example.a100nts.utils.ActivityHolder.setActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a100nts.data.login.LoginRepository;
import com.example.a100nts.databinding.ActivityUserBinding;
import com.example.a100nts.ui.login.LoginActivity;
import com.example.a100nts.ui.sites.SitesActivity;

public class UserActivity extends AppCompatActivity {

    private ActivityUserBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setActivity(this);

        binding.firstName.setText(getLoggedUser().getFirstName());

        setUpButtons();
    }

    private void setUpButtons() {
        binding.firstName.setOnClickListener(l -> {
            Intent editUser = new Intent(this, UserEditActivity.class);
            startActivity(editUser);
        });

        binding.buttonExit.setOnClickListener(l -> {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        });

        binding.buttonViewAllSites.setOnClickListener(l -> {
            setIsRankingEnabled(false);
            Intent viewAllSites = new Intent(this, SitesActivity.class);
            startActivity(viewAllSites);
        });

        binding.buttonViewTopSites.setOnClickListener(l -> {
            setIsRankingEnabled(true);
            Intent viewAllSites = new Intent(this, SitesActivity.class);
            startActivity(viewAllSites);
        });

        binding.buttonViewTopUsers.setOnClickListener(l -> {
            Intent viewTopUsers = new Intent(this, UserRankingActivity.class);
            startActivity(viewTopUsers);
        });
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
