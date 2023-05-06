package com.manager.btlonappbanhangonline.login.setprofile;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.manager.btlonappbanhangonline.home.HomeActivity;
import com.manager.btlonappbanhangonline.model.User;

public class ProfileViewModel extends AndroidViewModel {
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    LiveData<FirebaseUser> user;
    FirebaseStorage storage;
    String srtUrl;
    LiveData<User> userAc;
    public ProfileViewModel(@NonNull Application application) {
        super(application);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        user = getUser();
        userAc = getUserInfo();
    }

    public LiveData<FirebaseUser> getUser(){
        MutableLiveData<FirebaseUser> userMutableLiveData = new MutableLiveData<>();
        userMutableLiveData.setValue(mAuth.getCurrentUser());
        return userMutableLiveData;
    }

    private LiveData<User> getUserInfo(){
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
//                            binding.inputAddress.setText(userAc.getAddress());
//                            binding.inputPhoneNumber.setText(userAc.getPhoneNumber());
                            userMutableLiveData.postValue(userAc);
                        } else {
                        }
                    }
                });
        return userMutableLiveData;
    }

    public void saveInfo(Uri uri, String name, String email, String address, String phoneNumber){
        UserProfileChangeRequest profileUpdates;

        if (uri != null){
            profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .setPhotoUri(uri)
                    .build();
        } else {
            profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build();
        }

        insertImageOnFS(uri);

        String photoUrl = srtUrl;

        User u = new User(name, email, phoneNumber, photoUrl, address);
        user.getValue().updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        CollectionReference usersRef = db.collection("users");
                        usersRef.document(email)
                                .set(u)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Intent intent = new Intent(getApplication(), HomeActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        getApplication().startActivity(intent);
                                    }
                                });
                    }
                });
    }

    private void insertImageOnFS(Uri uri) {
        if (uri == null) return;

        StorageReference storageReference = storage.getReference("Images").child(String.valueOf(System.currentTimeMillis()));
        UploadTask uploadTask = storageReference.putFile(uri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> downloadUrlTask = taskSnapshot.getStorage().getDownloadUrl();
                downloadUrlTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        srtUrl = uri.toString();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        srtUrl = null;
                    }
                });
            }
        });
    }
}
