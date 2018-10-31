package PoulpoGaz.PicrossConverter;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class Main extends JFrame {

    public static void main(String[] args) {
        Main m = new Main();
    }

    private String log = "";
    private ArrayList<String> pngList = new ArrayList<>();
    private int i = 0;
    private String name = "";

    private Main() {
        FileInputStream fis;
        int resultat[][];

        try {
            //Lecture ligne par ligne du fichier config.picross
            fis = new FileInputStream(new File("config.picross"));
            BufferedReader configFile = new BufferedReader(new InputStreamReader(fis));
            String line, preLine = "";
            int i2=0;
            while ((line = configFile.readLine()) != null) {
                if(preLine.equals("PicrossName")) name=line;
                else if(!line.equals("PicrossName")){
                    //Ajout du nom du fichier png à l'ArrayList pngList
                    pngList.add(i2, line);
                    i2++;
                }
                preLine=line;
            }
            fis.close();
            if(name.equals("")) exit(4);
            if(pngList.isEmpty()) exit(5);
        } catch (FileNotFoundException e) {
            //Si le fichier config.picross n'existe pas
            exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(new File(name + ".picross")));
            //On parcourt l'ArrayLis pngList
            for (i=0; i < pngList.size(); i++) {
                BufferedImage img = ImageIO.read(new File(pngList.get(i) + ".png"));
                //On stocke les données des images dans un tableau d'int
                resultat = convertToRGB(img, pngList.get(i));
                //On crée un nouveau fichier et on écrit 0 si, il y a un pixel blanc et 1 s'il est noir
                fos.write((pngList.get(i)+":").getBytes());
                for (int x = 0; x < img.getWidth(); x++) {
                    for (int y = 0; y < img.getHeight(); y++) {
                        if (resultat[y][x] == -1) fos.write("0".getBytes());
                        else if (resultat[y][x] == -16777216) fos.write("1".getBytes());
                    }
                }
                fos.write("\n\r".getBytes());
            }
            fos.close();
        } catch(IIOException e) {
            //L'image n'existe pas
            exit(2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        exit(0);
    }

    private int[][] convertToRGB(BufferedImage image, String imageName) {
        int width = image.getWidth();
        int height = image.getHeight();
        //L'image est trop grande
        if(width>100 || height >100) exit(3);
        int[][] result = new int[height][width];
        double i=0;
        double wh = (double)width*(double)height;

        System.out.println("Convert: " + imageName);
        addLog("Convert: " + imageName);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                result[row][col] = image.getRGB(col, row);
                i++;
            }
            int pourcent = (int)(i/wh*100);
            System.out.println(pourcent + "%");
            addLog(pourcent + "%");
        }

        System.out.println("Finish!");
        addLog("Finish!");
        return result;
    }

    private void addLog(String str) {
        log+=str+"\n";
    }

    private void exit(int err) {
        String str;
        switch (err) {
            case 1:
                str = "Le fichier config.picross n'existe pas.";
                System.out.println(str);
                addLog(str);
                break;
            case 2:
                str = "L'image " + pngList.get(i) + " n'existe pas ou n'est pas au format png.";
                System.out.println(str);
                addLog(str);
                break;
            case 3:
                str = "L'image " + pngList.get(i) + " est trop grande. La taille maximal est de 100*100px.";
                System.out.println(str);
                addLog(str);
                break;
            case 4:
                str = "Le nom du fichier de sortie n'est pas spécifié.";
                System.out.println(str);
                addLog(str);
                break;
            case 5:
                str = "Aucune image n'est spécifié.";
                System.out.println(str);
                addLog(str);
                break;
        }
        try {
            //On écrit dans le fichier picross.log ce qui c'est passé.
            PrintStream ps = new PrintStream(new File("picross.log"));
            System.setOut(ps);
            System.out.println(log);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //Si il y a une erreur on quitte le programme
        if(err>0) System.exit(0);
    }
}