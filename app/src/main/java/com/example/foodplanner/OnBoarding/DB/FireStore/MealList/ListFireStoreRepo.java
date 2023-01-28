package com.example.foodplanner.OnBoarding.DB.FireStore.MealList;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.foodplanner.OnBoarding.Utilites.CurrentUser;
import com.example.foodplanner.OnBoarding.Models.MealListModel.MealList;
import com.example.foodplanner.OnBoarding.Models.detailsModel.Detail;
import com.example.foodplanner.OnBoarding.DB.FireStore.MealListFS;
import com.example.foodplanner.OnBoarding.network.NetworkRepo;
import com.example.foodplanner.OnBoarding.network.Presenters.DetailPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ListFireStoreRepo   {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef;
    NetworkRepo helperr ;
    List<MealList> days_meals;
    ListFireStorePresenter listFireStorePresenter;

    public ListFireStoreRepo(ListFireStorePresenter fireStorePresenter) {
        this.listFireStorePresenter = fireStorePresenter;
    }

    public ListFireStoreRepo() {

    }
    private void insertMealListDay(MealList meal) {
        ArrayList<MealList> meals = new ArrayList<>();
        MealListFS mealListFS = new MealListFS(meals);
        db.collection("Meals").document(CurrentUser.getEmail())
                .set(mealListFS)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Toast.makeText(ge, "The Meal Added To Favorite", Toast.LENGTH_LONG).show();
                        Log.i("hi", "onSuccess: The Meal Added To Favorite");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.i("hi", "onFail: can't add Meal  To Favorite");
                    }
                });
    }
    private void updateMealList(MealList meal) {
        db.collection("Meals")
                .document(CurrentUser.getEmail())
                .update("Meals", FieldValue.arrayUnion(meal) ).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // Toast.makeText(requireContext(), "The Meal Added To Favorite", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //  Toast.makeText(requireContext(), "The Meal  Failed Added To Favorite Try Again", Toast.LENGTH_LONG).show();
                    }
                });
    }
    public void addMealListMeal(MealList meal) {

        docRef = db.collection("Meals").document(CurrentUser.getEmail());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("FireStore", "Document exists!");
                        updateMealList(meal);
                    } else {
                        Log.d("FireStore", "Document does not exist!");
                        insertMealListDay(meal);
                    }
                } else {
                    Log.d("FireStore", "Failed with: ", task.getException());
                }
            }
        });
    }
    public void deleteMealListMeal(MealList meal) {
        db.collection("Meals")
                .document(CurrentUser.getEmail())

                .update("Meals", FieldValue.arrayRemove(meal)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // Toast.makeText(requireContext(), "The Meal Added To Favorite", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //  Toast.makeText(requireContext(), "The Meal  Failed Added To Favorite Try Again", Toast.LENGTH_LONG).show();
                    }
                });

    }

    public void getMealList() {
        docRef = db.collection("Meals").document(CurrentUser.getEmail());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {


                        Map<String, Object> map = document.getData();
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if (entry.getKey().equals("Meals")) {
                                //Log.d("TAG", entry.getValue().toString());
//                                MealList meal =entry.getValue().
//                                Log.d("DATAA", ""+  entry.getValue().toString());
//                                days_meals.add(entry.getValue());
                            }
                        }
                        //document.get("Meals");
                    // MealListFS meals =  document.toObject(MealListFS.class);;

                        //MealList mealList = (MealList) meals.get(0);

                        //listFireStorePresenter.succsessFireStoreList(meals);
                        //meals = (MealListFS) document.toObject(MealListFS.class);
                        //Long id = Long.parseLong(idArray.get(0));


                    } else {
                        Log.d("DATA", "No such document");
                    }
                } else {
                    Log.d("DATA", "get failed with ", task.getException());
                }
            }
        });



    }



}
