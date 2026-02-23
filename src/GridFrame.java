import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class GridFrame extends JFrame implements KeyEventDispatcher {
    int winWidth = 1280;
    int winHeight = 800;

    Grid grid;

    double cellSize = 10.0;

    double camx = 0.0;
    double camy = 0.0;

    double vel = 24.0/(cellSize*cellSize);

    boolean up = false, left = false, down = false, right = false;

    Font font = new Font("Arial", Font.BOLD, 18);

    public GridFrame(double size, int w, int h) {
        this.setTitle("Йо-хо-хо-хо!  xx");

        this.setSize(w,h);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.cellSize = size;

        winWidth = w;
        winHeight = h;

        grid = new Grid(cellSize, winWidth, winHeight);
    }

    @Override
    public void paint(Graphics g) {
        BufferStrategy bufferStrategy = getBufferStrategy();        // Обращаемся к стратегии буферизации
        if (bufferStrategy == null) {                               // Если она еще не создана
            createBufferStrategy(2);                      // то создаем ее
            bufferStrategy = getBufferStrategy();                   // и опять обращаемся к уже наверняка созданной стратегии
        }
        g = bufferStrategy.getDrawGraphics();                       // Достаем текущую графику (текущий буфер)
        g.clearRect(0, 0, getWidth(), getHeight());            // Очищаем наш холст (ведь там остался предыдущий кадр)

        if (up) camy -= vel;
        if (left) camx -= vel;
        if (down) camy += vel;
        if (right) camx += vel;

        //System.out.println(vel);    //some logs
        //System.out.println(up+" "+down+" "+left+" "+right);

        grid.setCam((int)camx, (int)camy);
        grid.setCellSize(cellSize);
        grid.paint(g);

        g.setColor(Color.BLACK);
        g.setFont(this.font);
        g.drawString("cellSize -- "+cellSize, winWidth-220, winHeight-100);
        g.drawString("vel      -- "+String.format("%.2f", vel), winWidth-220, winHeight-80);
        g.drawString("cam      -- x:"+(int)camx+" y:"+(int)camy, winWidth-220, winHeight-60);

        g.dispose();                // Освободить все временные ресурсы графики (после этого в нее уже нельзя рисовать)
        bufferStrategy.show();      // Сказать буферизирующей стратегии отрисовать новый буфер (т.е. поменять показываемый и обновляемый буферы местами)
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        //  87-w  65-a  83-s  68-d  73-i  79-o --- key codes
        if ((e.getID() == KeyEvent.KEY_PRESSED) && (e.getKeyCode() == KeyEvent.VK_W)){
            up = true;
        }
        if ((e.getID() == KeyEvent.KEY_RELEASED) && (e.getKeyCode() == KeyEvent.VK_W)){
            up = false;
        }

        if ((e.getID() == KeyEvent.KEY_PRESSED) && (e.getKeyCode() == KeyEvent.VK_A)){
            left = true;
        }
        if ((e.getID() == KeyEvent.KEY_RELEASED) && (e.getKeyCode() == KeyEvent.VK_A)){
            left = false;
        }

        if ((e.getID() == KeyEvent.KEY_PRESSED) && (e.getKeyCode() == KeyEvent.VK_S)){
            down = true;
        }
        if ((e.getID() == KeyEvent.KEY_RELEASED) && (e.getKeyCode() == KeyEvent.VK_S)){
            down = false;
        }

        if ((e.getID() == KeyEvent.KEY_PRESSED) && (e.getKeyCode() == KeyEvent.VK_D)){
            right = true;        }
        if ((e.getID() == KeyEvent.KEY_RELEASED) && (e.getKeyCode() == KeyEvent.VK_D)){
            right = false;
        }

        if ((e.getID() == KeyEvent.KEY_PRESSED) && (e.getKeyCode() == KeyEvent.VK_I)){
            cellSize = min(15.0, cellSize+0.25);
            vel = 24.0/(cellSize*cellSize);
        } else if ((e.getID() == KeyEvent.KEY_PRESSED) && (e.getKeyCode() == KeyEvent.VK_O)){
            cellSize = max(1.5, cellSize-0.25);
            vel = 24.0/(cellSize*cellSize);
        }

        return false;
    }
}