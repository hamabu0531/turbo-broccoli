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

    public View() {

        //フレーム設定
        setTitle("MVC Game Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 1000);
        setLayout(new BorderLayout());

        // JLayeredPaneを使用．パネルを重ねて描画するときに使うらしい．
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(600, 1000));
        add(layeredPane, BorderLayout.CENTER);

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
        
        //岩
        rockPanel = new RockPanel();
        rockPanel.setBounds(0, 0, 600, 1000); // フルサイズに調整
        layeredPane.add(rockPanel, JLayeredPane.PALETTE_LAYER); // プレイヤーレイヤー
        
        // デフォルトでwindow自体をfocusする(キー入力のため)
        this.setFocusable(true);
        this.requestFocusInWindow();
        
        setVisible(true);
    }
    
    /////////////////////////////////////////////////////////////////
///背景クラス
    public class LanePanel extends JPanel {
        
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            Color DarkGreen = new Color(0, 100, 0);
            
            // グラデーションの設定
            GradientPaint gradient = new GradientPaint(
                0, 0, Color.GREEN, // 開始位置と色
                0, getHeight(), DarkGreen// 終了位置と色
                );
    
                // グラデーションを適用して塗りつぶす
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        }
        
//////////////////////////////////////////////////////////////////////////
///プレーヤークラス
public class PlayerPanel extends JPanel{
    // メンバ変数
    int x, y, width, height; // x=0, 1, 2で位置決定
    Image image;
    //private int playerPosX = 1;
    
    // コンストラクタ
    public PlayerPanel(){
            x = 1; y = 0;
            width = 100; height = 100;
            try{
                image = ImageIO.read(new File("URL"));
            }catch(IOException e){
                e.printStackTrace();
            }
            setOpaque(false);//背景を透過するやつ。
        }
        
        public void paintComponent(Graphics g){
            int offsetX = 50, offsetY = 700;
            super.paintComponent(g);
            if(image!=null){
                g.drawImage(image, x*200+offsetX, y*100+offsetY, getFocusCycleRootAncestor());
            }else{
                g.setColor(Color.CYAN);
                g.fillOval(x*200+offsetX, y*100+offsetY, width, height);
            }
        }
        
        // プレイヤー位置を更新して再描画.
        //★★Controllerで呼ばれる。
        public void updatePlayerPos(int playerPosX) {
            this.x = playerPosX;
            repaint();
        }
    }
    /////////////////////////////////////////////////////////////////////////
    /// 岩クラス
    public class RockPanel extends JPanel{
        // メンバ変数
        int x, y, width, height; // x=0, 1, 2で位置決定
        Image image;
        //private int playerPosX = 1;
        
        // コンストラクタ
        public RockPanel(){
            x = 1; y = 0;
            width = 100; height = 100;
            try{
                image = ImageIO.read(new File("URL"));
            }catch(IOException e){
                e.printStackTrace();
            }
            setOpaque(false);//背景を透過するやつ。
        }
        
        public void paintComponent(Graphics g){
            int offsetX = 50, offsetY = 200;
            super.paintComponent(g);
            if(image!=null){
                g.drawImage(image, x*200+offsetX, y*100+offsetY, getFocusCycleRootAncestor());
            }else{
                g.setColor(Color.ORANGE);
                g.fillOval(x*200+offsetX, (int)(y*1.35+offsetY), width, height);

                // はまぶーによる変更
                //g.fillOval(x*200+offsetX, y*100+offsetY, width, height);
            }
        }
        
        // プレイヤー位置を更新して再描画.
        //★★Controllerで呼ばれる。
        public void updateRockPos(int rockPosY) {
            this.y = rockPosY;
            repaint();
        }

        // はまぶーによる変更

        // public void updateRockPos(int playerPosX) {
        //     this.x = playerPosX;
        //     repaint();
        // }
    }

    public PlayerPanel getPlayerPanel() {
        return playerPanel;
    }

    public RockPanel getRockPanel() {
        return rockPanel;
    }

}