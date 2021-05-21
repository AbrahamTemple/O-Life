package com.example.myapplication.view.layout;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;

import com.example.myapplication.util.PowerMenuUtils;
import com.skydoves.powermenu.OnDismissedListener;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;

public class MyPowerMenu {

    private Context context;
    private LifecycleOwner lifecycleOwner;
    private PowerMenu hamburgerMenu;
    private PowerMenu profileMenu;
    private PowerMenu iconMenu;

    public MyPowerMenu(Context context, LifecycleOwner lifecycleOwner) {
        this.context = context;
        this.lifecycleOwner = lifecycleOwner;
    }

    public void init(){
        hamburgerMenu = PowerMenuUtils.getHamburgerPowerMenu(context, lifecycleOwner, onHamburgerItemClickListener, onHamburgerMenuDismissedListener);
        profileMenu = PowerMenuUtils.getProfilePowerMenu(context, lifecycleOwner, onProfileItemClickListener);
        iconMenu = PowerMenuUtils.getIconPowerMenu(context,lifecycleOwner,onIconMenuItemClickListener);
    }

    private final OnMenuItemClickListener<PowerMenuItem> onHamburgerItemClickListener =
            new OnMenuItemClickListener<PowerMenuItem>() {
                @Override
                public void onItemClick(int position, PowerMenuItem item) {
                    Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
                    hamburgerMenu.setSelectedPosition(position);
                }
            };
    private final OnDismissedListener onHamburgerMenuDismissedListener = () -> Log.d("Test", "onDismissed hamburger menu");
    private final OnMenuItemClickListener<PowerMenuItem> onProfileItemClickListener =
            new OnMenuItemClickListener<PowerMenuItem>() {
                @Override
                public void onItemClick(int position, PowerMenuItem item) {
                    Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
                    profileMenu.dismiss();
                }
    };

    private final OnMenuItemClickListener<PowerMenuItem> onIconMenuItemClickListener =
            new OnMenuItemClickListener<PowerMenuItem>() {
                @Override
                public void onItemClick(int position, PowerMenuItem item) {
                    Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
                    iconMenu.dismiss();
                }
    };

    public void onHamburger(View view) {
        if (hamburgerMenu.isShowing()) {
            hamburgerMenu.dismiss();
            return;
        }
        hamburgerMenu.showAsDropDown(view);
    }

    public void onProfile(View view) {
        profileMenu.showAsDropDown(view, -370, 0);
    }

    public void onIcon(View view) {
        if (iconMenu.isShowing()) {
            iconMenu.dismiss();
            return;
        }
        iconMenu.showAsDropDown(view);
    }
}
