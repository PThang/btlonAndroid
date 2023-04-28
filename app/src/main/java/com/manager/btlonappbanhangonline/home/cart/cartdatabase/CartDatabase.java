package com.manager.btlonappbanhangonline.home.cart.cartdatabase;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.manager.btlonappbanhangonline.home.cart.cartdatabase.dao.CartDao;
import com.manager.btlonappbanhangonline.model.Cart;

import javax.inject.Singleton;

@Database(entities = {Cart.class}, version = 2)
public abstract class CartDatabase extends RoomDatabase {
    public static CartDatabase INSTANCE;
    public abstract CartDao cartDao();

    @Singleton
    public static synchronized CartDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CartDatabase.class, "cart_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }


    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
