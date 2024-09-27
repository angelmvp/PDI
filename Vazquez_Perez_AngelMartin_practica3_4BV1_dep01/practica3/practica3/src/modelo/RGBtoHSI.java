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
public class RGBtoHSI {
    private Image img;
    private BufferedImage buffered;
    private ImageBufferedImage imageBuffered;
    private int alto;
    private int ancho;
    private Image imagenHSI;
    public RGBtoHSI(BufferedImage buffered){
        this.buffered=buffered;
        alto=buffered.getHeight();
        ancho=buffered.getWidth();
        imageBuffered = new ImageBufferedImage();
        img=imageBuffered.getImage(buffered,4); 
        initComponents();
    }
    public void initComponents(){
        imagenHSI=this.convertirRGBtoHIS(1);
    }
    public Image convertirRGBtoHIS(int selector){
        int[][] nuevaImagen= new int [alto][ancho];
        double I,S,H;
        for(int y=0; y<alto ; y++){
            for(int x=0; x<ancho ; x ++){
                Color color=null;
                int pixel = buffered.getRGB(x, y);
                        int rojo = (pixel & 0x00ff0000) >> 16;
                        int verde = (pixel & 0x0000ff00) >> 8;
                        int azul = pixel & 0x000000ff;

                float min = Math.min(rojo, Math.min(verde, azul));
                I = (rojo + verde + azul) / 3.0;

                // Cálculo de la Saturación
                if ((rojo + verde + azul) != 0) {
                    S = 1 - (3 * min / (rojo + verde + azul));
                } else {
                    S = 0;
                }

                // Cálculo del Tono (Hue)
                double numerador = 0.5 * ((rojo - verde) + (rojo - azul));
                double denominador = Math.sqrt((rojo - verde) * (rojo - verde) + (rojo - azul) * (verde - azul));

                if (denominador == 0) {
                    H = 0;
                } else {
                    H = Math.acos(numerador / denominador);
                    H = Math.toDegrees(H);  // Convertir a grados
                    if (azul > verde) {
                        H = 360 - H;
                    }
                }

                // Normalización de valores
                H = H / 360.0 * 255.0;
                S = S * 255;
                I = I * 255;

                H = Math.min(255, Math.max(0, H));
                S = Math.min(255, Math.max(0, S));
                I = Math.min(255, Math.max(0, I));

                if (selector == 1) color = new Color((int) H, (int) S, (int) I);
                if (selector == 3) color = new Color((int) H, (int) H, (int) H); 
                if (selector == 2) color = new Color((int) S, (int) S, (int) S); 
                if (selector == 4) color = new Color((int) I, (int) I, (int) I); 

                nuevaImagen[y][x] = color.getRGB();
            }
        }

        JFrame padre = new JFrame();
        Image imagenNueva = padre.createImage(new MemoryImageSource(ancho,
                alto, imageBuffered.convertirInt2DAInt1D(nuevaImagen, ancho, alto),
                0, ancho));
        return imagenNueva;
    }
    public Image[] obtenerImagenes(){
        Image[] imagenes = new Image[4];
        imagenes[0]=imagenHSI;
        imagenes[1]=this.convertirRGBtoHIS(2);
        imagenes[2]=this.convertirRGBtoHIS(3);
        imagenes[3]=this.convertirRGBtoHIS(4);
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

