package com.example.foodplanner.OnBoarding.DB.FireStore.MealList;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foodplanner.OnBoarding.DB.Room.RoomRepo;
import com.example.foodplanner.OnBoarding.Utilites.CurrentUser;
import com.example.foodplanner.OnBoarding.Models.MealListModel.MealList;
import com.example.foodplanner.OnBoarding.DB.FireStore.Model.MealListFS;
import com.example.foodplanner.OnBoarding.network.NetworkRepo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListFireStoreRepo  {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef;
    NetworkRepo helperr ;
    ArrayList<MealList> days_meals;
    ListFireStorePresenter listFireStorePresenter;
    Context requiredContext;
    public ListFireStoreRepo(ListFireStorePresenter fireStorePresenter, Context requiredContext) {


        this.listFireStorePresenter = fireStorePresenter;
    }

    public ListFireStoreRepo() {

    }
    private void insertMealListDay(MealList meal) {
       // String day, String idMeal, String strMeal, String strMealThumb
        MealListFS myMeal = new MealListFS(meal.getDay(),String.valueOf(meal.getIdMeal()),meal.strMeal,meal.strMealThumb);
        db.collection("Meals").document(CurrentUser.getEmail())
                .set(myMeal)
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
        MealListFS myMeal = new MealListFS(meal.getDay(),String.valueOf(meal.getIdMeal()),meal.strMeal,meal.strMealThumb);
        db.collection("Meals")
                .document(CurrentUser.getEmail())
                .update("Meals", FieldValue.arrayUnion(myMeal) ).addOnSuccessListener(new OnSuccessListener<Void>() {
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
        db.collection("Meals").document(CurrentUser.getEmail())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("DATAA", "DocumentSnapshot successfully deleted!");


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("DATAA", "Error deleting document", e);
                    }
                });

    }

    public void getMealList() {
        days_meals = new ArrayList<>();
        docRef = db.collection("Meals").document(CurrentUser.getEmail());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        List<Map<String, Object>> mealsss = (List<Map<String, Object>>) document.get("Meals");
                        for (Map<String,Object> meal :mealsss) {
                            MealList mealList = new MealList(meal.get("strMeal").toString(),meal.get("strMealThumb").toString(), Long.parseLong( meal.get("idMeal").toString()),meal.get("day").toString());
                            days_meals.add(mealList);
                        }

                        listFireStorePresenter.succsessFireStoreList(days_meals);



                    }


                    } else {
                        Log.d("DATA", "No such document");
                    }
                }

        });



}



}

