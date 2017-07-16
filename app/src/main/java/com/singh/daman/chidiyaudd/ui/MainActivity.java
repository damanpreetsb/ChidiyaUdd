package com.singh.daman.chidiyaudd.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import com.singh.daman.chidiyaudd.R;
import com.singh.daman.chidiyaudd.utils.Constants;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private LinearLayout linearLayout;
    private String name;
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

//        Boolean touch = getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH);
//        if (touch) {
//            Toast.makeText(this, "Device has Touch Screen Multi-Touch capability", Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(this, "No Touch Screen Multi-Touch capability!", Toast.LENGTH_LONG).show();
//            btnTwo.setVisibility(View.GONE);
//            btnFour.setVisibility(View.GONE);
//        }

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
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(MainActivity.this);
                View mView = layoutInflaterAndroid.inflate(R.layout.dialog_edit_text, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilderUserInput.setView(mView);

                final EditText etName = (EditText) mView.findViewById(R.id.userInputDialog);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                if (!TextUtils.isEmpty(etName.getText().toString()))
                                    name = etName.getText().toString().toUpperCase();
                                else
                                    name = Constants.player1;
                                Intent intent = new Intent(MainActivity.this, OnePlayerGameActivity.class);
                                intent.putExtra(Constants.player1, name);
                                startActivity(intent);
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.setCancelable(false);
                alertDialogAndroid.show();
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
                Intent intent = new Intent(MainActivity.this, FourPlayerSettingsActivity.class);
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
