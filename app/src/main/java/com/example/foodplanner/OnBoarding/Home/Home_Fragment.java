package com.example.foodplanner.OnBoarding.Home;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;


import com.example.foodplanner.OnBoarding.Models.mealModel.RootMeal;
import com.example.foodplanner.OnBoarding.Search.Search_Fragment;
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
import com.example.foodplanner.OnBoarding.View.MealFilterdView.FilteredMealAdapterSearch;
import com.example.foodplanner.OnBoarding.View.MealFilterdView.OnFilteredSearchMealsClick;
import com.example.foodplanner.OnBoarding.network.APIClient;
import com.example.foodplanner.OnBoarding.network.APIinterface;
import com.example.foodplanner.OnBoarding.network.APIinterfaceLists;
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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Function3;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;


public class Home_Fragment extends Fragment implements OnMealClick, RandomPresenter
, FavFireStorePresenter, ListFireStorePresenter, OnFilteredSearchMealsClick {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    RecyclerView categoryRecyclerView,randomRecyclerView;
    MealAdapter adapter;
    ArrayList<Meal> mealArrayList;
    NetworkRepo helperr ;
    View view;
    RoomRepo repo;
    ImageView logout_btn,backeUp;
    FavFireStoreRepo fireStoreRepo;
    ListFireStoreRepo listFireStoreRepo;
    TextView userName;
    List <Meal>mealArrayListRondome=new ArrayList<Meal>();
    FilteredMealAdapterSearch filteredMealAdapterSearch;
    SharedPreferences sharedPref;
    Boolean Registered;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        helperr =  new NetworkRepo(this);
        repo = new RoomRepo(requireContext());
        fireStoreRepo = new FavFireStoreRepo(this);
        listFireStoreRepo = new ListFireStoreRepo(this,requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        connectDesign(view);
        helperr.getRandomMeals();
        logOutBtnClicked();
       Loading.activeLoading(requireContext());
        setCurrentUser();
        backeUpBtnClicked();
        setUserName();
        getRandomMeal();
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
                CurrentUser.setIsGuest(false);
                sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                Registered = sharedPref.getBoolean("Registered", false);


            }
        });

    }
    public void goSigns() {
        Navigation.findNavController(getView()).navigate(R.id.signs_Fragment);
    }

    @Override
    public void succsessRandoms(ArrayList<Meal> meals) {
//        mealArrayList = meals;
//        randomRecyclerView.setHasFixedSize(true);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
//        randomRecyclerView.setLayoutManager(linearLayoutManager);
//        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
//        adapter = new MealAdapter(this.mealArrayList, Home_Fragment.this,true);
//        randomRecyclerView.setAdapter(adapter);
//        Loading.dismiss();








        mealArrayList = meals;
        categoryRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        categoryRecyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        adapter = new MealAdapter(this.mealArrayList, Home_Fragment.this, true);
        categoryRecyclerView.setAdapter(adapter);
        Loading.dismiss();





    }
    @Override
    public void failRandoms(String err) {
        Loading.dismiss();
        Loading.alert(requireContext(),"Can't Fetch Data For Network Reason Please Try Again Later");


    }
    @Override
    public void onClickIndex(int position) {

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
        Loading.dismiss();
        Loading.alert(requireContext(),"Can't Fetch Your Cloud Favorite For Network Reason Please Try Again Later");
    }
    @Override
    public void succsessFireStoreList(ArrayList<MealList> meals) {
        for(MealList meal:meals) {
            repo.insertMealList(meal);
        }

        Loading.dismiss();

    }
    @Override
    public void failFireStoreList(String err) {
        Loading.dismiss();
        Loading.alert(requireContext(),"Can't Fetch Your Cloud Meal List Data For Network Reason Please Try Again Later");


    }
    public void getRandomMeal(){



        Retrofit apiClient = APIClient.getClient();
        APIinterface apiInterface = apiClient.create(APIinterface.class);



        apiInterface.getRandomMeal()
                .subscribeOn(Schedulers.io())
                .repeat(10)
                .distinct()
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<RootMeal>>() {
                               @Override
                               public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                               }

                               @Override
                               public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<RootMeal> rootMeal) {
                                   for (RootMeal randomMealResponse : rootMeal){
                                       mealArrayListRondome.add(randomMealResponse.getMeals().get(0));

                                   }
                                   succsessMealList(mealArrayListRondome);

                               }

                               @Override
                               public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                               }
                           } );


    }
    void succsessMealList(List<Meal> filterdListMeal){


        if(filterdListMeal==null) {
            Toast.makeText(getContext(), "No Meals Available", Toast.LENGTH_LONG).show();
        }
        else {

            if (!filterdListMeal.isEmpty()) {
//                Toast.makeText(getContext(), "Done"  , Toast.LENGTH_LONG).show();
//
//                categoryRecyclerView.new Recycler();
//                categoryRecyclerView.setHasFixedSize(true);
//                LinearLayoutManager linearLayoutManagerSearch2 = new LinearLayoutManager(requireContext());
//                categoryRecyclerView.setLayoutManager(linearLayoutManagerSearch2);
//                linearLayoutManagerSearch2.setOrientation(RecyclerView.HORIZONTAL);
//
//                filteredMealAdapterSearch = new FilteredMealAdapterSearch(mealArrayListRondome,Home_Fragment.this,false);
//                categoryRecyclerView.setAdapter(filteredMealAdapterSearch);





                Toast.makeText(getContext(), "Done"  , Toast.LENGTH_LONG).show();
                randomRecyclerView.new Recycler();
                randomRecyclerView.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManagerSearch2 = new LinearLayoutManager(requireContext());
                randomRecyclerView.setLayoutManager(linearLayoutManagerSearch2);
                linearLayoutManagerSearch2.setOrientation(RecyclerView.HORIZONTAL);

                filteredMealAdapterSearch = new FilteredMealAdapterSearch(mealArrayListRondome,Home_Fragment.this,false);
                randomRecyclerView.setAdapter(filteredMealAdapterSearch);




            }
            else  if (filterdListMeal.size()==0)  {


                Toast.makeText(getContext(), "There are no meals available", Toast.LENGTH_LONG).show();
            }}
    }
    @Override
    public void onClickItem(Long idMeal) {
        Log.i("FilteredMealAdapterSearch", "onClick: Search Frag "+idMeal);
        Home_FragmentDirections.ActionHomeFragmentToDetailFragment action = Home_FragmentDirections.actionHomeFragmentToDetailFragment();
        action.setID(idMeal);
        Navigation.findNavController(view).navigate(action);
    }

}