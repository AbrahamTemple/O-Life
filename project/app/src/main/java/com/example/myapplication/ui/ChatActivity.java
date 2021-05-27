package com.example.myapplication.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.myapplication.util.HideUtil;
import com.example.myapplication.view.layout.ChatBarView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/olife/chat")
public class ChatActivity extends AppCompatActivity {

    @BindView(R.id.chatbar)
    ChatBarView chatBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        HideUtil.init(this);
    }

    public void initCharView(){
        chatBarView.setSendClickListener(view -> {
            Toast.makeText(ChatActivity.this, chatBarView.getMessageText(), Toast.LENGTH_SHORT).show();
        });

        chatBarView.setOnMicListener(view -> {
            Toast.makeText(ChatActivity.this, "Recording...", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

}
