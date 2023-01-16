package com.example.foodplanner.OnBoarding.Utilites.network;
import com.example.foodplanner.OnBoarding.Models.mealModel.RootMeal;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIinterface {

    @GET("random.php")
    Call<RootMeal> getRandomMeals();


    @GET("filter.php")
    Call<RootMeal> getFilteredMeals(@Query("a") String country);


}
