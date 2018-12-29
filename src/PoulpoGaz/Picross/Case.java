package PoulpoGaz.Picross;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Case extends JPanel {

    private boolean cross;
    private Color back;
    private int x, y;

    public Case(boolean value, Picross picross, int x, int y) {
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(40,40));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        cross = false;
        back = Color.WHITE;

        this.x = x;
        this.y = y;

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(picross.getState()) {
                    if(SwingUtilities.isLeftMouseButton(e)) {
                        if (getColor() == Color.BLACK) {
                            setColor(Color.WHITE);
                            if (value) {
                                picross.setBlackCase(picross.getBlackCase()-1);
                            } else {
                                picross.setWrongBlackCase(picross.getWrongBlackCase()-1);
                            }
                        }else {
                            setColor(Color.BLACK);
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
                        setColor(Color.WHITE);
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

    public void setColor(Color back) {
        this.back = back;
    }

    public int getPosX() {
        return x;
    }

    public int getPosY() {
        return y;
    }
}
