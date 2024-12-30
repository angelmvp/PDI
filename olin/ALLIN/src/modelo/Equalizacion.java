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
public class Equalizacion {
    private Histograma histograma;
    private  double[] piac;
    private Image imagen;
    private int ancho;
    private int alto;
    private int [][] imagenInt;
    private ImageBufferedImage imageBuffered;
    public Equalizacion(Image imagen,double [] piac){
        this.imagen=imagen;
        this.piac=piac.clone();
        initComponents();
    }
    public void initComponents(){
        imageBuffered = new ImageBufferedImage();
        imagenInt = imageBuffered.getMatrizImagen(imageBuffered.getBufferedImage(imagen),5);
        ancho=imagenInt.length;
        alto=imagenInt[0].length;
    }
    public Image aplicarEcualizacion(int opcion, int fmin, int fmax, double alpha, double gamma) {
        int[][] nuevaMatriz = new int[alto][ancho];
        double[] lut = obtenerLut(opcion, fmin, fmax, alpha, gamma); 
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int intensidad = imagenInt[y][x];
                int nuevoValor = (int) lut[intensidad];
                nuevaMatriz[y][x] = new Color(nuevoValor, nuevoValor, nuevoValor).getRGB();
            }
        }
        JFrame padre = new JFrame();
        Image imagen = padre.createImage(new MemoryImageSource(ancho,
                alto, imageBuffered.convertirInt2DAInt1D(nuevaMatriz, ancho, alto),
                0, ancho));
        return imagen;
    }
    public double[] obtenerLut(int opcion, int fmin, int fmax, double alpha, double gamma) {
        double[] lut = new double[256];

        for (int i = 0; i < 256; i++) {
            double probabilidadAcumulada = piac[i];
            switch (opcion) {
                case 1:
                    lut[i] = ((fmax - fmin) * piac[i]) + fmin;
                    break;
                case 2:
                    lut[i] = fmin - (Math.log(1-piac[i])/alpha);
                    break;
                case 3:
                    lut[i] = fmin + Math.sqrt(2 *alpha* alpha * Math.log(1 / (1 - piac[i])));
                    break;
                case 4:
                    lut[i] = Math.pow((((Math.pow(fmax,(1/gamma))-Math.pow(fmin,(1/gamma)))*piac[i])+Math.pow(fmin, (1/gamma))),gamma);
                    break;
                case 5:
                    lut[i] = fmin *(Math.pow(fmax/fmin, piac[i]));
                    break;
                default:
                    System.out.println("no hay mas");
            }
            lut[i] = validar((int) lut[i]) ;
        }

        return lut;
    }

    public int validar(int n){
        return Math.min(255, Math.max(0, n));
    }
    public void setImagen(Image nuevaImagen,double[] nuevoPiac){
        this.imagen=nuevaImagen;
        this.piac=nuevoPiac.clone();
        initComponents();
    }
}
