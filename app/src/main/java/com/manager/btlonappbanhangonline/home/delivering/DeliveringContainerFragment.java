package com.manager.btlonappbanhangonline.home.delivering;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.databinding.FragmentDeliveringContainerBinding;
import com.manager.btlonappbanhangonline.home.delivering.delivery.DeliveringFragment;
import com.manager.btlonappbanhangonline.home.delivering.received.ReceivedFragment;

public class DeliveringContainerFragment extends Fragment {
    FragmentDeliveringContainerBinding binding;

    public DeliveringContainerFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void innitEvents() {
        binding.deliveringContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.deliveringContainer.setBackgroundResource(R.drawable.bg_button);
                binding.receivedContainer.setBackgroundResource(R.drawable.blank_bg);
                binding.deliveryText.setTextColor(ContextCompat.getColor(requireActivity().getApplicationContext(), R.color.white));
                binding.receivedText.setTextColor(ContextCompat.getColor(requireActivity().getApplicationContext(), R.color.black));

                replaceFragment(new DeliveringFragment());
            }
        });

        binding.receivedContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.receivedContainer.setBackgroundResource(R.drawable.bg_button);
                binding.deliveringContainer.setBackgroundResource(R.drawable.blank_bg);
                binding.receivedText.setTextColor(ContextCompat.getColor(requireActivity().getApplicationContext(), R.color.white));
                binding.deliveryText.setTextColor(ContextCompat.getColor(requireActivity().getApplicationContext(), R.color.black));

                replaceFragment(new ReceivedFragment());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDeliveringContainerBinding.inflate(getLayoutInflater());
        replaceFragment(new DeliveringFragment());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        innitEvents();
    }

    public void replaceFragment(Fragment fragment){
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.deliveryContainer, fragment)
                .commit();
    }
}