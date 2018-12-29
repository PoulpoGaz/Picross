package PoulpoGaz.Picross;

import javax.swing.*;
import java.awt.*;

public class NumPanel extends JPanel {

    private JPanel content = new JPanel();
    private boolean axis;

    public NumPanel(boolean axis) {
        super();
        if(axis) this.setPreferredSize(new Dimension(40,80));
        else this.setPreferredSize(new Dimension(80,40));
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