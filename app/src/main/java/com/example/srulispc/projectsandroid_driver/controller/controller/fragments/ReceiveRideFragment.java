package com.example.srulispc.projectsandroid_driver.controller.controller.fragments;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.srulispc.projectsandroid_driver.R;
import com.example.srulispc.projectsandroid_driver.controller.controller.MainActivity;
import com.example.srulispc.projectsandroid_driver.controller.model.backend.BackendFactory;
import com.example.srulispc.projectsandroid_driver.controller.model.backend.Ibackend;
import com.example.srulispc.projectsandroid_driver.controller.model.entities.Ride;
import com.rilixtech.materialfancybutton.MaterialFancyButton;

import java.util.List;
import java.util.Locale;


public class ReceiveRideFragment extends Fragment {

    final private int REQUEST_MULTIPLE_PERMISSIONS = 124;
    public static ReceiveRideFragment newInstance(Ride ride) {

        ReceiveRideFragment fragment = new ReceiveRideFragment();

        Bundle args = new Bundle();
        args.putString("clientName", ride.getClientName());
        args.putString("clientPhoneNumber", ride.getClientPhoneNumber());
        args.putString("clientMail", ride.getClientMail());
        args.putString("ridekey", ride.getRidekey());
        args.putString("sourcelatitude",""+ride.getSourceLocation().getLatitude());
        args.putString("sourcelongitude",""+ride.getSourceLocation().getLongitude());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_receive_ride,container, false);
        //Hide receiveRide button if necessary
        if (getFragmentManager().findFragmentByTag("myRidesFragment")!=null)
            view.findViewById(R.id.receiveRide).setVisibility(View.INVISIBLE);

        final String name= getArguments().getString("clientName", "");
        final String phone = getArguments().getString("clientPhoneNumber", "");
        String mail= getArguments().getString("clientMail", "");
        final String id= getArguments().getString("ridekey", "");

        ((TextView) view.findViewById(R.id.client_name)).setText(name);
        ((TextView) view.findViewById(R.id.client_phoneNumber)).setText(phone);
        ((TextView) view.findViewById(R.id.client_mail)).setText(mail);


        MaterialFancyButton catchRide = view.findViewById(R.id.receiveRide);
        catchRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ibackend backend = BackendFactory.getInstance();

                backend.setStatus(id, Ride.Status.CAUGHT);
                backend.setDriverIDInRide(id);
                ((MainActivity) getActivity()).closeRecieveRideFragment();
            }
        });
        ImageView wazeicon=view.findViewById(R.id.wazeicon);
        wazeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Ibackend backend = BackendFactory.getInstance();
                    String sourcename=findCityLongLat(Double.parseDouble(getArguments().getString("sourcelatitude", "")),Double.parseDouble(getArguments().getString("sourcelongitude", "")));
                    String url = "https://waze.com/ul?q=" + sourcename;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    ((MainActivity) getActivity()).startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
                    ((MainActivity) getActivity()).startActivity(intent);
                }
            }
        });
        ImageView callicon=view.findViewById(R.id.callIcon);
        callicon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+phone));
                if (ActivityCompat.checkSelfPermission(((MainActivity) getActivity()), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(((MainActivity) getActivity()), new String[]{Manifest.permission.CALL_PHONE}, 10);
                    return;
                }
                ((MainActivity) getActivity()).startActivity(callIntent);
            }
        });
        ImageView smsicon=view.findViewById(R.id.smsIcon);
        smsicon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(((MainActivity) getActivity()), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(((MainActivity) getActivity()), new String[]{Manifest.permission.SEND_SMS}, 10);
                    return;
                }
                SmsManager.getDefault().sendTextMessage(phone, null, "הנהג בדרך אליך ויהיה אצלך בעוד כמה דקות.", null,null);
                //Uri uri = Uri.parse("smsto:"+phone);
                //Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                //intent.putExtra("sms_body","הנהג בדרך אליך ויהיה אצלך בעוד כמה דקות.");
                //startActivity(intent);
            }
        });




        Button exit = view.findViewById(R.id.fragment_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                if (v != null) {v.vibrate(10);}

                ((MainActivity) getActivity()).closeRecieveRideFragment();
            }
        });

        return view;
    }
    private String findCityLongLat(double Latitude, double Longitude) {

        String strAdd = "";
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(Latitude, Longitude, 1);
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
