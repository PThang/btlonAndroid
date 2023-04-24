package com.manager.btlonappbanhangonline.database.cartdatabase.repository;

import android.app.Application;

import androidx.room.Room;


import com.manager.btlonappbanhangonline.database.cartdatabase.CartDatabase;
import com.manager.btlonappbanhangonline.database.cartdatabase.dao.CartDao;
import com.manager.btlonappbanhangonline.model.Cart;

import java.util.List;

public class CartRepository {
    CartDatabase db;
    Application application;
    CartDao cartDao;

    public CartRepository(Application application) {
        this.db = Room.databaseBuilder(this.application, CartDatabase.class, "cart_db").build();
        cartDao = db.getCartDao();
    }

    public void insertCart(Cart cart){
        cartDao.insertCart(cart);
    }
    public void updateCart(Cart cart){
        cartDao.updateCart(cart);
    }
    public void deleteCart(Cart cart){
        cartDao.deleteCart(cart);
    }
    public List<Cart> getAllCart(){
        return cartDao.selectAllCart();
    }
}
