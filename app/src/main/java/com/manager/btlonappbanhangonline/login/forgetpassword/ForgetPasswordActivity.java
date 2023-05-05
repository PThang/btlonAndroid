package com.manager.btlonappbanhangonline.login.forgetpassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.manager.btlonappbanhangonline.databinding.ActivityForgetPasswordBinding;
import com.manager.btlonappbanhangonline.home.HomeActivity;
import com.manager.btlonappbanhangonline.login.LoginActivity;

public class ForgetPasswordActivity extends AppCompatActivity {
    ActivityForgetPasswordBinding binding;
    ForgetPassWordViewModel forgetPassWordViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        forgetPassWordViewModel = new ViewModelProvider(ForgetPasswordActivity.this).get(ForgetPassWordViewModel.class);

        initEvents();
    }

    private void initEvents() {
        binding.sendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

        binding.backCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
            }
        });
    }

    private void resetPassword() {
        String emailAddress = binding.inputEmail.getText().toString();

        forgetPassWordViewModel.resetPassword(emailAddress);
    }
}