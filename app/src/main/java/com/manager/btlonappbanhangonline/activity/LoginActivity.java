package com.manager.btlonappbanhangonline.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.adapter.ViewPagerAdapter;
import com.manager.btlonappbanhangonline.databinding.ActivityLaptopBinding;
import com.manager.btlonappbanhangonline.databinding.ActivityLoginBinding;
import com.manager.btlonappbanhangonline.fragments.LoginFragment;
import com.manager.btlonappbanhangonline.fragments.RegisterFragment;

import io.paperdb.Paper;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    ViewPagerAdapter adapter;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        auth = FirebaseAuth.getInstance();

        adapter.addFragment(new RegisterFragment(), "Create Account");
        adapter.addFragment(new LoginFragment(), "Login");

        binding.viewPager.setAdapter(adapter);
        binding.tabs.setupWithViewPager(binding.viewPager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            //reload();
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        }
    }
}