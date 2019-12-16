package com.richardrs.utillityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        final LinearLayout netwoks = (LinearLayout) findViewById(R.id.netwoks);
        final LinearLayout locc = (LinearLayout) findViewById(R.id.locc);
        final LinearLayout batt = (LinearLayout) findViewById(R.id.batt);

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
