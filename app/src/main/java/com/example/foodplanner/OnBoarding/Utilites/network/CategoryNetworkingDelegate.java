package com.example.foodplanner.OnBoarding.Utilites.network;

import com.example.foodplanner.OnBoarding.Models.CategoryModel.Category;
import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;

import java.util.ArrayList;

public interface CategoryNetworkingDelegate {
    public void succsessCategory(ArrayList<Category> categories);
    public void failCategory(String err);
}
