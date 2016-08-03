package com.example.andrewsamir.drromance;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.andrewsamir.drromance.Adapters.All_Customers_Adapter;
import com.example.andrewsamir.drromance.Data.All_Customers;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class Customers extends AppCompatActivity {

    Firebase myFirebase;
    ArrayList<All_Customers> DataArray;

    ListView listView;
    DataSnapshot myChild;
    All_Customers_Adapter all_customers_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        listView=(ListView)findViewById(R.id.listView_all_customers);

        myFirebase = new Firebase("https://romance-pharmacy.firebaseio.com/");

    }

    @Override
    protected void onStart() {
        super.onStart();
        DataArray = new ArrayList<>();

        Query queryRef = myFirebase.child("SignIn");
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot snapshot) {
                DataArray.clear();
                //long size = snapshot.getChildrenCount();
                Iterable<DataSnapshot> myChildren = snapshot.getChildren();
                while (myChildren.iterator().hasNext()) {
                    int i = 0;
                    myChild  = myChildren.iterator().next();
                    try {
                        DataArray.add(new All_Customers(myChild.child("Name").getValue().toString(), myChild.child("Code").getValue().toString(),myChild.getKey()));
                    } catch (Exception e) {
                    }
                    i++;
                }
                all_customers_adapter = new All_Customers_Adapter(DataArray, Customers.this);
                listView.setAdapter(all_customers_adapter);
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

    }
}
