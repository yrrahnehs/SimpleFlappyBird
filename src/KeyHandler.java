import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private final Screen screen;

    public KeyHandler(Screen screen) {
        this.screen = screen;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == 27) {
            screen.stopGame();
        }
        if (key == 32) {
            screen.changePressed(true);
        }

    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == 32) {
            screen.changePressed(false);
        }
    }

    public void keyTyped(KeyEvent e) {

    }
}