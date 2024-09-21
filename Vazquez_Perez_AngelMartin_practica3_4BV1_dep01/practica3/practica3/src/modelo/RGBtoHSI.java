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
        imagenHSI=this.convertirRGBtoHIS();
    }
    public Image convertirRGBtoHIS(){
        int[][] nuevaImagen= new int [alto][ancho];
        double I,S,H;
        for(int y=0; y<alto ; y++){
            for(int x=0; x<ancho ; x ++){
                Color color=null;
                int pixel = buffered.getRGB(x,y);
                int rojo  = (pixel & 0x00ff0000) >> 16;
                int verde = (pixel & 0x0000ff00) >> 8;
                int azul  =  pixel & 0x000000ff;

                float min = Math.min(rojo, Math.min(verde, azul));
                I = (rojo + verde + azul) / 3.0f;
                S = 1 - ((min*3) / (rojo+verde+azul));

                H = (float) Math.toDegrees(Math.acos((0.5 * ((rojo - verde) + (rojo - azul))) /
                        (Math.sqrt((rojo - verde) * (rojo - verde) + (rojo - azul) * (verde - azul)))));
                if (azul > verde){
                    H = 360 - H;
                }
                if(H>255){
                    H=255;
                }
                color = new Color((int) H, (int) S * 255, (int) I );
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
    public Image[] obtenerImagenes(){
        Image[] imagenes = new Image[4];
        imagenes[0]=imagenHSI;
        BufferedImage bufferedHIS=imageBuffered.getBufferedImageColor(imagenHSI);
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

