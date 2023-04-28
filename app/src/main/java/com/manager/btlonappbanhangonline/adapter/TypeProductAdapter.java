package com.manager.btlonappbanhangonline.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.manager.btlonappbanhangonline.R;

import com.manager.btlonappbanhangonline.eventbus.TypeProClickListener;
import com.manager.btlonappbanhangonline.model.TypeProduct;

import java.util.List;

public class TypeProductAdapter extends RecyclerView.Adapter<TypeProductAdapter.Holder> {
    private Context context;
    private List<TypeProduct> data;
    private TypeProClickListener listener;

    public TypeProductAdapter(Context context, List<TypeProduct> data, TypeProClickListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.nameType.setText(data.get(position).getName());
        Glide.with(context)
                .load(data.get(position).getImage())
                .into(holder.iconImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Type product was selected : ", data.get(holder.getAdapterPosition()).getName());
                listener.OnClick(data.get(holder.getAdapterPosition()).getIdType());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private ImageView iconImage;
        private TextView nameType;

        public Holder(@NonNull View itemView) {
            super(itemView);

            iconImage = itemView.findViewById(R.id.iconImage);
            nameType = itemView.findViewById(R.id.nameTypeText);

        }

//        public void setItemClickListener(ItemClickListener itemClickListener) {
//            this.itemClickListener = itemClickListener;
//        }
//
//        @Override
//        public void onClick(View view) {
//            itemClickListener.onClick(view, getAdapterPosition(), false);
//        }
    }
}
