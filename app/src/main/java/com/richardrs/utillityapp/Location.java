package com.richardrs.utillityapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
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

import static android.content.Context.MODE_PRIVATE;


public class Location extends Fragment {
    Handler handler = new Handler();
    Runnable runnable;
    double lat,lon;
    private static Context mContext;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mContext=context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View convertView = inflater.inflate(R.layout.fragment_location, container, false);
        final TextView Loc = (TextView) convertView.findViewById(R.id.loc_stat);
        final TextView Loctxt = (TextView) convertView.findViewById(R.id.gpstxt);
        final TextView Loc_reg = (TextView) convertView.findViewById(R.id.loc_reg);
        final TextView Loc_regtxt = (TextView) convertView.findViewById(R.id.gpsreg);
        final TextView Loc_city = (TextView) convertView.findViewById(R.id.loc_city);
        final TextView Loc_citytxt = (TextView) convertView.findViewById(R.id.gpscity);
        final Button batonz = (Button) convertView.findViewById(R.id.batons);
        Button map_but = (Button) convertView.findViewById(R.id.gomap);
        final TextView Loc_x = (TextView) convertView.findViewById(R.id.loc_x);
        final TextView Loc_xtxt = (TextView) convertView.findViewById(R.id.gpslat);
        final TextView Loc_y = (TextView) convertView.findViewById(R.id.loc_y);
        final TextView Loc_ytxt = (TextView) convertView.findViewById(R.id.gpslong);
        final TextView Loc_s = (TextView) convertView.findViewById(R.id.loc_s);
        final TextView Loc_stxt = (TextView) convertView.findViewById(R.id.gpsadd);

        final LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
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

                Geocoder gCoder = new Geocoder(mContext);
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
                Toast.makeText(mContext, "Please Turn on GPS", Toast.LENGTH_SHORT).show();
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
                if (mContext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && mContext.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                    Toast.makeText(mContext, "Gps Not found. Please Try Again", Toast.LENGTH_SHORT).show();
                }else{
                String uri = String.format(Locale.ENGLISH, "geo:0,0?q=", lat, lon);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                mContext.startActivity(intent);
                }
            }
        });

        SharedPreferences preferences = mContext.getSharedPreferences("Pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String hex1 = Color_translate(preferences.getInt("txtcolor",8));
        String hex2 = Color_translate(preferences.getInt("butcolor",1));
        String hex3 = Color_translate(preferences.getInt("buttxtcolor",4));
        Typeface hex4 = Option_translator(preferences.getInt("font",2));

        GradientDrawable shape = (GradientDrawable) batonz.getBackground();
        GradientDrawable shape2 = (GradientDrawable) map_but.getBackground();


        Loc.setTextColor(Color.parseColor(hex1));
        Loctxt.setTextColor(Color.parseColor(hex1));
        Loc_reg.setTextColor(Color.parseColor(hex1));
        Loc_regtxt.setTextColor(Color.parseColor(hex1));
        Loc_city.setTextColor(Color.parseColor(hex1));
        Loc_citytxt.setTextColor(Color.parseColor(hex1));
        Loc_x.setTextColor(Color.parseColor(hex1));
        Loc_xtxt.setTextColor(Color.parseColor(hex1));
        Loc_y.setTextColor(Color.parseColor(hex1));
        Loc_ytxt.setTextColor(Color.parseColor(hex1));
        Loc_s.setTextColor(Color.parseColor(hex1));
        Loc_stxt.setTextColor(Color.parseColor(hex1));

        shape.setColor(Color.parseColor(hex2));
        shape2.setColor(Color.parseColor(hex2));

        batonz.setTextColor(Color.parseColor(hex3));
        map_but.setTextColor(Color.parseColor(hex3));

        Loc.setTypeface(hex4);
        Loctxt.setTypeface(hex4);
        Loc_reg.setTypeface(hex4);
        Loc_regtxt.setTypeface(hex4);
        Loc_city.setTypeface(hex4);
        Loc_citytxt.setTypeface(hex4);
        Loc_x.setTypeface(hex4);
        Loc_xtxt.setTypeface(hex4);
        Loc_y.setTypeface(hex4);
        Loc_ytxt.setTypeface(hex4);
        Loc_s.setTypeface(hex4);
        Loc_stxt.setTypeface(hex4);

        return convertView;
    }

    public Typeface Option_translator(Integer choice){
        Typeface options4 ;

        if (choice == 0){
            options4 = ResourcesCompat.getFont(mContext,R.font.micross);
        }else if(choice == 1){
            options4 = ResourcesCompat.getFont(mContext,R.font.arial);
        }else if(choice == 2){
            options4 = ResourcesCompat.getFont(mContext,R.font.fransisco);
        }else{
            options4 = ResourcesCompat.getFont(mContext,R.font.times);;
        }
        return  options4;
    }
    public String Color_translate(int choice){
        String Hexcode = "";
        String ColorCode[] = {"#F35D5D","#42A5F5","#5D5D5D","#F7E35B","#FFFFFF", "#78F672","#E272F6","#F98950","#000000"};
        for(int i=0;i<=ColorCode.length;i++){
            if (choice == i){
                Hexcode = ColorCode[i];
            }
        }
        return Hexcode;
    }
}