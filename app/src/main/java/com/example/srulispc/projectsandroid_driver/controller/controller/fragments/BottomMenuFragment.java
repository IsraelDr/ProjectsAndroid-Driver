package com.example.srulispc.projectsandroid_driver.controller.controller.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.srulispc.projectsandroid_driver.R;
import com.example.srulispc.projectsandroid_driver.controller.Adapters.SectionsPageAdapter;
import com.example.srulispc.projectsandroid_driver.controller.controller.MainActivity;
import com.example.srulispc.projectsandroid_driver.controller.model.entities.Ride;

public class BottomMenuFragment extends Fragment {

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
        View view =inflater.inflate(R.layout.fragment_bottom_menu, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        ViewPager viewPager= view.findViewById(R.id.viewpager);

        // Set up the ViewPager with the sections adapter.
        SectionsPageAdapter adapter = new SectionsPageAdapter(((MainActivity) getActivity()).getSupportFragmentManager());
        adapter.addFragment(new ReceiveRideFragment(), getString(R.string.receiveRide));
        adapter.addFragment(new NotifyPassengerFragment(), getString(R.string.notifypassenger));
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        return view;
    }


}
