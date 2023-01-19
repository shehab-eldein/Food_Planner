package com.example.foodplanner.OnBoarding.Utilites.network;



import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;

import java.util.ArrayList;

public interface RandomNetworkingDelegate {
    public void succsessRandoms(ArrayList<Meal> meals);
    public void failRandoms(String err);
}
