import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    public static void main(String[] args) {
        new Frame();
    }

    public Frame() {
        new JFrame();

        this.setTitle("Simple Flappy Bird");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(900, 700);
        this.setResizable(false);

        Screen screen = new Screen(this);
        this.add(screen);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
