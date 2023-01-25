package com.example.foodplanner.OnBoarding.Home;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;


import com.example.foodplanner.OnBoarding.CurrentUser;
import com.example.foodplanner.OnBoarding.Loading;
import com.example.foodplanner.OnBoarding.Models.CategoryModel.Category;
import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;
import com.example.foodplanner.OnBoarding.Utilites.network.CategoryNetworkingDelegate;
import com.example.foodplanner.OnBoarding.Utilites.network.NetworkHelper;
import com.example.foodplanner.OnBoarding.Utilites.network.RandomNetworkingDelegate;
import com.example.foodplanner.OnBoarding.View.viewMeal.MealAdapter;
import com.example.foodplanner.OnBoarding.View.viewMeal.OnMealClick;
import com.example.foodplanner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Home_Fragment extends Fragment implements OnMealClick, RandomNetworkingDelegate, CategoryNetworkingDelegate {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    RecyclerView categoryRecyclerView;
    RecyclerView randomRecyclerView;
    MealAdapter adapter;
    ArrayList<Meal> mealArrayList;
    ArrayList<Category> categoryArrayList;
    NetworkHelper helperr ;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        helperr =  new NetworkHelper(this,this);
        helperr.getRandomMeals();
        helperr.getCategories();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryRecyclerView = view.findViewById(R.id.Recycler_File);
        randomRecyclerView = view.findViewById(R.id.rv);
        if (firebaseAuth.getCurrentUser() != null) {
            CurrentUser.setEmail(firebaseAuth.getCurrentUser().getEmail());
        }
        Loading.activeLoading(requireContext());

    }


    @Override
    public void succsessRandoms(ArrayList<Meal> meals) {
        mealArrayList = meals;
        randomRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        randomRecyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        adapter = new MealAdapter(this.mealArrayList, Home_Fragment.this,true);
        randomRecyclerView.setAdapter(adapter);
        Loading.dismiss();
    }

    @Override
    public void failRandoms(String err) {

    }

    @Override
    public void onClickIndex(int position) {

    }

    @Override
    public void succsessCategory(ArrayList<Category> categories) {
        categoryArrayList = categories;
        categoryRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        categoryRecyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        adapter = new MealAdapter(this.categoryArrayList,false);
        categoryRecyclerView.setAdapter(adapter);
        //progress.dismiss();
    }

    @Override
    public void failCategory(String err) {

    }
}