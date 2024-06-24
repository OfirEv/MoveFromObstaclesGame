package com.example.exe1;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private AppCompatImageView[][] failsIMG;
    private AppCompatImageView[] myIMGArr;
    private AppCompatImageView[] heartsIMG;
    private MaterialButton right_button;
    private MaterialButton left_button;
    private GameManager gameManager;
    private Timer timer;
    private long startTime;
    private boolean timerOn = false;
    private static final long DELAY = 1000L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        gameManager = new GameManager();
        initView();
    }


    private void findView() {
        right_button = findViewById(R.id.right_button);
        left_button = findViewById(R.id.left_button);
        heartsIMG = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)
        };
        myIMGArr = new AppCompatImageView[]{
                findViewById(R.id.my_pic_1),
                findViewById(R.id.my_pic_2),
                findViewById(R.id.my_pic_3)
        };

        failsIMG = new AppCompatImageView[][]{
                {
                        findViewById(R.id.fail1),
                        findViewById(R.id.fail2),
                        findViewById(R.id.fail3)
                }
                ,
                {
                        findViewById(R.id.fail4),
                        findViewById(R.id.fail5),
                        findViewById(R.id.fail6)
                },
                {
                        findViewById(R.id.fail7),
                        findViewById(R.id.fail8),
                        findViewById(R.id.fail9)
                },
                {
                        findViewById(R.id.fail10),
                        findViewById(R.id.fail11),
                        findViewById(R.id.fail12)
                }
        };
    }


    private void initView() {
        right_button.setOnClickListener(v -> buttonClicked(1));
        left_button.setOnClickListener(v -> buttonClicked(0));
        if (!timerOn) {
            Log.d("startTimer", "startTimer: Timer Started");
            startTime = System.currentTimeMillis();
            timerOn = true;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(() -> updateMatUI());
                }
            },0L,DELAY);
        }

    }

    private void updateMatUI() {
        long currentTime = System.currentTimeMillis();
        int second = (int)(currentTime - startTime)/1000;
        gameManager.changeFailPlaceInMatrix(second);
        if(gameManager.isCollisionHappend(gameManager.getIndex())){
            toastAndVibrate("Collision Happend!");
        }
        refreshUI();
    }

    private void refreshUI() {
        if(gameManager.isGameLost()){
            changeActivity();
        }
        for (int i = 0; i < gameManager.getRows(); i++) {
            for (int j = 0; j < gameManager.getCols(); j++) {
                if(gameManager.failMatValue[i][j] == 1){
                    failsIMG[i][j].setVisibility(View.VISIBLE);
                }
                else{
                    failsIMG[i][j].setVisibility(View.INVISIBLE);
                }
            }
        }
        for (int i = 0; i < gameManager.myArrayValue.length; i++) {
            if(gameManager.myArrayValue[i] == 1){
                myIMGArr[i].setVisibility(View.VISIBLE);
            }
            else{
                myIMGArr[i].setVisibility(View.INVISIBLE);
            }
        }

            if(gameManager.getCollision() != 0){
                heartsIMG[gameManager.getCollision() - 1 ].setVisibility(View.INVISIBLE);
            }

    }

    private void changeActivity() {
        Intent intent = new Intent(this, GameLostActivity.class);
        //intent.putExtra();
        startActivity(intent);
        timer.cancel();
        finish();
    }


    private void buttonClicked(int direction) {
        gameManager.movePlayer(direction);
        refreshUI();
        if(gameManager.isCollisionHappend(gameManager.getIndex())){
            toastAndVibrate("Collision Happend!");
        }
    }

    private void toastAndVibrate(String text) {
        vibrate();
        toast(text);
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }
}