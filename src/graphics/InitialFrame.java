package graphics;

import connections.Peer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by inesa on 22/05/2016.
 */
public class InitialFrame extends JFrame {

    public InitialFrame() throws HeadlessException {
        super("Jogo 24");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        CenterInitialFrame centerPanel = new CenterInitialFrame();
        setLayout(new BorderLayout());
        NorthPanel north = new NorthPanel();
        Panel east = new Panel();
        east.add(Box.createHorizontalStrut(100));
        Panel west = new Panel();
        west.add(Box.createHorizontalStrut(100));
        Panel south = new Panel();
        south.add(Box.createVerticalStrut(100));
        getContentPane().add(north, BorderLayout.PAGE_START);
        getContentPane().add(west, BorderLayout.WEST);
        getContentPane().add(south, BorderLayout.SOUTH);
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        getContentPane().add(east, BorderLayout.EAST);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
