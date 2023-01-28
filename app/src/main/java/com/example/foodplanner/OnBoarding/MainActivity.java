package com.example.foodplanner.OnBoarding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.foodplanner.OnBoarding.Utilites.ConnectionReceiver;
import com.example.foodplanner.OnBoarding.Utilites.CurrentUser;
import com.example.foodplanner.OnBoarding.Utilites.Loading;
import com.example.foodplanner.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.checkerframework.checker.nullness.qual.NonNull;

public class MainActivity extends AppCompatActivity implements ConnectionReceiver.ReceiverListener {

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
        handelNavigationVisual();
        checkConnection();

    }


    @Override
    public void onBackPressed() {
       return;
    }

    @Override
    protected void onPause() {
        super.onPause();

        checkConnection();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkConnection();

    }
    void handelNavigationVisual(){

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                switch (destination.getId()){
                    case R.id.splash_Fragment:
                    case R.id.onBoardingBase_Fragment:
                    case R.id.signs_Fragment:
                    case R.id.signIn_Fragment2:
                    case R.id.signUp_Fragment2:
                        bottomNavigationView.setVisibility(View.GONE);
                        break;
                    default:
                        bottomNavigationView.setVisibility(View.VISIBLE);
                }
            }
        });

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
                                if (CurrentUser.getIsGuest()) {
                                    Loading.guestAlert(MainActivity.this);
                                }else{
                                    Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.mealList_Fragment);
                                }
                                return true;
                            case R.id.fav:
                                if (CurrentUser.getIsGuest()) {
                                    Loading.guestAlert(MainActivity.this);
                                }else{
                                    Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.favorite_Fragment);
                                }

                                return true;
                        }

                        return false;
                    }
                });
    }

    private void checkConnection() {
        // initialize intent filter
        IntentFilter intentFilter = new IntentFilter();
        // add action
        intentFilter.addAction("android.new.conn.CONNECTIVITY_CHANGE");
        // register receiver
        registerReceiver(new ConnectionReceiver(), intentFilter);
        // Initialize listener
        ConnectionReceiver.Listener = this;
        // Initialize connectivity manager
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        // Initialize network info
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        // get connection status
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
        // display snack bar
        showSnackBar(isConnected);

    }
    private void showSnackBar(boolean isConnected) {
        // initialize color and message

        String message;

        // check condition
        if (isConnected) {
            message = "Connected to Internet";
            CurrentUser.setIsConnected(true);
        } else {
            message = "Not Connected to Internet";

            CurrentUser.setIsConnected(false);
        }

    }


    @Override
    public void onNetworkChange(boolean isConnected) {
        showSnackBar(isConnected);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
