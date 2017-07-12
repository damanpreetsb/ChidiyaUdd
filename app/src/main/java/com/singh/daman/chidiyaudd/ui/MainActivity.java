package com.singh.daman.chidiyaudd.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.singh.daman.chidiyaudd.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Button btnOne = (Button) findViewById(R.id.btn_one);
        Button btnTwo = (Button) findViewById(R.id.btn_two);
        Button btnFour = (Button) findViewById(R.id.btn_four);
        Button btnExit = (Button) findViewById(R.id.btn_exit);

        Typeface menuFont = Typeface.createFromAsset(getAssets(), "menu_font.ttf");
        btnOne.setTypeface(menuFont);
        btnTwo.setTypeface(menuFont);
        btnFour.setTypeface(menuFont);
        btnExit.setTypeface(menuFont);

        Boolean touch = getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH);
        if (touch) {
            Toast.makeText(this, "Device has Touch Screen Multi-Touch capability", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "No Touch Screen Multi-Touch capability!", Toast.LENGTH_LONG).show();
            btnTwo.setVisibility(View.GONE);
            btnFour.setVisibility(View.GONE);
        }

        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TwoPlayerGameActivity.class));
            }
        });
        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TwoPlayerSettingsActivity.class));
            }
        });
        btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TwoPlayerGameActivity.class));
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
