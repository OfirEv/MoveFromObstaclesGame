package com.example.exe1.Logic;

import com.example.exe1.R;
import com.example.exe1.Sound.SoundPlayer;

import java.util.Random;

public class GameManager {
    public int[][] blackCarMatValue;
    public int[] yellowCarArrayValue;
    public int[][] coinMatValue;
    private static final int NUM_ROWS = 5;
    private static final int MUN_COLS = 5;
    public int life;
    public int index;
    public int collision = 0;
    public int rows;
    public int cols;
    public int score;
    private static final int POINTS = 10;
    Random ranodom = new Random();
    private SoundPlayer soundPlayer;


    public GameManager() {
        this(3,NUM_ROWS,MUN_COLS);
    }

    public GameManager(int life, int rows, int cols) {
        this.life = life;
        this.rows = rows;
        this.cols = cols;
        this.score = 0;
        blackCarMatValue = new int[rows][cols];
        yellowCarArrayValue = new int[cols];
        coinMatValue = new int[rows][cols];
        index = yellowCarArrayValue.length/2;
        yellowCarArrayValue[index] = 1;
    }


    public boolean isGameLost(){
        return getLife() == getCollision();
    }

    public void movePlayer(int direction){
        if(direction == 0){
            if(yellowCarArrayValue[0] == 1){
                return;
            }
            yellowCarArrayValue[index] = 0;
            index -= 1;
            yellowCarArrayValue[index] = 1;
        }
        if(direction == 1){
            if(yellowCarArrayValue[yellowCarArrayValue.length-1] == 1){
                return;
            }
            yellowCarArrayValue[index] = 0;
            index += 1;
            yellowCarArrayValue[index] = 1;
        }
        isCoinCollisionHappened(index);
    }

    public void changeBlackCarPlaceInMatrix(int sec){
        for (int i = rows-1; i >= 0; i--) {
            for (int j = cols-1; j >= 0; j--) {
                if(i == rows-1 && blackCarMatValue[i][j] == 1){
                    blackCarMatValue[i][j] = 0;
                }
                if(blackCarMatValue[i][j] == 1){
                    blackCarMatValue[i][j] = 0;
                    blackCarMatValue[i+1][j] = 1;
                }
            }
        }
        if(sec%2 == 0){
        int ranCol = ranodom.nextInt(cols);
            newColomn(ranCol);
        }
        score += POINTS;
    }



    public void changeCoinPlaceInMatrix(int sec){
        for (int i = rows-1; i >= 0; i--) {
            for (int j = cols-1; j >= 0; j--) {
                if(i == rows-1 && coinMatValue[i][j] == 1){
                    coinMatValue[i][j] = 0;
                }
                if(coinMatValue[i][j] == 1){
                    coinMatValue[i][j] = 0;
                    coinMatValue[i+1][j] = 1;
                }
            }
        }
        if(checkCarFirstRaw()){
            int ranCol = ranodom.nextInt(cols);
            coinRelease(ranCol);
        }
    }

    private boolean checkCarFirstRaw() {
        for (int i = 0; i < getRows(); i++) {
            if(blackCarMatValue[0][i] == 1){
                return false;
            }
        }
        return true;
    }


    public boolean isCollisionHappened(int playerIndex) {
        if(blackCarMatValue[blackCarMatValue.length-1][playerIndex] == 1 && yellowCarArrayValue[playerIndex] == 1){
            collision++;
            if (soundPlayer != null) {
                soundPlayer.playSound(R.raw.car_crash_sound);
            }
            return true;
        }
        return false;
    }

    public boolean isCoinCollisionHappened(int playerIndex){
        if(coinMatValue[coinMatValue.length-1][playerIndex] == 1 && yellowCarArrayValue[playerIndex] == 1) {
            score += 2*POINTS;
            if(soundPlayer != null){
                soundPlayer.playSound(R.raw.coin_sound);
            }
            return true;
        }
        return false;
    }

    private void coinRelease(int randomCol){
            coinMatValue[0][randomCol] = 1;
    }

    private void newColomn(int ranCol) {
        blackCarMatValue[0][ranCol] = 1;
    }


    public int[][] getBlackCarMat(){
        return blackCarMatValue;
    }

    public GameManager setBlackCarMat(int[][] blackCarMatValue) {
        this.blackCarMatValue = blackCarMatValue;
        return this;
    }

    public int[] getYellowCarArray() {
        return yellowCarArrayValue;
    }

    public GameManager setYellowCarArray(int[] yellowCarArrayValue) {
        this.yellowCarArrayValue = yellowCarArrayValue;
        return this;
    }

    public int getLife() {
        return life;
    }

    public GameManager setLife(int life) {
        this.life = life;
        return this;
    }

    public int getCollision() {
        return collision;
    }

    public GameManager setCollision(int collision) {
        this.collision = collision;
        return this;
    }

    public int getIndex() {
        return index;
    }
    public GameManager setIndex(int index) {
        this.index = index;
        return this;
    }

    public int getRows() {
        return rows;
    }

    public GameManager setRows(int rows) {
        this.rows = rows;
        return this;
    }

    public int getCols() {
        return cols;
    }

    public GameManager setCols(int cols) {
        this.cols = cols;
        return this;
    }

    public int getScore() {
        return score;
    }

    public GameManager setScore(int score) {
        this.score = score;
        return this;
    }
    public void setSoundPlayer(SoundPlayer soundPlayer) {
        this.soundPlayer = soundPlayer;
    }
}
