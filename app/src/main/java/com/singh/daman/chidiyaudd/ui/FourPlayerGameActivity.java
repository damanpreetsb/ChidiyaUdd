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

import com.singh.daman.chidiyaudd.R;
import com.singh.daman.chidiyaudd.model.Creature;
import com.singh.daman.chidiyaudd.utils.Constants;
import com.singh.daman.chidiyaudd.utils.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import at.markushi.ui.CircleButton;
import info.abdolahi.CircularMusicProgressBar;

public class FourPlayerGameActivity extends AppCompatActivity {
    private long time, totalSeconds, intervalSeconds = 1, itemCounterTime = 10;
    private int score = 0, score2 = 0, score3 = 0, score4 = 0;
    private boolean checkTapOnce, checkTapOnce2, checkTapOnce3, checkTapOnce4;
    private TextView tvTime, tvTime2, tvTime3, tvTime4, tvScore, tvScore2, tvScore3, tvScore4,
            tvItem1, tvItem2, tvItem3, tvItem4, tvName1, tvName2, tvName3, tvName4;
    private CircleButton circleButton1, circleButton2, circleButton3, circleButton4;
    private Creature creature;
    private CountDownTimer countDownTimer, totalCountDownTimer, delayTimer;
    private String message = "";
    private CircularMusicProgressBar circularMusicProgressBar, circularMusicProgressBar2;
    private int progress = 0, color1 = Color.BLUE, color2 = Color.RED, color3, color4;
    private String name1, name2, name3, name4;
    private AlertDialog dialog;
    private JSONObject jsonObject;
    private Utility utility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_four_player_game);

        color1 = getIntent().getIntExtra(Constants.color1, Color.BLUE);
        color2 = getIntent().getIntExtra(Constants.color2, Color.RED);
        color3 = getIntent().getIntExtra(Constants.color3, Color.GREEN);
        color4= getIntent().getIntExtra(Constants.color4, Color.CYAN);
        name1 = getIntent().getStringExtra(Constants.player1);
        name2 = getIntent().getStringExtra(Constants.player2);
        name3 = getIntent().getStringExtra(Constants.player3);
        name4 = getIntent().getStringExtra(Constants.player4);
        totalSeconds = getIntent().getIntExtra(Constants.time, 60);
        itemCounterTime = getIntent().getIntExtra(Constants.modeTime, 20);

        circleButton1 = (CircleButton) findViewById(R.id.circle_btn1);
        circleButton2 = (CircleButton) findViewById(R.id.circle_btn2);
        circleButton3 = (CircleButton) findViewById(R.id.circle_btn3);
        circleButton4 = (CircleButton) findViewById(R.id.circle_btn4);
        tvTime = (TextView) findViewById(R.id.time1);
        tvTime2 = (TextView) findViewById(R.id.time2);
        tvTime3 = (TextView) findViewById(R.id.time3);
        tvTime4 = (TextView) findViewById(R.id.time4);
        tvScore = (TextView) findViewById(R.id.score1);
        tvScore2 = (TextView) findViewById(R.id.score2);
        tvScore3 = (TextView) findViewById(R.id.score3);
        tvScore4 = (TextView) findViewById(R.id.score4);
        tvItem1 = (TextView) findViewById(R.id.tv_item1);
        tvItem2 = (TextView) findViewById(R.id.tv_item2);
        tvItem3 = (TextView) findViewById(R.id.tv_item3);
        tvItem4 = (TextView) findViewById(R.id.tv_item4);
        tvName1 = (TextView) findViewById(R.id.tv_name1);
        tvName2 = (TextView) findViewById(R.id.tv_name2);
        tvName3 = (TextView) findViewById(R.id.tv_name3);
        tvName4 = (TextView) findViewById(R.id.tv_name4);
        circularMusicProgressBar = (CircularMusicProgressBar) findViewById(R.id.album_art);
        circularMusicProgressBar2 = (CircularMusicProgressBar) findViewById(R.id.album_art2);
        circularMusicProgressBar.setValue(progress);
        circularMusicProgressBar2.setValue(progress);

        circleButton1.setColor(color1);
        circleButton2.setColor(color2);
        circleButton3.setColor(color3);
        circleButton4.setColor(color4);
        tvName1.setText(name1);
        tvName2.setText(name2);
        tvName3.setText(name3);
        tvName4.setText(name4);

        delayTimer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished/1000 == 3){
                    tvItem1.setText("Ready...");
                    tvItem2.setText("Ready...");
                    tvItem3.setText("Ready...");
                    tvItem4.setText("Ready...");
                }
                if(millisUntilFinished/1000 == 2){
                    tvItem1.setText("Steady...");
                    tvItem2.setText("Steady...");
                    tvItem3.setText("Steady...");
                    tvItem4.setText("Steady...");
                }
                if(millisUntilFinished/1000 == 1){
                    tvItem1.setText("Go...");
                    tvItem2.setText("Go...");
                    tvItem3.setText("Go...");
                    tvItem4.setText("Go...");
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
                circularMusicProgressBar2.setValue(progress);
            }

            public void onFinish() {
                progress = (int) (Math.round(totalSeconds * 1000) / (10 * totalSeconds));
                circularMusicProgressBar.setValue(progress);
                circularMusicProgressBar2.setValue(progress);
                Log.d("done!", "Time's up!");
                if (score > score2 && score > score3 && score > score4) {
                    message = "Winner is " + name1;
                    String dare = "Dare: " + Utility.getRandomDare();
                    showDialog(message, dare);
                } else if (score2 > score && score2 > score3 && score2 > score4) {
                    message = "Winner is " + name2;
                    String dare = "Dare: " + Utility.getRandomDare();
                    showDialog(message, dare);
                } else if (score3 > score && score3 > score2 && score3 > score4) {
                    message = "Winner is " + name3;
                    String dare = "Dare: " + Utility.getRandomDare();
                    showDialog(message, dare);
                } else if (score4 > score && score4 > score2 && score4 > score2) {
                    message = "Winner is " + name4;
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
        tvItem3.setText(creature.getName());
        tvItem4.setText(creature.getName());
        int id = getResources().getIdentifier(creature.getImage(), "drawable", getPackageName());
        circularMusicProgressBar.setImageDrawable(ContextCompat.getDrawable(this, id));
        circularMusicProgressBar2.setImageDrawable(ContextCompat.getDrawable(this, id));

        tvScore.setText("Score: " + score);
        tvScore2.setText("Score: " + score2);
        tvScore3.setText("Score: " + score3);
        tvScore4.setText("Score: " + score4);

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

        circleButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (time < itemCounterTime) {
                    if (!checkTapOnce3) {
                        if (creature.getFlag() == 1) {
                            score3 += 2;
                        } else {
                            score3 -= 1;
                        }
                        checkTapOnce3 = true;
                        circleButton3.setClickable(false);
                    }
                    tvScore3.setText("Score: " + score3);
                }
            }
        });

        circleButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (time < itemCounterTime) {
                    if (!checkTapOnce4) {
                        if (creature.getFlag() == 1) {
                            score4 += 2;
                        } else {
                            score4 -= 1;
                        }
                        checkTapOnce4 = true;
                        circleButton4.setClickable(false);
                    }
                    tvScore4.setText("Score: " + score4);
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
                tvTime3.setText("Time: " + time);
                tvTime4.setText("Time: " + time);
            }

            public void onFinish() {
                time = itemCounterTime;
                utility.getRandomData(jsonObject);
                startCounter();
                tvItem1.setText(creature.getName());
                tvItem2.setText(creature.getName());
                tvItem3.setText(creature.getName());
                tvItem4.setText(creature.getName());
                int id = getResources().getIdentifier(creature.getImage(), "drawable", getPackageName());
                circularMusicProgressBar.setImageDrawable(ContextCompat.getDrawable(FourPlayerGameActivity.this, id));
                circularMusicProgressBar2.setImageDrawable(ContextCompat.getDrawable(FourPlayerGameActivity.this, id));
                checkTapOnce = false;
                checkTapOnce2 = false;
                checkTapOnce3 = false;
                checkTapOnce4 = false;
                circleButton1.setClickable(true);
                circleButton2.setClickable(true);
                circleButton3.setClickable(true);
                circleButton4.setClickable(true);
                tvTime.setText("Time Up!");
                tvTime2.setText("Time Up!");
                tvTime3.setText("Time Up!");
                tvTime4.setText("Time Up!");
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
                        Intent intent = new Intent(FourPlayerGameActivity.this, MainActivity.class);
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
        if (totalCountDownTimer != null) {
            totalCountDownTimer.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
