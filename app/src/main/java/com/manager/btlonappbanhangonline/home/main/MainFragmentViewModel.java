package com.manager.btlonappbanhangonline.home.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.manager.btlonappbanhangonline.home.main.database.ProductRepository;
import com.manager.btlonappbanhangonline.home.main.database.TypeProRepository;
import com.manager.btlonappbanhangonline.model.Product;
import com.manager.btlonappbanhangonline.model.TypeProduct;

import java.util.List;

public class MainFragmentViewModel extends AndroidViewModel {
    private ProductRepository productRepository;
    private MutableLiveData<List<Product>> allProducts;
    private TypeProRepository typeProRepository;
    private MutableLiveData<List<TypeProduct>> allTypes;
    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository();
        allProducts = productRepository.getFirebaseProducts();
        typeProRepository = new TypeProRepository();
        allTypes = typeProRepository.getFbTypePro();
    }

    public LiveData<List<Product>> getAllProducts(){
        return allProducts;
    }
    public LiveData<List<TypeProduct>> getAllTypes(){
        return allTypes;
    }

    public LiveData<List<Product>> getProductByType(String id){
        return productRepository.getProductByType(id);
    }
    public LiveData<List<Product>> searchFirestore(String query){
        return productRepository.searchFirestore(query);
    }
}
