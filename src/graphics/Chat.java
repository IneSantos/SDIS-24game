package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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

    public Chat() {
        setPreferredSize(new Dimension(PREF_W, PREF_H));
       // setBorder(BorderFactory.createLineBorder(Color.black));

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

        textField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                //Your code here
                System.out.println("Gained focus");
                textField.setText("");
                textField.normalText();
            }

            @Override
            public void focusLost(FocusEvent e) {
                //Your code here
                System.out.println("lost focus");
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
                textArea.append(messages.get(messages.size()-1) + "\n");
                textField.setText("Enter text...");
                textField.custText();
                System.out.println(messages);
            }
        });
    }
}
