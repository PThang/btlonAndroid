package com.manager.btlonappbanhangonline.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.manager.btlonappbanhangonline.Interface.IImageClickListener;
import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.model.Cart;
import com.manager.btlonappbanhangonline.model.EventBus.TinhTongEvent;
import com.manager.btlonappbanhangonline.model.GioHang;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    List<Cart> data;

    public CartAdapter(Context context, List<Cart> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try{
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
                    Log.i("Error when setting adapter: ", String.valueOf(count));
                }
            });
            holder.subImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = Integer.parseInt(holder.quantityText.getText().toString());
                    count--;
                    holder.quantityText.setText(String.valueOf(count));
                    Log.i("Error when setting adapter: ", String.valueOf(count));
                }
            });
        } catch (Exception e){
            Log.i("Error when setting adapter: ", e.toString());
        }
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
