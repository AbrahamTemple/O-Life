package com.example.myapplication.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapplication.data.model.DoctorResponse;
import com.example.myapplication.data.model.HospitalResponse;
import com.example.myapplication.data.network.block.Contract;
import com.example.myapplication.data.network.block.Model;
import com.example.myapplication.data.network.block.Presenter;
import com.example.myapplication.data.network.scheduler.SchedulerProvider;
import com.example.myapplication.domain.Counter;
import com.example.myapplication.service.CounterService;
import com.example.myapplication.util.GsonUtils;
import com.example.myapplication.util.SharedPreferencesUtils;
import com.example.myapplication.view.adapter.ListAdapter;
import com.example.myapplication.domain.Intro;
import com.example.myapplication.view.layout.ShapeLoadingDialog;
import com.example.myapplication.view.layout.SpruceRecyclerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

@Route(path = "/olife/list")
public class ListActivity extends AppCompatActivity implements Contract.View{

    @BindView(R.id.o_list)
    RecyclerView o_list;

    private ShapeLoadingDialog shapeLoadingDialog;
    private Presenter presenter;
    private SharedPreferencesUtils sharedPreferences;

    @Autowired
    public int action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        ARouter.getInstance().inject(this);
        initLoading();
        initRequest();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEventProgress(Counter counter) {
        switch (counter.getTag()){
            case 405:
                if(counter.getProgress() == 0) {
                    shapeLoadingDialog.dismiss();
                    Toast.makeText(ListActivity.this, "网络请求出现问题", Toast.LENGTH_SHORT).show();
                }
                break;
            case 402:
                if(counter.getProgress() == 0) {
                    shapeLoadingDialog.dismiss();
                    Toast.makeText(ListActivity.this, "访问权限不足", Toast.LENGTH_SHORT).show();
                }
                break;
            case 201:
                if(counter.getProgress() == 0) {
                    shapeLoadingDialog.dismiss();
//                    if (action == 0) {
//                        String dataString = sharedPreferences.getString("all_hospital");
//                        System.out.println(dataString);
//                        HospitalResponse data = GsonUtils.fromJson(dataString, HospitalResponse.class);
//                        initRecycler(data);
//                    } else {
//                        String dataString = sharedPreferences.getString("all_doctor");
//                        System.out.println(dataString);
//                        DoctorResponse data = GsonUtils.fromJson(dataString, DoctorResponse.class);
//                        initRecycler(data);
//                    }
                    Toast.makeText(ListActivity.this, "页面渲染成功", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    private void initRequest(){
        presenter = new Presenter(new Model(), this, SchedulerProvider.getInstance());
        sharedPreferences = SharedPreferencesUtils.init(ListActivity.this);
        if(action == 0) {
            presenter.getAllDoctor("fda1aaad-5c81-4afb-bbb0-ee8fdad9e4fd");
        } else {
            presenter.getAllHospital("fda1aaad-5c81-4afb-bbb0-ee8fdad9e4fd");
        }
    }

    private void initLoading(){
        shapeLoadingDialog = new ShapeLoadingDialog(this);
        shapeLoadingDialog.setLoadingText("加载中...");
        shapeLoadingDialog.show();
    }

    private void initRecycler(Object body){
        List<Intro> lists = new ArrayList<>();
        if(action == 0){
            DoctorResponse doctorResponse = (DoctorResponse) body;
            doctorResponse.getData().forEach(d->{
                Intro r = new Intro(R.drawable.doctor_img,d.getName(),d.getIntro(),"主治专业："+d.getSort(),"剩余号数："+d.getCount());
                lists.add(r);
            });
        } else {
            HospitalResponse hospitalResponse = (HospitalResponse) body;
            hospitalResponse.getData().forEach(d->{
                Intro r = new Intro(R.drawable.ramain_register,d.getName(),d.getAddress(),"电话："+d.getPhone(),"地址："+d.getAddress());
                lists.add(r);
            });
        }
        ListAdapter listAdapter = new ListAdapter(lists);
        new SpruceRecyclerView(this, o_list, listAdapter, true).init();
    }

    @Override
    public void getDataSuccess(ResponseBody body) {
        try {
            String result = body.string();
            Log.e("网络请求", "响应结果: " + result);
            if(action == 0) {
                DoctorResponse data = GsonUtils.fromJson(result, DoctorResponse.class);
//                String dataString = GsonUtils.toJson(data);
//                sharedPreferences.putString("all_doctor",GsonUtils.toJson(data));
                initRecycler(data);
            } else {
                HospitalResponse data = GsonUtils.fromJson(result, HospitalResponse.class);
//                String dataString = GsonUtils.toJson(data);
//                sharedPreferences.putString("all_hospital",GsonUtils.toJson(data));
                initRecycler(data);
            }
            CounterService.startDownload(this,1, 201);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getDataFail(Throwable throwable) {
        String[] httpStatus = throwable.getMessage().split("[ ]");
//        Toast.makeText(HomeActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
        CounterService.startDownload(this,2, Integer.valueOf(httpStatus[1]+1));
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
