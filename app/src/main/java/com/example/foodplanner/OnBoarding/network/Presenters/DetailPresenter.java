package com.example.foodplanner.OnBoarding.network.Presenters;

import com.example.foodplanner.OnBoarding.Models.detailsModel.Detail;

import java.util.ArrayList;

public interface DetailPresenter {
     void succsessDetails(ArrayList<Detail> details);
     void failDetails(String err);
}
