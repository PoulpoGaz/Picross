package PoulpoGaz.Picross;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CurrentRender extends JPanel {

    private int cote=0;
    private int ratio=0;
    private int size;
    private ArrayList<Case> cases = new ArrayList<>();

    public CurrentRender(int size) {
        this.size = size*2;
        this.setPreferredSize(new Dimension(this.size,this.size));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void update(ArrayList<Case> cases, int cote) {
        this.cases = cases;
        ratio = size/cote;
        this.cote=cote*ratio;

        repaint();
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        int x=0, y=0;
        for (Case aCase : cases) {
            g.setColor(aCase.getBack());
            g.fillRect(x*ratio, y*ratio,cote,cote);
            y++;
            if (y==cote/ratio) {
                y=0;
                x++;
            }
        }
    }
}
