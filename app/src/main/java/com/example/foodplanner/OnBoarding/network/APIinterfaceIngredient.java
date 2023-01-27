package com.example.foodplanner.OnBoarding.network;



import com.example.foodplanner.OnBoarding.Models.Ingredient.RootMealIngredient;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface APIinterfaceIngredient {

    @GET("list.php?i=list")
    Single<RootMealIngredient> getingrdiants();

}