package com.manager.btlonappbanhangonline.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {
    FragmentRegisterBinding binding;
    FirebaseAuth auth;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(getLayoutInflater());
        auth = FirebaseAuth.getInstance();

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpWithGG();
            }
        });

        return binding.getRoot();
    }

    private void signUpWithGG() {

    }

    private void register() {
        String email = binding.emailRegisterText.getText().toString();
        String password = binding.passwordRegisterText.getText().toString();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            Log.i("Register State:", "Success");
                        } else {
                            Log.i("Register State:", "Failed");
                        }
                    }
                });
    }
}