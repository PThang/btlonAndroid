package com.manager.btlonappbanhangonline.home.delivering.delivery;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.databinding.FragmentDeliveringBinding;
import com.manager.btlonappbanhangonline.home.delivering.DeliveryOnclickListener;
import com.manager.btlonappbanhangonline.home.delivering.detail.DetailOrderActivity;
import com.manager.btlonappbanhangonline.model.Delivery;
import com.manager.btlonappbanhangonline.model.NewProduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveringFragment extends Fragment {
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String userEmail;

    FragmentDeliveringBinding binding;
    public DeliveringFragment() {
        // Required empty public constructor
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        userEmail = currentUser.getEmail();

        binding.listDelivery.setHasFixedSize(true);
        binding.listDelivery.setLayoutManager(new LinearLayoutManager(requireActivity().getApplicationContext()));
        getData();
    }

    void getData(){
        MutableLiveData<List<Delivery>> deliveries = new MutableLiveData<>();
        List<Delivery> data = new ArrayList<>();
        db.collection("deliveries")
                .document(currentUser.getEmail())
                .collection(currentUser.getEmail())
                .whereEqualTo("isReceived", false)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.i("error when getting data:", error.toString());
                            return;
                        }
                        for(DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                data.add(dc.getDocument().toObject(Delivery.class));
                                Log.i("when getting data:",dc.getDocument().toObject(Delivery.class).getIsReceived().toString());
                            }
                        }
                        deliveries.postValue(data);
                    }
                });
        deliveries.observe(getViewLifecycleOwner(), new Observer<List<Delivery>>() {
            @Override
            public void onChanged(List<Delivery> newDeliveries) {
                binding.listDelivery.setAdapter(new DeliveryAdapter(newDeliveries, requireActivity().getApplicationContext(), new DeliveryOnclickListener() {
                    @Override
                    public void onClick(int position) {
                        startActivity(new Intent(requireActivity(), DetailOrderActivity.class));
                    }

                    @Override
                    public void changeStateDeliveryListener(int position) {
                        Map<String, Object> updates = new HashMap<>();
                        updates.put("isReceived", true);
                        db.collection("deliveries")
                                .document(currentUser.getEmail())
                                .collection(currentUser.getEmail())
                                .document(data.get(position).getId())
                                .update(updates)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        //Log.i("Shange state : ", "Thanks");
                                        Toast.makeText(requireActivity(), "Thanks", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e("Shange state : ", "Lỗi khi cập nhật trạng thái", e);
                                    }
                                });
                    }
                }));
            }
        });
    }
}