package com.example.srulispc.projectsandroid_driver.controller.model.datasource;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.srulispc.projectsandroid_driver.controller.model.backend.Ibackend;
import com.example.srulispc.projectsandroid_driver.controller.model.entities.Driver;
import com.example.srulispc.projectsandroid_driver.controller.model.entities.Ride;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireBase implements Ibackend {

    //int counter;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private List<Ride> rideList;
    private Map<String,Ride> ridesMap=new HashMap<String,Ride>();


    public FireBase() {
        database = FirebaseDatabase.getInstance();
        //-------------------------Rides Listener---------------------------
//        myRef = database.getReference("Rides");
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                ridesMap = (HashMap<String,Ride>) dataSnapshot.getValue();
//
//                rideList = new ArrayList<>((ridesMap.values()));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.e("The read failed: " ,databaseError.getMessage());
//            }
//        });
        //-------------------------------------------------------------------
        //firebaseAuth = FirebaseAuth.getInstance();
        //myRef=database.getReference("Rides");

        //-------------Get counter from FireBase-------------
        /*myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                rides.put(dataSnapshot.getKey(),dataSnapshot.getValue(Ride.class));
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
        });*/
       /* myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //counter = dataSnapshot.getValue(Integer.class);
                HashMap temp2= (HashMap) ((HashMap) ( dataSnapshot.getValue())).get((String) (((HashMap)(dataSnapshot.getValue())).keySet()).toArray()[0]);
                Ride temp=new Ride((String) (temp2.get("clientName")),(String) (temp2.get("clientPhoneNumber")),
                        (String) (temp2.get("clientMail")),null,null);
                rides.put(temp.getClientPhoneNumber(),temp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }

    @Override
    public ArrayList<Driver> getalldrivers() {
        return null;
    }

    @Override
    public void addDriver(Driver newDriver) {
        myRef = database.getReference("Drivers");
        myRef.child(""+newDriver.getId()).setValue(newDriver);
    }

    @Override
    public ArrayList<Ride> getallopenrides() {
        myRef = database.getReference("Rides");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ridesMap = (HashMap<String,Ride>) dataSnapshot.getValue();
                rideList = new ArrayList<>((ridesMap.values()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("The read failed: " ,databaseError.getMessage());
            }
        });

        return (ArrayList<Ride>)rideList; }

    @Override
    public ArrayList<Ride> getallclosedrides() {
        return null;
    }

    @Override
    public ArrayList<Ride> getspecificdriverrides() {
        return null;
    }

    @Override
    public ArrayList<Ride> getopenridestospecificdestination() {
        return null;
    }

    @Override
    public ArrayList<Ride> geopenridesinspecificdistance() {
        return null;
    }

    @Override
    public ArrayList<Ride> getridesbydate() {
        return null;
    }

    @Override
    public ArrayList<Ride> getridesbyamount() {
        return null;
    }
}
