package zadanie.gui.paintableComponents;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

public class TasmaTransportowaGuiComponent extends JPanel {

    private final int x;
    private final int y;
    private int indexCegly;
    BufferedImage img;

    public TasmaTransportowaGuiComponent(int x, int y, int indexCegly) {
        this.x = x;
        this.y = y;
        this.indexCegly = indexCegly;
        URL resource = null;
        try {
            resource = Path.of(System.getProperty("user.dir") + "/trails.png").toUri().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            img = ImageIO.read(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g.drawImage(img, x, y, this);
        Color kolorCegly = Static.indexAndColorMap.get(indexCegly);
        if (kolorCegly!=null){
            g2.setColor(kolorCegly);
            g2.fillOval(x + 25, y + 25, 25, 25);
        }
        repaint();
    }

    public void setBrickIdx(int idx) {
        indexCegly = idx;
    }
}