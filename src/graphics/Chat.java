package graphics;

import connections.Peer;
import game.Game24;

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
    JButton clearAll;
    JButton backspace;
    JTextArea textArea;

    ArrayList<String> messages = new ArrayList<>();
    Peer p ;
    Game24 game;

    public Chat(Peer peer, Game24 game) {
        setPreferredSize(new Dimension(PREF_W, PREF_H));
       // setBorder(BorderFactory.createLineBorder(Color.black));

        this.p = peer;
        this.game = game;

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

        sendButton = new JButton("SEND");


        clearAll = new JButton("CLEAR ALL");

        backspace = new JButton("BACKSPACE");

        buttonListener();

        add(areaScroll);
        add(textField);
        add(sendButton);
        add(clearAll);
        add(backspace);
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

        clearAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.setEquation("");
                GameFrame.getSouth().getEquation().setText("Equation: " + game.getEquation());
                GameFrame.getSouth().getEquation().paintImmediately(GameFrame.getSouth().getEquation().getVisibleRect());
                System.out.println("Clear all: " +  game.getEquation());
            }
        });

        backspace.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newStr = game.getEquation().substring(0, game.getEquation().length() - 1);
                game.setEquation("");
                game.setEquation(newStr);
                GameFrame.getSouth().getEquation().setText("Equation: " + game.getEquation());
                GameFrame.getSouth().getEquation().paintImmediately(GameFrame.getSouth().getEquation().getVisibleRect());
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
