import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.border.LineBorder;
import java.awt.event.*;

//こっちをいじる。
// Vはユーザーに情報を表示する(主にJ~~関係)
// Vは、Mからデータを取得、Cの入力によって表示を変える

public class View extends JFrame {
    private LanePanel l;
    private PlayerPanel playerPanel;
    private RockPanel rockPanel;
    private JLayeredPane layeredPane;
    private ScorePanel scorePanel;
    private JPanel homePanel;
    private JPanel gameOverPanel;

    public View() {

        //フレーム設定
        setTitle("MVC Game Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 1000);
        setLayout(new BorderLayout());

        // JLayeredPaneを使用．パネルを重ねて描画するときに使うらしい．
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(600, 1000));
        add(layeredPane, BorderLayout.CENTER);

        // ホーム画面パネル
        homePanel = createHomeScreen();
        homePanel.setBounds(0, 0, 600, 1000);
        layeredPane.add(homePanel, JLayeredPane.DEFAULT_LAYER); // 一番下のレイヤー

        // ゲームオーバーパネル
        gameOverPanel = createGameOverScreen();
        gameOverPanel.setBounds(0, 0, 600, 1000);
        layeredPane.add(gameOverPanel, JLayeredPane.DEFAULT_LAYER); // 一番下のレイヤー
        gameOverPanel.setVisible(false); // 最初は非表示

        //背景
        JPanel lanePanel = new JPanel();
        lanePanel.setLayout(null); // nullにすると配置とサイズを手動で制御できる
        lanePanel.setOpaque(false);//背景を透過するやつ。各JPanelに入れる。
        lanePanel.setBounds(0, 0, 600, 1000);
        layeredPane.add(lanePanel, JLayeredPane.DEFAULT_LAYER);
                
        for (int i = 0; i <= 2; i++) {
            l = new LanePanel();
            l.setBounds(i * 200, 0, 200, 1000); // 3つの列として配置
            l.setBorder(new LineBorder(Color.white, 3));
            lanePanel.add(l);
        }
        
        //プレーヤー
        playerPanel = new PlayerPanel();
        playerPanel.setBounds(0, 0, 600, 1000); // フルサイズに調整
        layeredPane.add(playerPanel, JLayeredPane.PALETTE_LAYER); // プレイヤーレイヤー

        //スコアパネル
        scorePanel = new ScorePanel();
        scorePanel.setBounds(0,0,600,50); // スコアは600☓50のサイズに固定
        layeredPane.add(scorePanel, JLayeredPane.DRAG_LAYER);//DRAG_LAYERによってほかのすべての要素より全面にスコアパネルが表示される。
        
        //盾残機
        scorePanel = new ScorePanel();
        scorePanel.setBounds(0,0,50,50); // スコアは600☓50のサイズに固定
        layeredPane.add(scorePanel, JLayeredPane.DRAG_LAYER);

        // 初期表示はホーム画面
        setHomeScreenVisible(true);
        setGameOverScreenVisible(false);

        //デフォルトでwindow自体をfocusする(キー入力のため)
        this.setFocusable(true);
        this.requestFocusInWindow();
        
        setVisible(true);
    }

    // ホーム画面作成
    private JPanel createHomeScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false); // 背景透過

        JLabel titleLabel = new JLabel("Game Title", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setBounds(150, 200, 300, 100);
        panel.add(titleLabel);

        JButton startButton = new JButton("Start Game");
        startButton.setBounds(200, 400, 200, 50);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();  // ゲーム開始処理
            }
        });
        panel.add(startButton);

        return panel;
    }

    // ゲームオーバー画面作成
    private JPanel createGameOverScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false);

        JLabel gameOverLabel = new JLabel("Game Over", JLabel.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 50));
        gameOverLabel.setBounds(150, 200, 300, 100);
        panel.add(gameOverLabel);

        JButton retryButton = new JButton("Retry");
        retryButton.setBounds(200, 400, 200, 50);
        retryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retryGame();  // ゲームリトライ処理
            }
        });
        panel.add(retryButton);

        return panel;
    }

    // ゲーム開始処理
    private void startGame() {
        setHomeScreenVisible(false);  // ホーム画面非表示
        setGameOverScreenVisible(false);  // ゲームオーバー画面非表示
        // ゲーム開始の処理を書く
    }

    // ゲームリトライ処理
    private void retryGame() {
        setHomeScreenVisible(false);
        setGameOverScreenVisible(false);
        // ゲームをリセットして開始
    }

    // ホーム画面の表示・非表示
    private void setHomeScreenVisible(boolean visible) {
        homePanel.setVisible(visible);
    }

    // ゲームオーバー画面の表示・非表示
    private void setGameOverScreenVisible(boolean visible) {
        gameOverPanel.setVisible(visible);
    }
    
    public RockPanel getRockPanel() {
        return rockPanel;
    }

    public PlayerPanel getPlayerPanel() {
        return playerPanel;
    }

    public ScorePanel getScorePanel() {
        return scorePanel;
    }

    public JLayeredPane getJLayeredPane() {
        return layeredPane;
    }

    public RockPanel addRock(int posX, int posY){
        rockPanel = new RockPanel();
        rockPanel.setBounds(0, 0, 600, 1000); // フルサイズに調整
        rockPanel.setRockPos(posX, posY);
        layeredPane.add(rockPanel, JLayeredPane.PALETTE_LAYER);
        return rockPanel;
    }


}
