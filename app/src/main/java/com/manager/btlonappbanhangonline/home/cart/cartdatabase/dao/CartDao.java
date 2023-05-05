package com.manager.btlonappbanhangonline.home.cart.cartdatabase.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.manager.btlonappbanhangonline.model.Cart;

import java.util.List;

@Dao
public interface CartDao {
    @Insert
    void insert(Cart cart);
    @Update
    void update(Cart cart);
    @Delete
    void delete(Cart cart);

    @Query("SELECT * FROM cart_tb")
    LiveData<List<Cart>> selectAllCart();
}
