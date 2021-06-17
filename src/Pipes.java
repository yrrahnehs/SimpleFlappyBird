import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Pipes {
    Frame frame;
    private double x, y;

    public Pipes(Frame frame) {
        this.frame = frame;
        x = frame.getWidth() * 2 / 3.0;
        y = frame.getHeight() / 3.0;
    }

    public void drawPipes(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.GREEN);
        Rectangle2D toppipe = new Rectangle2D.Double(x, 0, 55, 100);
        Rectangle2D bottompipe = new Rectangle2D.Double(x, y + 40, 55, frame.getHeight());
        g2.fill(toppipe);
        g2.fill(bottompipe);
    }

    public void move(double pace) {
        this.x -= pace;
    }
}