import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.border.LineBorder;
import java.awt.event.*;

public class TateLifePanel extends JPanel {
    private JLabel tateLifePanel;
    Image image;

    public TateLifePanel() {
        //setLayout(new FlowLayout(FlowLayout.NORTH));//コンポーネントを横一列に並べるシンプルな配置
        try{
            image = ImageIO.read(new File("tatemini.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
        setOpaque(false); // 背景を透明にする
        add(tateLifePanel);
    }

    private void setTateLifeVisible(boolean visible) {
        tateLifePanel.setVisible(visible);
    }

    //盾の表示
}