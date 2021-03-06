package PoulpoGaz.Picross;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class Main extends JFrame {

    public static void main(String[] args) {
        new Main();
    }

    private Picross pic = new Picross(this);

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

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });

        this.setContentPane(pic);
        initBar();
        this.pack();
        this.setVisible(true);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void initBar() {
        JMenuBar bar = new JMenuBar();
        JMenu fichier = new JMenu("Fichier");
        JMenu jeu = new JMenu("Partie");
        JMenu help = new JMenu("Aide");

        JMenuItem file = new JMenuItem("Choisir un pack");
        JMenuItem quit = new JMenuItem("Quitter");
        JMenuItem reset = new JMenuItem("Recommencer");
        JMenuItem restart = new JMenuItem("Recommencer le pack");
        JMenuItem htp = new JMenuItem("Comment jouer?");
        JMenuItem htc = new JMenuItem("Comment créer des niveaux?");

        file.addActionListener(new ChoosePack());

        quit.addActionListener(e -> {
            exit();
            System.exit(0);
        });

        reset.addActionListener(e -> pic.init());

        restart.addActionListener(e -> pic.reset());

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
            jop.showMessageDialog(null, "1: Créez une image d'une taille maximale de 15*15px.\n" +
                    "2: Dessinez quelque chose en noir et blanc.\n" +
                    "3: Sauvegardez au format .png.\n" +
                    "4: Utilisez le Picross Converter:\n" +
                    "  a: Créer un fichier config.picross\n" +
                    "  b: Dans celui-ci écrivez PicrossName puis sautez une ligne.\n" +
                    "     Écrivez votre nom de pack.\n" +
                    "  c: À la suite du nom, écrivez les noms des fichiers .png que vous souhaitez convertir.\n" +
                    "  d: Lancez le PicrossConverter qui doit être présent dans le même dossier que vos images" +
                    "Vous avez convertit vos images!\n\n" +
                    "Pour toute erreur, regardez le .log ou regardez le dossier Exemple.", "Comment créer des niveaux?", JOptionPane.INFORMATION_MESSAGE);
        });

        fichier.add(file);
        fichier.add(quit);
        jeu.add(reset);
        jeu.add(restart);
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
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc.setDialogTitle("Selectionner un pack de niveau");
            jfc.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Picross file(.picross)", "picross");
            jfc.addChoosableFileFilter(filter);

            int returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                pic.setPath(jfc.getSelectedFile().getPath());
                pic.setLevel(0);
                pic.init();
            }
        }
    }

    public void exit() {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("save.picross")));
            bos.write(pic.getPath().getBytes());
            bos.write(("\n"+pic.getLevel()).getBytes());
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
