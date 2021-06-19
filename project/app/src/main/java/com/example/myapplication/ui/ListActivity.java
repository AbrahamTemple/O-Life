package com.example.myapplication.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapplication.data.model.DoctorResponse;
import com.example.myapplication.data.model.HospitalResponse;
import com.example.myapplication.data.model.RegisterResponse;
import com.example.myapplication.data.model.StaffResponse;
import com.example.myapplication.data.network.block.Contract;
import com.example.myapplication.data.network.block.Model;
import com.example.myapplication.data.network.block.Presenter;
import com.example.myapplication.data.network.scheduler.SchedulerProvider;
import com.example.myapplication.domain.Counter;
import com.example.myapplication.domain.EscortDto;
import com.example.myapplication.domain.ORegister;
import com.example.myapplication.domain.RegisterDto;
import com.example.myapplication.event.RxTimer;
import com.example.myapplication.router.LoginCallbackImpl;
import com.example.myapplication.router.RoutePath;
import com.example.myapplication.service.AmqpService;
import com.example.myapplication.service.CounterService;
import com.example.myapplication.service.OrderService;
import com.example.myapplication.util.GsonUtils;
import com.example.myapplication.util.SharedPreferencesUtils;
import com.example.myapplication.view.adapter.ListAdapter;
import com.example.myapplication.domain.Intro;
import com.example.myapplication.view.layout.ShapeLoadingDialog;
import com.example.myapplication.view.layout.SpruceRecyclerView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

@Route(path = "/olife/list")
public class ListActivity extends AppCompatActivity implements Contract.View{

    @BindView(R.id.o_list)
    RecyclerView o_list;

    private ShapeLoadingDialog shapeLoadingDialog;
    private Presenter presenter;
    private SharedPreferencesUtils sharedPreferences,tokenShared;

    @Autowired
    public int action;

    @Autowired
    public long id;

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
                    Toast.makeText(ListActivity.this, "页面渲染成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case 407:
                if(counter.getProgress() == 0) {
                    shapeLoadingDialog.dismiss();
                    Toast.makeText(ListActivity.this, "没有合法的请求头", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    private void initRequest(){
        presenter = new Presenter(new Model(), this, SchedulerProvider.getInstance());
        sharedPreferences = SharedPreferencesUtils.init(ListActivity.this);
        tokenShared = SharedPreferencesUtils.init(this,"oauth");
        switch (action) {
            case 0:
                presenter.getHospitalDoctor(id, tokenShared.getString("token"));
                break;
            case 1:
                presenter.getAllHospital(tokenShared.getString("token"));
                break;
            case 2:
                presenter.getAllDoctor(tokenShared.getString("token"));
                break;
            case 3:
                presenter.getAllStaff(tokenShared.getString("token"));
                break;
        }
    }

    private void initLoading(){
        shapeLoadingDialog = new ShapeLoadingDialog(this);
        shapeLoadingDialog.setLoadingText("加载中...");
        shapeLoadingDialog.show();
    }

    private void initRecycler(Object body){
        List<Intro> lists = new ArrayList<>();
        ListAdapter listAdapter = new ListAdapter(lists);
        switch (action){
            case 0:
                DoctorResponse doctorResponse = (DoctorResponse) body;
                doctorResponse.getData().forEach(d->{
                    Intro r = new Intro(R.drawable.doctor_img,d.getName(),d.getIntro(),"主治专业："+d.getSort(),"剩余号数："+d.getCount());
                    r.setId(d.getId());
                    lists.add(r);
                });
                listAdapter = new ListAdapter(lists);
                listAdapter.setOnItemClickListener(this::showRegisterDialog);
            break;
            case 1:
                HospitalResponse hospitalResponse = (HospitalResponse) body;
                hospitalResponse.getData().forEach(d->{
                    Intro r = new Intro(R.drawable.ramain_register,d.getName(),d.getIntro(),"电话："+d.getPhone(),"地址："+d.getAddress());
                    lists.add(r);
                });
                listAdapter = new ListAdapter(lists);
                listAdapter.setOnItemClickListener((intro, position) -> {
                    ARouter.getInstance().build(RoutePath.LIST.toString())
                            .withInt("action", 0)
                            .withLong("id",position)
                            .navigation(this,new LoginCallbackImpl());
                    tokenShared.putLong("hid",position);
                    tokenShared.putString("hospital",intro.getMain().replace("地址：",""));
                });
            break;
            case 2:
                DoctorResponse doctorResponse2 = (DoctorResponse) body;
                doctorResponse2.getData().forEach(d->{
                    Intro r = new Intro(R.drawable.doctor_ps,d.getName(),d.getIntro(),"主治专业："+d.getSort(),"工作时长："+d.getJobTime());
                    lists.add(r);
                });
                listAdapter = new ListAdapter(lists);
                listAdapter.setOnItemClickListener((intro, position) -> showChatDialog(intro.getTitle(),position));
            break;
            case 3:
                StaffResponse staffResponse = (StaffResponse) body;
                staffResponse.getData().forEach(d->{
                    Intro r = new Intro(R.drawable.staff_img,d.getName(),d.getIntro(),"电话："+d.getPhone(),"工作时长："+d.getJobTime()+"年");
                    lists.add(r);
                });
                listAdapter.setOnItemClickListener((intro, position) -> showEscortDialog(intro,position));
            break;
        }
        new SpruceRecyclerView(this, o_list, listAdapter, true).init();
    }

    @Override
    public void getDataSuccess(ResponseBody body) {
        try {
            String result = body.string();
            Log.e("网络请求", "响应结果: " + result);
            RxTimer rxTimer = new RxTimer();
            switch (action) {
                case 0:
                case 2:
                    DoctorResponse data0 = GsonUtils.fromJson(result, DoctorResponse.class);
                    rxTimer.timer(2000, number -> initRecycler(data0));
                break;
                case 1:
                    HospitalResponse data1 = GsonUtils.fromJson(result, HospitalResponse.class);
                    rxTimer.timer(2000, number ->initRecycler(data1));
                break;
                case 3:
                    StaffResponse data2 = GsonUtils.fromJson(result,StaffResponse.class);
                    rxTimer.timer(2000, number ->initRecycler(data2));
                break;
            }
            CounterService.startDownload(this,1, 201);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getDataFail(Throwable throwable) {
        String[] httpStatus = throwable.getMessage().split("[ ]");
        CounterService.startDownload(this,2, Integer.valueOf(httpStatus[1]+1));
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void showRegisterDialog(Intro doctor, int position){
        AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle("注意")
                .setMessage("你确定要挂"+doctor.getTitle()+"医生的号吗？")
                .setIcon(R.mipmap.info)
                .setPositiveButton("确定", (dialogInterface, i) -> {
                    shapeLoadingDialog.show();
                    Toast.makeText(ListActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                    RegisterDto dto = new RegisterDto(tokenShared.getLong("id"),tokenShared.getLong("id"),tokenShared.getLong("hid"),doctor.getId(),doctor.getTitle(),doctor.getOther().replace("主治专业：",""),tokenShared.getString("username"),tokenShared.getString("hospital"),new Date().getTime(),"待挂号");
                    new RxTimer().timer(500,number -> {
                        shapeLoadingDialog.dismiss();
                        OrderService.startRequest(this, GsonUtils.toJson(dto));
                        finish();
                    });
                })
                .setNegativeButton("取消", (dialogInterface, i) -> Toast.makeText(ListActivity.this, "已取消", Toast.LENGTH_SHORT).show())
                .create();
        alert.show();
    }

    private void showEscortDialog(Intro staff, int position) {
        AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle("注意")
                .setMessage("你确定要预定"+staff.getTitle()+"护工陪诊吗？")
                .setIcon(R.mipmap.danger)
                .setPositiveButton("确定", (dialogInterface, i) -> {
                    shapeLoadingDialog.show();
                    EscortDto dto = new EscortDto(tokenShared.getLong("id"), (long) position,tokenShared.getString("username"),tokenShared.getLong("escort_time"),tokenShared.getString("escort_address"),staff.getTitle(),Long.valueOf(staff.getOther().replace("电话：","")),"add","待接单");
                    AmqpService.setExchange("olife-reserve-exchange");
                    AmqpService.setQueue("olife-redis-queue");
                    AmqpService.setRountingKey("olife.reserve.redis."+ UUID.randomUUID().toString());
                    AmqpService.startPublish(this,GsonUtils.toJson(dto));
                    new RxTimer().timer(500,number -> {
                        shapeLoadingDialog.dismiss();
                        ARouter.getInstance().build(RoutePath.ORDER.toString()).navigation(this,new LoginCallbackImpl());
                        finish();
                    });
                })
                .setNegativeButton("取消", (dialogInterface, i) -> Toast.makeText(ListActivity.this, "已取消", Toast.LENGTH_SHORT).show())
                .create();
        alert.show();
    }

    private void showChatDialog(String doctor, int position){
        AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle("注意")
                .setMessage("你确定要选"+doctor+"医生进行咨询吗？")
                .setIcon(R.mipmap.success)
                .setPositiveButton("确定", (dialogInterface, i) -> {
                    shapeLoadingDialog.show();
                    new RxTimer().timer(500,number -> {
                        shapeLoadingDialog.dismiss();
                        ARouter.getInstance().build(RoutePath.CHAT.toString()).withLong("id", position).navigation();
                    });
                })
                .setNegativeButton("取消", (dialogInterface, i) -> Toast.makeText(ListActivity.this, "已取消", Toast.LENGTH_SHORT).show())
                .create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
