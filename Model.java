import javax.swing.*;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

// Mはゲームの状態やデータの保持を行う(スコア、ゲームオーバーとか)
// Mは、VやCに依存しない

public class Model {
    private int score;
    private Timer timer;
    private int remainingTime;
    private boolean isTitleScene;
    private boolean isPlayScene;
    private boolean isGameStarted;
    private boolean isGameOver;
    private int playerPosX;

    ArrayList<Integer> arrRockPosX;
    ArrayList<Integer> arrRockPosY;
    public Model() {
        score = 0; // 初期スコアは0
        playerPosX = 0;   //初期位置は0 (中央のレーン)
        remainingTime = 100;
        isTitleScene = true;
        isGameStarted = false;
        isGameOver = false;

        arrRockPosX = new ArrayList<Integer>();
        arrRockPosY = new ArrayList<Integer>();


        timer = new Timer(1000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(remainingTime > 0){
                    remainingTime--;
                }else{
                    isGameOver = true;
                    timer.stop();
                }
            }

        });
    }
    // +-----------+-----+------+-----+
    // | position  | -1  |  0   | 1   |
    // +-----------+-----+------+-----+
    // | 位置       | 左  | 中央 | 右   |
    // +-----------+-----+------+-----+

    public void moveToRight() {
        if (playerPosX != 1) playerPosX += 1;
    }

    public void moveToLeft() {
        if (playerPosX != -1) playerPosX -= 1;
    }

    public int getPlayerPosX() {
        return playerPosX;
    }

    // +------------------------------------------------------------------+
    // +------------------------------------------------------------------+

    public void setRockInfo(int RockPosX, int RockPosY) {
        arrRockPosX.add(RockPosX);  //  -1, 0, 1
        arrRockPosY.add(RockPosY);  //  岩のy座標
    }

    public void deleteRock() {
        for (int i=arrRockPosY.size()-1; i>=0; i--) {
            if (arrRockPosY.get(i) > 300) {
                arrRockPosX.remove(i);
                arrRockPosY.remove(i);
            }
        }
    }

    //  すべての岩の座標に +1 をする (岩が画面下側へ移動する)
    public void increaseRockPosY() {
        for (int i=0; i<arrRockPosY.size(); i++) {
            arrRockPosY.set(i, arrRockPosY.get(i) + 1);
        }
    }

    //  すべての岩に対してプレイヤーのindexとy座標が一致するかをチェック
    //  1つでも一致するなら衝突とみなす (trueを返す)
    public boolean checkCollision() {
        for (int i=0; i<arrRockPosX.size(); i++) {
            if (arrRockPosX.get(i) == playerPosX && arrRockPosY.get(i) == 300) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Integer> getRockPosY() {
        return arrRockPosY;
    }

    // +------------------------------------------------------------------+
    // +------------------------------------------------------------------+

    public void increaseScore() {
        score++;
    }

    public int getScore() {
        return score;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    // +------------------------------------------------------------------+
    // +------------------------------------------------------------------+

    public void goToPlayScene() {
        isTitleScene = false;
        isPlayScene = true;
    }

    //  必要かどうかは不明...
    public void backToTitleScene() {
        isPlayScene = false;
        isTitleScene = true;
    }

    public boolean isTitleScene() {
        return isTitleScene;
    }

    public boolean isPlayScene() {
        return isPlayScene;
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void startGame() {
        isGameStarted = true;
        timer.start();
    }

    public void stopGame() {
        isGameOver = true;
        timer.stop();
    }
}
