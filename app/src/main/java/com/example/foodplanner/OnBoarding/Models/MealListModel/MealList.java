package com.example.foodplanner.OnBoarding.Models.MealListModel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import javax.annotation.Nonnull;


@Entity(tableName ="mealList")
public class MealList  {

    @ColumnInfo(name = "id")
    @PrimaryKey
    public Integer id;

    @ColumnInfo(name = "day")
    @Nonnull
    public String day;

    @ColumnInfo(name = "idMeal")
   public Long idMeal;

    @ColumnInfo(name = "strMeal")
    public String strMeal;

    @ColumnInfo(name = "strMealThumb")
    public String strMealThumb;



    public MealList(String strMeal,
                    String strMealThumb,
                    Long idMeal,
                    String day) {

        this.strMeal = strMeal;
        this.strMealThumb = strMealThumb;
        this.idMeal = idMeal;
        this.day=day;
    }
    public MealList(String strMeal,
                String strMealThumb,
                Long idMeal) {

        this.strMeal = strMeal;
        this.strMealThumb = strMealThumb;
        this.idMeal = idMeal;
    }

    public MealList() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
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