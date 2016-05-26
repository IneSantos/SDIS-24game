package graphics.gameFrame;

import connections.peer2peer.Peer;
import game.Game24;

import javax.swing.*;
import java.awt.*;

/**
 * Created by inesa on 24/05/2016.
 */
public class South extends JPanel {
    JLabel equation;

    public South(Peer peer, Game24 game){

        JLabel jlabel = new JLabel("Room: " + peer.getDataBase().getCurrentRoom().getName());
        jlabel.setFont(new Font("Verdana",2,20));
        add(jlabel);

        add(Box.createHorizontalStrut(50));

        equation = new JLabel("Equation: " + game.getEquation());
        equation.setFont(new Font("Verdana",2,20));
        add(equation);

        add(Box.createHorizontalStrut(50));
        jlabel = new JLabel("Nickname: " + peer.getPeerID().getName());
        jlabel.setFont(new Font("Verdana",2,20));
        add(jlabel);


        add(Box.createHorizontalStrut(50));

        //TODO: add number of players
        jlabel = new JLabel("Nº Players: ");
        jlabel.setFont(new Font("Verdana",2,20));
        add(jlabel);

    }

    public JLabel getEquation() {
        return equation;
    }

}
