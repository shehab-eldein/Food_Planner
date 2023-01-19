package com.example.foodplanner.OnBoarding.Models.Ingredient;

import java.util.ArrayList;

public class RootMealIngrad {

    private ArrayList<Ingredient> meals;
    public RootMealIngrad(ArrayList<Ingredient> meals) {
        this.meals = meals;
    }
    public ArrayList<Ingredient> getMeals() {
        return meals;
    }


}
