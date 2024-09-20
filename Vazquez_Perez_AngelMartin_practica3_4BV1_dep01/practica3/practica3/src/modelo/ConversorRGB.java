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
public class ConversorRGB {
    private Image img;
    private BufferedImage buffered;
    private ImageBufferedImage imageBuffered;
    private int alto;
    private int ancho;
    private  RGBtoColores conversionRGBtoColores;
    private RGBtoCMY conversionRGBtoCMY;
    
    public ConversorRGB(BufferedImage buffered){
        this.buffered=buffered;
        alto=buffered.getHeight();
        ancho=buffered.getWidth();
        imageBuffered = new ImageBufferedImage();
        img=imageBuffered.getImage(buffered,4);
        initComponents();
    }
    public void initComponents(){
       conversionRGBtoColores = new RGBtoColores(buffered);
       conversionRGBtoCMY= new RGBtoCMY(buffered);
    }
    
    public Image toCMY(){
        RGBtoCMY conversionRGBtoCMY = new RGBtoCMY(buffered);
        System.out.println("de RGB a CMY");
        return conversionRGBtoCMY.convertirRGBtoCMY(img);
    }
    public Image[] toCMYCanales(){
        return conversionRGBtoCMY.obtenerImagenes();
    }
    public Image[] toCMYCanalesGris(){
        return conversionRGBtoCMY.obtenerImagenesGrises();
    }
    public Image[] toHIS(){
     Image[] nueva={img,img,img};
     return nueva;
    }
    public Image[] toHSV(){
     Image[] nueva={img,img,img};
     return nueva;
    }
    public Image[] toYIQ(){
     Image[] nueva={img,img,img};
     return nueva;
    }
    public Image[] toLAB(){
     Image[] nueva={img,img,img};
     return nueva;
    }
    public Image[] toRGBCanales(){
     System.out.println("de RGB a canales colores");
     return conversionRGBtoColores.obtener3Canales();
    }
    public Image[] toRGBCanalesGris(){
     System.out.println("de RGB a canales GRISES");
     return conversionRGBtoColores.obtener3CanalesGrises();
    }
    public void setImagen(Image imagen){
        img=imagen;
        buffered=imageBuffered.getBufferedImageColor(img);
        alto=buffered.getHeight();
        ancho=buffered.getWidth();
        conversionRGBtoColores.setImagen(imagen);
    }
   
}
