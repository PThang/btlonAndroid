package com.manager.btlonappbanhangonline.home.main.database;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.manager.btlonappbanhangonline.model.NewProduct;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

@Singleton
public class ProductRepository {
    FirebaseFirestore db;

    public ProductRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<List<NewProduct>> getFirebaseProducts(){
        MutableLiveData<List<NewProduct>> products = new MutableLiveData<>();
        List<NewProduct> data = new ArrayList<>();

        db.collection("items")
                .addSnapshotListener((value, error) -> {
                    if(error != null){
                        Log.i("error when getting data:", error.toString());
                        return;
                    }
                    for(DocumentChange dc : value.getDocumentChanges()){
                        if(dc.getType() == DocumentChange.Type.ADDED){
                            data.add(dc.getDocument().toObject(NewProduct.class));
                            Log.i("error when getting data:",dc.getDocument().toObject(NewProduct.class).getName());
                        }
                    }
                    products.postValue(data);
                });
        return products;
    }

    public LiveData<List<NewProduct>> getProductByType(String id){
        MutableLiveData<List<NewProduct>> products = new MutableLiveData<>();
        List<NewProduct> data = new ArrayList<>();

        db.collection("items")
                .whereEqualTo("type",id)
                .addSnapshotListener((value, error) -> {
                    if(error != null){
                        Log.i("error when getting data:", error.toString());
                        return;
                    }
                    for(DocumentChange dc : value.getDocumentChanges()){
                        if(dc.getType() == DocumentChange.Type.ADDED){
                            data.add(dc.getDocument().toObject(NewProduct.class));
                            Log.i("error when getting data:",dc.getDocument().toObject(NewProduct.class).getName());
                        }
                    }
                    products.postValue(data);
                });
        return products;
    }
    public LiveData<List<NewProduct>> searchFirestore(String query) {
        MutableLiveData<List<NewProduct>> products = new MutableLiveData<>();
        // Tạo truy vấn để tìm kiếm tài khoản người dùng có tên hoặc email chứa query
        Query data =  db.collection("items").whereGreaterThanOrEqualTo("name", query)
                .whereLessThanOrEqualTo("name", query + "\uf8ff");

        data.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<NewProduct> productData = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        NewProduct product = document.toObject(NewProduct.class);
                        productData.add(product);
                    }

                    products.postValue(productData);
                } else {
                    Log.i("Error getting documents: ", task.getException().toString());
                }
            }
        });
        return products;
    }

}
