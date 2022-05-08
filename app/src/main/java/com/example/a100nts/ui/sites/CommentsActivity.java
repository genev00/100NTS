package com.example.a100nts.ui.sites;

import static com.example.a100nts.data.login.LoginRepository.getLoggedUser;
import static com.example.a100nts.utils.ActivityHolder.setActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a100nts.R;
import com.example.a100nts.databinding.ActivityCommentsBinding;
import com.example.a100nts.entities.Comment;
import com.example.a100nts.ui.adapters.CommentAdapter;
import com.example.a100nts.utils.RestService;

import java.util.List;

public class CommentsActivity extends AppCompatActivity {

    private static Long siteId;
    private static List<Comment> comments;
    private ActivityCommentsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCommentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setActivity(this);

        if (!comments.isEmpty()) {
            binding.textNotComments.setVisibility(View.INVISIBLE);
            binding.commentsList.setAdapter(
                    new CommentAdapter(this, comments)
            );
        }

        setUpButtons();
    }

    private void setUpButtons() {
        binding.editTextComment.addTextChangedListener(getCommentTextChangedListener());

        binding.buttonPostComment.setOnClickListener(l -> {
            Comment newComment = RestService.postComment(new Comment(
                    String.valueOf(siteId),
                    binding.editTextComment.getText().toString().trim(),
                    String.valueOf(getLoggedUser().getId())
            ));
            if (newComment == null) {
                finish();
                System.exit(1);
            }
            comments.add(newComment);
            Toast.makeText(getApplicationContext(), R.string.saved_comment, Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    @NonNull
    private TextWatcher getCommentTextChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.buttonPostComment.setEnabled(false);
                if (binding.editTextComment.getText().toString().trim().length() < 5) {
                    binding.editTextComment.setError(getText(R.string.invalid_comment));
                    return;
                }
                binding.buttonPostComment.setEnabled(true);
            }
        };
    }

    public static void setSiteId(Long siteId) {
        CommentsActivity.siteId = siteId;
    }

    public static void setComments(List<Comment> comments) {
        CommentsActivity.comments = comments;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
