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
    private boolean isGameStarted;
    private boolean isGameOver;
    private int position;
    private boolean collision;

    ArrayList<Integer> arrPosX;
    ArrayList<Integer> arrPosY;
    public Model() {
        score = 0; // 初期スコアは0
        position = 0;   //初期位置は0 (中央のレーン)
        remainingTime = 100;
        isGameStarted = true;
        isGameOver = false;

        arrPosX = new ArrayList<Integer>();
        arrPosY = new ArrayList<Integer>();


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
    // | 位置      | 左  | 中央 | 右  |
    // +-----------+-----+------+-----+

    public void moveToRight() {
        if (position != 1) position += 1;
    }

    public void moveToLeft() {
        if (position != -1) position -= 1;
    }

    public int getPosition() {
        return position;
    }

    public int getScore() {
        return score;
    }

    public void setRockInfo(int posX, int posY) {
        arrPosX.add(posX);  //  -1, 0, 1
        arrPosY.add(posY);  //  岩のy座標
    }

    //  すべての岩に対してプレイヤーのindexとy座標が一致するかをチェック
    //  1つでも一致するなら衝突とみなす(trueを返す)
    public boolean checkCollision() {
        for (int i=0; i<arrPosX.size(); i++) {
            if (arrPosX.get(i) == position && arrPosY.get(i) == 300) {
                return true;
            }
        }
        return false;
    }

    public void increaseScore() {
        score++;
    }

    public int getRemainingTime() {
        return remainingTime;
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
