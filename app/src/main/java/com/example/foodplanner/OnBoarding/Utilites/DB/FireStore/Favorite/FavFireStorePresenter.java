package com.example.foodplanner.OnBoarding.Utilites.DB.FireStore.Favorite;

import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;

import java.util.List;

public interface FavFireStorePresenter {
    void succsessFireStoreFav(List<Meal> meals);
    void failFireStoreFav(String err);

}
