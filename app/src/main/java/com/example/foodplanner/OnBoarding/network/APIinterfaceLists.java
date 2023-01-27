package com.example.foodplanner.OnBoarding.network;

import com.example.foodplanner.OnBoarding.Models.AreaModel.RootArea;
import com.example.foodplanner.OnBoarding.Models.CategorySearchModel.RootCategory;
import com.example.foodplanner.OnBoarding.Models.Ingredient.RootMealIngredient;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface APIinterfaceLists {

    @GET("list.php?i=list")
    Single<RootMealIngredient> getIngrdiants();

    @GET("list.php?a=list")
    Single<RootArea> getAreas();

    @GET("list.php?c=list")
    Single<RootCategory> getCategories();






}