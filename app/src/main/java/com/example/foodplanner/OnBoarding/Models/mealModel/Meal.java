package com.example.foodplanner.OnBoarding.Models.mealModel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName ="favourite")
public class Meal {

    @ColumnInfo(name = "idMeal")
    @PrimaryKey
    private Long idMeal;

    @ColumnInfo(name = "strMeal")
    private String strMeal;

    @ColumnInfo(name = "strMealThumb")
    private String strMealThumb;


    @ColumnInfo(name="instraction")
    private String instraction;



    @ColumnInfo(name="area")
    private String area;



    public Meal(String strMeal,
                String strMealThumb,
                Long idMeal,String instraction,String area) {

        this.strMeal = strMeal;
        this.strMealThumb = strMealThumb;
        this.idMeal = idMeal;
        this.instraction=instraction;
        this.area=area;
    }

    public Meal() {
    }

   public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
    public String getInstraction() {
        return instraction;
    }

    public void setInstraction(String instraction) {
        this.instraction = instraction;
    }


    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    public Long getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(Long idMeal) {
        this.idMeal = idMeal;
    }

}