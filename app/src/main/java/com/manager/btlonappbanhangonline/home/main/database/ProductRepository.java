package com.manager.btlonappbanhangonline.home.main.database;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
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
}
