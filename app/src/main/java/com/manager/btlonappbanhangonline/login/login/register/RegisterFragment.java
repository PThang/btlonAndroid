package com.manager.btlonappbanhangonline.login.login.register;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.manager.btlonappbanhangonline.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {
    FragmentRegisterBinding binding;
    FirebaseAuth auth;
    ProgressDialog dialog;

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

        dialog = new ProgressDialog(requireActivity());
        dialog.setTitle("Waiting...");

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });


        return binding.getRoot();
    }

    private void register() {
        String email = binding.emailRegisterText.getText().toString();
        String password = binding.passwordRegisterText.getText().toString();
        String rePassword = binding.rePasswordText.getText().toString();

        if(email.equalsIgnoreCase("")){
            binding.emailRegisterText.setError("");
        }
        if(password.equalsIgnoreCase("")){
            binding.passwordRegisterText.setError("");
        }
        if(rePassword.equalsIgnoreCase("")){
            binding.rePasswordText.setError("");
        }
        if(!email.equalsIgnoreCase("")
            && !password.equalsIgnoreCase("")
            && !rePassword.equalsIgnoreCase("")
            && password.equalsIgnoreCase(rePassword))   {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = auth.getCurrentUser();
                                Log.i("Register State:", "Success");
                                Toast.makeText(requireActivity(), "Success", Toast.LENGTH_SHORT).show();
                                //dialog.dismiss();
                            } else {
                                Log.i("Register State:", "Failed");
                                Toast.makeText(requireActivity(), "Fail", Toast.LENGTH_SHORT).show();
                                //dialog.dismiss();
                            }
                        }
                    });
        }
        //dialog.show();
    }
}