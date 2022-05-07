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
import java.util.Comparator;
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
                new UserAdapter(this, getUsers())
        );
    }

    private List<UserUI> getUsers() {
        UserUI[] users = RestService.getUsers();
        if (users == null) {
            finish();
            System.exit(1);
        }

        final List<UserUI> sortedUsersList = Arrays.stream(users)
                .filter(UserUI::isRanking)
                .sorted(Comparator.comparingDouble(UserUI::getVisitedSites).reversed()
                    .thenComparing(UserUI::getFirstName))
                .collect(Collectors.toList());
        if (sortedUsersList.isEmpty()) {
            binding.textNotEnoughUserRankings.setVisibility(View.VISIBLE);
        } else {
            binding.textNotEnoughUserRankings.setVisibility(View.INVISIBLE);
        }
        return sortedUsersList;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
