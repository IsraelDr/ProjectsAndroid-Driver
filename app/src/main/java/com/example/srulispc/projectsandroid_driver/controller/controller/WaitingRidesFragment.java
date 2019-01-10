package com.example.srulispc.projectsandroid_driver.controller.controller;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.srulispc.projectsandroid_driver.R;
import com.example.srulispc.projectsandroid_driver.controller.Adapters.RideAdapter;
import com.example.srulispc.projectsandroid_driver.controller.model.backend.BackendFactory;
import com.example.srulispc.projectsandroid_driver.controller.model.backend.Ibackend;
import com.example.srulispc.projectsandroid_driver.controller.model.datasource.FireBase;
import com.example.srulispc.projectsandroid_driver.controller.model.entities.Ride;
import com.google.firebase.FirebaseApiNotAvailableException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class WaitingRidesFragment extends android.app.Fragment  {

    private Ibackend backend;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_waiting_rides,container, false);

        //-----------------------Show Available Rides From DataBase---------------------
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        backend = BackendFactory.getInstance();
        backend.listenToRideList(new Ibackend.Action<ArrayList<Ride>> (){

            @Override
            public void onDataChange(ArrayList<Ride> updatedList) {

                if (updatedList!=null) {

                    for(Iterator<Ride> i = updatedList.iterator(); i.hasNext();) {
                        Ride ride = i.next();

                        if (ride.getStatus()!=Ride.Status.AVAILABLE)
                            i.remove();
                    }

                    if (recyclerView.getAdapter() == null)
                        recyclerView.setAdapter(new RideAdapter(updatedList));
                    else
                        //need to change this, so the recycler-view will not rebuild itself..
                        recyclerView.setAdapter(new RideAdapter(updatedList));
                }
            }

            @Override
            public void onFailure(Exception exception) {
                Toast.makeText(getActivity(), "error to get ride list\n" + exception.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onProgress(String status, double percent) {

            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        backend.stopListenToRideList();
        super.onDetach();
    }
}
