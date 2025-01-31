import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Enemy {
    private int x, y, width, height;
    private Color color;
    private boolean isActive;
    private int activationRange;
    private double speed;

    public Enemy(int x, int y, int width, int height, int activationRange) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.activationRange = activationRange;
        this.color = Color.RED; // Default enemy color
        this.isActive = false;  // Enemy is inactive by default

        // Random speed between 0.5 and 1.3
        Random random = new Random();
        this.speed = 0.5 + (1.3 - 0.5) * random.nextDouble();
    }

    public void draw(Graphics g) {
        g.setColor(isActive ? Color.ORANGE : color); // Change color when active
        g.fillRect(x, y, width, height);
    }

    public void checkActivation(Player player) {
        double distance = Math.sqrt(Math.pow(player.getX() - this.x, 2) + Math.pow(player.getY() - this.y, 2));
        isActive = distance <= activationRange;
    }

    public void followPlayer(Player player, ArrayList<Wall> walls) {
    if (isActive) {
        double dx = player.getX() - x;
        double dy = player.getY() - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        // Normalize the direction vector and scale by speed
        double moveX = (dx / distance) * speed;
        double moveY = (dy / distance) * speed;

        int newX = (int) (x + moveX);
        int newY = (int) (y + moveY);

        boolean movedX = false, movedY = false;

        // Try moving in the x direction
        if (!collidesWithWalls(newX, y, walls)) {
            x = newX; // Update x if no collision
            movedX = true;
        }

        // Try moving in the y direction
        if (!collidesWithWalls(x, newY, walls)) {
            y = newY; // Update y if no collision
            movedY = true;
        }

        // Adjust movement to slide along walls if partially blocked
        if (!movedX && !movedY) {
            // If both directions are blocked, enemy is stuck.
            // Optional: Add additional handling for stuck scenarios.
        }
    }
}


    private boolean collidesWithWalls(int newX, int newY, ArrayList<Wall> walls) {
        Rectangle newBounds = new Rectangle(newX, newY, width, height);
        for (Wall wall : walls) {
            if (newBounds.intersects(wall.getBounds())) {
                return true; // Collision detected
            }
        }
        return false; // No collision
    }

    public boolean isHit(Laser laser) {
        return this.getBounds().intersects(laser.getBounds());
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
