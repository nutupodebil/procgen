import java.awt.*;
import java.util.*;

import static java.lang.Math.abs;

public class Grid {
    Random rand = new Random();

    int winWidth;
    int winHeight;

    final double DEF_FREQ = 0.05;
    final int    OCTAVES  = 8;
    final double koef     = 1.25;

    double cellSize = 10.0;

    int camx = 0;
    int camy = 0;

    final int TILE_SIZE = 500;

    CityGen ctGen;

    final int CITY_NUM = 2;
    final int CITY_SIZE = 100;
    final long CITY_DIST = 250; //400;

    HashMap<Point, int[][]> tiles = new HashMap<Point, int[][]>();
    HashMap<Point, Point> cities = new HashMap<Point, Point>();

    ImprovedPerlinNoise perlin = new ImprovedPerlinNoise(239239239L); //239239239L

    Grid(double size, int w, int h){
        this.cellSize = size;
        winWidth = w;
        winHeight = h;
        ctGen = new CityGen(TILE_SIZE);
    }

    void setCam(int x, int y){
        camx = x;
        camy = y;
    }

    void setCellSize(double cs){
        cellSize = cs;
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
        }else if (value > 0.5 && value < 0.52){
            return 2;
        }else if (value >= 0.52 && value < 0.62){
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

            case 5 -> new Color(129, 84, 56); //Color(186, 140, 99);

            case 239 -> Color.RED;

            default -> Color.BLUE;
        };
    }

    void saveTile(Point key){
        tiles.put(key, new int[TILE_SIZE][TILE_SIZE]);

        int[][] tile = tiles.get(key);

        for (int i = 0; i != TILE_SIZE; ++i){
            for (int j = 0; j != TILE_SIZE; ++j){
                tile[i][j] = noisePoint(DEF_FREQ,OCTAVES,koef,key.x+i,key.y+j);
            }
        }

        findCity(tile, key, CITY_SIZE);
    }

    boolean checkCityDist(int x, int y){
        if (CITY_DIST == 0L) return true;
        Set <Point> keys = cities.keySet();
        for (Point p : keys){
            long dx = abs((long)cities.get(p).x - (long)x);
            long dy = abs((long)cities.get(p).y - (long)y);
            if ((dx*dx + dy*dy) < CITY_DIST*CITY_DIST) return false;
        }
        return true;
    }

    void findCity(int[][] tile, Point origin, int CITY_SIZE){
        int[][] prefix = new int[TILE_SIZE + 1][TILE_SIZE + 1];
        for (int i = 0; i != TILE_SIZE; i++) {
            for (int j = 0; j != TILE_SIZE; j++) {
                int val = (tile[i][j] == 3) ? 1 : 0;
                prefix[i + 1][j + 1] = prefix[i][j + 1] + prefix[i + 1][j] - prefix[i][j] + val;
            }
        }

        int target = CITY_SIZE * CITY_SIZE;
        int num = 0;

        for (int i = 0; i <= (TILE_SIZE - CITY_SIZE); i++) {
            for (int j = 0; j <= (TILE_SIZE - CITY_SIZE); j++) {
                int sum = prefix[i + CITY_SIZE][j + CITY_SIZE] - prefix[i][j + CITY_SIZE] - prefix[i + CITY_SIZE][j] + prefix[i][j];

                Point city = new Point(origin.x + i, origin.y + j);
                if (sum == target && checkCityDist(city.x, city.y) && num < CITY_NUM) {
                    cities.put(origin, city);

                    checkCity(tile, i, j);
                    ctGen.addCity(tile, origin, new Point(i, j), CITY_SIZE);

                    num += 1;
                    if (num == 2) return;
                }
            }
        }
    }

    void checkCity(int[][] tile, int x, int y){
        for (int i = x; i != x+CITY_SIZE; ++i){
            tile[i][y] = 239;
        }
        for (int i = x; i != x+CITY_SIZE; ++i){
            tile[i][y+CITY_SIZE-1] = 239;
        }
        for (int i = y; i != y+CITY_SIZE; ++i){
            tile[x][i] = 239;
        }
        for (int i = y; i != y+CITY_SIZE; ++i){
            tile[x+CITY_SIZE-1][i] = 239;
        }
    }

    void paint(Graphics g){
        int cols = (int)((winWidth)/cellSize);
        int rows = (int)((winHeight)/cellSize);

        cols += cols%2;
        rows += rows%2;

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                //Color color = getColorForValue(randomPoint());  //random noise grid

                Color color = Color.RED;
                int x = i + camx-cols/2;
                int y = j + camy-rows/2;

                Point key = new Point(x - ((x % TILE_SIZE + TILE_SIZE) % TILE_SIZE),
                                      y - ((y % TILE_SIZE + TILE_SIZE) % TILE_SIZE));

                if (!tiles.containsKey(key)) {
                    saveTile(key);
                }

                color = getColorForValue(tiles.get(key)[x - key.x][y - key.y]);

//                if (x % TILE_SIZE == 0 || y % TILE_SIZE == 0){  // check tiles
//                    color = Color.GRAY;
//                }

                g.setColor(color);

                g.fillRect((int)(i * cellSize),
                           (int)(j * cellSize),
                           (int)((i+1)*cellSize) - (int)(i*cellSize),
                           (int)((j+1)*cellSize) - (int)(j*cellSize));

                if (cellSize >= 10.5){
                    g.setColor(Color.GRAY);  //only if cell size >= 5
                    g.drawRect((int)(i * cellSize),
                            (int)(j * cellSize),
                            (int)((i+1)*cellSize) - (int)(i*cellSize),
                            (int)((j+1)*cellSize) - (int)(j*cellSize));
                }
            }
        }
    }
}