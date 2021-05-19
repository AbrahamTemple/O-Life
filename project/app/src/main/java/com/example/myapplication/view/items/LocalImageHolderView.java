package com.example.myapplication.view.items;

import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.example.myapplication.R;

public class LocalImageHolderView extends Holder<Integer> {
    private ImageView imageView;

    public LocalImageHolderView(View itemView) {
        super(itemView);
    }

    @Override
    protected void initView(View itemView) {
        imageView =itemView.findViewById(R.id.ivPost);
    }

    @Override
    public void updateUI(Integer data) {
        imageView.setImageResource(data);
    }
}
