package com.example.foodplanner.OnBoarding.Utilites.network;



import com.example.foodplanner.OnBoarding.Models.CategoryModel.RootCategory;
import com.example.foodplanner.OnBoarding.Models.Ingredient.RootMealIngredient;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIinterfaceIngredient {

//    @GET("list.php?i=list")
//    Call<RootMealIngredient> getingrdiants();
//


    @GET("list.php?i=list")
        //Observable<RootMealIngredient> getingrdiants();
    Single<RootMealIngredient> getingrdiants();

}