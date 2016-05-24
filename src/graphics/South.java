package graphics;

import connections.Peer;

import javax.swing.*;
import javax.swing.text.Utilities;
import java.awt.*;

/**
 * Created by inesa on 24/05/2016.
 */
public class South extends JPanel {

    public South(Peer peer){

        JLabel jlabel = new JLabel("Room: " + peer.getDataBase().getCurrentRoom().getName());
        jlabel.setFont(new Font("Verdana",2,20));
        add(jlabel);

        add(Box.createHorizontalStrut(50));

        jlabel = new JLabel("Nickname: " + peer.getPeerID().getName());
        jlabel.setFont(new Font("Verdana",2,20));
        add(jlabel);

        add(Box.createHorizontalStrut(50));

        //TODO: add number of players
        jlabel = new JLabel("Nº Players: ");
        jlabel.setFont(new Font("Verdana",2,20));
        add(jlabel);

        add(Box.createHorizontalStrut(500));
    }
}
