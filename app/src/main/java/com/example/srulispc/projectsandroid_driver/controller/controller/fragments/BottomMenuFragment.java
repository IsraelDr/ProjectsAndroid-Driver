package com.example.srulispc.projectsandroid_driver.controller.controller.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.srulispc.projectsandroid_driver.R;
import com.example.srulispc.projectsandroid_driver.controller.model.entities.Ride;

public class BottomMenuFragment extends Fragment implements View.OnClickListener {

    public static BottomMenuFragment newInstance(Ride ride) {

        BottomMenuFragment fragment = new BottomMenuFragment();
        Bundle args = new Bundle();
        args.putString("clientName", ride.getClientName());
        args.putString("clientPhoneNumber", ride.getClientPhoneNumber());
        args.putString("clientMail", ride.getClientMail());
        args.putString("ridekey", ride.getRidekey());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_menu, container, false);

        //----------------Set the default fragment---------------------
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        Fragment fragment = new ReceiveRideFragment();
        ft.replace(R.id.fragment_containter, fragment, "receiveRideFragment").commit();

//        findViewById(R.id.nav_availableRides).setBackgroundColor(android.graphics.Color.rgb(61,58,58));

        return view;
    }


    @Override
    public void onClick(View view) {

//        view.setBackgroundColor(android.graphics.Color.rgb(61,58,58));
        FragmentManager fm = getChildFragmentManager();
        Fragment fragment;

        switch (view.getId()) {

            case R.id.tab1:

//                findViewById(R.id.nav_myRides).setBackgroundColor(android.graphics.Color.rgb(66,66,66));
//                findViewById(R.id.nav_exit).setBackgroundColor(android.graphics.Color.rgb(66,66,66));

                fragment = fm.findFragmentByTag("notifyPassengerFragment");
                if (fragment == null) {
                    FragmentTransaction ft = fm.beginTransaction();
                    fragment = new NotifyPassengerFragment();
                    ft.replace(R.id.fragment_containter, fragment, "notifyPassengerFragment").commit();
                }
                break;


            case R.id.tab2:

//                findViewById(R.id.nav_availableRides).setBackgroundColor(android.graphics.Color.rgb(66,66,66));
//                findViewById(R.id.nav_exit).setBackgroundColor(android.graphics.Color.rgb(66,66,66));

                fragment = fm.findFragmentByTag("receiveRideFragment");
                if (fragment == null) {
                    FragmentTransaction ft = fm.beginTransaction();
                    fragment = new MyRidesFragment();
                    ft.replace(R.id.fragment_containter, fragment, "myRidesFragment").commit();
                }
                break;
        }
    }
}
