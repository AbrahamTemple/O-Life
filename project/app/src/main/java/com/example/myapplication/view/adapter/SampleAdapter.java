package com.example.myapplication.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.R;

import java.util.List;
import java.util.Map;

public class SampleAdapter extends ArrayAdapter<Map<String, Integer>> {
    public static final String KEY_NAME = "name";
    public static final String KEY_COLOR = "color";

    private final LayoutInflater mInflater;
    private final List<Map<String, Integer>> mData;

    public SampleAdapter(Context context, int layoutResourceId, List<Map<String, Integer>> data) {
        super(context, layoutResourceId, data);
        mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
            viewHolder.textViewName = (TextView) convertView.findViewById(R.id.text_view_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textViewName.setText(mData.get(position).get(KEY_NAME));
        convertView.setBackgroundResource(mData.get(position).get(KEY_COLOR));

        return convertView;
    }

    class ViewHolder {
        TextView textViewName;
    }

}

