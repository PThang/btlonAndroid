package com.manager.btlonappbanhangonline.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.databinding.ActivityLoginBinding;
import com.manager.btlonappbanhangonline.login.forgetpassword.ForgetPasswordActivity;
import com.manager.btlonappbanhangonline.login.login.LoginContainerFragment;
import com.manager.btlonappbanhangonline.home.HomeActivity;
import com.manager.btlonappbanhangonline.login.setprofile.SetProfileActivity;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth auth;
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        replaceFragment(new LoginContainerFragment(
                new Intent(LoginActivity.this, SetProfileActivity.class),
                new Intent(LoginActivity.this, ForgetPasswordActivity.class),
                new Intent(LoginActivity.this, SetProfileActivity.class)));
    }

    public void replaceFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}