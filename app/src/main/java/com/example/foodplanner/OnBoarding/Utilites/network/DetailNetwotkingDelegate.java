package com.example.foodplanner.OnBoarding.Utilites.network;

import com.example.foodplanner.OnBoarding.Models.CategoryModel.Category;
import com.example.foodplanner.OnBoarding.Models.detailsModel.Detail;

import java.util.ArrayList;

public interface DetailNetwotkingDelegate {
    public void succsessDetails(ArrayList<Detail> details);
    public void failDetails(String err);
}
