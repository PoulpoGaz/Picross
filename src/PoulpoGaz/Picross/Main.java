package PoulpoGaz.Picross;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {

    public static void main(String[] args) {
        Main m = new Main();
    }

    public Main() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }
        this.setSize(800, 800);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("Picross");
        this.setContentPane(new Picross("Mon super picross"));
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
        reset.addActionListener(e -> {
            this.setContentPane(new Picross("Mon super picross"));
            SwingUtilities.updateComponentTreeUI(this);
        });
        htp.addActionListener(e -> {
            JOptionPane jop = new JOptionPane();
            jop.showMessageDialog(null, "Un picross, logigraphe, hanjie, griddler, nonogram ou encore logimage est un jeu de réflexion solitaire, qui consiste\n" +
                                        "à découvrir un dessin sur une grille en noircissant des cases, d'après des indices logiques laissés sur le bord de la\n" +
                                        "grille. Le but consiste à retrouver les cases noires dans chaque grille. Les chiffres donnés sur le côté et en haut de\n" +
                                        "la grille vous donnent des indices. Ils indiquent la taille des blocs de cases noires de la ligne ou de la colonne sur\n" +
                                        "laquelle ils se trouvent. Par exemple 3,4 à gauche d'une ligne indique qu'il y a, de gauche à droite, un bloc de 3 cases\n" +
                                        "noires puis un bloc de 4 cases noires sur cette ligne. En revanche, ce qui n'est pas mentionné et qui fait la difficulté,\n" +
                                        "est le nombre de cases blanches entre les cases noires. On sait simplement qu'il y en a au moins une.", "Comment jouer?", JOptionPane.INFORMATION_MESSAGE);
        });
        htc.addActionListener(e -> {
            JOptionPane jop = new JOptionPane();
            jop.showMessageDialog(null, "1: Créez une image d'une taille maximale de 100*100px.\n" +
                    "2: Dessinez quelque chose en noir et blanc.\n" +
                    "3: Sauvegardez au format .png.\n" +
                    "4: Utilisez le Picross Converter:\n" +
                    "  a: Dans le fichier config.picross, après PicrossName, écrivez le nom du pack\n" +
                    "  b: Dans les lignes suivante, écrivez le nom des images png\n" +
                    "  c: Sauvegardez et lancez le .jar du PicrossConverter\n" +
                    "Vous avez convertit vos images!\n\n" +
                    "Pour toute erreur, regardez le .log.", "Comment créer des niveaux?", JOptionPane.INFORMATION_MESSAGE);
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
}
