package com.manager.btlonappbanhangonline.home.profile;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.manager.btlonappbanhangonline.model.User;

public class ProfileViewModel extends AndroidViewModel {
    FirebaseAuth auth;
    FirebaseFirestore db;
    LiveData<User> userLiveData;
    LiveData<FirebaseUser> user;

    public ProfileViewModel(@NonNull Application application) {
        super(application);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        user = getUser();
        userLiveData = getUserLiveData();
    }

    public LiveData<FirebaseUser> getUser() {
        MutableLiveData<FirebaseUser> userMutableLiveData = new MutableLiveData<>();
        userMutableLiveData.setValue(auth.getCurrentUser());
        return userMutableLiveData;
    }

    public LiveData<User> getUserLiveData(){
        MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
        db.collection("users")
                .document(user.getValue().getEmail())
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
                    }
                });
        return userMutableLiveData;
    }
}
