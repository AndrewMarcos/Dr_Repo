package com.example.andrewsamir.drromance;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import pharmacy.morcos.andrew.drpharmacy.Adapters.NEWS_Adapter;
import pharmacy.morcos.andrew.drpharmacy.Data.data_news;


public class Set_News extends AppCompatActivity {

    Resources res;
    ArrayList<data_news> DataArray;
    NEWS_Adapter news_adapter;
    ListView listView_news;
    ProgressBar progressBar;
    String url;
    RequestQueue queue;
    ;

    Firebase myFirebase;
    DataSnapshot myChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_news_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        res = getResources();
        setSupportActionBar(toolbar);
        myFirebase = new Firebase("https://romance-pharmacy.firebaseio.com/");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goNews = new Intent(Set_News.this, NEWS.class);
               // goNews.putExtra("MaxID", max_int);
                startActivity(goNews);

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listView_news = (ListView) findViewById(R.id.listView_NEWS);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_NEWS);


        listView_news.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final CharSequence[] items = {
                        "Remove"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(Set_News.this);
                builder.setTitle("Choose Action ..");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        if (items[item].equals("Remove")) {

                            Thread t = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    postData(position);
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


    @Override
    protected void onStart() {
        super.onStart();

        DataArray = new ArrayList<>();

        Query queryRef = myFirebase.child("News");
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot snapshot) {
                DataArray.clear();
                long size = snapshot.getChildrenCount();
                Iterable<DataSnapshot> myChildren = snapshot.getChildren();
                while (myChildren.iterator().hasNext()) {
                    int i = 0;
                   myChild  = myChildren.iterator().next();
                    try {
                        DataArray.add(new data_news(myChild.child("Picture").getValue().toString(), myChild.child("Text").getValue().toString(),myChild.getKey()));
                    } catch (Exception e) {
                    }
                    i++;
                }
                news_adapter = new NEWS_Adapter(DataArray, Set_News.this);
                listView_news.setAdapter(news_adapter);
                listView_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent go_Details = new Intent(Set_News.this, News_details.class);
                        go_Details.putExtra("pic", snapshot.child((DataArray.get(position).getNews_id())).child("Picture").getValue().toString());
                        go_Details.putExtra("text", snapshot.child((DataArray.get(position).getNews_id())).child("Text").getValue().toString());
                        startActivity(go_Details);
                    }
                });
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

    }

    public void postData(int pos) {
        myFirebase.child("News").child(DataArray.get(pos).getNews_id()).removeValue();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
