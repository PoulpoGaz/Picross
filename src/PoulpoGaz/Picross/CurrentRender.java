package PoulpoGaz.Picross;

import javax.swing.*;
import java.awt.*;

public class CurrentRender extends JPanel {

    public CurrentRender(int size) {
        this.setPreferredSize(new Dimension(size*10,size*10));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setBackground(Color.PINK);
    }
}
