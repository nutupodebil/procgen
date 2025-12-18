import javax.swing.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int winWidth = 1280;
        int winHeight = 800;
        int cols = 128;
        int rows = 80;
        int cellSize = 10;

        GridFrame frame = new GridFrame(cellSize, cols, rows, winWidth, winHeight);

        while (true){
            frame.repaint();
            Thread.sleep(500);
        }
    }
}