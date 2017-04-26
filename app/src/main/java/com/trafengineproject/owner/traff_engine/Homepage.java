package com.trafengineproject.owner.traff_engine;
/**
 * Created by Raymond Klutse Ewoenam on 21/04/2017.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class Homepage extends AppCompatActivity implements View.OnKeyListener {
    private EditText editText;
    private TextView speedview, locationview;
    private Button searchbuton;
    private BroadcastReceiver broadcastreceiver;
    private Location gpslocation;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private String provider;

    //Source Node
    private static final String START = "A";

    //Destination Node
    private static final String END = "K";
    String[] values = {
            "abc_0", "def_0", "ghi_0",
            "abc_1", "def_1", "ghi_1",
            "abc_2", "def_2", "ghi_2",
            "abc_3", "def_3", "ghi_3",
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_homepage);

        editText = (EditText) findViewById(R.id.locationtxt);


        searchbuton = (Button) findViewById(R.id.srchbtn);
        searchbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckInternetConnectivity() != false ) {

                    String text = editText.getText().toString();
                    Intent i = new Intent(Homepage.this, TraffengineMaps.class);
                    String texttosend = text;
                    i.putExtra("ad_txt", texttosend);
                    startActivity(i);
                }

                else {
                    showInternetConnectionError();

                }


            }
        });

//        speedview = (TextView) findViewById(R.id.speedtxt);
//
//        locationview = (TextView) findViewById(R.id.locatviewtext);

        if (runtime_permission() != false) {
            enable_service();
        }

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        provider = LocationManager.GPS_PROVIDER;


        gpslocation = locationManager.getLastKnownLocation(provider);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(provider, 0, 0, locationListener);

        scheduleSendUserInfo(gpslocation);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                enable_service();
            }
            else {
                runtime_permission();
            }

        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastreceiver != null ){
            unregisterReceiver(broadcastreceiver);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (broadcastreceiver ==null ){
            broadcastreceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
//                speedview.append("\n" + intent.getExtras().get("Speed"));
//                    locationview.append("\n" + intent.getExtras().get("Address"));
                }
            };
        }
        registerReceiver(broadcastreceiver,new IntentFilter("location_update" ));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    // Method to show a toast
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(" Are you sure you want to exit? ")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Homepage.this.finish();

                            }
                        });
        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    private void startGPSService() {
        Intent i = new Intent(getBaseContext(),GPS_Service.class);
        startService(i);
    }
    private void stopGPSService() {
        Intent i = new Intent(getBaseContext(),GPS_Service.class);
        stopGPSService();
    }


    private void enable_service() {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGPSService();
            }
        });

    }

    private boolean runtime_permission() {
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            return true;
        }
        else {

        return false;
        }

    }


    public void showInternetConnectionError() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You need a network connection to use this application. Please turn on mobile network or Wi-Fi in Settings.")
                .setTitle("Unable to connect")
                .setCancelable(false)
                .setPositiveButton("Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callWirelessSettingsIntent = new Intent(
                                        Settings.ACTION_WIRELESS_SETTINGS);
                                startActivity(callWirelessSettingsIntent);
                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }
                );
        AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean CheckInternetConnectivity(){
        if (isNetworkAvailable()== true) {
            showToast("Internet Connection is good");
            return true;
        }
        try{
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            return true;
        }
        catch (Exception ex){

            Log.d("Permission Exception", "");
            return false;
        }

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
            finish();
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


    public void AddGraphDataStrcture(){
        Graph graph = new Graph();
        DepthFirst df = new DepthFirst();
          /* These are longitude and lattude coordiantes to identify each node.
        These coordinates also serve as marking points for each edge.*/

        //Madina-Zongo Junction
        double  a_latitude = 5.678381;
        double  a_longitude = -0.173035;

        //Atomic Round About
        double b_latitude = 5.667448;
        double b_longitude = -0.177112;

        //UPSA Junction
        double c_latitude = 5.659847;
        double c_longitude = -0.178571;

        //Okponglo
        double d_latitude = 5.640714;
        double d_longitude = -0.178163;

        //Hannah-Madina Junction
        double e_latitude = 5.67774;
        double e_longitude = -0.164859;

        //Methodist Book Shop Junction
        double f_latitude = 5.677676;
        double f_longitude = -0.164859;

        //Presec Junction
        double g_latitude = 5.667705;
        double g_longitude = -0.171221;

        //Hannah-Presec Junction
        double h_latitude = 5.671089;
        double h_longitude = -0.177114;

        //Hannah Junction
        double i_latitude = 5.671174;
        double i_longitude = -0.170406;

        //Rawlings Cirlce
        double j_latitude = 5.667811;
        double j_longitude = -0.165009;

        //UPSA-Presec Junction
        double k_latitude = 5.659793;
        double k_longitude = -0.169097;

        //Madina-UPSA Junction
        double l_latitude = 5.659633;
        double l_longitude = -0.164741;

        //ICAG Junction
        double m_latitude = 5.64159;
        double m_longitude = -0.169773;

        graph.addTwoWayVertex("0","A", "B",23);
        graph.addTwoWayVertex("1","A", "E",70);
        graph.addTwoWayVertex("2","B", "C",40);
        graph.addTwoWayVertex("3","B", "G",50);
        graph.addTwoWayVertex("4","C", "K",54);
        graph.addTwoWayVertex("5","C", "D",34);
        graph.addTwoWayVertex("6","D", "M",56);
        graph.addTwoWayVertex("7","E", "F",23);
        graph.addTwoWayVertex("8","E", "I",64);
        graph.addTwoWayVertex("9","F", "J",75);
        graph.addTwoWayVertex("10","G", "H",35);
        graph.addTwoWayVertex("11","G", "J",74);
        graph.addTwoWayVertex("12","H", "I",36);
        graph.addTwoWayVertex("13","J", "L",86);
        graph.addTwoWayVertex("14","K", "M",33);
        graph.addTwoWayVertex("15","K", "L",65);


        LinkedList<String> visited = new LinkedList();

        List<Integer> totalcostlist = df.gettotalcostlist();

        visited.add(START);

        //DepthFisrt Algorithm to print all paths from source to destination
        df.depthFirst(graph, visited,END);

        df.printtotalcostlist(totalcostlist);
//       //Function to find the highest cost
        df.findhighestcost(totalcostlist);
//

    }

    public int averagespeed(double s_lat,double s_long,double d_lat,double d_long){
        int averagespeed = 0;
        return averagespeed ;
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
    }
}
