package com.example.andrewsamir.drromance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import pharmacy.morcos.andrew.drpharmacy.Adapters.All_Customers_Adapter;
import pharmacy.morcos.andrew.drpharmacy.Data.All_Customers;

/**
 * Created by Andrew Samir on 7/23/2016.
 */
public class New_Customers extends AppCompatActivity {

    Firebase myFirebase;
    ArrayList<All_Customers> DataArray;

    ListView listView;
    DataSnapshot myChild;
    All_Customers_Adapter all_customers_adapter;

    RelativeLayout relativeLayout;

    Button all_customers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_customers);
        myFirebase = new Firebase("https://romance-pharmacy.firebaseio.com/");
        relativeLayout = (RelativeLayout) findViewById(R.id.relative_new_customer);
        relativeLayout.setVisibility(View.VISIBLE);
        listView = (ListView) findViewById(R.id.listView_new_customers);
        all_customers = (Button) findViewById(R.id.button_all_customers);
        all_customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(New_Customers.this, Customers.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(New_Customers.this, NewCustomerDetails.class);
                intent.putExtra("name_new", DataArray.get(position).getName());
                intent.putExtra("code_new", DataArray.get(position).getCode());
                intent.putExtra("id_new", DataArray.get(position).getId());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        DataArray = new ArrayList<>();

        Query queryRef = myFirebase.child("New_Customers");
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot snapshot) {
                DataArray.clear();
                //long size = snapshot.getChildrenCount();
                Iterable<DataSnapshot> myChildren = snapshot.getChildren();
                while (myChildren.iterator().hasNext()) {
                    int i = 0;
                    myChild = myChildren.iterator().next();
                    try {
                        DataArray.add(new All_Customers(myChild.child("Name").getValue().toString(), myChild.child("Code").getValue().toString(), myChild.getKey()));
                    } catch (Exception e) {
                    }
                    i++;
                }
                all_customers_adapter = new All_Customers_Adapter(DataArray, New_Customers.this);
                listView.setAdapter(all_customers_adapter);
                relativeLayout.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

    }
}
