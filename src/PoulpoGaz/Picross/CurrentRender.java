package PoulpoGaz.Picross;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CurrentRender extends JPanel {

    private double ratio = 0;
    private int size = 80;
    private ArrayList<Case> cases = new ArrayList<>();

    public CurrentRender() {
        this.setPreferredSize(new Dimension(size,size));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void update(ArrayList<Case> cases, int w, int h) {
        this.cases = cases;

        if(w>h) {
            ratio = (double) size/ (double) w;
        } else {
            ratio = (double) size / (double) h;
        }

        repaint();
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        for (Case aCase : cases) {
            g.setColor(aCase.getColor());
            g.fillRect((int)(aCase.getPosX() * ratio), (int)(aCase.getPosY() * ratio),(int) Math.ceil(ratio), (int) Math.ceil(ratio));
        }
    }
}
