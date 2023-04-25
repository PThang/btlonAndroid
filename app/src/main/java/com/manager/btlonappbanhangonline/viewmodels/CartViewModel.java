package com.manager.btlonappbanhangonline.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.manager.btlonappbanhangonline.database.cartdatabase.repository.CartRepository;
import com.manager.btlonappbanhangonline.model.Cart;

import java.io.Closeable;
import java.util.List;

public class CartViewModel extends AndroidViewModel {
    private CartRepository cartRepository;
    private LiveData<List<Cart>> allCarts;
    //private MutableLiveData<List<Cart>> allCarts;
    private LiveData<Long> cost;

    public CartViewModel(@NonNull Application application) {
        super(application);
        //super();
        cartRepository = new CartRepository(application);
        allCarts = cartRepository.getAllCarts();
    }

    public void insertCart(Cart cart){
        cartRepository.insertCart(cart);
    }

    public void updateCart(Cart cart){
        cartRepository.updateCart(cart);
    }

    public void deleteCart(Cart cart){
        cartRepository.deleteCart(cart);
    }
    public LiveData<List<Cart>> getAllCarts() {
        return allCarts;
    }

    /*public long cost(){
        long cost = 0;
        for(Cart i:allCarts.getValue()){
            cost+= i.getQuantity() * Long.valueOf(cost);
        }
        return cost;
    }*/
}
