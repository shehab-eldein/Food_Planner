package com.example.foodplanner.OnBoarding.Utilites.DB.FireStore;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MealListFS {

    private ArrayList<String> Saturday;
    private ArrayList<String> Sunday;
    private ArrayList<String> Monday;
    private ArrayList<String> Tuesday;
    private ArrayList<String> Wendnesday;
    private ArrayList<String> Thursday;
    private ArrayList<String> Friday;

    public MealListFS() {
    }


    public MealListFS(ArrayList<String> saturday, ArrayList<String> sunday, ArrayList<String> monday, ArrayList<String> tuesday, ArrayList<String> wendnesday, ArrayList<String> thursday, ArrayList<String> friday) {
        Saturday = saturday;
        Sunday = sunday;
        Monday = monday;
        Tuesday = tuesday;
        Wendnesday = wendnesday;
        Thursday = thursday;
        Friday = friday;
    }

    public ArrayList<String> getSaturday() {
        return Saturday;
    }

    public void setSaturday(ArrayList<String> saturday) {
        Saturday = saturday;
    }

    public ArrayList<String> getSunday() {
        return Sunday;
    }

    public void setSunday(ArrayList<String> sunday) {
        Sunday = sunday;
    }

    public ArrayList<String> getMonday() {
        return Monday;
    }

    public void setMonday(ArrayList<String> monday) {
        Monday = monday;
    }

    public ArrayList<String> getTuesday() {
        return Tuesday;
    }

    public void setTuesday(ArrayList<String> tuesday) {
        Tuesday = tuesday;
    }

    public ArrayList<String> getWendnesday() {
        return Wendnesday;
    }

    public void setWendnesday(ArrayList<String> wendnesday) {
        Wendnesday = wendnesday;
    }

    public ArrayList<String> getThursday() {
        return Thursday;
    }

    public void setThursday(ArrayList<String> thursday) {
        Thursday = thursday;
    }

    public ArrayList<String> getFriday() {
        return Friday;
    }

    public void setFriday(ArrayList<String> friday) {
        Friday = friday;
    }
}
