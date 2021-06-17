import java.awt.*;
import java.awt.geom.Rectangle2D;


public class Bird{
    public boolean spacePressed;
    private final double x;
    private double y;
    private final double gravity, yvel;
    private final Frame frame;


    public Bird(Frame frame) {
        this.spacePressed = false;
        this.frame = frame;
        this.x = frame.getWidth()/3.0 - 40;
        this.y = frame.getHeight()/3.0;
        this.gravity = 0.4;
        this.yvel = 0.003;
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

    // bird falling down
    public void drop() {
        if (y+60 > frame.getHeight()) {
            y = frame.getHeight() - 60;
        }
        else {
            y += gravity;
        }
    }

    // bird flying up
    public void fly(long t) {
        if (y > 0) {
            this.y -= (0.2 + yvel * t);
        }
    }

}
