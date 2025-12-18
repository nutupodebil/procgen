import java.awt.*;
import java.util.Random;

public class Grid {
    Random rand = new Random();
    int winWidth;
    int winHeight;
    int cols;
    int rows;
    int[][] grid;
    int cellSize = 10;

    Grid(int size, int cols, int rows, int w, int h){
        this.cols = cols;
        this.rows = rows;
        this.grid = new int[cols][rows];
        this.cellSize = size;
        winWidth = w;
        winHeight = h;
        initializeGrid(cols, rows);
    }

    void initializeGrid(int cols, int rows) {
        for (int x = 0; x != cols; x++) {
            for (int y = 0; y != rows; y++) {
                grid[x][y] = 0;
            }
        }
    }

    void randomGrid(){
        for (int x = 0; x != cols; x++) {
            for (int y = 0; y != rows; y++) {
                grid[x][y] = rand.nextInt(0,5);
            }
        }
    }

    void edgeCheckGrid(){
        for (int x = 0; x != cols; x++){
            for (int y = 0; y != rows; y++){
                if (x == 0 || y == 0 || x == cols-1 || y == rows-1){
                    grid[x][y] = 2;
                }else{
                    grid[x][y] = 0;
                }
            }
        }
    }

    private Color getColorForValue(int value) {
        return switch (value) {
            case 0 -> Color.WHITE;
            case 1 -> Color.BLACK;
            case 2 -> Color.RED;
            case 3 -> Color.BLUE;
            case 4 -> Color.GREEN;
            default -> Color.WHITE;
        };
    }

    void paint(Graphics g){
        if (grid == null) {
            return;
        }

        //edgeCheckGrid();
        randomGrid();

        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                Color color = getColorForValue(grid[x][y]);
                g.setColor(color);

                g.fillRect(x * cellSize + 15, y * cellSize + 40, cellSize, cellSize);

                g.setColor(Color.GRAY);
                g.drawRect(x * cellSize + 15, y * cellSize + 40, cellSize, cellSize);
            }
        }
    }
}
