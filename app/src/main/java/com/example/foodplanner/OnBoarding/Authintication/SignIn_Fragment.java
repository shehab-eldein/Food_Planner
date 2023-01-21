package com.example.foodplanner.OnBoarding.Authintication;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.example.foodplanner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;


public class SignIn_Fragment extends Fragment {
    TextView email,password,signUP;
    Button signIn;
    private ProgressDialog loadingBar;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private View v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in_, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signUP = view.findViewById(R.id.IN_signUP);
        signIn = view.findViewById(R.id.in_signIN_Btn);
        email = view.findViewById(R.id.IN_Email);

        password = view.findViewById(R.id.IN_Password);
        signInBtnClicked();

    }
    void signInBtnClicked () {
        loadingBar = new ProgressDialog(requireContext());

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingBar.setTitle("Signing in");
                loadingBar.setMessage("Please wait while signing in");
                loadingBar.setCanceledOnTouchOutside(false);


                String mail = email.getText().toString();
                String pass = password.getText().toString();


                if ((!mail.isEmpty()) && (!pass.isEmpty())) {

                    loadingBar.show();

                    firebaseAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            loadingBar.dismiss();

                            if (task.isSuccessful()) {
                                Log.i("tt", "onActivityResult: " + firebaseAuth.getCurrentUser().getEmail());

                                Toast.makeText(requireContext(), "login successful", Toast.LENGTH_SHORT).show();

//                                Intent intent = new Intent(requireContext(), MainActivity.class);
//                                startActivity(intent);
                                Navigation.findNavController(view).navigate(R.id.home_Fragment);
                            } else {
                                Exception exception = task.getException();
                                if (exception == null) {
                                    Toast.makeText(getContext(), "UnExpected error occurred", Toast.LENGTH_LONG).show();
                                } else {
                                    if (exception.getClass().equals(FirebaseAuthException.class)) {
                                        if (((FirebaseAuthException) exception).getErrorCode().equals("ERROR_USER_NOT_FOUND")) {
                                            Toast.makeText(getContext(), "User not found", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    } else if (exception.getClass().equals(FirebaseNetworkException.class)) {
                                        Toast.makeText(getContext(), "Network error", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                }

                            }
                        }
                    });
                } else {
                    if (mail.isEmpty()) {
                        Toast.makeText(getContext(), "Enter your mail", Toast.LENGTH_SHORT).show();
                    } else if (pass.isEmpty()) {
                        Toast.makeText(getContext(), "Enter your pass", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // button to skip to register fragment
        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.signUp_Fragment2);

            }
        });
    }
}