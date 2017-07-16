package com.singh.daman.chidiyaudd.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.singh.daman.chidiyaudd.R;
import com.singh.daman.chidiyaudd.model.Creature;
import com.singh.daman.chidiyaudd.utils.Constants;
import com.singh.daman.chidiyaudd.utils.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import at.markushi.ui.CircleButton;
import info.abdolahi.CircularMusicProgressBar;

public class TwoPlayerGameActivity extends AppCompatActivity {
    private long time, totalSeconds, intervalSeconds = 1, itemCounterTime = 10;
    private int score = 0, score2 = 0;
    private boolean checkTapOnce, checkTapOnce2;
    private TextView tvTime, tvTime2, tvScore, tvScore2, tvItem1, tvItem2, tvName1, tvName2;
    private CircleButton circleButton1, circleButton2;
    private Creature creature;
    private CountDownTimer countDownTimer, totalCountDownTimer, delayTimer;
    private String message = "";
    private CircularMusicProgressBar circularMusicProgressBar;
    private int progress = 0, color1 = Color.BLUE, color2 = Color.RED;
    private String name1, name2;
    private AlertDialog dialog;
    private JSONObject jsonObject;
    private Utility utility;
    private InterstitialAd interstitialAd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        interstitialAd= new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);

        color1 = getIntent().getIntExtra(Constants.color1, Color.BLUE);
        color2 = getIntent().getIntExtra(Constants.color2, Color.RED);
        name1 = getIntent().getStringExtra(Constants.player1);
        name2 = getIntent().getStringExtra(Constants.player2);
        totalSeconds = getIntent().getIntExtra(Constants.time, 60);
        itemCounterTime = getIntent().getIntExtra(Constants.modeTime, 20);

        circleButton1 = (CircleButton) findViewById(R.id.circle_btn1);
        circleButton2 = (CircleButton) findViewById(R.id.circle_btn2);
        tvTime = (TextView) findViewById(R.id.time1);
        tvTime2 = (TextView) findViewById(R.id.time2);
        tvScore = (TextView) findViewById(R.id.score1);
        tvScore2 = (TextView) findViewById(R.id.score2);
        tvItem1 = (TextView) findViewById(R.id.tv_item1);
        tvItem2 = (TextView) findViewById(R.id.tv_item2);
        tvName1 = (TextView) findViewById(R.id.tv_name1);
        tvName2 = (TextView) findViewById(R.id.tv_name2);
        circularMusicProgressBar = (CircularMusicProgressBar) findViewById(R.id.album_art);
        circularMusicProgressBar.setValue(progress);

        circleButton1.setColor(color1);
        circleButton2.setColor(color2);
        tvName1.setText(name1);
        tvName2.setText(name2);

        delayTimer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished/1000 == 3){
                    tvItem1.setText("Ready...");
                    tvItem2.setText("Ready...");
                }
                if(millisUntilFinished/1000 == 2){
                    tvItem1.setText("Steady...");
                    tvItem2.setText("Steady...");
                }
                if(millisUntilFinished/1000 == 1){
                    tvItem1.setText("Go...");
                    tvItem2.setText("Go...");
                }
            }

            @Override
            public void onFinish() {
                playGame();
            }
        };
        delayTimer.start();

    }

    public void playGame(){
        totalCountDownTimer = new CountDownTimer(totalSeconds * 1000, intervalSeconds * 1000) {

            public void onTick(long millisUntilFinished) {
                progress = (int) (Math.round(totalSeconds * 1000 - millisUntilFinished) / (10 * totalSeconds));
                Log.d("seconds elapsed: ", totalSeconds + " " + millisUntilFinished + " " + progress);
                circularMusicProgressBar.setValue(progress);
            }

            public void onFinish() {
                progress = (int) (Math.round(totalSeconds * 1000) / (10 * totalSeconds));
                circularMusicProgressBar.setValue(progress);
                Log.d("done!", "Time's up!");
                if (score2 > score) {
                    message = "Winner is " + name2;
                    String dare = "Dare: " + Utility.getRandomDare();
                    showDialog(message, dare);
                } else if (score2 < score) {
                    message = "Winner is " + name1;
                    String dare = "Dare: " + Utility.getRandomDare();
                    showDialog(message, dare);
                } else {
                    message = "Match is tie";
                    showDialog(message, null);
                }
            }

        };
        totalCountDownTimer.start();
        creature = new Creature();

        utility = new Utility(this, creature);
        try {
            jsonObject = new JSONObject(utility.readJSONFromAsset());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        utility.getRandomData(jsonObject);
        tvItem1.setText(creature.getName());
        tvItem2.setText(creature.getName());
        int id = getResources().getIdentifier(creature.getImage(), "drawable", getPackageName());
        circularMusicProgressBar.setImageDrawable(ContextCompat.getDrawable(this, id));

        tvScore.setText("Score: " + score);
        tvScore2.setText("Score: " + score2);

        startCounter();

        circleButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (time < itemCounterTime) {
                    if (!checkTapOnce) {
                        if (creature.getFlag() == 1) {
                            score += 2;
                        } else {
                            score -= 1;
                        }
                        checkTapOnce = true;
                        circleButton1.setClickable(false);
                    }
                    tvScore.setText("Score: " + score);
                }
            }
        });

        circleButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (time < itemCounterTime) {
                    if (!checkTapOnce2) {
                        if (creature.getFlag() == 1) {
                            score2 += 2;
                        } else {
                            score2 -= 1;
                        }
                        checkTapOnce2 = true;
                        circleButton2.setClickable(false);
                    }
                    tvScore2.setText("Score: " + score2);
                }
            }
        });
    }

    public void startCounter() {
        countDownTimer = new CountDownTimer(itemCounterTime * 100, 100) {
            public void onTick(long millisUntilFinished) {
                time = millisUntilFinished / 100;
                tvTime.setText("Time: " + time);
                tvTime2.setText("Time: " + time);
            }

            public void onFinish() {
                time = itemCounterTime;
                utility.getRandomData(jsonObject);
                startCounter();
                tvItem1.setText(creature.getName());
                tvItem2.setText(creature.getName());
                int id = getResources().getIdentifier(creature.getImage(), "drawable", getPackageName());
                circularMusicProgressBar.setImageDrawable(ContextCompat.getDrawable(TwoPlayerGameActivity.this, id));
                checkTapOnce = false;
                checkTapOnce2 = false;
                circleButton1.setClickable(true);
                circleButton2.setClickable(true);
                tvTime.setText("Time Up!");
                tvTime2.setText("Time Up!");
            }
        };
        countDownTimer.start();
    }

    public void showDialog(String msg, String dare) {

        countDownTimer.cancel();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(msg)
                .setMessage(dare)
                .setPositiveButton("PLAY AGAIN", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = getIntent();
                        startActivity(intent);
                        finish();
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                })
                .setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (interstitialAd.isLoaded()) {
                            interstitialAd.show();
                            interstitialAd.setAdListener(new AdListener() {
                                @Override
                                public void onAdClosed() {
                                    super.onAdClosed();
                                    Intent intent = new Intent(TwoPlayerGameActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                }
                            });
                        }
                        else{
                            Intent intent = new Intent(TwoPlayerGameActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        }
                    }
                });
        dialog = builder.create();
        dialog.show();
        dialog.setCancelable(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dialog != null) {
            dialog.cancel();
        }
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (totalCountDownTimer != null) {
            totalCountDownTimer.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });
        }else{
            super.onBackPressed();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }
}
