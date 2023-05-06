package com.manager.btlonappbanhangonline.home.cart;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.databinding.FragmentCartBinding;
import com.manager.btlonappbanhangonline.home.cart.finshorder.FinishOrderActivity;
import com.manager.btlonappbanhangonline.home.cart.adapter.CartAdapter;
import com.manager.btlonappbanhangonline.model.Cart;
import com.manager.btlonappbanhangonline.model.Delivery;
import com.manager.btlonappbanhangonline.model.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CartFragment extends Fragment {
    List<Cart> data;
    Long cost;
    CartAdapter adapter;
    CartViewModel cartViewModel;
    FragmentCartBinding binding;


    String TAG = "error when pushing notification :";

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Lấy được token
                        String token = task.getResult();
                        Log.d(TAG, "Token: " + token);
                    }
                });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.cartRecycler.setLayoutManager(new LinearLayoutManager(requireActivity().getApplicationContext()));
        binding.cartRecycler.setHasFixedSize(true);

        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);

        data = new ArrayList<>();

        cartViewModel.getAllCarts().observe(requireActivity(), carts -> {
            adapter = new CartAdapter(requireActivity(),carts, true);
            data = carts;
            binding.cartRecycler.setAdapter(adapter);
            binding.subTotalText.setText(getResources().getString(R.string.sub_total) + String.valueOf(cartViewModel.cost().getValue()) + " VND");
            cost = cartViewModel.cost().getValue();
        });

        binding.orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    private void order() {
        Order order = new Order(data, getCurrentDateTime(), cost);
        Delivery delivery = new Delivery(order, false, "");
        Intent intent = new Intent(requireActivity(), FinishOrderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        cartViewModel.order(delivery, requireActivity().getApplicationContext(),intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    public String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm - dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }
}