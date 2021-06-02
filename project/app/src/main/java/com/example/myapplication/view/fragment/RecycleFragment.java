package com.example.myapplication.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.model.HospitalResponse;
import com.example.myapplication.view.adapter.ListAdapter;
import com.example.myapplication.view.items.Intro;
import com.example.myapplication.view.layout.SpruceRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecycleFragment extends Fragment {

    private HospitalResponse data;
    private RecyclerView recommend;


    public RecycleFragment(HospitalResponse data) {
        this.data = data;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recommend = view.findViewById(R.id.frag_recommend);
        initRecycler(data);
        return view;
    }

    private void initRecycler(HospitalResponse hospitalResponse){
        List<Intro> lists = new ArrayList<>();
        hospitalResponse.getData().forEach(d->{
            Intro r = new Intro(R.drawable.ramain_register,d.getName(),d.getIntro(),"电话："+d.getPhone(),"地址："+d.getAddress());
            lists.add(r);
        });
        ListAdapter listAdapter = new ListAdapter(lists);
        new SpruceRecyclerView(getContext(), recommend, listAdapter, true).init();
    }
}
