package com.manager.btlonappbanhangonline.database.firebase.productdatabase.repository;


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

    public LiveData<List<NewProduct>> getFirebaseProducts(){
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
                    /*NewProductAdapter adapter = new NewProductAdapter(requireActivity().getApplicationContext(), data);
                    productRecycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();*/
                });
        products.setValue(data);
        return products;
    }
}