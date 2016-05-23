package graphics;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.html.CSS;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by inesa on 22/05/2016.
 */
public class JoinRoomPanel extends JPanel {

    private static final int PREF_W = 600;
    private static final int PREF_H = 500;
    JLabel jlabel;
    ArrayList<String> roomsName = new ArrayList<>();

    Document doc = new DefaultStyledDocument();

    public JoinRoomPanel() {

        roomsName.add("teste0000000000000000000000000000000000000000000000000000000000000000");
        roomsName.add("teste1000000000000000000000000000000000000000000000000000000000000000");
        roomsName.add("teste2000000000000000000000000000000000000000000000000000000000000000");
        roomsName.add("teste3000000000000000000000000000000000000000000000000000000000000000");
        roomsName.add("teste4000000000000000000000000000000000000000000000000000000000000000");

        setPreferredSize(new Dimension(PREF_W, PREF_H));
        setBorder(BorderFactory.createLineBorder(Color.blue));

        jlabel = new JLabel("Join Room: ");
        jlabel.setFont(new Font("Verdana", 1, 30));
        jlabel.setPreferredSize(new Dimension(400, 30));
        add(jlabel);

        add(Box.createVerticalStrut(80));

        //  JPanel panel = new JPanel(new FlowLayout());

        JTextPane pane = new JTextPane();
        pane.setEditable(false);

        JScrollPane areaScroll = new JScrollPane(pane);
        areaScroll.setPreferredSize(new Dimension(500, 300));
        areaScroll.setViewportBorder(new LineBorder(Color.RED));
        areaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //  areaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        JPanel jPanel = new JPanel();
        for (int i = roomsName.size() - 1; i >= 0; i--) {
            JLabel label = new JLabel();
            label.setText(roomsName.get(i));
            jPanel.add(label);
            jPanel.add(Box.createRigidArea(new Dimension(1, 5)));

           /*label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    label.setForeground(Color.BLUE);

                }

                @Override
                public void mouseExited(MouseEvent e) {
                    label.setForeground(Color.BLACK);

                }
            });*/
        }
        BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
        jPanel.setLayout(boxLayout);
        add(areaScroll);

      /*  JPanel jPanel = new JPanel();
        for (int i = 0; i < 40; i++) {
            JLabel jLabel = new JLabel("Label");
            jPanel.add(jLabel);
            jPanel.add(Box.createRigidArea(new Dimension(1, 5)));
        }
        BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
        jPanel.setLayout(boxLayout);
        JScrollPane jScrollPane = new JScrollPane(jPanel);
        add(jScrollPane);
*/

        JButton refreshButton = new JButton("REFRESH");
        refreshButton.setPreferredSize(new Dimension(200, 30));
        add(refreshButton);

        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Primiu o refresh");
            }
        });
        // }

    }

}
