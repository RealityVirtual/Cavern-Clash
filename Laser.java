import java.awt.*;

public class Laser {
    private int x, y;
    private int width, height;
    private int dx, dy; // Direction of the laser
    private boolean active;

    public Laser(int x, int y, int width, int height, int dx, int dy) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dx = dx;
        this.dy = dy;
        this.active = true; // Laser is active when created
    }

    public void move() {
        if (active) {
            x += dx/5;
            y += dy/5;
        }
    }

    public void draw(Graphics g) {
        if (active) {
            g.setColor(Color.YELLOW); // Laser color
            g.fillRect(x, y, width, height);
        }
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }

    // Check if the laser is out of bounds
    public boolean isOutOfBounds(int panelWidth, int panelHeight) {
        return x < 0 || y < 0 || x > panelWidth || y > panelHeight;
    }
    
    public Rectangle getBounds() {
    return new Rectangle(x, y, width, height);
}

}
