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
                // Playerの移動
                if(e.getKeyCode()==KeyEvent.VK_RIGHT && model.isPlayScene() && !model.isGameOver()){
                    model.moveToRight();
                    //view.updatePosition(model.getPlayerPosX());

                    System.out.println("PlayerPosX = " + model.getPlayerPosX());
                }else if(e.getKeyCode()==KeyEvent.VK_LEFT && model.isPlayScene() && !model.isGameOver()){
                    model.moveToLeft();
                    //view.updatePosition(model.getPlayerPosX());

                    System.out.println("PlayerPosX = " + model.getPlayerPosX());
                }

                // Titleシーン->Playシーン
                if(e.getKeyCode()==KeyEvent.VK_SPACE && model.isTitleScene()){
                    model.goToPlayScene();
                    gameTimer.start();
                    System.out.println("Title->Play");
                }

                // Playシーン->Titleシーン
                if(e.getKeyChar()=='q' && model.isGameOver()){
                    model.backToTitleScene();
                    System.out.println("Play->Title");
                }

                // 岩生成(デバッグ用)
                if(e.getKeyCode()==KeyEvent.VK_1){
                    model.setRockInfo(-1, 0);
                }else if(e.getKeyCode()==KeyEvent.VK_2){
                    model.setRockInfo(0, 0);
                }else if(e.getKeyCode()==KeyEvent.VK_3){
                    model.setRockInfo(1, 0);
                }

                // アーマー付与(デバッグ用)
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    model.getArmor();
                    System.out.println("You got armored");
                }
            }
            public void keyReleased(KeyEvent e){

            }
            public void keyTyped(KeyEvent e){
        
            }
        });

        // 岩生成(テスト)
        //model.setRockInfo(0, 0);

        // 一定時間ごとに岩を移動
        gameTimer = new Timer(10, new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e){
            // 岩を移動する関数
            // ArrayList<Integer> al = model.getRockPosY();
            model.increaseRockPosY();
            model.deleteRock();

            

            // 衝突判定関数
            if(model.checkCollision()){
                if(model.hasArmor()){
                    model.breakArmor();

                    System.out.println("Armor has broken!");
                }else{
                    model.stopGame();
                    gameTimer.stop();

                    System.out.println("You Lose...");
                }
            }            
           }
        });
    }
}
