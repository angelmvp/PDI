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
public class Logicas {
    private Image imagen1;
    private Image imagen2;
    private BufferedImage buffered1;
    private BufferedImage buffered2;
    private ImageBufferedImage imageBuffered;
    private int alto1;
    private int alto2;
    private int ancho1;
    private int ancho2;
    private int altoMin;
    private int anchoMin;
    private int altoMax;
    private int anchoMax;
    private int[][] matrizImagen1;
    private int[][] matrizImagen2;
    public Logicas (Image imagen1,Image imagen2){
        this.imagen1=imagen1;
        this.imagen2=imagen2;
        initComponents();
    }
    public void initComponents(){
        imageBuffered= new ImageBufferedImage();
        buffered1 = imageBuffered.getBufferedImage(imagen1);
        buffered2= imageBuffered.getBufferedImage(imagen2);
        alto1=buffered1.getHeight();
        ancho1=buffered1.getWidth();
        alto2=buffered2.getHeight();
        ancho2=buffered2.getWidth();
        matrizImagen1= imageBuffered.getMatrizImagen(buffered1, 5);
        matrizImagen2= imageBuffered.getMatrizImagen(buffered2, 5);
        altoMin = Math.min(alto1, alto2);
        anchoMin=Math.min(ancho1,ancho2);
        altoMax=Math.max(alto1, alto2);
        anchoMax=Math.max(ancho1, ancho2);
    }
    public Image ANDimagenes(){
        int [][] imagenInt = new int[altoMax][anchoMax];
        for(int y=0; y<altoMax; y++) {
            for (int x=0; x<anchoMax;x++){
                Color color;
                int nuevoGris;
                if(y>=alto1 || x>=ancho1){
                    nuevoGris=matrizImagen2[y][x];
                }else if (y>=alto2 || x>=ancho2){
                    nuevoGris=matrizImagen1[y][x];
                }else{
                    nuevoGris = (matrizImagen1[y][x] & matrizImagen2[y][x]);
                }
                color=new Color(nuevoGris,nuevoGris,nuevoGris);
                imagenInt[y][x] = color.getRGB();
            }
        }
        JFrame padre = new JFrame();
        Image nuevaImagen = padre.createImage(new MemoryImageSource(anchoMax,
                altoMax, imageBuffered.convertirInt2DAInt1D(imagenInt, anchoMax, altoMax),
                0, anchoMax));
        return nuevaImagen;
    }
    public Image ORimagenes(){
        int [][] imagenInt = new int[altoMax][anchoMax];
        for(int y=0; y<altoMax; y++) {
            for (int x=0; x<anchoMax;x++){
                Color color;
                int nuevoGris;
                if(y>=alto1 || x>=ancho1){
                    nuevoGris=matrizImagen2[y][x];
                }else if (y>=alto2 || x>=ancho2){
                    nuevoGris=matrizImagen1[y][x];
                }else{
                    nuevoGris = matrizImagen1[y][x] | matrizImagen2[y][x];
                }
                color=new Color(nuevoGris,nuevoGris,nuevoGris);
                imagenInt[y][x] = color.getRGB();
            }
        }
        JFrame padre = new JFrame();
        Image nuevaImagen = padre.createImage(new MemoryImageSource(anchoMax,
                altoMax, imageBuffered.convertirInt2DAInt1D(imagenInt, anchoMax, altoMax),
                0, anchoMax));
        return nuevaImagen;
    }
    public Image XORimagenes(){
        int [][] imagenInt = new int[altoMax][anchoMax];
        for(int y=0; y<altoMax; y++) {
            for (int x=0; x<anchoMax;x++){
                int min;
                int max;
                Color color;
                int nuevoGris;
                if(y>=alto1 || x>=ancho1){
                    nuevoGris=matrizImagen2[y][x];
                }else if (y>=alto2 || x>=ancho2){
                    nuevoGris=matrizImagen1[y][x];
                }else{
                    nuevoGris= matrizImagen1[y][x] ^ matrizImagen2[y][x];
                }
                color=new Color(nuevoGris,nuevoGris,nuevoGris);
                imagenInt[y][x] = color.getRGB();
            }
        }
        JFrame padre = new JFrame();
        Image nuevaImagen = padre.createImage(new MemoryImageSource(anchoMax,
                altoMax, imageBuffered.convertirInt2DAInt1D(imagenInt, anchoMax, altoMax),
                0, anchoMax));
        return nuevaImagen;
    }
    public Image NOTimagenes(){
        int [][] imagenInt = new int[altoMax][anchoMax];
        for(int y=0; y<altoMax; y++) {
            for (int x=0; x<anchoMax;x++){
                int min;
                int max;
                Color color;
                int nuevoGris;
                if(y>=alto1 || x>=ancho1){
                    nuevoGris=matrizImagen2[y][x];
                }else if (y>=alto2 || x>=ancho2){
                    nuevoGris=matrizImagen1[y][x];
                }else{
                    nuevoGris= ~matrizImagen1[y][x] & 0xff;
                }
                color=new Color(nuevoGris,nuevoGris,nuevoGris);
                imagenInt[y][x] = color.getRGB();
            }
        }
        JFrame padre = new JFrame();
        Image nuevaImagen = padre.createImage(new MemoryImageSource(anchoMax,
                altoMax, imageBuffered.convertirInt2DAInt1D(imagenInt, anchoMax, altoMax),
                0, anchoMax));
        return nuevaImagen;
    }
    public void setImagenes(Image imagen1,Image imagen2){
        this.imagen1=imagen1;
        this.imagen2=imagen2;
        initComponents();
    }
    private int validar(int gris){
        if(gris>255){
            return 255;
        }else if (gris<0){
            return 0;
        }
        return gris;
    }

    
}

