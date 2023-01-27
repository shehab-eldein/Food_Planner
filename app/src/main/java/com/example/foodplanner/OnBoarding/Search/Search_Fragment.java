package com.example.foodplanner.OnBoarding.Search;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.OnBoarding.Models.Ingredient.Ingredient;
import com.example.foodplanner.OnBoarding.Models.Ingredient.RootMealIngredient;
import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;
import com.example.foodplanner.OnBoarding.Models.mealModel.RootMeal;
import com.example.foodplanner.OnBoarding.network.APIClient;
import com.example.foodplanner.OnBoarding.network.APIinterfaceIngredient;
import com.example.foodplanner.OnBoarding.network.APIinterfaceMealIngredient;

import com.example.foodplanner.OnBoarding.View.IngredientView.IngredientAdapter;
import com.example.foodplanner.OnBoarding.View.IngredientView.OnSearchClick;
import com.example.foodplanner.OnBoarding.View.MealFilterdView.MealAdapterSearch;
import com.example.foodplanner.OnBoarding.View.viewSearch.SearchAdapter;
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
    IngredientAdapter ingredientAdapter;
    List<Ingredient> ingredients = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView recyclerView_result;
    SearchAdapter searchAdapter;
    List<String> ingredientQuery = null;
    TextView search_ing_tv;
    EditText search;

    String filterdMealIngred="salmon";


    List<Meal> filterdIngredMealsQuery = new ArrayList<>(); ;
    RecyclerView recyclerViewResults;
    MealAdapterSearch mealAdapterSearch;



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


        recyclerView = view.findViewById(R.id.ingredients_rv);

        search = view.findViewById(R.id.search_view);
        search_ing_tv=view.findViewById(R.id.search_by_ingredients_label_tv);
        recyclerView_result=view.findViewById(R.id.search_reslut_rv);
        recyclerView_result.setVisibility(View.GONE);

        recyclerViewResults=view.findViewById(R.id.filterd_rv);

        Retrofit apiClient = APIClient.getClient();
        APIinterfaceIngredient apiInterface = apiClient.create(APIinterfaceIngredient.class);


        apiInterface.getingrdiants()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<RootMealIngredient>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull RootMealIngredient rootMealIngredient) {
                        ingredients = rootMealIngredient.getMeals();
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
                    }
                    //todo//clear rv
                    recyclerView_result.new Recycler();
                    recyclerView_result.setHasFixedSize(true);
                    LinearLayoutManager linearLayoutManagerSearch = new LinearLayoutManager(requireContext());
                    recyclerView_result.setLayoutManager(linearLayoutManagerSearch);
                    linearLayoutManagerSearch.setOrientation(RecyclerView.VERTICAL);

                    searchAdapter= new SearchAdapter(ingredientQuery,Search_Fragment.this);
                    recyclerView_result.setAdapter(searchAdapter);

                }
                , error -> Log.i(TAG, "error: " + error.toString()),
                () -> Log.i(TAG, "completed ")

        );

    }

    @Override
    public void onClickItem(String searchItem) {
        filterdMealIngred=searchItem;
        List<Meal> temp=getMealsAPI(filterdMealIngred)  ;
        if(temp==null){
            Log.i("tt", "nullllllllllllllllllllllllllllllllllll " );
            Toast.makeText(getContext(), "nullllllllllllllllllllllllllllllllllll", Toast.LENGTH_LONG).show();
        }

        else {

            if (!temp.isEmpty()) {
                succsessMealList(temp);
                Log.i("tt", "doneeeeeeeeeeeeeeeeeee " );
                Toast.makeText(getContext(), "doneeeeeeeeeeeeeeeeeee", Toast.LENGTH_LONG).show();
            }
            else {
                Log.i("tt", "emptyyyyyyyyyyyyyyyyyyyyyy " );
                Toast.makeText(getContext(), "emptyyyyyyyyyyyyyyyyyyyyyy", Toast.LENGTH_LONG).show();
            }}
    }


    public List<Meal> getMealsAPI(String MealIngred){

        Retrofit aPIinterfaceMealIngredient = APIClient.getClient();
        APIinterfaceMealIngredient aPIinterfaceMealIngredientInterface =aPIinterfaceMealIngredient.create(APIinterfaceMealIngredient.class);

        aPIinterfaceMealIngredientInterface.getFilteredMainIngredientMeals(MealIngred)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<RootMeal>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull RootMeal rootMeal) {
                        filterdIngredMealsQuery =rootMeal.getMeals();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });

        return filterdIngredMealsQuery;

    }
    void succsessMealList(List<Meal> filterdListMeal){

        recyclerViewResults.new Recycler();
        recyclerViewResults.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManagerSearch2 = new LinearLayoutManager(requireContext());
        recyclerViewResults.setLayoutManager(linearLayoutManagerSearch2);
        linearLayoutManagerSearch2.setOrientation(RecyclerView.HORIZONTAL);

        mealAdapterSearch= new MealAdapterSearch(filterdListMeal);
        recyclerViewResults.setAdapter(mealAdapterSearch);

    }

}







