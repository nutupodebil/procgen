import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class GridFrame extends JFrame {
    int winWidth = 1280;
    int winHeight = 800;

    Grid grid;
    int cols;
    int rows;
    int cellSize;

    public GridFrame(int size, int cols, int rows, int w, int h) {
        this.setTitle("Клетчатое поле");
        this.setSize(w+32,h+60);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.cols = cols;
        this.rows = rows;
        this.cellSize = size;

        winWidth = w;
        winHeight = h;

        grid = new Grid(cellSize, cols, rows, winWidth, winHeight);
    }

    @Override
    public void paint(Graphics g) {
        BufferStrategy bufferStrategy = getBufferStrategy();        // Обращаемся к стратегии буферизации
        if (bufferStrategy == null) {                               // Если она еще не создана
            createBufferStrategy(2);                                // то создаем ее
            bufferStrategy = getBufferStrategy();                   // и опять обращаемся к уже наверняка созданной стратегии
        }
        g = bufferStrategy.getDrawGraphics();                       // Достаем текущую графику (текущий буфер)
        g.clearRect(0, 0, getWidth(), getHeight());                 // Очищаем наш холст (ведь там остался предыдущий кадр)

        grid.paint(g);

        g.dispose();                // Освободить все временные ресурсы графики (после этого в нее уже нельзя рисовать)
        bufferStrategy.show();      // Сказать буферизирующей стратегии отрисовать новый буфер (т.е. поменять показываемый и обновляемый буферы местами)
    }
}
