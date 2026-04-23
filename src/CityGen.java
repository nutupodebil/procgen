import java.awt.*;
import java.util.*;
import java.util.List;

import static java.lang.Math.abs;

public class CityGen {
    Random rand = new Random();
    int TILE_SIZE;

    final int HOUSE_SIZE = 5;

    ArrayList<Point> bigHouses = new ArrayList<>();
    ArrayList<Point> normHouses = new ArrayList<>();
    ArrayList<Point> smallHouses = new ArrayList<>();

    CityGen(int TILE_SIZE){
        this.TILE_SIZE = TILE_SIZE;

        bigHouses.add(new Point(3,2));
        bigHouses.add(new Point(2,3));
        bigHouses.add(new Point(3,3));

        normHouses.add(new Point(3,1));
        normHouses.add(new Point(1,3));
        normHouses.add(new Point(2,2));
        normHouses.add(new Point(2,2));
        normHouses.add(new Point(2,2));
        normHouses.add(new Point(2,2));
        normHouses.add(new Point(2,2));

        smallHouses.add(new Point(1,2));
        smallHouses.add(new Point(2,1));
        smallHouses.add(new Point(1,1));
    }

    long distance(int x1, int y1, int x2, int y2){
        long dx = abs((long)x1 - x2);
        long dy = abs((long)y1 - y2);
        return (long) (Math.sqrt(dx*dx + dy*dy));
    }

    void addSmallHouse(int[][] tile, int[] house, ArrayList<Point> porchy){
        Point porch = new Point();
        int type = rand.nextInt(3);
        int width = HOUSE_SIZE*smallHouses.get(type).x;
        int height = HOUSE_SIZE*smallHouses.get(type).y;
        for (int i = house[0]; i != house[0]+width; ++i){
            for (int j = house[1]; j != house[1]+height; ++j){
                tile[i][j] = 5;
            }
        }
        porch.x = house[0]+width/2;
        porch.y = house[1]+height;

        porchy.add(porch);
    }

    void addNormHouse(int[][] tile, int[] house, ArrayList<Point> porchy){
        Point porch = new Point();
        int type = rand.nextInt(7);

        if (type == 3){
            // xx
            // x
            for (int i = house[0]; i != house[0]+HOUSE_SIZE*2; ++i){
                for (int j = house[1]; j != house[1]+HOUSE_SIZE; ++j){
                    tile[i][j] = 5;
                }
            }
            for (int i = house[0]; i != house[0]+HOUSE_SIZE; ++i){
                for (int j = house[1]+HOUSE_SIZE; j != house[1]+HOUSE_SIZE*2; ++j){
                    tile[i][j] = 5;
                }
            }
            porch.x = house[0] + HOUSE_SIZE/2;
            porch.y = house[1] + HOUSE_SIZE*2;

            porchy.add(porch);

            return;
        }

        if (type == 4){
            // x
            // xx
            for (int i = house[0]; i != house[0]+HOUSE_SIZE; ++i){
                for (int j = house[1]; j != house[1]+HOUSE_SIZE; ++j){
                    tile[i][j] = 5;
                }
            }
            for (int i = house[0]; i != house[0]+HOUSE_SIZE*2; ++i){
                for (int j = house[1]+HOUSE_SIZE; j != house[1]+HOUSE_SIZE*2; ++j){
                    tile[i][j] = 5;
                }
            }
            porch.x = house[0]+HOUSE_SIZE;
            porch.y = house[1]+HOUSE_SIZE/2;

            porchy.add(porch);

            return;
        }

        if (type == 5){
            //  x
            // xx
            for (int i = house[0]+HOUSE_SIZE; i != house[0]+HOUSE_SIZE*2; ++i){
                for (int j = house[1]; j != house[1]+HOUSE_SIZE; ++j){
                    tile[i][j] = 5;
                }
            }
            for (int i = house[0]; i != house[0]+HOUSE_SIZE*2; ++i){
                for (int j = house[1]+HOUSE_SIZE; j != house[1]+HOUSE_SIZE*2; ++j){
                    tile[i][j] = 5;
                }
            }
            porch.x = house[0]+HOUSE_SIZE/2;
            porch.y = house[1]+HOUSE_SIZE-1;

            porchy.add(porch);

            return;
        }

        if (type == 6){
            // xx
            //  x
            for (int i = house[0]; i != house[0]+HOUSE_SIZE*2; ++i){
                for (int j = house[1]; j != house[1]+HOUSE_SIZE; ++j){
                    tile[i][j] = 5;
                }
            }
            for (int i = house[0]+HOUSE_SIZE; i != house[0]+HOUSE_SIZE*2; ++i){
                for (int j = house[1]+HOUSE_SIZE; j != house[1]+HOUSE_SIZE*2; ++j){
                    tile[i][j] = 5;
                }
            }
            porch.x = house[0]+HOUSE_SIZE*3/2;
            porch.y = house[1]+HOUSE_SIZE*2;

            porchy.add(porch);

            return;
        }

        int width = HOUSE_SIZE*normHouses.get(type).x;
        int height = HOUSE_SIZE*normHouses.get(type).y;

        for (int i = house[0]; i != house[0]+width; ++i){
            for (int j = house[1]; j != house[1]+height; ++j){
                tile[i][j] = 5;
            }
        }
        porch.x = house[0]+width/2;
        porch.y = house[1]+height;

        porchy.add(porch);
    }

    void addBigHouse(int[][] tile, int[] house, ArrayList<Point> porchy){
        Point porch = new Point();
        int type = rand.nextInt(3);
        int width = HOUSE_SIZE*bigHouses.get(type).x;
        int height = HOUSE_SIZE*bigHouses.get(type).y;
        for (int i = house[0]; i != house[0]+width; ++i){
            for (int j = house[1]; j != house[1]+height; ++j){
                tile[i][j] = 5;
            }
        }
        porch.x = house[0]+width/2;
        porch.y = house[1]+height;

        porchy.add(porch);
    }

    List<int[]> findHousesRand(int HOUSE_NUM, int CITY_SIZE, Point cityPnt){
        List<int[]> houses = new ArrayList<>();

        int cnt = 0;

        while (houses.size() < HOUSE_NUM){
            if (cnt > 2*HOUSE_NUM){
                houses.removeLast();
            }

            int[] newHouse = {rand.nextInt(CITY_SIZE-3*HOUSE_SIZE)+cityPnt.x,
                    rand.nextInt(CITY_SIZE-3*HOUSE_SIZE)+cityPnt.y,
                    0};

            long toCenter = distance(cityPnt.x+(CITY_SIZE/2)+HOUSE_SIZE*2, cityPnt.y+(CITY_SIZE/2)+HOUSE_SIZE*2, newHouse[0], newHouse[1]);
            long dist = HOUSE_SIZE*HOUSE_SIZE*4L;
            if (toCenter >= (long)(CITY_SIZE/4L) && toCenter < (long)(CITY_SIZE*1L/2L)){
                newHouse[2] = 1;
                dist = HOUSE_SIZE*3L;
            } else if (toCenter >= (long)(CITY_SIZE*1L/2L)){
                newHouse[2] = 2;
                dist = HOUSE_SIZE*2L;
            }

            boolean flag = true;
            for (int[] house : houses){
                if (distance(newHouse[0], newHouse[1], house[0], house[1]) < dist){
                    flag = false;
                    cnt++;
                    break;
                }
            }

            if (flag){
                houses.addLast(newHouse);
            }
        }

        return houses;
    }

    List<int[]> findHouses(int N, double r, int k) {

        // Размер ячейки пространственной сетки (чтобы в одной ячейке не могло быть двух точек)
        double cellSize = r / Math.sqrt(2.0);
        int gridSize = (int) (N / cellSize) + 2; // запас по границам

        // Сетка: индекс точки в ячейке или -1, если ячейка пуста
        int[][] grid = new int[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            Arrays.fill(grid[i], -1);
        }

        List<int[]> samples = new ArrayList<>();   // все точки
        List<Integer> activeList = new ArrayList<>(); // индексы активных точек

        // Шаг 1: начальная случайная точка
        int x0 = rand.nextInt(N);
        int y0 = rand.nextInt(N);
        int[] firstPoint = {x0, y0};
        samples.add(firstPoint);
        activeList.add(0);

        int cx0 = (int) (x0 / cellSize);
        int cy0 = (int) (y0 / cellSize);
        grid[cx0][cy0] = 0;

        // Шаг 2: основной цикл
        while (!activeList.isEmpty()) {
            int activeIndex = rand.nextInt(activeList.size());
            int pointIndex = activeList.get(activeIndex);
            int[] point = samples.get(pointIndex);

            boolean found = false;

            // Пытаемся сгенерировать до k кандидатов вокруг текущей точки
            for (int attempt = 0; attempt < k; attempt++) {
                double angle = 2 * Math.PI * rand.nextDouble();
                double radius = r + rand.nextDouble() * r; // равномерно от r до 2r
                double dx = radius * Math.cos(angle);
                double dy = radius * Math.sin(angle);
                double cx = point[0] + dx;
                double cy = point[1] + dy;

                int ix = (int) Math.round(cx);
                int iy = (int) Math.round(cy);

                // Проверка границ матрицы
                if (ix < 0 || ix >= N || iy < 0 || iy >= N) {
                    continue;
                }

                int candidateCellX = (int) (ix / cellSize);
                int candidateCellY = (int) (iy / cellSize);
                int cellRange = (int) Math.floor(r / cellSize) + 1; // радиус поиска по ячейкам
                boolean valid = true;

                // Проверка соседних ячеек на наличие точек ближе r
                for (int dxCell = -cellRange; dxCell <= cellRange; dxCell++) {
                    for (int dyCell = -cellRange; dyCell <= cellRange; dyCell++) {
                        int nx = candidateCellX + dxCell;
                        int ny = candidateCellY + dyCell;
                        if (nx >= 0 && nx < gridSize && ny >= 0 && ny < gridSize) {
                            int neighborIdx = grid[nx][ny];
                            if (neighborIdx != -1) {
                                int[] neighbor = samples.get(neighborIdx);
                                double dist = distance(ix, iy, neighbor[0], neighbor[1]);
                                // Используем небольшой допуск для учёта погрешностей вычислений
                                if (dist < r - 1e-9) {
                                    valid = false;
                                    break;
                                }
                            }
                        }
                    }
                    if (!valid) break;
                }

                if (valid) {
                    // Кандидат подходит – добавляем в список
                    int[] newPoint = {ix, iy};
                    int newIndex = samples.size();
                    samples.add(newPoint);
                    activeList.add(newIndex);
                    grid[candidateCellX][candidateCellY] = newIndex;
                    found = true;
                    break;
                }
            }

            // Если ни один кандидат не подошёл, удаляем текущую точку из активного списка
            if (!found) {
                activeList.remove(activeIndex);
            }
        }

        return samples;
    }


    public static ArrayList<Point> shortestPath(int[][] matrix, Point cityPnt, int K,
                                                int x1, int y1, int x2, int y2) {

        int a = cityPnt.x;
        int b = cityPnt.y;

        // Границы подматрицы
        int rowStart = a;
        int rowEnd = a + K - 1;
        int colStart = b;
        int colEnd = b + K - 1;

        // Посещённые клетки (относительные координаты внутри подматрицы)
        boolean[][] visited = new boolean[K][K];
        // parent[ri][cj] хранит точку-предка для клетки (a+ri, b+cj)
        Point[][] parent = new Point[K][K];

        Queue<Point> queue = new LinkedList<>();
        Point start = new Point(x1, y1);
        queue.add(start);
        visited[x1 - a][y1 - b] = true;
        parent[x1 - a][y1 - b] = null;

        // Направления движения: вниз, вверх, вправо, влево
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!queue.isEmpty()) {
            Point cur = queue.poll();
            int x = cur.x;
            int y = cur.y;

            // Достигли цели?
            if (x == x2 && y == y2) {
                // Восстанавливаем путь
                LinkedList<Point> path = new LinkedList<>();
                Point p = new Point(x2, y2);
                // Идём от финиша к старту, собирая в обратном порядке
                while (p != null && !(p.x == x1 && p.y == y1)) {
                    path.addFirst(p);
                    p = parent[p.x - a][p.y - b];
                }
                path.addFirst(new Point(x1, y1));
                return new ArrayList<>(path);
            }

            // Перебираем соседей
            for (int[] d : dirs) {
                int nx = x + d[0];
                int ny = y + d[1];
                if (nx >= rowStart && nx <= rowEnd && ny >= colStart && ny <= colEnd) {
                    int ri = nx - a;
                    int cj = ny - b;
                    if (matrix[nx][ny] != 5 && !visited[ri][cj]) {
                        visited[ri][cj] = true;
                        parent[ri][cj] = new Point(x, y);
                        queue.add(new Point(nx, ny));
                    }
                }
            }
        }

        // Путь не найден
        return new ArrayList<>();
    }

    public static ArrayList<int[]> primMST(ArrayList<ArrayList<Object[]>> graph) {
        int n = graph.size();
        if (n == 0) {
            return new ArrayList<>();
        }

        // Массив минимальных весов для подключения вершины к текущему дереву
        double[] key = new double[n];
        // Массив предков (какая вершина добавила данную в дерево)
        int[] parent = new int[n];
        // Флаг включения вершины в остовное дерево
        boolean[] inMST = new boolean[n];

        // Инициализация: все ключи бесконечны, кроме стартовой вершины (0)
        Arrays.fill(key, Double.POSITIVE_INFINITY);
        key[0] = 0.0;
        parent[0] = -1;

        // Построение MST (будет ровно n-1 итераций добавления вершин)
        for (int count = 0; count < n; count++) {
            // Выбираем вершину с наименьшим ключом, ещё не включённую в дерево
            int u = -1;
            double minKey = Double.POSITIVE_INFINITY;
            for (int v = 0; v < n; v++) {
                if (!inMST[v] && key[v] < minKey) {
                    minKey = key[v];
                    u = v;
                }
            }
            // В полном связном графе u всегда найдётся, но проверка для безопасности
            if (u == -1) {
                break;
            }

            // Добавляем вершину u в дерево
            inMST[u] = true;

            // Обновляем ключи для всех вершин, ещё не в дереве
            for (int v = 0; v < n; v++) {
                if (v == u || inMST[v]) {
                    continue;
                }
                // Извлекаем вес ребра (u, v)
                Object[] edge = graph.get(u).get(v);
                // Первый элемент — вес; приводим к Number и получаем double
                Number weightNumber = (Number) edge[0];
                double weight = weightNumber.doubleValue();

                if (weight < key[v]) {
                    key[v] = weight;
                    parent[v] = u;
                }
            }
        }

        // Формируем список рёбер MST (для всех вершин, кроме корневой)
        ArrayList<int[]> mstEdges = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            mstEdges.add(new int[]{parent[i], i});
        }
        return mstEdges;
    }


    void addCity(int[][] tile, Point origPnt, Point cityPnt, int CITY_SIZE){
        ArrayList<Point> porchy = new ArrayList<>();

        List<int[]> houses = findHousesRand(10, CITY_SIZE, cityPnt);
        for (int[] house : houses){
            if (house[2] == 2) addSmallHouse(tile, house, porchy);
            else if (house[2] == 1) addNormHouse(tile, house, porchy);
            else addBigHouse(tile, house, porchy);
        }

//        List<int[]> houses = findHouses(CITY_SIZE - HOUSE_SIZE*3, HOUSE_SIZE*5, 30);
//        for (int[] house : houses){
//            house[0] += cityPnt.x;
//            house[1] += cityPnt.y;
//            addNormHouse(tile, house, porchy);
//        }


        ArrayList<ArrayList<Object[]>> graph = new ArrayList<>();

        for (int i = 0; i != porchy.size(); ++i){
            ArrayList<Object[]> line = new ArrayList<>();
            for (int j = 0; j != porchy.size(); ++j){
                if (i == j){
                    line.add(new Object[]{0,0});
                    continue;
                }
                Point p1 = porchy.get(i);
                Point p2 = porchy.get(j);
                ArrayList<Point> path = shortestPath(tile, cityPnt, CITY_SIZE, p1.x, p1.y, p2.x, p2.y);

                line.add(new Object[]{path.size(), path});
            }
            graph.add(line);
        }

        ArrayList<int[]> mst = primMST(graph);

        for (int[] edge : mst) {
            int u = edge[0];
            int v = edge[1];
            ArrayList<Point> path = (ArrayList<Point>) graph.get(u).get(v)[1];
            for (Point p : path){
                tile[p.x][p.y] = 6;
            }
        }

        for (Point porch : porchy){
            tile[porch.x][porch.y] = 7;
        }
    }
}
