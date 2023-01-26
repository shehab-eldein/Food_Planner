package com.example.foodplanner.OnBoarding.MealList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanner.OnBoarding.Models.MealListModel.MealList;


import com.example.foodplanner.OnBoarding.Utilites.DB.Room.DAO;
import com.example.foodplanner.OnBoarding.Utilites.DB.Room.Presenters.GetMealListPresenter;
import com.example.foodplanner.OnBoarding.Utilites.DB.Room.RoomDatabase;

import com.example.foodplanner.OnBoarding.Utilites.DB.Room.RoomRepo;
import com.example.foodplanner.OnBoarding.View.viewMealList.DayAdapter;
import com.example.foodplanner.OnBoarding.View.viewMealList.MealListAdapter;
import com.example.foodplanner.OnBoarding.View.viewMealList.OnDayClickListener;
import com.example.foodplanner.OnBoarding.View.viewMealList.OnMeallistClickListener;
import com.example.foodplanner.R;

import java.util.ArrayList;

import java.util.List;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import io.reactivex.rxjava3.core.CompletableObserver;


public class MealList_Fragment extends Fragment implements OnDayClickListener, OnMeallistClickListener, GetMealListPresenter {
    RecyclerView mealList_rv;
    MealListAdapter mealListAdapter;
    List<MealList> mealList_meals;
    DayAdapter dayAdapter ;
    List<String>days_list;
    RecyclerView days_rv;
    String dayFilter="Sunday";
    RoomRepo repo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        days_list=new ArrayList<String>();
        repo = new RoomRepo(this,requireContext());


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

    }
}