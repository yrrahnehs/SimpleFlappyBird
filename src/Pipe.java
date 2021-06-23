import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Pipe {
    Frame frame;
    private double x, y;

    public Pipe(Frame frame) {
        this.frame = frame;
        x = frame.getWidth();
        y = frame.getHeight()*1.5 / 3.0;
    }

    public void drawPipes(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.GREEN);
        int PIPE_WIDTH = 55;
        Rectangle2D toppipe = new Rectangle2D.Double(x, 0, PIPE_WIDTH, y-125);
        Rectangle2D bottompipe = new Rectangle2D.Double(x, y, PIPE_WIDTH, frame.getHeight());
        g2.fill(toppipe);
        g2.fill(bottompipe);
    }

    public double getX() {
        return this.x;
    }

    public void setY(double newY) {
        this.y = newY;
    }

    public void move(double pace) {
        this.x -= pace;
    }
}