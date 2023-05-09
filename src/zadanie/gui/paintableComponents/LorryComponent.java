package zadanie.gui.paintableComponents;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

public class LorryComponent extends JPanel {

    private final int x;
    private final int y;
    private int indexCegly;
    BufferedImage img;
    public LorryComponent(int x, int y, int indexCegly) {
        this.x = x;
        this.y = y;
        this.indexCegly = indexCegly;
        URL resource = null;
        try {
            resource = Path.of(System.getProperty("user.dir")+"/lorry.png").toUri().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            img = ImageIO.read(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   String masaCiezarowki = "0";
   String iloscCegiel = "0";
   String lorryNo = "0";

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g.drawImage(img, 0, 180, this);
        g.drawString("masa: " + masaCiezarowki,0,130);
        g.drawString("ilość cegieł: " + iloscCegiel,0,180);
        g.drawString("numer: " + lorryNo,0,250);
        repaint();
    }
    public void setBrickIdx(int idx) {
        indexCegly = idx;
    }
    public void setBrickWeight(int idx) {
        indexCegly = idx;
    }

    public void setLorryWeight(int iloscCegielWCiezarowce) {
        this.masaCiezarowki = String.valueOf(iloscCegielWCiezarowce);
    }
    public void setLorryNo(int lorryNo){
        this.lorryNo = String.valueOf(lorryNo);
    }


    public void setBrickQty(int iloscCegiel) {
       this.iloscCegiel=String.valueOf(iloscCegiel);
    }
}