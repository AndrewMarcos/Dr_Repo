package com.example.andrewsamir.drromance;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.andrewsamir.drromance.Adapters.Delivery_ADapter;
import com.example.andrewsamir.drromance.Data.data_delivery;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class Delevery extends AppCompatActivity {

    Firebase myFirebase;
    DataSnapshot myChild,myChild1;
    ArrayList<data_delivery> DataArray;
    ListView list;
    ArrayList<String> images,msgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delevery);
        myFirebase = new Firebase("https://romance-pharmacy.firebaseio.com/");
        list=(ListView)findViewById(R.id.listViewDelivery);
        images=new ArrayList<String>();
        msgs=new ArrayList<String>();


    }

    @Override
    protected void onStart() {
        super.onStart();



        DataArray = new ArrayList<>();

        Query queryRef = myFirebase.child("Delivery");
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot snapshot) {
                DataArray.clear();
               // long size = snapshot.getChildrenCount();
                Iterable<DataSnapshot> myChildren = snapshot.getChildren();
                while (myChildren.iterator().hasNext()) {
                    int i = 0;
                    myChild = myChildren.iterator().next();
                    DataArray.add(new data_delivery(myChild.child("PersonID").getValue().toString(),myChild.child("Images"),myChild.child("Messages"),myChild.getKey().toString(),
                            myChild.child("Sender").getValue().toString(),myChild.child("senderCode").getValue().toString(),myChild.child("Time").getValue().toString()));

                    i++;
                }
               Delivery_ADapter delivery_aDapter= new Delivery_ADapter(DataArray, Delevery.this);
                list.setAdapter(delivery_aDapter);
                /*listView_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent go_Details = new Intent(Set_News.this, News_details.class);
                        go_Details.putExtra("pic", snapshot.child((position + 1) + "").child("Picture").getValue().toString());
                        go_Details.putExtra("text", snapshot.child((position + 1) + "").child("Text").getValue().toString());
                        startActivity(go_Details);
                    }
                });
                progressBar.setVisibility(View.GONE);*/
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

      list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

              images.clear();
              msgs.clear();
              Intent intent=new Intent(Delevery.this,View_Delivery.class);
             DataSnapshot dataSnapshot_images= DataArray.get(i).getImages();
              //dataSnapshot.getChildrenCount();
              DataSnapshot dataSnapshot_messages = DataArray.get(i).getMessages();
              Iterable<DataSnapshot> myChildren_images = dataSnapshot_images.getChildren();
              Iterable<DataSnapshot> myChildren_messages = dataSnapshot_messages.getChildren();
              while (myChildren_images.iterator().hasNext()) {
                  int no=0;
                DataSnapshot  myChild_images = myChildren_images.iterator().next();
                  DataSnapshot myChild_messages = myChildren_messages.iterator().next();
                  images.add(myChild_images.getValue().toString());
                  msgs.add(myChild_messages.getValue().toString());
                  no++;
              }
              intent.putStringArrayListExtra("images",images);
              intent.putStringArrayListExtra("msgs",msgs);
              intent.putExtra("person_id",DataArray.get(i).getPerson_id());
              //intent.putExtra("item",DataArray.get(i).getId());
              //Log.e("PersonID",DataArray.get(i).getId()+"");
              startActivity(intent);
          }
      });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final CharSequence[] items = {
                        "Remove"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(Delevery.this);
                builder.setTitle("Choose Action ..");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        if (items[item].equals("Remove")) {

                            Thread t = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    postData(i);
                                }
                            });
                            t.start();
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }
        });
    }
    public void postData(int pos) {
        myFirebase.child("Delivery").child(DataArray.get(pos).getOreder_id()).removeValue();
    }
}
