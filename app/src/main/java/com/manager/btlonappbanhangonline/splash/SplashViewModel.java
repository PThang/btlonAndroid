package com.manager.btlonappbanhangonline.splash;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.manager.btlonappbanhangonline.model.User;

public class SplashViewModel extends AndroidViewModel {
    FirebaseAuth auth;
    FirebaseFirestore db;
    LiveData<FirebaseUser> currentUser;
    LiveData<User> user;
    LiveData<Boolean> isChecked;
    public SplashViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
        currentUser = getUser();
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = getUserLiveData();
        isChecked = checkData();
    }

    public LiveData<FirebaseUser> getUser(){
        MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
        user.setValue(auth.getCurrentUser());
        return user;
    }

    public LiveData<User> getUserLiveData(){
        MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
        if(currentUser.getValue() != null){
            db.collection("users")
                    .document(currentUser.getValue().getEmail())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                // Chuyển đổi DocumentSnapshot thành object User
                                User userAc = documentSnapshot.toObject(User.class);
                                Log.i("Firebase's user :", userAc.getName());
                                userMutableLiveData.setValue(userAc);
                            } else {
                            }
                            //return null;
                        }
                    });
        }
        return userMutableLiveData;
    }
    LiveData<Boolean> checkData(){
        MutableLiveData<Boolean> checked = new MutableLiveData<>();
        if(currentUser.getValue() != null){
            if (!currentUser.getValue().getDisplayName().equalsIgnoreCase("")
                    && !currentUser.getValue().getEmail().equalsIgnoreCase("")) {
                if(user.getValue() != null) {
                    if(!user.getValue().getName().equalsIgnoreCase("")
                            && !user.getValue().getAddress().equalsIgnoreCase("")
                            && !user.getValue().getPhoneNumber().equalsIgnoreCase("")) {
                        checked.setValue(true);
                    } else {
                        checked.setValue(false);
                    }
                } else {
                    checked.setValue(false);
                }
            } else {
                checked.setValue(false);
            }
        }
        return checked;
    }
}
