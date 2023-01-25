package com.example.foodplanner.OnBoarding.Models.MealListModel;

import androidx.room.ColumnInfo;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.foodplanner.OnBoarding.Utilites.DB.Room.RoomDatabase;

import javax.annotation.Nonnull;


@Entity(tableName ="mealList")
public class MealList  {

    private String day;

   public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ColumnInfo(name = "id")
   @PrimaryKey
    private Integer id;

    @ColumnInfo(name = "idMeal")
   private Long idMeal;

    @ColumnInfo(name = "strMeal")
    private String strMeal;

    @ColumnInfo(name = "strMealThumb")
    private String strMealThumb;



    public MealList(String strMeal,
                String strMealThumb,
                Long idMeal,
                    String day) {

        this.strMeal = strMeal;
        this.strMealThumb = strMealThumb;
        this.idMeal = idMeal;
        this.day=day;
    }

    public MealList() {
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