package com.example.andrewsamir.drromance;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.andrewsamir.drromance.Adapters.Show_Delivery_Adapter;
import com.example.andrewsamir.drromance.Data.data_show_delivery;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class View_Delivery extends AppCompatActivity {
    DataSnapshot signIn;
    TextView name, code, address, mobile, floor, flat;
    ListView listView;
    ArrayList<data_show_delivery> data_show_deliveries;
    Show_Delivery_Adapter show_delivery_adapter;
    RelativeLayout relativeLayout;
    ImageButton whatsapp, call;
    String person_id, mobileNo;
    ImageView imageView;
    Boolean image=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__delivery);
        imageView = (ImageView) findViewById(R.id.see_image);
        name = (TextView) findViewById(R.id.text_name);
        code = (TextView) findViewById(R.id.text_code);
        address = (TextView) findViewById(R.id.text_address);
        mobile = (TextView) findViewById(R.id.text_mobile);
        floor = (TextView) findViewById(R.id.floor);
        flat = (TextView) findViewById(R.id.flat);
        listView = (ListView) findViewById(R.id.listView_show_deliveries);
        relativeLayout = (RelativeLayout) findViewById(R.id.layout_view_delivery);
        data_show_deliveries = new ArrayList<>();
        data_show_deliveries.clear();
        whatsapp = (ImageButton) findViewById(R.id.whatsapp);
        call = (ImageButton) findViewById(R.id.phonecall);
        person_id = getIntent().getStringExtra("person_id");


        final Intent intent = getIntent();

        ArrayList<String> test = new ArrayList<String>();
        test = intent.getStringArrayListExtra("msgs");
        ArrayList<String> imgs = intent.getStringArrayListExtra("images");
        for (String s : test) {
            data_show_deliveries.add(new data_show_delivery(imgs.get(test.indexOf(s)), s));
        }

//        textView.setText(test.get(test.size()-1));
        Firebase firebase = new Firebase("https://romance-pharmacy.firebaseio.com/");
        Query queryRef = firebase.child("SignIn");
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final DataSnapshot signin = dataSnapshot;
                mobileNo = signin.child(person_id).child("Mobile").getValue().toString();
                name.setText(signin.child(person_id).child("Name").getValue().toString());
                code.setText(signin.child(person_id).child("Code").getValue().toString());
                address.setText(signin.child(person_id).child("Address").getValue().toString());
                mobile.setText(signin.child(person_id).child("Mobile").getValue().toString());
                floor.setText(signin.child(person_id).child("Floor").getValue().toString());
                flat.setText(signin.child(person_id).child("Flat").getValue().toString());
                relativeLayout.setVisibility(View.GONE);
                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + mobileNo));
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                    }
                });
                whatsapp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + signin.child(intent.getStringExtra("person_id")).child("Mobile").getValue().toString()));
                        i.setPackage("com.whatsapp");           // so that only Whatsapp reacts and not the chooser
                        startActivity(i);
                    }
                });
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        show_delivery_adapter = new Show_Delivery_Adapter(data_show_deliveries, View_Delivery.this);
        listView.setAdapter(show_delivery_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String photo = data_show_deliveries.get(i).getImage();
                byte[] decodedString = Base64.decode(photo, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageView.setImageBitmap(decodedByte);
                imageView.setVisibility(View.VISIBLE);
                image=true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(image){
            imageView.setVisibility(View.GONE);
            image=false;
        }
        else
        super.onBackPressed();
    }
}
