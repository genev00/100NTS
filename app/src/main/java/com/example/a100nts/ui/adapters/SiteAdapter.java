package com.example.a100nts.ui.adapters;

import static com.example.a100nts.data.login.LoginRepository.isUserLogged;
import static com.example.a100nts.ui.sites.CutSiteDetailsActivity.setCurrentSite;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.a100nts.R;
import com.example.a100nts.entities.Site;
import com.example.a100nts.ui.sites.CutSiteDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SiteAdapter extends ArrayAdapter<Site> {

    private final Context mContext;
    private final List<Site> siteList;

    public SiteAdapter(@NonNull Context context, @NonNull List<Site> sites) {
        super(context, 0, sites);
        this.mContext = context;
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
        province.setText(currentSite.getProvince());

        final TextView town = listItem.findViewById(R.id.siteTown);
        town.setText(currentSite.getTown());

        final ImageView image = listItem.findViewById(R.id.siteImage);
        Picasso.get().load(currentSite.getImageUrls().get(0)).into(image);

        listItem.setOnClickListener(view -> {
            setCurrentSite(currentSite);
            if (isUserLogged()) {

            } else {
                Intent details = new Intent(mContext, CutSiteDetailsActivity.class);
                mContext.startActivity(details);
            }
        });

        return listItem;
    }
}
