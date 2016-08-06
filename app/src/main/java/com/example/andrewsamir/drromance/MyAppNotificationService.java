package com.example.andrewsamir.drromance;

import android.app.Activity;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by MorcosS on 7/12/16.
 */
public class MyAppNotificationService extends IntentService {
    View rootView;
    String deliveryId;
    static Firebase myNotificationFirebase;
    static Firebase newCustomersFirebase;

    public MyAppNotificationService(String name) {
        super("Notification app Service");
    }
    public MyAppNotificationService() {
        super("Notification app Service");
    }
    public void setView(View view){
        view = rootView;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onHandleIntent(intent);
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(final Intent intent) {

        Firebase.setAndroidContext(this);
        final SharedPreferences sharedPref =  getApplicationContext().getSharedPreferences("SharedPreference", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        myNotificationFirebase = new Firebase(getString(R.string.MyFirebase_Database)+"Delivery");
        myNotificationFirebase.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                Firebase myOwnFirebase = new Firebase(getString(R.string.MyFirebase_Database));
                myOwnFirebase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        deliveryId = dataSnapshot.child("DeliveriesNo").getValue().toString();
                        if(!sharedPref.getString("DeliveryID","").equals(deliveryId)) {

                            NotificationManager mNM;
                            mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            // Set the icon, scrolling text and timestamp
                            NotificationCompat.Builder mBuilder =
                                    new NotificationCompat.Builder(getBaseContext())
                                            .setSmallIcon(R.mipmap.ic_launcher)
                                            .setContentTitle("Delveries")
                                            .setContentText("You have recieved a new delivery, Let's Work Hard!").setAutoCancel(true);
                            Intent resultIntent = new Intent(getBaseContext(), Delevery.class);
                            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getBaseContext());
                            stackBuilder.addParentStack(Delevery.class);
                            // Adds the Intent that starts the Activity to the top of the stack
                            stackBuilder.addNextIntent(resultIntent);
                            PendingIntent resultPendingIntent =
                                    stackBuilder.getPendingIntent(
                                            0,
                                            PendingIntent.FLAG_UPDATE_CURRENT
                                    );
                            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            mBuilder.setSound(alarmSound);
                            mBuilder.setContentIntent(resultPendingIntent);
                            int mNotificationId = 001;
                            // Gets an instance of the NotificationManager service
                            NotificationManager mNotifyMgr =
                                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            // Builds the notification and issues it.
                            mNotifyMgr.notify(mNotificationId, mBuilder.build());
                            editor.putString("DeliveryID",deliveryId);
                            editor.commit();

                        }

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        newCustomersFirebase = new Firebase(getString(R.string.MyFirebase_Database)+"New_Customers");
        newCustomersFirebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                NotificationManager mNM;
                mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                // Set the icon, scrolling text and timestamp
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getBaseContext())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("New Customer")
                                .setContentText("A new Customer is regestered ").setAutoCancel(true);
                Intent resultIntent = new Intent(getBaseContext(), Delevery.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getBaseContext());
                stackBuilder.addParentStack(Delevery.class);
                // Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                mBuilder.setSound(alarmSound);
                mBuilder.setContentIntent(resultPendingIntent);
                int mNotificationId = 001;
                // Gets an instance of the NotificationManager service
                NotificationManager mNotifyMgr =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                // Builds the notification and issues it.
                mNotifyMgr.notify(mNotificationId, mBuilder.build());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
