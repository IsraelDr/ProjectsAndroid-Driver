package com.example.srulispc.projectsandroid_driver.controller.model.datasource;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.srulispc.projectsandroid_driver.controller.model.backend.Ibackend;
import com.example.srulispc.projectsandroid_driver.controller.model.entities.CustomLocation;
import com.example.srulispc.projectsandroid_driver.controller.model.entities.Driver;
import com.example.srulispc.projectsandroid_driver.controller.model.entities.Ride;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FireBase implements Ibackend {

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private Map<String,Ride> allRides;
    private Driver me;

    private static ChildEventListener childEventListener;

    public FireBase() {
        database = FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        final String driverMail = firebaseAuth.getCurrentUser().getEmail();
        DatabaseReference DriversRef=database.getReference("Drivers");
        DriversRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Driver newDriver=dataSnapshot.getValue(Driver.class);
                if(newDriver.getMail().equals(driverMail))
                    me=newDriver;
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
        myRef=database.getReference("Rides");
        allRides=new HashMap<String,Ride>();
        allRides = new HashMap<String,Ride>();
    }


    public void listenToRideList(final Action<ArrayList<Ride>> action) {

        if (childEventListener != null) {
            action.onFailure(new Exception("You already listen to the list!"));
            return;
        }
        allRides.clear();

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                allRides.put(dataSnapshot.getKey(),dataSnapshot.getValue(Ride.class));
                action.onDataChange(new ArrayList<Ride>(allRides.values()));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                allRides.put(dataSnapshot.getKey(),dataSnapshot.getValue(Ride.class));
                action.onDataChange(new ArrayList<Ride>(allRides.values()));
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                allRides.remove(dataSnapshot.getKey());
                action.onDataChange(new ArrayList<Ride>(allRides.values()));
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                action.onFailure(databaseError.toException());
            }
        };

        myRef = database.getReference("Rides");
        myRef.addChildEventListener(childEventListener);
    }


    public void stopListenToRideList() {

        if (childEventListener != null) {
            myRef = database.getReference("Rides");
            myRef.removeEventListener(childEventListener);
            childEventListener = null;
        }
    }

    @Override
    public void addDriver(Driver newDriver) {
        myRef = database.getReference("Drivers");
        myRef.child(""+newDriver.getId()).setValue(newDriver);
    }

    @Override
    public void setStatus(String id, Ride.Status s) {
        myRef = database.getReference("Rides");
        myRef.child(id).child("status").setValue(s);
    }

    @Override
    public void setDriverIDInRide(String rideID) {
        myRef = database.getReference("Rides");
        myRef.child(rideID).child("driverID").setValue(me.getId());
    }

    @Override
    public Long getCurrentDriveID() {
        return me.getId();
    }

    @Override
    public String getCurrentDriverName() {
        return me.getFirstName() + me.getLastName();
    }

    @Override
    public  ArrayList<Driver> getAllDrivers() { return null; }

    @Override
    public ArrayList<Ride> getallopenrides() {
        return new ArrayList<Ride>(allRides.values());
    }

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

    @Override
    public void setDriverID(String id, int driveID) {
        myRef = database.getReference("Rides");
        myRef.child(id).child("driverID").setValue(driveID);
    }

    @Override
    public void setmyLocation(String id,CustomLocation mylocation) {
        myRef = database.getReference("Drivers");
        myRef.child(id).child("location").setValue(mylocation);
    }


}
