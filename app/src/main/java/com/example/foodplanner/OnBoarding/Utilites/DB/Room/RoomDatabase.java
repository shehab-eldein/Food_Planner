package com.example.foodplanner.OnBoarding.Utilites.DB.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

import com.example.foodplanner.OnBoarding.Models.MealListModel.MealList;
import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;

@Database(entities = {Meal.class, MealList.class}, version = 2)
public abstract class RoomDatabase extends androidx.room.RoomDatabase {
    private static RoomDatabase instance = null;

    public abstract DAO DAO();

    public static synchronized RoomDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), RoomDatabase.class, "FPRoom")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;

    }
}