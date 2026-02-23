import java.awt.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static java.lang.Math.abs;

public class CityGen {
    Random rand = new Random();
    int TILE_SIZE;

    final int HOUSE_SIZE = 11;

    CityGen(int TILE_SIZE){
        this.TILE_SIZE = TILE_SIZE;
    }

    void addCity(int[][] tile, Point origPnt, Point cityPnt, int CITY_SIZE){
        int HOUSE_NUM = 5+rand.nextInt(5);
        Set<Point> houses = new HashSet<Point>();

        while (houses.size() < HOUSE_NUM){
            Point newHouse = new Point(rand.nextInt(CITY_SIZE-HOUSE_SIZE)+cityPnt.x,
                                       rand.nextInt(CITY_SIZE-HOUSE_SIZE)+cityPnt.y);
            boolean flag = true;
            for (Point house : houses){
                long dx = abs(newHouse.x - house.x);
                long dy = abs(newHouse.y - house.y);
                if ((dx*dx + dy*dy) < (long)(HOUSE_SIZE*HOUSE_SIZE*2)){
                    flag = false;
                    break;
                }
            }
            if (flag){
                houses.add(newHouse);
            }
        }

        for (Point house : houses){
            for (int i = house.x; i != house.x+HOUSE_SIZE; ++i){
                for (int j = house.y; j != house.y+HOUSE_SIZE; ++j){
                    tile[i][j] = 5;
                }
            }
        }
    }
}
