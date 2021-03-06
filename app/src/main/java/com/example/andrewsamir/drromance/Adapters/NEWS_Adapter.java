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

import com.example.andrewsamir.drromance.Data.data_news;
import com.example.andrewsamir.drromance.R;

import java.util.ArrayList;



/**
 * Created by Andrew Samir on 6/7/2016.
 */
public class NEWS_Adapter extends BaseAdapter {

    ArrayList<data_news> list;
    Activity activity;
    LayoutInflater inflater;

    public NEWS_Adapter(ArrayList<data_news> list, Activity activity) {
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

        convertView = inflater.inflate(R.layout.news_listview, null);
        TextView New = (TextView) convertView.findViewById(R.id.textViewNew);
        ImageView im = (ImageView) convertView.findViewById(R.id.imageViewNews);


        data_news dataNews = list.get(position);
        New.setText(dataNews.getText());


        String photo= dataNews.getPic_1();
        byte[] decodedString = Base64.decode(photo, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        im.setImageBitmap(decodedByte);


        return convertView;
    }

}
