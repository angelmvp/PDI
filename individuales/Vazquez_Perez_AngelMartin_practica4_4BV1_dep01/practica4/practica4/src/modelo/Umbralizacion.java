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
public class Umbralizacion {
    private Image imagen;
    private BufferedImage buffered;
    private ImageBufferedImage imageBuffered;
    private int alto;
    private int ancho;
    private int [][] matriz;
    private int [][] matrizUmbralizada;
    public Umbralizacion(BufferedImage buffered){
        this.buffered= buffered;
        initComponents();
    }
    public void initComponents(){
        imageBuffered=new ImageBufferedImage();
        ancho=buffered.getWidth();
        alto=buffered.getHeight();
        imagen=imageBuffered.getImage(buffered, 5);
        actualizarMatriz();
    }
public Image ajustarUmbral(int umbral) {
        int[][] imagenInt= new int [alto][ancho];
        for(int y=0; y<alto ; y++){
            for(int x=0; x<ancho ; x ++){
                Color color=null;
                int gris = matriz[y][x];
                int nuevoGris;
                if(gris<=umbral){
                    nuevoGris=0;
                }else{
                    nuevoGris=255;                    
                }
                color=new Color(nuevoGris,nuevoGris,nuevoGris);
                imagenInt[y][x]=color.getRGB();
                matrizUmbralizada[y][x]=nuevoGris;
            }
        }
        JFrame padre = new JFrame();
        Image imagenNueva = padre.createImage(new MemoryImageSource(ancho,
                alto, imageBuffered.convertirInt2DAInt1D(imagenInt, ancho, alto),
                0, ancho));
        System.out.println("se retorno la imagen");
        return imagenNueva;
    }

    public Image ajustarUmbral(int umbral1, int umbral2) {
        int[][] imagenInt = new int[alto][ancho];
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                Color color=null;
                int gris = matriz[y][x];
                int nuevoGris;
                if (gris <= umbral1) {
                    nuevoGris=0;
                } else if (gris <= umbral2) {
                    nuevoGris=128;
                } else {
                    nuevoGris=255;
                }
                color=new Color(nuevoGris,nuevoGris,nuevoGris);
                imagenInt[y][x]=color.getRGB();
                matrizUmbralizada[y][x]=nuevoGris;
            }
        }
        JFrame padre = new JFrame();
        Image nuevaImagen = padre.createImage(new MemoryImageSource(ancho,
                alto, imageBuffered.convertirInt2DAInt1D(imagenInt, ancho, alto),
                0, ancho));
        return nuevaImagen;
    }

    public Image ajustarUmbral(int umbral1, int umbral2, int umbral3) {
        int[][] imagenInt = new int[alto][ancho];
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                Color color=null;
                int gris = matriz[y][x];
                int nuevoGris;
                if (gris <= umbral1) {
                    nuevoGris=0;
                } else if (gris <= umbral2) {
                    nuevoGris=85;
                } else if(gris<=umbral3) {
                    nuevoGris=170;
                }else{
                    nuevoGris=255;
                }
                color=new Color(nuevoGris,nuevoGris,nuevoGris);
                imagenInt[y][x]=color.getRGB();
                matrizUmbralizada[y][x]=nuevoGris;
            }
        }
        JFrame padre = new JFrame();
        Image nuevaimagen = padre.createImage(new MemoryImageSource(ancho,
                alto, imageBuffered.convertirInt2DAInt1D(imagenInt, ancho, alto),
                0, ancho));
        return nuevaimagen;
    }

    public Image negativoImagen() {
        int[][] imagenInt = new int[alto][ancho];
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                Color color=null;
                int gris =  matrizUmbralizada[y][x];
                int grisNegativo= 255-gris;
                color = new Color(grisNegativo,grisNegativo,grisNegativo);
                imagenInt[y][x]=color.getRGB();
            }
        }
        JFrame padre = new JFrame();
        Image nuevaimagen = padre.createImage(new MemoryImageSource(ancho,
                alto, imageBuffered.convertirInt2DAInt1D(imagenInt, ancho, alto),
                0, ancho));
        return nuevaimagen;
    }
    public void actualizarMatriz(){
        matrizUmbralizada= new int[alto][ancho];
        matriz = new int[alto][ancho] ;
         for(int y=0; y < alto; y++){
             for (int x=0; x<ancho; x++){
                Color color=null;
                int pixel = buffered.getRGB(x, y);
                int rojo  = (pixel & 0x00ff0000) >> 16;
                int verde = (pixel & 0x0000ff00) >> 8;
                int azul  =  pixel & 0x000000ff;
                int gris = (rojo+verde+azul)/3;
                matriz[y][x]=gris;
                matrizUmbralizada[y][x]= gris;
             }
         }
    }
    public void setImagen(Image imagen){
        this.imagen=imagen;
        this.buffered=imageBuffered.getBufferedImage(imagen);
        alto=buffered.getHeight();
        ancho=buffered.getWidth();
        actualizarMatriz();
    }
}
