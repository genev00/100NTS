package com.example.a100nts.ui.sites;

import static com.example.a100nts.utils.ActivityHolder.setActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a100nts.databinding.ActivityCutSiteDetailsBinding;
import com.example.a100nts.entities.Site;
import com.example.a100nts.ui.adapters.SiteImageSliderAdapter;
import com.smarteist.autoimageslider.SliderView;

public class CutSiteDetailsActivity extends AppCompatActivity {

    private static Site currentSite;
    private ActivityCutSiteDetailsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCutSiteDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setActivity(this);

        binding.cutSiteDetailsTitle.setText(currentSite.getTitle());

        setUpButtons();
        setUpSliderView();
    }

    private void setUpButtons() {
        binding.googleMapsCutSite.setOnClickListener(l -> {
            final Uri googleMapsUri = Uri.parse("geo:" + currentSite.getLatitude() + ',' + currentSite.getLongitude());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, googleMapsUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            }
        });

        final String provinceAndTown = currentSite.getProvince() + ", " + currentSite.getTown();
        binding.cutSiteDetailsProvinceAndTown.setText(provinceAndTown);
        binding.cutSiteDetailsDescription.setText(currentSite.getDetails());
    }

    private void setUpSliderView() {
        final SliderView cutSiteImgSlider = binding.cutSiteImgSlider;
        cutSiteImgSlider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        SiteImageSliderAdapter adapter = new SiteImageSliderAdapter(currentSite.getImageUrls());
        cutSiteImgSlider.setSliderAdapter(adapter);
        cutSiteImgSlider.setScrollTimeInSec(5);
        cutSiteImgSlider.setAutoCycle(true);
        cutSiteImgSlider.startAutoCycle();
    }

    public static void setCurrentSite(Site currentSite) {
        CutSiteDetailsActivity.currentSite = currentSite;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
