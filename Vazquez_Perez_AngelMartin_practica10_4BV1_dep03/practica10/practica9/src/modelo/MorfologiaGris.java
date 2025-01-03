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
              obtenerMaximo(x,y); 
            }
        }
        Image nuevaImagen = generarImagenDesdeMatriz(nuevaMatriz);
        return nuevaImagen;
    }
    public void obtenerMaximo(int x,int y){
        int[] ventana=ES.obtenerVentanaES(x,y);
        int max=0;
        for(int i=0;i<ventana.length;i++){
            if (ventana[i]>max){
                max=ventana[i];
            }
        }
        setValorESMatriz(x,y,max);
    }

    public Image aplicarErosion() {
        nuevaMatriz=new int[alto][ancho];
        for(int y=0; y<alto;y++){
            for(int x=0;x<ancho;x++){
              obtenerMinimo(x,y);
            }
        }
        Image nuevaImagen = generarImagenDesdeMatriz(nuevaMatriz);
        return nuevaImagen;
    }
    public void obtenerMinimo(int x,int y){
        int[] ventana=ES.obtenerVentanaES(x,y);
        int min=255;
        for(int i=0;i<ventana.length;i++){
            if (ventana[i]<min){
                min=ventana[i];
            }
        }
        setValorESMatriz(x,y,min);
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
    public void setValorESMatriz(int x,int y,int valor){
        switch(tipoES){
                case "cuadrada":
                    setVentanaCuadrada(x,y,valor);
                    break;
                case "horizontal":
                    setVentanaHorizontal(x,y,valor);
                    break;
                case "vertical":
                    setVentanaVertical(x,y,valor);
                    break;
                case "cruz":
                    setVentanaCruz(x,y,valor);
                    break;
                case "equis":
                    setVentanaEquis(x,y,valor);
                    break;
                case "diamante":
                    setVentanaDiamante(x,y,valor);
                    break;
        }
    } 
    
    private void setVentanaCuadrada(int x, int y, int valor) {
        int mitad = tamES / 2;
        for (int i = -mitad; i <= mitad; i++) {
            for (int j = -mitad; j <= mitad; j++) {
                int fila = y + i; // Coordenada en y
                int columna = x + j; // Coordenada en x           
                if (fila >= 0 && fila < alto && columna >= 0 && columna < ancho) {
                    Color nuevoColor = new Color(valor, valor, valor);
                    nuevaMatriz[fila][columna] = nuevoColor.getRGB(); // Actualizar valor
                }
            }
        }
    }
    private void setVentanaHorizontal(int x, int y, int valor) {
        int mitad = tamES / 2;
        for (int i = -mitad; i <= mitad; i++) {
            int columna = x + i; // Coordenada en x
            if (columna >= 0 && columna < ancho) {
                Color nuevoColor = new Color(valor, valor, valor);
                nuevaMatriz[y][columna] = nuevoColor.getRGB(); // Actualizar valor
            }
        }
    }

    private void setVentanaVertical(int x, int y, int valor) {
        int mitad = tamES / 2;
        for (int i = -mitad; i <= mitad; i++) {
            int fila = y + i; // Coordenada en y
            if (fila >= 0 && fila < alto) {
                Color nuevoColor = new Color(valor, valor, valor);
                nuevaMatriz[fila][x] = nuevoColor.getRGB(); // Actualizar valor
            }
        }
    }

    private void setVentanaEquis(int x, int y, int valor) {
        int mitad = tamES / 2;
        for (int i = -mitad; i <= mitad; i++) {
            int fila1 = y + i; // Diagonal principal hacia abajo
            int columna1 = x + i;
            int fila2 = y + i; // Diagonal secundaria hacia arriba
            int columna2 = x - i;

            // Verificar y actualizar diagonal principal
            if (fila1 >= 0 && fila1 < alto && columna1 >= 0 && columna1 < ancho) {
                Color nuevoColor = new Color(valor, valor, valor);
                nuevaMatriz[fila1][columna1] = nuevoColor.getRGB();
            }

            // Verificar y actualizar diagonal secundaria
            if (fila2 >= 0 && fila2 < alto && columna2 >= 0 && columna2 < ancho) {
                Color nuevoColor = new Color(valor, valor, valor);
                nuevaMatriz[fila2][columna2] = nuevoColor.getRGB();
            }
        }
    }
private void setVentanaDiamante(int x, int y, int valor) {
    int mitad = tamES / 2;
    for (int i = -mitad; i <= mitad; i++) {
        for (int j = -mitad; j <= mitad; j++) {
            // Verifica si el punto (i, j) está dentro del diamante
            if (Math.abs(i) + Math.abs(j) <= mitad) {
                int fila = y + i;
                int columna = x + j;
                if (fila >= 0 && fila < alto && columna >= 0 && columna < ancho) {
                    Color nuevoColor = new Color(valor, valor, valor);
                    nuevaMatriz[fila][columna] = nuevoColor.getRGB();
                }
            }
        }
    }
}
private void setVentanaCruz(int x, int y, int valor) {
    int mitad = tamES / 2;

    // Línea horizontal
    for (int i = -mitad; i <= mitad; i++) {
        int columna = x + i;
        if (columna >= 0 && columna < ancho) {
            Color nuevoColor = new Color(valor, valor, valor);
            nuevaMatriz[y][columna] = nuevoColor.getRGB();
        }
    }

    // Línea vertical
    for (int i = -mitad; i <= mitad; i++) {
        int fila = y + i;
        if (fila >= 0 && fila < alto) {
            Color nuevoColor = new Color(valor, valor, valor);
            nuevaMatriz[fila][x] = nuevoColor.getRGB();
        }
    }
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
