package com.trafengineproject.owner.traff_engine;
/**
 * Created by Raymond Klutse Ewoenam on 21/04/2017.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class TraffengineMaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Location location;


    //Madina-Zongo Junction
    double a_latitude = 5.678470;
    double a_longitude = -0.173056;

    //Atomic Round About
    double b_latitude = 5.667430;
    double b_longitude = -0.177112;

    //UPSA Junction
    double c_latitude = 5.659722;
    double c_longitude = -0.178657;

    //Okponglo
    double d_latitude = 5.640653;
    double d_longitude = -0.178099;

    //Hannah-Madina Junction
    double e_latitude = 5.677680;
    double e_longitude = -0.170374;

    //Methodist Book Shop Junction
    double f_latitude = 5.677594;
    double f_longitude = -0.164859;

    //Presec Junction
    double g_latitude = 5.667622;
    double g_longitude = -0.171211;

    //Hannah-Presec Junction
    double h_latitude = 5.670975;
    double h_longitude = -0.171125;

    //Hannah Junction
    double i_latitude = 5.671146;
    double i_longitude = -0.170395;

    //Rawlings Cirlce
    double j_latitude = 5.667793;
    double j_longitude = -0.165031;

    //UPSA-Presec Junction
    double k_latitude = 5.659774;
    double k_longitude = -0.169108;

    //Madina-UPSA Junction
    double l_latitude = 5.659603;
    double l_longitude = -0.164731;

    //ICAG Junction
    double m_latitude = 5.641538;
    double m_longitude = -0.169795;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffengine_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.traffmap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Bundle bundle = getIntent().getExtras();
        String address = bundle.getString("ad_txt");
        Double useradd_latitude;
        Double useradd_longitude;
        float zoom = 15;

        //Add a marker in Address from Search Box and move the camera
        //LatLng sydney = new LatLng(-34, 151);

        //get address latitude
        useradd_latitude = getAddLatitude(this, address);

        //get address longitude
        useradd_longitude = getAddLongitude(this, address);


        LatLng AddressInput = new LatLng(useradd_latitude, useradd_longitude);
        mMap.addMarker(new MarkerOptions().position(AddressInput).title("Marker in" + address));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(AddressInput, zoom));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
//                double lat = latLng.latitude;
//                double lng = latLng.longitude;
//                showToast("Latitude is : " + lat + " \t" + "\n" + "Longtitude is : " + lng );

                //averagespeed();
                DisplayTrafficMessage();
            }
        });

    }

    // Method to show a toast
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    // Method to get Latitude from User Address Input
    public Double getAddLatitude(Context context, String Locationtext) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> address;
        Double addlatitude = null;

        try {
            // May throw an IOException
            address = geocoder.getFromLocationName(Locationtext, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            addlatitude = location.getLatitude();

        } catch (IOException ex) {

            ex.printStackTrace();
        }
        return addlatitude;
    }

    // Method to get Longitude from User Address Input
    public Double getAddLongitude(Context context, String Locationtext) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> address;
        Double addlongitude = null;

        try {
            // May throw an IOException
            address = geocoder.getFromLocationName(Locationtext, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            addlongitude = location.getLongitude();


        } catch (IOException ex) {

            ex.printStackTrace();
        }
        return addlongitude;
    }


    public void DisplayTrafficMessage() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(" There is heavy traffic on this road. Do you want an alternative route? ")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(TraffengineMaps.this, SearchRoute.class);
                                startActivity(i);
                            }
                        });
        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();


    }

       /* if (0 <= averagespeed() && averagespeed() <=20 ) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(" There is heavy traffic on this road. Do you want an alternative route? ")
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent i = new Intent(TraffengineMaps.this, SearchRoute.class);
                                    startActivity(i);
                                }
                            });
            alertDialogBuilder.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }

        else if (20 <= averagespeed() && averagespeed() <=40 ) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(" There is mild traffic on this road. Do you want an alternative route? ")
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent i = new Intent(TraffengineMaps.this, SearchRoute.class);
                                    startActivity(i);
                                }
                            });
            alertDialogBuilder.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }
        else if (averagespeed() >40)  {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(" There is no traffic on this road. Do you want an alternative route? ")
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent i = new Intent(TraffengineMaps.this, SearchRoute.class);
                                    startActivity(i);
                                }
                            });
            alertDialogBuilder.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }
        else if(averagespeed()== 0){
            showToast("There is no traffic information for this area. Please try again later");

        }

    }
    public boolean servicesOK() {

        return true;
    }*/

    /*public int averagespeed(){
        int averagespeed = 25;

        double clat = location.getLatitude();
        double clong = location.getLongitude();

        String edge ;
        String s_clat = Double.toString(location.getLatitude());
        String s_clong = Double.toString(location.getLongitude());
        //gets speed from location ,converts it to km/h and changes it to String
//        long timestamp = getUnixtime();
        String s_cspeed = Float.toString((location.getSpeed() * 3600) / 1000);
        String cur_city = getCompleteAddressString(clat, clong);

        String method = "searchinfo";

        //Method call to performing terask in background
        BackgroundTask backgroundTask = new BackgroundTask(this);

        //check if lat and long coordinates are on Edge AB
        if((a_latitude <=clat && clat <= b_latitude) && (a_longitude <=clong&& clong <= b_longitude) ){
            edge = "AB";
            backgroundTask.execute(method, edge);
            finish();
        }
        //check if lat and long coordinates are on Edge AE
        else if((a_latitude <=clat && clat <= e_latitude) &&(a_longitude <=clong && clong <= e_longitude)){
            edge = "AE";
            backgroundTask.execute(method, edge);
            finish();
        }
        //check if lat and long coordinates are on Edge BC
        else if((a_latitude <=clat  && clat <= c_latitude) && (a_longitude <=clong && clong<= c_longitude) ){
            edge = "BC";
            backgroundTask.execute(method, edge);
            finish();
        }
        //check if lat and long coordinates are on Edge BG
        else if((b_latitude <=clat && clat <= g_latitude) && (b_longitude <=clong && clong <= g_longitude)){
            edge = "BG";
            backgroundTask.execute(method, edge);
            finish();
        }
        //check if lat and long coordinates are on Edge CK
        else if((c_latitude <=clat && clat <= k_latitude) &&(c_longitude <=clong && clong <= k_longitude )){
            edge = "CK";
            backgroundTask.execute(method, edge);
            finish();
        }
        //check if lat and long coordinates are on Edge CD
       else  if((c_latitude <=clat && clat <= d_latitude) && (c_longitude <=clong && clong <= d_longitude)){
            edge = "CD";
            backgroundTask.execute(method, edge);
            finish();
        }
        //check if lat and long coordinates are on Edge DM
       else  if((d_latitude <=clat && clat <= m_latitude) && (d_longitude <=clong && clong<= m_longitude )){
            edge = "DM";
            backgroundTask.execute(method, edge);
            finish();
        }
        //check if lat and long coordinates are on Edge EF
        else if((e_latitude <=clat && clat <= f_latitude) && (e_longitude <=clong && clong <= f_longitude)){
            edge = "EF";
            backgroundTask.execute(method, edge);
            finish();
        }

        //check if lat and long coordinates are on Edge CI
        else if((c_latitude <=clat && clat <= i_latitude) && (c_longitude <=clong && clong <= i_longitude )){
            edge = "CI";
            backgroundTask.execute(method, edge);
            finish();
        }
        //check if lat and long coordinates are on Edge FJ
        else if((f_latitude <=clat && clat <= j_latitude )&& (f_longitude <=clong && clong<= j_longitude)){
            edge = "FJ";
            backgroundTask.execute(method, edge);
            finish();
        }
        //check if lat and long coordinates are on Edge GH
       else if((g_latitude <=clat && clat <= h_latitude) && (g_longitude <=clong && clong <= h_longitude )){
            edge = "GH";
            backgroundTask.execute(method, edge);
            finish();
        }
        //check if lat and long coordinates are on Edge GJ
        else if((g_latitude <=clat && clat <= j_latitude) && (g_longitude <=clong && clong<= j_longitude)){
            edge = "GJ";
            backgroundTask.execute(method, edge);
            finish();
        }
        //check if lat and long coordinates are on Edge HI
        else if((h_latitude <=clat && clat <= i_latitude ) && (h_longitude <=clong && clong <= i_longitude)){
            edge = "HI";
            backgroundTask.execute(method, edge);
            finish();
        }
        //check if lat and long coordinates are on Edge JL
        else if((j_latitude <=clat && clat <= l_latitude) && (j_longitude <=clong && clong<= l_longitude )){
            edge = "JL";
            backgroundTask.execute(method, edge);
            finish();
        }
        //check if lat and long coordinates are on Edge KM
        else if((k_latitude <=clat && clat <= m_latitude) && (k_longitude <=clong && clong <= m_longitude)){
            edge = "KM";
            backgroundTask.execute(method, edge);
            finish();
        }
        //check if lat and long coordinates are on Edge KL
        else if((k_latitude <=clat && clat<= l_latitude) && (k_longitude <=clong && clong<= l_longitude)){
            edge = "KL";
            backgroundTask.execute(method, edge);
            finish();
        }

        return averagespeed ;
    }

    //method to get unixtime
    public long getUnixtime() throws ParseException {
        String dateString = "Fri, 09 Nov 2012 23:40:18 GMT";
        DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss z");
        Date date = dateFormat.parse(dateString);
        long unixTime = (long)date.getTime()/1000;
        return unixTime;
//System.out.println(unixTime); //<- prints 1352504418
    }

        //gets the current location of the user
        public String getCompleteAddressString( double latitude, double longitude){
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
        }*/


    }
