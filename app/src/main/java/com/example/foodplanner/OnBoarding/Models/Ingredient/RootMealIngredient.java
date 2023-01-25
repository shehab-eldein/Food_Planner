package com.example.foodplanner.OnBoarding.Models.Ingredient;

import java.util.ArrayList;

public class RootMealIngredient {

    private ArrayList<Ingredient> meals;
    public RootMealIngredient(ArrayList<Ingredient> meals) {
        this.meals = meals;
    }
    public ArrayList<Ingredient> getMeals() {
        return meals;
    }


}
