package com.manager.btlonappbanhangonline.home.delivering.received;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.manager.btlonappbanhangonline.databinding.FragmentReceivedBinding;
import com.manager.btlonappbanhangonline.home.delivering.DeliveryOnclickListener;
import com.manager.btlonappbanhangonline.home.delivering.DeliveryAdapter;
import com.manager.btlonappbanhangonline.home.delivering.detail.DetailOrderActivity;
import com.manager.btlonappbanhangonline.model.Delivery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceivedFragment extends Fragment {
    private FragmentReceivedBinding binding;
    private ReceivedViewModel receivedViewModel;
    public ReceivedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReceivedBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        receivedViewModel = new ViewModelProvider(requireActivity()).get(ReceivedViewModel.class);
        binding.receivedList.setHasFixedSize(true);
        binding.receivedList.setLayoutManager(new LinearLayoutManager(requireActivity().getApplicationContext()));
        getData();
    }

    void getData(){
        receivedViewModel.receivedLiveData.observe(getViewLifecycleOwner(), new Observer<List<Delivery>>() {
            @Override
            public void onChanged(List<Delivery> deliveries) {
                binding.receivedList.setAdapter(new DeliveryAdapter(deliveries, requireActivity().getApplicationContext(), new DeliveryOnclickListener() {
                    @Override
                    public void onClick(int position) {
                        Intent intent = new Intent(requireActivity(), DetailOrderActivity.class);
                        intent.putExtra("order", deliveries.get(position).getOrder());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                    @Override
                    public void changeStateDeliveryListener(int position) {
                    }
                }, true));
            }
        });
    }
}