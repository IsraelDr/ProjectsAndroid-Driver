package com.example.srulispc.projectsandroid_driver.controller.controller.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.srulispc.projectsandroid_driver.R;
import com.example.srulispc.projectsandroid_driver.controller.model.entities.Ride;

public class NotifyPassengerFragment extends Fragment {

    public static NotifyPassengerFragment newInstance(Ride ride) {

        NotifyPassengerFragment fragment = new NotifyPassengerFragment();

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notify_passenger, container, false);




        return view;
    }
}
