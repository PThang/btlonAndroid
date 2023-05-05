package com.manager.btlonappbanhangonline.splash;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashViewModel extends AndroidViewModel {
    FirebaseAuth auth;
    LiveData<FirebaseUser> currentUser;
    public SplashViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
        currentUser = getUser();
    }

    public LiveData<FirebaseUser> getUser(){
        MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
        user.setValue(auth.getCurrentUser());
        return user;
    }
}
