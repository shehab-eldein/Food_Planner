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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.OnBoarding.CurrentUser;
import com.example.foodplanner.OnBoarding.Models.CategoryModel.Category;
import com.example.foodplanner.OnBoarding.Models.detailsModel.Detail;
import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;
import com.example.foodplanner.OnBoarding.UserFav;
import com.example.foodplanner.OnBoarding.Utilites.DB.Room.DAO;
import com.example.foodplanner.OnBoarding.Utilites.DB.Room.RoomDatabase;
import com.example.foodplanner.OnBoarding.Utilites.network.DetailNetwotkingDelegate;
import com.example.foodplanner.OnBoarding.Utilites.network.NetworkHelper;
import com.example.foodplanner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class Favorite_Fragment extends Fragment implements DetailNetwotkingDelegate {
    TextView instructions,mealName,country;
    ImageView mealImg,next,prev;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef;
    ArrayList<String> idArray= new ArrayList<>();
    NetworkHelper helperr ;
    int counter =1;

    List<Meal> fav_meals;
    DAO dao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        docRef = db.collection("Fav").document(CurrentUser.getEmail());
        RoomDatabase roomDatabase = RoomDatabase.getInstance(requireContext());
        dao = roomDatabase.DAO();
        return inflater.inflate(R.layout.fragment_favorite_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        connectDesign(view);
        getAllData();
        nextBtnClicked();
        prevBtnClicked();
        getFavMealsFromRoom();


    }
    void checkForBtnHide() {
        if (counter == idArray.size()-1){
            next.setVisibility(View.INVISIBLE);
        } else if (counter == 0){
            prev.setVisibility(View.VISIBLE);
        } else {
            next.setVisibility(View.VISIBLE);
            prev.setVisibility(View.VISIBLE);
        }

    }
    void nextBtnClicked() {

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               checkForBtnHide();
                Long id = Long.parseLong(idArray.get(counter));

//                helperr = new NetworkHelper(Favorite_Fragment.this,id);
//                helperr.getMealsDetails();

                instructions.setText(fav_meals.get(0).getInstraction());
                Glide.with(mealImg.getContext()).load(fav_meals.get(0).getStrMealThumb()).into(mealImg);
                country.setText(fav_meals.get(0).getArea());
                mealName.setText(fav_meals.get(0).getStrMeal());
                if (counter<idArray.size()-1 || counter == 0) {
                    counter+=1;
                }

            }
        });
    }
    void prevBtnClicked(){
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForBtnHide();
                Long id = Long.parseLong(idArray.get(counter));
                helperr = new NetworkHelper(Favorite_Fragment.this,id);
                helperr.getMealsDetails();

                if ( counter>=idArray.size()-1 ||counter<idArray.size()-1) {
                    counter-=1;
                }

            }
        });

    }
    void connectDesign(View view) {
        next = view.findViewById(R.id.next_Arrow);
        prev = view.findViewById(R.id.previous_Arrow);
        mealImg = view.findViewById(R.id.fav_img);
        instructions = view.findViewById(R.id.fav_instruction);
        mealName =view.findViewById(R.id.fav_mealName);
         country =view.findViewById(R.id.fav_Country);
    }

    void getAllData() {
        prev.setVisibility(View.INVISIBLE);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        idArray =  (ArrayList<String>)document.get("favMeals");
                        Long id = Long.parseLong(idArray.get(0));
                        helperr = new NetworkHelper(Favorite_Fragment.this,id);
                        helperr.getMealsDetails();
                    } else {
                        Log.d("DATA", "No such document");
                    }
                } else {
                    Log.d("DATA", "get failed with ", task.getException());
                }
            }
        });

    }

    @Override
    public void succsessDetails(ArrayList<Detail> details) {

        instructions.setText(details.get(0).getStrInstructions());
        Glide.with(mealImg.getContext()).load(details.get(0).getStrMealThumb()).into(mealImg);
        country.setText(details.get(0).getStrArea());
        mealName.setText(details.get(0).getStrMeal());

    }

    @Override
    public void failDetails(String err) {
        Log.e("err", "failDetails: "+ err.toString());
    }

    void getFavMealsFromRoom(){


        dao.getFavMeals().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<Meal>>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
            }
            @Override
            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Meal> meals) {
                fav_meals = meals;
            }
            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }
        });
    }
}