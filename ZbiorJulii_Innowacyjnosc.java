import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

public class ZbiorJulii_Innowacyjnosc extends JPanel {
    private static final int LICZBA_ITERACJI = 200;
    private static final double ZOOM = 0.5;
    private static final double CX = -0.7;
    private static final double CY = 0.27015;
    private static final double X = 0;
    private static final double Y = 0;

    public ZbiorJulii_Innowacyjnosc() {
        setPreferredSize(new Dimension(600, 500));
        setBackground(Color.red);

    }

    void drawJuliaSet(Graphics2D g) {
        int w = getWidth();
        int h = getHeight();
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        IntStream.range(0, w).parallel().forEach(x -> {
            IntStream.range(0, h).parallel().forEach(y -> {
                double zx = 1.5 * (x - w / 2) / (0.5 * ZOOM * w) + X;
                double zy = (y - h / 2) / (0.5 * ZOOM * h) + Y;
                float i = LICZBA_ITERACJI;
                while (zx * zx + zy * zy < 4 && i > 0) {
                    double tmp = zx * zx - zy * zy + CX;
                    zy = 2.0 * zx * zy + CY;
                    zx = tmp;
                    i--;
                }
                int c = Color.HSBtoRGB((LICZBA_ITERACJI / i) % 1, 1, i > 0 ? 1 : 0);
                image.setRGB(x, y, c);
            });
        });
        g.drawImage(image,1, 0, null);
    }

    @Override
    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        drawJuliaSet(g);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setTitle("Zbiór Julii");
            f.setResizable(false);
            f.add(new ZbiorJulii_Innowacyjnosc(), BorderLayout.CENTER);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}