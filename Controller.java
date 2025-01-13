import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.*;
import javax.swing.*;

// Cはユーザー入力を処理する(Listener関係)
// Cは、Mの更新をし、Vにイベントを伝える

public class Controller {
    private Model model;
    private View view;
    private Timer gameTimer;
    private ArrayList<RockPanel> rocks; // ArrayListに変更
    private int deletedRock, generateCounter, spawnInterval;
    private Clip gameoverClip, gameBgmClip, titleBgmClip;

    public Controller(Model model, View view) {
        // 初期設定
        this.model = model;
        this.view = view;
        rocks = new ArrayList<>(); // ArrayListを初期化
        deletedRock = 0;
        generateCounter=0;
        spawnInterval = 100;
        try{
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(Controller.class.getResource("Explosion.wav"));
            gameoverClip = AudioSystem.getClip();
            gameoverClip.open(audioIn);
            AudioInputStream audioIn2 = AudioSystem.getAudioInputStream(Controller.class.getResource("usi.wav"));
            gameBgmClip = AudioSystem.getClip();
            gameBgmClip.open(audioIn2);
            AudioInputStream audioIn3 = AudioSystem.getAudioInputStream(Controller.class.getResource("Title BGMへのURL"));
            titleBgmClip = AudioSystem.getClip();
            titleBgmClip.open(audioIn3);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        // タイトルBGMの再生
        if(titleBgmClip!=null){
            titleBgmClip.setFramePosition(0);
            titleBgmClip.start();
        }

        model.startGame();

        view.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Playerの移動
                if (e.getKeyCode() == KeyEvent.VK_RIGHT && model.isPlayScene() && !model.isGameOver()) {
                    model.moveToRight();
                    view.getPlayerPanel().updatePlayerPos(model.getPlayerPosX());
                    //ブロッコリーの修正：＋１の削除
                    System.out.println("PlayerPosX = " + model.getPlayerPosX());
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT && model.isPlayScene() && !model.isGameOver()) {
                    model.moveToLeft();
                    view.getPlayerPanel().updatePlayerPos(model.getPlayerPosX());
                    //ブロッコリーの修正：＋１の削除
                    System.out.println("PlayerPosX = " + model.getPlayerPosX());
                }

                // Titleシーン->Playシーン(デバッグ)
                // if (e.getKeyCode() == KeyEvent.VK_SPACE && model.isTitleScene()) {
                //     model.goToPlayScene();
                //     gameTimer.start();
                //     System.out.println("Title->Play");

                //     // ここでbgm流す
                //     if(gameBgmClip!=null){
                //         gameBgmClip.setFramePosition(0);
                //         gameBgmClip.start();
                //         titleBgmClip.stop();
                //     }
                // }

                // Playシーン->Titleシーン(デバッグ)
                // if (e.getKeyChar() == 'q' && model.isGameOver()) {
                //     model.backToTitleScene();
                //     generateCounter = 0;
                //     // 岩の配列リセット
                //     rocks.clear();
                //     model.resetRock();
                //     if(gameBgmClip!=null){
                //         gameBgmClip.stop();
                //     }
                //     if(titleBgmClip!=null){
                //         titleBgmClip.start();
                //     }
                //     System.out.println("Play->Title");
                // }

                // 岩生成(デバッグ用)
                // if (e.getKeyCode() == KeyEvent.VK_1) {
                //     generateRock(-1, -100);
                // } else if (e.getKeyCode() == KeyEvent.VK_2) {
                //     generateRock(0, -100);
                // } else if (e.getKeyCode() == KeyEvent.VK_3) {
                //     generateRock(1, -100);
                // }

                // アーマー付与(デバッグ用)
                if (e.getKeyCode() == KeyEvent.VK_ENTER && model.isPlayScene()) {
                    model.getArmor();
                    view.getShieldLifePanel().showShieldLife();//盾所持表示
                    System.out.println("You got armored");
                }
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });

        // 各ボタンのListener
        view.getStartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.goToPlayScene();
                generateTimer();
                view.startGame();
                gameTimer.start();
                System.out.println("Title->Play");

                // ここでbgm流す
                if(gameBgmClip!=null){
                    gameBgmClip.setFramePosition(0);
                    gameBgmClip.start();
                    titleBgmClip.stop();
                }
            }
        });

        view.getHomeButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.backToTitleScene();
                generateCounter = 0;
                deletedRock = 0;
                // 岩の配列リセット
                rocks.clear();
                model.resetRock();
                view.backToTitle();
                if(gameBgmClip!=null){
                    gameBgmClip.stop();
                }
                if(titleBgmClip!=null){
                    titleBgmClip.start();
                }
                System.out.println("Play->Title");
            }
        });

        view.getRetryButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.backToTitleScene();
                model.goToPlayScene();
                view.retryGame();
                rocks.clear();
                model.resetRock();
                generateCounter = 0;
                deletedRock = 0;
                generateTimer();
                gameTimer.start();
                System.out.println("Retry");
            }
        });
    }

    // 岩を生成する関数
    private void generateRock(int posX, int posY) {
        model.setRockInfo(posX, posY);
        RockPanel newRock = view.addRock(posX, posY);
        rocks.add(newRock); // ArrayListに追加
    }

    private void generateTimer(){
        // 一定時間ごとに岩を移動
        gameTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 岩生成(自動)
                if(generateCounter%spawnInterval == 0){
                    int num1 = new Random().nextInt(3)-1;
                    int num2 = num1;
                    while(num1==num2){
                        num2 = new Random().nextInt(3)-1;
                    }
                    generateRock(num1, -100);
                    if(1<new Random().nextInt(5)){
                        generateRock(num2, -100);
                    }
                }
                generateCounter++;


                // 岩を移動する関数
                model.increaseRockPosY();

                // 削除はステージクリア後とLose時
                // model.deleteRock();
                for(int i=deletedRock; i<model.getRockPosY().size()-1; i++){
                    if(model.getRockPosY().get(i) > 1200){
                        deletedRock++;
                    }
                }

                // 岩の位置を更新して再描画
                for (int i = deletedRock; i < model.getRockPosY().size(); i++) {
                    // ArrayListのRockPanelを更新
                    rocks.get(i).updateRockPos(model.getRockPosY().get(i));
                }

                // 衝突判定関数
                if (model.checkCollision()) {
                    if (model.hasArmor()) {
                        model.breakArmor();
                        view.getShieldLifePanel().hideShieldLife();//盾表示排除
                        System.out.println("Armor has broken!");
                    } else {
                        // 衝突音再生
                        if(gameoverClip!=null){
                            gameoverClip.setFramePosition(0);
                            gameoverClip.start();
                        }

                        model.stopGame();
                        view.setGameOverScreenVisible(true);//ゲームオーバー表示
                        gameTimer.stop();
                        System.out.println("You Lose...");
                    }
                }
            }
        });
    }
}
