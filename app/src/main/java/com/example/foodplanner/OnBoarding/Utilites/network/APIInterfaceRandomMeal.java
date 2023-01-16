package com.example.foodplanner.OnBoarding.Utilites.network;


import com.example.foodplanner.OnBoarding.Models.mealModel.RootMeal;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterfaceRandomMeal {

    @GET("random.php")//random.php
    Call<RootMeal> getRandomMeals();
}
