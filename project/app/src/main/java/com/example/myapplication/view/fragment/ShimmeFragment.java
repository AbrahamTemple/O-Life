package com.example.myapplication.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

import io.supercharge.shimmerlayout.ShimmerLayout;

public class ShimmeFragment extends Fragment {

    private ShimmerLayout shimmerLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shimme, container, false);
        shimmerLayout = view.findViewById(R.id.shimmer_layout);
        shimmerLayout.startShimmerAnimation();
        return view;
    }
}
