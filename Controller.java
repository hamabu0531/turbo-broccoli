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
    private ArrayList<RockPanel> rocks; // ArrayListに変更
    private int deletedRock, generateCounter, spawnInterval;
    private Clip gameoverClip, gameBgmClip, titleBgmClip;

    public Controller(Model model, View view) {
        // 初期設定
        this.model = model;
        this.view = view;
        rocks = new ArrayList<>();
        deletedRock = 0;
        generateCounter = 50;
        spawnInterval = 100;

        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(Controller.class.getResource("Explosion.wav"));
            gameoverClip = AudioSystem.getClip();
            gameoverClip.open(audioIn);

            AudioInputStream audioIn2 = AudioSystem.getAudioInputStream(Controller.class.getResource("usi.wav"));
            gameBgmClip = AudioSystem.getClip();
            gameBgmClip.open(audioIn2);

            AudioInputStream audioIn3 = AudioSystem.getAudioInputStream(Controller.class.getResource("Title BGMへのURL"));
            titleBgmClip = AudioSystem.getClip();
            titleBgmClip.open(audioIn3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (titleBgmClip != null) {
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
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT && model.isPlayScene() && !model.isGameOver()) {
                    model.moveToLeft();
                    view.getPlayerPanel().updatePlayerPos(model.getPlayerPosX());
                    // ここのデバッグ関数は削除
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
                    view.getShieldLifePanel().showShieldLife(); // 盾所持表示
                    System.out.println("You got armored");
                }
            }

            public void keyReleased(KeyEvent e) {}

            public void keyTyped(KeyEvent e) {}
        });

        // 各ボタンのLietener
        view.getStartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.goToPlayScene();
                view.startGame();
                System.out.println("Title->Play");

                if (gameBgmClip != null) {
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
                generateCounter = 50;
                deletedRock = 0;
                rocks.clear();
                model.resetRock();
                view.backToTitle();
                if (gameBgmClip != null) {
                    gameBgmClip.stop();
                }
                if (titleBgmClip != null) {
                    titleBgmClip.start();
                }
                System.out.println("Play->Title");
            }
        });

        view.getRetryButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.backToTitleScene();
                generateCounter = 50;
                deletedRock = 0;
                rocks.clear();
                model.resetRock();
                model.goToPlayScene();
                view.retryGame();
                System.out.println("Retry");
            }
        });

        // Timer基準->フレーム基準
        new Thread(() -> {
            final int frameRate = 120; // 1秒間のフレーム数(増やしすぎると重くなる?)
            final long frameTime = 1000 / frameRate; // 1フレームにかかる時間(ms)

            while (true) {
                long startTime = System.currentTimeMillis();

                if (model.isPlayScene() && !model.isGameOver()) {
                    // 岩生成
                    if (generateCounter % spawnInterval == 0) {
                        int num1 = new Random().nextInt(3) - 1;
                        int num2 = num1;
                        while (num1 == num2) {
                            num2 = new Random().nextInt(3) - 1;
                        }
                        generateRock(num1, -100);
                        if (1 < new Random().nextInt(5)) {
                            generateRock(num2, -100);
                        }
                    }
                    generateCounter++;

                    // 岩移動と削除
                    model.increaseRockPosY();
                    for (int i = deletedRock; i < model.getRockPosY().size(); i++) {
                        if (model.getRockPosY().get(i) > 1200) {
                            deletedRock++;
                        }
                    }
                    System.out.println("size(model): " + model.getRockPosY().size() + ", deletedRock: " + deletedRock + ", size(rocks): " + rocks.size());

                    // 岩の位置更新
                    for (int i = deletedRock; i < model.getRockPosY().size(); i++) {
                        rocks.get(i).updateRockPos(model.getRockPosY().get(i));
                    }

                    // 衝突判定
                    if (model.checkCollision()) {
                        if (model.hasArmor()) {
                            model.breakArmor();
                            view.getShieldLifePanel().hideShieldLife();
                            System.out.println("Armor has broken!");
                        } else {
                            if (gameoverClip != null) {
                                gameoverClip.setFramePosition(0);
                                gameoverClip.start();
                            }

                            model.stopGame();
                            view.setGameOverScreenVisible(true);
                            System.out.println("You Lose...");
                        }
                    }
                }

                long elapsedTime = System.currentTimeMillis() - startTime;
                try {
                    Thread.sleep(Math.max(0, frameTime - elapsedTime));
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    private void generateRock(int posX, int posY) {
        model.setRockInfo(posX, posY);
        RockPanel newRock = view.addRock(posX, posY);
        rocks.add(newRock);
    }
}
