package graphics;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.html.CSS;
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

    Document doc = new DefaultStyledDocument();

    public JoinRoomPanel() {

        roomsName.add("teste0000000000000000000000000000000000000000000000000000000000000000");
        roomsName.add("teste10000000000000000000000000000000000000000000000000000000000000000");
        roomsName.add("teste20000000000000000000000000000000000000000000000000000000000000000");
        roomsName.add("teste30000000000000000000000000000000000000000000000000000000000000000");
        roomsName.add("teste40000000000000000000000000000000000000000000000000000000000000000");

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

        JScrollPane areaScroll = new JScrollPane(textArea);
        areaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(areaScroll);

        for(int i = roomsName.size() ; i > 0; i--){
            JLabel roomName = new JLabel("1",JLabel.CENTER);
            roomName.setText("<html>" + roomsName.get(i-1) + "</html>");
            roomName.setFont(new Font("Verdana",4,20));
            /*Dimension d = roomName.getPreferredSize();
            System.out.println("width " + d.width);
            System.out.println("height "  + d.height);
            roomName.setPreferredSize(new Dimension(d.width,d.height));*/
            roomName.setBorder(BorderFactory.createLineBorder(Color.blue));
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
