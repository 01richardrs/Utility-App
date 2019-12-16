package com.richardrs.utillityapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import static android.content.Context.BATTERY_SERVICE;


public class battery extends Fragment {
    Handler handler = new Handler();
    Runnable runnable;
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View convertView = inflater.inflate(R.layout.fragment_battery, container, false);
        final TextView bat_stat = (TextView) convertView.findViewById(R.id.bat_stat);
        final TextView bat_perc = (TextView) convertView.findViewById(R.id.bat_perc);
        final TextView bat_health = (TextView) convertView.findViewById(R.id.bat_health);
        final TextView bat_save = (TextView) convertView.findViewById(R.id.bat_save);
        final TextView bat_sizes = (TextView) convertView.findViewById(R.id.bat_size);
        final TextView bat_tek = (TextView) convertView.findViewById(R.id.bat_tech);
        final ImageView img_battery = (ImageView) convertView.findViewById(R.id.pics);
        final Button batonz = (Button) convertView.findViewById(R.id.batons);

        runnable = new Runnable() {
            public void run() {
                batonz.performClick();
                handler.postDelayed(runnable, 2000);
            }
        };

        handler.postDelayed(runnable, 500);

        batonz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                 Intent batteryStat = mContext.registerReceiver(null,ifilter);
                 BatteryManager bm = (BatteryManager)mContext.getSystemService(BATTERY_SERVICE);

                int level = batteryStat.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int status = batteryStat.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
                int chargePlug = batteryStat.getIntExtra(BatteryManager.EXTRA_PLUGGED,-1);
                double batteryCapacity = getBatteryCapacity(mContext);
                boolean isCharge = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;
                boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
                boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

                String tech = batteryStat.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
                String Bat_lev = Integer.toString(level);
                String bat_cap = Double.toString(batteryCapacity);
                String temp = batteryTemperature(mContext);

                bat_tek.setText(tech);
                bat_perc.setText(Bat_lev+" %");
                bat_sizes.setText(bat_cap+"mAH");
                bat_save.setText(temp);

                if(level > 80)
                    if(isCharge){
                        img_battery.setImageResource(R.drawable.bat5chrge);
                    }else{
                        img_battery.setImageResource(R.drawable.bat5);
                    }
                else if(level > 60)
                    if(isCharge){
                        img_battery.setImageResource(R.drawable.bat4chrge);
                    }else{
                        img_battery.setImageResource(R.drawable.bat4);
                    }
                else if(level > 40)
                    if(isCharge){
                        img_battery.setImageResource(R.drawable.bat3chrge);
                    }else{
                        img_battery.setImageResource(R.drawable.bat3);
                    }
                else if(level > 20)
                    if(isCharge){
                        img_battery.setImageResource(R.drawable.bat2chrge);
                    }else{
                        img_battery.setImageResource(R.drawable.bat2);
                    }
                else if(level < 20)
                    if(isCharge){
                        img_battery.setImageResource(R.drawable.bat1chrge);
                    }else{
                        img_battery.setImageResource(R.drawable.bat1);
                    }
                else
                    img_battery.setImageResource(R.drawable.bat0);

                int deviceHealth = batteryStat.getIntExtra(BatteryManager.EXTRA_HEALTH,0);

                if(deviceHealth == bm.BATTERY_HEALTH_COLD){
                    bat_health.setText("Cold");
                }
                if(deviceHealth == bm.BATTERY_HEALTH_DEAD){
                    bat_health.setText("Dead");
                }
                if (deviceHealth == bm.BATTERY_HEALTH_GOOD){
                    bat_health.setText("Good");
                }
                if(deviceHealth == bm.BATTERY_HEALTH_OVERHEAT){
                    bat_health.setText("OverHeat");
                }
                if (deviceHealth == bm.BATTERY_HEALTH_OVER_VOLTAGE){
                    bat_health.setText("Over voltage");
                }
                if (deviceHealth == bm.BATTERY_HEALTH_UNKNOWN){
                    bat_health.setText(" = Unknown");
                }
                if (deviceHealth == bm.BATTERY_HEALTH_UNSPECIFIED_FAILURE){
                    bat_health.setText(" = Unspecified Failure");
                }

                if (isCharge == true){
                    if(usbCharge==true){
                        bat_stat.setText("USB Charging");
                    }else{
                        if(acCharge==true){
                            bat_stat.setText("AC Charging");
                        }else{
                            bat_stat.setText("Wireless Charging");
                        }
                    }
                }else{
                    bat_stat.setText("Not Charging");
                }

            }
        });

        return convertView;
    }
    public static String batteryTemperature(Context context)
    {
        Intent intent = mContext.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        float  temp   = ((float) intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0)) / 10;

        return String.valueOf(temp) + "C";
    }
    @SuppressLint("PrivateApi")
    private double getBatteryCapacity(Context context) {
        Object mPowerProfile;
        double batteryCapacity = 0;
        final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";

        try {
            mPowerProfile = Class.forName(POWER_PROFILE_CLASS)
                    .getConstructor(Context.class)
                    .newInstance(context);

            batteryCapacity = (double) Class
                    .forName(POWER_PROFILE_CLASS)
                    .getMethod("getBatteryCapacity")
                    .invoke(mPowerProfile);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return batteryCapacity;

    }
}