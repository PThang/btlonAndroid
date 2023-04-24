package com.manager.btlonappbanhangonline.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.model.GioHang;
import com.manager.btlonappbanhangonline.model.NewProduct;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {
    NewProduct newProduct;
    ImageView imageProduct;
    TextView nameProduct, detailProduct;
    CardView cardBack;
    AppCompatButton addCartButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.i("activity: ", "Detail");

        initView();
        initData();
        initControl();
    }

    private void initControl() {
        /*btnthem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                themGioHang();
            }
        });*/
    }
    /*private void themGioHang() {
        if(Utils.manggiohang.size()>0){
            boolean flag=false;
            int soluong= Integer.parseInt(spinner.getSelectedItem().toString());
            for(int i=0;i<Utils.manggiohang.size();i++){
                if(Utils.manggiohang.get(i).getIdsp()==sanPhamMoi.getId()) {
                    Utils.manggiohang.get(i).setSoluong(soluong + Utils.manggiohang.get(i).getSoluong());
                    long gia = Long.parseLong(sanPhamMoi.getPrice())*Utils.manggiohang.get(i).getSoluong();
                    Utils.manggiohang.get(i).setGiasp(gia);
                    flag=true;
                }
            }
            if(flag==false){
                long gia = Long.parseLong(sanPhamMoi.getPrice())*soluong;
                GioHang gioHang= new GioHang();
                gioHang.setGiasp(gia);
                gioHang.setSoluong(soluong);
                gioHang.setIdsp(sanPhamMoi.getId());
                gioHang.setTensp(sanPhamMoi.getName());
                gioHang.setHinhsp(sanPhamMoi.getImg());
                Utils.manggiohang.add(gioHang);
            }
        }else{
            int soluong= Integer.parseInt(spinner.getSelectedItem().toString());
            long gia = Long.parseLong(sanPhamMoi.getPrice())*soluong;
            GioHang gioHang= new GioHang();
            gioHang.setGiasp(gia);
            gioHang.setSoluong(soluong);
            gioHang.setIdsp(sanPhamMoi.getId());
            gioHang.setTensp(sanPhamMoi.getName());
            gioHang.setHinhsp(sanPhamMoi.getImg());
            Utils.manggiohang.add(gioHang);
        }
        int totalItem=0;
        for(int i=0;i<Utils.manggiohang.size();i++){
            totalItem= totalItem+Utils.manggiohang.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalItem));
    }*/

    private void initData() {
        newProduct = (NewProduct) getIntent().getSerializableExtra("chitiet");
        detailProduct.setText(newProduct.getDetail());
        nameProduct.setText(newProduct.getName());
        Glide.with(getApplicationContext()).load(newProduct.getImg()).into(imageProduct);
        addCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Add To Cart New Product:", newProduct.toString());
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

    /*private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        /*if(Utils.manggiohang != null){
            int totalItem=0;
            for(int i=0;i<Utils.manggiohang.size();i++){
                totalItem= totalItem+Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }*/
    }
}