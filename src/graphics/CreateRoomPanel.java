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
        textField1 = new JTextField("Enter name...", 20);
        textField1.setFont(new Font("Verdana", 4, 10));


        add(jlabel1);
        add(textField1);

        jlabel = new JLabel("Your nickname: ");
        jlabel.setFont(new Font("Verdana", 1, 15));

        //enterText
        textField = new JTextField("Enter nickname...", 20);
        textField.setFont(new Font("Verdana", 4, 10));

        add(jlabel);
        add(textField);

        mouseListenners();
        keyboardListenners();
    }

    public void mouseListenners() {

        textField1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textField1.setText("");
                if (textField.getText().equals(""))
                    textField.setText("Enter nickname...");
            }
        });

        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textField.setText("");
                if (textField1.getText().equals(""))
                    textField1.setText("Enter Name..");
            }
        });
    }


    public void keyboardListenners() {

        textField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                char c = e.getKeyChar();
                roomName += c;
                System.out.println(roomName);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
            }
        });


        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                char c = e.getKeyChar();
                nickName += c;
                System.out.println(nickName);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
            }
        });
    }
}
