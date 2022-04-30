package com.example.a100nts.ui.sites;

import static com.example.a100nts.common.RestUtil.REST_TEMPLATE;
import static com.example.a100nts.common.RestUtil.SERVER_URL;
import static com.example.a100nts.common.StringProvider.setContext;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a100nts.R;
import com.example.a100nts.databinding.ActivityCutSitesBinding;
import com.example.a100nts.entities.Site;
import com.example.a100nts.entities.SiteAdapter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CutSitesActivity extends AppCompatActivity {

    private ActivityCutSitesBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCutSitesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setContext(this);

        binding.cutSitesList.setAdapter(new SiteAdapter(this, getSites()));
    }

    private List<Site> getSites() {
        String restUrl = SERVER_URL + "/sites";
        if (Locale.getDefault().getLanguage().equals("bg")) {
            restUrl += "/bg";
        }
        ResponseEntity<? extends Site[]> responseEntity = REST_TEMPLATE.getForEntity(
                restUrl, Site[].class
        );
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return Arrays.asList(responseEntity.getBody());
        }
        return new ArrayList<>();
    }

}
