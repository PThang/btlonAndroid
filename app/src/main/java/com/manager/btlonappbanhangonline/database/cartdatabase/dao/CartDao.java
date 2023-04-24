package com.manager.btlonappbanhangonline.database.cartdatabase.dao;

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
    void insertCart(Cart cart);
    @Update
    void updateCart(Cart cart);
    @Delete
    void deleteCart(Cart cart);
    @Query("SELECT * FROM cart_tb")
    List<Cart> selectAllCart();
}
