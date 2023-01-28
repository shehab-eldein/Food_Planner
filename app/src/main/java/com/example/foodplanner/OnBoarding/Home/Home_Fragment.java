package com.example.foodplanner.OnBoarding.Home;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;


import com.example.foodplanner.OnBoarding.Utilites.ConnectionReceiver;
import com.example.foodplanner.OnBoarding.Utilites.CurrentUser;
import com.example.foodplanner.OnBoarding.Utilites.Loading;
import com.example.foodplanner.OnBoarding.Models.CategoryModel.Category;
import com.example.foodplanner.OnBoarding.Models.MealListModel.MealList;
import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;
import com.example.foodplanner.OnBoarding.DB.FireStore.Favorite.FavFireStorePresenter;
import com.example.foodplanner.OnBoarding.DB.FireStore.Favorite.FavFireStoreRepo;
import com.example.foodplanner.OnBoarding.DB.FireStore.MealList.ListFireStorePresenter;
import com.example.foodplanner.OnBoarding.DB.FireStore.MealList.ListFireStoreRepo;
import com.example.foodplanner.OnBoarding.DB.Room.RoomRepo;
import com.example.foodplanner.OnBoarding.network.Presenters.CategoryPresenter;
import com.example.foodplanner.OnBoarding.network.NetworkRepo;
import com.example.foodplanner.OnBoarding.network.Presenters.RandomPresenter;
import com.example.foodplanner.OnBoarding.View.MealView.MealAdapter;
import com.example.foodplanner.OnBoarding.View.MealView.OnMealClick;
import com.example.foodplanner.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class Home_Fragment extends Fragment implements OnMealClick, RandomPresenter, CategoryPresenter
, FavFireStorePresenter, ListFireStorePresenter {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    RecyclerView categoryRecyclerView;
    RecyclerView randomRecyclerView;
    MealAdapter adapter;
    ArrayList<Meal> mealArrayList;
    ArrayList<Category> categoryArrayList;
    NetworkRepo helperr ;
    DocumentReference docRef;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<String> idArray= new ArrayList<>();
    RoomRepo repo;
    ImageView logout_btn;
    ImageView backeUp;
    FavFireStoreRepo fireStoreRepo;
    ListFireStoreRepo listFireStoreRepo;
    TextView userName;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        helperr =  new NetworkRepo(this,this);
        repo = new RoomRepo(requireContext());
        fireStoreRepo = new FavFireStoreRepo(this);
        listFireStoreRepo = new ListFireStoreRepo(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // docRef =  db.collection("Fav").document(CurrentUser.getEmail());
        return inflater.inflate(R.layout.fragment_home_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        connectDesign(view);
        helperr.getRandomMeals();
        helperr.getCategories();
        logOutBtnClicked();
      //  Loading.activeLoading(requireContext());
        setCurrentUser();
        backeUpBtnClicked();
        setUserName();


    }
    void setUserName() {
        if (CurrentUser.getIsGuest()) {
            userName.setText("Guest");
            backeUp.setVisibility(View.GONE);
        } else {
            userName.setText(CurrentUser.getEmail());
        }

    }
    void connectDesign(View view) {
        categoryRecyclerView = view.findViewById(R.id.Recycler_File);
        randomRecyclerView = view.findViewById(R.id.rv);
        logout_btn=view.findViewById(R.id.logOut_btn);
        backeUp = view.findViewById(R.id.bakeup_label);
        userName = view.findViewById(R.id.UserName);
    }
    void backeUpBtnClicked() {
        backeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireStoreRepo.getFavList();
                listFireStoreRepo.getMealList();
                Loading.activeLoading(requireContext());
            }
        });


    }
    void setCurrentUser() {
        if (firebaseAuth.getCurrentUser() != null) {
            CurrentUser.setEmail(firebaseAuth.getCurrentUser().getEmail());
        }

    }
    void logOutBtnClicked() {
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              repo.deleteAllFav();
              repo.deleteAllList();
              goSigns();


            }
        });

    }
    public void goSigns() {
        Navigation.findNavController(getView()).navigate(R.id.signs_Fragment);
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



    @Override
    public void succsessFireStoreFav(List<Meal> meals) {
        for(Meal meal:meals) {
           repo.insertFav(meal);
        }
        Loading.dismiss();

    }
    @Override
    public void failFireStoreFav(String err) {

    }

    @Override
    public void succsessFireStoreList(List<MealList> meals) {
        for(MealList meal:meals) {
            meal.setDay("Sunday");
            repo.insertMealList(meal);
            //repo.insertFav(new Meal(meal.getStrMeal(),meal.getStrMealThumb(),meal.getIdMeal(),"hello","egypt"));
        }
        Loading.dismiss();
    }

    @Override
    public void failFireStoreList(String err) {
        Log.i("eeee", "failFireStoreList: "+err);

    }


}