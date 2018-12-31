package com.example.srulispc.projectsandroid_driver.controller.model.backend;


import com.example.srulispc.projectsandroid_driver.controller.model.datasource.FireBase;

public class BackendFactory {
    private static Ibackend Instance;

    public static Ibackend getInstance() {
        if(Instance==null)
            Instance=new FireBase();
        return Instance;
    }
}
