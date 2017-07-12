package com.singh.daman.chidiyaudd.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.singh.daman.chidiyaudd.R;
import com.singh.daman.chidiyaudd.utils.Constants;
import com.singh.daman.colorpickerlibrary.ColorPickerDialog;
import com.singh.daman.colorpickerlibrary.ColorPickerListener;

import at.markushi.ui.CircleButton;

public class TwoPlayerSettingsActivity extends AppCompatActivity {
    private CircleButton button1, button2;
    private Button btnPlay;
    private EditText etName1, etName2, etTime, etMode;
    private int color1 = Color.BLUE, color2 = Color.RED, time, modeTime = 20;
    private String name1, name2;
    private CharSequence[] values = {"Easy","Medium","Hard"};
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_two_player_settings);

        button1 = (CircleButton) findViewById(R.id.color1);
        button2 = (CircleButton) findViewById(R.id.color2);
        btnPlay = (Button) findViewById(R.id.btn_play);
        etName1 = (EditText)findViewById(R.id.name_player1);
        etName2 = (EditText)findViewById(R.id.name_player2);
        etTime = (EditText)findViewById(R.id.set_time);
        etMode = (EditText)findViewById(R.id.set_mode);

        button1.setColor(color1);
        button2.setColor(color2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorPickerDialog colorPickerDialog = new ColorPickerDialog(TwoPlayerSettingsActivity.this);
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

                ColorPickerDialog colorPickerDialog = new ColorPickerDialog(TwoPlayerSettingsActivity.this);
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

        etMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showModeDialog();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(etName1.getText().toString()))
                    name1 = etName1.getText().toString();
                else
                    name1 = Constants.player1;
                if(!TextUtils.isEmpty(etName2.getText().toString()))
                    name2 = etName2.getText().toString();
                else
                    name2 = Constants.player2;
                if(!TextUtils.isEmpty(etTime.getText().toString()))
                    time = Integer.parseInt(etTime.getText().toString());
                else
                    time = 60;
                Intent intent = new Intent(TwoPlayerSettingsActivity.this, TwoPlayerGameActivity.class);
                intent.putExtra(Constants.color1, color1);
                intent.putExtra(Constants.color2, color2);
                intent.putExtra(Constants.player1, name1);
                intent.putExtra(Constants.player2, name2);
                intent.putExtra(Constants.time, time);
                intent.putExtra(Constants.modeTime, modeTime);
                startActivity(intent);
            }
        });
    }

    public void showModeDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select the mode");
        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch(item) {
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
}
