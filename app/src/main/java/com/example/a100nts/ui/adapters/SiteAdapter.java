package com.example.a100nts.ui.adapters;

import static com.example.a100nts.data.login.LoginRepository.getLoggedUser;
import static com.example.a100nts.data.login.LoginRepository.isUserLogged;
import static com.example.a100nts.ui.sites.SitesActivity.isRankingEnabled;
import static com.example.a100nts.ui.sites.SitesActivity.isUserSitesView;
import static com.example.a100nts.utils.ActivityHolder.getActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.a100nts.R;
import com.example.a100nts.entities.Site;
import com.example.a100nts.ui.sites.CutSiteDetailsActivity;
import com.example.a100nts.ui.sites.SiteDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.stream.Collectors;

public class SiteAdapter extends ArrayAdapter<Site> {

    private final Context mContext;
    private List<Site> initialSiteList;
    private List<Site> siteList;

    public SiteAdapter(@NonNull Context context, @NonNull List<Site> sites) {
        super(context, 0, sites);
        this.mContext = context;
        this.initialSiteList = sites;
        this.siteList = sites;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.site_list_item, parent, false);
        }

        final Site currentSite = siteList.get(position);

        final TextView title = listItem.findViewById(R.id.userNamesRank);
        title.setText(currentSite.getTitle());

        final TextView province = listItem.findViewById(R.id.siteProvince);
        if (isUserSitesView()) {
            province.setText(mContext.getText(R.string.visited_on));
        } else {
            province.setText(currentSite.getProvince());
        }

        final TextView town = listItem.findViewById(R.id.siteTown);
        if (isUserSitesView()) {
            final String siteVisitTime = getLoggedUser().getSitesTime().stream()
                    .map(t -> t.split("\\+"))
                    .filter(t -> Long.parseLong(t[0]) == currentSite.getId())
                    .map(t -> t[1])
                    .findFirst()
                    .orElse(null);
            town.setText(siteVisitTime);
        } else {
            town.setText(currentSite.getTown());
        }

        final TextView rating = listItem.findViewById(R.id.siteRating);
        final ImageView star = listItem.findViewById(R.id.starRating);
        if (isRankingEnabled()) {
            star.setVisibility(View.VISIBLE);
            rating.setVisibility(View.VISIBLE);
            rating.setText(String.valueOf(currentSite.getRating()));
        } else {
            star.setVisibility(View.INVISIBLE);
            rating.setVisibility(View.INVISIBLE);
        }

        final ImageView image = listItem.findViewById(R.id.siteImage);
        Picasso.get().load(currentSite.getImageUrls().get(0)).into(image);

        listItem.setOnClickListener(view -> {
            Intent details;
            if (isUserLogged()) {
                SiteDetailsActivity.setCurrentSite(currentSite);
                details = new Intent(mContext, SiteDetailsActivity.class);
            } else {
                CutSiteDetailsActivity.setCurrentSite(currentSite);
                details = new Intent(mContext, CutSiteDetailsActivity.class);
            }
            mContext.startActivity(details);
            getActivity().finish();
        });

        return listItem;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence searchValue) {
                String searchKey = searchValue.toString().toLowerCase();
                FilterResults filteredSites = new FilterResults();
                if (!searchKey.isEmpty()) {
                    List<Site> filteredItems = initialSiteList.stream()
                            .filter(s -> s.getTitle().toLowerCase().contains(searchKey)
                                    || s.getProvince().toLowerCase().contains(searchKey)
                                    || s.getTown().toLowerCase().contains(searchKey))
                            .collect(Collectors.toList());
                    filteredSites.values = filteredItems;
                    filteredSites.count = filteredItems.size();
                    if (filteredItems.isEmpty()) {
                        Toast.makeText(mContext.getApplicationContext(), R.string.no_search_results,
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    filteredSites.values = initialSiteList;
                    filteredSites.count = initialSiteList.size();
                }
                return filteredSites;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                if (results.count > 0) {
                    siteList = (List<Site>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }

    @Override
    public int getCount() {
        return siteList.size();
    }

}
