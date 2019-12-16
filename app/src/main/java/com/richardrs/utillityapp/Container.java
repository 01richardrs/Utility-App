package com.richardrs.utillityapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.KeyEvent;

public class Container extends AppCompatActivity {
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);

        FragmentManager fragmentmanager=getSupportFragmentManager();
        FragmentTransaction transaction = fragmentmanager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        network Option_zero=new network();
        battery Option_one=new battery();
        Location Option_two=new Location();

        Bundle extras;
        extras = getIntent().getExtras();
        if(extras!=null){
            position = extras.getInt("choice");
            if(position==0){
                transaction.replace(R.id.fragment_loc, Option_zero);
            }else if(position == 1){
                transaction.replace(R.id.fragment_loc,Option_one);
            }else{
                transaction.replace(R.id.fragment_loc,Option_two);
            }
            transaction.addToBackStack(null).commit();
        }else{
            finish();
        }
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
