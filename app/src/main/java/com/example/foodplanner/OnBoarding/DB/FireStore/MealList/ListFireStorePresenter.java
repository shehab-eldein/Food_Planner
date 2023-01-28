package com.example.foodplanner.OnBoarding.DB.FireStore.MealList;

import com.example.foodplanner.OnBoarding.Models.MealListModel.MealList;
import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;

import java.util.ArrayList;
import java.util.List;

public interface ListFireStorePresenter {
    void succsessFireStoreList(ArrayList<MealList> meals);
    void failFireStoreList(String err);
}
