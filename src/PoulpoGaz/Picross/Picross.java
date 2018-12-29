package PoulpoGaz.Picross;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Picross extends JPanel {

    private int wrongBlackCase;
    private int blackCase;
    private int nbBlackCase;
    private int maxLvl;
    private int width;
    private int height;
    private int level;
    private boolean state;
    private int map[][];
    private CurrentRender cr;
    private ArrayList<Case> cases;
    private Main m;
    private String path, line;
    private String packName;

    public Picross(Main main) {
        this.m = main;
        restoreProgress();
        init();
    }

    public int getMaxLvl() {
        try {
            LineNumberReader br = new LineNumberReader(new BufferedReader(new FileReader(path)));
            while(br.readLine() !=null) {}
            maxLvl = br.getLineNumber();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return maxLvl;
    }

    public int getWH(String line) {
        int wh = Integer.parseInt(line.substring(0,3));
        this.line = line.substring(3, line.length());
        return wh;
    }

    public void init() {
        this.removeAll();
        this.state=true;
        this.cases = new ArrayList<>();
        JPanel content = new JPanel();
        JPanel game = new JPanel();

        this.wrongBlackCase=0;
        this.blackCase=0;
        this.nbBlackCase=0;
        int size = 40;

        this.maxLvl = getMaxLvl();

        this.line = "";

        int x=0, y=0;

        game.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        String name = "";

        File pack = new File(path);

        try {
            BufferedReader br = new BufferedReader(new FileReader(pack));
            for(int a=0;a<level;a++) {
                br.readLine();
            }
            int i;
            line = br.readLine();

            name = line.substring(0, line.indexOf(":"));
            line = line.substring(line.indexOf(":")+1, line.length());

            width = getWH(line);
            height = getWH(line);
            map = new int[height][width];

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
        packName = pack.getName();
        packName = packName.replace(".picross", "");

        JPanel label = new JPanel();
        label.setLayout(new BorderLayout());
        JLabel packNameLbl = new JLabel(packName);
        packNameLbl.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel nameLbl = new JLabel(name + " - " + width + "x" + height + "px");
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
                level--;
                jop.showMessageDialog(null, "Vous avez finit le pack " + packName + "\n                  Bien joué!", "Pack fini à 100%!", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public void update() {
        cr.update(cases, width, height);
    }

    public String getPath() {
        return path;
    }

    public int getLevel() {
        return level;
    }

    public void restoreProgress() {
        BufferedReader br;

        File f = new File("src/PoulpoGaz/save.picross");

        if(!f.exists()) {
            path = "ressources/Default.picross";
            File f2 = new File(path);
            System.out.println(f2.getAbsolutePath());
        } else {
            try {
                br = new BufferedReader(new FileReader(f));
                path = br.readLine();

                File f2 = new File(path);
                if(!f2.exists()) {
                    err(3);
                    path = "ressources/Default.picross";
                    br.close();
                    f.delete();
                    System.exit(3);
                }

                try {
                    level = Integer.parseInt(br.readLine());
                } catch(NumberFormatException e) {
                    br.close();
                    f.delete();
                    err(1);
                }
                br.close();

                if(level > getMaxLvl()) {
                    f.delete();
                    err(2);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File f3 = new File(path);
        if(!f3.exists()) {
            err(4);
        }
    }

    public void err(int err) {
        switch (err) {
            case 1:
                JOptionPane.showMessageDialog(null,"Fichier de sauvegarde vide\nSuppression...", "Erreur", JOptionPane.ERROR_MESSAGE);
                break;
            case 2:
                JOptionPane.showMessageDialog(null,"Le fichier de sauvegarde est corompu", "Erreur", JOptionPane.ERROR_MESSAGE);
                break;
            case 3:
                JOptionPane.showMessageDialog(null, "Le fichier contenant le pack de niveau\na été supprimé(" + path + ").", "Erreur critique", JOptionPane.ERROR_MESSAGE);
                break;
            case 4:
                JOptionPane.showMessageDialog(null, "Le fichier contenant le pack défaut a été supprimé","Erreur", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    public void reset() {
        level = 0;
        init();
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getWrongBlackCase() {
        return wrongBlackCase;
    }

    public int getBlackCase() {
        return blackCase;
    }

    public void setBlackCase(int nb) {
        blackCase = nb;
    }

    public void setWrongBlackCase(int nb) {
        wrongBlackCase = nb;
    }

    public boolean getState() {
        return state;
    }
}
