package PoulpoGaz.Picross;

import javax.swing.*;
import java.awt.*;

public class NumPanel extends JPanel {

    private JPanel content = new JPanel();
    private boolean axis;

    public NumPanel(int size, boolean axis) {
        super();
        if(axis) this.setPreferredSize(new Dimension(size,size*2));
        else this.setPreferredSize(new Dimension(size*2,size));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setBackground(Color.WHITE);
        this.axis = axis;
        if(axis) content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
        else content.setLayout(new BoxLayout(content, BoxLayout.LINE_AXIS));
    }

    public void addLabel(String name) {
        if(axis) content.add(new JLabel(name));
        else content.add(new JLabel(name + " "));
    }

    public void finish() {
        content.setBackground(Color.WHITE);
        this.add(content);
    }
}