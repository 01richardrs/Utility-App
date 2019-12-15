package com.richardrs.utillityapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class network extends Fragment {
    Handler handler = new Handler();
    Runnable runnable;
    TelephonyManager mTelephonyManager;
    network.MyPhoneStateListener myPhoneStateListener;
    int mSignalStrength;


    private static boolean isAirplaneModeOn(Context context) {

        return Settings.System.getInt(context.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;

    }

    class MyPhoneStateListener extends PhoneStateListener {

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength){
            super.onSignalStrengthsChanged(signalStrength);
            mSignalStrength = signalStrength.getGsmSignalStrength();
            mSignalStrength = (2* mSignalStrength) - 113;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View convertView = inflater.inflate(R.layout.fragment_network, container, false);
        final Button batons = (Button) convertView.findViewById(R.id.batons);
        myPhoneStateListener =  new MyPhoneStateListener();
        mTelephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        mTelephonyManager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);

        runnable = new Runnable() {
            public void run() {
                batons.performClick();
                handler.postDelayed(runnable, 2000);
            }
        };

        handler.postDelayed(runnable, 500);


        batons.setOnClickListener(new View.OnClickListener() {
                                      TextView Netw = (TextView) convertView.findViewById(R.id.Net);
                                      TextView Net_stat = (TextView) convertView.findViewById(R.id.Net_status);
                                      TextView Net_strength = (TextView) convertView.findViewById(R.id.Net_strength);
                                      TextView DL_speed = (TextView) convertView.findViewById(R.id.DL_speed);
                                      TextView UP_speed = (TextView) convertView.findViewById(R.id.UP_speed);
                                      ImageView Net_pic = (ImageView) convertView.findViewById(R.id.pics);
                                      int downSpeed = 0;
                                      int upSpeed = 0;
                                      @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

                                      @Override
                                      public void onClick(View v) {
                                          ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                                          WifiManager wifiManager = (WifiManager)getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                                          WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                                          int numberOfLevels = 6;
                                          int level = wifiManager.calculateSignalLevel(wifiInfo.getRssi(),numberOfLevels);

                                          if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                                                  connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                                              if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
                                                  Netw.setText("Wifi");

                                                  if(level == 5){
                                                      Net_strength.setText("Strong Signal");
                                                      Net_pic.setImageResource(R.drawable.wifi4);
                                                  }
                                                  else{
                                                      if(level == 4){Net_strength.setText("Good");
                                                          Net_pic.setImageResource(R.drawable.wifi3);
                                                      }
                                                      else{
                                                          if(level==3){
                                                              Net_strength.setText("Fair Signal");
                                                              Net_pic.setImageResource(R.drawable.wifi2);}
                                                          else{
                                                              Net_strength.setText("Low Signal");
                                                              Net_pic.setImageResource(R.drawable.wifi1);
                                                          }
                                                      }
                                                  }
                                              }else{
                                                  if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED){
                                                      Netw.setText("Mobile Connection");

                                                      if (mSignalStrength > 60){
                                                          Net_strength.setText("Strong Signal");
                                                          Net_pic.setImageResource(R.drawable.net5);
                                                      }else{
                                                          if(mSignalStrength> 50 && mSignalStrength < 60){Net_strength.setText("Good");
                                                              Net_pic.setImageResource(R.drawable.net4);}
                                                          else{
                                                              if(mSignalStrength> 35 && mSignalStrength < 50){
                                                                  Net_strength.setText("Fair Signal");
                                                                  Net_pic.setImageResource(R.drawable.net3);}
                                                              else {
                                                                  if (mSignalStrength > 25 && mSignalStrength < 35) {
                                                                      Net_strength.setText("Good");
                                                                      Net_pic.setImageResource(R.drawable.net2);
                                                                  } else {
                                                                      Net_pic.setImageResource(R.drawable.net2);
                                                                      Net_strength.setText("Low Signal");
                                                                  }
                                                              }
                                                          }
                                                      }

                                                  }
                                              }
                                              Net_stat.setText("Connected.");


                                              NetworkCapabilities nc = null;
                                              if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                                  nc = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                                              }
                                              int downSpeed = (nc.getLinkDownstreamBandwidthKbps())/1000;
                                              int upSpeed = (nc.getLinkUpstreamBandwidthKbps())/1000;

                                              if (downSpeed>1000){
                                                  DL_speed.setText(""+((double)downSpeed/1000)+"Gbs/Sec");
                                              }else{
                                                  DL_speed.setText(""+(downSpeed)+"Mbs/Sec");
                                              }

                                              if (upSpeed>1000){
                                                  UP_speed.setText(""+((double)upSpeed/1000)+"Gbs/Sec");
                                              }else{
                                                  UP_speed.setText(""+(upSpeed)+"Mbs/Sec");
                                              }

                                          }
                                          else {
                                              Net_pic.setImageResource(R.drawable.nonet);
                                              Netw.setText("No Connection.");
                                              Net_stat.setText("No Internet.");
                                              Net_strength.setText("No Signal.");
                                              DL_speed.setText(""+(downSpeed)+"Mbs");
                                              UP_speed.setText(""+(upSpeed)+"Mbs");
                                              if (isAirplaneModeOn(getContext()) == true){
                                                  Net_pic.setImageResource(R.drawable.plane);
                                                  Netw.setText("Airplane Mode.");
                                              }
                                          }
                                      }

                                  }
        );

        Button Net_setting = (Button) convertView.findViewById(R.id.batonn);
        Button Wifi_setting = (Button) convertView.findViewById(R.id.batonw);

        Net_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS);
                startActivity(i);
            }
        });
        Wifi_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Settings.ACTION_WIFI_SETTINGS);
                startActivity(i);
            }
        });
        batons.performClick();

        return convertView;
    }
}