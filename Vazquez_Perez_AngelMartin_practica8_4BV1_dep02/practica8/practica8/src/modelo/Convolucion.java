    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.awt.Image;
import java.awt.image.MemoryImageSource;
import javax.swing.JFrame;

/**
 *
 * @author sdela
 */
public class Convolucion {
    /**
     * Altura de la imagen en pixeles
     */
    private int alto;
    /**
     * Ancho de la imagen en pixeles
     */
    private int ancho;
    /**
     * Valor minimo de inicio para encontrar un maximo.
     */
    private final double MAXC = -99999999999.0;
    /**
     * Valor maximo de inicio para encontrar un minimo.
     */
    private final double MINC = 99999999999.0;
    /**
     * Escala de niveles de gris.
     */
    private final double ESCALA = 255.0;
    /**
     * Bias de la mascara con que se realiza el proceso de convolucion.
     */
    private double Dbias;
    private ImageBufferedImage imageBuffered;
    public Convolucion(double Dbias) {
        alto = 0;
        ancho = 0;
        this.Dbias = Dbias;
        imageBuffered= new ImageBufferedImage();
    }
    /**
     * Se hace el proceso de Convolucion, recibe la mascara, el tamanio de la
     * misma y la matriz de la imagen con la que se realizara el proceso de
     * Convolucion, devuelve la imagen convolucionada.
     *
     * @param mask la mascara que se aplica para convolucion es entera
     * @param tam el tamanio de la mascara
     * @param imagen la imagen que se emplea en la convolucion
     * @param sel no trabaja
     *
     * @return devuelve la imagen convolucionada en un arreglo entero de dos
     * dimensiones
     */
        public int[][] calcularConvolucion(int[][] mask, int tam, int[][] imagen,
            int sel) {
        alto = imagen.length;
        ancho = imagen[0].length;
            System.out.println("Dbias en entero" + Dbias );
        try {
            int[] msk = new int[tam * tam];
            double[][] matCon = new double[alto][ancho];
            msk = aplanarMascara2Da1D(msk, mask, tam);
            int[] subImg;
            for(int y=0; y<alto; y++) {
                if (matCon[y] == null) {
                    matCon[y] = new double[ancho];
                    }
                for (int x=0; x<ancho; x++) {
                    subImg = asignarImagen(imagen, x, y, tam);
                    matCon[y][x] = Dbias * convolucionar(msk, subImg, tam);
                    }
                }
            matCon = dimensionarIntervalo(matCon, sel);
            for(int y=0; y<alto; y++) {
                for(int x=0; x<ancho; x++) {
                    imagen[y][x] = (int) matCon[y][x];
                    }
                }
        } catch(NegativeArraySizeException e) {
            System.out.println(" Error en calcularConvolucion()"
                    + " tam es negativo " + e);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(" Error en calcularConvolucion()"
                    + " indice fuera de rango " + e);
        } catch (NullPointerException e) {
            System.out.println(" Error en calcularConvolucion()" + e);
        }
        return imagen;
    }

    public int[][] calcularConvolucion(double[][] mask, int tam, int[][] imagen,
            int sel) {
        alto = imagen.length;   
        ancho = imagen[0].length;
            System.out.println("DBias en double convolucion : " + Dbias);
        try {
            double[] msk = new double[tam * tam];
            double[][] matCon = new double[alto][ancho];
            msk = aplanarMascara2Da1D(msk, mask, tam);
            int[] subImg;
            for(int y=0; y<alto; y++) {
                if (matCon[y] == null) {
                    matCon[y] = new double[ancho];
                    }
                for (int x=0; x<ancho; x++) {
                    subImg = asignarImagen(imagen, x, y, tam);
                    
                    matCon[y][x] = Dbias * convolucionar(msk, subImg, tam);
                    }
                }
            matCon = dimensionarIntervalo(matCon, sel);
            for(int y=0; y<alto; y++) {
                for(int x=0; x<ancho; x++) {
                    imagen[y][x] = (int) matCon[y][x];
                    }
                }
        } catch(NegativeArraySizeException e) {
            System.out.println(" Error en calcularConvolucion()"
                    + " tam es negativo " + e);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(" Error en calcularConvolucion()"
                    + " indice fuera de rango " + e);
        } catch (NullPointerException e) {
            System.out.println(" Error en calcularConvolucion()" + e);
        }
        return imagen;
    }
    /**
     * Convoluciona el pedazo de imagen, con la mascara de tipo entero, ambas
     * del mismo tamanio.
     *
     * @param msk la mascara de convolucion, es entera
     * @param img la subimagen que se convoluciona
     * @param tam el tamanio de la mascara
     *
     * @return devuelve el resultado de la convolucion
     */
    public double convolucionar(int[] msk, int[] img, int tam) {
        double nuevoPixel = 0.0;
        int longitud = tam*tam;
        for(int y=0; y<longitud; y++) {
            nuevoPixel += (img[y] * msk[y]);
            }
        return nuevoPixel;
    }
    public double convolucionar(double[] msk, int[] img, int tam) {
        double nuevoPixel = 0.0;
        int longitud = tam*tam;
        for(int y=0; y<longitud; y++) {
            nuevoPixel += (img[y] * msk[y]);
            }
        return nuevoPixel;
    }
    /**
     * Cambia una mascara int [][] (2D) a una int [] (1D).
     *
     * @param msk la mascara en una dimension
     * @param mask la mascara en dos dimensiones
     * @param tam el tamanio de la mascara
     *
     * @return devuelve la mascara en una dimension entera
     */
    public int[] aplanarMascara2Da1D(int[] msk, int[][] mask, int tam)
            throws NegativeArraySizeException, ArrayIndexOutOfBoundsException,
            NullPointerException {
        // cambia la mascara de 2 a 1 dimension
        for(int n=0, ct=0; n<tam; n++) {
            for(int m=0; m<tam; m++) {
                msk[ct] = mask[n][m];
                ct++;
                }
            }
        return msk;
    }
    public double[] aplanarMascara2Da1D(double[] msk, double[][] mask, int tam)
            throws NegativeArraySizeException, ArrayIndexOutOfBoundsException,
            NullPointerException {
        // cambia la mascara de 2 a 1 dimension
        for(int n=0, ct=0; n<tam; n++) {
            for(int m=0; m<tam; m++) {
                msk[ct] = mask[n][m];
                ct++;
                }
            }
        return msk;
    }
    /**
     * Toma un pedazo de la imagen del tamanio de la mascara, segun donde vaya
     * moviendose la mascara, es de donde se toma la imagen:
     *
     * @param imagen la imagen de donde se toma la subimagen a convolucionar
     * @param x la posicion en X del pixel central de la subimagen
     * @param y la posicion en Y del pixel central de la subimagen
     * @param tam el tamanio de la subimagen en pixeles
     *
     * @return devuelve la subimagen en un arreglo unidimensional de enteros
     */
    public int[] asignarImagen(int[][] imagen, int x, int y, int tam) {
        int ct = 0;
        int[] subImg = null;
        try {
            subImg = new int[tam * tam];
            for(int n=-tam/2; n<=tam/2; n++) {
                for(int m=-tam/2; m<=tam/2; m++, ct++) {
                    if((y + n)<0 && (x + m)< 0) {
                        subImg[ct] = imagen[alto + n][ancho + m];
                        } 
                    else 
                    if((y + n)<0 && (x + m)< ancho) {
                        subImg[ct] = imagen[alto + n][x + m];
                        } 
                    else 
                    if((x + m)<0 && (y + n)<alto) {
                        subImg[ct] = imagen[y + n][ancho + m];
                        } 
                    else 
                    if((x + m)==ancho && (y + n)==alto) {
                        subImg[ct] = imagen[0][0];
                        } 
                    else 
                    if((x + m)>=ancho) {
                        subImg[ct] = imagen[y][m];
                        } 
                    else 
                    if((y + n)>=alto) {
                        subImg[ct] = imagen[n][x];
                        } 
                    else {
                        subImg[ct] = imagen[y + n][x + m];
                    }
                    }
                }
        } catch (NegativeArraySizeException e) {
            System.out.println(" Error en asignarImagen()"
                    + " tam es negativo " + e);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(" Error en asignarImagen()"
                    + " indice fuera de rango " + e);
        } catch (NullPointerException e) {
            System.out.println(" Error en asignarImagen()" + e);
        }
        return subImg;
    }
    /**
     * Obtiene el maximo y minimo contenido en la imagen convolucionada y
     * realiza un redimensionamiento al intervalo de 256 niveles de gris,
     * devuelve la imagen redimensionada.
     *
     * @param matriz la imagen que se redimensiona al espacio de niveles de gris
     * entre 0 y 256
     * @param sel selector de dimensionamiento
     *
     * @return devuelve la imagen redimensionada en un arreglo bidimensional
     * double
     */
    public double[][] dimensionarIntervalo(double[][] matriz, int sel) {
        double max = MAXC;
        double min = MINC;
        double temp;
        try {
            for(int y=0; y<alto; y++) {
                for(int x=0; x<ancho; x++) {
                    if(matriz[y][x]>max) {
                        max = matriz[y][x];
                        }
                    if (matriz[y][x]<min) {
                        min = matriz[y][x];
                        }
                    //System.out.println( "(" + y + ", " + x + " ) max " + 
                    //          max + " min " + min );
                }
            }
            System.out.println(" max " + max + " min " + min);
            for(int y=0; y<alto; y++) {
                for(int x=0; x<ancho; x++) {
                    if(sel==2) {
                        temp = ((matriz[y][x]-min) * (ESCALA/(max-min)));
                        } 
                    else {
                        temp = matriz[y][x];
                        }
                    if(temp>ESCALA) {
                        temp = ESCALA;
                        }
                    if(temp<0.0) {
                        temp = 0.0;
                        }
                    matriz[y][x] = temp;
                    //System.out.print( " dim:" + temp );
                    }
                //System.out.println(  );
                //System.out.println(  );
                }
            max = MAXC;
            min = MINC;
            for(int y=0; y<alto; y++) {
                for(int x=0; x<ancho; x++) {
                    if(matriz[y][x]>max) {
                        max = matriz[y][x];
                        }
                    if(matriz[y][x]<min) {
                        min = matriz[y][x];
                        }
                    //System.out.println( "(" + y + ", " + x + " ) max " + 
                    //          max + " min " + min );
                    }
                }
            System.out.println(" max " + max + " min " + min);
        } catch (NegativeArraySizeException e) {
            System.out.println(" Error en dimensionarIntervalo()"
                    + " x o y es negativo " + e);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(" Error en dimensionarIntervalo()"
                    + " indice fuera de rango " + e);
        } catch (NullPointerException e) {
            System.out.println(" Error en dimensionarIntervalo()" + e);
        }
        return matriz;
    }
    public Image getImageConvolucionInt(int[][] mask, int tam, int[][] imagen,
            int sel){
            int[][] imagenInt=this.calcularConvolucion(mask, tam, imagen, sel);
                JFrame padre = new JFrame();
                Image imagenNueva;
                imagenNueva = padre.createImage(new MemoryImageSource(ancho,
                alto, imageBuffered.convertirInt2DAInt1D(imagenInt, ancho, alto),
                0, ancho));
        
        return imagenNueva;
    }
    public Image getImageConvolucionDouble(double[][] mask, int tam, int[][] imagen,
            int sel){
            int[][] imagenInt=this.calcularConvolucion(mask, tam, imagen, sel);
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
}
