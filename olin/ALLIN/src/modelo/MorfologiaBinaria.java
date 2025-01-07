/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import java.util.Arrays;
import javax.swing.JFrame;

/**
 *
 * @author jhona
 */
public class MorfologiaBinaria {
    private int[][] imagenInt;
    private int [][] copiaMatriz;
    private int alto;
    private int ancho;
    private ImageBufferedImage imageBuffered;
    private int tamES;
    private String tipoES;
    private int[][] nuevaMatriz;
    private ElementoEstructurante ES;
    public MorfologiaBinaria(int[][] imagenInt,int tamMascara){
        this.imagenInt=imagenInt;
        this.tamES=tamMascara;
        imageBuffered = new ImageBufferedImage();
        tipoES="cruz";
        ES= new ElementoEstructurante(imagenInt,tamES,tipoES);
        initComponents();
    }
    public void initComponents(){
        alto=imagenInt.length;
        ancho=imagenInt[0].length;
        copiaMatriz=imagenInt.clone();
       
    }
    public Image aplicarDilatacion() {
        nuevaMatriz= new int[alto][ancho];
        for(int y=0; y<alto;y++){
            for(int x=0;x<ancho;x++){
              int pixel = dilatarPixel(x,y);;
              nuevaMatriz[y][x]= new Color(pixel,pixel,pixel).getRGB();
            }
        }
        Image nuevaImagen = generarImagenDesdeMatriz(nuevaMatriz);
        return nuevaImagen;
    }
    public int dilatarPixel(int x,int y){
        int[] ventana=ES.obtenerVentanaES(x,y);
        for(int i=0;i<ventana.length;i++){
            if (ventana[i]==255){
                return 255;
            }
        }
        return 0;
    }

    public Image aplicarErosion() {
        nuevaMatriz=new int[alto][ancho];
        for(int y=0; y<alto;y++){
            for(int x=0;x<ancho;x++){
              int pixel = erosionarPixel(x,y);;
              nuevaMatriz[y][x]= new Color(pixel,pixel,pixel).getRGB();
            }
        }
        Image nuevaImagen = generarImagenDesdeMatriz(nuevaMatriz);
        return nuevaImagen;
    }
    public int erosionarPixel(int x,int y){
        int[] ventana=ES.obtenerVentanaES(x,y);
        for(int i=0;i<ventana.length;i++){
            if(ventana[i]==0){
                return 0;
            }
        }
        return 255;
    }  
    
    public Image aplicarClausura(){
        Image dilatacion = this.aplicarDilatacion();
        MorfologiaBinaria nuevaMorfo = new MorfologiaBinaria(imageBuffered.getMatrizImagen(imageBuffered.getBufferedImageColor(dilatacion),5),3);
        Image nuevaImg = nuevaMorfo.aplicarErosion();
        return nuevaImg;
    }
    public Image aplicarApertura(){
        Image erosion = this.aplicarErosion();
        MorfologiaBinaria nuevaMorfo = new MorfologiaBinaria(imageBuffered.getMatrizImagen(imageBuffered.getBufferedImageColor(erosion),5),3);
        Image nuevaImg = nuevaMorfo.aplicarDilatacion();
        return nuevaImg;
    }
    

   

    private int validar(int n){
        return Math.min(255, Math.max(0, n));
    }
    private Image generarImagenDesdeMatriz(int[][] matriz) {
        JFrame padre = new JFrame();
        return padre.createImage(new MemoryImageSource(ancho, alto, 
                imageBuffered.convertirInt2DAInt1D(matriz, ancho, alto), 0, ancho));
    }
    public void setTipoES(String tipoMascara){
        this.tipoES=tipoMascara;
    }
    public void setTamES(int tam){
        this.tamES=tam;
    }
    public void setMatrizImagen(int[][] imagenInt){
        this.imagenInt=imagenInt;
        initComponents();
    }
}
