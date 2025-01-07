/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import javax.swing.JFrame;

/**
 *
 * @author jhona
 */
public class RGBtoHSV {
    private Image img;
    private BufferedImage buffered;
    private ImageBufferedImage imageBuffered;
    private int alto;
    private int ancho;
    private Image imagenHSV;
    public RGBtoHSV(BufferedImage buffered){
        this.buffered=buffered;
        alto=buffered.getHeight();
        ancho=buffered.getWidth();
        imageBuffered = new ImageBufferedImage();
        img=imageBuffered.getImage(buffered,4); 
        initComponents();
    }
    public void initComponents(){
        imagenHSV=this.convertirRGBtoHSV(1);
    }
    public Image convertirRGBtoHSV(int selector ) {
    int[][] nuevaImagen = new int[alto][ancho];
    double H, S, V;

    for (int y = 0; y < alto; y++) {
        for (int x = 0; x < ancho; x++) {
            Color color=null;
            int pixel = buffered.getRGB(x, y);
            int rojo = (pixel & 0x00ff0000) >> 16;
            int verde = (pixel & 0x0000ff00) >> 8;
            int azul = pixel & 0x000000ff;

            // Convertir los valores RGB a rango [0, 1]
            double r = rojo / 255.0;
            double g = verde / 255.0;
            double b = azul / 255.0;

            // Encontrar el valor máximo y mínimo de los tres componentes
            double max = Math.max(r, Math.max(g, b));
            double min = Math.min(r, Math.min(g, b));

            // Valor (V) en el modelo HSV
            V = max;

            // Saturación (S)
            S = (max != 0) ? (max - min) / max : 0;

            // Hue (H)
            if (S == 0) {
                H = 0; // Si no hay saturación, el tono es 0
            } else {
                if (max == r) {
                    H = (g - b) / (max - min);
                } else if (max == g) {
                    H = 2 + (b - r) / (max - min);
                } else {
                    H = 4 + (r - g) / (max - min);
                }

                H *= 60; // Convertir H a grados
                if (H < 0) {
                    H += 360; // Asegurarse de que el valor de H esté en [0, 360]
                }
            }

            // Convertir H a escala [0, 255] para la imagen
            H = (H / 360.0) * 255.0;
            S = S * 255.0;
            V = V * 255.0;

            // Crear el color en el espacio HSV y asignarlo a la nueva imagen
            if(selector==1) color = new Color((int) H, (int) S, (int) V);
            if(selector==2) color = new Color((int) H, (int) H, (int) H);
            if(selector==3) color = new Color((int) S, (int) S, (int) S);
            if(selector==4) color = new Color((int) V, (int) V, (int) V);
            nuevaImagen[y][x] = color.getRGB();
        }
    }

    // Crear la nueva imagen con los valores convertidos a HSV
    JFrame padre = new JFrame();
    Image imagenNueva = padre.createImage(new MemoryImageSource(ancho, alto, imageBuffered.convertirInt2DAInt1D(nuevaImagen, ancho, alto), 0, ancho));

    System.out.println("RGB to HSV conversión completada");
    return imagenNueva;
}

    public Image[] obtenerImagenes(){
        Image[] imagenes = new Image[4];
        imagenes[0]=imagenHSV;
        BufferedImage bufferedHIS=imageBuffered.getBufferedImageColor(imagenHSV);
        imagenes[1]=this.convertirRGBtoHSV(2);
        imagenes[2]=this.convertirRGBtoHSV(3);
        imagenes[3]=this.convertirRGBtoHSV(4);
        return imagenes;
    }
    public void setImagen(Image imagen){
        this.img=imagen;
        this.buffered=imageBuffered.getBufferedImageColor(imagen);
        alto=buffered.getHeight();
        ancho=buffered.getWidth();
        initComponents();
    }
}


