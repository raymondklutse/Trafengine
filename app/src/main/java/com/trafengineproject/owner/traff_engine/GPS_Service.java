package com.trafengineproject.owner.traff_engine;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Owner G on 24/04/2017.
 */

public class GPS_Service extends Service {

    private LocationManager locationManager;
    private LocationListener locationListener;
    private String provider;
    private Location gpslocation;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //this service will run until we stop it
        showToast("Service has started");
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                float currentspeed;
                double currentLatitude;
                double currentLongitude;
                String city;

                if (location == null) {
                    showToast("No location data found");
                } else {

                    //gets latitude coordinate
                    currentLatitude = location.getLatitude();

                    //gets longitude coordinate
                    currentLongitude = location.getLongitude();

                    //gets speed from location and converts it to km/h
                    currentspeed = (float) ((location.getSpeed() * 3600) / 1000);

                    //get city name from longitude and latitude
                    city = getCompleteAddressString(currentLatitude, currentLongitude);
                    Intent intent = new Intent("location_update");
                    intent.putExtra("Address", city);
                    intent.putExtra("Speed", currentspeed);
                    sendBroadcast(intent);
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                showToast("Location Provider Enabled");
            }

            @Override
            public void onProviderDisabled(String provider) {
                showToast("Location Provider Disabled ");
            }
        };
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        provider = LocationManager.GPS_PROVIDER;

        //noinspection MissingPermission
        gpslocation = locationManager.getLastKnownLocation(provider);
        //noinspection MissingPermission
        locationManager.requestLocationUpdates(provider, 0, 0, locationListener);
        scheduleSendUserInfo(gpslocation);


        return Service.START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            locationManager.removeUpdates(locationListener);
        }
    }

    // Method to show a toast
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    //method call to send location data to database every minute
    public void scheduleSendUserInfo(final Location location) {
        Timer timer = new Timer();

        final Handler handler = new Handler();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            showToast(" sendUserInfo method called");
                            sendUserInfo(location);

                        } catch (Exception e) {
                            //showToast("Data was not sent to database");
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask,30000,60000);

    }
    //method to send user data to the database
    public void sendUserInfo(Location location ) {

        if (location == null) {
            showToast("No location found");
        }
        else{
            showToast("BackgroundTask in progress");
            double clat = location.getLatitude();
            double clong = location.getLongitude();

            String s_clat = Double.toString(location.getLatitude());
            String s_clong = Double.toString(location.getLongitude());
            // showToast("Latitude is : " + s_clat);

            //gets speed from location ,converts it to km/h and changes it to String
            String s_cspeed = Float.toString((location.getSpeed() * 3600) / 1000);
            String road_name = "Black Avenue";
            String cur_city = getCompleteAddressString(clat, clong);
            String c_date = getcurrentdate();
            String c_time = getcurrenttime();
            String method = "insertdata";

            //Method call to performing terask in background
            BackgroundTask backgroundTask = new BackgroundTask(this);
            backgroundTask.execute(method, s_clat, s_clong, s_cspeed, cur_city, road_name, c_date,c_time);
//            finish();
            showToast("Data has been sent to Database");
        }
    }

    //gets the current location of the user
    public String getCompleteAddressString(double latitude, double longitude) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");
                strReturnedAddress.append(returnedAddress.getLocality()).append("\n");

                strAdd = strReturnedAddress.toString();
                Log.w("My Current location", "" + strReturnedAddress.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current location", "No Location returned!");
        }
        return strAdd;
    }

    //Method to get users current date
    public  String getcurrentdate (){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String cur_date = dateFormat.format(calendar.getTime());
        showToast(cur_date);
        return cur_date;

    }


    //Method to get users current time
    public String getcurrenttime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String cur_time = dateFormat.format(calendar.getTime());
        showToast(cur_time);
        return cur_time;
    }

}
