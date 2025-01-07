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
public class ModificacionHistograma1 {
    private ImageBufferedImage imageBuffered;
    private Image imagen;
    private int[][] imagenInt;
    private int [][] imagenIntRojo;
    private int [][] imagenIntAzul;
    private int [][] imagenIntVerde;
    private int alto;
    private int ancho;
    private Histograma histograma;
    private Histograma histogramaAzul;
    private Histograma histogramaVerde;
    private Histograma histogramaRojo;
    private BufferedImage bufferedColor;
    private BufferedImage bufferedGrises;
    private double[]piac;
    private double[]piacRojo;
    private double[]piacVerde;
    private double[]piacAzul;
    public ModificacionHistograma1(Image imagen){
        this.imagen=imagen;
        initComponents();
    }
    public void initComponents(){
        imageBuffered=new ImageBufferedImage();
        bufferedColor= imageBuffered.getBufferedImageColor(imagen);
        imagenInt= imageBuffered.getMatrizImagen(bufferedColor, 5);
        imagenIntRojo= imageBuffered.getMatrizImagen(bufferedColor, 1);
        imagenIntVerde= imageBuffered.getMatrizImagen(bufferedColor, 2);
        imagenIntAzul= imageBuffered.getMatrizImagen(bufferedColor, 3);
        histograma= new Histograma(imagenInt);
        histogramaRojo= new Histograma(imagenIntRojo);
        histogramaVerde= new Histograma(imagenIntVerde);
        histogramaAzul= new Histograma(imagenIntAzul);
        histograma.ejecutarTodo();
        histogramaRojo.ejecutarTodo();
        histogramaVerde.ejecutarTodo();
        histogramaAzul.ejecutarTodo();
        alto=imagenInt.length;
        ancho=imagenInt[0].length;
        piac = histograma.getPiac();
        piacRojo = histogramaRojo.getPiac();
        piacVerde = histogramaVerde.getPiac();
        piacAzul = histogramaAzul.getPiac();
    }
    public Image aplicarHistogramaAcumuladoColor(){
        double[] piacAzul = this.piacAzul.clone();
        double[] piacVerde = this.piacVerde.clone();
        double[] piacRojo = this.piacRojo.clone();
        for (int i=0;i<256;i++){
            piacAzul[i]*=255;
            piacVerde[i]*=255;
            piacRojo[i]*=255;
            System.out.println(piacAzul[i]+" " + piacVerde[i]+ " "+ piacRojo[i] + "");
        }
        int[][] nuevaMatriz= new int [alto][ancho];
        for(int y=0; y<alto;y++){
            for(int x=0; x<ancho;x++){
                int pixel = bufferedColor.getRGB(x,y);
                int rojo  = (pixel & 0x00ff0000) >> 16;
                int verde = (pixel & 0x0000ff00) >> 8;
                int azul  =  pixel & 0x000000ff;
                int nuevoRojo=(int)piacRojo[rojo];
                int nuevoVerde=(int)piacVerde[verde];
                int nuevoAzul=(int)piacAzul[azul];
                nuevoRojo=validar(nuevoRojo);
                nuevoVerde=validar(nuevoVerde);
                nuevoAzul=validar(nuevoAzul);
                Color colorNuevo= new Color(nuevoRojo,nuevoVerde,nuevoAzul);
                nuevaMatriz[y][x]=colorNuevo.getRGB();
            }
        }        
        JFrame padre = new JFrame();
        Image imagen = padre.createImage(new MemoryImageSource(ancho,
                alto, imageBuffered.convertirInt2DAInt1D(nuevaMatriz, ancho, alto),
                0, ancho));
        return imagen;
    }
    public Image aplicarHistogramaAcumulado(){
        double[] piac = this.piac.clone();
        for (int i=0;i<256;i++){
            piac[i]*=255;
        }
        int[][] nuevaMatriz= new int [alto][ancho];
        for(int y=0; y<alto;y++){
            for(int x=0; x<ancho;x++){
                int pixel = imagenInt[y][x];
                int nuevoPixel=(int)piac[pixel];
                nuevoPixel=validar(nuevoPixel);
                Color colorNuevo= new Color(nuevoPixel,nuevoPixel,nuevoPixel);
                nuevaMatriz[y][x]=colorNuevo.getRGB();
            }
        }        
        JFrame padre = new JFrame();
        Image imagen = padre.createImage(new MemoryImageSource(ancho,
                alto, imageBuffered.convertirInt2DAInt1D(nuevaMatriz, ancho, alto),
                0, ancho));
        return imagen;
    }
    public Image desplazamiento( int n){
        int[][] nuevaMatriz= new int [alto][ancho];
        for(int y=0; y<alto;y++){
            for(int x=0; x<ancho;x++){
                int pixel = imagenInt[y][x];
                int nuevoPixel=pixel+n;
                nuevoPixel=validar(nuevoPixel);
                Color colorNuevo= new Color(nuevoPixel,nuevoPixel,nuevoPixel);
                nuevaMatriz[y][x]=colorNuevo.getRGB();
            }
        }
        JFrame padre = new JFrame();
        Image imagen = padre.createImage(new MemoryImageSource(ancho,
                alto, imageBuffered.convertirInt2DAInt1D(nuevaMatriz, ancho, alto),
                0, ancho));
        return imagen;
    }
    
    public int validar(int n){
        return Math.min(255, Math.max(0, n));
    }
    public void setImagen(Image imagen){
        this.imagen=imagen;
        initComponents();
    }
}
