package com.example.andrewsamir.drromance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class NewCustomerDetails extends AppCompatActivity {



    TextView name,code,address,flat,floor,mobile;
    RelativeLayout relativeLayout;

    String nameS,codeS,addressS,flatS,floorS,mobileS,idS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer_details);

        name=(TextView)findViewById(R.id.text_name_new);
        code=(TextView)findViewById(R.id.text_code_new);
        address=(TextView)findViewById(R.id.text_address_new);
        flat=(TextView)findViewById(R.id.flat_new);
        floor=(TextView)findViewById(R.id.floor_new);
        mobile=(TextView)findViewById(R.id.text_mobile_new);
        relativeLayout=(RelativeLayout)findViewById(R.id.relative_new_customer_details);
        relativeLayout.setVisibility(View.VISIBLE);

        Intent intent=getIntent();
        nameS=intent.getStringExtra("name_new");
        codeS=intent.getStringExtra("code_new");
        idS=intent.getStringExtra("id_new");

        name.setText(nameS);
        code.setText(codeS);


        Firebase firebase = new Firebase("https://romance-pharmacy.firebaseio.com/");
        Query queryRef = firebase.child("SignIn");
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final DataSnapshot signin = dataSnapshot;
                //mobileNo = signin.child(codeS).child("Mobile").getValue().toString();
                name.setText(signin.child(idS).child("Name").getValue().toString());
                code.setText(signin.child(idS).child("Code").getValue().toString());
                address.setText(signin.child(idS).child("Address").getValue().toString());
                mobile.setText(signin.child(idS).child("Mobile").getValue().toString());
                floor.setText(signin.child(idS).child("Floor").getValue().toString());
                flat.setText(signin.child(idS).child("Flat").getValue().toString());
                relativeLayout.setVisibility(View.GONE);
                /*relativeLayout.setVisibility(View.GONE);
                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + mobileNo  ));
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                    }
                });
                whatsapp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + signin.child(intent.getStringExtra("person_id")).child("Mobile").getValue().toString() ));
                        i.setPackage("com.whatsapp");           // so that only Whatsapp reacts and not the chooser
                        startActivity(i);
                    }
                });*/
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
