package com.example.foodplanner.OnBoarding;

public class CurrentUser {
     static String email;

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        CurrentUser.email = email;
    }
}
