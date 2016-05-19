import java.awt.*;

import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        super("Jogo 24");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        NumbersPanel numbersPanel = new NumbersPanel();
        setLayout(new BorderLayout());
        getContentPane().add(numbersPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public static void main(String[] args) {
        new GameFrame();
    }

}

