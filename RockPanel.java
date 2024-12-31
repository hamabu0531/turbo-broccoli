import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.border.LineBorder;
import java.awt.event.*;

/// 岩クラス
public class RockPanel extends JPanel{
    // メンバ変数
    int x, y, width, height; // x=-1, 0, 1で位置決定
    Image image;
    
    // コンストラクタ
    public RockPanel(){
        x = 0; y = 0;
        width = 100; height = 100;
        try{
            image = ImageIO.read(new File("URL"));
        }catch(IOException e){
            e.printStackTrace();
        }
        setOpaque(false);//背景を透過するやつ。
    }
    
    public void paintComponent(Graphics g){
        int offsetX = 250, offsetY = 200;
        super.paintComponent(g);
        if(image!=null){
            g.drawImage(image, x*200+offsetX, y*100+offsetY, getFocusCycleRootAncestor());
        }else{
            g.setColor(Color.ORANGE);
            g.fillOval(x*200+offsetX, (int)(y*1.35+offsetY), width, height);

        }
    }

    public void updateRockPos(int rockPosY) {
        this.y = rockPosY;
        repaint();
    }

    public void EmergeRock(int rockPosX, int rockPosY) {
        this.x = rockPosX;
        this.y = rockPosY;
        repaint();
    }
}


