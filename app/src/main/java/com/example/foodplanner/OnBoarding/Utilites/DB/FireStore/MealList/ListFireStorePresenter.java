package com.example.foodplanner.OnBoarding.Utilites.DB.FireStore.MealList;

import com.example.foodplanner.OnBoarding.Models.MealListModel.MealList;
import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;

import java.util.List;

public interface ListFireStorePresenter {
    void succsessFireStoreList(List<MealList> meals);
    void failFireStoreList(String err);
}
