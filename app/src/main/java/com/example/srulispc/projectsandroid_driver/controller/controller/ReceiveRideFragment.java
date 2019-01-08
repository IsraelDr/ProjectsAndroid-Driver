package com.example.srulispc.projectsandroid_driver.controller.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.srulispc.projectsandroid_driver.R;
import com.example.srulispc.projectsandroid_driver.controller.model.backend.BackendFactory;
import com.example.srulispc.projectsandroid_driver.controller.model.backend.Ibackend;
import com.example.srulispc.projectsandroid_driver.controller.model.entities.Ride;
import com.rilixtech.materialfancybutton.MaterialFancyButton;


public class ReceiveRideFragment extends android.app.Fragment {

    public static ReceiveRideFragment newInstance(Ride ride) {

        ReceiveRideFragment fragment = new ReceiveRideFragment();

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
//        return inflater.inflate(R.layout.fragment_receive_ride, container, false);
        View view =  inflater.inflate(R.layout.fragment_receive_ride,container, false);

        String name= getArguments().getString("clientName", "");
        String phone = getArguments().getString("clientPhoneNumber", "");
        String mail= getArguments().getString("clientMail", "");
        final String id= getArguments().getString("ridekey", "");
        ((TextView) view.findViewById(R.id.client_name)).setText(name);
        ((TextView) view.findViewById(R.id.client_phoneNumber)).setText(phone);
        ((TextView) view.findViewById(R.id.client_mail)).setText(mail);
        MaterialFancyButton catchride=view.findViewById(R.id.receiveRide);
        catchride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ibackend backend = BackendFactory.getInstance();
                backend.setstatus(id, Ride.Status.BUSY);
            }
        });

        return view;
    }

}
