package com.example.foodplanner.OnBoarding.Home;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.OnBoarding.Models.detailsModel.Detail;
import com.example.foodplanner.OnBoarding.Utilites.network.DetailNetwotkingDelegate;
import com.example.foodplanner.OnBoarding.Utilites.network.Helper;
import com.example.foodplanner.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;


import java.util.ArrayList;


public class Detail_Fragment extends Fragment implements DetailNetwotkingDelegate {

    Long id;
    Helper helperr ;
    TextView mealName,mealArea,mealInstructions;
    ImageView mealImage;
    YouTubePlayerView mealVidio;

    ProgressDialog progress;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
         mealName = view.findViewById(R.id.meal_Name);
         mealArea = view.findViewById(R.id.detail_mealArea);
         mealImage = view.findViewById(R.id.detail_mealImage);
         mealInstructions = view.findViewById(R.id.instructionLabel);
         mealVidio = view.findViewById(R.id.videoView);
         getLifecycle().addObserver(mealVidio);
         id = Detail_FragmentArgs.fromBundle(getArguments()).getID();

        helperr = new Helper(this,id);
        helperr.getMealsDetails();




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

    }

    @Override
    public void failDetails(String err) {

    }
}