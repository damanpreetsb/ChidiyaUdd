package com.singh.daman.chidiyaudd.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.singh.daman.chidiyaudd.R;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Button btnOne = (Button) findViewById(R.id.btn_one);
        Button btnTwo = (Button) findViewById(R.id.btn_two);
        Button btnFour = (Button) findViewById(R.id.btn_four);
        Button btnExit = (Button) findViewById(R.id.btn_exit);
        textView = (TextView) findViewById(R.id.main_app_name);
        linearLayout = (LinearLayout) findViewById(R.id.layout_button);

        Boolean touch = getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH);
        if (touch) {
            Toast.makeText(this, "Device has Touch Screen Multi-Touch capability", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "No Touch Screen Multi-Touch capability!", Toast.LENGTH_LONG).show();
            btnTwo.setVisibility(View.GONE);
            btnFour.setVisibility(View.GONE);
        }

        Typeface appNameFont = Typeface.createFromAsset(getAssets(), "main_menu.ttf");
        textView.setTypeface(appNameFont);

        Typeface menuFont = Typeface.createFromAsset(getAssets(), "menu_font.ttf");
        btnOne.setTypeface(menuFont);
        btnTwo.setTypeface(menuFont);
        btnFour.setTypeface(menuFont);
        btnExit.setTypeface(menuFont);

        CountDownTimer countDownTimer = new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                translateAppName();
            }
        };
        countDownTimer.start();

        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TwoPlayerGameActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TwoPlayerSettingsActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TwoPlayerGameActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    public void translateAppName(){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int h = displaymetrics.heightPixels;
        textView.animate()
                .translationY(-h/4)
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        linearLayout.setVisibility(View.VISIBLE);
                        linearLayout.setAlpha(0.0f);
                        textView.setBackgroundResource(R.drawable.appname_back);
                        linearLayout.animate()
                                .alpha(1.0f)
                                .setDuration(1000)
                                .start();
                    }
                }).start();
    }
}
