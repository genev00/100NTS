package com.example.a100nts.ui.sites;

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
        binding.cutSiteDetailsTitle.setText(currentSite.getTitle());
        final SliderView cutSiteImgSlider = binding.cutSiteImgSlider;
        cutSiteImgSlider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        SiteImageSliderAdapter adapter = new SiteImageSliderAdapter(currentSite.getImageUrls());
        cutSiteImgSlider.setSliderAdapter(adapter);
        cutSiteImgSlider.setScrollTimeInSec(3);
        cutSiteImgSlider.setAutoCycle(true);
        cutSiteImgSlider.startAutoCycle();

        final String provinceAndTown = currentSite.getProvince() + ", " + currentSite.getTown();
        binding.cutSiteDetailsProvinceAndTown.setText(provinceAndTown);
        binding.cutSiteDetailsDescription.setText(currentSite.getDetails());
    }

    public static void setCurrentSite(Site currentSite) {
        CutSiteDetailsActivity.currentSite = currentSite;
    }
}