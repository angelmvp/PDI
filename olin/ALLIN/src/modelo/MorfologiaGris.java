/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import javax.swing.JFrame;

/**
 *
 * @author jhona
 */
public class MorfologiaGris {
    private int[][] imagenInt;
    private int [][] copiaMatriz;
    private int alto;
    private int ancho;
    private ImageBufferedImage imageBuffered;
    private int tamES;
    private String tipoES;
    private int[][] nuevaMatriz;
    private ElementoEstructurante ES;
    public MorfologiaGris(int[][] imagenInt,int tamES){
        this.imagenInt=imagenInt;
        this.tamES=tamES;
        imageBuffered = new ImageBufferedImage();
        tipoES="cruz";
        ES = new ElementoEstructurante(imagenInt,tamES,tipoES);
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
              int pixel=obtenerMaximo(x,y); 
              nuevaMatriz[y][x]= new Color(pixel,pixel,pixel).getRGB();
            }
        }
        Image nuevaImagen = generarImagenDesdeMatriz(nuevaMatriz);
        return nuevaImagen;
    }
    public int obtenerMaximo(int x,int y){
        int[] ventana=ES.obtenerVentanaES(x,y);
        int max=0;
        for(int i=0;i<ventana.length;i++){
            if (ventana[i]>max){
                max=ventana[i];
            }
        }
        return max;
    }
    public Image aplicarErosion() {
        nuevaMatriz=new int[alto][ancho];
        for(int y=0; y<alto;y++){
            for(int x=0;x<ancho;x++){
              int pixel=obtenerMinimo(x,y); 
              nuevaMatriz[y][x]= new Color(pixel,pixel,pixel).getRGB();
            }
        }
        Image nuevaImagen = generarImagenDesdeMatriz(nuevaMatriz);
        return nuevaImagen;
    }
    public int obtenerMinimo(int x,int y){
        int[] ventana=ES.obtenerVentanaES(x,y);
        int min=255;
        for(int i=0;i<ventana.length;i++){
            if (ventana[i]<min){
                min=ventana[i];
            }
        }
        return min;
    }   
    public Image aplicarClausura(){
        Image dilatacion = this.aplicarDilatacion();
        MorfologiaGris nuevoes = new MorfologiaGris(imageBuffered.getMatrizImagen(imageBuffered.getBufferedImageColor(dilatacion),5),3);
        Image nuevaImg = nuevoes.aplicarErosion();
        return nuevaImg;
    }
    
    public Image aplicarApertura(){
        Image erosion = this.aplicarErosion();
        MorfologiaGris nuevoes = new MorfologiaGris(imageBuffered.getMatrizImagen(imageBuffered.getBufferedImageColor(erosion),5),3);
        Image nuevaImg = nuevoes.aplicarDilatacion();
        return nuevaImg;
    }
    private Image generarImagenDesdeMatriz(int[][] matriz) {
        JFrame padre = new JFrame();
        return padre.createImage(new MemoryImageSource(ancho, alto, 
                imageBuffered.convertirInt2DAInt1D(matriz, ancho, alto), 0, ancho));
    }
    public void setTipoES(String tipoES){
        this.tipoES=tipoES;
        ES.setTipoES(tipoES);
    }
    public void setTamES(int tam){
        this.tamES=tam;
        ES.setTamES(tam);
    }
    public void setMatrizImagen(int[][] imagenInt){
        this.imagenInt=imagenInt;
        ES.setMatrizImagen(imagenInt);
        initComponents();
    }
}
