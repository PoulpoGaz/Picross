package PoulpoGaz.Picross;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CurrentRender extends JPanel {

    private int height = 0, width = 0;
    private int ratio = 0, div = 0;
    private int size;
    private ArrayList<Case> cases = new ArrayList<>();

    public CurrentRender(int s) {
        size = s*2;
        this.setPreferredSize(new Dimension(size,size));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void update(ArrayList<Case> cases, int w, int h) {
        this.cases = cases;

        if(w>h) {
            ratio = size/w;
        } else {
            ratio = size/h;
        }

        width = w;
        height = h;

        div = size / (h * ratio);

        repaint();
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        int x = 0, y = 0;
        for (Case aCase : cases) {
            g.setColor(aCase.getColor());
            g.fillRect(x * ratio, y * ratio, ratio, ratio);
            y++;
            if (y == size / (ratio * div)) {
                y = 0;
                x++;
            }
        }
    }
}
