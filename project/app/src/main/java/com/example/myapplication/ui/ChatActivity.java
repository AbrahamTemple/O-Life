package com.example.myapplication.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.daasuu.bl.BubbleLayout;
import com.daasuu.bl.BubblePopupHelper;
import com.example.myapplication.util.HideUtil;
import com.example.myapplication.view.layout.ChatBarView;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/olife/chat")
public class ChatActivity extends AppCompatActivity {

    @BindView(R.id.chatbar)
    ChatBarView chatBarView;

    @BindView(R.id.msg_task)
    LinearLayout task;

    private PopupWindow popupWindow;
    private BubbleLayout bubbleLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        HideUtil.init(this);
        init();
        initCharView();
    }

    public void init(){
        bubbleLayout = (BubbleLayout) LayoutInflater.from(this).inflate(R.layout.bubble_layout, null);
        popupWindow = BubblePopupHelper.create(this, bubbleLayout);
    }

    public void initCharView(){
        chatBarView.setSendClickListener(view -> {
            Toast.makeText(ChatActivity.this, chatBarView.getMessageText(), Toast.LENGTH_SHORT).show();
            int rand = new Random().nextBoolean()?1:0;
            if (rand == 0) {
                addUserViewItem(chatBarView.getMessageText());
            } else {
                addDocterViewItem(chatBarView.getMessageText());
            }
//            int[] location = new int[2];
//            task.getLocationInWindow(location);
//            popupWindow.showAtLocation(task, Gravity.NO_GRAVITY, location[0], view.getHeight() + location[1]);
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

    private void addDocterViewItem(String msg) {
        View bubble = View.inflate(this, R.layout.fragment_bubble2, null);
        TextView bubbleText = (TextView) bubble.findViewById(R.id.bubble_text);
        bubbleText.setText(msg); //发送内容
        bubbleText.setTag("doctor"); //发送人
        task.addView(bubble);
    }

}
