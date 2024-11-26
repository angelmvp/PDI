package temporal.morfologia;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
/**
 * Esta clase crea una imagen propia.
 * 
 * @author Yo
 * @version 1.0
 * 
 */
public class MiImagen {
    
    /** 
     * Almacena la imagen de referencia 
     */
    private BufferedImage imagen;
    
    /** 
     * Almacena el ancho de la imagen
     */
    private int ancho;
    /**
     * Almacena el alto de la imagen
     */
    private int alto;
    
    /** 
     * Pixeles valores - RGB 
     */
    private int [] pixeles;
    
    /** 
     * Numero total de pixeles en la imagen
     */
    private int totalPixeles;
    
    /** 
     * Ejemplos de imagenes almacenadas jpg o png. JPG no soporta canal alfa 
     * (se pierde la transparencia) mientras PNG si soporta el canal alfa.
     */
    private enum TipoImagen {
        JPG, PNG
    };
    /**
     * Tipo de imagen
     */
    private TipoImagen tipoImagen;
    
    /** 
     * Constructor por defecto
     */
    public MiImagen() {
        imagen = null;
        ancho = 0;
        alto = 0;
        pixeles = null;
        totalPixeles = 0;
    }
    
    /** 
     * Constructor para crear un objeto MiImagen
     * 
     * @param ancho ancho de la imagen
     * @param alto alto de la imagen
     */
    public MiImagen(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
        this.totalPixeles = this.ancho * this.alto;
        this.pixeles = new int[this.totalPixeles];
        imagen = new BufferedImage(this.ancho, this.alto, 
                BufferedImage.TYPE_INT_ARGB);
        this.tipoImagen = TipoImagen.PNG;
        iniciarArregloPixeles();
    }
    
    /** 
     * Constructor de copia.
     * 
     * @param imagen la imagen que se va a copiar
     */
    public MiImagen(MiImagen imagen) {
        this.ancho = imagen.getImagenAncho();
        this.alto = imagen.getImagenAlto();
        this.totalPixeles = this.ancho * this.alto;
        this.pixeles = new int[this.totalPixeles];
        
        this.tipoImagen = imagen.tipoImagen;
        if(this.tipoImagen == TipoImagen.PNG) {
            this.imagen = new BufferedImage(ancho, alto, 
                    BufferedImage.TYPE_INT_ARGB);
            }
        else {
            this.imagen = new BufferedImage(ancho, alto, 
                    BufferedImage.TYPE_INT_RGB);
            }
        
        //copia los valores de pixeles de la imagen original a la nueva imagen
        for(int y = 0; y < this.alto; y++) {
            for(int x = 0; x < this.ancho; x++) {
                this.imagen.setRGB(x, y, imagen.getPixel(x, y));
                this.pixeles[x+y*this.ancho] = imagen.getPixel(x, y);
                }
            }
    }
    
    /**
     * Modifica la imagen.
     * 
     * @param ancho el ancho de la nueva imagen.
     * @param alto el alto de la nueva imagen.
     * @param bi El reemplazo de la imagen vieja.
     */
    public void modifyImageObject(int ancho, int alto, BufferedImage bi) {
        this.ancho = ancho;
        this.alto = alto;
        this.totalPixeles = this.ancho * this.alto;
        this.pixeles = new int[this.totalPixeles];
        if(this.tipoImagen == TipoImagen.PNG){
            this.imagen = new BufferedImage(ancho, alto, 
                    BufferedImage.TYPE_INT_ARGB);
            }
        else{
            this.imagen = new BufferedImage(ancho, alto, 
                    BufferedImage.TYPE_INT_RGB);
            }
        Graphics2D g2d = this.imagen.createGraphics();
        g2d.drawImage(bi, 0, 0, null);
        g2d.dispose();
        iniciarArregloPixeles();
    }
    
    /** 
     * Lee la imagen empleando su acceso
     * 
     * @param filePath el acceso al archivo de imagen
     * Ejemplo filePath = "C:\\casita.jpg"
     */
    public void readImage(String filePath) {
        try{
            File f = new File(filePath);
            imagen = ImageIO.read(f);
            String fileType = filePath.substring(filePath.lastIndexOf('.')+1);
            if("jpg".equals(fileType)) {
                tipoImagen = TipoImagen.JPG;
                }
            else{
                tipoImagen = TipoImagen.PNG;
                }
            this.ancho = imagen.getWidth();
            this.alto = imagen.getHeight();
            this.totalPixeles = this.ancho * this.alto;
            this.pixeles = new int[this.totalPixeles];
            iniciarArregloPixeles();
        } catch(IOException e) {
            System.out.println("Error Occurred!\n"+e);
        }
    }
    
    /**
     * Escribe el contenido de BufferedImage hacia un archivo
     * 
     * @param filePath El acceso con nombre del archivo
     * Ejemplo filePath = "c:\\casitaout.jpg"
     */
    public void writeImage(String filePath) {
        try {
            File f = new File(filePath);
            String fileType = 
                    filePath.substring(filePath.lastIndexOf('.')+1);
            ImageIO.write(imagen, fileType, f);
        } catch(IOException e){
            System.out.println("Error!\n"+e);
        }
    }
    
    /**
     * Inicializa el arreglo de pixeles
     * 
     * Origen de la imagen a las coordenadas (x,y)=(0,0)
     * (0,0)--------  eje-X
     *     |
     *     |
     *     |
     *     v
     *   eje-Y
     * 
     * Este metodo almacena los valores de cada pixel de la imagen de dos 
     * dimensiones en un arreglo de una dimension.
     */
    private void iniciarArregloPixeles() {
        PixelGrabber pg = 
                new PixelGrabber(imagen, 0, 0, ancho, alto, pixeles, 0, ancho);
        try {
            pg.grabPixels();
        } catch(InterruptedException e) {
            System.out.println("Error: " + e);
        }
    }
    
    /**
     * Este metodo compara si dos imagenes son iguales.
     * Si se tiene dos imagenes img1 e img2
     * El llamado img1.isEqual(img2) devuelve true si img1 e img2 son iguales 
     * en caso contrario devuelve false.
     * 
     * @param imagen La imagen a verificar con la incluida en la clase.
     * @return true si las dos imagenes son iguales, si no false.
     */
    public boolean isEqual(MiImagen imagen) {
        //verifica las dimensiones
        if(this.ancho != imagen.getImagenAncho() || 
                this.alto != imagen.getImagenAlto()) {
            return false;
            }
        
        //verifica los calores de pixel de cada imagen
        for(int y = 0; y < this.alto; y++) {
            for(int x = 0; x < this.ancho; x++) {
                if(this.pixeles[x+y*this.ancho] != imagen.getPixel(x, y)) {
                    return false;
                    }
                }
            }
        
        return true;
    }
    
    /**
     * Devuelve el ancho de la imagen
     * 
     * @return devuelve ancho
     */
    public int getImagenAncho() {
        return ancho;
    }
    
    /**
     * Devuelve el alto de la imagen
     * 
     * @return devuelve alto
     */
    public int getImagenAlto() {
        return alto;
    }
    
    /**
     * Devuelve el numero total de pixeles en la imagen
     * 
     * @return el total de pixeles
     */
    public int getImageTotalPixels() {
        return totalPixeles;
    }
    
    /**
     * Devuelve la cantidad de valor alpha entre 0-255 para el pixel (x,y)
     * 
     * @param x la coordenada en x del pixel
     * @param y la coordenada en y del pixel
     * @return la cantidad de alpha (transparencia)
     * 
     * 0 mas transparente
     * 255 mas opaco
     */
    public int getAlpha(int x, int y) {
        return (pixeles[x+(y*ancho)] >> 24) & 0xFF;
    }
    
    /**
     * This method will return the amount of red value between 0-255 at the 
     * pixel (x,y)
     * 
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @return the amount of red
     * 
     * 0 means none
     * 255 means fully red
     */
    public int getRed(int x, int y) {
        return (pixeles[x+(y*ancho)] >> 16) & 0xFF;
    }
    
    /**
     * This method will return the amount of green value between 0-255 at the 
     * pixel (x,y)
     * 
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel 
     * @return the amount of green
     * 
     * 0 means none
     * 255 means fully green
     */
    public int getGreen(int x, int y) {
        return (pixeles[x+(y*ancho)] >> 8) & 0xFF;
    }
    
    /**
     * This method will return the amount of blue value between 0-255 at the 
     * pixel (x,y)
     * 
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @return the amount of blue
     * 
     * 0 means none
     * 255 means fully blue
     */
    public int getBlue(int x, int y) {
        return pixeles[x+(y*ancho)] & 0xFF;
    }
    
    /**
     * This method will return the pixel value of the pixel at the coordinate 
     * (x,y)
     * 
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @return the pixel value in integer [Value can be negative and positive.]
     */
    public int getPixel(int x, int y) {
        return pixeles[x+(y*ancho)];
    }
    
    /**
     * This method will return the pixel value of the image as 1D array.
     * 
     * @return 1D array containing value of each pixels of the image.
     */
    public int [] getPixelArray() {
        return pixeles;
    }
        
    /**
     * This method will set the amount of alpha value between 0-255 at the 
     * pixel (x,y)
     * 
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @param alpha the alpha value to set
     * 
     * 0 means transparent
     * 255 means opaque
     */
    public void setAlpha(int x, int y, int alpha) {
        pixeles[x+(y*ancho)] = (alpha<<24) | 
                (pixeles[x+(y*ancho)] & 0x00FFFFFF);
        updateImagePixelAt(x,y);
    }
    
    /**
     * This method will set the amount of red value between 0-255 at the 
     * pixel (x,y)
     * 
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @param red the red value to set
     * 
     * 0 means none
     * 255 means fully red
     */
    public void setRed(int x, int y, int red) {
        pixeles[x+(y*ancho)] = (red<<16) | (pixeles[x+(y*ancho)] & 0xFF00FFFF);
        updateImagePixelAt(x,y);
    }
    
    /**
     * This method will set the amount of green value between 0-255 at the 
     * pixel (x,y)
     * 
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @param green the green value to set
     * 
     * 0 means none
     * 255 means fully green
     */
    public void setGreen(int x, int y, int green) {
        pixeles[x+(y*ancho)] = (green<<8) | (pixeles[x+(y*ancho)] & 0xFFFF00FF);
        updateImagePixelAt(x,y);
    }
    
    /**
     * This method will set the amount of blue value between 0-255 at the 
     * pixel (x,y)
     * 
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @param blue the blue value to set
     * 
     * 0 means none
     * 255 means fully blue
     */
    public void setBlue(int x, int y, int blue) {
        pixeles[x+(y*ancho)] = blue | (pixeles[x+(y*ancho)] & 0xFFFFFF00);
        updateImagePixelAt(x,y);
    }
    
    /**
     * This method will set pixel(x,y) to ARGB value.
     * 
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @param alpha the alpha value to set [value from 0-255]
     * @param red the red value to set [value from 0-255]
     * @param green the green value to set [value from 0-255]
     * @param blue the blue value to set  [value from 0-255]
     */
    public void setPixel(int x, int y, int alpha, int red, int green, 
            int blue) {
        pixeles[x+(y*ancho)] = (alpha<<24) | (red<<16) | (green<<8) | blue;
        updateImagePixelAt(x,y);
    }
    
    /**
     * This method will set pixel (x,y) to pixelValue.
     * 
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @param pixelValue the pixel value to set
     */
    public void setPixelToValue(int x, int y, int pixelValue) {
        pixeles[x+(y*ancho)] = pixelValue;
        updateImagePixelAt(x,y);
    }
    
    /**
     * This method will set the image pixel array to the value of the 1D 
     * pixelArray.
     * 
     * @param pixelArray 1D array containing values that is set to the image 
     * pixel array.77
     */
    public void setPixelArray(int pixelArray[]) {
        for(int y = 0; y < alto; y++) {
            for(int x = 0; x < ancho; x++) {
                pixeles[x+y*ancho] = pixelArray[x+y*ancho];
                updateImagePixelAt(x,y);
                }
            }
    }
    
    /**
     * This method will update the image pixel at coordinate (x,y)
     * 
     * @param x the x coordinate of the pixel that is set
     * @param y the y coordinate of the pixel that is set
     */
    private void updateImagePixelAt(int x, int y){
        imagen.setRGB(x, y, pixeles[x+(y*ancho)]);
    }
    
    
}