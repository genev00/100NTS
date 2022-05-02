package com.example.a100nts.ui.error;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a100nts.R;
import com.example.a100nts.databinding.ActivityErrorBinding;

public class ErrorActivity extends AppCompatActivity {

    private static String errorMessage;
    private ActivityErrorBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityErrorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (errorMessage != null) {
            binding.errorMessage.setText(errorMessage);
        }
        binding.buttonExitOnFailure.setOnClickListener(o -> {
            finish();
            System.exit(1);
        });
    }

    public static void setErrorMessage(String errorMessage) {
        if (errorMessage != null && !errorMessage.isEmpty()) {
            ErrorActivity.errorMessage = errorMessage;
        }
    }

}
