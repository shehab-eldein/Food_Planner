package com.example.foodplanner.OnBoarding.network;
import com.example.foodplanner.OnBoarding.Models.mealModel.RootMeal;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIinterfaceMealFilter {

    @GET("filter.php")
    Single<RootMeal> getFilteredMainIngredientMeals(@Query("i") String  mainIngredient);


//////////////////////////////////////////////////


    @GET("filter.php")
    Single<RootMeal> getFilteredMainAreaMeals(@Query("a") String  area);


/////////////////////////////////////////////////////////////////////

    @GET("filter.php")
    Single<RootMeal> getFilteredMainCategoryMeals(@Query("c") String  category);




}
