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
public class RGBtoCMY {
    private double c;
    private double m;
    private double y;
    private Image img;
    private BufferedImage buffered;
    private ImageBufferedImage imageBuffered;
    private int alto;
    private int ancho;
    private Image imagenC;
    private Image imagenM;
    private Image imagenY;
    public RGBtoCMY(BufferedImage buffered){
        this.buffered=buffered;
        alto=buffered.getHeight();
        ancho=buffered.getWidth();
        imageBuffered = new ImageBufferedImage();
        img=imageBuffered.getImage(buffered,4); 
        initComponents();
    }
    public void initComponents(){
        imagenC=this.obtenerC();
        imagenM=this.obtenerM();
        imagenY=this.obtenerY();
    }
    public Image convertirRGBtoCMY(Image imagen){
        int[][] nuevaImagen= new int [alto][ancho];
        int C,M,Y;
        for(int y=0; y<alto ; y++){
            for(int x=0; x<ancho ; x ++){
                Color color=null;
                int pixel = buffered.getRGB(x,y);
                int rojo  = (pixel & 0x00ff0000) >> 16;
                int verde = (pixel & 0x0000ff00) >> 8;
                int azul  =  pixel & 0x000000ff;
                C = 255 - rojo;
                M = 255 - verde;
		Y = 255 - azul;
                color = new Color(C,M,Y);
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
    public Image obtenerC(){
        int[][] nuevaImagen= new int [alto][ancho];
        int C,M,Y;
        for(int y=0; y<alto ; y++){
            for(int x=0; x<ancho ; x ++){
                Color color=null;
                int pixel = buffered.getRGB(x,y);
                int rojo  = (pixel & 0x00ff0000) >> 16;
                int verde = (pixel & 0x0000ff00) >> 8;
                int azul  =  pixel & 0x000000ff;
                C = 255- rojo;
                M = 255 - verde;
		Y = 255 - azul ;
                color = new Color(0,M,Y);
                nuevaImagen[y][x]=color.getRGB();
            }
        }        
        JFrame padre = new JFrame();
        Image imagenNueva = padre.createImage(new MemoryImageSource(ancho,
                alto, imageBuffered.convertirInt2DAInt1D(nuevaImagen, ancho, alto),
                0, ancho));
        return imagenNueva;
    }
    public Image obtenerM(){
        int[][] nuevaImagen= new int [alto][ancho];
        int C,M,Y;
        for(int y=0; y<alto ; y++){
            for(int x=0; x<ancho ; x ++){
                Color color=null;
                int pixel = buffered.getRGB(x,y);
                int rojo  = (pixel & 0x00ff0000) >> 16;
                int verde = (pixel & 0x0000ff00) >> 8;
                int azul  =  pixel & 0x000000ff;
                C = 255- rojo;
                M = 255 - verde;
		Y = 255 - azul ;
                color = new Color(C,0,Y);
                nuevaImagen[y][x]=color.getRGB();
            }
        }        
        JFrame padre = new JFrame();
        Image imagenNueva = padre.createImage(new MemoryImageSource(ancho,
                alto, imageBuffered.convertirInt2DAInt1D(nuevaImagen, ancho, alto),
                0, ancho));
        return imagenNueva;
    }
    public Image obtenerY(){
        int[][] nuevaImagen= new int [alto][ancho];
        int C,M,Y;
        for(int y=0; y<alto ; y++){
            for(int x=0; x<ancho ; x ++){
                Color color=null;
                int pixel = buffered.getRGB(x,y);
                int rojo  = (pixel & 0x00ff0000) >> 16;
                int verde = (pixel & 0x0000ff00) >> 8;
                int azul  =  pixel & 0x000000ff;
                C = 255- rojo;
                M = 255 - verde ;
		Y = 255 - azul ;
                color = new Color(C,M,0);
                nuevaImagen[y][x]=color.getRGB();
            }
        }        
        JFrame padre = new JFrame();
        Image imagenNueva = padre.createImage(new MemoryImageSource(ancho,
                alto, imageBuffered.convertirInt2DAInt1D(nuevaImagen, ancho, alto),
                0, ancho));
        return imagenNueva;
    }
    public Image[] obtenerImagenes(){
        Image[] imagenes = new Image[3];
        imagenes[0]=imagenC;
        imagenes[1]=imagenM;
        imagenes[2]=imagenY;
        return imagenes;
    }
    public Image[] obtenerImagenesGrises(){
        Image[] imagenesGrises= new Image[3];
        imagenesGrises[0]= imageBuffered.getImage(imageBuffered.getBufferedImageColor(imagenC),5);
        imagenesGrises[1]= imageBuffered.getImage(buffered, 7);
        imagenesGrises[2]= imageBuffered.getImage(buffered, 8);
        return imagenesGrises;
    }
}
