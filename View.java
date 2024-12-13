import javax.swing.*;
import java.awt.*;
//こっちをいじる。
// Vはユーザーに情報を表示する(主にJ~~関係)
// Vは、Mからデータを取得、Cの入力によって表示を変える

public class View extends JFrame {
    private JLabel scoreLabel;
    private JButton scoreButton;

    public View() {
        //フレーム設定
        setTitle("MVC Game Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 1000);
        setLayout(new BorderLayout());

        //ラベルとボタン
        //scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        //scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        //add(scoreLabel, BorderLayout.CENTER);
        //scoreButton = new JButton("Increase Score");
        //add(scoreButton, BorderLayout.SOUTH);

        //背景
        LanePanel lanePanel = new LanePanel();
        lanePanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        add(lanePanel, BorderLayout.WEST);
        
        // //プレーヤー
        // public void paintComponent(Graphics g){
        //     int offsetX = 50, offsetY = 400;
        //     super.paintComponent(g);
        //     if(image!=null){
        //         g.drawImage(image, x*200+offsetX, y*100+offsetY, getFocusCycleRootAncestor());
        //     }else{
        //         g.setColor(Color.CYAN);
        //         g.fillOval(x*200+offsetX, y*100+offsetY, width, height);
        //     }
        // }
        

        
        //setOpaque()//背景を透過するやつ。各JPanelに入れる。
        
        // デフォルトでwindow自体をfocusする(キー入力のため)
        this.setFocusable(true);
        this.requestFocusInWindow();

        setVisible(true);
    }
    
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

    // public class PlayerPanel extends JPanel{
    //     // メンバ変数
    //     int x, y, width, height; // x=0, 1, 2で位置決定
    //     Image image;
    
    //     // メンバ関数
    //     public PlayerPanel(){
    //         x = 0; y = 0;
    //         width = 100; height = 100;
    //         try{
    //             image = ImageIO.read(new File("URL"));
    //         }catch(IOException e){
    //             e.printStackTrace();
    //         }
    //     }
    
    //     public void paintComponent(Graphics g){
    //         int offsetX = 50, offsetY = 400;
    //         super.paintComponent(g);
    //         if(image!=null){
    //             g.drawImage(image, x*200+offsetX, y*100+offsetY, getFocusCycleRootAncestor());
    //         }else{
    //             g.setColor(Color.CYAN);
    //             g.fillOval(x*200+offsetX, y*100+offsetY, width, height);
    //         }
    //     }
    
    //     public void moveImage(int dx, int dy){
    //         x += dx;
    //         y += dy;
    //         if(x<0){
    //             x=0;
    //         }
    //         if(x>2){
    //             x=2;
    //         }
    //         repaint();
    //     }
    // }

    public JLabel getScoreLabel() {
        return scoreLabel;
    }

    // public JButton getScoreButton() {
    //     return scoreButton;
    // }
}
