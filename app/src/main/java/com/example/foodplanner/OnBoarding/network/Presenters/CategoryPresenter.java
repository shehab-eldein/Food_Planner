package com.example.foodplanner.OnBoarding.network.Presenters;

import com.example.foodplanner.OnBoarding.Models.CategoryModel.Category;

import java.util.ArrayList;

public interface CategoryPresenter {
     void succsessCategory(ArrayList<Category> categories);
     void failCategory(String err);
}
