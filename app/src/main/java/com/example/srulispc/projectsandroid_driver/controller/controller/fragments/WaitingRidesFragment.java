package com.example.srulispc.projectsandroid_driver.controller.controller.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.srulispc.projectsandroid_driver.R;
import com.example.srulispc.projectsandroid_driver.controller.Adapters.RideAdapter;
import com.example.srulispc.projectsandroid_driver.controller.controller.MainActivity;
import com.example.srulispc.projectsandroid_driver.controller.model.backend.BackendFactory;
import com.example.srulispc.projectsandroid_driver.controller.model.backend.Ibackend;
import com.example.srulispc.projectsandroid_driver.controller.model.entities.Ride;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class WaitingRidesFragment extends Fragment {

    private Ibackend backend;
    private RecyclerView recyclerView;
    private RideAdapter adapter;
    private ArrayList<Ride> finishedRides = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_waiting_rides,container, false);

        //Find Views
        //final EditText cityFilter = getActivity().findViewById(R.id.filter_city);
        final LinearLayout distanceFilterBody = getActivity().findViewById(R.id.filter_distance_body);
        distanceFilterBody.setVisibility(View.VISIBLE);
        final SeekBar distanceFilter = getActivity().findViewById(R.id.filter_distance);

        //-----------------------Show Available Rides From DataBase---------------------
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        backend = BackendFactory.getInstance();
        backend.listenToRideList(new Ibackend.Action<ArrayList<Ride>> (){

            @Override
            public void onDataChange(ArrayList<Ride> updatedList) {

                if (updatedList!=null) {

                    finishedRides.clear();

                    for (Ride ride : updatedList) {
                        if (ride.getStatus() == Ride.Status.AVAILABLE)
                                finishedRides.add(ride);
                    }

                    //----Check filters----
                    ArrayList<Ride> filteredList = finishedRides;

                    //String cityName = cityFilter.getText().toString();
                    //if (!cityFilter.equals(""))
                    //    filteredList = filterListByCity(finishedRides,cityName);
                    //---------------------

                    adapter = new RideAdapter(filteredList);
                        recyclerView.setAdapter(adapter);
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

        //-----------------------Set City Filter------------------------
        /*cityFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable city) {
                ArrayList<Ride> list = filterListByCity(finishedRides, city.toString());
                adapter.changeList(list);
            }
        });*/
        //-----------------------Set Distance Filter------------------------
        distanceFilter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int distance , boolean b) {

                adapter = new RideAdapter(filterListByDistance(finishedRides, distance));
                recyclerView.setAdapter(adapter);


                String s = distance + "Km";
                ((TextView) getActivity().findViewById(R.id.seekbar_text)).setText(s);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        return view;
    }

    @Override
    public void onDetach() {
        backend.stopListenToRideList();
        super.onDetach();
    }

    private ArrayList<Ride> filterListByCity(ArrayList<Ride> list, String city)
    {
        ArrayList<Ride> filteredList = new ArrayList<>();

        for (Ride ride : list) {

            String rideCityName = findCityName(ride.getSourceLocation());

            if (rideCityName.toLowerCase().contains(city.toString().toLowerCase())) {
                filteredList.add(ride);
            }
        }
        return filteredList;
    }

    private ArrayList<Ride> filterListByDistance(ArrayList<Ride> list, int distance)
    {
        int k=5;
        ArrayList<Ride> filteredList = new ArrayList<>();
        if(MainActivity.myLocation!=null) {
            for (Ride ride : list) {
                Float distance2 = (float)Math.round(MainActivity.myLocation.distanceTo(ride.getSourceLocation()))/1000;
                distance2 = Float.valueOf(new DecimalFormat("##.#").format(distance2).replace(",","."));
                if(distance2<=distance)
                    filteredList.add(ride);
            }
        }
        return filteredList;
    }

    private String findCityName(Location location) {

        String cityName = "";
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {

            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null)
                cityName = addresses.get(0).getLocality();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cityName;
    }
}
