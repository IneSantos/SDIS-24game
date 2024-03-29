package graphics.gameFrame;

import connections.tcp.TCPClient;
import game.Game24;

import javax.swing.*;
import java.awt.*;

/**
 * Created by inesa on 24/05/2016.
 */
public class South extends JPanel {
    private JLabel equation;

    public South(TCPClient TCPClient, Game24 game) {

        JLabel jlabel = new JLabel("Room: " + TCPClient.getInstance().getDataBase().getCurrentRoom().getName());
        jlabel.setFont(new Font("Verdana", 2, 20));
        add(jlabel);

        add(Box.createHorizontalStrut(50));

        equation = new JLabel("Equation: " + game.getEquation());
        equation.setFont(new Font("Verdana", 2, 20));
        add(equation);

        add(Box.createHorizontalStrut(50));
        System.out.println("Nickname: " +  TCPClient.getInstance().getPeerID().getUsername());
        jlabel = new JLabel("Nickname: " + TCPClient.getInstance().getPeerID().getUsername());
        jlabel.setFont(new Font("Verdana", 2, 20));
        add(jlabel);


        add(Box.createHorizontalStrut(50));

        //TODO: add number of players
        jlabel = new JLabel("Nº Players: ");
        jlabel.setFont(new Font("Verdana", 2, 20));
        add(jlabel);

    }

    public JLabel getEquation() {
        return equation;
    }

}
