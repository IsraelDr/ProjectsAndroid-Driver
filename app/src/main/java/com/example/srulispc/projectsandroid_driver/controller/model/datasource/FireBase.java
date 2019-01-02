package com.example.srulispc.projectsandroid_driver.controller.model.datasource;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.srulispc.projectsandroid_driver.controller.model.backend.Ibackend;
import com.example.srulispc.projectsandroid_driver.controller.model.entities.Driver;
import com.example.srulispc.projectsandroid_driver.controller.model.entities.Ride;
import com.google.firebase.database.ChildEventListener;
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

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private Map<String,Ride> ridesMap;

    public FireBase() {database = FirebaseDatabase.getInstance();}

    @Override
    public void getallrides(final Action<List<Ride>> action) {
        myRef=database.getReference("Rides");
        ridesMap=new HashMap<String,Ride>();
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ridesMap.put(dataSnapshot.getKey(),dataSnapshot.getValue(Ride.class));
                action.onSuccess(new ArrayList<Ride>(ridesMap.values()));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ridesMap.put(dataSnapshot.getKey(),dataSnapshot.getValue(Ride.class));
                action.onSuccess(new ArrayList<Ride>(ridesMap.values()));
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
    public void addDriver(Driver newDriver) {
        myRef = database.getReference("Drivers");
        myRef.child(""+newDriver.getId()).setValue(newDriver);
    }

    @Override
    public  ArrayList<Driver> getalldrivers() {return null;}

    @Override
    public ArrayList<Ride> getallopenrides() {
        return new ArrayList<Ride>(ridesMap.values()); }

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
