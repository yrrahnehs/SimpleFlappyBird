import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Screen extends JPanel implements Runnable {
    private Frame frame;
    private int fps = 0;
    private boolean running;

    private Bird bird;

    Timer timer;

    public Screen(Frame frame) {
        Thread thread = new Thread(this);
        this.frame = frame;
        this.frame.addKeyListener(new KeyHandler(this));

        bird = new Bird(frame);

        thread.start();
    }


    public void paintComponent(Graphics g) {
        // sets color to green and displays the fps
        g.setColor(Color.GREEN);
        g.drawString("fps: " + fps + "", 10, 20);

        bird.drawBird(g);
    }

    public void run() {

        long lastFrame = System.currentTimeMillis();

        int frames = 0;

        running = true;

        while(running) {
            repaint();
            frames++;
            // displays fps ----------------------------------------
            if (System.currentTimeMillis() - 1000 >= lastFrame) {
                fps = frames;
                frames = 0;
                lastFrame = System.currentTimeMillis();
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // -----------------------------------------------------

            if (!bird.spacePressed) {
                bird.drop();
            } else {
                bird.fly();
            }
            repaint();
        }

        System.exit(1);
    }

    public void stopGame() {
        running = false;
    }

    public void changePressed(boolean b) {
        bird.changeSpacePressed(b);
    }
}