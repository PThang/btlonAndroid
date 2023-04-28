package com.manager.btlonappbanhangonline.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.activity.LoginActivity;
import com.manager.btlonappbanhangonline.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    FirebaseAuth auth;
    FirebaseUser user;

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

        if (user != null) {
//            // Name, email address, and profile photo Url
//            String name = user.getDisplayName();
//            String email = user.getEmail();
//            Uri photoUrl = user.getPhotoUrl();
//
//            // Check if user's email is verified
//            boolean emailVerified = user.isEmailVerified();
//
//            // The user's ID, unique to the Firebase project. Do NOT use this value to
//            // authenticate with your backend server, if you have one. Use
//            // FirebaseUser.getIdToken() instead.
//            String uid = user.getUid();
//            Log.i("Firebase's user :", user.getDisplayName());
            Log.i("Firebase's user :", user.getEmail());
        }


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