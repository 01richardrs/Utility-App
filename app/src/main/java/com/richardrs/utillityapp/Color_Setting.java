package com.richardrs.utillityapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Activity;
import android.app.MediaRouteButton;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

public class Color_Setting extends AppCompatActivity {
    public static Activity fa;
    private androidx.appcompat.widget.Toolbar toolbar;
    private final String ColorOptions[] = {"Calm Red", "Light Blue", "Light Grey", "Yellow Lemon", "Pure White", "Pastel Green",
            "Easy Purple", "Lite Orange", "Dark Black"};
    private final String ColorCode[] = {"#F35D5D", "#42A5F5", "#5D5D5D", "#F7E35B", "#FFFFFF", "#78F672", "#E272F6", "#F98950", "#000000"};
    private final String FontOptions[] = {"Sans Serif", "Arial", "San Fransisco", "Times New Roman"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color__setting);

        fa= this;
        toolbar = findViewById(R.id.toolBar);
        final Spinner bgcolor = (Spinner) findViewById(R.id.bgcolor);
        final Spinner textcolor = (Spinner) findViewById(R.id.textcolor);
        final Spinner butcolor = (Spinner) findViewById(R.id.butcolor);
        final Spinner buttextcolor = (Spinner) findViewById(R.id.buttexcolor);
        final Spinner fonttext = (Spinner) findViewById(R.id.textfont);
        LinearLayout bg = (LinearLayout) findViewById(R.id.backgrounds);

        final Button default_color = (Button) findViewById(R.id.deaful);
        Button saves = (Button) findViewById(R.id.sav);

        final Switch darkmod = (Switch) findViewById(R.id.switcher);

        final SharedPreferences preferences = getSharedPreferences("Pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(bgcolor);
            android.widget.ListPopupWindow popupWindow1 = (android.widget.ListPopupWindow) popup.get(textcolor);
            android.widget.ListPopupWindow popupWindow2 = (android.widget.ListPopupWindow) popup.get(butcolor);
            android.widget.ListPopupWindow popupWindow3 = (android.widget.ListPopupWindow) popup.get(buttextcolor);
            android.widget.ListPopupWindow popupWindow4 = (android.widget.ListPopupWindow) popup.get(fonttext);
            popupWindow.setHeight(460);
            popupWindow1.setHeight(460);
            popupWindow2.setHeight(460);
            popupWindow3.setHeight(460);
            popupWindow4.setHeight(460);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {

        }

        final ArrayAdapter<String> ColorAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, ColorOptions);
        ArrayAdapter<String> FontAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, FontOptions);
        ColorAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        FontAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);

        bgcolor.setAdapter(ColorAdapter);
        textcolor.setAdapter(ColorAdapter);
        butcolor.setAdapter(ColorAdapter);
        buttextcolor.setAdapter(ColorAdapter);
        fonttext.setAdapter(FontAdapter);

        setTitle("Settings");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.gg));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bgcolor.setSelection(preferences.getInt("bgcolor",4));
        textcolor.setSelection(preferences.getInt("txtcolor",8));
        butcolor.setSelection(preferences.getInt("butcolor",1));
        buttextcolor.setSelection(preferences.getInt("buttxtcolor",4));
        fonttext.setSelection(preferences.getInt("font",2));
        darkmod.setChecked(preferences.getBoolean("dmode",false));


        bgcolor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ChangeForm("bg", ColorCode[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        textcolor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ChangeForm("fcolor", ColorCode[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        butcolor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ChangeForm("btn", ColorCode[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        buttextcolor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ChangeForm("btntxt", ColorCode[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        fonttext.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ChangeForm("ffam", Integer.toString(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        saves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Color_Setting.this, Container.class);
                SharedPreferences preferences = getSharedPreferences("Pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("bgcolor",bgcolor.getSelectedItemPosition());
                editor.putInt("txtcolor",textcolor.getSelectedItemPosition());
                editor.putInt("butcolor",butcolor.getSelectedItemPosition());
                editor.putInt("buttxtcolor",buttextcolor.getSelectedItemPosition());
                editor.putInt("font",fonttext.getSelectedItemPosition());
                editor.putBoolean("dmode",darkmod.isChecked());
                editor.apply();
                finish();
                Container.fa.finish();
                Toast.makeText(Color_Setting.this, "Changes Are Applied.",
                        Toast.LENGTH_LONG).show();
            }
        });

        default_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bgcolor.setSelection(4);
                textcolor.setSelection(8);
                butcolor.setSelection(1);
                buttextcolor.setSelection(4);
                fonttext.setSelection(2);
                darkmod.setChecked(false);
            }
        });

        darkmod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bgcolor.setSelection(2);
                    textcolor.setSelection(4);
                    butcolor.setSelection(8);
                    buttextcolor.setSelection(4);
                    fonttext.setSelection(2);
                } else {
                    default_color.performClick();
                }
            }
        });


    }

    public void ChangeForm(String choice, String options) {
        LinearLayout bg = (LinearLayout) findViewById(R.id.backgrounds);
        TextView bgcolortxt = (TextView) findViewById(R.id.bgtext);
        TextView txtcolor = (TextView) findViewById(R.id.txtcolor);
        TextView btncolor = (TextView) findViewById(R.id.btnclr);
        TextView btntxtcolor = (TextView) findViewById(R.id.btntexclr);
        TextView fchoice = (TextView) findViewById(R.id.fchoice);
        TextView dmode = (TextView) findViewById(R.id.dmode);
        Button defaults = (Button) findViewById(R.id.deaful);
        Button sav = (Button) findViewById(R.id.sav);
        GradientDrawable shape = (GradientDrawable) defaults.getBackground();
        GradientDrawable shape2 = (GradientDrawable) sav.getBackground();
        Typeface face;

        if (choice == "bg") {
            bg.setBackgroundColor(Color.parseColor(options));
        } else if (choice == "fcolor") {
            bgcolortxt.setTextColor(Color.parseColor(options));
            txtcolor.setTextColor(Color.parseColor(options));
            btncolor.setTextColor(Color.parseColor(options));
            btntxtcolor.setTextColor(Color.parseColor(options));
            fchoice.setTextColor(Color.parseColor(options));
            dmode.setTextColor(Color.parseColor(options));
        } else if (choice == "btn") {
            shape.setColor(Color.parseColor(options));
            shape2.setColor(Color.parseColor(options));
        } else if (choice == "btntxt") {
            defaults.setTextColor(Color.parseColor(options));
            sav.setTextColor(Color.parseColor(options));
        } else if (choice == "ffam") {
            int choices = Integer.parseInt(options);
            if (choices == 0) {
                face = ResourcesCompat.getFont(this, R.font.micross);
            } else if (choices == 1) {
                face = ResourcesCompat.getFont(this, R.font.arial);
            } else if (choices == 2) {
                face = ResourcesCompat.getFont(this, R.font.fransisco);
            } else {
                face = ResourcesCompat.getFont(this, R.font.times);
            }
            bgcolortxt.setTypeface(face);
            txtcolor.setTypeface(face);
            btncolor.setTypeface(face);
            btntxtcolor.setTypeface(face);
            fchoice.setTypeface(face);
            dmode.setTypeface(face);
            defaults.setTypeface(face);
            sav.setTypeface(face);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            Toast.makeText(Color_Setting.this, "Changes Are not made.",
                    Toast.LENGTH_LONG).show();
        } return true;
}
}
