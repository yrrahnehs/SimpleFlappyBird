import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Pipe {
    Frame frame;
    private double x, y;
    private final int PIPE_WIDTH, GAP_SIZE;

    public Pipe(Frame frame) {
        this.frame = frame;
        x = frame.getWidth();
        y = frame.getHeight()*1.5 / 3.0;
        PIPE_WIDTH = 55;
        GAP_SIZE = 125;
    }

    public void drawPipes(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        Rectangle2D toppipe = new Rectangle2D.Double(x, 0, PIPE_WIDTH, y-GAP_SIZE);
        Rectangle2D bottompipe = new Rectangle2D.Double(x, y, PIPE_WIDTH, frame.getHeight());
        g2.fill(toppipe);
        g2.fill(bottompipe);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public int getPIPE_WIDTH() {
        return PIPE_WIDTH;
    }

    public int getGAP_SIZE() {
        return GAP_SIZE;
    }

    public void setY(double newY) {
        this.y = newY;
    }

    public void move(double pace) {
        this.x -= pace;
    }
}