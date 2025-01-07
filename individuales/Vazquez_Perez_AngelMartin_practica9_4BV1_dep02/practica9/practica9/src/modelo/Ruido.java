package modelo; 

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import javax.swing.JFrame;
import java.util.Random;
import modelo.ImageBufferedImage;

public class Ruido {
    private Image imagen;
    private int[][] matrizImagen;
    private ImageBufferedImage imageBuffered;
    private int alto;
    private int ancho;

    public Ruido(Image imagen) {
        this.imagen = imagen;
        imageBuffered = new ImageBufferedImage();
        initComponents();
    }

    public void initComponents() {
        BufferedImage buffered = imageBuffered.getBufferedImage(imagen);
        matrizImagen = imageBuffered.getMatrizImagen(buffered, 5);
        alto = matrizImagen.length;
        ancho = matrizImagen[0].length;
    }

    public Image aplicarRuidoCoherente(int u0, int y0) {
        int[][] nuevaMatriz = new int[alto][ancho];
        Random random = new Random();

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int ruido = (int) (Math.sin(u0*x + y0*y) * 150);
                int gris = matrizImagen[y][x] + ruido;
                gris = Math.min(255, Math.max(0, gris));
                Color color = new Color(gris, gris, gris);
                nuevaMatriz[y][x] = color.getRGB();
            }
        }
        return generarImagenDesdeMatriz(nuevaMatriz);
    }

    public Image aplicarRuidoGamma() {
        int[][] nuevaMatriz = new int[alto][ancho];
        double gamma = 2.0;

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int gris = matrizImagen[y][x];
                gris = (int) (255 * Math.pow((double) gris / 255, gamma));
                gris = Math.min(255, Math.max(0, gris));
                Color color = new Color(gris, gris, gris);
                nuevaMatriz[y][x] = color.getRGB();
            }
        }
        return generarImagenDesdeMatriz(nuevaMatriz);
    }

    public Image aplicarRuidoGaussiano(int media,int varianza) {
        int[][] nuevaMatriz = new int[alto][ancho];
        Random random = new Random();
        double mean = 0.0;
        double stdDev = 25.0;

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int ruido = (int) (random.nextGaussian() * varianza + media);
                int gris = matrizImagen[y][x] + ruido;
                gris = Math.min(255, Math.max(0, gris));
                Color color = new Color(gris, gris, gris);
                nuevaMatriz[y][x] = color.getRGB();
            }
        }
        return generarImagenDesdeMatriz(nuevaMatriz);
    }

    public Image aplicarRuidoExponencialNegativo(int varianza) {
        int[][] nuevaMatriz = new int[alto][ancho];
        Random random = new Random();
        double lambda = 1.0;

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int ruido = (int) (-Math.log(1 - random.nextDouble()) / varianza * 50);
                int gris = matrizImagen[y][x] + ruido;
                gris = Math.min(255, Math.max(0, gris));
                Color color = new Color(gris, gris, gris);
                nuevaMatriz[y][x] = color.getRGB();
            }
        }
        return generarImagenDesdeMatriz(nuevaMatriz);
    }

    public Image aplicarRuidoRayleigh(int sigma) {
        int[][] nuevaMatriz = new int[alto][ancho];
        Random random = new Random();

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int ruido = (int) (sigma * Math.sqrt(-2 * Math.log(1 - random.nextDouble())));
                int gris = matrizImagen[y][x] + ruido;
                gris = Math.min(255, Math.max(0, gris));
                Color color = new Color(gris, gris, gris);
                nuevaMatriz[y][x] = color.getRGB();
            }
        }
        return generarImagenDesdeMatriz(nuevaMatriz);
    }

    public Image aplicarRuidoSalyPimienta() {
        int[][] nuevaMatriz = new int[alto][ancho];
        Random random = new Random();
        double probability = 0.02;

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int gris = matrizImagen[y][x];
                if (random.nextDouble() < probability) {
                    gris = (random.nextDouble() < 0.5) ? 0 : 255;
                }
                Color color = new Color(gris, gris, gris);
                nuevaMatriz[y][x] = color.getRGB();
            }
        }
        return generarImagenDesdeMatriz(nuevaMatriz);
    }

    public Image aplicarRuidoUniforme(int min,int max) {
        int[][] nuevaMatriz = new int[alto][ancho];
        Random random = new Random();

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int ruido = random.nextInt(max - min + 1) + min;
                int gris = matrizImagen[y][x] + ruido;
                gris = Math.min(255, Math.max(0, gris));
                Color color = new Color(gris, gris, gris);
                nuevaMatriz[y][x] = color.getRGB();
            }
        }
        return generarImagenDesdeMatriz(nuevaMatriz);
    }
    public Image aplicarRuidoSal() {
        int[][] nuevaMatriz = new int[alto][ancho];
        Random random = new Random();
        double probability = 0.02; // Probabilidad de que un píxel sea afectado

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int gris = matrizImagen[y][x];
                if (random.nextDouble() < probability) {
                    gris = 255; // Sal (blanco)
                }
                Color color = new Color(gris, gris, gris);
                nuevaMatriz[y][x] = color.getRGB();
            }
        }
        return generarImagenDesdeMatriz(nuevaMatriz);
    }
    public Image aplicarRuidoPimienta() {
        int[][] nuevaMatriz = new int[alto][ancho];
        Random random = new Random();
        double probability = 0.02; // Probabilidad de que un píxel sea afectado

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int gris = matrizImagen[y][x];
                if (random.nextDouble() < probability) {
                    gris = 0; // Pimienta (negro)
                }
                Color color = new Color(gris, gris, gris);
                nuevaMatriz[y][x] = color.getRGB();
            }
        }
        return generarImagenDesdeMatriz(nuevaMatriz);
    }



    private Image generarImagenDesdeMatriz(int[][] matriz) {
        JFrame padre = new JFrame();
        return padre.createImage(new MemoryImageSource(ancho, alto, imageBuffered.convertirInt2DAInt1D(matriz, ancho, alto), 0, ancho));
    }

    public void setImagen(Image nuevaImagen) {
        this.imagen = nuevaImagen;
        initComponents();
    }
}
