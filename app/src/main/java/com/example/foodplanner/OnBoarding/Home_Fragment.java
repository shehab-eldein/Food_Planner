package com.example.foodplanner.OnBoarding;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;



import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;
import com.example.foodplanner.OnBoarding.Models.mealModel.RootMeal;
import com.example.foodplanner.OnBoarding.Utilites.network.APIClient;
import com.example.foodplanner.OnBoarding.Utilites.network.APIInterfaceRandomMeal;
import com.example.foodplanner.OnBoarding.Utilites.network.APIinterface;
import com.example.foodplanner.OnBoarding.Views.view.MealAdapter;
import com.example.foodplanner.OnBoarding.Views.view.OnMealClick;
import com.example.foodplanner.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Home_Fragment extends Fragment implements OnMealClick {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;


    RecyclerView recyclerView;
    RecyclerView recyclerViewRandom;

    MealAdapter adapter;
    ArrayList<Meal> meals;
    String[] random_countrys;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        random_countrys=new String[]{"British","French","Egyptian","Japanese","Croatian","Canadian","Indian","Polish"};


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.Recycler_File);
        recyclerViewRandom=view.findViewById(R.id.rv);

        Retrofit apiClient= APIClient.getClient();

        APIinterface apiInterface = apiClient.create(APIinterface.class);
        APIInterfaceRandomMeal apiInterface2 =  apiClient.create( APIInterfaceRandomMeal.class);

        Call<RootMeal> call=apiInterface.getFilteredMeals(random_countrys[new Random().nextInt(random_countrys.length)]);
        call.enqueue( new Callback<RootMeal>() {
            @Override
            public void onResponse(Call<RootMeal> call, Response<RootMeal> response) {


                if(response.isSuccessful()){
                    meals  = (ArrayList<Meal>) response.body().getMeals();
                    recyclerView.setHasFixedSize(true);
                    // recyclerViewRandom.setHasFixedSize(true);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(requireContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    // recyclerViewRandom.setLayoutManager(linearLayoutManager);
                    linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                    adapter=new MealAdapter(meals, Home_Fragment.this);
                    recyclerView.setAdapter(adapter);
                    // recyclerViewRandom.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<RootMeal> call, Throwable t) {

            }
        });



        /////////////////////////////////////////////////////////////////////////





        //APIinterface apiInterface = apiClient.create(APIinterface.class);
        APIInterfaceRandomMeal apiInterface1 =  apiClient.create( APIInterfaceRandomMeal.class);

        Call<RootMeal> call1=apiInterface.getFilteredMeals(random_countrys[new Random().nextInt(random_countrys.length)]);
        call1.enqueue( new Callback<RootMeal>() {
            @Override
            public void onResponse(Call<RootMeal> call, Response<RootMeal> response) {


                if(response.isSuccessful()){
                    meals  = (ArrayList<Meal>) response.body().getMeals();
                    recyclerViewRandom.setHasFixedSize(true);
                    // recyclerViewRandom.setHasFixedSize(true);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(requireContext());
                    recyclerViewRandom.setLayoutManager(linearLayoutManager);
                    // recyclerViewRandom.setLayoutManager(linearLayoutManager);
                    linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                    adapter=new MealAdapter(meals, Home_Fragment.this);
                    recyclerViewRandom.setAdapter(adapter);
                    // recyclerViewRandom.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<RootMeal> call, Throwable t) {

            }
        });



    }

    @Override
    public void onClickIndex(int position) {
    }
}