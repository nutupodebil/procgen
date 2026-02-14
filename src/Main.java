import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int winWidth = 1280;
        int winHeight = 800;

        double cellSize = 10.0;

        GridFrame frame = new GridFrame(cellSize, winWidth, winHeight);

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(frame);

        while (true){
            frame.repaint();
            Thread.sleep(5);
        }
    }
}