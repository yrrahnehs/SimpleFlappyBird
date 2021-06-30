import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Screen extends JPanel implements Runnable {
    private final Frame frame;
    private int fps, score;
    private boolean running;
    private int timeheld = 0;
    private int timedrop = 0;

    private final Bird bird;

    private final ArrayList<Pipe> pipeslist;

    public Screen(Frame frame) {
        Thread thread = new Thread(this);
        this.frame = frame;
        this.frame.addKeyListener(new KeyHandler(this));

        bird = new Bird(frame);
        pipeslist = new ArrayList<Pipe>();
        Pipe pipe = new Pipe(frame);
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
        g.drawString("score: " + score + "", 10, 40);


        // draws bird
        bird.drawBird(g);

        g.setColor(Color.GREEN);
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
                int randomNumber = random.nextInt((getHeight() - 125) + 1) + 125;
                pipe.setY(randomNumber);
                pipeslist.add(pipe);
            }
            // if pipe reaches the end of the screen, remove from array
            if (pipeslist.get(0).getX() == -55) {
                pipeslist.remove(0);
            }
            // moves all pipes to the left of the screen
            for (Pipe value : pipeslist) {
                value.move(0.25);
            }
            // -----------------------------------------------------
            // +1 to score when bird goes through gap --------------
            Pipe frontPipe = pipeslist.get(0);
            if (frontPipe.getY() - frontPipe.getGAP_SIZE() < bird.getY() &&
                    bird.getY() + bird.getBIRD_HEIGHT() < frontPipe.getY()) {
                if (frontPipe.getX() + frontPipe.getPIPE_WIDTH() > bird.getX() + bird.getBIRD_WIDTH() &&
                        frontPipe.getX() < bird.getX()) {
                    if (bird.getX() + bird.getBIRD_WIDTH() == (frontPipe.getX() + frontPipe.getPIPE_WIDTH()) - 1) {
                        score++;
                    }
                }
            }

            // collision with the pipes results in exit application -
            // if bird y is not in gap
            if (frontPipe.getY() - frontPipe.getGAP_SIZE() > bird.getY() ^
                    frontPipe.getY() < bird.getY() + bird.getBIRD_HEIGHT()) {
                // if bird x is in between gap
                if (frontPipe.getX() + frontPipe.getPIPE_WIDTH() > bird.getX() + bird.getBIRD_WIDTH() &&
                        frontPipe.getX() < bird.getX()) {
                    System.exit(0);
                }
                // if front of bird hits pipe, and is in between gap
                if (frontPipe.getX() <= bird.getX() + bird.getBIRD_WIDTH() &&
                        bird.getX() + bird.getBIRD_WIDTH() <= frontPipe.getX() + frontPipe.getPIPE_WIDTH()) {
                    System.exit(0);
                }
                // if back of bird is between gap
                if (frontPipe.getX() <= bird.getX() &&
                        bird.getX() <= frontPipe.getX() + frontPipe.getPIPE_WIDTH()) {
                    System.exit(0);
                }
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