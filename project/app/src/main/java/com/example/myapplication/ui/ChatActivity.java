package com.example.myapplication.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.daasuu.bl.BubbleLayout;
import com.daasuu.bl.BubblePopupHelper;
import com.example.myapplication.util.HideUtil;
import com.example.myapplication.view.layout.ChatBarView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

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
            int[] location = new int[2];
            task.getLocationInWindow(location);
            popupWindow.showAtLocation(task, Gravity.NO_GRAVITY, location[0], view.getHeight() + location[1]);
//            popupWindow.showAsDropDown(task, location[0], view.getHeight() + location[1]);
        });

        chatBarView.setOnMicListener(view -> {
            Toast.makeText(ChatActivity.this, "Recording...", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

}
