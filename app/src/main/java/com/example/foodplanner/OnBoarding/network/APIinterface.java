package com.example.foodplanner.OnBoarding.network;
import com.example.foodplanner.OnBoarding.Models.CategoryModel.RootCategory;
import com.example.foodplanner.OnBoarding.Models.Ingredient.RootMealIngredient;
import com.example.foodplanner.OnBoarding.Models.detailsModel.DetailRoot;
import com.example.foodplanner.OnBoarding.Models.mealModel.RootMeal;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIinterface {


    @GET("categories.php")
    Observable<RootCategory> getAllCategories();

    @GET("filter.php")
    Observable<RootMeal> getFilteredMeals(@Query("a") String country);

    @GET("lookup.php")
    Observable<DetailRoot> getByID(@Query("i") Long id);

    @GET("list.php?i=list")
    Single<RootMealIngredient> getingrdiants();
/////////////////////////////////////////////////

    @GET("random.php")
    Observable<RootMeal> getRandomMeal();

}
