package com.example.foodplanner.OnBoarding.network;

import com.example.foodplanner.OnBoarding.Models.CategoryModel.Category;
import com.example.foodplanner.OnBoarding.Models.CategoryModel.RootCategory;
import com.example.foodplanner.OnBoarding.Models.detailsModel.Detail;
import com.example.foodplanner.OnBoarding.Models.detailsModel.DetailRoot;
import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;
import com.example.foodplanner.OnBoarding.Models.mealModel.RootMeal;
import com.example.foodplanner.OnBoarding.network.Presenters.CategoryPresenter;
import com.example.foodplanner.OnBoarding.network.Presenters.DetailPresenter;
import com.example.foodplanner.OnBoarding.network.Presenters.RandomPresenter;

import java.util.ArrayList;
import java.util.Random;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;

public class NetworkRepo {
    RandomPresenter randomDelegate;

    DetailPresenter detailDelegate;
    String[] random_countrys = new String[]{"British", "French", "Egyptian", "Japanese", "Croatian", "Canadian", "Indian", "Polish"};

    ArrayList<Meal> meals;

    ArrayList<Detail> details;
    Retrofit mealClient = APIClient.getClient();
    APIinterface apiInterface = mealClient.create(APIinterface.class);
    Long id;


    public NetworkRepo(RandomPresenter randomDelegate) {
        this.randomDelegate = randomDelegate;

    }

    public NetworkRepo(DetailPresenter detailDelegate, Long id) {
        this.detailDelegate = detailDelegate;
        this.id = id;
    }



    public void getRandomMeals() {

        Observable<RootMeal> mealObservable = apiInterface.getFilteredMeals(random_countrys[new Random().nextInt(random_countrys.length)]);

        mealObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    meals = (ArrayList<Meal>) response.getMeals();
                    randomDelegate.succsessRandoms(meals);
                }, error -> {
                    randomDelegate.failRandoms(error.toString());

                });
    }

    public void getMealsDetails() {

        Observable<DetailRoot> mealObservable = apiInterface.getByID(id);

        mealObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    details = (ArrayList<Detail>) response.getMeals();
                    detailDelegate.succsessDetails(details);
                }, error -> {
                    detailDelegate.failDetails(error.toString());

                });
    }


}

