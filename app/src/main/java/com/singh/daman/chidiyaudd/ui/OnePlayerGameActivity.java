package com.singh.daman.chidiyaudd.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.singh.daman.chidiyaudd.R;
import com.singh.daman.chidiyaudd.model.Creature;
import com.singh.daman.chidiyaudd.utils.Constants;
import com.singh.daman.chidiyaudd.utils.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import at.markushi.ui.CircleButton;
import info.abdolahi.CircularMusicProgressBar;

public class OnePlayerGameActivity extends AppCompatActivity {

    private long time, itemCounterTime = 30;
    private int score = 0, bestScore = 0;
    private boolean checkTapOnce;
    private TextView tvTime, tvScore, tvItem1, tvName1, tvBestScore;
    private CircleButton circleButton1;
    private Creature creature;
    private CountDownTimer countDownTimer, delayTimer;
    private CircularMusicProgressBar circularMusicProgressBar;
    private int progress = 0, color1 = Color.BLUE;
    private String name1;
    private AlertDialog dialog;
    private JSONObject jsonObject;
    private Utility utility;
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_one_player_game);
        sharedpreferences = getSharedPreferences(Constants.spKey, Context.MODE_PRIVATE);

        name1 = getIntent().getStringExtra(Constants.player1);

        circleButton1 = (CircleButton) findViewById(R.id.circle_btn1);
        tvTime = (TextView) findViewById(R.id.time1);
        tvScore = (TextView) findViewById(R.id.score1);
        tvItem1 = (TextView) findViewById(R.id.tv_item1);
        tvName1 = (TextView) findViewById(R.id.tv_name1);
        tvBestScore = (TextView) findViewById(R.id.tv_best_score);
        circularMusicProgressBar = (CircularMusicProgressBar) findViewById(R.id.album_art);
        circularMusicProgressBar.setValue(progress);

        if(sharedpreferences != null){
            bestScore = sharedpreferences.getInt(Constants.score, 0);
        }
        tvBestScore.setText("Your personal best is: " + bestScore);

        circleButton1.setColor(color1);
        tvName1.setText(name1);
        delayTimer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished/1000 == 3){
                    tvItem1.setText("Ready...");
                }
                if(millisUntilFinished/1000 == 2){
                    tvItem1.setText("Steady...");
                }
                if(millisUntilFinished/1000 == 1){
                    tvItem1.setText("Go...");
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
        creature = new Creature();

        utility = new Utility(this, creature);
        try {
            jsonObject = new JSONObject(utility.readJSONFromAsset());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        utility.getRandomData(jsonObject);
        tvItem1.setText(creature.getName());
        int id = getResources().getIdentifier(creature.getImage(), "drawable", getPackageName());
        circularMusicProgressBar.setImageDrawable(ContextCompat.getDrawable(this, id));

        tvScore.setText("Score: " + score);

        startCounter();

        circleButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (time < itemCounterTime) {
                    if (!checkTapOnce) {
                        if (creature.getFlag() == 1) {
                            score += 2;
                        } else {
                            if(score > bestScore) {
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putInt(Constants.score, score);
                                editor.apply();
                            }
                            String title = "Oops! Score is: " + score;
                            showDialog(title,null);
                        }
                        checkTapOnce = true;
                        circleButton1.setClickable(false);
                    }
                    tvScore.setText("Score: " + score);
                }
            }
        });

    }

    public void startCounter() {
        countDownTimer = new CountDownTimer(itemCounterTime * 100, 100) {
            public void onTick(long millisUntilFinished) {
                time = millisUntilFinished / 100;
                tvTime.setText("Time: " + time);
            }

            public void onFinish() {
                time = itemCounterTime;
                utility.getRandomData(jsonObject);
                if(itemCounterTime > 0) {
                    itemCounterTime -= 1;
                }
                startCounter();
                tvItem1.setText(creature.getName());
                int id = getResources().getIdentifier(creature.getImage(), "drawable", getPackageName());
                circularMusicProgressBar.setImageDrawable(ContextCompat.getDrawable(OnePlayerGameActivity.this, id));
                checkTapOnce = false;
                circleButton1.setClickable(true);
                tvTime.setText("Time Up!");
                System.out.println(creature.getName());
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
                        Intent intent = new Intent(OnePlayerGameActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
