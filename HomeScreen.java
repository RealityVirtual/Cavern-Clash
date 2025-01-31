import javax.swing.*;
import java.awt.*;

public class HomeScreen extends JPanel {
    public HomeScreen() {
        setOpaque(false); // Allows transparency so the game is visible behind it
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Fill the background with a semi-transparent black
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw the title
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        String title = "Cavern Clash";
        int titleWidth = g.getFontMetrics().stringWidth(title);
        g.drawString(title, (getWidth() - titleWidth) / 2, 100);

        // Draw the instructions
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        String[] instructions = {
            "Press Enter to start the game.",
            "You are trapped in a dark cave,",
            "press space to shoot a laser in the",
            "direction of your movement, and use WASD to move.",
            "Avoid the red enemies, they will turn orange when aggressive",
            "Your only chance at survival is reaching the blue diamond.",
            "Good luck!"
        };

        int lineHeight = g.getFontMetrics().getHeight();
        int y = 200; // Starting y-coordinate for the instructions

        for (String line : instructions) {
            int lineWidth = g.getFontMetrics().stringWidth(line);
            g.drawString(line, (getWidth() - lineWidth) / 2, y);
            y += lineHeight; // Move down to the next line
        }
    }
}
