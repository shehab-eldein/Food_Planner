package com.example.foodplanner.OnBoarding.MealList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanner.OnBoarding.Models.MealListModel.MealList;


import com.example.foodplanner.OnBoarding.Utilites.DB.Room.DAO;
import com.example.foodplanner.OnBoarding.Utilites.DB.Room.RoomDatabase;
import com.example.foodplanner.OnBoarding.View.viewMealList.DayAdapter;
import com.example.foodplanner.OnBoarding.View.viewMealList.MealListAdapter;
import com.example.foodplanner.OnBoarding.View.viewMealList.OnDayClickListener;

import com.example.foodplanner.R;

import java.util.ArrayList;

import java.util.List;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MealList_Fragment extends Fragment implements  OnDayClickListener{
    RecyclerView mealList_rv;
    MealListAdapter mealListAdapter;
    List<MealList> mealList_meals;
    DAO dao;


    OnDayClickListener onDayClickListener;
    DayAdapter dayAdapter ;
    List<String>days_list;

    RecyclerView days_rv;
    String dayFilter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RoomDatabase roomDatabase = RoomDatabase.getInstance(requireContext());
        dao = roomDatabase.DAO();

        days_list=new ArrayList<String>();
        getDayList();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_list_, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealList_rv = view.findViewById(R.id.recycler_meal_list);
//        mealList_rv.setVisibility(View.GONE);
        days_rv=view.findViewById(R.id.recycler_days);
        days_list.add("Saturday");
        days_list.add("Sunday");
        days_list.add("Monday");
        days_list.add("Tuesday");
        days_list.add("Wendnesday");
        days_list.add("Thursday");
        days_list.add("Friday");
        days_rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(requireContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        days_rv.setLayoutManager(layoutManager);
        dayAdapter=new DayAdapter(days_list,this);
        days_rv.setAdapter(dayAdapter);


    }


    @Override
    public void onClickDay(String dayName) {

        dayFilter=dayName;
        getDayList();




    }
    void getDayList() {
        if (dayFilter == null ) {
            dayFilter = "Sunday";
        }
        dao.getListMeals(dayFilter)
                .subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<MealList>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<MealList> mealLists) {

                        mealList_meals = mealLists;
                        mealList_rv.setHasFixedSize(true);
                        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(requireContext());
                        mealList_rv.setLayoutManager(linearLayoutManager);
                        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                        mealListAdapter=new MealListAdapter(mealList_meals);
                        mealList_rv.setAdapter(mealListAdapter);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });


    }
}