import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Timer;

public class Bird{
    public boolean spacePressed;
    private final double x;
    private double y, yvel;
    private final double gravity;
    private final Frame frame;


    public Bird(Frame frame) {
        this.spacePressed = false;
        this.frame = frame;
        this.x = frame.getWidth()/3.0;
        this.y = frame.getHeight()/3.0;
        this.gravity = 0.2;
        this.yvel = 0.0001;
    }

    public void changeSpacePressed(boolean b) {
        spacePressed = b;
    }

    public void drawBird(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.BLUE);
        Rectangle2D bird = new Rectangle2D.Double(x, y, 40, 40);
        g2.fill(bird);
    }

    public void drop() {
        if (y+60 > frame.getHeight()) {
            y = frame.getHeight() - 60;
        } else {
            y += gravity;
        }
    }

    public void fly(long t) {
        this.y -= (0.1 + yvel*t);
    }

}
