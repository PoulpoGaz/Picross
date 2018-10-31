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

    private Main() {
        FileInputStream fis;
        int resultat[][];

        try {
            fis = new FileInputStream(new File("config.picross"));
            BufferedReader configFile = new BufferedReader(new InputStreamReader(fis));
            String line;
            int i2=0;
            while ((line = configFile.readLine()) != null) {
                pngList.add(i2, line);
                i2++;
            }
            fis.close();
        } catch (FileNotFoundException e) {
            exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            for (i=0; i < pngList.size(); i++) {
                BufferedImage img = ImageIO.read(new File(pngList.get(i) + ".png"));
                resultat = convertToRGB(img, pngList.get(i));
                BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(new File(pngList.get(i) + ".picross")));
                for (int x = 0; x < img.getWidth(); x++) {
                    for (int y = 0; y < img.getHeight(); y++) {
                        if (resultat[y][x] == -1) fos.write("0".getBytes());
                        else if (resultat[y][x] == -16777216) fos.write("1".getBytes());
                    }
                }
                fos.close();
            }
        } catch(IIOException e) {
            exit(2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        exit(0);
    }

    private int[][] convertToRGB(BufferedImage image, String imageName) {
        int width = image.getWidth();
        int height = image.getHeight();
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
        if(err==1) {
            String str = "Le fichier config.picross n'existe pas.";
            System.out.println(str);
            addLog(str);
        } else if(err==2) {
            String str = "L'image " + pngList.get(i) + " n'existe pas ou n'est pas au format png.";
            System.out.println(str);
            addLog(str);
        } else if(err==3) {
            String str = "L'image " + pngList.get(i) + " est trop grande. La taille maximal est de 100*100px.";
            System.out.println(str);
            addLog(str);
        }
        try {
            PrintStream str = new PrintStream(new File("picross.log"));
            System.setOut(str);
            System.out.println(log);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(err>0) System.exit(0);
    }
}