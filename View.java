import javax.swing.*;
import java.awt.*;

// Vはユーザーに情報を表示する(主にJ~~関係)
// Vは、Mからデータを取得、Cの入力によって表示を変える

public class View extends JFrame {
    private JLabel scoreLabel;
    private JButton scoreButton;

    public View() {
        // フレーム設定
        setTitle("MVC Game Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLayout(new BorderLayout());

        // ラベルとボタン
        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(scoreLabel, BorderLayout.CENTER);

        scoreButton = new JButton("Increase Score");
        add(scoreButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    public JLabel getScoreLabel() {
        return scoreLabel;
    }

    public JButton getScoreButton() {
        return scoreButton;
    }
}
