package graphics;

import connections.Peer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by inesa on 22/05/2016.
 */
public class CreateRoomPanel extends JPanel {

    private static final int PREF_W = 400;
    private static final int PREF_H = 400;
    JLabel jlabel;
    JLabel jlabel1;
    Peer peer;
    CustomTextField textFieldNick;
    CustomTextField textFieldRoom;
    JButton createButton;

    public CreateRoomPanel(Peer peer) {
        this.peer = peer;
        setPreferredSize(new Dimension(PREF_W, PREF_H));
        //setBorder(BorderFactory.createLineBorder(Color.blue));
        jlabel = new JLabel("Create Room: ");
        jlabel.setFont(new Font("Verdana", 1, 30));
        jlabel.setPreferredSize(new Dimension(300, 30));
        add(jlabel);

        add(Box.createVerticalStrut(80));

        jlabel1 = new JLabel("Room name: ");
        jlabel1.setFont(new Font("Verdana", 1, 15));
        jlabel1.setPreferredSize(new Dimension(130, 30));


        //enterText
        textFieldRoom = new CustomTextField("Name a room...", 20);


        add(jlabel1);
        add(textFieldRoom);

        jlabel = new JLabel("Your nickname: ");
        jlabel.setFont(new Font("Verdana", 1, 15));

        //enterText
        textFieldNick = new CustomTextField("insert your nickname...", 20);


        add(jlabel);
        add(textFieldNick);

        add(Box.createVerticalStrut(80));

        createButton = new JButton("CREATE ROOM");
        createButton.setPreferredSize(new Dimension(200, 30));
        add(createButton);
        buttonListener();


    }


    private void buttonListener() {
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nickName = textFieldNick.getText();
                String roomName = textFieldRoom.getText();
                peer.createRoom(roomName, nickName);

                if (nickName.equals("") || roomName.equals("")) {
                    JOptionPane.showMessageDialog(InitialFrame.getFrames()[0],
                            "Missing Room Name or Nickname",
                            "Missing parameter error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    InitialFrame.getFrames()[0].setVisible(false);
                    new GameFrame(peer);
                }
            }
        });
    }

}
