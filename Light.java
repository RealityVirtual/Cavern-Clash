import java.awt.*;

public class Light {
    private int x, y;
    private int width, height;
    private Color centerColor, edgeColor;

    public Light(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.centerColor = new Color(252, 255, 141, 178); // Light yellow color at 70% opacity
        this.edgeColor = new Color(0, 0, 0, 0);  // Fully transparent black

    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int centerX = x + width / 2;
        int centerY = y + height / 2;
        float radius = Math.min(width, height) / 2.0f;

        // Create a radial gradient
        RadialGradientPaint gradient = new RadialGradientPaint(
            new Point(centerX, centerY), // Center of the gradient
            radius,                      // Radius of the gradient
            new float[]{0.0f, 1.0f},     // Fractions
            new Color[]{centerColor, edgeColor} // Colors at the fractions
        );

        g2d.setPaint(gradient);
        g2d.fillOval(x, y, width, height); // Draw the gradient-filled oval
    }
}
