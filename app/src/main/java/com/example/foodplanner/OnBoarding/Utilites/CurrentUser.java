package com.example.foodplanner.OnBoarding.Utilites;

public class CurrentUser {
     static String email;
    static boolean isConnected;


    public static boolean getIsConnected() {
        return isConnected;
    }

    public static void setIsConnected(boolean isConnected) {
        CurrentUser.isConnected = isConnected;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        CurrentUser.email = email;
    }
}
