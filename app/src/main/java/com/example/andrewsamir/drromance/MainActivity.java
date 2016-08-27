package com.example.andrewsamir.drromance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    public static Firebase myFirebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        System.out.println("Registration.onTokenRefresh TOKEN: " + FirebaseInstanceId.getInstance().getToken() );
        FirebaseMessaging.getInstance().subscribeToTopic("delivery");
        FirebaseMessaging.getInstance().subscribeToTopic("newCustomers");

        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://romance-pharmacy.firebaseio.com/");

        Button button_Set_News = (Button) findViewById(R.id.buttonSetNews);
        Button button_News = (Button) findViewById(R.id.buttonNews);
        Button button_Delevery = (Button) findViewById(R.id.buttonDelevery);

        button_Set_News.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_set_news = new Intent(MainActivity.this, Set_News.class);
                startActivity(go_set_news);
            }
        });

        button_News.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_set_news = new Intent(MainActivity.this, New_Customers.class);
                startActivity(go_set_news);
            }
        });

        button_Delevery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_delivery = new Intent(MainActivity.this, Delevery.class);
                startActivity(go_delivery);
            }
        });
    }


}
