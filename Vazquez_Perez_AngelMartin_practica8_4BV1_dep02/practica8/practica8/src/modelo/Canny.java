package modelo;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import javax.swing.JFrame;
import modelo.ImageBufferedImage;

public class Canny {
    private int[][] imagenInt;
    private int alto;
    private int ancho;
    private ImageBufferedImage imageBuffered;
    private Convolucion convolucion;
    private final double[][] gauss5x5 = {
        {1,  4,  7,  4, 1},
        {4, 16, 26, 16, 4},
        {7, 26, 41, 26, 7},
        {4, 16, 26, 16, 4},
        {1,  4,  7,  4, 1}
    };
    private double bias = 273.0;
    public Canny() {
        
    }

    public void initComponents() {
        alto = imagenInt.length;
        ancho = imagenInt[0].length;
        convolucion = new Convolucion(1/bias);
        imageBuffered = new ImageBufferedImage();
    }

    public int[][] cannyAlgorythm() {
        int[][] suavizada = aplicarMascaraGauss(imagenInt);

        // Paso 2: Cálculo del gradiente en cada píxel
        int[][] gradienteMagnitud = new int[alto][ancho];
        int[][] gradienteAngulo = new int[alto][ancho];
        calcularGradiente(suavizada, gradienteMagnitud, gradienteAngulo);

        // Paso 3: Supresión de no-máximos
        int[][] maximaSupresion = supresionNoMaximos(gradienteMagnitud, gradienteAngulo);

        // Paso 4: Umbralización para detección de bordes
        return aplicarUmbral(maximaSupresion, 40, 75);
    }

    // Método para aplicar un filtro gaussiano
    private int[][] aplicarMascaraGauss(int[][] imagen) {
        int[][] convolucionada =convolucion.calcularConvolucion(gauss5x5, gauss5x5.length, imagen, 1);
        System.out.println("se resolivio ntodo andk nfl");
        return convolucionada;
    }

    // Método para calcular la magnitud y el ángulo del gradiente
    private void calcularGradiente(int[][] imagen, int[][] magnitud, int[][] angulo) {
        for (int y = 1; y < alto - 1; y++) {
            for (int x = 1; x < ancho - 1; x++) {
                int gx = (imagen[y - 1][x + 1] + 2 * imagen[y][x + 1] + imagen[y + 1][x + 1]) -
                         (imagen[y - 1][x - 1] + 2 * imagen[y][x - 1] + imagen[y + 1][x - 1]);

                int gy = (imagen[y + 1][x - 1] + 2 * imagen[y + 1][x] + imagen[y + 1][x + 1]) -
                         (imagen[y - 1][x - 1] + 2 * imagen[y - 1][x] + imagen[y - 1][x + 1]);

                magnitud[y][x] = (int) Math.hypot(gx, gy);
                angulo[y][x] = (int) (Math.toDegrees(Math.atan2(gy, gx)) + 180) % 180; // Ángulo en grados
            }
        }
    }

    // Método para la supresión de no-máximos
    private int[][] supresionNoMaximos(int[][] magnitud, int[][] angulo) {
        int[][] resultado = new int[alto][ancho];
        for (int y = 1; y < alto - 1; y++) {
            for (int x = 1; x < ancho - 1; x++) {
                int dir = angulo[y][x];

                int valorActual = magnitud[y][x];
                if ((dir >= 0 && dir < 22.5) || (dir >= 157.5 && dir <= 180)) {
                    if (valorActual >= magnitud[y][x - 1] && valorActual >= magnitud[y][x + 1]) {
                        resultado[y][x] = valorActual;
                    }
                } else if (dir >= 22.5 && dir < 67.5) {
                    if (valorActual >= magnitud[y - 1][x + 1] && valorActual >= magnitud[y + 1][x - 1]) {
                        resultado[y][x] = valorActual;
                    }
                } else if (dir >= 67.5 && dir < 112.5) {
                    if (valorActual >= magnitud[y - 1][x] && valorActual >= magnitud[y + 1][x]) {
                        resultado[y][x] = valorActual;
                    }
                } else if (dir >= 112.5 && dir < 157.5) {
                    if (valorActual >= magnitud[y - 1][x - 1] && valorActual >= magnitud[y + 1][x + 1]) {
                        resultado[y][x] = valorActual;
                    }
                }
            }
        }
        return resultado;
    }

    private int[][] aplicarUmbral(int[][] imagen, int bajo, int alta) {
        BufferedImage buffered = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int valor = imagen[y][x];

                // Ajusta el valor para ser interpretado como RGB (0xRRGGBB)
                int rgb = (valor << 16) | (valor << 8) | valor;

                buffered.setRGB(x, y, rgb);
            }
        }
        Umbralizacion umbralizador = new Umbralizacion(buffered);
        Image imagenUmbralizada = umbralizador.ajustarUmbral(bajo, alto);
        BufferedImage BufferedImageNueva=imageBuffered.getBufferedImageColor(imagenUmbralizada);
        int[][] nuevaMatriz = imageBuffered.getMatrizImagen(BufferedImageNueva, 5);
        return nuevaMatriz;
    }

    public Image getImageCanny(int[][] imagenInt) {
        this.imagenInt=imagenInt;
        initComponents();
        int[][] matrizNueva = cannyAlgorythm();
        System.out.println(matrizNueva.length);
        System.out.println(matrizNueva[0].length);
        //System.out.println(matrizNueva[150][150]);
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int pixel = matrizNueva[y][x];
                Color color = new Color(pixel,pixel,pixel);
                    matrizNueva[y][x] = color.getRGB();
                }
            }
                JFrame padre = new JFrame();
                Image imagenNueva;
                imagenNueva = padre.createImage(new MemoryImageSource(ancho,
                alto, imageBuffered.convertirInt2DAInt1D(matrizNueva, ancho, alto),
                0, ancho));
        return imagenNueva;
    }
    public void setMatriz(int[][] matriz){
        this.imagenInt=matriz;
        initComponents();
    }
}
