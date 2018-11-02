package PoulpoGaz.Picross;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Picross extends JPanel {

    public int wrongBlackCase=0;
    public int blackCase=0;
    public int nbBlackCase=0;
    public boolean state;
    public int map[][] = new int[5][5];
    public JPanel content = new JPanel();
    public JPanel game = new JPanel();
    public JLabel nameLbl = new JLabel();
    public CurrentRender cr;
    public ArrayList<Case> cases = new ArrayList<>();

    public Picross(String path) {
        state=true;
        int x=0, y=0;
        game.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        int size=6;
        String name = "";

        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridheight=2;
        gbc.gridwidth=2;
        cr = new CurrentRender(size);
        game.add(cr,gbc);

        File pack = new File(path + ".picross");

        try {
            FileReader fr = new FileReader(pack);
            int i;
            boolean start = false;

            gbc.gridheight=1;
            gbc.gridwidth=1;

            while ((i=fr.read())!=-1) {
                char c = (char)i;
                if(start) {
                    Case Case;
                    gbc.gridx=x+2;
                    gbc.gridy=y+2;
                    if(c=='0') {
                        map[y][x]=0;
                        Case = new Case(false, size, this);
                        game.add(Case, gbc);
                        cases.add(Case);
                    }
                    else if(c=='1') {
                        map[y][x]=1;
                        Case = new Case(true, size, this);
                        game.add(Case, gbc);
                        cases.add(Case);
                        nbBlackCase++;
                    }
                    x++;
                    if(x==5) {
                        x=0;
                        y++;
                    }
                } else {
                    if(c==':') start = true;
                    else name+=c;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int total;
        gbc.gridheight=1;
        gbc.gridwidth=2;
        for(x=0;x<5;x++) {
            total=0;
            NumPanel num = new NumPanel(size, false);
            for(y=0; y<5;y++) {
                if(map[y][x]==1) total++;
                else if(map[y][x]==0) {
                    if(total!=0) {
                        num.addLabel(""+total);
                    }
                    total=0;
                }
                if(y==4&&total!=0) {
                    num.addLabel(""+total);
                }
            }
            gbc.gridx=0;
            gbc.gridy=x+2;
            num.finish();
            game.add(num,gbc);
        }

        gbc.gridwidth=1;
        gbc.gridheight=2;
        for(y=0;y<5;y++) {
            total=0;
            NumPanel num = new NumPanel(size, true);
            for(x=0; x<5;x++) {
                if(map[y][x]==1) total++;
                else if(map[y][x]==0) {
                    if(total!=0) {
                        num.addLabel(""+total);
                    }
                    total=0;
                }
                if(x==4&&total!=0) {
                    num.addLabel(""+total);
                }
            }
            gbc.gridx=y+2;
            gbc.gridy=0;
            num.finish();
            game.add(num,gbc);
        }

        String packName = pack.getName();
        packName = packName.replace(".picross", "");
        nameLbl.setText("Pack: " + packName + " Picross: "+name);

        content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
        content.add(nameLbl);
        content.add(game);
        nameLbl.setBackground(Color.WHITE);
        game.setBackground(Color.WHITE);
        content.setBackground(Color.WHITE);
        this.add(content);
        this.setBackground(Color.WHITE);
    }

    public void check() {
        if(nbBlackCase==blackCase && wrongBlackCase==0) {
            state=false;
            System.out.println("VICTORY");
        }
    }

    public void update() {
        cr.update(cases, 5, 5);
    }
}
