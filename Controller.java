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
                else if(e.getKeyChar()=='e'){
                    //model.stopGame();
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
        new Timer(10, new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e){
            // 岩を移動する関数
            ArrayList<Integer> al = model.getRockPosY();
            for(int i=0; i<al.size(); i++){
                model.increaseRockPosY();
            }

            // 衝突判定関数
            if(model.checkCollision()){
                model.stopGame();
            }
           }
        }).start();


        new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(model.isGameOver()){
                    // ゲームオーバー処理
                    //System.out.println("You Lose...");
                }
                System.out.println("PlayerPosX = " + model.getPlayerPosX());
            }
        }).start();
    }
}
