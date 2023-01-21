package com.example.foodplanner.OnBoarding;

import java.util.ArrayList;

public class UserFav {

    private String email;
    private ArrayList<Long> favMeals ;

    public UserFav() {
    }

    public UserFav(String email, ArrayList<Long> favMeals) {
            this.email = email;
            this.favMeals = favMeals;
        }

    public String getEmail() {
        return email;
    }

    public ArrayList<Long> getFavMeals() {
        return favMeals;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFavMeals(ArrayList<Long> favMeals) {
        this.favMeals = favMeals;
    }
}



