package com.example.foodplanner.OnBoarding.network.Presenters;



import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;

import java.util.ArrayList;

public interface RandomPresenter {
     void succsessRandoms(ArrayList<Meal> meals);
     void failRandoms(String err);
}
