import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Screen extends JPanel implements Runnable {
    private Frame frame;
    private int fps, score;
    private boolean running;
    int timeheld = 0;
    int timedrop = 0;

    private Bird bird;
    private Pipe pipe;

    ArrayList<Pipe> pipeslist;

    public Screen(Frame frame) {
        Thread thread = new Thread(this);
        this.frame = frame;
        this.frame.addKeyListener(new KeyHandler(this));

        bird = new Bird(frame);
        pipeslist = new ArrayList<Pipe>();
        pipe = new Pipe(frame);
        pipeslist.add(pipe);

        fps = 0;
        score = 0;

        thread.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // sets color to green and displays the fps
        g.setColor(Color.GREEN);
        g.drawString("fps: " + fps + "", 10, 20);
        g.drawString("score: " + score + "",10,40);


        // draws bird
        bird.drawBird(g);

        // draws pipes
        for (Pipe value : pipeslist) {
            value.drawPipes(g);
        }
    }

    public void run() {
        long lastFrame = System.currentTimeMillis();
        int frames = 0;

        running = true;

        // while game is running
        while (running) {
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
                timedrop++;
                bird.drop(timedrop);
                timeheld = 0;
            } else {
                timedrop = 0;
                timeheld++;
                bird.fly(timeheld);
            }
            // -----------------------------------------------------
            // moving and adding pipes -----------------------------
            // adds pipe once it reaches a certain point on screen,
            // and randomizes the gap position
            if (pipeslist.get(0).getX() == frame.getWidth() / 3.0 + 100) {
                Random random = new Random();
                Pipe pipe = new Pipe(frame);
                int randomNumber = random.nextInt((getHeight()-125)+1)+125;
                pipe.setY(randomNumber);
                pipeslist.add(pipe);
            }
            // if pipe reaches the end of the screen, remove from array
            if (pipeslist.get(0).getX() == -55) {
                pipeslist.remove(0);
            }
            // moves all pipes
            for (Pipe value : pipeslist) {
                value.move(0.5);
            }
            // adds +1 to score once bird passes a pipe
            if (pipeslist.get(0).getX() == frame.getWidth()/3.0 - 40) {
                score += 1;
            }
            // -----------------------------------------------------

            repaint();
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