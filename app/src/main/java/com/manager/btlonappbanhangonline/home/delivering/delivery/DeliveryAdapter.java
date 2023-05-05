package com.manager.btlonappbanhangonline.home.delivering.delivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.home.delivering.DeliveryOnclickListener;
import com.manager.btlonappbanhangonline.model.Delivery;

import java.util.List;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.Holder>{
    List<Delivery> data;
    Context context;
    DeliveryOnclickListener listener;

    public DeliveryAdapter(List<Delivery> data, Context context, DeliveryOnclickListener listener) {
        this.data = data;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_item,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.onBind(data.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView dateText;
        AppCompatButton receivedButton;
        public Holder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.dateOrder);
            receivedButton = itemView.findViewById(R.id.receivedButton);
        }

        void onBind(Delivery delivery, DeliveryOnclickListener listener){
            dateText.setText(delivery.getOrder().getOrderDate());
            receivedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.changeStateDeliveryListener(getAdapterPosition());
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition());
                }
            });
        }
    }
}
