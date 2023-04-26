package com.manager.btlonappbanhangonline.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
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
import com.manager.btlonappbanhangonline.viewmodels.MainFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    ViewFlipper viewFlipper;
    RecyclerView productRecycler;
    SearchView searchView;
    MainFragmentViewModel mainFragmentViewModel;
    public MainFragment() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewFlipper = view.findViewById(R.id.viewFlipper);
        productRecycler = view.findViewById(R.id.productRecycler);
        searchView = view.findViewById(R.id.searchView);

        mainFragmentViewModel = new ViewModelProvider(requireActivity()).get(MainFragmentViewModel.class);

        ActionViewFlipper();
        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(requireActivity().getApplicationContext(),2);
        productRecycler.setLayoutManager(layoutManager);
        productRecycler.setHasFixedSize(true);
        getProductData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    void getProductData(){
        mainFragmentViewModel.getAllProducts().observe(requireActivity(),newProducts ->{
            NewProductAdapter adapter = new NewProductAdapter(requireActivity().getApplicationContext(), newProducts);
            productRecycler.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });
    }

    private void ActionViewFlipper() {
        List<String> advertisements=new ArrayList<>();
        advertisements.add("https://intphcm.com/data/upload/banner-la-gi.jpg");
        advertisements.add("https://insieutoc.vn/wp-content/uploads/2021/02/mau-banner-quang-cao-khuyen-mai.jpg");
        advertisements.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-big-ky-nguyen-800-300.jpg");
        for(int i=0;i<advertisements.size();i++){
            ImageView imageView=new ImageView(requireActivity().getApplicationContext());
            Glide.with(requireActivity().getApplicationContext()).load(advertisements.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(2000);
        viewFlipper.setAutoStart(true);
        Animation slide_in= AnimationUtils.loadAnimation(requireActivity().getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out= AnimationUtils.loadAnimation(requireActivity().getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setInAnimation(slide_out);
    }
}