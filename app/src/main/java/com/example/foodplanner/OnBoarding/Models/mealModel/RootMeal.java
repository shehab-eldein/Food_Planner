package com.example.foodplanner.OnBoarding.Models.mealModel;
import java.util.ArrayList;

public class RootMeal {

       private ArrayList<Meal> meals;

        public RootMeal(ArrayList<Meal> meals) {
            this.meals = meals;
        }

        public ArrayList<Meal> getMeals() {
            return meals;
        }



        public void setMeals(ArrayList<Meal> meals) {
            this.meals = meals;
        }


}