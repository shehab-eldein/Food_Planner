package com.example.foodplanner.OnBoarding.Models.CategorySearchModel;

import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("strCategory")

    private String strCategory;


    public String getStrCategory() {
        return strCategory;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

}

