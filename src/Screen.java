import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

public class Screen extends JPanel implements Runnable, MouseListener, MouseMotionListener {
    private final Frame frame;
    private int fps, score;
    private boolean running;
    private int timeheld = 0;
    private int timedrop = 0;
    private int scene;
    private Color color1, color2;

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

        scene = 0;
        addMouseListener(this);
        addMouseMotionListener(this);
        thread.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g);
        g.setColor(Color.green);
        g.drawString("fps: " + fps + "", 10, 20);
        g.setColor(Color.black);
        Rectangle quitbutton = new Rectangle(325, 350, 250, 50);
        // Start menu
        if (scene == 0) {
            // Draws out title, start and quit buttons
            Font titlefont = new Font("arial", Font.BOLD, 50);
            g.setFont(titlefont);
            g.drawString("SIMPLE FLAPPY BIRD", frame.getWidth() / 5 + 10, 100);

            Font textfont = new Font("arial", Font.BOLD, 30);
            g.setFont(textfont);
            g.setColor(color1);
            Rectangle playbutton = new Rectangle(325, 250, 250, 50);
            g2d.draw(playbutton);
            g.drawString("PLAY", playbutton.x + 85, playbutton.y + 35);

            g.setColor(color2);
            g2d.draw(quitbutton);
            g.drawString("QUIT", quitbutton.x + 87, quitbutton.y + 35);
        }

        if (scene == 1) {
            // sets color to green and displays the fps
            g.setColor(Color.GREEN);
            g.drawString("score: " + score + "", 10, 40);

            // draws bird
            bird.drawBird(g);

            g2d.setColor(Color.GREEN);
            // draws pipes
            for (Pipe value : pipeslist) {
                value.drawPipes(g);
            }
        }

        if (scene == 2) {
            // Draws out title, start and quit buttons
            Font titlefont = new Font("arial", Font.BOLD, 50);
            g.setFont(titlefont);
            g.drawString("GAME  OVER", frame.getWidth()/3 - 10, 100);

            Font textfont = new Font("arial", Font.BOLD, 30);
            g.setFont(textfont);
            g.setColor(color1);
            Rectangle playagainbutton = new Rectangle(325, 250, 250, 50);
            g2d.draw(playagainbutton);
            g.drawString("PLAY AGAIN", playagainbutton.x + 35, playagainbutton.y + 35);

            g.setColor(color2);
            g2d.draw(quitbutton);
            g.drawString("QUIT", quitbutton.x + 87, quitbutton.y + 35);
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

            if (scene == 1) {
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

                // collision with the pipes results prompts a menu -
                // if bird y is not in gap
                if (frontPipe.getY() - frontPipe.getGAP_SIZE() > bird.getY() ^
                        frontPipe.getY() < bird.getY() + bird.getBIRD_HEIGHT()) {
                    // if bird x is in between gap
                    if (frontPipe.getX() + frontPipe.getPIPE_WIDTH() > bird.getX() + bird.getBIRD_WIDTH() &&
                            frontPipe.getX() < bird.getX()) {
                        scene = 2;
                    }
                    // if front of bird hits pipe, and is in between gap
                    if (frontPipe.getX() <= bird.getX() + bird.getBIRD_WIDTH() &&
                            bird.getX() + bird.getBIRD_WIDTH() <= frontPipe.getX() + frontPipe.getPIPE_WIDTH()) {
                        scene = 2;
                    }
                    // if back of bird is between gap
                    if (frontPipe.getX() <= bird.getX() &&
                            bird.getX() <= frontPipe.getX() + frontPipe.getPIPE_WIDTH()) {
                        scene = 2;
                    }
                }
                // -----------------------------------------------------
            }
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

    @Override
    public void mouseClicked(MouseEvent e) {
        if (scene == 0 || scene == 2) {
            // If mouse is clicked on play
            if (325 <= e.getX() && e.getX() <= 575 && 250 <= e.getY() && e.getY() <= 300) {
                scene = 1;
            }
            if (325 <= e.getX() && e.getX() <= 575 && 350 <= e.getY() && e.getY() <= 400) {
                System.exit(0);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (scene == 0 || scene == 2) {
            if (325 <= e.getX() && e.getX() <= 575 && 250 <= e.getY() && e.getY() <= 300) {
                color1 = new Color(10, 225, 10);
            } else if (325 <= e.getX() && e.getX() <= 575 && 350 <= e.getY() && e.getY() <= 400) {
                color2 = new Color(10, 225, 10);
            } else {
                color1 = Color.black;
                color2 = Color.black;
            }
        }
        repaint();
    }
}
