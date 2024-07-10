package com.example.exe1;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.exe1.Fragments.fragmentActivity;
import com.example.exe1.Interfaces.MoveCallback;
import com.example.exe1.Logic.GameManager;
import com.example.exe1.Sound.SoundPlayer;
import com.example.exe1.Utillities.MoveDetector;
import com.example.exe1.Utillities.Player;
import com.example.exe1.Utillities.PlayerList;
import com.example.exe1.Utillities.SharePreferencesManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private AppCompatImageView[][] blackCarsMat;
    private AppCompatImageView[][] coinsMat;
    private AppCompatImageView[] yellowCarsArr;
    private AppCompatImageView[] heartsIMG;
    private MaterialButton right_button;
    private MaterialButton left_button;
    private MaterialTextView textView;
    private GameManager gameManager;
    private Timer timer;
    private long startTime;
    private boolean timerOn = false;
    private static final long DELAY = 1000L;
    private int playMode;
    public static String KEY_CHOICE = "how_to_play";
    public static String KEY_NAME = "user_name";
    public static String KEY_LATITUDE = "latitude";
    public static String KEY_LONGITUDE = "longitude";
    private SoundPlayer soundPlayer;
    private MoveDetector moveDetector;
    private Player player;
    private PlayerList playerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        soundPlayer = new SoundPlayer(this);
        findView();
        gameManager = new GameManager();
        gameManager.setSoundPlayer(soundPlayer);

        Intent intent2 = getIntent();
        player = new Player();
        player.setName(intent2.getStringExtra(KEY_NAME));
        player.setLatitude(intent2.getDoubleExtra(KEY_LATITUDE, 0.0));
        player.setLongitude(intent2.getDoubleExtra(KEY_LONGITUDE, 0.0));
        SharePreferencesManager preferencesManager = SharePreferencesManager.getInstance();
        playerList = new PlayerList();
        playerList.getPlayerList().addAll(preferencesManager.loadPlayerList());

        initView();
    }

    private void findView() {
        right_button = findViewById(R.id.right_button);
        left_button = findViewById(R.id.left_button);
        textView = findViewById(R.id.textView);

        heartsIMG = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)
        };
        yellowCarsArr = new AppCompatImageView[]{
                findViewById(R.id.yellow_car_1),
                findViewById(R.id.yellow_car_2),
                findViewById(R.id.yellow_car_3),
                findViewById(R.id.yellow_car_4),
                findViewById(R.id.yellow_car_5)
        };

        blackCarsMat = new AppCompatImageView[][]{
                {
                        findViewById(R.id.car1),
                        findViewById(R.id.car2),
                        findViewById(R.id.car3),
                        findViewById(R.id.car4),
                        findViewById(R.id.car5)
                },
                {
                        findViewById(R.id.car6),
                        findViewById(R.id.car7),
                        findViewById(R.id.car8),
                        findViewById(R.id.car9),
                        findViewById(R.id.car10)
                },
                {
                        findViewById(R.id.car11),
                        findViewById(R.id.car12),
                        findViewById(R.id.car13),
                        findViewById(R.id.car14),
                        findViewById(R.id.car15)
                },
                {
                        findViewById(R.id.car16),
                        findViewById(R.id.car17),
                        findViewById(R.id.car18),
                        findViewById(R.id.car19),
                        findViewById(R.id.car20)
                },
                {
                        findViewById(R.id.car21),
                        findViewById(R.id.car22),
                        findViewById(R.id.car23),
                        findViewById(R.id.car24),
                        findViewById(R.id.car25)
                }
        };

        coinsMat = new AppCompatImageView[][]{
                {
                        findViewById(R.id.coin1),
                        findViewById(R.id.coin2),
                        findViewById(R.id.coin3),
                        findViewById(R.id.coin4),
                        findViewById(R.id.coin5)
                },
                {
                        findViewById(R.id.coin6),
                        findViewById(R.id.coin7),
                        findViewById(R.id.coin8),
                        findViewById(R.id.coin9),
                        findViewById(R.id.coin10)
                },
                {
                        findViewById(R.id.coin11),
                        findViewById(R.id.coin12),
                        findViewById(R.id.coin13),
                        findViewById(R.id.coin14),
                        findViewById(R.id.coin15)
                },
                {
                        findViewById(R.id.coin16),
                        findViewById(R.id.coin17),
                        findViewById(R.id.coin18),
                        findViewById(R.id.coin19),
                        findViewById(R.id.coin20)
                },
                {
                        findViewById(R.id.coin21),
                        findViewById(R.id.coin22),
                        findViewById(R.id.coin23),
                        findViewById(R.id.coin24),
                        findViewById(R.id.coin25)
                }
        };
    }

    private void initView() {
        Intent intent = getIntent();
        playMode = intent.getIntExtra(KEY_CHOICE, 0);
        handlePlayMode(playMode);
        if (playMode == 1) {
            right_button.setOnClickListener(v -> buttonClicked(1));
            left_button.setOnClickListener(v -> buttonClicked(0));
        } else {
            initMoveDetector();
        }
        textView.setText(String.valueOf(gameManager.getScore()));
        if (!timerOn) {
            startTime = System.currentTimeMillis();
            timerOn = true;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(() -> updateMatUI());
                }
            }, 0L, DELAY);
        }
    }

    private void handlePlayMode(int playMode) {
        if (playMode == 1) {
            right_button.setVisibility(View.VISIBLE);
            left_button.setVisibility(View.VISIBLE);
        } else if (playMode == 2) {
            right_button.setVisibility(View.INVISIBLE);
            left_button.setVisibility(View.INVISIBLE);
        }
    }

    private void updateMatUI() {
        long currentTime = System.currentTimeMillis();
        int second = (int) (currentTime - startTime) / 1000;
        gameManager.changeBlackCarPlaceInMatrix(second);
        gameManager.changeCoinPlaceInMatrix(second);
        if (gameManager.isCollisionHappened(gameManager.getIndex())) {
            toastAndVibrate("Collision Happened!");
        }
        if (gameManager.isCoinCollisionHappened(gameManager.getIndex())) {
            toastAndVibrate("+20");
        }
        refreshUI();
    }

    private void refreshUI() {
        if (gameManager.isGameLost()) {
            changeActivity();
        }
        textView.setText(String.valueOf(gameManager.getScore()));
        for (int i = 0; i < gameManager.getRows(); i++) {
            for (int j = 0; j < gameManager.getCols(); j++) {
                if (gameManager.blackCarMatValue[i][j] == 1) {
                    blackCarsMat[i][j].setVisibility(View.VISIBLE);
                } else {
                    blackCarsMat[i][j].setVisibility(View.INVISIBLE);
                }
            }
        }
        for (int i = 0; i < gameManager.getRows(); i++) {
            for (int j = 0; j < gameManager.getCols(); j++) {
                if (gameManager.coinMatValue[i][j] == 1) {
                    coinsMat[i][j].setVisibility(View.VISIBLE);
                } else {
                    coinsMat[i][j].setVisibility(View.INVISIBLE);
                }
            }
        }
        for (int i = 0; i < gameManager.yellowCarArrayValue.length; i++) {
            if (gameManager.yellowCarArrayValue[i] == 1) {
                yellowCarsArr[i].setVisibility(View.VISIBLE);
            } else {
                yellowCarsArr[i].setVisibility(View.INVISIBLE);
            }
        }
        if (gameManager.getCollision() != 0) {
            heartsIMG[gameManager.getCollision() - 1].setVisibility(View.INVISIBLE);
        }
    }

    private void initMoveDetector() {
        moveDetector = new MoveDetector(this, new MoveCallback() {
            @Override
            public void moveX() {
                buttonClicked(moveDetector.getMoveCountX());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (playMode == 2) {
            moveDetector.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        soundPlayer.stopSound();
        if (moveDetector != null) {
            moveDetector.stop();
        }
    }

    private void changeActivity() {
        player.setScore(gameManager.getScore());
        playerList.add(player);
        SharePreferencesManager.getInstance().savePlayerList(playerList.getPlayerList());
        Intent intent = new Intent(this, fragmentActivity.class);
        startActivity(intent);
        timer.cancel();
        finish();
    }

    private void buttonClicked(int direction) {
        gameManager.movePlayer(direction);
        if (gameManager.isCollisionHappened(gameManager.getIndex())) {
            toastAndVibrate("Collision Happened!");
        }
        if (gameManager.isCoinCollisionHappened(gameManager.getIndex())) {
            toastAndVibrate("+20");
        }
        refreshUI();
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(500);
        }
    }
}
