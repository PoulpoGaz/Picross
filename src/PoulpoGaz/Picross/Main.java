package PoulpoGaz.Picross;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {

    public static void main(String[] args) {
        Main m = new Main();
    }

    public Main() {
        this.setSize(800, 800);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("Picross");
        this.setContentPane(new Picross("Mon super picross", 10));

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }
        initBar();
        this.pack();
        this.setVisible(true);
    }

    public void initBar() {
        JMenuBar bar = new JMenuBar();
        JMenu fichier = new JMenu("Fichier");
        JMenu jeu = new JMenu("Partie");
        JMenu help = new JMenu("Aide");

        JMenuItem file = new JMenuItem("Choisir un pack");
        JMenuItem quit = new JMenuItem("Quitter");
        JMenuItem reset = new JMenuItem("Recommencer");
        JMenuItem stat = new JMenuItem("Statistiques");
        JMenuItem htp = new JMenuItem("Comment jouer?");
        JMenuItem htc = new JMenuItem("Comment créer des niveaux?");

        file.addActionListener(new ChoosePack());
        quit.addActionListener(e -> System.exit(0));
        reset.addActionListener(new Reset());
        htp.addActionListener(e -> {
            JOptionPane jop = new JOptionPane();
            jop.showMessageDialog(null, "", "Comment jouer?", JOptionPane.INFORMATION_MESSAGE);
        });

        fichier.add(file);
        fichier.add(quit);
        jeu.add(reset);
        jeu.add(stat);
        help.add(htp);
        help.add(htc);
        bar.add(fichier);
        bar.add(jeu);
        bar.add(help);

        this.setJMenuBar(bar);
    }

    private class ChoosePack implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class Reset implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
