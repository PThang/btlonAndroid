package com.manager.btlonappbanhangonline.home.cart.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.model.Cart;
import com.manager.btlonappbanhangonline.home.cart.CartViewModel;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    List<Cart> data;
    CartViewModel viewModel;

    public CartAdapter(Context context, List<Cart> data) {
        this.context = context;
        this.data = data;
        viewModel = ViewModelProviders.of((FragmentActivity) context).get(CartViewModel.class);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context)
                .load(data.get(position).getImageProduct())
                .into(holder.productImage);
        holder.nameProductText.setText(data.get(position).getNameProduct());
        holder.quantityText.setText(String.valueOf(data.get(position).getQuantity()));
        holder.costProductText.setText(data.get(position).getCostProduct());

        holder.addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(holder.quantityText.getText().toString());
                count++;
                holder.quantityText.setText(String.valueOf(count));
                Cart cart = data.get(holder.getAdapterPosition());
                cart.setQuantity(count);
                //Log.i("Error when setting adapter: ", String.valueOf(count));
                viewModel.updateCart(cart);
            }
        });
        holder.subImage.setOnClickListener(v -> {
            int count = Integer.parseInt(holder.quantityText.getText().toString());
            if(count > 1){
                count--;
                holder.quantityText.setText(String.valueOf(count));
                Cart cart = data.get(holder.getAdapterPosition());
                cart.setQuantity(count);
                //Log.i("Error when setting adapter: ", String.valueOf(count));
                viewModel.updateCart(cart);
                //Log.i("Error when setting adapter: ", String.valueOf(count));
            } else if (count <= 1){
                viewModel.deleteCart(data.get(position));
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView productImage, subImage, addImage;
        TextView nameProductText, costProductText, quantityText,noName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productCartImage);
            subImage = itemView.findViewById(R.id.subImage);
            addImage = itemView.findViewById(R.id.addImage);
            nameProductText = itemView.findViewById(R.id.nameProductCart);
            costProductText = itemView.findViewById(R.id.costProductCart);
            quantityText = itemView.findViewById(R.id.quantityText);
            noName = itemView.findViewById(R.id.noName);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
