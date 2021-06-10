package com.example.myapplication.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.daasuu.bl.BubbleLayout;
import com.daasuu.bl.BubblePopupHelper;
import com.example.myapplication.domain.MsgDto;
import com.example.myapplication.service.WsService;
import com.example.myapplication.util.HideUtil;
import com.example.myapplication.util.SharedPreferencesUtils;
import com.example.myapplication.view.layout.ChatBarView;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/olife/chat")
public class ChatActivity extends AppCompatActivity {

    @Autowired
    public long id;

    @BindView(R.id.chatbar)
    ChatBarView chatBarView;

    @BindView(R.id.msg_task)
    LinearLayout task;

    private PopupWindow popupWindow;
    private BubbleLayout bubbleLayout;

    private SharedPreferencesUtils tokenShared;
    private String serverId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        HideUtil.init(this);
        EventBus.getDefault().register(this);
        init();
        initCharView();
        initWebsocket();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEventProgress(MsgDto dto) {
        if (dto.getIsm()) {
            addDoctorViewItem(dto.getMsg());
        } else {
            addUserViewItem(dto.getMsg());
        }
    }

    public void init(){
        bubbleLayout = (BubbleLayout) LayoutInflater.from(this).inflate(R.layout.bubble_layout, null);
        popupWindow = BubblePopupHelper.create(this, bubbleLayout);
        tokenShared = SharedPreferencesUtils.init(this,"oauth");
    }

    private void initWebsocket(){
        WsService.startConnection(this,String.valueOf(tokenShared.getLong("id")));
        serverId = UUID.nameUUIDFromBytes(String.valueOf(id).getBytes()).toString();
        Log.e("医生的通道", serverId);
    }

    public void initCharView(){
        chatBarView.setSendClickListener(view -> {
            Toast.makeText(ChatActivity.this, chatBarView.getMessageText(), Toast.LENGTH_SHORT).show();
            List<String> persons = new ArrayList<>();
            persons.add(String.valueOf(tokenShared.getLong("id")));
            persons.add(String.valueOf(serverId));
            WsService.startSend(this,new MsgDto(persons,chatBarView.getMessageText(),false));
        });

        chatBarView.setOnMicListener(view -> {
            Toast.makeText(ChatActivity.this, "Recording...", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void addUserViewItem(String msg) {
        View bubble = View.inflate(this, R.layout.fragment_bubble, null);
        TextView bubbleText = (TextView) bubble.findViewById(R.id.bubble_text);
        bubbleText.setText(msg); //发送内容
        bubbleText.setTag("user"); //发送人
        task.addView(bubble);
    }

    private void addDoctorViewItem(String msg) {
        View bubble = View.inflate(this, R.layout.fragment_bubble2, null);
        TextView bubbleText = (TextView) bubble.findViewById(R.id.bubble_text);
        bubbleText.setText(msg); //发送内容
        bubbleText.setTag("doctor"); //发送人
        task.addView(bubble);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WsService.startClose(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
