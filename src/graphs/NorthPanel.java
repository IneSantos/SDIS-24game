package graphs;

import javax.swing.*;
import java.awt.*;

/**
 * Created by inesa on 19/05/2016.
 */
public class NorthPanel  extends JPanel {

    public NorthPanel() {
        add(Box.createVerticalStrut(100));
        JLabel jlabel = new JLabel("JOGO 24");
        jlabel.setFont(new Font("Verdana",1,50));
        add(jlabel);
    }
}
