package graphs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by inesa on 19/05/2016.
 */
class NumbersPanel extends JPanel {
    private static final int PREF_W = 400;
    private static final int PREF_H = 400;
    private static final int REC_WIDTH = 200;
    private ArrayList<BufferedImage> squares = new ArrayList<BufferedImage>();
    private ArrayList<Integer> challenges = new ArrayList<>();


    public NumbersPanel(ArrayList<Integer> challenges) {
        this.challenges = challenges;
        //setBorder(BorderFactory.createLineBorder(Color.black));
        
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 2; i++) {
                addSquare(REC_WIDTH, REC_WIDTH, this.challenges.get(i*2+j));
            }
        }

    }

    public void addSquare(int width, int height, int number) {
        BufferedImage rect;
        int posX = number%6;
        int posY = number/6;
        try {
            rect = ImageIO.read(new File(System.getProperty("user.dir") + "/resources/numbers.png"));
            BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = resizedImage.createGraphics();
            int h = rect.getHeight()/3;
            int w = rect.getWidth()/6;
            g.drawImage(rect.getSubimage(posX*w, posY*h ,w, h), 0, 0, width, height, null);
            g.dispose();
            squares.add(resizedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PREF_W, PREF_H);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for(int i =0; i < 2; i++){
            for(int j= 0; j < 2; j++) {
                g2.drawImage(squares.get(i*2+j), i * REC_WIDTH, j* REC_WIDTH, null);
            }
        }
    }

}
