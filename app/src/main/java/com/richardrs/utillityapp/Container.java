package com.richardrs.utillityapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Container extends AppCompatActivity {
    public static Activity fa;
    private int position;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);

        fa= this;
        MenuView.ItemView maklo = findViewById(R.id.item1);
        ConstraintLayout bg = (ConstraintLayout) findViewById(R.id.backgroundd);
        toolbar = findViewById(R.id.toolBar);

        SharedPreferences preferences = getSharedPreferences("Pref", MODE_PRIVATE);
        String hex = Color_translate(preferences.getInt("bgcolor",4));
        bg.setBackgroundColor(Color.parseColor(hex));

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
    public void dothis(){
        Intent intent = new Intent(Container.this,Color_Setting.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                dothis();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
