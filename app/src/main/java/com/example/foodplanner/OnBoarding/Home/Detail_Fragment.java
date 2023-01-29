package com.example.foodplanner.OnBoarding.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodplanner.OnBoarding.Utilites.CurrentUser;
import com.example.foodplanner.OnBoarding.Utilites.Loading;
import com.example.foodplanner.OnBoarding.Models.MealListModel.MealList;
import com.example.foodplanner.OnBoarding.Models.detailsModel.Detail;
import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;
import com.example.foodplanner.OnBoarding.DB.FireStore.Favorite.FavFireStoreRepo;
import com.example.foodplanner.OnBoarding.DB.FireStore.MealList.ListFireStoreRepo;
import com.example.foodplanner.OnBoarding.DB.Room.RoomRepo;
import com.example.foodplanner.OnBoarding.network.Presenters.DetailPresenter;
import com.example.foodplanner.OnBoarding.network.NetworkRepo;
import com.example.foodplanner.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;


import java.util.ArrayList;


public class Detail_Fragment extends Fragment implements DetailPresenter, AdapterView.OnItemSelectedListener {
    Long id;
    NetworkRepo helperr ;
    TextView mealName,mealArea,mealInstructions,favIcon,meal_list;
    ImageView mealImage;
    YouTubePlayerView mealVidio;
    private ArrayList<String> favMeals  = new ArrayList<>();
    MealList mealList = new MealList();
    Meal mealFav;
    RoomRepo repo;
    FavFireStoreRepo fireRepo;
    ListFireStoreRepo listFireStoreRepo;
    String[] days = {"Sunday", "Monday", "Tuesday", "Wendnesday","Thursday","Friday","Saturday"};
    String day= "Sunday";
    Spinner spino;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repo = new RoomRepo(requireContext());
        fireRepo = new FavFireStoreRepo();
        listFireStoreRepo = new ListFireStoreRepo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Loading.activeLoading(requireContext());
        return inflater.inflate(R.layout.fragment_detail_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        connectDesign(view);
        getLifecycle().addObserver(mealVidio);
        id = Detail_FragmentArgs.fromBundle(getArguments()).getID();
        favBtnClicked();
        ListBtnClicked();
        checkGeust();
        setSpinnerAdapter();
        setPresenter();

    }
    void setPresenter() {
        helperr = new NetworkRepo(this,id);
        helperr.getMealsDetails();
    }
    void setSpinnerAdapter() {
        spino.setOnItemSelectedListener(this);
        ArrayAdapter ad = new ArrayAdapter(requireActivity(),android.R.layout.simple_spinner_item,days);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spino.setAdapter(ad);
    }
    void checkGeust() {
        if (CurrentUser.getIsGuest()) {
            favIcon.setVisibility(View.GONE);
            meal_list.setVisibility(View.GONE);
        }
    }
    void favBtnClicked() {
        favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repo.insertFav(mealFav);
                fireRepo.addFavMeal(  String.valueOf(mealFav.getIdMeal()));
                Toast.makeText(getContext(), "Your Meal Added to Favorite", Toast.LENGTH_SHORT).show();

            }
        });
    }
    void ListBtnClicked(){
        meal_list.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        repo.insertMealList(mealList);
                        listFireStoreRepo.addMealListMeal(mealList);
                        Toast.makeText(getContext(), "Your Meal Added to your Meal List", Toast.LENGTH_SHORT).show();

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
        spino = view.findViewById(R.id.spinnerDays);

    }

    @Override
    public void succsessDetails(ArrayList<Detail> details) {
        favMeals.add(details.get(0).idMeal);
        mealName.setText(details.get(0).getStrMeal());
        mealArea.setText(details.get(0).getStrArea());
        mealInstructions.setText(details.get(0).getStrInstructions());
        Glide.with(mealImage.getContext()).load(details.get(0).getStrMealThumb()).into(mealImage);
       Loading.dismiss();
        String[] separated = details.get(0).strYoutube.split("=");
        mealVidio.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = separated[1];
                youTubePlayer.loadVideo(videoId, 0);
            }
        });

        /////todo drop list
        mealList=new MealList(details.get(0).strMeal,details.get(0).getStrMealThumb(),Long.valueOf(details.get(0).idMeal),day);
        mealFav=new Meal(details.get(0).strMeal,details.get(0).strMealThumb,Long.valueOf(details.get(0).idMeal),details.get(0).strInstructions,details.get(0).strArea);

    }
    @Override
    public void failDetails(String err) {

        Loading.dismiss();
        Loading.alert(requireContext(),"Can't Get Meal Details Know According to Network Please Try Again Later");

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(requireContext(), days[position], Toast.LENGTH_LONG)
                .show();
        day = days[position];
        mealList.setDay(day);
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}