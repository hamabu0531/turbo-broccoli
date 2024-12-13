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
                    model.moveToLeft();
                    System.out.println("Hi");
                }else if(e.getKeyChar()=='d'){
                    model.moveToRight();
                    System.out.println("Hello");
                }
            }
            public void keyReleased(KeyEvent e){

            }
            public void keyTyped(KeyEvent e){
        
            }
        });

        new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(model.isGameOver()){
                    // ゲームオーバー処理
                    view.getScoreLabel().setText("Time Over!");
                }
            }
        }).start();
    }
}
