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
    JTextField textField;
    JTextField textField1;
    JButton createButton;
    String nickName = "";
    String roomName = "";

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
        textField1 = new CustomTextField("Name a room...", 20);


        add(jlabel1);
        add(textField1);

        jlabel = new JLabel("Your nickname: ");
        jlabel.setFont(new Font("Verdana", 1, 15));

        //enterText
        textField = new CustomTextField("insert your nickname...", 20);

        keyBoardListener();

        add(jlabel);
        add(textField);

        add(Box.createVerticalStrut(80));

        createButton = new JButton("CREATE ROOM");
        createButton.setPreferredSize(new Dimension(200, 30));
        add(createButton);
        buttonListener();


    }


    private void keyBoardListener(){
        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                nickName += e.getKeyChar();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        textField1.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                roomName += e.getKeyChar();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void buttonListener(){
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Primiu o create");

                System.out.println("nick: " + nickName);

                System.out.println("room: " + roomName);

                peer.setPeerUsername(nickName);
                peer.createRoom(roomName);

                if(!nickName.equals("") && !roomName.equals("")) {
                    InitialFrame.getFrames()[0].setVisible(false);
                    new GameFrame(peer);
                }
                else {
                    JOptionPane.showMessageDialog(InitialFrame.getFrames()[0],
                            "Missing Room Name or Nickname",
                            "Inane error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

}
