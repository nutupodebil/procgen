import java.awt.*;
import java.util.Random;

public class Grid {
    Random rand = new Random();

    int winWidth;
    int winHeight;

    double cellSize = 10.0;

    int camx = 0;
    int camy = 0;

    ImprovedPerlinNoise perlin = new ImprovedPerlinNoise(239239239L);//rand.nextInt(1000000000, 2000000000));

    Grid(double size, int w, int h){
        this.cellSize = size;
        winWidth = w;
        winHeight = h;
    }

    int noisePoint(double defFreq, int octaves, double k, int x, int y){
        double value = 0;
        double amplitude = 1.0;
        double totalAmplitude = 0;
        double freq = defFreq;

        for (int i = 0; i < octaves; i++) {
            value += perlin.noise(x * freq, y * freq, freq) * amplitude;
            totalAmplitude += amplitude;
            amplitude *= 0.5;             // Уменьшаем амплитуду
            freq *= k;                    // Увеличиваем частоту (обычно 2.0)
        }
        value /= totalAmplitude;          // Нормализация

        value = (value+1)/2;

        if (value < 0.44) {
            return 0;
        }else if (value <= 0.5 && value >= 0.44){
            return 1;
        }else if (value > 0.5 && value < 0.53){
            return 2;
        }else if (value >= 0.53 && value < 0.62){
            return 3;
        } else {
            return 4;
        }
    }

    int randomPoint(){
        return rand.nextInt(0,5);
    }

    private Color getColorForValue(int value) {
        return switch (value) {
            case 0 -> new Color(0,0,139);
            case 1 -> Color.BLUE;
            case 2 -> new Color(246,215,176);
            case 3 -> Color.GREEN;
            case 4 -> new Color(0,100,0);
            default -> Color.BLUE;
        };
    }

    void setCam(int x, int y){
        camx = x;
        camy = y;
    }

    void setCellSize(double cs){
        cellSize = cs;
    }

    void paint(Graphics g){
        int cols = (int)(winWidth/cellSize);
        int rows = (int)(winHeight/cellSize);
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                //Color color = getColorForValue(randomPoint());  //random noise grid

                Color color = getColorForValue(noisePoint(0.05,10,1.3,x+camx,y+camy));  //noise grid
                g.setColor(color);

                g.fillRect((int)(x * cellSize),
                           (int)(y * cellSize),
                           (int)((x+1)*cellSize) - (int)(x*cellSize),
                           (int)((y+1)*cellSize) - (int)(y*cellSize));

//                g.setColor(Color.GRAY);  //only if cell size >= 5
//                g.drawRect((int)(x * cellSize),
//                           (int)(y * cellSize),
//                           (int)((x+1)*cellSize) - (int)(x*cellSize),
//                           (int)((y+1)*cellSize) - (int)(y*cellSize));
            }
        }
    }
}
