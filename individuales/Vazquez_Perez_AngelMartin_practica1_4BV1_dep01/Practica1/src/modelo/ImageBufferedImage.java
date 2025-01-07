package modelo;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import javax.swing.JFrame;

/**
 * Clase para convertir entre objetos:<br>
 * BufferedImage a Image<br>
 * y al contrario:<br>
 * Image a bufferedImage<br>
 * 
 * @author sdelaot
 */
public class ImageBufferedImage {
    private Image imagen;
    private BufferedImage bufferedImagen;
    private int [][] matrizImagen;
    /**
     * Convierte un objeto BufferedImage a un objeto Image.
     * 
     * @param input La imagen que se transforma<br>
     * @param queCanal el canal a tomar
     * 
     * @return Devuelve ob objeto de tipo image
     */
        public Image getImage(BufferedImage input, int queCanal) {
        int alto = input.getHeight();
        int ancho = input.getWidth();
        int pixel;
        
        int [][] imagenInt = new int[alto][ancho];
        matrizImagen = new int [alto][ancho];
        for(int y=0; y<alto; y++) {
            for(int x=0; x<ancho; x++) {
                pixel = input.getRGB(x,y);
                int rojo  = (pixel & 0x00ff0000) >> 16;
                int verde = (pixel & 0x0000ff00) >> 8;
                int azul  =  pixel & 0x000000ff;
                int elPixel;
                Color color = null;
                
                switch(queCanal) {
                    case 1: // red
                        elPixel = rojo;
                        if( rojo==255 && verde==0 && azul==0 ) {       
                            color = new Color(rojo, verde, azul);
                            }
                        else {
                            color = new Color(elPixel, 0, 0);
                            }
                        break;
                    case 2: // green
                        elPixel = verde;
                        if( rojo==0 && verde==255 && azul==0 ) {       
                            color = new Color(rojo, verde, azul);
                            }
                        else {
                            color = new Color(0, elPixel, 0);
                            }
                        break;
                    case 3: // blue
                        elPixel = azul;
                        if( rojo==0 && verde==0 && azul==255 ) {       
                            color = new Color(rojo, verde, azul);
                            }
                        else {
                            color = new Color(0, 0, elPixel);
                            }
                        break;
                    case 4: // todos
                        color = new Color(rojo, verde, azul);
                        break;
                    case 5:
                        int gris = (rojo + verde + azul) / 3;
                        color = new Color(gris, gris, gris);
                        break;
                    case 6:
                        color=new Color(rojo,rojo,rojo);
                        break;
                    case 7:
                        color=new Color(verde,verde,verde);
                        break;
                    case 8:
                        color=new Color(azul,azul,azul);
                        break; 
                       
                }
                imagenInt[y][x] = color.getRGB();
                matrizImagen[y][x]=color.getRGB();
                }
            }
        JFrame padre = new JFrame();
        imagen = padre.createImage(new MemoryImageSource(ancho,
                alto, convertirInt2DAInt1D(imagenInt, ancho, alto),
                0, ancho));
        
        return imagen;
    }
    public Image modificarBrillo(BufferedImage input,int escalar) {
        int alto = input.getHeight();
        int ancho = input.getWidth();
        int pixel;
        int [][] imagenInt = new int[alto][ancho];
        for(int y=0; y<alto; y++) {
            for(int x=0; x<ancho; x++) {
                pixel = input.getRGB(x,y);
                int rojo  = (pixel & 0x00ff0000) >> 16;
                int verde = (pixel & 0x0000ff00) >> 8;
                int azul  =  pixel & 0x000000ff;
                int elPixel;
                Color color = null;
                        int gris = (rojo + verde + azul) / 3;
                        gris += escalar;
                        if(gris>255) {
                            gris = 255;
                            }
                        if(gris<0) {
                            gris = 0;
                            }
                        color = new Color(gris, gris, gris);
                    
                imagenInt[y][x] = color.getRGB();
            }
        }
        JFrame padre = new JFrame();
        imagen = padre.createImage(new MemoryImageSource(ancho,
                alto, convertirInt2DAInt1D(imagenInt, ancho, alto),
                0, ancho));
        return imagen;
    }
    public Image modificarContraste(BufferedImage input,double escalar) {
        int alto = input.getHeight();
        int ancho = input.getWidth();
        int pixel;
        
        int [][] imagenInt = new int[alto][ancho];
        for(int y=0; y<alto; y++) {
            for(int x=0; x<ancho; x++) {
                pixel = input.getRGB(x,y);
                int rojo  = (pixel & 0x00ff0000) >> 16;
                int verde = (pixel & 0x0000ff00) >> 8;
                int azul  =  pixel & 0x000000ff;
                int elPixel;
                Color color = null;
                    int gris = (rojo + verde + azul) / 3;
                            gris *= escalar;
                            if(gris>255) {
                            gris = 255;
                            }
                            if(gris<0) {
                            gris = 0;
                            }
                            color = new Color(gris, gris, gris);
                    
                imagenInt[y][x] = color.getRGB();
            }
        }
        JFrame padre = new JFrame();
        imagen = padre.createImage(new MemoryImageSource(ancho,
                alto, convertirInt2DAInt1D(imagenInt, ancho, alto),
                0, ancho));
        return imagen;
    }
    /**
     * Convierte un buffer de tipo double y de dos dimensiones de imagen a uno
     * de una dimension de tipo entero.
     * 
     * @param matriz la matriz a convertir
     * @param ancho ancho de la imagen en pixeles
     * @param alto alto de la imagen en pixeles
     * 
     * @return el vector de la image convertida
     */
    public int [] convertirInt2DAInt1D(int[][] matriz, int ancho, int alto) {
        int index = 0;
        int [] bufferInt = null;
        try {
            bufferInt = new int[ancho * alto];
            for(int y = 0; y < alto; y++) {
                for(int x=0; x < ancho; x++) {
                    bufferInt[index++] = matriz[y][x];
                    }
                }
        } catch (NegativeArraySizeException e) {
            System.out.println(" Error alto, ancho o ambos negativos"
                + " en  convierteInt2DAInt1D( double [][] ) "
                + e);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(" Error desbordamiento en bufferInt"
                + " en  convierteInt2DAInt1D( double [][] ) "
                + e);
        } catch (NullPointerException e) {
            System.out.println(" Error bufferInt nulo"
                + " en  convierteInt2DAInt1D( double [][] ) "
                + e);
        }
        return bufferInt;
    }
    /**
     * Convierte un objeto Image a un objeto BufferedImage nivel de gris.
     * 
     * @param input La imagen que se transforma<br>
     * 
     * @return Devuelve ob objeto de tipo image
     */
    public BufferedImage getBufferedImage(Image input) {
        int alto = input.getHeight(null);
        int ancho = input.getWidth(null);
        bufferedImagen = 
                new BufferedImage(ancho, alto, BufferedImage.TYPE_BYTE_GRAY);
        ExtractorDePixel op = new ExtractorDePixel();
        matrizImagen = op.obtenerPixelesEn2D(input, 0, 0, ancho, alto);
        for(int y=0; y<alto; y++) {
            for(int x=0; x<ancho; x++) {
                int pixel = matrizImagen[y][x];
                int azul  =  pixel & 0x000000ff;
                Color color = new Color(azul, azul, azul);
                pixel = color.getRGB();
                bufferedImagen.setRGB(x, y, pixel);
                }
            }
        
        return bufferedImagen;
    }
    /**
     * Convierte un objeto Image a un objeto BufferedImage en color.
     * 
     * @param input La imagen que se transforma<br>
     * 
     * @return Devuelve ob objeto de tipo image
     */
    public BufferedImage getBufferedImageColor(Image input) {
        int alto = input.getHeight(null);
        int ancho = input.getWidth(null);
        bufferedImagen = 
                new BufferedImage(ancho, alto, BufferedImage.TYPE_3BYTE_BGR);
        ExtractorDePixel op = new ExtractorDePixel();
        int [][] imagenInt = op.obtenerPixelesEn2D(input, 0, 0, ancho, alto);
        for(int y=0; y<alto; y++) {
            for(int x=0; x<ancho; x++) {
                int pixel = imagenInt[y][x];
                int rojo  = (pixel & 0x00ff0000) >> 16;
                int verde = (pixel & 0x0000ff00) >>  8;
                int azul  =  pixel & 0x000000ff;
                Color color = new Color(rojo, verde, azul);
                pixel = color.getRGB();
                bufferedImagen.setRGB(x, y, pixel);
                }
            }
        return bufferedImagen;
    }
    public Image escalaGrises(BufferedImage image) {
    int ancho = image.getWidth();
    int alto = image.getHeight();
    int[][] imagenInt = new int[alto][ancho];

    for (int y = 0; y < alto; y++) {
        for (int x = 0; x < ancho; x++) {
            int rgb = image.getRGB(x, y);
            int alpha = (rgb >> 24) & 0xff;
            int rojo = (rgb >> 16) & 0xff;
            int verde = (rgb >> 8) & 0xff;
            int azul = rgb & 0xff;
            int gris = (rojo + verde + azul) / 3;
            int newRgb = (alpha << 24) | (gris << 16) | (gris << 8) | gris;
            imagenInt[y][x] = newRgb;
        }
    }
    int[] pixeles = convertirInt2DAInt1D(imagenInt, ancho, alto);

    // Creamos la imagen
    JFrame frame = new JFrame();
    Image imagenGris = frame.createImage(new MemoryImageSource(ancho, alto, pixeles, 0, ancho));

    return imagenGris;
}


    public Image getImagen() {
        return imagen;
    }

    public BufferedImage getBufferedImagen() {
        return bufferedImagen;
    }

    public int[][] getMatrizImagen() {
        return matrizImagen;
    }
    
}
