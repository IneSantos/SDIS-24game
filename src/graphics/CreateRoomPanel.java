package graphics;

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
    JButton create;
    JTextField textField;
    JTextField textField1;
    String nickName = "";
    String roomName = "";

    public CreateRoomPanel() {
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
        textField1 = new CustomTextField("Pick a nickname...", 20);


        add(jlabel1);
        add(textField1);

        jlabel = new JLabel("Your nickname: ");
        jlabel.setFont(new Font("Verdana", 1, 15));

        //enterText
        textField = new CustomTextField("Pick a room name...", 20);

        add(jlabel);
        add(textField);

        add(Box.createVerticalStrut(80));

        JButton createButton = new JButton("CREATE ROOM");
        createButton.setPreferredSize(new Dimension(200, 30));
        add(createButton);

        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Primiu o create");
            }
        });

    }

}
