package com.example.foodplanner.OnBoarding.MealList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanner.OnBoarding.Models.MealListModel.MealList;


import com.example.foodplanner.OnBoarding.DB.FireStore.Favorite.FavFireStoreRepo;
import com.example.foodplanner.OnBoarding.DB.FireStore.MealList.ListFireStoreRepo;
import com.example.foodplanner.OnBoarding.DB.Room.Presenters.GetMealListPresenter;

import com.example.foodplanner.OnBoarding.DB.Room.RoomRepo;
import com.example.foodplanner.OnBoarding.View.MealListView.DayAdapter;
import com.example.foodplanner.OnBoarding.View.MealListView.MealListAdapter;
import com.example.foodplanner.OnBoarding.View.MealListView.OnDayClickListener;
import com.example.foodplanner.OnBoarding.View.MealListView.OnMeallistClickListener;
import com.example.foodplanner.R;

import java.util.ArrayList;

import java.util.List;


public class MealList_Fragment extends Fragment implements OnDayClickListener, OnMeallistClickListener, GetMealListPresenter {
    RecyclerView mealList_rv;
    MealListAdapter mealListAdapter;
    List<MealList> mealList_meals;
    DayAdapter dayAdapter ;
    List<String>days_list;
    RecyclerView days_rv;
    String dayFilter="sunday";
    RoomRepo repo;
    FavFireStoreRepo fireRepo;
    ListFireStoreRepo listFireStoreRepo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        days_list=new ArrayList<String>();
        repo = new RoomRepo(this,requireContext());
        fireRepo = new FavFireStoreRepo();
        listFireStoreRepo = new ListFireStoreRepo();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_list_, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealList_rv = view.findViewById(R.id.recycler_Fav);
        updateDayRecycleView(view);
        repo.getMealList(dayFilter);


    }
    void updateDayRecycleView(View view) {
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
        repo.getMealList(dayFilter);
    }


    @Override
    public void onClickMealList(MealList mealList, int position) {
        repo.deleteMealFromList(mealList);
        listFireStoreRepo.deleteMealListMeal(mealList);
        mealList_meals.remove(mealList);
        mealListAdapter.notifyItemRemoved(position);
    }

    @Override
    public void succsessMealList(List<MealList> meals) {
        mealList_meals = meals;
        mealList_rv.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(requireContext());
        mealList_rv.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mealListAdapter=new MealListAdapter(mealList_meals,MealList_Fragment.this);
        mealList_rv.setAdapter(mealListAdapter);

    }

    @Override
    public void failMealList(String err) {
        Log.i("tttt",err);

    }


}