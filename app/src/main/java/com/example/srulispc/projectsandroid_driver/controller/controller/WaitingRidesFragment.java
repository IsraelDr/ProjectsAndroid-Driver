package com.example.srulispc.projectsandroid_driver.controller.controller;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.srulispc.projectsandroid_driver.R;
import com.example.srulispc.projectsandroid_driver.controller.Adapters.RideAdapter;
import com.example.srulispc.projectsandroid_driver.controller.model.backend.BackendFactory;
import com.example.srulispc.projectsandroid_driver.controller.model.backend.Ibackend;
import com.example.srulispc.projectsandroid_driver.controller.model.entities.Ride;

import java.util.ArrayList;
import java.util.List;


public class WaitingRidesFragment extends android.app.Fragment  {

    private Ibackend backend;

    private RecyclerView recyclerView;
    private RideAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_waiting_rides,container, false);

        //-----------------------Show Available Rides From DataBase---------------------
        backend = BackendFactory.getInstance();
        backend.getallrides(new Ibackend.Action<List<Ride>>(){

            @Override
            public void onSuccess(List<Ride> list) {
                //Parcelable recyclerViewState;
                //recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();

                recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view1);
                adapter = new RideAdapter((ArrayList<Ride>) list);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                if (list!=null)
                    recyclerView.setAdapter(adapter);

                //adapter.notifyDataSetChanged();
                //recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
            }

            @Override
            public void onFailure(Exception exception) {

            }

            @Override
            public void onProgress(String status, double percent) {

            }
        });

        return view;
    }

}
