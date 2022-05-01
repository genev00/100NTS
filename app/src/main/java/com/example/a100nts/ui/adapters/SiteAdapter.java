package com.example.a100nts.ui.adapters;

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
    private final List<Site> sitesList;

    public SiteAdapter(@NonNull Context context, @NonNull List<Site> sites) {
        super(context, 0, sites);
        this.mContext = context;
        this.sitesList = sites;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.sites_list_item, parent, false);
        }

        Site currentSite = sitesList.get(position);

        TextView title = listItem.findViewById(R.id.siteTitle);
        title.setText(currentSite.getTitle());

        TextView province = listItem.findViewById(R.id.siteProvince);
        province.setText(currentSite.getProvince());

        TextView town = listItem.findViewById(R.id.siteTown);
        town.setText(currentSite.getTown());

        ImageView image = listItem.findViewById(R.id.siteImage);
        Picasso.get().load(currentSite.getImageUrls().get(0)).into(image);

        listItem.setOnClickListener(view -> {
            setCurrentSite(currentSite);
            Intent details = new Intent(mContext, CutSiteDetailsActivity.class);
            mContext.startActivity(details);
        });

        return listItem;
    }
}
