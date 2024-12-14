import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;

// Cはユーザー入力を処理する(Listener関係)
// Cは、Mの更新をし、Vにイベントを伝える

public class Controller{
    private Model model;
    private View view;
    private Timer gameTimer;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        model.startGame();

        view.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode()==KeyEvent.VK_RIGHT){
                    model.moveToRight();
                }else if(e.getKeyCode()==KeyEvent.VK_LEFT){
                    model.moveToLeft();
                }
            }
            public void keyReleased(KeyEvent e){

            }
            public void keyTyped(KeyEvent e){
        
            }
        });

        // 岩生成(テスト)
        model.setRockInfo(0, 0);

        // 一定時間ごとに岩を移動
        gameTimer = new Timer(10, new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e){
            // 岩を移動する関数
            ArrayList<Integer> al = model.getRockPosY();
            for(int i=0; i<al.size(); i++){
                model.increaseRockPosY();
                if(al.get(i)>300){
                    // 岩を削除する関数
                }
            }

            System.out.println("PlayerPosX = " + model.getPlayerPosX());

            // 衝突判定関数
            if(model.checkCollision()){
                model.stopGame();
                gameTimer.stop();
                System.out.println("You Lose...");
            }
            
           }
        });
        gameTimer.start();
    }
}
