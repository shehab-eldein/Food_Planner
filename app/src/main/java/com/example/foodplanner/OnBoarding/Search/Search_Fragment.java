package com.example.foodplanner.OnBoarding.Search;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.OnBoarding.Models.AreaModel.Area;
import com.example.foodplanner.OnBoarding.Models.AreaModel.RootArea;

import com.example.foodplanner.OnBoarding.Models.CategorySearchModel.Category;
import com.example.foodplanner.OnBoarding.Models.CategorySearchModel.RootCategory;
import com.example.foodplanner.OnBoarding.Models.Ingredient.Ingredient;
import com.example.foodplanner.OnBoarding.Models.Ingredient.RootMealIngredient;
import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;
import com.example.foodplanner.OnBoarding.Models.mealModel.RootMeal;


import com.example.foodplanner.OnBoarding.View.IngredientView.IngredientAdapter;
import com.example.foodplanner.OnBoarding.View.MealFilterdView.MealAdapterSearch;
import com.example.foodplanner.OnBoarding.View.viewSearch.OnSearchClick;

import com.example.foodplanner.OnBoarding.View.viewSearch.SearchAdapter;
import com.example.foodplanner.OnBoarding.network.APIClient;
import com.example.foodplanner.OnBoarding.network.APIinterfaceLists;
import com.example.foodplanner.OnBoarding.network.APIinterfaceMealFilter;
import com.example.foodplanner.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import retrofit2.Retrofit;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Search_Fragment extends Fragment implements OnSearchClick {


    RecyclerView recyclerView_result;
    SearchAdapter searchAdapter;
    EditText search;
    RecyclerView recyclerViewResults;
    MealAdapterSearch mealAdapterSearch;

    List<Ingredient> ingredients = new ArrayList<>();
    List<String> ingredientQuery = null;
    List<Area> areas = new ArrayList<>();
    List<String> areaQuery = new ArrayList<>();
    List<Category> categories = new ArrayList<>();
    List<String> categoryQuery = new ArrayList<>();
    private static final String TAG = "Search_Fragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        search = view.findViewById(R.id.search_view);
        recyclerView_result=view.findViewById(R.id.search_reslut_rv);
        recyclerView_result.setVisibility(View.GONE);
        recyclerViewResults=view.findViewById(R.id.filterd_rv);



        Retrofit apiClient = APIClient.getClient();
        APIinterfaceLists apiInterface = apiClient.create(APIinterfaceLists.class);


        apiInterface.getIngrdiants()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<RootMealIngredient>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull RootMealIngredient rootMealIngredient) {
                        ingredients = rootMealIngredient.getMeals();
                        Log.i("TAG", "onSuccess: iiiiiiiiiiiiiiiiiiiiiiii "+ingredients.size());
//                        recyclerView.setHasFixedSize(true);
//                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
//                        recyclerView.setLayoutManager(linearLayoutManager);
//                        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
//                        ingredientAdapter = new IngredientAdapter(ingredients);//, Search_Fragment.this);
//                        recyclerView.setAdapter(ingredientAdapter);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });


       ///////////////////////////////////////////////////////////////////////

        apiInterface.getAreas()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<RootArea>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull RootArea rootArea) {
                        areas=rootArea.getMeals();


                        Log.i("TAG", "onSuccess: aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+areas.size());
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
        ///////////////////////////////////////////////////////////////////////
        apiInterface.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<RootCategory>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull RootCategory rootCategory) {
                        categories=rootCategory.getMeals();


                        Log.i("TAG", "onSuccess: ggggggggggggggggggggggggggggggggggggggggggggg "+categories.size());
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
        ////////////////////////////////////////////////////////////
        Observable < String > observable_it = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<String> emitter) throws Throwable {
                search.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() == 0) {
                            searchAdapter = new SearchAdapter(new ArrayList<>(), Search_Fragment.this);
                            recyclerView_result.setAdapter(searchAdapter);
                        } else {
                            emitter.onNext(s.toString());
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                       closeKeyboard();
                    }
                });


            }
        });

        observable_it.subscribe(
                i -> {
                    //todo///search_by_ingredients_label_tv

                    recyclerView_result.setVisibility(View.VISIBLE);

                    Log.i(TAG, "onTextChanged: item is: " + i);


                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {


                        ingredientQuery = ingredients
                                .stream()
                                .filter(item -> item.getStrIngredient().toLowerCase().startsWith(i))
                                .map(ingredient -> ingredient.getStrIngredient().toString()).collect(Collectors.toList());
                        ////////////////////////////////////////
                        areaQuery=areas
                                .stream()
                                .filter(item -> item.getStrArea().toLowerCase().startsWith(i))
                                .map(area -> area.getStrArea().toString()).collect(Collectors.toList());
                        /////////////////////////////////////////////////////


                        categoryQuery=categories
                                .stream()
                                .filter(item -> item.getStrCategory().toLowerCase().startsWith(i))
                                .map(category -> category.getStrCategory().toString()).collect(Collectors.toList());


                        ////////////////////////////////////////////

                    }
                    //todo//clear rv

                    recyclerView_result.new Recycler();
                    recyclerView_result.setHasFixedSize(true);
                    LinearLayoutManager linearLayoutManagerSearch = new LinearLayoutManager(requireContext());
                    recyclerView_result.setLayoutManager(linearLayoutManagerSearch);
                    linearLayoutManagerSearch.setOrientation(RecyclerView.VERTICAL);

                    searchAdapter= new SearchAdapter(areaQuery,ingredientQuery,categoryQuery,Search_Fragment.this);

                    recyclerView_result.setAdapter(searchAdapter);

                }
                , error -> Log.i(TAG, "error: " + error.toString()),
                () -> Log.i(TAG, "completed ")

        );

    }

    @Override
    public void onClickItem(String searchItem,int check) {
        getMealsAPI(searchItem,check);
        closeKeyboard();
        recyclerView_result.setVisibility(View.GONE);

    }
    private void closeKeyboard()
    {
        // this will give us the view
        // which is currently focus
        // in this layout
        View view = getActivity().getCurrentFocus();

        // if nothing is currently
        // focus then this will protect
        // the app from crash
        if (view != null) {

            // now assign the system
            // service to InputMethodManager
            InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(
                            view.getWindowToken(), 0); }
    }

  public void getMealsAPI(String mealFilter,int check){

        Retrofit aPIinterfaceMealIngredient = APIClient.getClient();
        APIinterfaceMealFilter aPIinterfaceMealFilterInterface =aPIinterfaceMealIngredient.create(APIinterfaceMealFilter.class);
        if(check==1){

            aPIinterfaceMealFilterInterface.getFilteredMainAreaMeals(mealFilter).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<RootMeal>() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull RootMeal rootMeal) {

                            succsessMealList(rootMeal.getMeals());

                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                        }
                    });
        }
        else if(check==2) {
            aPIinterfaceMealFilterInterface.getFilteredMainIngredientMeals(mealFilter)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<RootMeal>() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull RootMeal rootMeal) {

                            succsessMealList(rootMeal.getMeals());
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                        }
                    });

        }
        else if(check==3) {
            aPIinterfaceMealFilterInterface.getFilteredMainCategoryMeals(mealFilter)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<RootMeal>() {
                                   @Override
                                   public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                                   }

                                   @Override
                                   public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull RootMeal rootMeal) {
                                       succsessMealList(rootMeal.getMeals());
                                   }

                                   @Override
                                   public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                                   }
                               }
                    );}

    }
    void succsessMealList(List<Meal> filterdListMeal){


        if(filterdListMeal==null) {
            Toast.makeText(getContext(), "No Meals Available", Toast.LENGTH_LONG).show();
        }
        else {

            if (!filterdListMeal.isEmpty()) {
                Toast.makeText(getContext(), "Done"  , Toast.LENGTH_LONG).show();

                recyclerViewResults.new Recycler();
                recyclerViewResults.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManagerSearch2 = new LinearLayoutManager(requireContext());
                recyclerViewResults.setLayoutManager(linearLayoutManagerSearch2);
                linearLayoutManagerSearch2.setOrientation(RecyclerView.HORIZONTAL);

                mealAdapterSearch= new MealAdapterSearch(filterdListMeal);
                recyclerViewResults.setAdapter(mealAdapterSearch);

            }
            else  if (filterdListMeal.size()==0)  {


                Toast.makeText(getContext(), "There are no meals available", Toast.LENGTH_LONG).show();
            }}
    }

}







