package graphics;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by inesa on 19/05/2016.
 */
class  OperationsPanel  extends JPanel {

    private static final int PREF_W = 200;
    private static final int PREF_H = 400;
    private static final int REC_WIDTH = 100;
    private ArrayList<BufferedImage> squares = new ArrayList<BufferedImage>();
    private ArrayList<String> scores = new ArrayList<String>();

    public OperationsPanel(ArrayList<String> scores) {
        this.scores = scores;
        //setBorder(BorderFactory.createLineBorder(Color.black));

        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 2; i++) {
                addSquare(REC_WIDTH, REC_WIDTH, i*2+j, i, j);
            }
        }

        JLabel jlabel = new JLabel("Scores: ");
        jlabel.setFont(new Font("Verdana",1,25));
        add(jlabel);
        for(int k = 0; k < this.scores.size(); k++){
            jlabel = new JLabel(k + ". " + this.scores.get(k));
            jlabel.setFont(new Font("Verdana",1,15));
            jlabel.setPreferredSize(new Dimension(100,20));
            add(jlabel);
        }
    }

    public void addSquare(int width, int height, int number, int i, int j) {
        BufferedImage rect;
        int posX = number%4;
        int posY = number/4;
        try {
            rect = ImageIO.read(new File(System.getProperty("user.dir") + "/resources/operations.png"));
            BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = resizedImage.createGraphics();
            int h = rect.getHeight()/1;
            int w = rect.getWidth()/4;
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
                g2.drawImage(squares.get(i*2+j), i * REC_WIDTH , j* REC_WIDTH + REC_WIDTH*2, null);
            }
        }
    }

}