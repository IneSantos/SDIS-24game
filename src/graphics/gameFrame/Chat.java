package graphics.gameFrame;

import connections.peer2peer.Peer;
import game.Game24;
import graphics.utilities.CustomTextField;
import org.json.JSONObject;
import utilities.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by inesa on 22/05/2016.
 */
public class Chat extends JPanel {

    private static final int PREF_W = 600;
    private static final int PREF_H = 400;

    private CustomTextField textField;
    private JButton sendButton;
    private JButton clearAll;
    private JButton backspace;
    private JTextArea textArea;

    private ArrayList<String> messages = new ArrayList<>();
    private Peer p;
    private Game24 game;

    private static Chat instance;

    public Chat(Peer peer, Game24 game) {
        setPreferredSize(new Dimension(PREF_W, PREF_H));
        // setBorder(BorderFactory.createLineBorder(Color.black));

        this.p = peer;
        this.game = game;

        textArea = new JTextArea(22, 35);
        textArea.setFont(new Font("Verdana", 1, 15));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        JScrollPane areaScroll = new JScrollPane(textArea);
        areaScroll.setPreferredSize(new Dimension(500, 300));
        areaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        //enterText
        textField = new CustomTextField("Enter text...", 30);
        textField.setFont(new Font("Verdana", 1, 15));

        sendButton = new JButton("SEND");

        clearAll = new JButton("CLEAR ALL");

        backspace = new JButton("BACKSPACE");

        buttonListener();

        add(areaScroll);
        add(textField);
        add(sendButton);
        add(clearAll);
        add(backspace);
        instance = this;
    }


    private void buttonListener() {
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        clearAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.setEquation("");
                GameFrame.getSouth().getEquation().setText("Equation: " + game.getEquation());
                GameFrame.getSouth().getEquation().paintImmediately(GameFrame.getSouth().getEquation().getVisibleRect());
                System.out.println("Clear all: " + game.getEquation());
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

    private void sendMessage() {
        messages.add(textField.getText());
        JSONObject msg = new JSONObject();
        msg.put(Constants.REQUEST, Constants.MESSAGE);
        msg.put(Constants.PEER_ID, Peer.getInstance().getPeerID().getJSON());
        msg.put(Constants.MESSAGE, textField.getText());
        Peer.getInstance().add2Responses(msg);
        textField.setTextCust("Enter text...");
        textField.setFont(new Font("Verdana", 1, 15));
    }



    public static Chat getInstance() {
        return instance;
    }

    public void add2Chat(String text) {
        textArea.append(text + "\n");
    }
}
