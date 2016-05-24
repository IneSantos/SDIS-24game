package graphics;

import connections.Peer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.html.CSS;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by inesa on 22/05/2016.
 */
public class JoinRoomPanel extends JPanel {

    private JList listbox;

    ArrayList<String> roomsName = new ArrayList<>();

    Document doc = new DefaultStyledDocument();
    public static JoinRoomPanel instance;

    public JoinRoomPanel() {

        int PREF_W = 600;
        int PREF_H = 500;
        setPreferredSize(new Dimension(PREF_W, PREF_H));
        setBorder(BorderFactory.createLineBorder(Color.blue));

        JLabel jlabel = new JLabel("Join Room: ");
        jlabel.setFont(new Font("Verdana", 1, 30));
        jlabel.setPreferredSize(new Dimension(400, 30));
        add(jlabel);

        add(Box.createVerticalStrut(80));

        JTextPane pane = new JTextPane();
        pane.setEditable(false);

        for (int i = 0; i < 30; i++) {
            roomsName.add("Sala " + i);
        }

        // Create a new listbox control
        listbox = new JList( roomsName.toArray() );

        JScrollPane areaScroll = new JScrollPane(listbox);
        areaScroll.setPreferredSize(new Dimension(500, 300));
        areaScroll.setViewportBorder(new LineBorder(Color.RED));
        areaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(areaScroll);

        JButton refreshButton = new JButton("REFRESH");
        refreshButton.setPreferredSize(new Dimension(200, 30));
        add(refreshButton);

        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Peer.getInstance().requestAvailableRooms();
            }
        });
        // }
        instance = this;
    }

    public static JoinRoomPanel getInstance() {
        return instance;
    }
    public JList getListbox() {
        return listbox;
    }

}
