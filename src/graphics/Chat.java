package graphics;

import javax.swing.*;
import java.awt.*;

/**
 * Created by inesa on 22/05/2016.
 */
public class Chat extends JPanel {

    private static final int PREF_W = 600;
    private static final int PREF_H = 400;

    JTextField textField;

    public Chat() {
        setPreferredSize(new Dimension(PREF_W, PREF_H));
       // setBorder(BorderFactory.createLineBorder(Color.black));

        JTextArea textArea = new JTextArea(22,35);
        textArea.setFont(new Font("Verdana",1,15));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        JScrollPane areaScroll = new JScrollPane (textArea);
        areaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        //enterText
        textField = new JTextField("Enter text...", 35);
        textField.setFont(new Font("Verdana",1,15));
        textField.setEditable(true);

        add(areaScroll);
        add(textField);
    }
}
