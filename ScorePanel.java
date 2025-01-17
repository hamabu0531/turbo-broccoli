import javax.swing.JPanel;       // JPanelを使用
import javax.swing.JLabel;       // スコア表示用のJLabelを使用
import java.awt.FlowLayout;      // レイアウトマネージャにFlowLayoutを使用
import java.awt.Font;            // フォント設定にFontを使用
import java.awt.Color;           // 色設定にColorを使用

public class ScorePanel extends JPanel {
    private JLabel scoreLabel;

    public ScorePanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER));//コンポーネントを横一列に並べるシンプルな配置
        setOpaque(false); // 背景を透明にする
        scoreLabel = new JLabel("[ Score: ¥0 ]");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 30));
        scoreLabel.setForeground(Color.RED); // 赤い文字
        add(scoreLabel);
    }

    public void updateScore(int score) {//Controllerで呼ぶ
        scoreLabel.setText("[Score: ¥" + score + "]");
    }
}
