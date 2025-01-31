import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndScreen extends JPanel {
    private JFrame frame;

    public EndScreen(JFrame frame, String message, String buttonText) {
        this.frame = frame;
        setLayout(new BorderLayout());

        // Display the message
        JLabel messageLabel = new JLabel(message, JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 40));
        messageLabel.setForeground(Color.RED);
        add(messageLabel, BorderLayout.CENTER);

        // Add a button to restart or return to the home screen
        JButton actionButton = new JButton(buttonText);
        actionButton.setFont(new Font("Arial", Font.PLAIN, 20));
        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
        add(actionButton, BorderLayout.SOUTH);
    }

    private void restartGame() {
        frame.dispose(); // Close the current frame
        Main.startGame(); // Restart the game
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
    }
}
