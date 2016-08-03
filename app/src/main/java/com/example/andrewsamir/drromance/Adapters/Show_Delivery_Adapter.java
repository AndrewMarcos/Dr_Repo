package com.example.andrewsamir.drromance.Adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pharmacy.morcos.andrew.drpharmacy.Data.data_show_delivery;
import pharmacy.morcos.andrew.drpharmacy.R;

/**
 * Created by MorcosS on 7/13/16.
 */
public class Show_Delivery_Adapter extends BaseAdapter {

    ArrayList<data_show_delivery> list;
    Activity activity;
    LayoutInflater inflater;

    public Show_Delivery_Adapter(ArrayList<data_show_delivery> list, Activity activity) {
        this.list = list;
        this.activity = activity;
        inflater = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        convertView = inflater.inflate(R.layout.show_delivery, null);
        TextView msg = (TextView) convertView.findViewById(R.id.text_msg_delivery);
        final ImageView img=(ImageView)convertView.findViewById(R.id.imageView_delivery);


        data_show_delivery data_delivery = list.get(i);
        msg.setText(data_delivery.getMsg());
        //name.setText("Order No. "+(position+1));

        String photo=data_delivery.getImage();
        byte[] decodedString = Base64.decode(photo, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        img.setImageBitmap(decodedByte);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return convertView;
    }
}
