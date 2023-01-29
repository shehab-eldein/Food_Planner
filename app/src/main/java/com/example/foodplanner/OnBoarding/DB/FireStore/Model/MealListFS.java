package com.example.foodplanner.OnBoarding.DB.FireStore.Model;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import com.example.foodplanner.OnBoarding.Models.MealListModel.MealList;

import java.util.ArrayList;

import javax.annotation.Nonnull;

public class MealListFS {
    public String day;

    public String idMeal;

    public String strMeal;
    public String strMealThumb;


    public MealListFS() {
    }

    public MealListFS(String day, String idMeal, String strMeal, String strMealThumb) {
        this.day = day;
        this.idMeal = idMeal;
        this.strMeal = strMeal;
        this.strMealThumb = strMealThumb;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }
}
