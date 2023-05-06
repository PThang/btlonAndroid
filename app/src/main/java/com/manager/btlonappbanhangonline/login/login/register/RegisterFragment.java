package com.manager.btlonappbanhangonline.login.login.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.manager.btlonappbanhangonline.databinding.FragmentRegisterBinding;

import java.util.regex.Pattern;

public class RegisterFragment extends Fragment {
    FragmentRegisterBinding binding;
    RegisterViewModel registerViewModel;
    ProgressDialog dialog;
    Intent intent;

    public RegisterFragment(Intent intent) {
        this.intent = intent;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(getLayoutInflater());

        registerViewModel = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);

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

        if(!validateEmail(email)){
            Toast.makeText(requireActivity(), "Email is not correct.", Toast.LENGTH_SHORT).show();
        }

        if(email.equalsIgnoreCase("")){
            binding.emailRegisterText.setError("");
        }
        if(password.equalsIgnoreCase("")){
            binding.passwordRegisterText.setError("");
        }
        if(!rePassword.equalsIgnoreCase(password)){
            Toast.makeText(requireActivity(), "Repeat password is not correct.", Toast.LENGTH_SHORT).show();
        }
        if(!email.equalsIgnoreCase("")
            && !password.equalsIgnoreCase("")
            && !rePassword.equalsIgnoreCase("")
            && password.equalsIgnoreCase(rePassword)
            && validateEmail(email))   {
            registerViewModel.register(email, password);
        }
    }

    Boolean validateEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pattern.matcher(email).matches();
    }
}