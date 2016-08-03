package com.example.andrewsamir.drromance.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.andrewsamir.drromance.Data.data_delivery;
import com.example.andrewsamir.drromance.R;

import java.util.ArrayList;

/**
 * Created by Andrew Samir on 6/28/2016.
 */
public class Delivery_ADapter extends BaseAdapter {

    ArrayList<data_delivery> list;
    Activity activity;
    LayoutInflater inflater;

    public Delivery_ADapter(ArrayList<data_delivery> list, Activity activity) {
        this.list = list;
        this.activity = activity;
        inflater = activity.getLayoutInflater();
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.delivery_item, null);
        TextView name = (TextView) convertView.findViewById(R.id.textView_name);
        TextView code = (TextView) convertView.findViewById(R.id.textView_code);
        TextView time = (TextView) convertView.findViewById(R.id.textView_time);
        data_delivery dataNews = list.get(position);
        name.setText(list.get(position).getName());
        code.setText(list.get(position).getCode());
        time.setText(list.get(position).getTime());
        return convertView;
    }
}
