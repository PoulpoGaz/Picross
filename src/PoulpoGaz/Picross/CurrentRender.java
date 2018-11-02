package PoulpoGaz.Picross;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CurrentRender extends JPanel {

    private int width;
    private int height;
    private int ratioW;
    private int ratioH;
    private int size;
    private ArrayList<Case> cases = new ArrayList<>();

    public CurrentRender(int size) {
        this.size = size;
        this.setPreferredSize(new Dimension(size*20,size*20));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void update(ArrayList<Case> cases, int width, int height) {
        this.cases = cases;
        ratioW = size*20/width;
        ratioH = size*20/height;
        this.width=width*ratioW;
        this.height=height*ratioH;
        repaint();
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        int x = 0;
        int y = 0;
        for (Case aCase : cases) {
            g.setColor(aCase.getBack());
            g.fillRect(x*ratioW, y*ratioH,ratioW,height);
            x++;
            if (x == 5) {
                x = 0;
                y++;
            }
        }
    }
}
