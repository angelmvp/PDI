/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import javax.swing.JFrame;
import temporal.morfologia.ElementoEstructuranteBinario;

/**
 *
 * @author jhona
 */
public class MorfologicaBinaria {
    private int[][] imagenInt;
    private BufferedImage buffered;
    private Image imagen;
    private int alto;
    private int ancho;
    private ImageBufferedImage imageBuffered;
    private ElementoEstructuranteBinario ESBinario;
    public MorfologicaBinaria(Image imagen){
        this.imagen=imagen;
        imageBuffered= new ImageBufferedImage();
    }
    private void initComponents(){
        buffered= imageBuffered.getBufferedImageColor(imagen);
        imagenInt = imageBuffered.getMatrizImagen(buffered,5);
        alto=imagenInt.length;
        ancho=imagenInt[0].length;
    }
public Image aplicarErosion(String tipoES) {
    int[][] nuevaImagen = new int[alto][ancho];
    //ElementoEstructuranteBinario ESBinario = new ElementoEstructuranteBinario(tipoES);

    // Recorre cada pixel de la imagen original
    for (int i = 0; i < alto; i++) {
        for (int j = 0; j < ancho; j++) {
            nuevaImagen[i][j] = erosionarPixel(i, j, ESBinario);
        }
    }

    // Convierte la nueva imagen a un objeto Image
    return crearImagenDesdeMatriz(nuevaImagen);
}

    private int erosionarPixel(int x, int y, ElementoEstructuranteBinario es) {
        for (int i = 0; i < es.getHeight(); i++) {
            for (int j = 0; j < es.getWidth(); j++) {
                int offsetX = x + i - es.getOrigin().x;
                int offsetY = y + j - es.getOrigin().y;

                // Verifica que la posición esté dentro de los límites
                if (offsetX >= 0 && offsetX < alto && offsetY >= 0 && offsetY < ancho) {
                    if (es.getPixel(i, j) == 1 && imagenInt[offsetX][offsetY] != 1) {
                        return 0; // No cumple con la condición para la erosión
                    }
                }
            }
        }
        return 1; // El pixel cumple con la condición para la erosión
    }
    public Image aplicarDilatacion(String tipoES){
        int[][] nuevaImagen = new int[alto][ancho];
        //ElementoEstructuranteBinario ESBinario = new ElementoEstructuranteBinario(tipoES);
        JFrame padre = new JFrame();
        Image imagenNueva = padre.createImage(new MemoryImageSource(ancho,
                alto, imageBuffered.convertirInt2DAInt1D(nuevaImagen, ancho, alto),
                0, ancho));
        System.out.println("se retorno la imagen");
        return imagenNueva;
    }
    public Image aplicarApertura(String tipoES){
        int[][] nuevaImagen = new int[alto][ancho];
        //ElementoEstructuranteBinario ESBinario = new ElementoEstructuranteBinario(tipoES);
        JFrame padre = new JFrame();
        Image imagenNueva = padre.createImage(new MemoryImageSource(ancho,
                alto, imageBuffered.convertirInt2DAInt1D(nuevaImagen, ancho, alto),
                0, ancho));
        System.out.println("se retorno la imagen");
        return imagenNueva;
    }
    public Image aplicarClausura(String tipoES){
        int[][] nuevaImagen = new int[alto][ancho];
        //ElementoEstructuranteBinario ESBinario = new ElementoEstructuranteBinario(tipoES);
        JFrame padre = new JFrame();
        Image imagenNueva = padre.createImage(new MemoryImageSource(ancho,
                alto, imageBuffered.convertirInt2DAInt1D(nuevaImagen, ancho, alto),
                0, ancho));
        System.out.println("se retorno la imagen");
        return imagenNueva;
    }
    
    
    private Image crearImagenDesdeMatriz(int[][] matriz) {
    JFrame padre = new JFrame();
    return padre.createImage(new MemoryImageSource(
        ancho,
        alto,
        imageBuffered.convertirInt2DAInt1D(matriz, ancho, alto),
        0,
        ancho
    ));
}
    
}
