package com.richardrs.utillityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothClass;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.method.TextKeyListener;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    Handler handler = new Handler();
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        TextView model = (TextView) findViewById(R.id.modell);
        TextView devname = (TextView) findViewById(R.id.textView3);
        final TextView time = (TextView) findViewById(R.id.tiem);
        final TextView dat = (TextView) findViewById(R.id.date);
        final LinearLayout netwoks = (LinearLayout) findViewById(R.id.netwoks);
        final LinearLayout locc = (LinearLayout) findViewById(R.id.locc);
        final LinearLayout batt = (LinearLayout) findViewById(R.id.batt);

        runnable = new Runnable() {
            public void run() {
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                String currenDay = new SimpleDateFormat("EEEE", Locale.getDefault()).format(new Date());
                time.setText(currentTime);
                dat.setText(currenDay+","+currentDate);
                handler.postDelayed(runnable, 900);
            }
        };
        handler.postDelayed(runnable, 500);

        String Modell = Build.MODEL;
        String Manufact = Build.MANUFACTURER;
        Manufact.substring(0,1).toUpperCase();
        Modell.substring(0,1).toUpperCase();
        model.setText(" "+Manufact+" "+Modell);

        String name = Settings.Secure.getString(getContentResolver(), "bluetooth_name");
        devname.setText("Welcome "+name);

        netwoks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Container.class);
                intent.putExtra("choice",0);
                startActivity(intent);
            }
        });
        batt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Container.class);
                intent.putExtra("choice",1);
                startActivity(intent);
            }
        });
        locc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Container.class);
                intent.putExtra("choice",2);
                startActivity(intent);
            }
        });
        }

}
