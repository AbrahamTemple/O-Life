package com.example.myapplication.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.view.model.Intro;
import com.intrusoft.squint.DiagonalView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    List<Intro> lists;
    OnItemClickListener onItemClickListener;

    public ListAdapter(List<Intro> lists) {
        this.lists = lists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_recommend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Intro recommend = lists.get(position);
        holder.image.setImageResource(recommend.getImg());
        holder.about.setText(recommend.getAbout());
        holder.title.setText(recommend.getTitle());
        holder.other.setText(recommend.getOther());
        holder.main.setText(recommend.getMain());
        holder.itemView.setOnClickListener(v -> {
            if(onItemClickListener!=null){
                Log.e("TT",holder.itemView.toString());
                onItemClickListener.onClick(recommend,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        DiagonalView image;
        TextView title;
        TextView about;
        TextView other;
        TextView main;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.diagonal);
            title = itemView.findViewById(R.id.r_title);
            about = itemView.findViewById(R.id.r_about);
            other = itemView.findViewById(R.id.r_other);
            main = itemView.findViewById(R.id.r_main);
        }
    }

    /**
     * 定义点击回调接口
     */
    public interface  OnItemClickListener{
        void onClick(Intro recommend, int position);
    }

}
