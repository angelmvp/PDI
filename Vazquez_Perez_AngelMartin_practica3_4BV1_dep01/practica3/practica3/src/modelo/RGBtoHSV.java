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
        imagenHSV=this.convertirRGBtoHSV();
    }
    public Image convertirRGBtoHSV(){
        int[][] nuevaImagen= new int [alto][ancho];
        double H,S,V;
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int pixel = buffered.getRGB(x, y);
                int rojo = (pixel & 0x00ff0000) >> 16;
                int verde = (pixel & 0x0000ff00) >> 8;
                int azul = pixel & 0x000000ff;

                // Obtener el valor mínimo y máximo
                float min = Math.min(rojo, Math.min(verde, azul));
                float Max = Math.max(rojo, Math.max(verde, azul));

                // Cálculo del valor (V)
                V = Max / 255.0;

                // Cálculo de la saturación (S)
                S = (Max > 0) ? (1 - (min / Max)) : 0;

                // Cálculo del tono (H)
                double numerador = (rojo - 0.5 * verde - 0.5 * azul);
                double denominador = Math.sqrt(rojo * rojo + verde * verde + azul * azul - rojo * verde - rojo * azul - verde * azul);
                
                if (denominador == 0) {
                    H = 0; // Evitar divisiones por cero
                } else {
                    H = Math.acos(numerador / denominador);
                    if (verde < azul) {
                        H = 360 - H;
                    }
                }
                // Convertir H a grados y limitar a 360 grados
                H = Math.toDegrees(H);
                if (H < 0) H += 360;
                if (H > 360) H = 360;

                // Normalización a la escala de 0 a 255
                H = (H / 360.0) * 255.0;
                S = S * 255.0;
                V = V * 255.0;

                // Crear un nuevo color basado en los valores H, S, V
                Color color = new Color((int) H, (int) S, (int) V);
                nuevaImagen[y][x]= color.getRGB();
            }
        }
        JFrame padre = new JFrame();
        Image imagenNueva = padre.createImage(new MemoryImageSource(ancho,
                alto, imageBuffered.convertirInt2DAInt1D(nuevaImagen, ancho, alto),
                0, ancho));
        System.out.println("se retorno la imagen");
        return imagenNueva;
    }
    public Image[] obtenerImagenes(){
        Image[] imagenes = new Image[4];
        imagenes[0]=imagenHSV;
        BufferedImage bufferedHIS=imageBuffered.getBufferedImageColor(imagenHSV);
        imagenes[1]=imageBuffered.getImage(bufferedHIS, 6);
        imagenes[2]=imageBuffered.getImage(bufferedHIS, 7);
        imagenes[3]=imageBuffered.getImage(bufferedHIS, 8);
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


