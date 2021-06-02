package com.example.myapplication.view.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.util.HideUtil;
import com.example.myapplication.view.layout.MyPowerMenu;
import com.yalantis.jellytoolbar.listener.JellyListener;
import com.yalantis.jellytoolbar.widget.JellyToolbar;

public class JellyToolFragment extends Fragment {

    private JellyToolbar toolbar;
    private AppCompatEditText editText;
    private MyPowerMenu myPowerMenu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jelly, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.getToolbar().setNavigationIcon(R.drawable.ic_menu);
        toolbar.setJellyListener(jellyListener);
        toolbar.getToolbar().setPadding(0, getStatusBarHeight(), 0, 0);

        editText = (AppCompatEditText) LayoutInflater.from(getContext()).inflate(R.layout.edit_text, null);
        editText.setBackgroundResource(R.color.colorTransparent);
        toolbar.setContentView(editText);

        myPowerMenu = new MyPowerMenu(getContext(),this);
        myPowerMenu.init();
        toolbar.getToolbar().setNavigationOnClickListener(v -> onIcon(v));
        return view;
    }

    private JellyListener jellyListener = new JellyListener() {
        @Override
        public void onCancelIconClicked() {
            if (TextUtils.isEmpty(editText.getText())) {
                toolbar.collapse();
            } else {
                editText.getText().clear();
            }
        }
    };

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void onHamburger(View view) {
        myPowerMenu.onHamburger(view);
    }

    public void onProfile(View view) {
        myPowerMenu.onProfile(view);
    }

    public void onIcon(View view){
        myPowerMenu.onIcon(view);
    }

}
