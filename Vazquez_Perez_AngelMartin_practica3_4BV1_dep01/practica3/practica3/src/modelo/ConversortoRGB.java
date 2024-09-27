/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.MemoryImageSource;
import javax.swing.JFrame;

/**
 *
 * @author jhona
 */
public class ConversortoRGB {
    private Image imagen;
    private BufferedImage buffered;
    private ImageBufferedImage imageBuffered;
    private int alto;
    private int ancho;
    public ConversortoRGB(Image Imagen){
        this.imagen=Imagen;
        initComponents();
    }
    public void initComponents(){
        imageBuffered = new ImageBufferedImage();
        buffered = imageBuffered.getBufferedImageColor(imagen);
        alto=buffered.getHeight();
        ancho=buffered.getWidth();
    }
    
    public Image CMYtoRGB(){
        int[][] nuevaImagen= new int [alto][ancho];
        int R,G,B;
        for(int y=0; y<alto ; y++){
            for(int x=0; x<ancho ; x ++){
                Color color=null;
                int pixel = buffered.getRGB(x,y);
                int cyan  = (pixel & 0x00ff0000) >> 16;
                int magenta = (pixel & 0x0000ff00) >> 8;
                int amarillo  =  pixel & 0x000000ff;
                R = 255 - cyan;
                G = 255 - magenta;
		B = 255 - amarillo;
                color = new Color(R,G,B);
                nuevaImagen[y][x]=color.getRGB();
            }
        }
        JFrame padre = new JFrame();
        Image imagenNueva = padre.createImage(new MemoryImageSource(ancho,
                alto, imageBuffered.convertirInt2DAInt1D(nuevaImagen, ancho, alto),
                0, ancho));
        System.out.println("se retorno la imagen");
        return imagenNueva;    
    }
    public Image HSItoRGB() {
    int[][] nuevaImagen = new int[alto][ancho];
    for (int y = 0; y < alto; y++) {
        for (int x = 0; x < ancho; x++) {
            Color color=null;
            int pixel = buffered.getRGB(x, y);
            int rojo = (pixel & 0x00ff0000) >> 16;
            int verde = (pixel & 0x0000ff00) >> 8;
            int azul = pixel & 0x000000ff;

            // Obtención de los componentes HSI
            double H = rojo / 255.0 * 360; // Convertir H de [0, 255] a [0, 360]
            double S = verde / 255.0; // Saturación de [0, 255] a [0, 1]
            double I =  azul / 255.0; // Intensidad de [0, 255] a [0, 1]

            double R = 0, G = 0, B = 0;

            // Convertir HSI a RGB
            if (H >= 0 && H < 120) {
                B = I * (1 - S);
                R = I * (1 + (S * Math.cos(Math.toRadians(H))) / Math.cos(Math.toRadians(60 - H)));
                G = 3 * I - (R + B);
            } else if (H >= 120 && H < 240) {
                H = H - 120;
                R = I * (1 - S);
                G = I * (1 + (S * Math.cos(Math.toRadians(H))) / Math.cos(Math.toRadians(60 - H)));
                B = 3 * I - (R + G);
            } else if (H >= 240 && H <= 360) {
                H = H - 240;
                G = I * (1 - S);
                B = I * (1 + (S * Math.cos(Math.toRadians(H))) / Math.cos(Math.toRadians(60 - H)));
                R = 3 * I - (G + B);
            }
            rojo= (int) Math.min(255, Math.max(0, R * 255));
            verde = (int) Math.min(255, Math.max(0, G * 255));
            azul = (int) Math.min(255, Math.max(0, B * 255));

            nuevaImagen[y][x] = new Color(rojo, verde, azul).getRGB();
        }
    }

    JFrame padre = new JFrame();
    Image imagenNueva = padre.createImage(new MemoryImageSource(ancho, alto, 
                imageBuffered.convertirInt2DAInt1D(nuevaImagen, ancho, alto), 0, ancho));
    return imagenNueva;
    }
    
    public Image HSVtoRGB() {
    int[][] nuevaImagen = new int[alto][ancho];
    for (int y = 0; y < alto; y++) {
        for (int x = 0; x < ancho; x++) {
            Color color = null;
            int pixel = buffered.getRGB(x, y);
            int rojo = (pixel & 0x00ff0000) >> 16;
            int verde = (pixel & 0x0000ff00) >> 8;
            int azul = pixel & 0x000000ff;

            // Conversión de los valores HSV
            double H = rojo / 255.0 * 360;
            double S = verde / 255.0;     
            double V = azul / 255.0;      

            // Calcular M, m, z
            double M = 255 * V;
            double m = M * (1 - S);
            double z = (M - m) * (1 - Math.abs(((H / 60) % 2) - 1));

            // Variables RGB
            double R = 0, G = 0, B = 0;

            int hi = (int) (H / 60) % 6;

            switch (hi) {
                case 0:
                    R = M;
                    G = z + m;
                    B = m;
                    break;
                case 1:
                    R = z + m;
                    G = M;
                    B = m;
                    break;
                case 2:
                    R = m;
                    G = M;
                    B = z + m;
                    break;
                case 3:
                    R = m;
                    G = z + m;
                    B = M;
                    break;
                case 4:
                    R = z + m;
                    G = m;
                    B = M;
                    break;
                case 5:
                    R = M;
                    G = m;
                    B = z + m;
                    break;
            }
            rojo =validar(rojo);
            verde=validar(verde);
            azul=validar(azul);

            // Asignar el nuevo valor de color
            color = new Color(rojo, verde,azul);
            nuevaImagen[y][x] = color.getRGB();
        }
    }

    JFrame padre = new JFrame();
    Image imagenNueva = padre.createImage(new MemoryImageSource(ancho, alto,
            imageBuffered.convertirInt2DAInt1D(nuevaImagen, ancho, alto), 0, ancho));
    System.out.println("HSV a rgb completada");
    return imagenNueva;
}



    public Image YIQtoRGB(){
        int[][] nuevaImagen= new int [alto][ancho];
        int R,G,B;
        for(int y=0; y<alto ; y++){
            for(int x=0; x<ancho ; x ++){
                Color color=null;
                int pixel = buffered.getRGB(x,y);
                int Y  = (pixel & 0x00ff0000) >> 16;
                int I = ((pixel & 0x0000ff00) >> 8)-128;
                int Q  = ( pixel & 0x000000ff) -128;
                R = (int) (Y + .956*I + .621*Q);
                G =(int) (Y - .272*I - .647*Q);
		B =(int) (Y - 1.106*I + 1.703*Q);
                R=validar(R);
                G=validar(G);
                B=validar(B);
                color = new Color(R,G,B);
                nuevaImagen[y][x]=color.getRGB();
            }
        }
        JFrame padre = new JFrame();
        Image imagenNueva = padre.createImage(new MemoryImageSource(ancho,
                alto, imageBuffered.convertirInt2DAInt1D(nuevaImagen, ancho, alto),
                0, ancho));
        System.out.println("se retorno la imagen");
        return imagenNueva;    
    }
public Image LABtoRGB() {
    int[][] nuevaImagen = new int[alto][ancho];

    for (int y = 0; y < alto; y++) {
        for (int x = 0; x < ancho; x++) {
            int pixel = buffered.getRGB(x, y);
            int l = (pixel & 0x00ff0000) >> 16; // L componente
            int a = (pixel & 0x0000ff00) >> 8;  // A componente
            int b = (pixel & 0x000000ff);       // B componente

            // Convertir L, A, B a valores en el rango adecuado
            double L = l * 100.0 / 255.0;
            double A = a - 128.0;
            double B = b - 128.0;

            // Paso 1: Convertir LAB a XYZ
            double Y = (L + 16) / 116.0;
            double X = A / 500.0 + Y;
            double Z = Y - B / 200.0;

            // Aplicar la función inversa f^-1(t)
            X = Math.pow(X, 3) > 0.008856 ? Math.pow(X, 3) : (X - 16.0 / 116.0) / 7.787;
            Y = Math.pow(Y, 3) > 0.008856 ? Math.pow(Y, 3) : (Y - 16.0 / 116.0) / 7.787;
            Z = Math.pow(Z, 3) > 0.008856 ? Math.pow(Z, 3) : (Z - 16.0 / 116.0) / 7.787;

            // Escalar XYZ
            X *= 95.047;
            Y *= 100.0;
            Z *= 108.883;

            // Paso 2: Convertir XYZ a RGB
            X /= 100.0;
            Y /= 100.0;
            Z /= 100.0;

            double r = X * 3.2406 + Y * -1.5372 + Z * -0.4986;
            double g = X * -0.9689 + Y * 1.8758 + Z * 0.0415;
            double azul = X * 0.0557 + Y * -0.2040 + Z * 1.0570;

            // Convertir a rango [0, 1]
            r = (r > 0.0031308) ? (1.055 * Math.pow(r, (1 / 2.4)) - 0.055) : 12.92 * r;
            g = (g > 0.0031308) ? (1.055 * Math.pow(g, (1 / 2.4)) - 0.055) : 12.92 * g;
            azul = (azul > 0.0031308) ? (1.055 * Math.pow(azul, (1 / 2.4)) - 0.055) : 12.92 * azul;

            // Escalar a [0, 255] y corregir valores fuera de rango
            int rojo = (int) Math.min(255, Math.max(0, r * 255));
            int verde = (int) Math.min(255, Math.max(0, g * 255));
            int azule = (int) Math.min(255, Math.max(0, azul * 255));

            // Crear el color RGB
            Color color = new Color(rojo, verde, azule);
            nuevaImagen[y][x] = color.getRGB();
        }
    }

    // Crear la imagen con los valores convertidos a RGB
    JFrame padre = new JFrame();
    Image imagenNueva = padre.createImage(new MemoryImageSource(ancho, alto, imageBuffered.convertirInt2DAInt1D(nuevaImagen, ancho, alto), 0, ancho));

    System.out.println("LAB to RGB conversión completada");
    return imagenNueva;
}

    public int validar(int numero){
        if(numero>255){
            return 255;
        }else if (numero<0){
            return 0;
        }
        return numero;
    }
}
