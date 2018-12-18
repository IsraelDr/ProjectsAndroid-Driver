package com.example.srulispc.projectsandroid_driver.controller.model.backend;


import com.example.srulispc.projectsandroid_driver.controller.model.datasource.FireBase;

public class BackendFactory {
    private static final Ibackend Instance = new FireBase();

    public static Ibackend getInstance() {
        return Instance;
    }

    private BackendFactory() {
    }
}
