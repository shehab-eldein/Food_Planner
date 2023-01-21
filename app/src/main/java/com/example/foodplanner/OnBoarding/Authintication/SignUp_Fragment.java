package com.example.foodplanner.OnBoarding.Authintication;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class SignUp_Fragment extends Fragment {



    TextView email,pass,signIn,confirmPass;
    Button signUp;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    View v;
    private ProgressDialog loadingBar;
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
        signUpBtnClicked();
    }

    void signUpBtnClicked() {
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                loadingBar.setTitle("Registering");
                loadingBar.setMessage("Please wait while registering");
                loadingBar.setCanceledOnTouchOutside(false);


                String mail = email.getText().toString();
                String password = pass.getText().toString();
                String confirm=confirmPass.getText().toString();


                if ((!mail.isEmpty()) && (!password.isEmpty())&&(password.equals(confirm))) {

                    loadingBar.show();

                    firebaseAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(task -> {

                        loadingBar.dismiss();


                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user != null) {
                                updateUserData(user, mail);
                                Toast.makeText(requireContext(), "Registration was successful", Toast.LENGTH_SHORT).show();
                                Navigation.findNavController(view).navigate(R.id.home_Fragment);

                            }
                        } else {
                            Exception exception = task.getException();
                            if (exception == null) {
                                Toast.makeText(getContext(), "UnExpected error occurred", Toast.LENGTH_LONG).show();
                            }
                            else if (exception.getClass().equals(FirebaseNetworkException.class)){
                                Toast.makeText(getContext(), "Network error", Toast.LENGTH_LONG).show();

                            }
                            else {
                                if (((FirebaseAuthException) exception).getErrorCode().equals("ERROR_WEAK_PASSWORD")) {

                                    Toast.makeText(getContext(), "Password should be at least 6 characters", Toast.LENGTH_LONG).show();
                                }

                                else {
                                    Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                                }

                            }


                        }

                    });
                } else {
                    if (mail.isEmpty()) {
                        Toast.makeText(getContext(), "Enter your mail", Toast.LENGTH_SHORT).show();
                    } else if (password.isEmpty()) {
                        Toast.makeText(getContext(), "Enter your password", Toast.LENGTH_SHORT).show();
                    }
                    else if(!password.equals(confirm))
                    {
                        Toast.makeText(getContext(), "Password not identical", Toast.LENGTH_LONG).show();

                    }


                }


            }
            private void updateUserData(FirebaseUser currentUser, String name) {
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build();

                currentUser.updateProfile(profileUpdates).addOnCompleteListener(task -> {


                    if (task.isSuccessful()) {

                    } else {
                        Exception exception = task.getException();
                        if (exception == null) {
                            Toast.makeText(getContext(), "UnExpected error occurred", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }


        });
    }

    void connectDesign(View view) {
        email = view.findViewById(R.id.IN_Email);
        pass = view.findViewById(R.id.IN_Password);
        signIn = view.findViewById(R.id.IN_signUP);
        signUp = view.findViewById(R.id.in_signIN_Btn);
        confirmPass = view.findViewById(R.id.up_Confirm_Password);
        loadingBar = new ProgressDialog(requireContext());

    }
}