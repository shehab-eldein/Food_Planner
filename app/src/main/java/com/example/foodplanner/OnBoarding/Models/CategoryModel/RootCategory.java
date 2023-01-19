package com.example.foodplanner.OnBoarding.Models.CategoryModel;

import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;

import java.util.ArrayList;

public class RootCategory{

    public ArrayList<Category> categories;

    public ArrayList<Category> getCategories() {
        return categories;
    }
    public RootCategory(ArrayList<Category> categories) {
        this.categories = categories;
    }
}



