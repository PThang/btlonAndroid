package com.manager.btlonappbanhangonline.login.setprofile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.manager.btlonappbanhangonline.databinding.ActivitySetProfileBinding;
import com.manager.btlonappbanhangonline.home.HomeActivity;
import com.manager.btlonappbanhangonline.login.LoginActivity;
import com.manager.btlonappbanhangonline.model.User;

public class SetProfileActivity extends AppCompatActivity {
    private ActivitySetProfileBinding binding;
    private ProfileViewModel profileViewModel;
    Uri uri = null;
    String start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        profileViewModel = new ViewModelProvider(SetProfileActivity.this).get(ProfileViewModel.class);
        initEvents();

        start = getIntent().getStringExtra("start");
        if(getIntent().getStringExtra("result") != null){
            setData();
        }
    }

    private void setData() {
        profileViewModel.user.observe(SetProfileActivity.this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser user) {
                Glide.with(SetProfileActivity.this).load(user.getPhotoUrl()).into(binding.profileImageView);
                binding.inputName.setText(user.getDisplayName());
                binding.inputEmail.setText(user.getEmail());
            }
        });

        profileViewModel.userAc.observe(SetProfileActivity.this, new Observer<User>() {
            @Override
            public void onChanged(User userAc) {
                binding.inputAddress.setText(userAc.getAddress());
                binding.inputPhoneNumber.setText(userAc.getPhoneNumber());
            }
        });
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

        profileViewModel.user.observe(SetProfileActivity.this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                binding.inputEmail.setText(firebaseUser.getEmail().toString());
            }
        });
        binding.backCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(start.equalsIgnoreCase("login")){
                    startActivity(new Intent(SetProfileActivity.this, LoginActivity.class));
                }
                else if(start.equalsIgnoreCase("home")){
                    startActivity(new Intent(SetProfileActivity.this, HomeActivity.class));
                }
            }
        });
    }

    private void saveInfo() {
        String name = binding.inputName.getText().toString();
        String email = binding.inputEmail.getText().toString();
        String address = binding.inputAddress.getText().toString();
        String phoneNumber = binding.inputPhoneNumber.getText().toString();

        profileViewModel.saveInfo(uri, name, email, address, phoneNumber);
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
}