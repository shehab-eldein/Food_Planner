package com.example.foodplanner.OnBoarding.DB.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplanner.OnBoarding.Models.MealListModel.MealList;
import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface DAO {
    @Query("SELECT * FROM favourite")
    Single<List<Meal>> getFavMeals();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertFavMeal(Meal meal);

    @Delete
    Completable deleteFavMeal(Meal meal);

///////////////////////////////////////////////////

    @Query("SELECT * FROM mealList")
    Single<List<MealList>> getListMeals();

    @Query("SELECT * FROM mealList WHERE day LIKE :filterdDay  ")
    Single<List<MealList>> getListMeals(String filterdDay);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertListMeal(MealList meal);
    @Delete
    Completable deleteListMeal(MealList meal);


    /////////////////////////////////////////




    @Query("DELETE FROM favourite")
    Completable deleteAllFav();

    @Query("DELETE FROM mealList")
    Completable deleteAllMealList();






}
