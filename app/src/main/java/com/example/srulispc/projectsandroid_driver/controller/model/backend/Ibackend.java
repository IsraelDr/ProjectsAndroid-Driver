package com.example.srulispc.projectsandroid_driver.controller.model.backend;

import com.example.srulispc.projectsandroid_driver.controller.model.entities.Driver;
import com.example.srulispc.projectsandroid_driver.controller.model.entities.Ride;

import java.util.List;

public interface Ibackend {
    List<Driver> getalldrivers();
    void addDriver(Driver newDriver);
    List<Ride> getallopenrides();
    List<Ride> getallclosedrides();
    List<Ride> getspecificdriverrides();
    List<Ride> getopenridestospecificdestination();
    List<Ride> geopenridesinspecificdistance();
    List<Ride> getridesbydate();
    List<Ride> getridesbyamount();


}
