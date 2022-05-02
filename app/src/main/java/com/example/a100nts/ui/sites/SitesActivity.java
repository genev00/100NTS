package com.example.a100nts.ui.sites;

import static com.example.a100nts.utils.ActivityHolder.setActivity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a100nts.R;
import com.example.a100nts.utils.RestService;
import com.example.a100nts.databinding.ActivitySitesBinding;
import com.example.a100nts.entities.Site;
import com.example.a100nts.ui.adapters.SiteAdapter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SitesActivity extends AppCompatActivity {

    private static boolean isRankingEnabled;
    private ActivitySitesBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySitesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setActivity(this);

        binding.textNotEnoughSiteRankings.setVisibility(View.INVISIBLE);

        binding.cutSitesList.setAdapter(
                new SiteAdapter(this, getSites())
        );
    }

    private List<Site> getSites() {
        Site[] sites = RestService.getSites();
        if (sites == null) {
            finish();
            System.exit(1);
        }

        checkViewTitle();
        if (isRankingEnabled) {
            final List<Site> sortedSites = Arrays.stream(sites)
                    .filter(s -> s.getRating() > 0.0)
                    .sorted(Comparator.comparingDouble(Site::getRating).reversed())
                    .collect(Collectors.toList());
            if (sortedSites.isEmpty()) {
                binding.textNotEnoughSiteRankings.setVisibility(View.VISIBLE);
            } else {
                binding.textNotEnoughSiteRankings.setVisibility(View.INVISIBLE);
            }
            return sortedSites;
        }
        return Arrays.asList(sites);
    }

    private void checkViewTitle() {
        if (isRankingEnabled) {
            binding.textViewSitesTitle.setText(getString(R.string.rated_sites));
        } else {
            binding.textViewSitesTitle.setText(getString(R.string.sites));
        }
    }

    public static void setIsRankingEnabled(boolean isRankingEnabled) {
        SitesActivity.isRankingEnabled = isRankingEnabled;
    }

}
