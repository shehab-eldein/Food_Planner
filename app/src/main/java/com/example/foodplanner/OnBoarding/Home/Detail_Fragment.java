package com.example.foodplanner.OnBoarding.Home;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodplanner.OnBoarding.CurrentUser;
import com.example.foodplanner.OnBoarding.Models.MealListModel.MealList;
import com.example.foodplanner.OnBoarding.Models.detailsModel.Detail;
import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;
import com.example.foodplanner.OnBoarding.UserFav;
import com.example.foodplanner.OnBoarding.Utilites.DB.Room.DAO;
import com.example.foodplanner.OnBoarding.Utilites.DB.Room.RoomDatabase;
import com.example.foodplanner.OnBoarding.Utilites.network.DetailNetwotkingDelegate;
import com.example.foodplanner.OnBoarding.Utilites.network.NetworkHelper;
import com.example.foodplanner.OnBoarding.View.viewMeal.OnMealClick;
import com.example.foodplanner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;


import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class Detail_Fragment extends Fragment implements DetailNetwotkingDelegate {

    Long id;
    NetworkHelper helperr ;
    TextView mealName,mealArea,mealInstructions,favIcon,meal_list;
    ImageView mealImage;
    YouTubePlayerView mealVidio;
    private ArrayList<String> favMeals  = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    ProgressDialog progress;
    DocumentReference docRef;
    MealList mealList;
    Meal mealFav;
    DAO dao;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RoomDatabase roomDatabase = RoomDatabase.getInstance(requireContext());
        dao = roomDatabase.DAO();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activeLoading();
        return inflater.inflate(R.layout.fragment_detail_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        connectDesign(view);
        docRef = db.collection("Fav").document(CurrentUser.getEmail());
        getLifecycle().addObserver(mealVidio);
        id = Detail_FragmentArgs.fromBundle(getArguments()).getID();
        helperr = new NetworkHelper(this,id);
        helperr.getMealsDetails();
        favBtnClicked();
        mealListClicked();

    }

    void updateFireStore() {
        db.collection("Fav")
                .document(CurrentUser.getEmail())
                .update("favMeals", FieldValue.arrayUnion(id.toString())).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(requireContext(), "The Meal Added To Favorite", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(), "The Meal  Failed Added To Favorite Try Again", Toast.LENGTH_LONG).show();
                    }
                });
    }
    private void addToFireStore() {
        UserFav fav = new UserFav(favMeals);
        db.collection("Fav").document(CurrentUser.getEmail())
                .set(fav)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(requireContext(), "The Meal Added To Favorite", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(), "The Meal  Failed Added To Favorite Try Again", Toast.LENGTH_LONG).show();

                    }
                });
    }

    void favBtnClicked() {
       /* favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("FireStore", "Document exists!");
                                updateFireStore();
                            } else {
                                Log.d("FireStore", "Document does not exist!");
                                addToFireStore();
                            }
                        } else {
                            Log.d("FireStore", "Failed with: ", task.getException());
                        }
                    }
                });



            }
        });*/

        insertFav(mealFav);

    }

    public void insertFav(Meal meal) {
        dao.insertFavMeal(meal).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new CompletableObserver() {
                            @Override
                            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                            }

                            @Override
                            public void onComplete() {

                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                            }
                        }
                );

    }

    void mealListClicked(){

        meal_list.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        insertMealList(mealList);
                    }
                }
        );
    }

    void connectDesign(View view) {
        mealName = view.findViewById(R.id.meal_Name);
        mealArea = view.findViewById(R.id.detail_mealArea);
        mealImage = view.findViewById(R.id.detail_mealImage);
        mealInstructions = view.findViewById(R.id.instructionLabel);
        mealVidio = view.findViewById(R.id.videoView);
        favIcon = view.findViewById(R.id.fav_icon);
        meal_list=view.findViewById(R.id.meal_list);
    }
    void activeLoading() {
        progress = new ProgressDialog(requireContext());
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
    }


    @Override
    public void succsessDetails(ArrayList<Detail> details) {
        favMeals.add(details.get(0).idMeal);
        mealName.setText(details.get(0).getStrMeal());
        mealArea.setText(details.get(0).getStrArea());
        mealInstructions.setText(details.get(0).getStrInstructions());
        Glide.with(mealImage.getContext()).load(details.get(0).getStrMealThumb()).into(mealImage);
        progress.dismiss();
        String[] separated = details.get(0).strYoutube.split("=");
        mealVidio.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = separated[1];
                youTubePlayer.loadVideo(videoId, 0);
            }
        });

        /////todo drop list

        mealList=new MealList(details.get(0).strMeal,details.get(0).getStrMealThumb(),Long.valueOf(details.get(0).idMeal),"Sunday");

//        String strMeal,
//        String strMealThumb,
//        Long idMeal,
//        String instraction,
//        String area


mealFav=new Meal(details.get(0).strMeal,details.get(0).strMealThumb,Long.valueOf(details.get(0).idMeal),
        details.get(0).getStrInstructions(),details.get(0).strArea);


    }

    @Override
    public void failDetails(String err) {

    }


    public void insertMealList(MealList mealList) {
        dao.insertListMeal(mealList).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new CompletableObserver() {
                            @Override
                            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                            }

                            @Override
                            public void onComplete() {

                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                            }
                        }
                );

    }
}