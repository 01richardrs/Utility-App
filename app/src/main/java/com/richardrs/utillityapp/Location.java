package com.richardrs.utillityapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;


public class Location extends Fragment {
    Handler handler = new Handler();
    Runnable runnable;
    double lat,lon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View convertView = inflater.inflate(R.layout.fragment_location, container, false);
        final TextView Loc = (TextView) convertView.findViewById(R.id.loc_stat);
        final TextView Loc_reg = (TextView) convertView.findViewById(R.id.loc_reg);
        final TextView Loc_city = (TextView) convertView.findViewById(R.id.loc_city);
        final Button batonz = (Button) convertView.findViewById(R.id.batons);
        Button map_but = (Button) convertView.findViewById(R.id.gomap);
        final TextView Loc_x = (TextView) convertView.findViewById(R.id.loc_x);
        final TextView Loc_y = (TextView) convertView.findViewById(R.id.loc_y);
        final TextView Loc_s = (TextView) convertView.findViewById(R.id.loc_s);

        final LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        final String Provider = locationManager.getBestProvider(criteria,false);


        runnable = new Runnable() {
            public void run() {
                batonz.performClick();
                handler.postDelayed(runnable, 60000);
            }
        };

        handler.postDelayed(runnable, 1000);

        final LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(android.location.Location location) {
                lon = location.getLongitude();
                lat = location.getLatitude();
                Loc_x.setText(Double.toString(lon));
                Loc_y.setText(Double.toString(lat));

                Geocoder gCoder = new Geocoder(getContext());
                ArrayList<Address> addresses = null;
                try {
                    addresses = (ArrayList<Address>) gCoder.getFromLocation(lat,lon,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (addresses != null && addresses.size() > 0) {
                    Loc_reg.setText(addresses.get(0).getCountryName());
                    Loc_city.setText(addresses.get(0).getLocality());
                    Loc_s.setText(addresses.get(0).getAddressLine(0));

                    WebView webView = (WebView) convertView.findViewById(R.id.mywebview);
                    webView.getSettings().setJavaScriptEnabled(true);
                    String link = "https://www.google.com/maps/@";
                    String map = (String) link+lat+","+lon+"&output=classic";
                    webView.loadUrl(map);

                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(getContext(), "Please Turn on GPS", Toast.LENGTH_SHORT).show();
                Loc_x.setText("Not Found");
                Loc_y.setText("Not Found");
                Loc.setText("Offline");
                Loc_s.setText("Not Found.");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(i);
                    }
                }, 1500);

            }
        };

        batonz.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                                ,10);
                    }
                    return;
                }
                locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 0, listener);
                locationManager.requestLocationUpdates(Provider, 500, 0, listener);
                Loc_x.setText("Searching..");
                Loc_y.setText("Searching..");
                Loc_s.setText("Searching..");
                Loc.setText("Online");
    }
});

        map_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lat == 0 || lon ==0 ){
                    Toast.makeText(getContext(), "Gps Not found. Please Try Again", Toast.LENGTH_SHORT).show();
                }else{
                String uri = String.format(Locale.ENGLISH, "geo:0,0?q=", lat, lon);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                getContext().startActivity(intent);
                }
            }
        });

        return convertView;
    }
}