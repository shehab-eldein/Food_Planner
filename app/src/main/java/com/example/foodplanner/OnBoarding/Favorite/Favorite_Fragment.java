package com.example.foodplanner.OnBoarding.Favorite;

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
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.OnBoarding.Loading;
import com.example.foodplanner.OnBoarding.MealList.MealList_Fragment;
import com.example.foodplanner.OnBoarding.Models.MealListModel.MealList;
import com.example.foodplanner.OnBoarding.Models.detailsModel.Detail;
import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;
import com.example.foodplanner.OnBoarding.Utilites.DB.Room.DAO;
import com.example.foodplanner.OnBoarding.Utilites.DB.Room.RoomDatabase;
import com.example.foodplanner.OnBoarding.Utilites.network.DetailNetwotkingDelegate;
import com.example.foodplanner.OnBoarding.Utilites.network.NetworkHelper;
import com.example.foodplanner.OnBoarding.View.viewMealList.MealListAdapter;
import com.example.foodplanner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class Favorite_Fragment extends Fragment  {
    RecyclerView fav_rv;
    MealListAdapter fav_Adapter;
    List<Meal> fav_meals;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef;
    ArrayList<String> idArray= new ArrayList<>();
    NetworkHelper helperr ;
    DAO dao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // docRef = db.collection("Fav").document(CurrentUser.getEmail());
        RoomDatabase roomDatabase = RoomDatabase.getInstance(requireContext());
        dao = roomDatabase.DAO();

        return inflater.inflate(R.layout.fragment_favorite_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        connectDesign(view);
        //getAllData();
        getFavMealsFromRoom();
        Loading.activeLoading(requireContext());


    }







    void connectDesign(View view) {
        fav_rv = view.findViewById(R.id.recycler_Fav);
    }

    void getAllData() {


        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        idArray =  (ArrayList<String>)document.get("favMeals");
                        Long id = Long.parseLong(idArray.get(0));
                        //helperr = new NetworkHelper(Favorite_Fragment.this,id);
                        //helperr.getMealsDetails();
                    } else {
                        Log.d("DATA", "No such document");
                    }
                } else {
                    Log.d("DATA", "get failed with ", task.getException());
                }
            }
        });

    }





    void getFavMealsFromRoom(){


        dao.getFavMeals().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<Meal>>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
            }
            @Override
            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Meal> meals) {
                fav_meals = meals;
                Log.i("mealsss", "onSuccess: "+meals.size());
                Loading.dismiss();
                fav_rv.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(requireContext());
                fav_rv.setLayoutManager(linearLayoutManager);
                linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                fav_Adapter=new MealListAdapter(fav_meals, Favorite_Fragment.this);
                fav_rv.setAdapter(fav_Adapter);

            }
            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                Log.i("mealsss", "onError: "+e.getMessage());
            }
        });
    }
}