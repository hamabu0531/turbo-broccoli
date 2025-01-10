import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.border.LineBorder;
import java.awt.event.*;

public class ScorePanel extends JPanel {
    private JLabel scoreLabel;

    public ScorePanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER));//コンポーネントを横一列に並べるシンプルな配置
        //setOpaque(false); // 背景を透明にする
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 30));
        scoreLabel.setForeground(Color.RED); // 赤い文字
        add(scoreLabel);
    }

    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }
}
