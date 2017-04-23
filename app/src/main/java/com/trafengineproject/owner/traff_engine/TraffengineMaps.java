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
        useradd_latitude = getAddLatitude(this,address);

        //get address longitude
        useradd_longitude = getAddLongitude(this,address);

        LatLng AddressInput = new LatLng( useradd_latitude,useradd_longitude );
        mMap.addMarker(new MarkerOptions().position(AddressInput).title("Marker in" + address));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(AddressInput, zoom));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
//                double lat = latLng.latitude;
//                double lng = latLng.longitude;
//                showToast("Latitude is : " + lat + " \t" + "\n" + "Longtitude is : " + lng );
                DisplayTrafficMessage();
            }
        });

    }
    // Method to show a toast
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    // Method to get Latitude from User Address Input
    public Double getAddLatitude (Context context, String Locationtext) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> address;
        Double addlatitude =null;

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
    public Double getAddLongitude (Context context, String Locationtext) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> address;
        Double addlongitude =null;

        try {
            // May throw an IOException
            address = geocoder.getFromLocationName(Locationtext, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            addlongitude= location.getLongitude();


        } catch (IOException ex) {

            ex.printStackTrace();
        }
        return addlongitude;
    }

    public void TrafficSearch( Double s_lat, Double s_long ){
        String s_latitude =Double.toString(location.getLatitude());
        String s_longitude = Double.toString(location.getLatitude());

        String method = "searchinfo";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(method,s_latitude,s_longitude);
    }
    public void DisplayTrafficMessage(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(" There is heavy traffic on this road. Do you want an alternative route? ")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent i = new Intent(TraffengineMaps.this, SearchRoute.class);
                                startActivity(i);
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
    public boolean servicesOK() {

        return true;
    }


}