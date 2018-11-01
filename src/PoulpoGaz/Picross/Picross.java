package PoulpoGaz.Picross;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Picross extends JPanel {

    public Picross(String path, int size) {
        int x=0, y=0;
        int map[][] = new int[5][5];
        try {
            FileReader fr = new FileReader(new File(path + ".picross"));

            int i=0;

            while ((i=fr.read())!=-1) {
                char c = (char)i;
                if(c=='0') map[y][x]=0;
                else if(c=='1') map[y][x]=1;
                x++;
                if(x==5) {
                    x=0;
                    y++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx=0;
        gbc.gridy=0;
        content.add(new CurrentRender(size),gbc);
        for(y=0; y<5; y++) {
            for(x=0;x<5;x++) {
                gbc.gridx=x+1;
                gbc.gridy=y+1;
                if(map[x][y]==0) content.add(new Case(false, size), gbc);
                else if(map[x][y]==1) content.add(new Case(true, size), gbc);
            }
        }
        int total;


        for(x=0;x<5;x++) {
            total=0;
            NumPanel num = new NumPanel(size);
            for(y=0; y<5;y++) {
                if(map[y][x]==1) total++;
                else if(map[y][x]==0) {
                    if(total!=0) {
                        num.add(new JLabel(""+total));
                    }
                    total=0;
                }
            }
            gbc.gridx=0;
            gbc.gridy=x+1;
            content.add(num,gbc);
        }

        for(y=0;y<5;y++) {
            total=0;
            NumPanel num = new NumPanel(size);
            for(x=0; x<5;x++) {
                if(map[y][x]==1) total++;
                else if(map[y][x]==0) {
                    if(total!=0) {
                        num.add(new JLabel(""+total));
                    }
                    total=0;
                }
                if(x==4&&total!=0) {
                    num.add(new JLabel(""+total));
                }
            }
            gbc.gridx=y+1;
            gbc.gridy=0;
            content.add(num,gbc);
        }
        this.add(content);
    }
}
