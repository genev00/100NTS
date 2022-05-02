package com.example.a100nts.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.a100nts.R;
import com.example.a100nts.entities.UserUI;

import java.util.List;

public class UserAdapter extends ArrayAdapter<UserUI> {

    private final Context mContext;
    private final List<UserUI> userList;

    public UserAdapter(@NonNull Context context, @NonNull List<UserUI> users) {
        super(context, 0, users);
        this.mContext = context;
        this.userList = users;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.user_list_item, parent, false);
        }

        final UserUI currentUser = userList.get(position);

        final TextView userPosition = listItem.findViewById(R.id.userRankPos);
        userPosition.setText(position);

        final TextView names = listItem.findViewById(R.id.userNamesRank);
        final String currentName = currentUser.getFirstName() + " " + currentUser.getLastName();
        names.setText(currentName);

        return listItem;
    }
}
