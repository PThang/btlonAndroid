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
import com.manager.btlonappbanhangonline.login.login.ResultCallBack;

import java.util.regex.Pattern;

public class RegisterFragment extends Fragment {
    FragmentRegisterBinding binding;
    RegisterViewModel registerViewModel;
    private ProgressDialog progressDialog;
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

        progressDialog = new ProgressDialog(requireActivity());
        progressDialog.setMessage("Waiting...");
        progressDialog.setCancelable(false);

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
        progressDialog.show();
        String email = binding.emailRegisterText.getText().toString().trim();
        String password = binding.passwordRegisterText.getText().toString();
        String rePassword = binding.rePasswordText.getText().toString();

        registerViewModel.setRegisterResultCallback(new ResultCallBack() {
            @Override
            public void onLoginSuccess() {
                requireActivity().finish();
                progressDialog.dismiss();
            }

            @Override
            public void onLoginFailure(String message) {
                Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

        if(!validateEmail(email)){
            Toast.makeText(requireActivity(), "Email is not correct.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }

        if(email.equalsIgnoreCase("")){
            binding.emailRegisterText.setError("");
            progressDialog.dismiss();
        }
        if(password.equalsIgnoreCase("")){
            binding.passwordRegisterText.setError("");
            progressDialog.dismiss();
        }
        if(!rePassword.equalsIgnoreCase(password)){
            Toast.makeText(requireActivity(), "Repeat password is not correct.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
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