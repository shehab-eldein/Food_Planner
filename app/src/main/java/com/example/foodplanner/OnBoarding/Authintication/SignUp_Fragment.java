package com.example.foodplanner.OnBoarding.Authintication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodplanner.R;

public class SignUp_Fragment extends Fragment {



    TextView email,pass,signIn;
    Button signUp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        connectDesign(view);
    }
    void connectDesign(View view) {
        email = view.findViewById(R.id.UP_Email);
        pass = view.findViewById(R.id.Up_Password);
        signIn = view.findViewById(R.id.UP_signIn);
        signUp = view.findViewById(R.id.UP_signUp_Btn);

    }
}