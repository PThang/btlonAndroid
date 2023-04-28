package com.manager.btlonappbanhangonline.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.manager.btlonappbanhangonline.database.firebase.productdatabase.repository.ProductRepository;
import com.manager.btlonappbanhangonline.database.firebase.typeproductdatabase.TypeProRepository;
import com.manager.btlonappbanhangonline.model.NewProduct;
import com.manager.btlonappbanhangonline.model.TypeProduct;

import java.util.List;

public class MainFragmentViewModel extends AndroidViewModel {
    private ProductRepository productRepository;
    private MutableLiveData<List<NewProduct>> allProducts;
    private TypeProRepository typeProRepository;
    private MutableLiveData<List<TypeProduct>> allTypes;
    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository();
        allProducts = productRepository.getFirebaseProducts();
        typeProRepository = new TypeProRepository();
        allTypes = typeProRepository.getFbTypePro();
    }

    public LiveData<List<NewProduct>> getAllProducts(){
        return allProducts;
    }
    public LiveData<List<TypeProduct>> getAllTypes(){
        return allTypes;
    }
    public LiveData<List<NewProduct>> getProductByType(String id){
        return productRepository.getProductByType(id);
    }
}
