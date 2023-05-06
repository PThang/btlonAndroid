package com.manager.btlonappbanhangonline.home.main.detailproduct;

import android.content.Intent;
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
import com.manager.btlonappbanhangonline.home.HomeActivity;
import com.manager.btlonappbanhangonline.model.Cart;
import com.manager.btlonappbanhangonline.model.Product;
import com.manager.btlonappbanhangonline.home.cart.CartViewModel;

public class DetailActivity extends AppCompatActivity {
    Product newProduct;
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
        newProduct = (Product) getIntent().getSerializableExtra("detail");
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
                startActivity(new Intent(DetailActivity.this, HomeActivity.class));
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DetailActivity.this, HomeActivity.class));
    }
}