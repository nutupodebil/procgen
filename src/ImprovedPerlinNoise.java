import java.util.Random;

public class ImprovedPerlinNoise {
    private static final int PERMUTATION_SIZE = 256;
    private int[] permutation;
    private double[] gradientsX;
    private double[] gradientsY;

    public ImprovedPerlinNoise(long seed) {
        permutation = new int[PERMUTATION_SIZE * 2];
        gradientsX = new double[PERMUTATION_SIZE];
        gradientsY = new double[PERMUTATION_SIZE];

        Random random = new Random(seed);

        // Инициализация градиентов
        for (int i = 0; i < PERMUTATION_SIZE; i++) {
            double angle = random.nextDouble() * 2 * Math.PI;
            gradientsX[i] = Math.cos(angle);
            gradientsY[i] = Math.sin(angle);
            permutation[i] = i;
        }

        // Перемешивание перестановки
        for (int i = PERMUTATION_SIZE - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = permutation[i];
            permutation[i] = permutation[j];
            permutation[j] = temp;
        }

        // Дублирование массива
        System.arraycopy(permutation, 0, permutation, PERMUTATION_SIZE, PERMUTATION_SIZE);
    }

    public double noise(double x, double y, double frequency) {
        x *= frequency;
        y *= frequency;

        int xi = (int) Math.floor(x) & 255;
        int yi = (int) Math.floor(y) & 255;

        double xf = x - Math.floor(x);
        double yf = y - Math.floor(y);

        double u = fade(xf);
        double v = fade(yf);

        int aa = permutation[permutation[xi] + yi];
        int ab = permutation[permutation[xi] + yi + 1];
        int ba = permutation[permutation[xi + 1] + yi];
        int bb = permutation[permutation[xi + 1] + yi + 1];

        double dotAA = dot(gradientsX[aa], gradientsY[aa], xf, yf);
        double dotBA = dot(gradientsX[ba], gradientsY[ba], xf - 1, yf);
        double dotAB = dot(gradientsX[ab], gradientsY[ab], xf, yf - 1);
        double dotBB = dot(gradientsX[bb], gradientsY[bb], xf - 1, yf - 1);

        double x1 = lerp(u, dotAA, dotBA);
        double x2 = lerp(u, dotAB, dotBB);

        return lerp(v, x1, x2);
    }

    private double dot(double gx, double gy, double x, double y) {
        return gx * x + gy * y;
    }

    private double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    private double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }
}