package com.trafengineproject.owner.traff_engine;

/**
 * Created by Raymond Klutse Ewoenam on 21/04/2017.
 */

import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

public class AlternativeRouteMaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alternative_route_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.altroutmap);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Bundle bundle = getIntent().getExtras();
        String startpoint = bundle.getString("ad_txt_1");
        String endpoint = bundle.getString("ad_txt_2");
        Double start_latitude, start_longitude, end_latitude,end_longitude;
        float zoom = 18;

        start_latitude = getAddLatitude(this,startpoint);
        start_longitude = getAddLongitude(this,startpoint);
        end_latitude = getAddLatitude(this,endpoint);
        end_longitude = getAddLongitude(this,endpoint);

        final LatLng InitialPoint = new LatLng( start_latitude ,start_longitude);
        final LatLng EndPoint = new LatLng(end_latitude ,end_longitude);


        mMap.addMarker(new MarkerOptions().position(InitialPoint).title("Marker in" +startpoint));

        mMap.addMarker(new MarkerOptions().position(EndPoint).title("Marker in" +endpoint));
        PolylineOptions polylineOptions = new PolylineOptions().add(InitialPoint).add(EndPoint).width(5).color(Color.BLUE).geodesic(true);
        mMap.addPolyline(polylineOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom( InitialPoint, zoom));

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

    // Method to show a toast
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}
