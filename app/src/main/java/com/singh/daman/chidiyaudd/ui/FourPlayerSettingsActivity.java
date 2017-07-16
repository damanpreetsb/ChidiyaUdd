package com.singh.daman.chidiyaudd.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.singh.daman.chidiyaudd.R;
import com.singh.daman.chidiyaudd.utils.Constants;
import com.singh.daman.colorpickerlibrary.ColorPickerDialog;
import com.singh.daman.colorpickerlibrary.ColorPickerListener;

import at.markushi.ui.CircleButton;

public class FourPlayerSettingsActivity extends AppCompatActivity {
    private CircleButton button1, button2, button3, button4;
    private Button btnPlay;
    private EditText etName1, etName2, etName3, etName4, etTime, etMode;
    private int color1 = Color.BLUE, color2 = Color.RED, color3 = Color.GREEN, color4 = Color.CYAN, time, modeTime = 20;
    private String name1, name2, name3, name4;
    private CharSequence[] values = {"Easy", "Medium", "Hard"};
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_four_player_settings);

        button1 = (CircleButton) findViewById(R.id.color1);
        button2 = (CircleButton) findViewById(R.id.color2);
        button3 = (CircleButton) findViewById(R.id.color3);
        button4 = (CircleButton) findViewById(R.id.color4);
        btnPlay = (Button) findViewById(R.id.btn_play);
        etName1 = (EditText) findViewById(R.id.name_player1);
        etName2 = (EditText) findViewById(R.id.name_player2);
        etName3 = (EditText) findViewById(R.id.name_player3);
        etName4 = (EditText) findViewById(R.id.name_player4);
        etTime = (EditText) findViewById(R.id.set_time);
        etMode = (EditText) findViewById(R.id.set_mode);

        Typeface menuFont = Typeface.createFromAsset(getAssets(), "menu_font.ttf");
        btnPlay.setTypeface(menuFont);

        button1.setColor(color1);
        button2.setColor(color2);
        button3.setColor(color3);
        button4.setColor(color4);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorPickerDialog colorPickerDialog = new ColorPickerDialog(FourPlayerSettingsActivity.this);
                colorPickerDialog.setColorListener(new ColorPickerListener() {
                    @Override
                    public void OnColorClick(View v, int color) {
                        button1.setColor(color);
                        color1 = color;
                    }
                });
                colorPickerDialog.show();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorPickerDialog colorPickerDialog = new ColorPickerDialog(FourPlayerSettingsActivity.this);
                colorPickerDialog.setColorListener(new ColorPickerListener() {
                    @Override
                    public void OnColorClick(View v, int color) {
                        button2.setColor(color);
                        color2 = color;
                    }
                });
                colorPickerDialog.show();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialog colorPickerDialog = new ColorPickerDialog(FourPlayerSettingsActivity.this);
                colorPickerDialog.setColorListener(new ColorPickerListener() {
                    @Override
                    public void OnColorClick(View v, int color) {
                        button3.setColor(color);
                        color3 = color;
                    }
                });
                colorPickerDialog.show();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorPickerDialog colorPickerDialog = new ColorPickerDialog(FourPlayerSettingsActivity.this);
                colorPickerDialog.setColorListener(new ColorPickerListener() {
                    @Override
                    public void OnColorClick(View v, int color) {
                        button4.setColor(color);
                        color4 = color;
                    }
                });
                colorPickerDialog.show();
            }
        });

        etMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showModeDialog();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etName1.getText().toString()))
                    name1 = etName1.getText().toString().toUpperCase();
                else
                    name1 = Constants.player1;
                if (!TextUtils.isEmpty(etName2.getText().toString()))
                    name2 = etName2.getText().toString().toUpperCase();
                else
                    name2 = Constants.player2;
                if (!TextUtils.isEmpty(etName3.getText().toString()))
                    name3 = etName3.getText().toString().toUpperCase();
                else
                    name3 = Constants.player3;
                if (!TextUtils.isEmpty(etName4.getText().toString()))
                    name4 = etName4.getText().toString().toUpperCase();
                else
                    name4 = Constants.player4;
                if (!TextUtils.isEmpty(etTime.getText().toString()))
                    time = Integer.parseInt(etTime.getText().toString());
                else
                    time = 60;
                Intent intent = new Intent(FourPlayerSettingsActivity.this, FourPlayerGameActivity.class);
                intent.putExtra(Constants.color1, color1);
                intent.putExtra(Constants.color2, color2);
                intent.putExtra(Constants.color3, color3);
                intent.putExtra(Constants.color4, color4);
                intent.putExtra(Constants.player1, name1);
                intent.putExtra(Constants.player2, name2);
                intent.putExtra(Constants.player3, name3);
                intent.putExtra(Constants.player4, name4);
                intent.putExtra(Constants.time, time);
                intent.putExtra(Constants.modeTime, modeTime);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    public void showModeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select the mode");
        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        etMode.setText(values[0]);
                        modeTime = 30;
                        break;
                    case 1:
                        etMode.setText(values[1]);
                        modeTime = 20;
                        break;
                    case 2:
                        etMode.setText(values[2]);
                        modeTime = 10;
                        break;
                }
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
