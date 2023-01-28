package com.example.foodplanner.OnBoarding.DB.FireStore;

import androidx.annotation.Nullable;

import com.example.foodplanner.OnBoarding.Models.MealListModel.MealList;

import java.util.ArrayList;

public class MealListFS {
    public ArrayList<MealList> meals;

    public MealListFS() {
    }


    public MealListFS(ArrayList<MealList> meals) {
        this.meals = meals;
    }

    public ArrayList<MealList> getMeals() {
        return meals;
    }
    public void setMeals(ArrayList<MealList> meals) {
        this.meals = meals;
    }

}
