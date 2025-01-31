import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        startGame();
    }

    public static void startGame() {
        // Create the game frame
        JFrame frame = new JFrame("Grid Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setResizable(false);

        // Create the GamePanel and HomeScreen
        GamePanel gamePanel = new GamePanel();
        HomeScreen homeScreen = new HomeScreen();

        // Use a JLayeredPane to manage layers
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 600, 600);
        frame.add(layeredPane);

        // Add the game panel at the bottom layer
        gamePanel.setBounds(0, 0, 600, 600);
        layeredPane.add(gamePanel, Integer.valueOf(0));

        // Add the home screen on top of the game panel
        homeScreen.setBounds(0, 0, 600, 600);
        layeredPane.add(homeScreen, Integer.valueOf(1)); 

        frame.setVisible(true);

        // Start the game loop immediately
        gamePanel.startGameLoop();

        // Use a separate thread to wait for input to avoid blocking
        new Thread(() -> {
            Scanner input = new Scanner(System.in);
            System.out.println("Type anything to start the game:");
            if (input.hasNextLine()) {
                // Hide the HomeScreen
                SwingUtilities.invokeLater(() -> layeredPane.remove(homeScreen));
                frame.repaint(); // Refresh the frame
            }
        }).start();
    }
}
