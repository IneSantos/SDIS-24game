package graphics.initialFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by inesa on 22/05/2016.
 */
public class CenterInitialPanel extends JPanel {

    private static final int PREF_W = 400;
    private static final int PREF_H = 400;

    public CenterInitialPanel() {
        setPreferredSize(new Dimension(PREF_W, PREF_H));
        //setBorder(BorderFactory.createLineBorder(Color.blue));

        CreateRoomPanel createRoom = new CreateRoomPanel();
        add(createRoom);


        add(Box.createHorizontalStrut(50));
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setPreferredSize(new Dimension(2,450));
        add(separator);

        JoinRoomPanel joinRoomPanel = new JoinRoomPanel();
        add(joinRoomPanel);



    }
}
