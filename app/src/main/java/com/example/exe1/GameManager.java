package com.example.exe1;

import java.util.Random;
import java.util.Timer;

public class GameManager {
    public int[][] failMatValue;
    public int[] myArrayValue;
    public int life;
    public int index;
    public int collision = 0;
    public int rows;
    public int cols;

    Random ranodom = new Random();


    public GameManager() {
        this(3,4,3);
    }

    public GameManager(int life, int rows, int cols) {
        this.life = life;
        this.rows = rows;
        this.cols = cols;
        failMatValue = new int[rows][cols];
        myArrayValue = new int[cols];
        index = myArrayValue.length/2;
        myArrayValue[index] = 1;
    }


    public boolean isGameLost(){
        return getLife() == getCollision();
    }

    public void movePlayer(int direction){
        if(direction == 0){
            if(myArrayValue[0] == 1){
                return;
            }
            myArrayValue[index] = 0;
            index -= 1;
            myArrayValue[index] = 1;
        }
        if(direction == 1){
            if(myArrayValue[myArrayValue.length-1] == 1){
                return;
            }
            myArrayValue[index] = 0;
            index += 1;
            myArrayValue[index] = 1;
        }
        isCollisionHappend(index);
    }

    public void changeFailPlaceInMatrix(long sec){
        for (int i = rows-1; i >= 0; i--) {
            for (int j = cols-1; j >= 0; j--) {
                if(i == rows-1 && failMatValue[i][j] == 1){
                    failMatValue[i][j] = 0;
                }
                if(failMatValue[i][j] == 1){
                    failMatValue[i][j] = 0;
                    failMatValue[i+1][j] = 1;
                }
            }
        }

        if(sec%2 == 0){
        int ranCol = ranodom.nextInt(cols);
            newColomn(ranCol);
        }
        //isCollisionHappend(index);
    }

    public boolean isCollisionHappend(int playerIndex) {
        if(failMatValue[failMatValue.length-1][playerIndex] == 1 && myArrayValue[playerIndex] == 1){
            collision++;
            return true;
        }
        return false;
    }



    private void newColomn(int ranCol) {
        failMatValue[0][ranCol] = 1;
    }


    public int[][] getFailMat(){
        return failMatValue;
    }

    public GameManager setFailMat(int[][] failMat) {
        this.failMatValue = failMat;
        return this;
    }

    public int[] getMyArray() {
        return myArrayValue;
    }

    public GameManager setMyArray(int[] myArray) {
        this.myArrayValue = myArray;
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
}
