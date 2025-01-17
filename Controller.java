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
    private ArrayList<RockPanel> rocks; // ArrayListで実装
    private ArrayList<ItemPanel> items;
    private int deletedRock, deletedItem, generateCounter, rockSpawnInterval, itemSpawnInterval, offsetY;
    private Clip gameoverClip, gameBgmClip, titleBgmClip;

    public Controller(Model model, View view) {
        // 初期設定
        this.model = model;
        this.view = view;
        rocks = new ArrayList<>();
        items = new ArrayList<>();
        deletedRock = 0;
        deletedItem = 0;
        generateCounter = 50;
        rockSpawnInterval = 100;
        itemSpawnInterval = 150;
        offsetY = -100;

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
                model.setPlayerPositionZero();
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
                model.setPlayerPositionZero();
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
                    int num1=0, num2=0, num3=0;
                    if (generateCounter % rockSpawnInterval == 0) {
                        num1 = new Random().nextInt(3) - 1;
                        num2 = num1;
                        while (num1 == num2) {
                            num2 = new Random().nextInt(3) - 1;
                        }
                        generateRock(num1, offsetY);
                        if (1 < new Random().nextInt(5)) {
                            generateRock(num2, offsetY);
                        }
                    }

                    // アイテム生成
                    if (generateCounter % itemSpawnInterval == 0) {
                        num3 = new Random().nextInt(3) - 1; // -1, 0, 1
                        while(num3 == num1 || num3 == num2){
                            num3 = new Random().nextInt(3) - 1;
                        }
                        generateItem(num3, offsetY);
                    }

                    generateCounter++;

                    // 岩移動と削除
                    model.increaseRockPosY();
                    for (int i = deletedRock; i < model.getRockPosY().size(); i++) {
                        if (model.getRockPosY().get(i) > 1200) {
                            deletedRock++;
                        }
                    }
                    // System.out.println("size(model): " + model.getRockPosY().size() + ", deletedRock: " + deletedRock + ", size(rocks): " + rocks.size());

                    // アイテム移動と削除
                    model.increaseItemPosY();
                    for (int i = deletedItem; i < model.getItemPosY().size(); i++) {
                        if (model.getItemPosY().get(i) > 1200) {
                            deletedItem++;
                        }
                    }
                    System.out.println("size(model): " + model.getItemPosY().size() + ", deletedRock: " + deletedItem + ", size(rocks): " + items.size());

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

                    // アイテムの位置更新
                    for (int i = deletedItem; i < model.getItemPosY().size(); i++) {
                        items.get(i).updateItemPos(model.getItemPosY().get(i)); // 再描画を含む
                        items.get(i).updateItemPos(model.getItemPosY().get(i)); // 再描画を含む
                    }

                    // アイテム取得判定
                    // if(model.handleItemCollecting()){
                    //     // スコアを増加
                    //     model.increaseScore();
                    //     view.getScorePanel().updateScore(model.getScore());
                    // }
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

    private void generateItem(int posX, int posY) {
        model.setItemInfo(posX, posY);
        ItemPanel newItem = view.addItem(posX, posY);
        items.add(newItem);
    }
}
