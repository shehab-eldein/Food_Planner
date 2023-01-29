package com.example.foodplanner.OnBoarding.DB.Room;

import android.content.Context;
import android.util.Log;

import com.example.foodplanner.OnBoarding.Models.MealListModel.MealList;
import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;
import com.example.foodplanner.OnBoarding.DB.Room.Presenters.GetFavPresenter;
import com.example.foodplanner.OnBoarding.DB.Room.Presenters.GetMealListPresenter;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RoomRepo {
    List <Meal> fav_meals;
    List<MealList> mealList_meals;
    DAO dao;
    GetFavPresenter getFavPresenter;
    GetMealListPresenter getMealListPresenter;
    Context requireContext;


    public RoomRepo(GetMealListPresenter getMealListPresenter,Context requireContext) {
        this.getMealListPresenter = getMealListPresenter;
        RoomDatabase roomDatabase = RoomDatabase.getInstance(requireContext);
        dao = roomDatabase.DAO();
        this.requireContext = requireContext;

    }

    public RoomRepo(Context requireContext) {
        RoomDatabase roomDatabase = RoomDatabase.getInstance(requireContext);
        dao = roomDatabase.DAO();
        this.requireContext = requireContext;
    }

    public RoomRepo(GetFavPresenter presenter, Context requireContext) {
        RoomDatabase roomDatabase = RoomDatabase.getInstance(requireContext);
        dao = roomDatabase.DAO();
        this.getFavPresenter = presenter;
        this.requireContext = requireContext;

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

                               // Toast.makeText(requireContext, "The Meal Added To Offline Favorite", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                            }
                        }
                );

    }
    public void insertMealList(MealList mealList) {
        dao.insertListMeal(mealList).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new CompletableObserver() {
                            @Override
                            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                                Log.i("mealsss", "onSub: ");
                            }

                            @Override
                            public void onComplete() {

                                Log.i("mealsss", "onComplete: "+mealList.getIdMeal());
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                                Log.i("mealsss", "onEror: "+e.getMessage());
                            }
                        }
                );

    }
    public void getFavMeals(){
        dao.getFavMeals().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<Meal>>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
            }
            @Override
            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Meal> meals) {
                fav_meals = meals;
                getFavPresenter.succsessFavList(fav_meals);

            }
            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                getFavPresenter.failFavList(e.getMessage());
            }
        });

    }
    public void getMealsList(){
      dao.getListMeals().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<MealList>>() {
          @Override
          public void onSubscribe(@NonNull Disposable d) {

          }

          @Override
          public void onSuccess(@NonNull List<MealList> mealLists) {
              Log.i("roomm", "onSuccess: "+mealLists.size());

              mealList_meals = mealLists;


          }

          @Override
          public void onError(@NonNull Throwable e) {

              Log.i("roomm", "error: "+e.getMessage());
          }
      });

    }
    public void deleteMealFromFav(Meal meal) {
        dao.deleteFavMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {



                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }
    public void getMealList(String dayFilter) {
        dao.getListMeals(dayFilter)

                .subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<MealList>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<MealList> mealLists) {

                        mealList_meals = mealLists;
                        getMealListPresenter.succsessMealList(mealList_meals);

                        Log.i("mealsss", "Succ: "+mealLists.size());
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        getMealListPresenter.failMealList(e.getMessage());
                        Log.i("mealsss", "onError: "+e.getStackTrace());
                    }
                });
    }
    public void deleteMealFromList(MealList meal) {
        dao.deleteListMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {



                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }
    public void deleteAllList() {
        dao.deleteAllMealList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }
    public  void deleteAllFav() {
        dao.deleteAllFav()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }
}
