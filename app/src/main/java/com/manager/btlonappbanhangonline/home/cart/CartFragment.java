package com.manager.btlonappbanhangonline.home.cart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.home.HomeActivity;
import com.manager.btlonappbanhangonline.home.cart.adapter.CartAdapter;
import com.manager.btlonappbanhangonline.model.Cart;

import java.util.ArrayList;
import java.util.List;



public class CartFragment extends Fragment {
    List<Cart> data;
    CartAdapter adapter;
    RecyclerView cartRecycler;
    TextView subTotalText;
    CartViewModel cartViewModel;


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);

        cartRecycler = view.findViewById(R.id.cartRecycler);
        subTotalText = view.findViewById(R.id.subTotalText);
        cartRecycler.setLayoutManager(new LinearLayoutManager(requireActivity().getApplicationContext()));
        cartRecycler.setHasFixedSize(true);

        data = new ArrayList<>();

        cartViewModel.getAllCarts().observe((HomeActivity) requireActivity(), carts -> {
            adapter = new CartAdapter((HomeActivity) requireActivity(),carts);
            cartRecycler.setAdapter(adapter);
            subTotalText.setText("Sub-total: " + String.valueOf(cartViewModel.cost().getValue()) + " VND");
        });

        return view;
    }
}