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
public class RGBtoYIQ {
    private Image img;
    private BufferedImage buffered;
    private ImageBufferedImage imageBuffered;
    private int alto;
    private int ancho;
    private Image imagenYIQ;
    public RGBtoYIQ(BufferedImage buffered){
        this.buffered=buffered;
        alto=buffered.getHeight();
        ancho=buffered.getWidth();
        imageBuffered = new ImageBufferedImage();
        img=imageBuffered.getImage(buffered,4); 
        initComponents();
    }
    public void initComponents(){
        imagenYIQ=this.convertirRGBtoYIQ();
    }
    public Image convertirRGBtoYIQ(){
        int[][] nuevaImagen= new int [alto][ancho];
        int Y,I,Q;
        for(int y=0; y<alto ; y++){
            for(int x=0; x<ancho ; x ++){
                Color color=null;
                int pixel = buffered.getRGB(x,y);
                int rojo  = (pixel & 0x00ff0000) >> 16;
                int verde = (pixel & 0x0000ff00) >> 8;
                int azul  =  pixel & 0x000000ff;
                Y = (int) (.299*rojo + .587*verde + .114*azul);
                I =(int) (.596*rojo - .274*verde - .322*azul);
		Q =(int) (.211*rojo - .523*verde + .312*azul);
                I+=128;
                Q+=128;
                Y=validar(Y);
                Q=validar(Q);
                I=validar(I);
                color = new Color(Y,I,Q);
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
        imagenes[0]=imagenYIQ;
        BufferedImage bufferedYIQ=imageBuffered.getBufferedImageColor(imagenYIQ);
        imagenes[1]=imageBuffered.getImage(bufferedYIQ, 6);
        imagenes[2]=imageBuffered.getImage(bufferedYIQ, 7);
        imagenes[3]=imageBuffered.getImage(bufferedYIQ, 8);
        return imagenes;
    }
    public void setImagen(Image imagen){
        this.img=imagen;
        this.buffered=imageBuffered.getBufferedImageColor(imagen);
        alto=buffered.getHeight();
        ancho=buffered.getWidth();
        initComponents();
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
