package com.example.foodplanner.OnBoarding.Authintication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodplanner.OnBoarding.MainActivity;
import com.example.foodplanner.OnBoarding.Utilites.CurrentUser;
import com.example.foodplanner.R;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.w3c.dom.Text;


public class Signs_Fragment extends Fragment {

    Button signInnBtn, googleBtn, fbBtn,guestBtn;
    TextView signUpBtn;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    ProgressDialog progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.ClientId))
                .requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(requireContext(),options);
        firebaseAuth = FirebaseAuth.getInstance();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signs, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activeDesign(view);
        googleBtnTapped();
        signUpBtnTapped();
        signInBtnTapped();
        fbBtnTapped();
        guestBtnTapped();
    }


 void guestBtnTapped() {
        guestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentUser.setIsGuest(true);
                Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.home_Fragment);
            }
        });
 }
    public void signInBtnTapped() {
        signInnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_signs_Fragment_to_signIn_Fragment2);
            }
        });
    }

    public void signUpBtnTapped() {
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_signs_Fragment_to_signUp_Fragment2);
            }
        });
    }

    public void fbBtnTapped() {
        fbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void googleBtnTapped() {
        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = googleSignInClient.getSignInIntent();
              startActivityForResult(intent,100);
                 progress = new ProgressDialog(requireContext());
                progress.setTitle("Loading");
                progress.setMessage("Wait while loading...");
                progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                progress.show();


            }
        });
    }

    public void activeDesign(View view) {
        signInnBtn = view.findViewById(R.id.signIn_Btn);
        googleBtn = view.findViewById(R.id.google_Btn);
        fbBtn = view.findViewById(R.id.faceBook_Btn);
        signUpBtn = view.findViewById(R.id.signUP_textView);
        guestBtn = view.findViewById(R.id.guestBtn);
    }




    public void goHome() {
        Navigation.findNavController(getView()).navigate(R.id.action_signs_Fragment_to_home_Fragment);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            GoogleSignInAccount result = GoogleSignIn.getSignedInAccountFromIntent(data).getResult();
            if(result != null) {
                authWithGoogle(result);
            }
        }
    }

    private void authWithGoogle(GoogleSignInAccount result) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(result.getIdToken(),null);
        try {
            firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    // To dismiss the dialog
                    progress.dismiss();
                    goHome();
                }
            });
        }catch (Exception e) {
            Log.i("auth", "authWithGoogle: "+e.getMessage());
        }
    }
}




