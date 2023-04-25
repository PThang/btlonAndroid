package com.manager.btlonappbanhangonline.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.model.Cart;
import com.manager.btlonappbanhangonline.model.NewProduct;
import com.manager.btlonappbanhangonline.viewmodels.CartViewModel;

public class DetailActivity extends AppCompatActivity {
    NewProduct newProduct;
    ImageView imageProduct;
    TextView nameProduct, detailProduct;
    CardView cardBack;
    AppCompatButton addCartButton;

    CartViewModel cartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.i("activity: ", "Detail");

        cartViewModel = ViewModelProviders.of(DetailActivity.this).get(CartViewModel.class);

        initView();
        initData();
        initControl();
    }

    private void initControl() {
    }

    private void initData() {
        newProduct = (NewProduct) getIntent().getSerializableExtra("chitiet");
        detailProduct.setText(newProduct.getDetail());
        nameProduct.setText(newProduct.getName());
        Glide.with(getApplicationContext()).load(newProduct.getImg()).into(imageProduct);
        addCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cart = new Cart(newProduct.getId(), newProduct.getName(), newProduct.getPrice(), newProduct.getImg(), 1);
                cartViewModel.insertCart(cart);
                Toast.makeText(DetailActivity.this, "Successful", Toast.LENGTH_SHORT).show();
            }
        });

        cardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        imageProduct = findViewById(R.id.productImage);
        detailProduct = findViewById(R.id.detailText);
        nameProduct = findViewById(R.id.nameProductText);
        cardBack = findViewById(R.id.backCard);
        addCartButton = findViewById(R.id.addCartButton);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}