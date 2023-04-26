package com.manager.btlonappbanhangonline.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.manager.btlonappbanhangonline.database.firebase.productdatabase.repository.ProductRepository;
import com.manager.btlonappbanhangonline.model.NewProduct;

import java.util.List;

public class MainFragmentViewModel extends AndroidViewModel {
    private ProductRepository productRepository;
    private LiveData<List<NewProduct>> allProducts;
    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository();
        allProducts = productRepository.getFirebaseProducts();
    }

    public LiveData<List<NewProduct>> getAllProducts(){
        return allProducts;
    }
}
