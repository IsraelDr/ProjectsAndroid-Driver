package com.example.srulispc.projectsandroid_driver.controller.model.backend;

import com.example.srulispc.projectsandroid_driver.controller.model.entities.Driver;
import com.example.srulispc.projectsandroid_driver.controller.model.entities.Ride;

import java.util.ArrayList;
import java.util.List;

public interface Ibackend {
    public interface Action<T>{
        void onSuccess(T obj);
        void onFailure(Exception exception);
        void onProgress(String status,double percent);
    }

    void getallrides(Action<List<Ride>> action);
    ArrayList<Driver> getalldrivers();
    void addDriver(Driver newDriver);
    ArrayList<Ride>getallopenrides();
    ArrayList<Ride>getallclosedrides();
    ArrayList<Ride>getspecificdriverrides();
    ArrayList<Ride>getopenridestospecificdestination();
    ArrayList<Ride>geopenridesinspecificdistance();
    ArrayList<Ride>getridesbydate();
    ArrayList<Ride>getridesbyamount();
    void setstatus(String id,Ride.Status s);


}
