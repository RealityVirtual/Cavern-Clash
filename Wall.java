import java.awt.*;
import java.util.ArrayList;

public class Wall {
    private int x, y, width, height;
    private Color color;

    public Wall(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = Color.GRAY; // Default wall color
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    public static ArrayList<Wall> getMaze() {
        ArrayList<Wall> walls = new ArrayList<>();

        // Outer boundary walls
        for (int x = 0; x < 600; x += 20) {
            walls.add(new Wall(x, 0, 20, 20)); // Top edge
            walls.add(new Wall(x, 580, 20, 20)); // Bottom edge
        }
        for (int y = 0; y < 600; y += 20) {
            walls.add(new Wall(0, y, 20, 20)); // Left edge
            walls.add(new Wall(580, y, 20, 20)); // Right edge
        }

        // Maze structure
        // Horizontal and vertical walls creating the maze layout

        // Start zone
        walls.add(new Wall(20, 40, 120, 20)); // Horizontal start path
        walls.add(new Wall(140, 40, 20, 100)); // Vertical block
        walls.add(new Wall(20, 160, 100, 20)); // Horizontal path extension

        // Middle zone
        walls.add(new Wall(160, 200, 200, 20)); // Large horizontal path
        walls.add(new Wall(200, 220, 20, 120)); // Vertical block
        walls.add(new Wall(100, 360, 300, 20)); // Large horizontal open path
        walls.add(new Wall(400, 200, 20, 160)); // Vertical block

        // Open area with scattered walls
        for (int i = 240; i < 360; i += 40) {
            walls.add(new Wall(300, i, 20, 20)); // Vertical scattered blocks
        }
        for (int i = 100; i < 200; i += 40) {
            walls.add(new Wall(400, i, 20, 20)); // Horizontal scattered blocks
        }

        // Path to the bottom-right
        walls.add(new Wall(480, 400, 20, 100)); // Vertical block
        walls.add(new Wall(500, 480, 80, 20)); // Horizontal path leading to bottom-right

        // Block off dead ends for more maze-like structure
        walls.add(new Wall(240, 240, 20, 40));
        walls.add(new Wall(260, 320, 20, 40));

        // Adjustments to ensure accessibility

        // Bottom-left corner adjustments
        walls.add(new Wall(20, 480, 60, 20)); // Horizontal path
        walls.add(new Wall(80, 480, 20, 100)); // Vertical path
        walls.add(new Wall(40, 540, 60, 20)); // Horizontal block
        walls.add(new Wall(100, 560, 20, 20)); // Extend path to bottom left corner

        // Top-right corner adjustments
        walls.add(new Wall(480, 20, 20, 60)); // Vertical path
        walls.add(new Wall(500, 60, 60, 20)); // Horizontal path
        walls.add(new Wall(540, 80, 20, 40)); // Vertical block
        
        // Fill large open spaces
        walls.add(new Wall(300, 100, 20, 80)); // Vertical block to connect paths
        walls.add(new Wall(320, 100, 60, 20)); // Horizontal block

        // Ensure flow throughout maze
        walls.add(new Wall(200, 400, 20, 60)); // Vertical block
        walls.add(new Wall(220, 440, 100, 20)); // Horizontal block

        // Additional walls near bottom and middle-right gaps
        walls.add(new Wall(460, 500, 20, 40)); // Wall near bottom red squares
        walls.add(new Wall(420, 520, 40, 20)); // Close gap around enemies
        walls.add(new Wall(340, 300, 20, 60)); // Vertical wall in middle-right gap
        walls.add(new Wall(360, 340, 40, 20)); // Horizontal wall in middle-right gap


        //Walls at the bottom 
        walls.add(new Wall(150, 530, 200, 20)); // Wall near bottom red squares
        walls.add(new Wall(350, 530, 20, 120)); // Wall near bottom red squares


        //Mid Weft Walls
        
        walls.add(new Wall(60, 250, 60, 60)); // Wall near bottom red squares

        return walls;
    }
}
