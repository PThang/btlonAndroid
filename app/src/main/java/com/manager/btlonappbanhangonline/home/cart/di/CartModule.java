package com.manager.btlonappbanhangonline.home.cart.di;

import android.app.Application;

import com.manager.btlonappbanhangonline.home.cart.cartdatabase.CartDatabase;
import com.manager.btlonappbanhangonline.home.cart.cartdatabase.repository.dao.CartDao;

import dagger.Module;
import dagger.Provides;

@Module
public class CartModule {
    @Provides
    CartDatabase provideCartDatabase(Application application){
        return CartDatabase.getInstance(application);
    }
    @Provides
    CartDao provideCartDao(CartDatabase cartDatabase){
        return cartDatabase.cartDao();
    }
}
