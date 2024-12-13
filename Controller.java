import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
                if(e.getKeyChar()=='a'){
                    model.moveToleft();
                }else if(e.getKeyChar()=='d'){
                    model.moveToright();
                }
            }
            public void keyReleased(KeyEvent e){

            }
            public void keyTyped(KeyEvent e){
        
            }
        });

        // ボタンがクリックされたときのイベントリスナー
        view.getScoreButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.increaseScore(); // スコアを増加
                view.getScoreLabel().setText("Score: " + model.getScore()); // ラベルを更新
            }
        });

        new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(model.isGameOver()){
                    view.getScoreButton().setEnabled(false);
                    view.getScoreLabel().setText("Time Over!");
                }
            }
        }).start();
    }
}
