package com.manager.btlonappbanhangonline.viewmodels;

import android.app.Application;
import androidx.lifecycle.ViewModel;
import com.manager.btlonappbanhangonline.database.repository.CartRepository;
import com.manager.btlonappbanhangonline.model.Cart;
import java.util.List;

public class CartViewModel extends ViewModel {
    Application application;
    CartRepository cartRepository;

    public CartViewModel(Application application) {
        this.application = application;
        cartRepository = new CartRepository(application);
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

    public List<Cart> getAllCart(){
        return cartRepository.getAllCart();
    }

}
