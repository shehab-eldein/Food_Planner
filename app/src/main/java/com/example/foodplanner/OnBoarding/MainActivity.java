package com.example.foodplanner.OnBoarding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.foodplanner.OnBoarding.Home.Home_Fragment;
import com.example.foodplanner.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.checkerframework.checker.nullness.qual.NonNull;

public class MainActivity extends AppCompatActivity {

View view;
    BottomNavigationView bottomNavigationView;
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activeNavigation();
        this.getSupportActionBar().hide();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);
        handelButtomNavigation();
    }

     private void activeNavigation() {
         navController = Navigation.findNavController(this,R.id.nav_host_fragment);
         NavigationUI.setupActionBarWithNavController(this,navController);
    }
    void handelButtomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch(item.getItemId())
                        {
                            case R.id.home:
                                Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.home_Fragment);
                                return true;
                            case R.id.search:
                                Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.search_Fragment);
                                return true;
                            case R.id.list:
                                Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.mealList_Fragment);
                                return true;
                            case R.id.fav:
                                Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.favorite_Fragment);
                                return true;
                        }

                        return false;
                    }
                });
    }



}
