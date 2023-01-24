package com.example.foodplanner.OnBoarding;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.ArrayList;

public class UserFav implements Serializable {


    private ArrayList<String> favMeals ;




    public UserFav() {
    }

    public UserFav( ArrayList<String> favMeals) {

            this.favMeals = favMeals;
        }
        public ArrayList<String> getFavMeals() {
        return favMeals;
    }
    public void setFavMeals(ArrayList<String> favMeals) {
        this.favMeals = favMeals;
    }
}



