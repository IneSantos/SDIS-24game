package graphics;

import connections.Peer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by inesa on 22/05/2016.
 */
public class Chat extends JPanel {

    private static final int PREF_W = 600;
    private static final int PREF_H = 400;

    CustomTextField textField;
    JButton sendButton;
    JTextArea textArea;

    ArrayList<String> messages = new ArrayList<>();
    Peer p ;

    public Chat(Peer peer) {
        setPreferredSize(new Dimension(PREF_W, PREF_H));
       // setBorder(BorderFactory.createLineBorder(Color.black));

        this.p = peer;

        textArea = new JTextArea(22,35);
        textArea.setFont(new Font("Verdana",1,15));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        JScrollPane areaScroll = new JScrollPane(textArea);
        areaScroll.setPreferredSize(new Dimension(500, 300));
        areaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(areaScroll);


        //enterText
        textField = new CustomTextField("Enter text...", 30);
        textField.setFont(new Font("Verdana",1,15));
        textField.setEditable(true);
        keyBoardListener();

        textField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                textField.setText("");
                textField.normalText();
            }

            @Override
            public void focusLost(FocusEvent e) {
                textField.custText();
            }
        });

        sendButton = new JButton("SEND");
        buttonListener();

        add(areaScroll);
        add(textField);
        add(sendButton);
    }


    private void buttonListener(){
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                messages.add(textField.getText());
                textArea.append(p.getPeerID().getName() +  ": " + messages.get(messages.size()-1) + "\n");
                textField.setText("Enter text...");
                textField.custText();
            }
        });
    }

    public void keyBoardListener(){
        textField.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    messages.add(textField.getText());
                    textArea.append(p.getPeerID().getName() +  ": " + messages.get(messages.size()-1) + "\n");
                    textField.setText("Enter text...");
                    textField.custText();
                }
            }
        });
    }
}
