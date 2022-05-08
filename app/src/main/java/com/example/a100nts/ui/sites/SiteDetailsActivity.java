package com.example.a100nts.ui.sites;

import static com.example.a100nts.data.login.LoginRepository.getLoggedUser;
import static com.example.a100nts.ui.sites.CommentsActivity.setComments;
import static com.example.a100nts.ui.sites.CommentsActivity.setSiteId;
import static com.example.a100nts.utils.ActivityHolder.setActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a100nts.R;
import com.example.a100nts.databinding.ActivitySiteDetailsBinding;
import com.example.a100nts.entities.Site;
import com.example.a100nts.ui.adapters.SiteImageSliderAdapter;
import com.example.a100nts.utils.RestService;
import com.smarteist.autoimageslider.SliderView;

public class SiteDetailsActivity extends AppCompatActivity {

    private static Site currentSite;
    private ActivitySiteDetailsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySiteDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setActivity(this);

        setUpButtons();
        setUpSliderView();
    }

    private void setUpButtons() {
        binding.googleMapsSite.setOnClickListener(l -> {
            final Uri googleMapsUri = Uri.parse("geo:" + currentSite.getLatitude() + ',' + currentSite.getLongitude());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, googleMapsUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            }
        });

        binding.siteDetailsTitle.setText(currentSite.getTitle());

        final String provinceAndTown = currentSite.getProvince() + ", " + currentSite.getTown();
        binding.siteDetailsProvinceAndTown.setText(provinceAndTown);
        binding.siteDetailsDescription.setText(currentSite.getDetails());

        binding.buttonViewAllComments.setOnClickListener(l -> {
            setSiteId(currentSite.getId());
            setComments(currentSite.getComments());
            Intent comments = new Intent(this, CommentsActivity.class);
            startActivity(comments);
        });

        setUpStars();
    }

    private void setUpSliderView() {
        final SliderView cutSiteImgSlider = binding.siteImgSlider;
        cutSiteImgSlider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        SiteImageSliderAdapter adapter = new SiteImageSliderAdapter(currentSite.getImageUrls());
        cutSiteImgSlider.setSliderAdapter(adapter);
        cutSiteImgSlider.setScrollTimeInSec(3);
        cutSiteImgSlider.setAutoCycle(true);
        cutSiteImgSlider.startAutoCycle();
    }

    private void setUpStars() {
        loadRating();
        binding.oneStar.setOnClickListener(getStartsOnClickListener(1));
        binding.twoStar.setOnClickListener(getStartsOnClickListener(2));
        binding.threeStar.setOnClickListener(getStartsOnClickListener(3));
        binding.fourStar.setOnClickListener(getStartsOnClickListener(4));
        binding.fiveStar.setOnClickListener(getStartsOnClickListener(5));
    }

    private void loadRating() {
        binding.oneStar.setImageResource(R.drawable.star_inactive);
        binding.twoStar.setImageResource(R.drawable.star_inactive);
        binding.threeStar.setImageResource(R.drawable.star_inactive);
        binding.fourStar.setImageResource(R.drawable.star_inactive);
        binding.fiveStar.setImageResource(R.drawable.star_inactive);
        Integer siteRating = RestService.getVote(getLoggedUser().getId(), currentSite.getId());
        if (siteRating == null) {
            finish();
            System.exit(1);
        }
        siteRating = siteRating == 0 ? currentSite.getRating() : siteRating;
        switch (siteRating) {
            case 1:
                binding.oneStar.setImageResource(R.drawable.star_active);
                break;
            case 2:
                binding.oneStar.setImageResource(R.drawable.star_active);
                binding.twoStar.setImageResource(R.drawable.star_active);
                break;
            case 3:
                binding.oneStar.setImageResource(R.drawable.star_active);
                binding.twoStar.setImageResource(R.drawable.star_active);
                binding.threeStar.setImageResource(R.drawable.star_active);
                break;
            case 4:
                binding.oneStar.setImageResource(R.drawable.star_active);
                binding.twoStar.setImageResource(R.drawable.star_active);
                binding.threeStar.setImageResource(R.drawable.star_active);
                binding.fourStar.setImageResource(R.drawable.star_active);
                break;
            case 5:
                binding.oneStar.setImageResource(R.drawable.star_active);
                binding.twoStar.setImageResource(R.drawable.star_active);
                binding.threeStar.setImageResource(R.drawable.star_active);
                binding.fourStar.setImageResource(R.drawable.star_active);
                binding.fiveStar.setImageResource(R.drawable.star_active);
                break;
        }
    }

    private View.OnClickListener getStartsOnClickListener(int vote) {
        return l -> {
            if (!getLoggedUser().getVisitedSites().contains(currentSite.getId())) {
                Toast.makeText(getApplicationContext(), R.string.voting_not_enabled, Toast.LENGTH_SHORT).show();
                return;
            }
            Site updated = RestService.voteSite(getLoggedUser().getId(), currentSite.getId(), vote);
            if (updated == null) {
                finish();
                System.exit(1);
            }
            currentSite = updated;
            loadRating();
            Toast.makeText(getApplicationContext(), R.string.saved_vote, Toast.LENGTH_SHORT).show();
        };
    }

    public static void setCurrentSite(Site currentSite) {
        SiteDetailsActivity.currentSite = currentSite;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
