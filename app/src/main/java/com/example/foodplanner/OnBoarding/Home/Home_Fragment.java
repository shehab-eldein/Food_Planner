package com.example.foodplanner.OnBoarding.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import java.util.ArrayList;


import com.example.foodplanner.OnBoarding.CurrentUser;
import com.example.foodplanner.OnBoarding.Loading;
import com.example.foodplanner.OnBoarding.Models.CategoryModel.Category;
import com.example.foodplanner.OnBoarding.Models.detailsModel.Detail;
import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;
import com.example.foodplanner.OnBoarding.Utilites.DB.Room.DAO;
import com.example.foodplanner.OnBoarding.Utilites.DB.Room.RoomDatabase;
import com.example.foodplanner.OnBoarding.Utilites.DB.Room.RoomRepo;
import com.example.foodplanner.OnBoarding.Utilites.network.Presenters.CategoryPresenter;
import com.example.foodplanner.OnBoarding.Utilites.network.Presenters.DetailPresenter;
import com.example.foodplanner.OnBoarding.Utilites.network.NetworkRepo;
import com.example.foodplanner.OnBoarding.Utilites.network.Presenters.RandomPresenter;
import com.example.foodplanner.OnBoarding.View.viewMeal.MealAdapter;
import com.example.foodplanner.OnBoarding.View.viewMeal.OnMealClick;
import com.example.foodplanner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class Home_Fragment extends Fragment implements OnMealClick, RandomPresenter, CategoryPresenter, DetailPresenter {
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
    Button logout_btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        helperr =  new NetworkRepo(this,this);
        repo = new RoomRepo(requireContext());




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
        categoryRecyclerView = view.findViewById(R.id.Recycler_File);
        randomRecyclerView = view.findViewById(R.id.rv);
        logout_btn=view.findViewById(R.id.logOut_btn);
        helperr.getRandomMeals();
        helperr.getCategories();
        logOutBtnClicked();
        Loading.activeLoading(requireContext());
        setCurrentUser();







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


            }
        });

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
                        helperr = new NetworkRepo(Home_Fragment.this,id);
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
    public void succsessDetails(ArrayList<Detail> details) {


    }

    @Override
    public void failDetails(String err) {

    }
}