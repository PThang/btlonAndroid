package com.manager.btlonappbanhangonline.home.cart;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.manager.btlonappbanhangonline.home.cart.cartdatabase.repository.CartRepository;
import com.manager.btlonappbanhangonline.model.Cart;

import java.util.List;

public class CartViewModel extends AndroidViewModel {
    private CartRepository cartRepository;
    private LiveData<List<Cart>> allCarts;
    private LiveData<Long> cost;

    public CartViewModel(@NonNull Application application) {
        super(application);
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

    public void deleteAll(){
        cartRepository.deleteAll();
    }

    public LiveData<Long> cost(){
        MutableLiveData<Long> cost = new MutableLiveData<Long>();
        cost.setValue(0L);
        for(Cart i:allCarts.getValue()){
            cost.setValue(cost.getValue() + i.getQuantity() * Long.valueOf(i.getCostProduct()));
        }
        return cost;
    }
}
