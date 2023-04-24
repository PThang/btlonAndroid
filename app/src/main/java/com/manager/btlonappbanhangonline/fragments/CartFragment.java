package com.manager.btlonappbanhangonline.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.adapter.CartAdapter;
import com.manager.btlonappbanhangonline.model.Cart;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment {
    List<Cart> data;
    CartAdapter adapter;
    RecyclerView cartRecycler;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try{
            data = new ArrayList<>();
            cartRecycler = view.findViewById(R.id.cartRecycler);
            cartRecycler.setLayoutManager(new LinearLayoutManager(requireActivity().getApplicationContext()));
            cartRecycler.setHasFixedSize(true);
            fakeData();
        } catch (Exception e){
            Log.i("Error when setting adapter: ", e.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    void fakeData(){
        try{
            data.add(new Cart("1", "Samsung Galaxy S22 Ultra", "17490000", "https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/s/m/sm-s908_galaxys22ultra_front_burgundy_211119.jpg", 5));
            data.add(new Cart("2", "Samsung Galaxy S22 Ultra", "17490000", "https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/s/m/sm-s908_galaxys22ultra_front_burgundy_211119.jpg", 6));
            data.add(new Cart("3", "Samsung Galaxy S22 Ultra", "17490000", "https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/s/m/sm-s908_galaxys22ultra_front_burgundy_211119.jpg", 7));

            adapter = new CartAdapter(requireActivity(), data);
            cartRecycler.setAdapter(adapter);
        } catch (Exception e){
            Log.i("Error when setting adapter: ", e.toString());
        }
    }
}