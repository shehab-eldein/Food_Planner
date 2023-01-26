package com.example.foodplanner.OnBoarding.Utilites.network;
import com.example.foodplanner.OnBoarding.Models.mealModel.RootMeal;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIinterfaceMealIngredient {

    @GET("filter.php")
    Single<RootMeal> getFilteredMainIngredientMeals(@Query("i") String  mainIngredient);

}
