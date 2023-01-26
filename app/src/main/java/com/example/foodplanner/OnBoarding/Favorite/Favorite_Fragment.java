package com.example.foodplanner.OnBoarding.Favorite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanner.OnBoarding.Loading;
import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;
import com.example.foodplanner.OnBoarding.Utilites.DB.Room.Presenters.GetFavPresenter;
import com.example.foodplanner.OnBoarding.Utilites.DB.Room.RoomRepo;
import com.example.foodplanner.OnBoarding.View.viewMealList.MealListAdapter;
import com.example.foodplanner.OnBoarding.View.viewMealList.OnClickMealListenerFav;
import com.example.foodplanner.R;

import java.util.List;


public class Favorite_Fragment extends Fragment implements OnClickMealListenerFav, GetFavPresenter {
    RecyclerView fav_rv;
    MealListAdapter fav_Adapter;
    List<Meal> fav_meals;
    RoomRepo repo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        repo = new RoomRepo(this,requireContext());
        return inflater.inflate(R.layout.fragment_favorite_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        connectDesign(view);
        Loading.activeLoading(requireContext());
        repo.getFavMeals();
    }

    void connectDesign(View view) {
        fav_rv = view.findViewById(R.id.recycler_Fav);
    }
    @Override
    public void onClickMealFav(Meal meal, int position) {
        fav_meals.remove(meal);
        repo.deleteMealFromFav(meal);
        fav_Adapter.notifyItemRemoved(position);
    }

    @Override
    public void succsessFavList(List<Meal> meals) {
        fav_meals = meals;
        Loading.dismiss();
        fav_rv.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(requireContext());
        fav_rv.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        fav_Adapter=new MealListAdapter(fav_meals, Favorite_Fragment.this,Favorite_Fragment.this);
        fav_rv.setAdapter(fav_Adapter);
    }

    @Override
    public void failFavList(String err) {

    }


}