import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class GridFrame extends JFrame implements KeyEventDispatcher, MouseListener, MouseMotionListener, MouseWheelListener {
    int winWidth = 1280;
    int winHeight = 800;

    Grid grid;

    double cellSize = 10.0;

    double camx = 0.0;
    double camy = 0.0;

    int mouseX = 0;
    int mouseY = 0;

    double vel = 24.0/(cellSize*cellSize);

    boolean up = false, left = false, down = false, right = false;

    boolean logFlag = false;

    Font font = new Font("Arial", Font.BOLD, 20);
    FontMetrics metrics = getFontMetrics(font);
    int textXOffset = -160;

    public GridFrame(double size, int w, int h) {
        this.setTitle("Йо-хо-хо-хо!  xx");

        this.setSize(w,h);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.cellSize = size;

        winWidth = w;
        winHeight = h;

        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);

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
        //System.out.println(mouseX+" "+mouseY);

        grid.setCam((int)camx, (int)camy);
        grid.setMouse(mouseX, mouseY);
        grid.setCellSize(cellSize);
        grid.setLogFlag(logFlag);
        grid.paint(g);

        long rendTilesNum = grid.getRendTilesNum();
        long rendCityNum = grid.getRendCitiesNum();

        String camxinfo = "cam X: "+(int)camx;
        String camyinfo = "cam Y: "+(int)camy;
        String citiesinfo = "cities num: "+rendCityNum;

        textXOffset = -160; //-max(160, max(metrics.stringWidth(citiesinfo),max(metrics.stringWidth(camxinfo), metrics.stringWidth(camyinfo))));

        g.setColor(Color.BLACK);
        g.setFont(this.font);
        g.drawString(camxinfo, winWidth+textXOffset, winHeight-140);
        g.drawString(camyinfo, winWidth+textXOffset, winHeight-120);
        g.drawString("cell size: "+cellSize, winWidth+textXOffset, winHeight-100);
        g.drawString("velocity: "+String.format("%.2f", vel), winWidth+textXOffset, winHeight-80);
        g.drawString("tiles num: "+rendTilesNum, winWidth+textXOffset, winHeight-60);
        g.drawString(citiesinfo, winWidth+textXOffset, winHeight-40);

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

        if ((e.getID() == KeyEvent.KEY_PRESSED) && (e.getKeyCode() == KeyEvent.VK_L)){
            logFlag = !logFlag;
        }

        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0){
            cellSize = min(15.0, cellSize+0.25);
            vel = 24.0/(cellSize*cellSize);
        } else {
            cellSize = max(1.5, cellSize-0.25);
            vel = 24.0/(cellSize*cellSize);
        }
    }
}