package com.manager.btlonappbanhangonline.login.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.manager.btlonappbanhangonline.login.ViewPagerAdapter;
import com.manager.btlonappbanhangonline.databinding.FragmentLoginContainerBinding;
import com.manager.btlonappbanhangonline.eventbus.IMoveClickListener;
import com.manager.btlonappbanhangonline.login.login.login.LoginFragment;
import com.manager.btlonappbanhangonline.login.login.register.RegisterFragment;

public class LoginContainerFragment extends Fragment {
    FragmentLoginContainerBinding binding;
    ViewPagerAdapter adapter;
    FirebaseAuth auth;
    NavController navController;
    public LoginContainerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginContainerBinding.inflate(inflater, container, false);
        //navController = Navigation.findNavController(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ViewPagerAdapter(requireActivity().getSupportFragmentManager());
        auth = FirebaseAuth.getInstance();

        //navController = Navigation.findNavController(view);

        adapter.addFragment(new RegisterFragment(), "Create Account");

        adapter.addFragment(new LoginFragment(new IMoveClickListener() {
            @Override
            public void moveTo() {
                //navController = Navigation.findNavController(view);
                //navController.navigate(R.id.action_loginFragment_to_forgetPassWordFragment);
            }
        }), "Login");

        binding.viewPager.setAdapter(adapter);
        binding.tabs.setupWithViewPager(binding.viewPager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}