package com.example.foodplanner.OnBoarding.Utilites.DB.FireStore.MealList;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foodplanner.OnBoarding.CurrentUser;
import com.example.foodplanner.OnBoarding.Models.MealListModel.MealList;
import com.example.foodplanner.OnBoarding.Models.detailsModel.Detail;
import com.example.foodplanner.OnBoarding.Utilites.DB.FireStore.Favorite.FavFireStorePresenter;
import com.example.foodplanner.OnBoarding.Utilites.DB.FireStore.MealListFS;
import com.example.foodplanner.OnBoarding.Utilites.network.NetworkRepo;
import com.example.foodplanner.OnBoarding.Utilites.network.Presenters.DetailPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ListFireStoreRepo implements DetailPresenter {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth;
    DocumentReference docRef;
    NetworkRepo helperr ;
    List<MealList> days_meals;
    ListFireStorePresenter listFireStorePresenter;
    ArrayList<String> idDays ;



    public ListFireStoreRepo(ListFireStorePresenter fireStorePresenter) {
        this.listFireStorePresenter = fireStorePresenter;
    }

    public ListFireStoreRepo() {

    }


    @Override
    public void succsessDetails(ArrayList<Detail> details) {

        days_meals.add(new MealList(details.get(0).strMeal,details.get(0).strMealThumb,Long.parseLong(details.get(0).idMeal)));
        if (days_meals.size() == idDays.size()) {
            listFireStorePresenter.succsessFireStoreList(days_meals);

            // fav_meals.removeAll(fav_meals);
        }

    }
    @Override
    public void failDetails(String err) {
        listFireStorePresenter.failFireStoreList(err);
    }


    private void insertMealListDay(String id) {
        ArrayList<String> Saturday  = new ArrayList<>();
        ArrayList<String> Sunday  = new ArrayList<>();
        ArrayList<String> Monday  = new ArrayList<>();
        ArrayList<String> Tuesday  = new ArrayList<>();
        ArrayList<String> Wendnesday  = new ArrayList<>();
        ArrayList<String> Thursday  = new ArrayList<>();
        ArrayList<String> Friday  = new ArrayList<>();
        Sunday.add(id);
        MealListFS mealListFS = new MealListFS(Saturday,Sunday,Monday,Tuesday,Wendnesday,Thursday,Friday);
        db.collection("Fav").document(CurrentUser.getEmail())
                .set(mealListFS)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Toast.makeText(requireContext(), "The Meal Added To Favorite", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(requireContext(), "The Meal  Failed Added To Favorite Try Again", Toast.LENGTH_LONG).show();

                    }
                });
    }
    private void updateMealList(String id) {
        db.collection("Fav")
                .document(CurrentUser.getEmail())
                .update("sunday", FieldValue.arrayUnion(id) ).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public void addMealListMeal(String id) {
        docRef = db.collection("Fav").document(CurrentUser.getEmail());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("FireStore", "Document exists!");
                        updateMealList(id);
                    } else {
                        Log.d("FireStore", "Document does not exist!");
                        addMealListMeal(id);
                    }
                } else {
                    Log.d("FireStore", "Failed with: ", task.getException());
                }
            }
        });
    }
    public void deleteMealListMeal(String id) {
        db.collection("Fav")
                .document(CurrentUser.getEmail())

                .update("sunday", FieldValue.arrayRemove(id)).addOnSuccessListener(new OnSuccessListener<Void>() {
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
        days_meals = new ArrayList<>();
        docRef = db.collection("Fav").document(CurrentUser.getEmail());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        idDays = (ArrayList<String>) document.get("sunday");
                        //document.toObject(MealListFS.class);
                        //Long id = Long.parseLong(idArray.get(0));
                        for (String id :idDays) {
                            helperr = new NetworkRepo(ListFireStoreRepo.this,Long.parseLong(id));
                            helperr.getMealsDetails();
                        }

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
