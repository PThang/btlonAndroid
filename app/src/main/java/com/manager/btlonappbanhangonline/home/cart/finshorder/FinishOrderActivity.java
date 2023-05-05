package com.manager.btlonappbanhangonline.home.cart.finshorder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.manager.btlonappbanhangonline.databinding.ActivityFinishOrderBinding;

public class FinishOrderActivity extends AppCompatActivity {
    ActivityFinishOrderBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFinishOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initEvents();
    }

    private void initEvents() {
        binding.skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}