package PoulpoGaz.Picross;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Picross extends JPanel {

    public int wrongBlackCase, blackCase, nbBlackCase, maxLvl, width, height, size, level=0;
    public boolean state;
    public int map[][];
    public JPanel content, game;
    public CurrentRender cr;
    public ArrayList<Case> cases;
    public Main m;
    public String path, line;

    public Picross(String path, Main main) {
        this.path = path;
        this.m = main;
        init();
    }

    public int getMaxLvl() {
        try {
            LineNumberReader br = new LineNumberReader(new BufferedReader(new FileReader(path + ".picross")));
            String i;
            while((i=br.readLine())!=null) {
                maxLvl = br.getLineNumber();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return maxLvl;
    }

    public int getWH(String line) {
        int wh = Integer.parseInt(line.substring(0,3));
        this.line = line.substring(3, line.length());
        System.out.println(wh + "\n" + this.line);
        return wh;
    }

    public void init() {
        this.removeAll();
        this.state=true;
        this.cases = new ArrayList<>();
        this.content = new JPanel();
        this.game = new JPanel();

        this.wrongBlackCase=0;
        this.blackCase=0;
        this.nbBlackCase=0;
        this.size=0;

        this.maxLvl = getMaxLvl();

        this.line = "";

        int x=0, y=0;

        game.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        String name = "";

        File pack = new File(path + ".picross");

        try {
            BufferedReader br = new BufferedReader(new FileReader(pack));
            for(int a=0;a<level;a++) br.readLine();
            int i;
            line = br.readLine();

            name = line.substring(0, line.indexOf(":"));
            line = line.substring(line.indexOf(":")+1, line.length());

            width = getWH(line);
            height = getWH(line);
            this.map = new int[height][width];
            size = 40;

            gbc.gridx=0;
            gbc.gridy=0;
            gbc.gridheight=2;
            gbc.gridwidth=2;
            cr = new CurrentRender(size);
            game.add(cr,gbc);

            gbc.gridheight=1;
            gbc.gridwidth=1;

            for(i=0; i<line.length(); i++) {
                char c = line.charAt(i);
                System.out.println(c);

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
                y++;
                if(y==height) {
                    y=0;
                    x++;
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int total;
        gbc.gridheight=2;
        gbc.gridwidth=1;
        for(x=0;x<width;x++) {
            total=0;
            NumPanel num = new NumPanel(size, true);
            for(y=0; y<height;y++) {
                if(map[y][x]==1) total++;
                else if(map[y][x]==0) {
                    if(total!=0) {
                        num.addLabel(""+total);
                    }
                    total=0;
                }
                if(y==height-1&&total!=0) {
                    num.addLabel(""+total);
                }
            }
            gbc.gridx=x+2;
            gbc.gridy=0;
            num.finish();
            game.add(num,gbc);
        }

        gbc.gridwidth=2;
        gbc.gridheight=1;
        for(y=0;y<height;y++) {
            total=0;
            NumPanel num = new NumPanel(size, false);
            for(x=0; x<width;x++) {
                if(map[y][x]==1) total++;
                else if(map[y][x]==0) {
                    if(total!=0) {
                        num.addLabel(""+total);
                    }
                    total=0;
                }
                if(x==width-1&&total!=0) {
                    num.addLabel(""+total);
                }
            }
            gbc.gridx=0;
            gbc.gridy=y+2;
            num.finish();
            game.add(num,gbc);
        }
        String packName = pack.getName();
        packName = packName.replace(".picross", "");

        JPanel label = new JPanel();
        label.setLayout(new BorderLayout());
        JLabel packNameLbl = new JLabel(packName);
        packNameLbl.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel nameLbl = new JLabel(name);
        nameLbl.setHorizontalAlignment(SwingConstants.CENTER);

        label.add(packNameLbl, BorderLayout.NORTH);
        label.add(nameLbl, BorderLayout.CENTER);

        content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
        content.add(label);
        content.add(game);

        label.setBackground(Color.WHITE);
        game.setBackground(Color.WHITE);
        content.setBackground(Color.WHITE);
        this.add(content);
        this.setBackground(Color.WHITE);
        SwingUtilities.updateComponentTreeUI(m);
        m.pack();
        this.repaint();
    }

    public void check() {
        if(nbBlackCase==blackCase && wrongBlackCase==0) {
            state=false;
            JOptionPane jop = new JOptionPane();
            level++;
            if(maxLvl>level) {
                jop.showMessageDialog(null, "Bien joué!", "Niveau réussit!", JOptionPane.INFORMATION_MESSAGE);
                init();
            } else {
                jop.showMessageDialog(null, "Vous avez finit le pack " + path + "\n                  Bien joué!", "Pack fini à 100%!", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public void update() {
        if(width>height) cr.update(cases, width);
        else cr.update(cases, height);
    }
}
