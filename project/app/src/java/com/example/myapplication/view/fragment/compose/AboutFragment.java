package com.example.myapplication.view.fragment.compose;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.ui.HomeActivity;
import com.skydoves.elasticviews.ElasticAnimation;
import com.skydoves.elasticviews.ElasticLayout;

public class AboutFragment extends Fragment {

    private ElasticLayout task;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        task = view.findViewById(R.id.about_context);
        new ElasticAnimation(task).setScaleX(0.85f).setScaleY(0.85f).setDuration(500)
                .setOnFinishListener(() -> Toast.makeText(getContext(), "内容更新完成", Toast.LENGTH_SHORT).show()).doAction();
    }
}
