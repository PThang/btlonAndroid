package com.manager.btlonappbanhangonline.home.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.databinding.FragmentProfileBinding;
import com.manager.btlonappbanhangonline.login.LoginActivity;


public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore db;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();


        if (user != null) {
            Log.i("Firebase's user :", user.getEmail());
        }

        Glide.with(this).load(user.getPhotoUrl()).into(binding.profileImageView).onLoadFailed(getResources().getDrawable(R.drawable.user));
        binding.inputName.setText(user.getDisplayName());
        //binding.inputName.setText(db.collection("infoUsers").);
        binding.inputEmail.setText(user.getEmail());
        binding.logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });

        return binding.getRoot();
    }

    private void logOut() {
        auth.signOut();
        startActivity(new Intent(requireActivity(), LoginActivity.class));
    }
}