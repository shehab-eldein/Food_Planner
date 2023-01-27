package com.example.foodplanner.OnBoarding.DB.Room.Presenters;

import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;

import java.util.List;

public interface GetFavPresenter {
    void succsessFavList(List<Meal> meals);
    void failFavList(String err);
}
