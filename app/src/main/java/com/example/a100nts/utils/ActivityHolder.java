package com.example.a100nts.utils;

import androidx.appcompat.app.AppCompatActivity;

public final class ActivityHolder {

    private static AppCompatActivity activity;

    private ActivityHolder() {
    }

    public static AppCompatActivity getActivity() {
        return activity;
    }

    public static void setActivity(AppCompatActivity activity) {
        ActivityHolder.activity = activity;
    }

}
