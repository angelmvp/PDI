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
public class ES {
    private int[][] imagenInt;
    private int [][] copiaMatriz;
    private int alto;
    private int ancho;
    private ImageBufferedImage imageBuffered;
    private int tamMascara;
    private String tipoMascara;
    private int[][] nuevaMatriz;
    
    public ES(int[][] imagenInt,int tamMascara){
        this.imagenInt=imagenInt;
        this.tamMascara=tamMascara;
        imageBuffered = new ImageBufferedImage();
        tipoMascara="cruz";
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
        int[] ventana=obtenerVentana(x,y);
        int max=0;
        for(int i=0;i<ventana.length;i++){
            if (ventana[i]>max){
                max=ventana[i];
            }
        }
        setValorVentana(x,y,max);
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
        int[] ventana=obtenerVentana(x,y);
        int min=255;
        for(int i=0;i<ventana.length;i++){
            if (ventana[i]<min){
                min=ventana[i];
            }
        }
        setValorVentana(x,y,min);
    }  
    public Image aplicarClausura(){
        Image dilatacion = this.aplicarDilatacion();
        ES nuevoes = new ES(imageBuffered.getMatrizImagen(imageBuffered.getBufferedImageColor(dilatacion),5),3);
        Image nuevaImg = nuevoes.aplicarErosion();
        return nuevaImg;
    }
    public Image aplicarApertura(){
        Image erosion = this.aplicarErosion();
        ES nuevoes = new ES(imageBuffered.getMatrizImagen(imageBuffered.getBufferedImageColor(erosion),5),3);
        Image nuevaImg = nuevoes.aplicarDilatacion();
        return nuevaImg;
    }
    public void setValorVentana(int x,int y,int valor){
        switch(tipoMascara){
                case "cuadrada":
                    setVentanaCuadrada(x,y,valor);
                case "horizontal":
                    setVentanaHorizontal(x,y,valor);
                case "vertical":
                    setVentanaVertical(x,y,valor);
                case "cruz":
                    setVentanaCruz(x,y,valor);
                case "equis":
                    setVentanaEquis(x,y,valor);
                case "diamante":
                    setVentanaDiamante(x,y,valor);
        }
    }
    private int[] obtenerVentana(int x, int y) {
        switch(tipoMascara){
                case "cuadrada":
                    return obtenerVentanaCuadrada(x,y);
                case "horizontal":
                    return obtenerVentanaHorizontal(x,y);
                case "vertical":
                    return obtenerVentanaVertical(x,y);
                case "cruz":
                    return obtenerVentanaCruz(x,y);
                case "equis":
                    return obtenerVentanaEquis(x,y);
                case "diamante":
                    return obtenerVentanaDiamante(x,y);
        }
        return null;
    }
    private int[] obtenerVentanaCuadrada(int x, int y) {
        int mitad = tamMascara / 2;
        int[] ventana = new int[tamMascara * tamMascara];
        int index = 0;
        for (int i = -mitad; i <= mitad; i++) {
            for (int j = -mitad; j <= mitad; j++) {
                int posX = Math.min(Math.max(x + i, 0), ancho - 1);
                int posY = Math.min(Math.max(y + j, 0), alto - 1);
                ventana[index++] = copiaMatriz[posY][posX];
            }
        }
        return ventana;
    }
    private int[] obtenerVentanaHorizontal(int x, int y) {
        int mitad = tamMascara / 2;
        int[] ventana = new int[tamMascara];
        int index = 0;
        for (int i = -mitad; i <= mitad; i++) {
                int posX = Math.min(Math.max(x + i, 0), ancho - 1);
                ventana[index++] = copiaMatriz[y][posX];
        }
        return ventana;
    }
    private int[] obtenerVentanaVertical(int x, int y) {
        int mitad = tamMascara / 2;
        int[] ventana = new int[tamMascara];
        int index = 0;
        for (int i = -mitad; i <= mitad; i++) {
                int posY = Math.min(Math.max(y + i, 0), alto - 1);
                ventana[index++] = copiaMatriz[posY][x];
        }
        return ventana;
    }
    private int[] obtenerVentanaCruz(int x, int y) {
        int mitad= tamMascara/2;
        int[] ventana= new int[tamMascara*2-1];
        int[] horizontal = obtenerVentanaHorizontal(x,y);
        int[] vertical = obtenerVentanaVertical(x,y);
        for (int i = 0; i < tamMascara; i++) { 
            ventana[i]=horizontal[i];
        }
        ventana[mitad]=vertical[0];
        int index=1;
        for (int i = tamMascara; i < tamMascara*2-1; i++) { 
            ventana[i]=vertical[index];
            index++;
        } 
        return ventana;
    }
    private int[] obtenerVentanaEquis(int x, int y) {
        int mitad = tamMascara / 2;
        int[] ventana = new int[tamMascara * 2 - 1]; // Ajustamos el tamaño para evitar un índice extra
        int index = 0;

        for (int i = -mitad; i <= mitad; i++) {
            int posX1 = Math.min(Math.max(x + i, 0), ancho - 1);
            int posY1 = Math.min(Math.max(y + i, 0), alto - 1);

            int posX2 = Math.min(Math.max(x - i, 0), ancho - 1);
            int posY2 = Math.min(Math.max(y + i, 0), alto - 1);

            if(index+1==ventana.length){
                break;
            }
            if (index < ventana.length) { 
                ventana[index++] = copiaMatriz[posY1][posX1];
            }
            if (i != 0 && index < ventana.length) {
                ventana[index++] = copiaMatriz[posY2][posX2];
            }
        }
        return ventana;
    }
    private int[] obtenerVentanaEquis(int x, int y,int tam) {
        int mitad = tam / 2;
        int[] ventana = new int[tam * 2 - 1]; // Ajustamos el tamaño para evitar un índice extra
        int index = 0;

        for (int i = -mitad; i <= mitad; i++) {
            int posX1 = Math.min(Math.max(x + i, 0), ancho - 1);
            int posY1 = Math.min(Math.max(y + i, 0), alto - 1);

            int posX2 = Math.min(Math.max(x - i, 0), ancho - 1);
            int posY2 = Math.min(Math.max(y + i, 0), alto - 1);

            if(index+1==ventana.length){
                break;
            }
            if (index < ventana.length) { 
                ventana[index++] = copiaMatriz[posY1][posX1];
            }
            if (i != 0 && index < ventana.length) {
                ventana[index++] = copiaMatriz[posY2][posX2];
            }
        }
        return ventana;
    }


    private int[] obtenerVentanaDiamante(int x, int y) {
        int mitad = tamMascara / 2;
        int[] cruz = obtenerVentanaCruz(x,y);
        int[] equis = obtenerVentanaEquis(x,y,tamMascara-2);
        int[] ventana = new int[cruz.length+equis.length-1]; 
        for(int i=0; i<equis.length;i++){
            ventana[i]=equis[i];
        }
        ventana[mitad]=cruz[0];
        int index=1;
        for(int i=equis.length;i<cruz.length+equis.length-1;i++){
            ventana[i]=cruz[index];
            index++;
        }
        return ventana;
    }

    
    
    private void setVentanaCuadrada(int x, int y, int valor) {
        int mitad = tamMascara / 2;
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
        int mitad = tamMascara / 2;
        for (int i = -mitad; i <= mitad; i++) {
            int columna = x + i; // Coordenada en x
            if (columna >= 0 && columna < ancho) {
                Color nuevoColor = new Color(valor, valor, valor);
                nuevaMatriz[y][columna] = nuevoColor.getRGB(); // Actualizar valor
            }
        }
    }

    private void setVentanaVertical(int x, int y, int valor) {
        int mitad = tamMascara / 2;
        for (int i = -mitad; i <= mitad; i++) {
            int fila = y + i; // Coordenada en y
            if (fila >= 0 && fila < alto) {
                Color nuevoColor = new Color(valor, valor, valor);
                nuevaMatriz[fila][x] = nuevoColor.getRGB(); // Actualizar valor
            }
        }
    }

    private void setVentanaEquis(int x, int y, int valor) {
        int mitad = tamMascara / 2;
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
    int mitad = tamMascara / 2;
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
    int mitad = tamMascara / 2;

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
    public void setTipoMascara(String tipoMascara){
        this.tipoMascara=tipoMascara;
    }
    public void setTamMascara(int tam){
        this.tamMascara=tam;
    }
    public void setMatrizImagen(int[][] imagenInt){
        this.imagenInt=imagenInt;
        initComponents();
    }
}
