import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

// Cはユーザー入力を処理する(Listener関係)
// Cは、Mの更新をし、Vにイベントを伝える

public class Controller {
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        model.startGame();

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
