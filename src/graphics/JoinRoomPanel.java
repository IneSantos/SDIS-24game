package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by inesa on 22/05/2016.
 */
public class JoinRoomPanel extends JPanel {

    private static final int PREF_W = 500;
    private static final int PREF_H = 400;
    JLabel jlabel;
    ArrayList<String> roomsName = new ArrayList<>();

    public JoinRoomPanel() {

        roomsName.add("teste00000000000000000000000000000");
        roomsName.add("teste111111111111111111111111111111111");
        roomsName.add("teste22222222222222222222222222");
        roomsName.add("teste3222222222");
        roomsName.add("teste433333333333333333");

        setPreferredSize(new Dimension(PREF_W, PREF_H));
        setBorder(BorderFactory.createLineBorder(Color.blue));

        jlabel  = new JLabel("Join Room: ");
        jlabel.setFont(new Font("Verdana",1,30));
        jlabel.setPreferredSize(new Dimension(400,30));
        add(jlabel);

        add(Box.createVerticalStrut(80));

        JTextPane textArea = new JTextPane();
        textArea.setPreferredSize(new Dimension(400,300));
        textArea.setEditable(false);

        JScrollPane areaScroll = new JScrollPane (textArea);
        areaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(areaScroll);

        for(int i= roomsName.size() ; i > 0; i--){
            JLabel roomName = new JLabel(roomsName.get(i-1));
            roomName.setFont(new Font("Verdana",4,20));
            roomName.setPreferredSize(new Dimension(100,20));
            roomName.setBorder(BorderFactory.createLineBorder(Color.blue));
            textArea.setCaretPosition(textArea.getDocument().getLength());
            textArea.insertComponent(roomName);
            roomName.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    roomName.setForeground(Color.BLUE);

                }

                @Override
                public void mouseExited(MouseEvent e) {
                    roomName.setForeground(Color.BLACK);

                }
            });
        }



    }

}
