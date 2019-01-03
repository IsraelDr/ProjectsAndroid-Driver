package com.example.srulispc.projectsandroid_driver.controller.controller;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.example.srulispc.projectsandroid_driver.R;
import com.example.srulispc.projectsandroid_driver.controller.model.backend.BackendFactory;
import com.example.srulispc.projectsandroid_driver.controller.model.backend.Ibackend;
import com.example.srulispc.projectsandroid_driver.controller.model.entities.Ride;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class ReceivedRideService extends Service {
    public Date startservicetime;
    public ReceivedRideService() {

        startservicetime=new Date();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("Rides");
        myRef.addChildEventListener(new ChildEventListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Ride newride=dataSnapshot.getValue(Ride.class);
                if((new Date(newride.getTimestamp())).compareTo(startservicetime)==1) {
                    Notification notification = new NotificationCompat.Builder(getBaseContext(), "aaa")
                            .setSmallIcon(R.drawable.driver_icon)
                            .setContentTitle(newride.getClientName())
                            .setContentText(newride.getClientMail())
                           // .setLargeIcon(newrid)
                           // .setStyle(new NotificationCompat.BigTextStyle()
                            //        .bigText(emailObject.getSubjectAndSnippet()))
                            .build();

                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public int onStartCommand(Intent intent,int d,int k)
    {
        Ibackend backend;
        backend = BackendFactory.getInstance();
        /*backend.getallrides(new Ibackend.Action<List<Ride>>(){

            @Override
            public void onSuccess(List<Ride> obj) {
                //Parcelable recyclerViewState;
                //recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
                int k=5;
                Notification.Builder nBuilder = new Notification.Builder(getBaseContext());
                //nBuilder.setSmallIcon(R.drawable.services);
                nBuilder.setContentTitle("Notification Title");
                nBuilder.setContentText("Notification Content");
                Notification notification = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    notification = nBuilder.build();
                }
                startForeground(1234, notification);
                //adapter.notifyDataSetChanged();
                //recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
            }

            @Override
            public void onFailure(Exception exception) {

            }

            @Override
            public void onProgress(String status, double percent) {

            }
        });*/
        return Service.START_NOT_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}