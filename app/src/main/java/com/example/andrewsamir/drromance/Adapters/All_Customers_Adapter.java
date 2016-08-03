package com.example.andrewsamir.drromance.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.andrewsamir.drromance.Data.All_Customers;
import com.example.andrewsamir.drromance.R;

import java.util.ArrayList;


/**
 * Created by Andrew Samir on 7/23/2016.
 */
public class All_Customers_Adapter extends BaseAdapter {

    ArrayList<All_Customers> list;
    Activity activity;
    LayoutInflater inflater;

    public All_Customers_Adapter(ArrayList<All_Customers> list, Activity activity) {
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
        convertView = inflater.inflate(R.layout.customers_item, null);
        TextView name = (TextView) convertView.findViewById(R.id.textView_customer_name);
        TextView code = (TextView) convertView.findViewById(R.id.textView_customer_code);


        All_Customers all_customers = list.get(position);
        name.setText(all_customers.getName());
        code.setText(all_customers.getCode());




        return convertView;
    }
}
