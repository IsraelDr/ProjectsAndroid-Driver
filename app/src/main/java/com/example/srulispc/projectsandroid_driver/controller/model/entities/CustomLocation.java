package com.example.srulispc.projectsandroid_driver.controller.model.entities;


import android.location.Location;

public class CustomLocation extends Location {

    public CustomLocation(){
        super(new Location(""));
    }
   public CustomLocation(String provider){
       super(new Location(provider));
   }
   public CustomLocation(Location l)   {
        super(l);
   }
}
