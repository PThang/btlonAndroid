package com.manager.btlonappbanhangonline.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.databinding.ActivityHomeBinding;
import com.manager.btlonappbanhangonline.fragments.CartFragment;
import com.manager.btlonappbanhangonline.fragments.MainFragment;
import com.manager.btlonappbanhangonline.fragments.ProfileFragment;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private GoogleSignInClient client;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        replaceFragment(new MainFragment());
        initEvent();
    }

    private void initEvent(){
        binding.homeContainer.setOnClickListener(v -> {
            binding.homeContainer.setBackgroundResource(R.drawable.bg_menu);
            binding.homeText.setVisibility(View.VISIBLE);
            replaceFragment(new MainFragment());

            binding.profileContainer.setBackgroundResource(R.drawable.menu_not_select);
            binding.profileText.setVisibility(View.GONE);

            binding.cartContainer.setBackgroundResource(R.drawable.menu_not_select);
            binding.cartText.setVisibility(View.GONE);
        });

        binding.profileContainer.setOnClickListener(v -> {
            binding.profileContainer.setBackgroundResource(R.drawable.bg_menu);
            binding.profileText.setVisibility(View.VISIBLE);
            replaceFragment(new ProfileFragment());

            binding.homeContainer.setBackgroundResource(R.drawable.menu_not_select);
            binding.homeText.setVisibility(View.GONE);

            binding.cartContainer.setBackgroundResource(R.drawable.menu_not_select);
            binding.cartText.setVisibility(View.GONE);
        });

        binding.cartContainer.setOnClickListener(v -> {
            binding.cartContainer.setBackgroundResource(R.drawable.bg_menu);
            binding.cartText.setVisibility(View.VISIBLE);
            replaceFragment(new CartFragment());

            binding.profileContainer.setBackgroundResource(R.drawable.menu_not_select);
            binding.profileText.setVisibility(View.GONE);

            binding.homeContainer.setBackgroundResource(R.drawable.menu_not_select);
            binding.homeText.setVisibility(View.GONE);
        });
    }

    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFragmentContainer, fragment)
                .commit();
    }
}