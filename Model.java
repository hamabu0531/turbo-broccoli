import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Mはゲームの状態やデータの保持を行う(スコア、ゲームオーバーとか)
// Mは、VやCに依存しない

public class Model {
    private int score;
    private Timer timer;
    private int remainingTime;
    private boolean isGameOver;
    private int position;

    public Model() {
        score = 0; // 初期スコアは0
        remainingTime = 3;
        isGameOver = false;
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

    public void increaseScore() {
        score++;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void startGame() {
        timer.start();
    }
}
