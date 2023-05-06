package com.manager.btlonappbanhangonline.home.main.database;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.manager.btlonappbanhangonline.model.Product;
import com.manager.btlonappbanhangonline.model.TypeProduct;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

@Singleton
public class TypeProRepository {
    FirebaseFirestore db;

    public TypeProRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<List<TypeProduct>> getFbTypePro(){
        MutableLiveData<List<TypeProduct>> allTypes = new MutableLiveData<>();

        db.collection("producttype")
                .addSnapshotListener((value, error) -> {
                    if(error != null){
                        Log.i("error when getting data:", error.toString());
                        return;
                    }
                    List<TypeProduct> types = new ArrayList<>();
                    for(DocumentChange dc : value.getDocumentChanges()){
                        if(dc.getType() == DocumentChange.Type.ADDED){
                            types.add(dc.getDocument().toObject(TypeProduct.class));
                            Log.i("error when getting data:",dc.getDocument().toObject(Product.class).getName());
                        }
                    }
                    allTypes.postValue(types);
                });
        return allTypes;
    }
}
