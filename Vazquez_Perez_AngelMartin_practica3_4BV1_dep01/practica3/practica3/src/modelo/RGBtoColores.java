/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author jhona
 */
public class RGBtoColores {
    private Image img;
    private BufferedImage buffered;
    private ImageBufferedImage imageBuffered;
    private int alto;
    private int ancho;
    private Image imagenRojo;
    private Image imagenVerde;
    private Image imagenAzul;
    
    public RGBtoColores(BufferedImage buffered){
        this.buffered=buffered;
        alto=buffered.getHeight();
        ancho=buffered.getWidth();
        imageBuffered = new ImageBufferedImage();
        img=imageBuffered.getImage(buffered,4);
        initComponents();
    }
    public void initComponents(){
        imagenRojo= this.obtenerCanalRojo();
        imagenVerde=this.obtenerCanalVerde();
        imagenAzul=this.obtenerCanalAzul();
    }
    public Image obtenerCanalRojo(){
        return imageBuffered.getImage(buffered, 1);
    }
    public Image obtenerCanalVerde(){
        return imageBuffered.getImage(buffered, 2);
    }
    public Image obtenerCanalAzul(){
        return imageBuffered.getImage(buffered, 3);
    }
    
    public Image[]obtener3Canales(){
        Image[] imagenes = new Image[3];
        imagenes[0]=imagenRojo;
        imagenes[1]=imagenVerde;
        imagenes[2]=imagenAzul;
        return imagenes;
    }
    public Image[] obtener3CanalesGrises(){
        Image[] imagenes = new Image[3];
        imagenes[0]=imageBuffered.getImage(buffered, 6);
        imagenes[1]=imageBuffered.getImage(buffered, 7);
        imagenes[2]=imageBuffered.getImage(buffered, 8);       
        return imagenes;        
    }
    public void setImagen(Image imagen){
        this.img=imagen;
        this.buffered=imageBuffered.getBufferedImageColor(img);
        alto=buffered.getHeight();
        ancho=buffered.getWidth();
        initComponents();
    }
}
