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
        // rockPanel = new RockPanel(layeredPane);
        rockPanel = new RockPanel();
        rockPanel.setBounds(0, 0, 600, 1000); // フルサイズに調整
        layeredPane.add(rockPanel, JLayeredPane.PALETTE_LAYER); // プレイヤーレイヤー
        
        //デフォルトでwindow自体をfocusする(キー入力のため)
        this.setFocusable(true);
        this.requestFocusInWindow();
        
        setVisible(true);
    }
    
    public RockPanel getRockPanel() {
        return rockPanel;
    }

    public PlayerPanel getPlayerPanel() {
        return playerPanel;
    }

    public JLayeredPane getJLayeredPane() {
        return layeredPane;
    }
}
