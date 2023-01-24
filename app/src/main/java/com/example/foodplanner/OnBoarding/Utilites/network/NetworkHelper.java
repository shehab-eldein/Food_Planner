package com.example.foodplanner.OnBoarding.Utilites.network;

import com.example.foodplanner.OnBoarding.Models.CategoryModel.Category;
import com.example.foodplanner.OnBoarding.Models.CategoryModel.RootCategory;
import com.example.foodplanner.OnBoarding.Models.detailsModel.Detail;
import com.example.foodplanner.OnBoarding.Models.detailsModel.DetailRoot;
import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;
import com.example.foodplanner.OnBoarding.Models.mealModel.RootMeal;

import java.util.ArrayList;
import java.util.Random;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;

public class NetworkHelper {
    RandomNetworkingDelegate randomDelegate;
    CategoryNetworkingDelegate categoryDelegate;
    DetailNetwotkingDelegate detailDelegate;
    String[] random_countrys = new String[]{"British", "French", "Egyptian", "Japanese", "Croatian", "Canadian", "Indian", "Polish"};
    ArrayList<Meal> meals;
    ArrayList<Category> categories;
    ArrayList<Detail> details;
    Retrofit mealClient = APIClient.getClient();
    APIinterface apiInterface = mealClient.create(APIinterface.class);
    Long id;


    public NetworkHelper(RandomNetworkingDelegate randomDelegate, CategoryNetworkingDelegate categoryDelegate) {
        this.randomDelegate = randomDelegate;
        this.categoryDelegate = categoryDelegate;
    }

    public NetworkHelper(DetailNetwotkingDelegate detailDelegate, Long id) {
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
    public void getCategories() {

        Observable<RootCategory> categoryObservable = apiInterface.getAllCategories();

        categoryObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    categories = (ArrayList<Category>) response.getCategories();
                    categoryDelegate.succsessCategory(categories);
                }, error -> {
                    categoryDelegate.failCategory(error.toString());
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

