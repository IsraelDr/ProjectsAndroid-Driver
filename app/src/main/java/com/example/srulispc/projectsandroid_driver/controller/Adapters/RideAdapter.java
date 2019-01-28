package com.example.srulispc.projectsandroid_driver.controller.Adapters;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.srulispc.projectsandroid_driver.R;
import com.example.srulispc.projectsandroid_driver.controller.controller.MainActivity;
import com.example.srulispc.projectsandroid_driver.controller.controller.fragments.BottomMenuFragment;
import com.example.srulispc.projectsandroid_driver.controller.controller.fragments.NotifyPassengerFragment;
import com.example.srulispc.projectsandroid_driver.controller.controller.fragments.ReceiveRideFragment;
import com.example.srulispc.projectsandroid_driver.controller.model.entities.Ride;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class RideAdapter extends RecyclerView.Adapter<RideAdapter.RideViewHolder> {

    private Context context;
    private ArrayList<Ride> dataList;

    public RideAdapter(ArrayList<Ride> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ride_cardview, parent, false);
        return new RideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RideViewHolder holder, final int position) {
        holder.txtClientName.setText(dataList.get(position).getClientName());

        Location location1,location2 ;
        String address;

        location1 = dataList.get(position).getSourceLocation();
        address = locationToAddress(location1);
        holder.txtSourceLocation.setText(address);

        location2 = dataList.get(position).getTargetLocation();
        address= locationToAddress(location2);
        holder.txtTargetLocation.setText(address);

        if(MainActivity.myLocation!=null) {
            Float distance = (float)Math.round(MainActivity.myLocation.distanceTo(location1))/1000;
            distance = Float.parseFloat(new DecimalFormat("##.#").format(distance));
            String s = distance + " " + context.getString(R.string.km);
            holder.distance.setText(s);
        }

        holder.cardViewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Ride currentRide = dataList.get(holder.getAdapterPosition());

                //Vibrate
                Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                if (v != null) {v.vibrate(10);}

                //Load Fragment
                FragmentManager fm = ((MainActivity) context).getSupportFragmentManager();
                FragmentTransaction ft =  fm.beginTransaction();
                Fragment fragment = ReceiveRideFragment.newInstance(currentRide);
                ft.replace(R.id.fragment_holder2, fragment,"bottomMenuFragment");
                ft.commit();

                //Reduce the RecyclerView
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        2.0f
                );
                FrameLayout fragmentHolder = ((LinearLayout)view.getParent().getParent().getParent().getParent()).findViewById(R.id.fragment_holder2);
                fragmentHolder.setLayoutParams(param);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class RideViewHolder extends RecyclerView.ViewHolder {

        TextView txtClientName, txtSourceLocation, txtTargetLocation,distance;
        CardView cardViewLayout;

        RideViewHolder(View itemView) {
            super(itemView);
            txtClientName = (TextView) itemView.findViewById(R.id.client_name);
            txtSourceLocation = (TextView) itemView.findViewById(R.id.location1);
            txtTargetLocation = (TextView) itemView.findViewById(R.id.location2);
            cardViewLayout = (CardView) itemView.findViewById(R.id.cardview_layout);
            distance=(TextView) itemView.findViewById(R.id.distance);
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

    public void changeList(ArrayList<Ride> list) {
        dataList = list;
        notifyDataSetChanged();
    }
}
