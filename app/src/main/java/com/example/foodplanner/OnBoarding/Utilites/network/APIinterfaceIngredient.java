package com.example.foodplanner.OnBoarding.Utilites.network;



import com.example.foodplanner.OnBoarding.Models.Ingredient.RootMealIngrad;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIinterfaceIngredient {

    @GET("list.php?i=list")
    Call<RootMealIngrad> getingrdiants();

}
