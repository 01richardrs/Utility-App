package com.richardrs.utillityapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"Check Networks", "Check Battery", "Check GPS Locations"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                FragmentManager fragmentmanager=getSupportFragmentManager();
                FragmentTransaction transaction = fragmentmanager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                network Option_zero=new network();
                battery Option_one=new battery();
                Location Option_two=new Location();

                switch (position){
                    case 0:
                        transaction.replace(android.R.id.content, Option_zero);
                        break;
                    case 1:
                        transaction.replace(android.R.id.content, Option_one);
                        break;
                    case 2:
                        transaction.replace(android.R.id.content, Option_two);
                    break;
                }
                transaction.addToBackStack(null).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        }

}
