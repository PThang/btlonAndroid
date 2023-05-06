package com.manager.btlonappbanhangonline.home.delivering.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.databinding.ActivityDetailBinding;
import com.manager.btlonappbanhangonline.databinding.ActivityDetailOrderBinding;
import com.manager.btlonappbanhangonline.home.cart.adapter.CartAdapter;
import com.manager.btlonappbanhangonline.model.Order;

public class DetailOrderActivity extends AppCompatActivity {
    ActivityDetailOrderBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initData();

        binding.backCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        Order order = (Order) getIntent().getSerializableExtra("order");

        binding.cartRecycler.setLayoutManager(new LinearLayoutManager(DetailOrderActivity.this));
        binding.cartRecycler.setHasFixedSize(true);
        CartAdapter adapter = new CartAdapter(DetailOrderActivity.this, order.getCarts(), false);
        binding.cartRecycler.setAdapter(adapter);
    }
}