package com.example.foodplanner.OnBoarding.Utilites.network;

import com.example.foodplanner.OnBoarding.Models.CategoryModel.Category;
import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;

import java.util.ArrayList;

public interface CategoryNetworkingDelegate {
     void succsessCategory(ArrayList<Category> categories);
     void failCategory(String err);
}
