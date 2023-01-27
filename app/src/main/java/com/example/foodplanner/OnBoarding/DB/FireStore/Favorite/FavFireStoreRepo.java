package com.example.foodplanner.OnBoarding.DB.FireStore.Favorite;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foodplanner.OnBoarding.Utilites.CurrentUser;
import com.example.foodplanner.OnBoarding.Models.MealListModel.MealList;
import com.example.foodplanner.OnBoarding.Models.detailsModel.Detail;
import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;
import com.example.foodplanner.OnBoarding.DB.FireStore.UserFav;
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

import java.util.ArrayList;
import java.util.List;

public class FavFireStoreRepo implements DetailPresenter {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth;
    DocumentReference docRef;
    NetworkRepo helperr ;
    List<Meal> fav_meals;
    List<MealList> days_meals;
    FavFireStorePresenter fireStorePresenter;
    ArrayList<String> idArray;
    ArrayList<String> idDays ;



    public FavFireStoreRepo(FavFireStorePresenter fireStorePresenter) {
        this.fireStorePresenter = fireStorePresenter;
    }

    public FavFireStoreRepo() {

    }

    //********FAVORITE_List*********
    public void getFavList() {
        fav_meals = new ArrayList<>();
        docRef = db.collection("Fav").document(CurrentUser.getEmail());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                         idArray = (ArrayList<String>) document.get("favMeals");
                        //Long id = Long.parseLong(idArray.get(0));
                        for (String id :idArray) {
                            helperr = new NetworkRepo(FavFireStoreRepo.this,Long.parseLong(id));
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
    public void deleteFavMeal(String id) {
        db.collection("Fav")
                .document(CurrentUser.getEmail())

                .update("favMeals", FieldValue.arrayRemove(id)).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public void addFavMeal(String id) {
        docRef = db.collection("Fav").document(CurrentUser.getEmail());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("FireStore", "Document exists!");
                        updateFireStore(id);
                    } else {
                        Log.d("FireStore", "Document does not exist!");
                        addToFireStore(id);
                    }
                } else {
                    Log.d("FireStore", "Failed with: ", task.getException());
                }
            }
        });
    }
    private void updateFireStore(String id) {
        db.collection("Fav")
                .document(CurrentUser.getEmail())
                .update("favMeals", FieldValue.arrayUnion(id) ).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    private void addToFireStore(String id) {
        ArrayList<String> favMeals = new ArrayList<>();
        favMeals.add(id);
        UserFav fav = new UserFav(favMeals);
        db.collection("Fav").document(CurrentUser.getEmail())
                .set(fav)
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
    @Override
    public void succsessDetails(ArrayList<Detail> details) {

            fav_meals.add(new Meal(details.get(0).strMeal,details.get(0).strMealThumb,Long.parseLong(details.get(0).idMeal),
                    details.get(0).strInstructions,details.get(0).strArea));
            if (fav_meals.size() == idArray.size()) {
                fireStorePresenter.succsessFireStoreFav(fav_meals);

                // fav_meals.removeAll(fav_meals);
            }

        }
        @Override
    public void failDetails(String err) {
        fireStorePresenter.failFireStoreFav(err);
    }



}
