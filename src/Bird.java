import java.awt.*;
import java.awt.geom.Rectangle2D;


public class Bird{
    public boolean spacePressed;
    private final double x;
    private double y;
    private final double GRAVITY, YVEL;
    private final int BIRD_WIDTH, BIRD_HEIGHT;
    private final Frame frame;


    public Bird(Frame frame) {
        this.spacePressed = false;
        this.frame = frame;
        this.x = frame.getWidth()/3.0 - 40;
        this.y = frame.getHeight()/3.0;
        this.GRAVITY = 0.2;
        this.YVEL = 0.005;
        this.BIRD_WIDTH = 40;
        this.BIRD_HEIGHT = 40;
    }

    public void changeSpacePressed(boolean b) {
        spacePressed = b;
    }

    public void drawBird(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.BLUE);
        Rectangle2D bird = new Rectangle2D.Double(x, y, BIRD_WIDTH, BIRD_HEIGHT);
        g2.fill(bird);
    }

    public int getBIRD_WIDTH() {
        return BIRD_WIDTH;
    }
    public int getBIRD_HEIGHT() {
        return BIRD_HEIGHT;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    // bird falling down
    public void drop(int t) {
        if (y+60 > frame.getHeight()) {
            y = frame.getHeight() - 60;
        }
        else {
            y += GRAVITY + 0.002*t;
        }
    }

    // bird flying up
    public void fly(long t) {
        if (y > 0) {
            this.y -= (0.2 + YVEL * t);
        }
    }

}
