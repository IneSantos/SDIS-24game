package graphics.initialFrame;

import connections.server.Client;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by inesa on 22/05/2016.
 */
public class JoinRoomPanel extends JPanel {

    private JList listbox;

    ArrayList<String> roomsName = new ArrayList<>();

    public static JoinRoomPanel instance;
    JButton refreshButton;

    public JoinRoomPanel() {

        int PREF_W = 600;
        int PREF_H = 500;
        setPreferredSize(new Dimension(PREF_W, PREF_H));
        //setBorder(BorderFactory.createLineBorder(Color.blue));

        JLabel jlabel = new JLabel("Join Room: ");
        jlabel.setFont(new Font("Verdana", 1, 30));
        jlabel.setPreferredSize(new Dimension(400, 30));
        add(jlabel);

        add(Box.createVerticalStrut(80));

        JTextPane pane = new JTextPane();
        pane.setEditable(false);

        // Create a new listbox control
        listbox = new JList( roomsName.toArray() );

        JScrollPane areaScroll = new JScrollPane(listbox);
        areaScroll.setPreferredSize(new Dimension(500, 300));
        areaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(areaScroll);

        refreshButton = new JButton("REFRESH");
        refreshButton.setPreferredSize(new Dimension(200, 30));
        add(refreshButton);
        buttonListener();

        listbox.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.println(e.toString());
             //  new GameFrame(peer);
            }
        });

                instance = this;
    }

    public static JoinRoomPanel getInstance() {
        return instance;
    }

    public JList getListbox() {
        return listbox;
    }

    private void buttonListener(){
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Client.getInstance().requestAvailableRooms();
                Client.getInstance().clearAvailableRooms();
            }
        });
    }

}
