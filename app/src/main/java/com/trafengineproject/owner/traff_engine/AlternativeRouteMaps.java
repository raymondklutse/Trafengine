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
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlternativeRouteMaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Graph graph = new Graph();
    DepthFirst df = new DepthFirst();

  // create an array of vertexes. Each vertex represents a junction on the road.
        private static List <Node> nodes  = new ArrayList <Node> ();

        //create an array of edges. Each edge is connected by two nodes.
        private static List <Edge> edges = new ArrayList<Edge>();


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
        mMap = googleMap;Bundle bundle = getIntent().getExtras();
        String startpoint = bundle.getString("ad_txt_1");
        String endpoint = bundle.getString("ad_txt_2");
        Double start_latitude, start_longitude, end_latitude,end_longitude;
        float zoom = 18;



        final LatLng A = new LatLng( 5.678381 ,-0.173035);
        final LatLng E = new LatLng(  5.67774 ,-0.164859);
        final LatLng F = new LatLng(5.677676 ,-0.164859);
        final LatLng J = new LatLng( 5.667811 ,-0.165009);
        final LatLng L = new LatLng( 5.659633 ,-0.164741);
        final LatLng K = new LatLng(  5.659793 ,-0.169097);
        final LatLng M = new LatLng( 5.641538 ,-0.169795);
        final LatLng D = new LatLng( 5.640653 ,-0.178099);
        PolylineOptions polylineOptions = new PolylineOptions().add(A).add(E).add(F).add(J).add(L).add(K).add(M).add(D).width(5).color(Color.BLUE).geodesic(true);
        mMap.addPolyline(polylineOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom( A, zoom));

        /*
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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom( InitialPoint, zoom)); */

/*        for (int i=0;i<df.getPathWithHighestTotalCost().size()-1;i++)
        {
            for(Node node :nodes)

            {
                if(startpoint== node.getName()){
                    String name = node.getName();
                    String point = df.getPathWithHighestTotalCost().get(i);
                }



                }

            }*/

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
    public void creategraph(){
        //Madina-Zongo Junction
        double  a_latitude = 5.678470;
        double  a_longitude = -0.173056;

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
        double f_latitude =5.677594;
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
        double l_latitude =5.659603;
        double l_longitude =-0.164731;

        //ICAG Junction
        double m_latitude = 5.641538;
        double m_longitude = -0.169795;

        Node v_A =  new Node ( "[0]" ,"Madina-Zongo Junction" , 'A' , a_latitude, a_longitude);
        nodes.add(v_A);
        Node v_B =  new Node ("[1]" ,"Atomic Round About" , 'B' , b_latitude, b_longitude);
        nodes.add(v_B);
        Node v_C =  new Node ("[2]" ,"UPSA Junction" , 'C' , c_latitude, c_longitude);
        nodes.add(v_C);
        Node v_D =  new Node ("[3]" ,"Okponglo" , 'D' , d_latitude, d_longitude);
        nodes.add(v_D);
        Node v_E =  new Node ("[4]" ,"Hannah-Madina Junction" , 'E' , e_latitude, e_longitude);
        nodes.add(v_E);
        Node v_F =  new Node ("[5]" ,"Methodist Book Shop Junction" , 'F' , f_latitude, f_longitude);
        nodes.add(v_F);
        Node v_G =  new Node ("[6]","Presec Junction" , 'G' , g_latitude, g_longitude);
        nodes.add(v_G);
        Node v_H =  new Node ("[7]","Hannah-Presec Junction" , 'H' , h_latitude, h_longitude);
        nodes.add(v_H);
        Node v_I =  new Node ("[8]" ,"Hannah Junction" , 'I' , i_latitude, i_longitude);
        nodes.add(v_I);
        Node v_J =  new Node ("[9]" ,"Rawlings Cirlce" , 'J' , j_latitude, j_longitude);
        nodes.add(v_J);
        Node v_K =  new Node ("[10]" ,"UPSA-Presec Junction" , 'K' , k_latitude, k_longitude);
        nodes.add(v_K);
        Node v_L =  new Node ("[11]" ,"Madina-UPSA Junction" , 'L' , l_latitude, l_longitude);
        nodes.add(v_L);
        Node v_M =  new Node ("[12]" ,"ICAG Junction" , 'M' , m_latitude, m_longitude);
        nodes.add(v_M);
    }

}
