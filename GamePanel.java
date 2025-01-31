//This class handles the creation of elements onto the screen including enemies, walls, collisions, and interactions.


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

public class GamePanel extends JPanel {
    private static final int GRID_SIZE = 20; // Size of each grid cell
    private Player player;
    private Light light;
    private ArrayList<Laser> lasers;
    private ArrayList<Enemy> enemies;
    private ArrayList<Wall> walls; // Walls list
    private Rectangle diamond; // Blue diamond
    private int playerDx = 0; // Player's facing direction on the x-axis
    private int playerDy = -1; // Player's facing direction on the y-axis (default up)
    private long lastShotTime = 0; // Tracks the last time the laser was fired
    private static final int COOLDOWN_TIME = 1000; // Cooldown time in milliseconds

    public GamePanel() {
        player = new Player(100, 100, GRID_SIZE - 2, GRID_SIZE - 2); // Start at (100, 100)
        light = new Light(-112, -115, 22 * GRID_SIZE - 2, 22 * GRID_SIZE - 2); // Light initial position
        lasers = new ArrayList<>();
        enemies = new ArrayList<>();
        walls = Wall.getMaze(); // Initialize walls using static Wall.getMaze()

        diamond = new Rectangle(550, 550, GRID_SIZE, GRID_SIZE); // Initialize diamond position and size

        setFocusable(true);

        // Add some enemies for testing
        enemies.add(new Enemy(440, 200, GRID_SIZE - 2, GRID_SIZE - 2, 250));
        enemies.add(new Enemy(380, 300, GRID_SIZE - 2, GRID_SIZE - 2, 250));
        enemies.add(new Enemy(400, 40, GRID_SIZE - 2, GRID_SIZE - 2, 250));
        enemies.add(new Enemy(280, 300, GRID_SIZE - 2, GRID_SIZE - 2, 250));
        enemies.add(new Enemy(400, 500, GRID_SIZE - 2, GRID_SIZE - 2, 250));
        enemies.add(new Enemy(200, 500, GRID_SIZE - 2, GRID_SIZE - 2, 250));
        enemies.add(new Enemy(550, 40, GRID_SIZE - 2, GRID_SIZE - 2, 250));

        enemies.add(new Enemy(60, 180, GRID_SIZE - 2, GRID_SIZE - 2, 250));
        enemies.add(new Enemy(80, 380, GRID_SIZE - 2, GRID_SIZE - 2, 250));
        enemies.add(new Enemy(50, 430, GRID_SIZE - 2, GRID_SIZE - 2, 250));

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_W: // Move up
                    case KeyEvent.VK_UP:
                        if (!collidesWithWalls(player.getX(), player.getY() - GRID_SIZE)) {
                            player.move(0, -GRID_SIZE);
                            light.move(0, -GRID_SIZE);
                        }
                        playerDx = 0;
                        playerDy = -1;
                        break;
                    case KeyEvent.VK_S: // Move down
                    case KeyEvent.VK_DOWN:
                        if (!collidesWithWalls(player.getX(), player.getY() + GRID_SIZE)) {
                            player.move(0, GRID_SIZE);
                            light.move(0, GRID_SIZE);
                        }
                        playerDx = 0;
                        playerDy = 1;
                        break;
                    case KeyEvent.VK_A: // Move left
                    case KeyEvent.VK_LEFT:
                        if (!collidesWithWalls(player.getX() - GRID_SIZE, player.getY())) {
                            player.move(-GRID_SIZE, 0);
                            light.move(-GRID_SIZE, 0);
                        }
                        playerDx = -1;
                        playerDy = 0;
                        break;
                    case KeyEvent.VK_D: // Move right
                    case KeyEvent.VK_RIGHT:
                        if (!collidesWithWalls(player.getX() + GRID_SIZE, player.getY())) {
                            player.move(GRID_SIZE, 0);
                            light.move(GRID_SIZE, 0);
                        }
                        playerDx = 1;
                        playerDy = 0;
                        break;
                    case KeyEvent.VK_SPACE: // Fire laser
                        fireLaser();
                        break;
                }
                repaint();
            }
        });
    }

    private void fireLaser() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= COOLDOWN_TIME) {
            int laserWidth = GRID_SIZE / 4;
            int laserHeight = GRID_SIZE / 4;
            int laserX = player.getX() + player.getWidth() / 2 - laserWidth / 2;
            int laserY = player.getY() + player.getHeight() / 2 - laserHeight / 2;
            int laserSpeed = GRID_SIZE;
            lasers.add(new Laser(laserX, laserY, laserWidth, laserHeight, playerDx * laserSpeed, playerDy * laserSpeed));
            lastShotTime = currentTime;
        }
    }

    private boolean collidesWithWalls(int x, int y) {
        Rectangle newBounds = new Rectangle(x, y, player.getWidth(), player.getHeight());
        for (Wall wall : walls) {
            if (newBounds.intersects(wall.getBounds())) {
                return true; // Collision detected
            }
        }
        return false; // No collision
    }

    private void showEndScreen(String message, String buttonText) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this); // Get the current JFrame
        frame.setContentPane(new EndScreen(frame, message, buttonText)); // Set the EndScreen as the content pane
        frame.validate(); // Refresh the frame
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Fill the background with black
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw background grid
        g.setColor(Color.DARK_GRAY);
        for (int x = 0; x < getWidth(); x += GRID_SIZE) {
            for (int y = 0; y < getHeight(); y += GRID_SIZE) {
                g.drawRect(x, y, GRID_SIZE, GRID_SIZE);
            }
        }

        // Draw the light, player, walls, lasers, enemies, and diamond
        light.draw(g);
        player.draw(g);
        for (Wall wall : walls) {
            wall.draw(g);
        }
        for (Laser laser : lasers) {
            laser.draw(g);
        }
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }

        // Draw the diamond
        g.setColor(Color.BLUE);
        g.fillPolygon(new int[]{diamond.x, diamond.x + diamond.width / 2, diamond.x + diamond.width, diamond.x + diamond.width / 2},
                new int[]{diamond.y + diamond.height / 2, diamond.y, diamond.y + diamond.height / 2, diamond.y + diamond.height}, 4);
    }

    public void startGameLoop() {
        Timer timer = new Timer(16, e -> {
            // Update lasers
            Iterator<Laser> laserIt = lasers.iterator();
            while (laserIt.hasNext()) {
                Laser laser = laserIt.next();
                laser.move();

                boolean collidedWithWall = false;
                for (Wall wall : walls) {
                    if (laser.getBounds().intersects(wall.getBounds())) {
                        collidedWithWall = true;
                        break;
                    }
                }

                if (collidedWithWall || laser.isOutOfBounds(getWidth(), getHeight())) {
                    laserIt.remove();
                    continue;
                }
            }

            // Update enemies and check collisions with player
            Iterator<Enemy> enemyIt = enemies.iterator();
            while (enemyIt.hasNext()) {
                Enemy enemy = enemyIt.next();
                enemy.checkActivation(player);
                enemy.followPlayer(player, walls);

                // Collision detection: player and enemy
                if (enemy.getBounds().intersects(player.getBounds())) {
                    showEndScreen("Game Over.", "Play Again");
                    return;
                }

                // Collision detection: laser and enemy
                Rectangle enemyBounds = enemy.getBounds();
                for (Laser laser : lasers) {
                    if (enemyBounds.intersects(laser.getBounds())) {
                        enemyIt.remove();
                        laserIt.remove();
                        break;
                    }
                }
            }

            // Check for collision with diamond
            if (player.getBounds().intersects(diamond)) {
                showEndScreen("You Escaped!", "Play Again");
                return;
            }

            repaint();
        });
        timer.start();
    }
}
