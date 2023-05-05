package com.manager.btlonappbanhangonline.login.setprofile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.databinding.ActivitySetProfileBinding;
import com.manager.btlonappbanhangonline.home.HomeActivity;
import com.manager.btlonappbanhangonline.model.Cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetProfileActivity extends AppCompatActivity {
    private ActivitySetProfileBinding binding;
    Uri uri = null;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseStorage storage;
    String strUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        initEvents();
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        if(getIntent().getStringExtra("result") != null){
            setData();
        }
    }

    private void setData() {
        Glide.with(SetProfileActivity.this).load(user.getPhotoUrl()).into(binding.profileImageView);
        binding.inputName.setText(user.getDisplayName());
        //binding.inputName.setText(db.collection("infoUsers").);
        binding.inputEmail.setText(user.getEmail());
    }

    private void initEvents() {
        binding.updateImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputImage();
            }
        });
        
        binding.moveToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInfo();
            }
        });
    }

    private void saveInfo() {
        String name = binding.inputName.getText().toString();
        String email = binding.inputEmail.getText().toString();
        String phoneNumber = binding.inputPhoneNumber.getText().toString();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(uri)
                .build();

        insertImageOnFS();
        String photoUrl = strUrl;

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        CollectionReference userCollection = db.collection("infoUsers").document(email).collection("information");
                        Map<String, String> info = new HashMap<>();
                        info.put("photo", photoUrl);
                        info.put("name", name);
                        info.put("phone", phoneNumber);
                        info.put("email", email);
                        userCollection.document(email).set(info)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        startActivity(new Intent(SetProfileActivity.this, HomeActivity.class));
                                    }
                                })
                                .addOnCanceledListener(new OnCanceledListener() {
                                    @Override
                                    public void onCanceled() {
                                        Toast.makeText(SetProfileActivity.this,"Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
    }

    private void inputImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "get picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            if(data == null) return;
            uri = data.getData();
            Glide.with(this).load(uri).into(binding.profileImageView);
        }
    }

    private void insertImageOnFS() {
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
                        strUrl = uri.toString();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        strUrl = null;
                    }
                });
            }
        });
    }

}