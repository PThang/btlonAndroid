package com.manager.btlonappbanhangonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.manager.btlonappbanhangonline.R;

import com.manager.btlonappbanhangonline.model.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    private RecyclerView.RecycledViewPool viewPool=new RecyclerView.RecycledViewPool();
    Context context;
    List<Order> listdonhang;

    public OrderAdapter(Context context, List<Order> listdonhang) {
        this.context = context;
        this.listdonhang = listdonhang;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Order donHang= listdonhang.get(position);
        holder.txtdonhang.setText("Đơn hàng: "+ donHang.getId());
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.reChitiet.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(donHang.getItem().size());
        //adapter chitiet
        DetailAdapter chitietAdapter=new DetailAdapter(context,donHang.getItem());
        holder.reChitiet.setLayoutManager(layoutManager);
        holder.reChitiet.setAdapter(chitietAdapter);
        holder.reChitiet.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return listdonhang.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtdonhang;
        RecyclerView reChitiet;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            txtdonhang =itemView.findViewById(R.id.iddonhang);
            reChitiet=itemView.findViewById(R.id.recycleview_chitiet);
        }
    }
}