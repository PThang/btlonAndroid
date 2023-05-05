package com.manager.btlonappbanhangonline.login.login.login;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.manager.btlonappbanhangonline.databinding.FragmentLoginBinding;
import com.manager.btlonappbanhangonline.home.HomeActivity;
import com.manager.btlonappbanhangonline.eventbus.IMoveClickListener;
import com.manager.btlonappbanhangonline.login.LoginActivity;
import com.manager.btlonappbanhangonline.login.forgetpassword.ForgetPasswordActivity;
import com.manager.btlonappbanhangonline.login.login.LoginContainerFragment;

import java.util.Objects;

public class LoginFragment extends Fragment{
    FragmentLoginBinding binding;
    LoginViewModel loginViewModel;
    private IMoveClickListener moveClickListener;

    public LoginFragment(IMoveClickListener moveClickListener) {
        this.moveClickListener = moveClickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        binding.loginButton.setOnClickListener(v -> login());

        binding.forgetPassWordText.setOnClickListener(v -> {
            moveClickListener.moveTo();
        });

        binding.isShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                binding.passwordText.setInputType(InputType.TYPE_NULL);
            } else {
                binding.passwordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });

        binding.forgetPassWordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetPassword();
            }
        });
    }

    private void forgetPassword() {
        startActivity(new Intent(requireActivity(), ForgetPasswordActivity.class));
        requireActivity().finish();
    }


    void login(){
        String email = binding.emailText.getText().toString();
        String password = binding.passwordText.getText().toString();

        loginViewModel.setLoginResultCallback(new LoginResultCallBack() {
            @Override
            public void onLoginSuccess() {
                requireActivity().finish();
            }
            @Override
            public void onLoginFailure(String message) {
                Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });

        if(email.equalsIgnoreCase("")){
            binding.emailText.setError("");
        }
        if(password.equalsIgnoreCase("")){
            binding.passwordText.setError("");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            loginViewModel.login(email,password);
        }

    }

}