package com.example.srulispc.projectsandroid_driver.controller.Adapters;

import android.content.Context;
import android.content.ContextWrapper;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.srulispc.projectsandroid_driver.R;
import com.example.srulispc.projectsandroid_driver.controller.controller.MainActivity;
import com.example.srulispc.projectsandroid_driver.controller.model.entities.Ride;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class RideAdapter extends RecyclerView.Adapter<RideAdapter.RideViewHolder> {

    private Context context;
    public static ArrayList<Ride> dataList;


    public RideAdapter(ArrayList<Ride> dataList) {
        this.dataList = dataList;
    }

    @Override
    public RideViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ride_cardview, parent, false);
        return new RideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RideViewHolder holder, int position) {
        holder.txtClientName.setText(dataList.get(position).getClientName());

        Location location ;
        String address;

        location = dataList.get(position).getSourceLocation();
        address = locationToAddress(location);
        holder.txtSourceLocation.setText(address);

        location = dataList.get(position).getTargetLocation();
        address= locationToAddress(location);
        holder.txtTargetLocation.setText(address);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class RideViewHolder extends RecyclerView.ViewHolder {

        TextView txtClientName, txtSourceLocation, txtTargetLocation;

        RideViewHolder(View itemView) {
            super(itemView);
            txtClientName = (TextView) itemView.findViewById(R.id.client_name);
            txtSourceLocation = (TextView) itemView.findViewById(R.id.location1);
            txtTargetLocation = (TextView) itemView.findViewById(R.id.location2);
        }
    }

    private String locationToAddress(Location location) {

        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("Current loction address", strReturnedAddress.toString());
            } else {
                Log.w("Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Current loction address", "Cannot get Address!");
        }
        return strAdd;
    }
}
