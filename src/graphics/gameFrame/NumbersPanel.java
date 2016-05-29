package graphics.gameFrame;

import connections.peer2peer.Peer;
import game.Game24;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by inesa on 19/05/2016.
 */
class NumbersPanel extends JPanel {
    private static final int PREF_W = 400;
    private static final int PREF_H = 400;
    private static final int REC_WIDTH = 200;
    private ArrayList<BufferedImage> squares = new ArrayList<BufferedImage>();
    private ArrayList<Integer> challenges = new ArrayList<>();
    private Game24 game;



    public NumbersPanel(ArrayList<Integer> challenges, Game24 game) {
        this.game = game;
        this.challenges = challenges;
        // setBorder(BorderFactory.createLineBorder(Color.red));
        setPreferredSize(new Dimension(PREF_W, PREF_H));

        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 2; i++) {
                addSquare(REC_WIDTH, REC_WIDTH, this.challenges.get(i * 2 + j));
            }
        }

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getX() >= 0 && e.getX() <= 200) {
                    if (e.getY() >= 0 && e.getY() <= 200) {
                        game.setEquation(game.getEquation() + String.valueOf(challenges.get(0)));
                        game.stateMachine(String.valueOf(challenges.get(0)));
                    } else if (e.getY() > 200 && e.getY() <= 400) {
                        game.setEquation(game.getEquation() + String.valueOf(challenges.get(2)));
                        game.stateMachine(String.valueOf(challenges.get(2)));
                    }
                } else if (e.getX() > 200 && e.getX() <= 400) {
                    if (e.getY() >= 0 && e.getY() <= 200) {
                        game.setEquation(game.getEquation() + String.valueOf(challenges.get(1)));
                        game.stateMachine(String.valueOf(challenges.get(1)));

                    } else if (e.getY() > 200 && e.getY() <= 400) {
                        game.setEquation(game.getEquation() + String.valueOf(challenges.get(3)));
                        game.stateMachine(String.valueOf(challenges.get(3)));
                    }
                }
                GameFrame.getSouth().getEquation().setText("Equation: " + game.getEquation());
                GameFrame.getSouth().getEquation().paintImmediately(GameFrame.getSouth().getEquation().getVisibleRect());
                if ( game.check24(game.getEquation())) {
                    Peer.getInstance().setWinner(game.getEquation());
                } else {
                    System.out.println(game.getEquation() + " it's not 24");
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        System.out.println(this.toString());
    }

    public void addSquare(int width, int height, int number) {
        BufferedImage rect;
        int posX = number % 5;
        int posY = number / 5;
        try {
            rect = ImageIO.read(new File(System.getProperty("user.dir") + "/resources/images/all.png"));
            BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = resizedImage.createGraphics();
            int h = rect.getHeight() / 2;
            int w = rect.getWidth() / 5;
            g.drawImage(rect.getSubimage(posX * w, posY * h, w, h), 0, 0, width, height, null);
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
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                g2.drawImage(squares.get(i * 2 + j), i * REC_WIDTH, j * REC_WIDTH, null);
            }
        }
    }

}
