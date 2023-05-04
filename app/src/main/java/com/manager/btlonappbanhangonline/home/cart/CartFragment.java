package com.manager.btlonappbanhangonline.home.cart;

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

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
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
import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.databinding.FragmentCartBinding;
import com.manager.btlonappbanhangonline.home.HomeActivity;
import com.manager.btlonappbanhangonline.home.cart.adapter.CartAdapter;
import com.manager.btlonappbanhangonline.model.Cart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CartFragment extends Fragment {
    List<Cart> data;
    Long cost;
    CartAdapter adapter;
    CartViewModel cartViewModel;
    FragmentCartBinding binding;

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String userEmail;


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

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        userEmail = currentUser.getEmail();

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
        CollectionReference userCollection = db.collection("carts").document(userEmail).collection("cartList").document().collection("detail");
        Map<String, List<Cart>> products = new HashMap<>();
        products.put("products", data);
        userCollection.document("products").set(products);
        Map<String, String> cost = new HashMap<>();
        cost.put("cost", String.valueOf(cartViewModel.cost().getValue()));
        userCollection.document("cost").set(cost);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}