package com.manager.btlonappbanhangonline.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.adapter.NewProductAdapter;
import com.manager.btlonappbanhangonline.model.NewProduct;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    ViewFlipper viewFlipper;
    RecyclerView productRecycler;
    SearchView searchView;
    //NewProductAdapter adapter;
    //List<NewProduct> data;
    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewFlipper = view.findViewById(R.id.viewFlipper);
        productRecycler = view.findViewById(R.id.productRecycler);
        searchView = view.findViewById(R.id.searchView);
        db = FirebaseFirestore.getInstance();

        ActionViewFlipper();
        //adapter = new NewProductAdapter(requireActivity().getApplicationContext(), data);
        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(requireActivity().getApplicationContext(),2);
        productRecycler.setLayoutManager(layoutManager);
        productRecycler.setHasFixedSize(true);
        getProductData();
        //productRecycler.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    void getProductData(){
        List<NewProduct> data = new ArrayList<>();
        db.collection("items")
                .addSnapshotListener((value, error) -> {
                    if(error != null){
                        Log.i("error when getting data:", error.toString());
                        return;
                    }
                    for(DocumentChange dc : value.getDocumentChanges()){
                        if(dc.getType() == DocumentChange.Type.ADDED){
                            data.add(dc.getDocument().toObject(NewProduct.class));
                            Log.i("error when getting data:",dc.getDocument().toObject(NewProduct.class).getName());
                        }
                    }
                    NewProductAdapter adapter = new NewProductAdapter(requireActivity().getApplicationContext(), data);
                    productRecycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                });
    }

    private void ActionViewFlipper() {
        List<String> mangquangcao=new ArrayList<>();
        mangquangcao.add("https://intphcm.com/data/upload/banner-la-gi.jpg");
        mangquangcao.add("https://insieutoc.vn/wp-content/uploads/2021/02/mau-banner-quang-cao-khuyen-mai.jpg");
        mangquangcao.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-big-ky-nguyen-800-300.jpg");
        for(int i=0;i<mangquangcao.size();i++){
            ImageView imageView=new ImageView(requireActivity().getApplicationContext());
            Glide.with(requireActivity().getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in= AnimationUtils.loadAnimation(requireActivity().getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out= AnimationUtils.loadAnimation(requireActivity().getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setInAnimation(slide_out);
    }
}