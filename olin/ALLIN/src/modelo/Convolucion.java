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
 * @author sdela
 */
public class Convolucion {
        private int alto;
    private int ancho;
    private final double MAXC = -99999999999.0;
    private final double MINC = 99999999999.0;
    private final double ESCALA = 255.0;
    private  double Dbias;
    private ImageBufferedImage imageBuffered;
    public Convolucion(double Dbias) {
        alto = 0;
        ancho = 0;
        this.Dbias = Dbias;
        imageBuffered= new ImageBufferedImage();
    }

    public int[][] calcularConvolucion(double[][] mask, int tam, int[][] imagen, int sel) {
        alto = imagen.length;
        ancho = imagen[0].length;
        try {
            double[] msk = new double[tam * tam];
            double[][] matCon = new double[alto][ancho];
            msk = aplanarMascara2Da1D(msk, mask, tam);
            double[] subImg;
            for(int y = 0; y < alto; y++) {
                for (int x = 0; x < ancho; x++) {
                    subImg = asignarImagen(imagen, x, y, tam);
                    matCon[y][x] = Dbias * convolucionar(msk, subImg, tam);
                }
            }
            matCon = dimensionarIntervalo(matCon, sel);
            for(int y = 0; y < alto; y++) {
                for(int x = 0; x < ancho; x++) {
                    imagen[y][x] = (int) Math.round(matCon[y][x]); // Convertir a int
                }
            }
        } catch (NegativeArraySizeException | ArrayIndexOutOfBoundsException | NullPointerException e) {
            System.out.println(" Error en calcularConvolucion()" + e);
        }
        return imagen;
    }

    public double convolucionar(double[] msk, double[] img, int tam) {
        double nuevoPixel = 0.0;
        int longitud = tam * tam;
        for(int i = 0; i < longitud; i++) {
            nuevoPixel += img[i] * msk[i];
        }
        return nuevoPixel;
    }

    public double[] aplanarMascara2Da1D(double[] msk, double[][] mask, int tam) {
        for(int n = 0, ct = 0; n < tam; n++) {
            for(int m = 0; m < tam; m++) {
                msk[ct++] = mask[n][m];
            }
        }
        return msk;
    }

    public double[] asignarImagen(int[][] imagen, int x, int y, int tam) {
        int ct = 0;
        double[] subImg = new double[tam * tam];
        for(int n = -tam / 2; n <= tam / 2; n++) {
            for(int m = -tam / 2; m <= tam / 2; m++, ct++) {
                if((y + n) < 0 || (x + m) < 0 || (y + n) >= alto || (x + m) >= ancho) {
                    subImg[ct] = 0;
                } else {
                    subImg[ct] = imagen[y + n][x + m];
                }
            }
        }
        return subImg;
    }

    public double[][] dimensionarIntervalo(double[][] matriz, int sel) {
        double max = MAXC;
        double min = MINC;
        double temp;
        try {
            for(int y = 0; y < alto; y++) {
                for(int x = 0; x < ancho; x++) {
                    if(matriz[y][x] > max) max = matriz[y][x];
                    if(matriz[y][x] < min) min = matriz[y][x];
                }
            }
            for(int y = 0; y < alto; y++) {
                for(int x = 0; x < ancho; x++) {
                    if(sel == 2) {
                        temp = ((matriz[y][x] - min) * (ESCALA / (max - min)));
                    } else {
                        temp = matriz[y][x];
                    }
                    if(temp > ESCALA) temp = ESCALA;
                    if(temp < 0.0) temp = 0.0;
                    matriz[y][x] = temp;
                }
            }
        } catch (NegativeArraySizeException | ArrayIndexOutOfBoundsException | NullPointerException e) {
            System.out.println(" Error en dimensionarIntervalo()" + e);
        }
        return matriz;
    }
    public Image getImageConvolucion(double[][] mask, int tam, int[][] imagen,
            int sel){
        int alto=imagen.length;
        int ancho=imagen[0].length;
            int[][] imagenInt= new int [alto][ancho];
            imagenInt = this.calcularConvolucion(mask, tam, imagen, sel);
                JFrame padre = new JFrame();
                Image imagenNueva;
                imagenNueva = padre.createImage(new MemoryImageSource(ancho,
                alto, imageBuffered.convertirInt2DAInt1D(imagenInt, ancho, alto),
                0, ancho));
        return imagenNueva;
    }
    public Image getImageConvolucionDouble(double[][] mask, int tam, int[][] imagen,
            int sel){
        int alto=imagen.length;
        int ancho=imagen[0].length;
            int[][] imagenInt= new int [alto][ancho];
            imagenInt = this.calcularConvolucion(mask, tam, imagen, sel);
            for(int y=0; y<alto; y++) {
                for(int x=0; x<ancho; x++) {
                    int pixel=imagenInt[y][x];
                    Color color = new Color(pixel,pixel,pixel);
                    imagenInt[y][x] = color.getRGB();
                }
            }
                JFrame padre = new JFrame();
                Image imagenNueva;
                imagenNueva = padre.createImage(new MemoryImageSource(ancho,
                alto, imageBuffered.convertirInt2DAInt1D(imagenInt, ancho, alto),
                0, ancho));
        return imagenNueva;
    }
    public void setBias(double bias){
        this.Dbias=bias;
    }
    public void imprimirValores(int umbral,int[][] imagenInt){
        System.out.println("imprimiendo valores");
        System.out.println(imagenInt.length);
        System.out.println(imagenInt[0].length);
        System.out.println(umbral);
        for(int y = 0; y < imagenInt.length; y++) {
                for(int x = 0; x < imagenInt[0].length; x++) {
                    if(imagenInt[y][x]>umbral){
                        System.out.println("pisicion" +  x + "y: " + y + "lugar" +imagenInt[y][x]);
                    }
                }
        }
        
                }
    
}
