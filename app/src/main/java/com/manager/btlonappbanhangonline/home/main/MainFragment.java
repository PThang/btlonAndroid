package com.manager.btlonappbanhangonline.home.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.home.main.adapter.NewProductAdapter;

import com.manager.btlonappbanhangonline.home.main.adapter.TypeProductAdapter;
import com.manager.btlonappbanhangonline.databinding.FragmentMainBinding;
import com.manager.btlonappbanhangonline.eventbus.TypeProClickListener;
import com.manager.btlonappbanhangonline.model.NewProduct;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    MainFragmentViewModel mainFragmentViewModel;
    FragmentMainBinding binding;
    NewProductAdapter newProductAdapter;
    TypeProductAdapter typeProductAdapter;
    public MainFragment() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainFragmentViewModel = new ViewModelProvider(requireActivity()).get(MainFragmentViewModel.class);
        binding.searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent= new Intent(requireActivity().getApplicationContext(), SearchActivity.class);
                startActivity(intent);*/
            }
        });

        ActionViewFlipper();
        RecyclerView.LayoutManager layoutManagerType = new StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL);
        binding.typeProductRecycler.setLayoutManager(layoutManagerType);
        binding.typeProductRecycler.setHasFixedSize(true);
        //getTypeProData();
        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(requireActivity(),2);
        binding.productRecycler.setLayoutManager(layoutManager);
        binding.productRecycler.setHasFixedSize(true);
        //getProductData();
        mainFragmentViewModel.getAllProducts().observe(getViewLifecycleOwner(), new Observer<List<NewProduct>>() {
            @Override
            public void onChanged(List<NewProduct> newProducts) {
                newProductAdapter = new NewProductAdapter(requireActivity().getApplicationContext(), newProducts);
                binding.productRecycler.setAdapter(newProductAdapter);
                Log.i("Size of data: ", String.valueOf(newProducts.size()));
                newProductAdapter.notifyDataSetChanged();
            }
        });

        mainFragmentViewModel.getAllTypes().observe(getViewLifecycleOwner(), types -> {
            typeProductAdapter = new TypeProductAdapter(requireActivity().getApplicationContext(), types, new TypeProClickListener() {
                @Override
                public void OnClick(String id) {
                    mainFragmentViewModel.getProductByType(id).observe(getViewLifecycleOwner(), data ->{
                        newProductAdapter = new NewProductAdapter(requireActivity().getApplicationContext(), data);
                        binding.productRecycler.setAdapter(newProductAdapter);
                        Log.i("Size of data: ", String.valueOf(data.size()));
                        newProductAdapter.notifyDataSetChanged();
                    });
                }
            });
            binding.typeProductRecycler.setAdapter(typeProductAdapter);
            typeProductAdapter.notifyDataSetChanged();
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        getProductData();
        getTypeProData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }

    void getProductData(){
        mainFragmentViewModel.getAllProducts().observe(getViewLifecycleOwner(), new Observer<List<NewProduct>>() {
            @Override
            public void onChanged(List<NewProduct> newProducts) {
                NewProductAdapter adapter = new NewProductAdapter(requireActivity().getApplicationContext(), newProducts);
                binding.productRecycler.setAdapter(adapter);
                Log.i("Size of data: ", String.valueOf(newProducts.size()));
            }
        });
    }

    void getTypeProData(){
        mainFragmentViewModel.getAllTypes().observe(getViewLifecycleOwner(), types -> {
            TypeProductAdapter adapter = new TypeProductAdapter(requireActivity().getApplicationContext(), types, new TypeProClickListener() {
                @Override
                public void OnClick(String id) {
                    mainFragmentViewModel.getProductByType(id).observe(getViewLifecycleOwner(), data ->{
                        NewProductAdapter adapter = new NewProductAdapter(requireActivity().getApplicationContext(), data);
                        binding.productRecycler.setAdapter(adapter);
                        Log.i("Size of data: ", String.valueOf(data.size()));
                    });
                }
            });
            binding.typeProductRecycler.setAdapter(adapter);
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
            binding.viewFlipper.addView(imageView);
        }
        binding.viewFlipper.setFlipInterval(2000);
        binding.viewFlipper.setAutoStart(true);
        Animation slide_in= AnimationUtils.loadAnimation(requireActivity().getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out= AnimationUtils.loadAnimation(requireActivity().getApplicationContext(), R.anim.slide_out_right);
        binding.viewFlipper.setInAnimation(slide_in);
        binding.viewFlipper.setInAnimation(slide_out);
    }
}