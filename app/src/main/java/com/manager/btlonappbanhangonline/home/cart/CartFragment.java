package com.manager.btlonappbanhangonline.home.cart;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.core.FirestoreClient;
import com.google.firebase.firestore.remote.FirestoreChannel;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.databinding.FragmentCartBinding;
import com.manager.btlonappbanhangonline.home.HomeActivity;
import com.manager.btlonappbanhangonline.home.cart.adapter.CartAdapter;
import com.manager.btlonappbanhangonline.login.setprofile.SetProfileActivity;
import com.manager.btlonappbanhangonline.model.Cart;
import com.manager.btlonappbanhangonline.model.Delivery;
import com.manager.btlonappbanhangonline.model.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


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
            adapter = new CartAdapter(requireActivity(),carts);
            data = carts;
            binding.cartRecycler.setAdapter(adapter);
            binding.subTotalText.setText("Sub-total: " + String.valueOf(cartViewModel.cost().getValue()) + " VND");
            cost = cartViewModel.cost().getValue();
        });

        binding.orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order();
            }
        });
    }

    private void order() {
        Order order = new Order(data, getCurrentDateTime(), cost);
        Delivery delivery = new Delivery(order, false, "");

        cartViewModel.order(delivery);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    public String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:hh - dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }
}