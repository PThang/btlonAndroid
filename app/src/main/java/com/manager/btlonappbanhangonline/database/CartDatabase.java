package com.manager.btlonappbanhangonline.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.manager.btlonappbanhangonline.database.dao.CartDao;
import com.manager.btlonappbanhangonline.model.Cart;

@Database(entities = {Cart.class}, version = 1)
public abstract class CartDatabase extends RoomDatabase {
    public abstract CartDao getCartDao();
}
