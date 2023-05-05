package com.manager.btlonappbanhangonline.home.cart;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.manager.btlonappbanhangonline.home.cart.cartdatabase.repository.CartRepository;
import com.manager.btlonappbanhangonline.home.cart.cartdatabase.repository.DeliveryRepository;
import com.manager.btlonappbanhangonline.model.Cart;
import com.manager.btlonappbanhangonline.model.Delivery;
import com.manager.btlonappbanhangonline.model.Order;

import java.util.List;

public class CartViewModel extends AndroidViewModel {
    private CartRepository cartRepository;
    private DeliveryRepository deliveryRepository;
    private LiveData<List<Cart>> allCarts;
    private LiveData<Long> cost;

    public CartViewModel(@NonNull Application application) {
        super(application);
        cartRepository = new CartRepository(application);
        allCarts = cartRepository.getAllCarts();
        deliveryRepository = new DeliveryRepository(CartViewModel.this);
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

    public void order(Delivery delivery, Context context,Intent intent){
        if(delivery != null){
            deliveryRepository.order(delivery,context, intent);
        }
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
