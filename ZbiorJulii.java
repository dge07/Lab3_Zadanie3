import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
public class ZbiorJulii extends Thread {
    final static int N = 4000;
    final static int CUTOFF = 300;
    private static final double ZOOM = 1;
    private static final double CX = -0.7;
    private static final double CY = 0.27015;
    private static final double MOVE_X = 0;
    private static final double MOVE_Y = 0;
    static int[][] set = new int[N][N];

    public static void main(String[] args) throws Exception {

        long startTime = System.currentTimeMillis();
        ZbiorJulii thread0 = new ZbiorJulii(0);
        thread0.start();
        thread0.join();

        long endTime = System.currentTimeMillis();
        System.out.println("Obliczenia zakończone w czasie " + (endTime - startTime) + " millisekund");
// wyświetlanie rusunku
        BufferedImage img = new BufferedImage(N, N, BufferedImage.TYPE_INT_ARGB);
// Rysowanie pixeli
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int k = set[i][j];
                float level;
                if (k < CUTOFF) {
                    level = (float) k / CUTOFF;
                } else {
                    level = 0;
                }
                Color c = new Color(0, 0,level); // niebieski
                img.setRGB(i, j, c.getRGB());
            }
        }
// zapis do pliku
        ImageIO.write(img, "PNG", new File("ZbiorJulii.png"));
    }

    int me;

    public ZbiorJulii(int me) {
        this.me = me;
    }

    public void run() {
        int begin = 0, end = 0;
        if (me == 0) {
            end = (N);
        }
        for (int x = begin; x < end; x++) {
            for (int y = 0; y < N; y++) {
                double zx = 1.5 * (x - end / 2) / (0.5 * ZOOM * end) + MOVE_X;
                double zy = (y - N / 2) / (0.5 * ZOOM * N) + MOVE_Y;
                int i = 0;
                while ( zx * zx + zy * zy < 4 && i >= 0 && i<CUTOFF) {
                    double tmp = zx * zx - zy * zy + CX;
                    zy = 2.0 * zx * zy + CY;
                    zx = tmp;
                    i++;
                }
                set[x][y] = i;
            }
        }
    }
}