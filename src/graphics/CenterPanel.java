package graphics;

import game.Game24;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by inesa on 19/05/2016.
 */
public class CenterPanel  extends JPanel {

    private static final int SPACE = 50;

    public CenterPanel() {

        Game24 g = new Game24();
        g.readFile();

        ArrayList<String> scores = new ArrayList<String>();
        scores.add("Joao");
        scores.add("Ines");
        scores.add("Pedro");

        Random r = new Random();

        NumbersPanel numbersPanel = new NumbersPanel(g.challenges.get(r.nextInt(g.challenges.size())));
        OperationsPanel operationsPanel = new OperationsPanel(scores);
        add(numbersPanel);
        add(Box.createHorizontalStrut(SPACE));
        add(operationsPanel);
    }




}
