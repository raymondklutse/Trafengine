package com.trafengineproject.owner.traff_engine;
/**
 * Created by Raymond Klutse Ewoenam on 21/04/2017.
 */

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
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;



public class Homepage extends AppCompatActivity implements LocationListener,View.OnKeyListener {
    private EditText editText;
    private TextView speedview, locationview;
    private String provider;
    private LocationManager lm;
    private Button searchbuton;

    //Source Node
    private static final String START = "A";

    //Destination Node
    private static final String END = "K";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_homepage);

        editText = (EditText) findViewById(R.id.locationtxt);

        editText.setOnKeyListener(this);

        searchbuton = (Button) findViewById(R.id.srchbtn);
        searchbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                Intent i = new Intent(Homepage.this, TraffengineMaps.class);
                String texttosend = text;
                //i.putExtra("add_key", true);
                i.putExtra("ad_txt", texttosend);
                startActivity(i);
            }
        });

        //create an object of LocationManager to get location
        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        provider = LocationManager.GPS_PROVIDER;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lm.requestLocationUpdates(provider, 1000,0 , this);
        this.onLocationChanged(null);
    }
    // Method for change in Location
    @Override
    public void onLocationChanged(Location location) {
        float currentspeed;
        double currentLatitude;
        double currentLongitude;
        String city;

        if (location == null) {
            showToast("No location data found");
        }
        else {
            //gets latitude coordinate
            currentLatitude = location.getLatitude();

            //gets longitude coordinate
            currentLongitude = location.getLongitude();

            //gets speed from location and converts it to km/h
            currentspeed = (float) ((location.getSpeed() * 3600) / 1000);

            //displays speed
            speedview.setText(currentspeed + "km/h");

            //latview.setText((int) currentLatitude);
            //longview.setText((int) currentLongitude);


            //get city name from longitude and latitude
            city = getCompleteAddressString(currentLatitude, currentLongitude);

            //displays city
            locationview.setText(city);

            //scheduleSendUserInfo(location);
        }
    }

    // Method to display Location on Google Maps when Enter Key is pressed
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        String text = editText.getText().toString();
        EditText myEditText = (EditText) v;
        if (keyCode == EditorInfo.IME_ACTION_SEARCH || keyCode == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            Intent i = new Intent(Homepage.this, TraffengineMaps.class);
            String texttosend = text;
            //i.putExtra("add_key", true);
            i.putExtra("ad_txt", texttosend);
            startActivity(i);
            //displayLocation(text);
            return true;
        }
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        try{
            provider = LocationManager.GPS_PROVIDER;
            lm.requestLocationUpdates(provider, 1000, 0, this);

        }
        catch (Exception ex){
            Log.d("Permission Exception", "");
        }
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

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        showToast( "Location Provider Enabled ");
    }

    @Override
    public void onProviderDisabled(String provider) {
        showToast("Location Provider Disabled ");
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

    //method to send user data to the database
    public void sendUserInfo(Location location ) {

        if (location == null) {
            showToast("No location found");
        }
        else{
            showToast("BackgroundTask in progress");
            double clat = location.getLatitude();
            double clong = location.getLongitude();
            float cspeed = (float) ((location.getSpeed() * 3600) / 1000);


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
    //method to send Location text to Maps Activity to Display Location on Maps
    public void sendTextToMapsActivity(String text){

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


}
