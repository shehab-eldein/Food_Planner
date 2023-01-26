package com.example.foodplanner.OnBoarding.Utilites.DB.Room.Presenters;

import com.example.foodplanner.OnBoarding.Models.MealListModel.MealList;

import java.util.List;

public interface GetMealListPresenter {
    void succsessMealList(List<MealList> meals);
    void failMealList(String err);
}
