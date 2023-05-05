package com.manager.btlonappbanhangonline.home.cart.cartdatabase.repository;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.manager.btlonappbanhangonline.home.cart.CartViewModel;
import com.manager.btlonappbanhangonline.model.Delivery;

import java.text.SimpleDateFormat;


public class DeliveryRepository {
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String userEmail;
    CartViewModel cartViewModel;

    public DeliveryRepository(CartViewModel cartViewModel) {
        this.cartViewModel = cartViewModel;

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        userEmail = currentUser.getEmail();
    }

    public void order(Delivery delivery) {
        CollectionReference userCollection = db.collection("deliveries").document(currentUser.getEmail()).collection(currentUser.getEmail());

        userCollection.document().set(delivery)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                        cartViewModel.deleteAll();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
