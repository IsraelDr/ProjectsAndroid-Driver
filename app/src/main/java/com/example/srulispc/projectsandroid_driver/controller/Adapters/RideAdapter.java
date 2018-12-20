package com.example.srulispc.projectsandroid_driver.controller.Adapters;

import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.srulispc.projectsandroid_driver.R;
import com.example.srulispc.projectsandroid_driver.controller.model.entities.Ride;

import java.util.ArrayList;



public class RideAdapter extends RecyclerView.Adapter<RideAdapter.RideViewHolder> {

    private ArrayList<Ride> dataList;

    public RideAdapter(ArrayList<Ride> dataList) {
        this.dataList = dataList;
    }

    @Override
    public RideViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ride_cardview, parent, false);
        return new RideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RideViewHolder holder, int position) {
        holder.txtClientName.setText(dataList.get(position).getClientName());

        Location l;
        l = dataList.get(position).getSourceLocation();
        holder.txtSourceLocation.setText((l != null)? l.toString() : "" );

        l = dataList.get(position).getTargetLocation();
        holder.txtTargetLocation.setText((l != null)? l.toString() : "" );
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
            txtSourceLocation = (TextView) itemView.findViewById(R.id.sourcelocation);
            txtTargetLocation = (TextView) itemView.findViewById(R.id.targetlocation);
        }
    }
}
