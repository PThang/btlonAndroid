package com.manager.btlonappbanhangonline.home.cart.cartdatabase.repository;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.manager.btlonappbanhangonline.home.cart.CartViewModel;
import com.manager.btlonappbanhangonline.model.Delivery;

import java.util.Random;


public class DeliveryRepository {
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String userEmail;
    CartViewModel cartViewModel;
    Application application;

    public DeliveryRepository(CartViewModel cartViewModel, Application application) {
        this.cartViewModel = cartViewModel;
        this.application = application;

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
        if(!delivery.getOrder().getCarts().isEmpty()){
            userCollection.document(documentId).set(delivery)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            //Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                            cartViewModel.deleteAll();
                            context.startActivity(intent);
                            //sendNotification();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(context, "Empty Cart", Toast.LENGTH_SHORT).show();
        }

    }

}
