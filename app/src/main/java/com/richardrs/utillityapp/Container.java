package com.richardrs.utillityapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

public class Container extends AppCompatActivity {
    private int position;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);

        toolbar = findViewById(R.id.toolBar);

        FragmentManager fragmentmanager=getSupportFragmentManager();
        FragmentTransaction transaction = fragmentmanager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        final network Option_zero=new network();
        final battery Option_one=new battery();
        final Location Option_two=new Location();

        Bundle extras;
        extras = getIntent().getExtras();
        if(extras!=null){
            position = extras.getInt("choice");
            if(position==0){
                transaction.replace(R.id.fragment_loc, Option_zero);
                setTitle("Networks");
                setSupportActionBar(toolbar);
            }else if(position == 1){
                transaction.replace(R.id.fragment_loc,Option_one);
                setTitle("Battery");
                setSupportActionBar(toolbar);
            }else{
                transaction.replace(R.id.fragment_loc,Option_two);
                setTitle("Locations");
                setSupportActionBar(toolbar);
            }
            transaction.addToBackStack(null).commit();
        }else{
            finish();
        }

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.gg));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position==0){
                    getSupportFragmentManager().beginTransaction().remove(Option_zero).commit();
                }else if(position == 1){
                    getSupportFragmentManager().beginTransaction().remove(Option_one).commit();
                }else{
                    getSupportFragmentManager().beginTransaction().remove(Option_two).commit();}
                finish();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_bar_menu,menu);
        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            network Option_zero=new network();
            battery Option_one=new battery();
            Location Option_two=new Location();

            if(position==0){
                getSupportFragmentManager().beginTransaction().remove(Option_zero).commit();
            }else if(position == 1){
                getSupportFragmentManager().beginTransaction().remove(Option_one).commit();
            }else{
                getSupportFragmentManager().beginTransaction().remove(Option_two).commit();}
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
