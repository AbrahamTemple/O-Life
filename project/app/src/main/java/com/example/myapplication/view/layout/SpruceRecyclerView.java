package com.example.myapplication.view.layout;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.view.adapter.RecommendAdapter;
import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.SpruceAnimator;
import com.willowtreeapps.spruce.animation.DefaultAnimations;
import com.willowtreeapps.spruce.sort.DefaultSort;


public class SpruceRecyclerView {

    private Context context;
    private SpruceAnimator spruceAnimator;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private boolean isAnimator;

    public SpruceRecyclerView(Context context, RecyclerView recyclerView, RecyclerView.Adapter adapter, boolean isAnimator) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.adapter = adapter;
        this.isAnimator = isAnimator;
    }

    public void init(){
        LinearLayoutManager manager = new LinearLayoutManager(context){
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                if(isAnimator) {
                    initSpruce();
                }
            }
        };//创造布局方式
        recyclerView.setLayoutManager(manager);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    private void initSpruce() {
        spruceAnimator = new Spruce
                .SpruceBuilder(recyclerView)
                .sortWith(new DefaultSort(100)) //偏移延迟
                .animateWith(DefaultAnimations.dynamicFadeIn(recyclerView),
                        DefaultAnimations.dynamicTranslationUpwards(recyclerView))
                .start();
    }

    public void stop(){
        if(spruceAnimator!=null) {
            spruceAnimator.cancel();
        }
    }
}
