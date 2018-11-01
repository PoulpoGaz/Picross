package PoulpoGaz.Picross;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Case extends JPanel {

    private boolean cross;
    private int size;
    private Color back;

    public Case(boolean value, int size) {
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setPreferredSize(new Dimension(size*10,size*10));
        this.cross=false;
        this.size=size;
        this.back=Color.WHITE;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e)) {
                    if(getBack()==Color.BLACK) setBack(Color.WHITE);
                    else setBack(Color.BLACK);
                    cross=false;
                } else if(SwingUtilities.isRightMouseButton(e)) {
                    cross = !cross;
                    setBack(Color.WHITE);
                }
                repaint();
            }
        });
    }

    public void paintComponent(Graphics g) {
        g.setColor(getBack());
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        if(cross) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.BLACK);
            BasicStroke line = new BasicStroke(1.5f);
            g2d.setStroke(line);
            g.drawLine(0, 0, this.getWidth(), this.getHeight());
            g.drawLine(0,this.getWidth(), this.getHeight(), 0);
        }
    }

    public Color getBack() {
        return back;
    }

    public void setBack(Color back) {
        this.back = back;
    }
}