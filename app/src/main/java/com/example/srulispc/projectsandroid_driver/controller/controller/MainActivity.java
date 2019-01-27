package com.example.srulispc.projectsandroid_driver.controller.controller;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.srulispc.projectsandroid_driver.R;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private LocationManager locationManager;
    public static Location myLocation;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location locat) {
                myLocation = getGpsLocation();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //---Set the default fragment---
        Fragment fragment = getFragmentManager().findFragmentByTag("waitingRidesFragment");
        if (fragment==null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            fragment = new WaitingRidesFragment();
            ft.replace(R.id.fragment_holder1, fragment, "waitingRidesFragment").commit();
        }
        //-------------------------------

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
            }
        });
        fab.hide();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        //-------------Set Driver Details In The Drawer-----------------------
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        TextView textview;
        String driverMail = firebaseAuth.getCurrentUser().getEmail();

        textview = (TextView) navigationView.getHeaderView(0).findViewById(R.id.driverMail);
        textview.setText(driverMail);

//        String driverName = "";
//        ArrayList<Driver> driverList = backend.getalldrivers();
//
//        for (Driver driver : driverList) {
//            if (driver.getMail().equals(driverMail))
//                driverName = driver.getFirstName()+ " " + driver.getLastName();
//        }
//
//        textview = (TextView) navigationView.getHeaderView(0).findViewById(R.id.driverName);
//        textview.setText(driverName);
        //------------------------------------------------------------------------------
        startService(new Intent(getBaseContext(), ReceivedRideService.class));

    }
    private Location getGpsLocation() {
        //     Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // got premission in DriverActivity
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
        return locationManager.getLastKnownLocation(locationManager.PASSIVE_PROVIDER);
    }
    @Override
    public void onDestroy() {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        super.onDestroy();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (!closeRecieveRideFragment()) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                super.onBackPressed();
            }

        }
    }

    public boolean closeRecieveRideFragment() {

        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentByTag("receiveRideFragment");

        if (fragment!=null) {
            //Expand the RecyclerView
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0,
                    0.0f
            );
            FrameLayout fragmentHolder = findViewById(R.id.fragment_holder2);
            fragmentHolder.setLayoutParams(param);

            //remove fragment
            fm.beginTransaction().remove(fragment).commit();
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment;

        switch (item.getItemId()){

            case R.id.nav_availableRides:

                fragment = getFragmentManager().findFragmentByTag("waitingRidesFragment");
                if (fragment==null) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    fragment = new WaitingRidesFragment();
                    ft.replace(R.id.fragment_holder1,fragment,"waitingRidesFragment").commit();

                    closeRecieveRideFragment();
                }
                break;


            case R.id.nav_myRides:

                fragment = getFragmentManager().findFragmentByTag("myRidesFragment");
                if (fragment==null) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    fragment = new MyRidesFragment();
                    ft.replace(R.id.fragment_holder1, fragment, "myRidesFragment").commit();

                    closeRecieveRideFragment();
                }
                break;


            case R.id.nav_exit:
                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                firebaseAuth.signOut();

                super.onBackPressed();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
