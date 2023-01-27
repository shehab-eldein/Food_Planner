package com.example.foodplanner.OnBoarding.Models.AreaModel;

import com.google.gson.annotations.SerializedName;

public class Area {


    @SerializedName("strArea")

    private String strArea;

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

}
