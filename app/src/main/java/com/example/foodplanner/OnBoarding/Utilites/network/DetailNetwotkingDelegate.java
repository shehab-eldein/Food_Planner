package com.example.foodplanner.OnBoarding.Utilites.network;

import com.example.foodplanner.OnBoarding.Models.CategoryModel.Category;
import com.example.foodplanner.OnBoarding.Models.detailsModel.Detail;

import java.util.ArrayList;

public interface DetailNetwotkingDelegate {
     void succsessDetails(ArrayList<Detail> details);
     void failDetails(String err);
}
