package PoulpoGaz.Picross;

import javax.swing.*;
import java.awt.*;

public class NumPanel extends JPanel {

    public NumPanel(int size) {
        super();
        this.setPreferredSize(new Dimension(size*10,size*10));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setBackground(Color.WHITE);
    }
}
