package com.example.foodplanner.OnBoarding.Models.detailsModel;

public class Detail {
    public String idMeal;
    public String strMeal;
    public String strArea;
    public String strInstructions;
    public String strMealThumb;
    public String strYoutube;

    public Detail(String idMeal, String strMeal, String strArea, String strInstructions, String strMealThumb, String strYoutube) {
        this.idMeal = idMeal;
        this.strMeal = strMeal;
        this.strArea = strArea;
        this.strInstructions = strInstructions;
        this.strMealThumb = strMealThumb;
        this.strYoutube = strYoutube;
    }

    public String getIdMeal() {
        return idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public String getStrArea() {
        return strArea;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public String getStrYoutube() {
        return strYoutube;
    }
}
