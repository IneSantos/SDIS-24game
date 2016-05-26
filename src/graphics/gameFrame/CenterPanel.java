package graphics.gameFrame;

import connections.peer2peer.Peer;
import game.Game24;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by inesa on 19/05/2016.
 */
public class CenterPanel  extends JPanel {

    private static final int SPACE = 50;

    public CenterPanel(Peer peer, Game24 g) {
        g.readFile();

        ArrayList<String> scores = new ArrayList<String>();

        Random r = new Random();

        NumbersPanel numbersPanel = new NumbersPanel(g.challenges.get(r.nextInt(g.challenges.size())), g);
        OperationsPanel operationsPanel = new OperationsPanel(scores, g);
        add(numbersPanel);
        add(Box.createHorizontalStrut(SPACE));
        add(operationsPanel);

    }




}
