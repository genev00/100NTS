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
import com.example.a100nts.entities.Comment;

import java.util.List;

public class CommentAdapter extends ArrayAdapter<Comment> {

    private final Context mContext;
    private final List<Comment> commentList;

    public CommentAdapter(@NonNull Context context, @NonNull List<Comment> comments) {
        super(context, 0, comments);
        this.mContext = context;
        this.commentList = comments;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.comment_list_item, parent, false);
        }

        final Comment currentComment = commentList.get(position);

        final TextView commenter = listItem.findViewById(R.id.userNamesComment);
        commenter.setText(currentComment.getCommenter());

        final TextView commentTime = listItem.findViewById(R.id.commentDateTime);
        commentTime.setText(currentComment.getDateTime());

        final TextView commentBody = listItem.findViewById(R.id.commentBody);
        commentBody.setText(currentComment.getComment());

        return listItem;
    }

}
