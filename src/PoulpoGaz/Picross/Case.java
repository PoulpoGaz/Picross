package PoulpoGaz.Picross;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Case extends JPanel {

    private boolean cross;
    private Color back;

    public Case(boolean value, int size, Picross picross) {
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setPreferredSize(new Dimension(size,size));
        this.cross=false;
        this.back=Color.WHITE;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(picross.getState()) {
                    if(SwingUtilities.isLeftMouseButton(e)) {
                        if (getColor() == Color.BLACK) {
                            setBack(Color.WHITE);
                            if (value) {
                                picross.setBlackCase(picross.getBlackCase()-1);
                            } else {
                                picross.setWrongBlackCase(picross.getWrongBlackCase()-1);
                            }
                        }else {
                            setBack(Color.BLACK);
                            if(value) {
                                picross.setBlackCase(picross.getBlackCase()+1);
                            }
                            else {
                                picross.setWrongBlackCase(picross.getWrongBlackCase()+1);
                            }
                        }
                        cross=false;
                    } else if(SwingUtilities.isRightMouseButton(e)) {
                        if(getColor()==Color.BLACK && value) {
                            picross.setBlackCase(picross.getBlackCase()-1);
                        }
                        else if(getColor()==Color.BLACK&& !value) {
                            picross.setWrongBlackCase(picross.getWrongBlackCase()-1);
                        }
                        cross = !cross;
                        setBack(Color.WHITE);
                    }
                    picross.update();
                    repaint();
                    picross.check();
                }
            }
        });
    }

    public void paintComponent(Graphics g) {
        g.setColor(getColor());
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

    public Color getColor() {
        return back;
    }

    public void setBack(Color back) {
        this.back = back;
    }
}
