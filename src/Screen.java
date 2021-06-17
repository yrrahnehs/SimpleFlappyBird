import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Screen extends JPanel implements Runnable {
    private Frame frame;
    private int fps = 0;
    private boolean running;
    int timeheld = 0;

    private Bird bird;
    private Pipes pipes;

    ArrayList<Pipes> pipeslist;

    public Screen(Frame frame) {
        Thread thread = new Thread(this);
        this.frame = frame;
        this.frame.addKeyListener(new KeyHandler(this));

        bird = new Bird(frame);
        pipeslist = new ArrayList<Pipes>();
        pipes = new Pipes(frame);
        pipeslist.add(pipes);

        thread.start();
    }


    public void paintComponent(Graphics g) {
        // sets color to green and displays the fps
        g.setColor(Color.GREEN);
        g.drawString("fps: " + fps + "", 10, 20);

        // draws bird
        bird.drawBird(g);

        // draws pipes
        for (Pipes value : pipeslist) {
            value.drawPipes(g);
        }
    }

    public void run() {
        long lastFrame = System.currentTimeMillis();
        int frames = 0;

        running = true;

        // while game is running
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
            // if space not pressed, bird falls --------------------
            if (!bird.spacePressed) {
                bird.drop();
                timeheld = 0;
            } else {
                timeheld++;
                bird.fly(timeheld);
            }
            // -----------------------------------------------------
            // moving pipes to the left ----------------------------

            pipes.move(0.1);

            // -----------------------------------------------------

        }
        System.exit(1);
    }

    // quits game
    public void stopGame() {
        running = false;
    }

    // changes the boolean value of space pressed in Bird
    public void changePressed(boolean b) {
        bird.changeSpacePressed(b);
    }
}