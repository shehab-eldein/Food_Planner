package com.example.foodplanner.OnBoarding.OnBoarding;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.OnBoarding.Utilites.CurrentUser;
import com.example.foodplanner.R;


public class Splash_Fragment extends Fragment {
    ImageView logo;
    LottieAnimationView gifImageView;
    SharedPreferences sharedPref;
    Boolean Registered;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        Registered = sharedPref.getBoolean("Registered", false);
        gifImageView = view.findViewById(R.id.gif_splash2);
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                // Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.onBoardingBase_Fragment);
                navigation();
            }
        }, 5000);

    }


    public void navigation(){

        if (Registered==true)
        {
            Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.home_Fragment);
            CurrentUser currentUser=new CurrentUser();
            currentUser.setEmail(sharedPref.getString("Email",""));
        }

        else {

            Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.onBoardingBase_Fragment);
        }

    }
}