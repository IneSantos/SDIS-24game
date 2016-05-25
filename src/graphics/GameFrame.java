package graphics;

import connections.Peer;
import game.Game24;

import java.awt.*;

import javax.swing.*;

public class GameFrame extends JFrame {
    static South south;

    public GameFrame(Peer peer){
        super("Jogo 24");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        Game24 g = new Game24();

        CenterPanel centerPanel = new CenterPanel(peer, g);
        setLayout(new BorderLayout());

        NorthPanel north = new NorthPanel();
        Chat east = new Chat(peer, g);
        east.add(Box.createHorizontalStrut(100));
        Panel west = new Panel();

        west.add(Box.createHorizontalStrut(10));
        south = new South(peer, g);
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

    public static South getSouth() {
        return south;
    }
}

