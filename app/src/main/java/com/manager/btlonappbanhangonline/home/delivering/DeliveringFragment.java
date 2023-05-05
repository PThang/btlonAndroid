package com.manager.btlonappbanhangonline.home.delivering;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.databinding.FragmentDeliveringBinding;

public class DeliveringFragment extends Fragment {
    FragmentDeliveringBinding binding;

    public DeliveringFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDeliveringBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}