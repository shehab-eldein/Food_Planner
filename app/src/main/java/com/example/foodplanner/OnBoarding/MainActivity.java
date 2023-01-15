package com.example.foodplanner.OnBoarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.foodplanner.R;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activeNavigation();



    }

     private void activeNavigation() {
         NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);
         NavigationUI.setupActionBarWithNavController(this,navController);
    }









}
