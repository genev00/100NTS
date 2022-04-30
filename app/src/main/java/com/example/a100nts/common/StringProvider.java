package com.example.a100nts.common;

import android.annotation.SuppressLint;
import android.content.Context;

public final class StringProvider {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private StringProvider() {
    }

    public static void setContext(Context context) {
        StringProvider.context = context;
    }

    public static String getTranslatedString(int id) {
        return context.getString(id);
    }

}
