package graphics;

import connections.Peer;

import java.awt.*;

import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame(Peer peer){
        super("Jogo 24");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        CenterPanel centerPanel = new CenterPanel(peer);
        setLayout(new BorderLayout());
        NorthPanel north = new NorthPanel();
        Chat east = new Chat();
        east.add(Box.createHorizontalStrut(100));
        Panel west = new Panel();
        west.add(Box.createHorizontalStrut(10));
        South south = new South(peer);
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

