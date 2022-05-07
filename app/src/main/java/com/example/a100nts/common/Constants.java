package com.example.a100nts.common;

public final class Constants {

    public static final String PASSWORD_REGEX = "^(?=[^A-Z\\n]*[A-Z])(?=[^a-z\\n]*[a-z])(?=[^0-9\\n]*[0-9])(?=[^#?!@$%^&*\\n-]*[#?!@$%^&*-]).{8,}$";

    private Constants() {
    }

}
