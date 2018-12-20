package com.example.srulispc.projectsandroid_driver.controller.model.backend;

import com.example.srulispc.projectsandroid_driver.controller.model.entities.Driver;
import com.example.srulispc.projectsandroid_driver.controller.model.entities.Ride;

import java.util.ArrayList;
import java.util.List;

public interface Ibackend {
    ArrayList<Driver> getalldrivers();
    void addDriver(Driver newDriver);
    ArrayList<Ride>getallopenrides();
    ArrayList<Ride>getallclosedrides();
    ArrayList<Ride>getspecificdriverrides();
    ArrayList<Ride>getopenridestospecificdestination();
    ArrayList<Ride>geopenridesinspecificdistance();
    ArrayList<Ride>getridesbydate();
    ArrayList<Ride>getridesbyamount();


}
