package com.example.myapplication.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.domain.Intro;
import com.example.myapplication.domain.Order;
import com.skyfishjy.library.RippleBackground;

import java.text.SimpleDateFormat;
import java.util.List;

public class OrderAdpater extends RecyclerView.Adapter<OrderAdpater.ViewHolder>{

    private List<Order> lists;
    private OnItemClickListener onItemClickListener;
    private RippleBackground point1,piont2;

    public OrderAdpater(List<Order> lists) {
        this.lists = lists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order o = lists.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        holder.title.setText(o.getTitle());
        holder.time.setText(sdf.format(o.getTime()));
        holder.address.setText(o.getAddress());
        holder.state.setText(o.getState());
        holder.server.setText(o.getServer());
        holder.itemView.setOnClickListener(v -> onItemClickListener.onClick(o,position+1));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView state;
        TextView time;
        TextView address;
        TextView server;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.order_title);
            state = itemView.findViewById(R.id.order_state);
            time = itemView.findViewById(R.id.order_time);
            address = itemView.findViewById(R.id.order_address);
            server = itemView.findViewById(R.id.order_server);
        }
    }

    public interface  OnItemClickListener{
        void onClick(Order order, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
