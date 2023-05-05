package com.manager.btlonappbanhangonline.home.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.databinding.FragmentProfileBinding;
import com.manager.btlonappbanhangonline.login.LoginActivity;
import com.manager.btlonappbanhangonline.login.setprofile.SetProfileActivity;
import com.manager.btlonappbanhangonline.model.NewProduct;
import com.manager.btlonappbanhangonline.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    ProfileViewModel profileViewModel;

    FirebaseAuth auth;
//    FirebaseAuth auth;
//    FirebaseUser user;
//    FirebaseFirestore db;
    //User userAc;

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

        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        setData();
        binding.logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });

        binding.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });

        return binding.getRoot();
    }

    private void setData() {
        profileViewModel.user.observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser user) {
                Glide.with(requireActivity()).load(user.getPhotoUrl()).into(binding.profileImageView)/*.onLoadFailed(getResources().getDrawable(R.drawable.user))*/;
                binding.inputName.setText(user.getDisplayName());
                binding.inputEmail.setText(user.getEmail());
            }
        });

        profileViewModel.getUserLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.inputAddress.setText(user.getAddress());
                binding.inputPhoneNumber.setText(user.getPhoneNumber());
            }
        });
    }

    private void editProfile() {
        String srtMove = "MoveFromProfile";
        Intent i = new Intent(requireActivity(), SetProfileActivity.class);
        i.putExtra("result", srtMove);
        startActivity(i);

    }

    private void logOut() {
        auth.signOut();
        startActivity(new Intent(requireActivity(), LoginActivity.class));
    }
}