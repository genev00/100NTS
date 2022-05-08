package com.example.a100nts.ui.user;

import static com.example.a100nts.utils.ActivityHolder.setActivity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a100nts.databinding.ActivityUserRankingBinding;
import com.example.a100nts.entities.UserUI;
import com.example.a100nts.ui.adapters.UserAdapter;
import com.example.a100nts.utils.RestService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserRankingActivity extends AppCompatActivity {

    private ActivityUserRankingBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserRankingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setActivity(this);

        binding.userRankingList.setAdapter(
                new UserAdapter(this, getUsersAndCheckTexts())
        );
    }

    private List<UserUI> getUsersAndCheckTexts() {
        UserUI[] users = RestService.getUsers();
        if (users == null) {
            finish();
            System.exit(1);
        }

        final List<UserUI> sortedUsersList = getSortedUsers(users);
        if (sortedUsersList.isEmpty()) {
            binding.textNotEnoughUserRankings.setVisibility(View.VISIBLE);
        } else {
            binding.textNotEnoughUserRankings.setVisibility(View.INVISIBLE);
        }
        return sortedUsersList;
    }

    private List<UserUI> getSortedUsers(UserUI[] users) {
        return Arrays.stream(users)
                .filter(UserUI::isRanking)
                .sorted((st, nd) -> {
                    int result = Integer.compare(nd.getVisitedSites().size(), st.getVisitedSites().size());
                    if (result == 0) {
                        result = st.getFirstName().compareToIgnoreCase(nd.getFirstName());
                    }
                    return result;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
