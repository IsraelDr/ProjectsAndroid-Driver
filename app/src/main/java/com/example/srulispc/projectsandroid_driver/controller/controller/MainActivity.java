package com.example.srulispc.projectsandroid_driver.controller.controller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.srulispc.projectsandroid_driver.R;
import com.example.srulispc.projectsandroid_driver.controller.controller.fragments.MyRidesFragment;
import com.example.srulispc.projectsandroid_driver.controller.controller.fragments.WaitingRidesFragment;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private LocationListener locationListener;
    private LocationManager locationManager;
    public static Location myLocation;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        forceUserTurnGPS();

        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                myLocation = getGpsLocation();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) { }
            public void onProviderEnabled(String provider) { }
            public void onProviderDisabled(String provider) { }
        };
        myLocation = getGpsLocation();

        //----------------Set the default fragment---------------------
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("waitingRidesFragment");
        if (fragment==null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            fragment = new WaitingRidesFragment();
            ft.replace(R.id.fragment_holder1, fragment, "waitingRidesFragment").commit();
        }
        findViewById(R.id.nav_availableRides).setBackgroundColor(android.graphics.Color.rgb(61,58,58));


        //-------------Set Driver Details In The Drawer-----------------------
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        TextView textview;
        String driverMail = firebaseAuth.getCurrentUser().getEmail();

        textview = (TextView) navigationView.getHeaderView(0).findViewById(R.id.driverMail);
        textview.setText(driverMail);

        textview = (TextView) navigationView.getHeaderView(0).findViewById(R.id.driverName);
//      textview.setText(BackendFactory.getInstance().getCurrentDriverName());
        //------------------------------------------------------------------------------

        findViewById(R.id.nav_availableRides).setOnClickListener(this);
        findViewById(R.id.nav_myRides).setOnClickListener(this);
        findViewById(R.id.nav_exit).setOnClickListener(this);

        final SeekBar seekBar = findViewById(R.id.filter_distance);

        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                seekBar.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        getSupportActionBar().setTitle(R.string.availibleRides);
        startService(new Intent(getBaseContext(), ReceivedRideService.class));
    }

    private Location getGpsLocation() {

        try {
            //     Check the SDK version and whether the permission is already granted or not.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // got premission in DriverActivity
            } else {
                // Android version is lesser than 6.0 or the permission is already granted.
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2, 1   , locationListener);
            }
            return locationManager.getLastKnownLocation(locationManager.PASSIVE_PROVIDER);
        } catch (SecurityException e) {
            return null;
        }
    }
    @Override
    public void onDestroy() {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        super.onDestroy();
    }

    private void forceUserTurnGPS() {

        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(R.string.locationrequired);
            builder.setMessage(R.string.locationdescription);
            builder.setPositiveButton(R.string.settings,null);
            builder.setNegativeButton(R.string._continue, null);
            builder.setCancelable(false);

            final AlertDialog alertDialog = builder.create();
            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {

                    //Set positive Button
                    Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent,1);
                        }
                    });

                    //Set Negative Button
                    b = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER))
                                alertDialog.dismiss();
                            else
                                Toast.makeText(MainActivity.this, "Turn On GPS!", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            });

            alertDialog.show();
        }
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

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("bottomMenuFragment");

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

        //if (id == R.id.action_settings) {
        //    return true;
        //}..

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        view.setBackgroundColor(android.graphics.Color.rgb(61,58,58));
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment;

        switch (view.getId()) {

            case R.id.nav_availableRides:

                findViewById(R.id.nav_myRides).setBackgroundColor(android.graphics.Color.rgb(66,66,66));
                findViewById(R.id.nav_exit).setBackgroundColor(android.graphics.Color.rgb(66,66,66));
                getSupportActionBar().setTitle(R.string.availibleRides);
                fragment = fm.findFragmentByTag("waitingRidesFragment");
                if (fragment==null) {
                    FragmentTransaction ft = fm.beginTransaction();
                    fragment = new WaitingRidesFragment();
                    ft.replace(R.id.fragment_holder1,fragment,"waitingRidesFragment").commit();

                    closeRecieveRideFragment();
                }
                break;


            case R.id.nav_myRides:

                findViewById(R.id.nav_availableRides).setBackgroundColor(android.graphics.Color.rgb(66,66,66));
                findViewById(R.id.nav_exit).setBackgroundColor(android.graphics.Color.rgb(66,66,66));
                getSupportActionBar().setTitle(R.string.my_rides);
                fragment = fm.findFragmentByTag("myRidesFragment");
                if (fragment==null) {
                    FragmentTransaction ft = fm.beginTransaction();
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
    }
}
