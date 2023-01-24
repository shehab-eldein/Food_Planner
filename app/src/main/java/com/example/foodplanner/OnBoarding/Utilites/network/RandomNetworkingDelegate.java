package com.example.foodplanner.OnBoarding.Utilites.network;



import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;

import java.util.ArrayList;

public interface RandomNetworkingDelegate {
     void succsessRandoms(ArrayList<Meal> meals);
     void failRandoms(String err);
}
