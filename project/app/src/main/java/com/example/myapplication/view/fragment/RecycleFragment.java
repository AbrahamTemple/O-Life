package com.example.myapplication.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapplication.R;
import com.example.myapplication.data.model.HospitalResponse;
import com.example.myapplication.router.LoginCallbackImpl;
import com.example.myapplication.router.RoutePath;
import com.example.myapplication.util.SharedPreferencesUtils;
import com.example.myapplication.view.adapter.ListAdapter;
import com.example.myapplication.domain.Intro;
import com.example.myapplication.view.layout.SpruceRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecycleFragment extends Fragment {

    private HospitalResponse data;
    private RecyclerView recommend;
//    private SharedPreferencesUtils tokenShared;

    public RecycleFragment(HospitalResponse data) {
        this.data = data;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recommend = view.findViewById(R.id.frag_recommend);
//        tokenShared = SharedPreferencesUtils.init(getActivity(),"oauth");
        initRecycler(data);
    }

    private void initRecycler(HospitalResponse hospitalResponse){
        List<Intro> lists = new ArrayList<>();
        hospitalResponse.getData().forEach(d->{
            Intro r = new Intro(R.drawable.ramain_register,d.getName(),d.getIntro(),"电话："+d.getPhone(),"地址："+d.getAddress());
            lists.add(r);
        });
        ListAdapter listAdapter = new ListAdapter(lists);
        listAdapter.setOnItemClickListener((intro, position) -> {
            System.out.println(position);
//            ARouter.getInstance().build(RoutePath.LIST.toString())
//                    .withInt("action", 0)
//                    .withLong("id",position)
//                    .navigation(getActivity(),new LoginCallbackImpl());
//            tokenShared.putLong("hid",position);
        });
        new SpruceRecyclerView(getContext(), recommend, listAdapter, true).init();
    }
}
