package com.manager.btlonappbanhangonline.home.cart.cartdatabase.repository;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.manager.btlonappbanhangonline.home.cart.CartViewModel;
import com.manager.btlonappbanhangonline.model.Delivery;


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

    public void order(Delivery delivery, Context context, Intent intent) {
        CollectionReference userCollection = db.collection("deliveries").document(currentUser.getEmail()).collection(currentUser.getEmail());
        DocumentReference document = userCollection.document();
        String documentId = document.getId();
        delivery.setId(documentId);
        userCollection.document(documentId).set(delivery)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                        cartViewModel.deleteAll();
                        context.startActivity(intent);
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
