package com.example.myapplication.view.model;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.cleveroad.loopbar.adapter.ICategoryItem;
import com.cleveroad.loopbar.model.CategoryItem;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class LoopBarItemsFactory {
    private LoopBarItemsFactory(){}

    public static List<ICategoryItem> getCategoryItems(Context context) {
        List<ICategoryItem> items = new ArrayList<>();
        items.add(new CategoryItem(ContextCompat.getDrawable(context, R.drawable.enls_ic_account_balance), "主页"));
        items.add(new CategoryItem(ContextCompat.getDrawable(context, R.drawable.ic_call_selected), "通话"));
        items.add(new CategoryItem(ContextCompat.getDrawable(context, R.drawable.enls_ic_alarm), "预约"));
        items.add(new CategoryItem(ContextCompat.getDrawable(context, R.drawable.ic_event_selected), "订单"));
        items.add(new CategoryItem(ContextCompat.getDrawable(context, R.drawable.ic_account_selected), "用户"));
        items.add(new CategoryItem(ContextCompat.getDrawable(context, R.drawable.enls_vector_moon_white_24dp), "晚班"));
        return items;
    }
}
