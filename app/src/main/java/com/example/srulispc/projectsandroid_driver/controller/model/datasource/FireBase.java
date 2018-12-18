package com.example.srulispc.projectsandroid_driver.controller.model.datasource;

import com.example.srulispc.projectsandroid_driver.controller.model.backend.Ibackend;
import com.example.srulispc.projectsandroid_driver.controller.model.entities.Driver;
import com.example.srulispc.projectsandroid_driver.controller.model.entities.Ride;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class FireBase implements Ibackend {

    //int counter;
    FirebaseDatabase database;
    DatabaseReference myRef;
    HashMap<String,Ride> rides=new HashMap<String,Ride>();


    public FireBase()
    {
        //firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
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
    public List<Driver> getalldrivers() {
        return null;
    }

    @Override
    public void addDriver(Driver newDriver) {
        myRef = database.getReference("Drivers");
        myRef.child(""+newDriver.getId()).setValue(newDriver);
    }

    @Override
    public List<Ride> getallopenrides() {
        return null;
    }

    @Override
    public List<Ride> getallclosedrides() {
        return null;
    }

    @Override
    public List<Ride> getspecificdriverrides() {
        return null;
    }

    @Override
    public List<Ride> getopenridestospecificdestination() {
        return null;
    }

    @Override
    public List<Ride> geopenridesinspecificdistance() {
        return null;
    }

    @Override
    public List<Ride> getridesbydate() {
        return null;
    }

    @Override
    public List<Ride> getridesbyamount() {
        return null;
    }
}
