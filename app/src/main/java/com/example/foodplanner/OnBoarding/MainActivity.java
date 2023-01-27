package com.example.foodplanner.OnBoarding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodplanner.OnBoarding.Home.Home_Fragment;
import com.example.foodplanner.OnBoarding.Utilites.AppStatus;
import com.example.foodplanner.OnBoarding.Utilites.ConnectionReceiver;
import com.example.foodplanner.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.checkerframework.checker.nullness.qual.NonNull;

public class MainActivity extends AppCompatActivity implements ConnectionReceiver.ReceiverListener {

View view;
    BottomNavigationView bottomNavigationView;
    NavController navController;
    Button reloadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activeNavigation();
        this.getSupportActionBar().hide();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);
        handelButtomNavigation();
        reloadBtn = findViewById(R.id.reload);


        checkConnection();
        reloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(MainActivity.this).isOnline()) {

                    showSnackBar(true);

                } else {
                    showSnackBar(false);


                }
            }
        });
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
        } else {
            message = "Not Connected to Internet";
        }
        // initialize snack bar
        Snackbar snackbar = Snackbar.make(findViewById(R.id.reload),message, Snackbar.LENGTH_LONG);
        // initialize view
       // View view = snackbar.getView();
        // Assign variable
       // TextView textView = view.findViewById(R.id.app_name_food_tv);
        // set text color
       // textView.setTextColor(color);
        // show snack bar
        snackbar.show();
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
