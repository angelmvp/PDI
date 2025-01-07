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
public class FiltrosNoLineales {
    private int[][] imagenInt;
    private int [][] copiaMatriz;
    private int alto;
    private int ancho;
    private ImageBufferedImage imageBuffered;
    private int tamMascara;
    private String tipoMascara;
    
    
    public FiltrosNoLineales(int[][] imagenInt,int tamMascara){
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
    public Image aplicarFiltrodeAlfaTrimmed(int p){
        int[][] nuevaMatriz= new int[alto][ancho];
        for(int y=0; y<alto;y++){
            for(int x=0;x<ancho;x++){
              int gris=obtenerAlfaTrimmed(x,y,p); 
              Color color= new Color(gris,gris,gris);
              nuevaMatriz[y][x] = color.getRGB();
            }
        }
        Image nuevaImagen = generarImagenDesdeMatriz(nuevaMatriz);
        return nuevaImagen;
    }
    public int obtenerAlfaTrimmed(int x, int y,int p){
        int[] ventana=obtenerVentana(x,y);
        Arrays.sort(ventana);
        int pixel;
        int suma=0;
        for (int i= p; i<ventana.length-p;i++){
            suma+=ventana[i];
        }
        pixel = (suma/(ventana.length-(2*p)));
        return pixel;
    }
    public Image aplicarFiltroMediana() {
        int[][] nuevaMatriz=new int[alto][ancho];
        for(int y=0; y<alto;y++){
            for(int x=0;x<ancho;x++){
              int gris=obtenerMediana(x,y); 
              Color color= new Color(gris,gris,gris);
              nuevaMatriz[y][x] = color.getRGB();
            }
        }
        Image nuevaImagen = generarImagenDesdeMatriz(nuevaMatriz);
        return nuevaImagen;
    }
    public int obtenerMediana(int x,int y){
        int[] ventana=obtenerVentana(x,y);
        Arrays.sort(ventana);
        return ventana[(int)ventana.length / 2];
    }
    public Image aplicarFiltroMaximo() {
        int[][] nuevaMatriz= new int[alto][ancho];
        for(int y=0; y<alto;y++){
            for(int x=0;x<ancho;x++){
              int gris=obtenerMaximo(x,y); 
              Color color= new Color(gris,gris,gris);
              nuevaMatriz[y][x] = color.getRGB();
            }
        }
        Image nuevaImagen = generarImagenDesdeMatriz(nuevaMatriz);
        return nuevaImagen;
    }
    public int obtenerMaximo(int x,int y){
        int[] ventana=obtenerVentana(x,y);
        int max=0;
        for(int i=0;i<ventana.length;i++){
            if (ventana[i]>max){
                max=ventana[i];
            }
        }
        return max;
    }

    public Image aplicarFiltroMinimo() {
        int[][] nuevaMatriz=new int[alto][ancho];
        for(int y=0; y<alto;y++){
            for(int x=0;x<ancho;x++){
              int gris=obtenerMinimo(x,y); 
              Color color= new Color(gris,gris,gris);
              nuevaMatriz[y][x] = color.getRGB();
            }
        }
        Image nuevaImagen = generarImagenDesdeMatriz(nuevaMatriz);
        return nuevaImagen;
    }
    public int obtenerMinimo(int x,int y){
        int[] ventana=obtenerVentana(x,y);
        int min=255;
        for(int i=0;i<ventana.length;i++){
            if (ventana[i]<min){
                min=ventana[i];
            }
        }
        return min;
    }

    public Image aplicarFiltroPuntoMedio() {
        int[][] nuevaMatriz=new int[alto][ancho];
        for(int y=0; y<alto;y++){
            for(int x=0;x<ancho;x++){
              int gris=obtenerPuntoMedio(x,y); 
              Color color= new Color(gris,gris,gris);
              nuevaMatriz[y][x] = color.getRGB();
            }
        }
        Image nuevaImagen = generarImagenDesdeMatriz(nuevaMatriz);
        return nuevaImagen;
    }
    public int obtenerPuntoMedio(int x,int y){
        int[] ventana=obtenerVentana(x,y);
        Arrays.sort(ventana);
        return (ventana[0] + ventana[ventana.length-1]) / 2;
    }

    public Image aplicarFiltroInferiorArmonico() {
        int[][] nuevaMatriz=new int[alto][ancho];
        for(int y=0; y<alto;y++){
            for(int x=0;x<ancho;x++){
              int gris=obtenerInferiorArmonico(x,y); 
              gris = validar(gris);
              Color color= new Color(gris,gris,gris);
              nuevaMatriz[y][x] = color.getRGB();
            }
        }
        Image nuevaImagen = generarImagenDesdeMatriz(nuevaMatriz);
        return nuevaImagen;
    }
    public int obtenerInferiorArmonico(int x,int y){
        int[] ventana=obtenerVentana(x,y);
        double suma = 0;
        int contador=0;
        for(int i=0;i<ventana.length;i++){
            if(ventana[i]!=0){
                suma+=1.0/(ventana[i]);
                contador++;
            }           
        }

        if(contador==0){
            return 0;
        }
        return (int)(contador/ suma);
    }

    public Image aplicarFiltroInferiorContraArmonico(double valorP) {
        int[][] nuevaMatriz=new int[alto][ancho];
        for(int y=0; y<alto;y++){
            for(int x=0;x<ancho;x++){
              int gris=(int)obtenerInferiorContraArmonico(x,y,valorP); 
              gris = validar(gris);
              Color color= new Color(gris,gris,gris);
              nuevaMatriz[y][x] = color.getRGB();
            }
        }
        Image nuevaImagen = generarImagenDesdeMatriz(nuevaMatriz);
        return nuevaImagen;
    }
    public double obtenerInferiorContraArmonico(int x,int y,double p){
        int[] ventana=obtenerVentana(x,y);
        double suma1 = 0;
        double suma2=0;
        for(int i=0;i<tamMascara;i++){
            suma1+=Math.pow(ventana[i], p+1);
            suma2+=Math.pow(ventana[i], p);
        }
        if (suma2==0){
            return 255;
        }
        return suma1/suma2;
    }

    public Image aplicarFiltroInferiorGeometrico() {
        int[][] nuevaMatriz=new int[alto][ancho];
        for(int y=0; y<alto;y++){
            for(int x=0;x<ancho;x++){
              int gris=obtenerInferiorGeometrico(x,y); 
              gris = validar(gris);
              Color color= new Color(gris,gris,gris);
              nuevaMatriz[y][x] = color.getRGB();
            }
        }
        Image nuevaImagen = generarImagenDesdeMatriz(nuevaMatriz);
        return nuevaImagen;
    }
    public int obtenerInferiorGeometrico(int x,int y){
        int[] ventana=obtenerVentana(x,y);
        double multiplicatorio=1;
        double inversa = 1.0/(ventana.length);
        for(int i=0;i<ventana.length;i++){
            multiplicatorio*=ventana[i];
        }
        
        return (int)(Math.pow(multiplicatorio, inversa));
    }

    public Image aplicarFiltroMaximoMinimo() {
        int[][] nuevaMatriz=new int[alto][ancho];
        for(int y=0; y<alto;y++){
            for(int x=0;x<ancho;x++){
              int gris=obtenerMaximoMinimo(x,y); 
              Color color= new Color(gris,gris,gris);
              nuevaMatriz[y][x] = color.getRGB();
            }
        }
        Image nuevaImagen = generarImagenDesdeMatriz(nuevaMatriz);
        return nuevaImagen;
    }
    public int obtenerMaximoMinimo(int x,int y){
        int[] ventana=obtenerVentana(x,y);
        int min=255;
        int max=0;
        for(int i=0;i<ventana.length;i++){
            if (ventana[i]<min){
                min=ventana[i];
            }
            if (ventana[i]>max){
                max=ventana[i];
            }
        }
        if(imagenInt[y][x]-max<=imagenInt[y][x]-min){
            return max;
        }
        return min;
    }

    public Image aplicarFiltroMediaAritmetica() {
        int[][] nuevaMatriz=new int[alto][ancho];
        for(int y=0; y<alto;y++){
            for(int x=0;x<ancho;x++){
              int gris=obtenerMediaAritmetica(x,y); 
              Color color= new Color(gris,gris,gris);
              nuevaMatriz[y][x] = color.getRGB();
            }
        }
        Image nuevaImagen = generarImagenDesdeMatriz(nuevaMatriz);
        return nuevaImagen;
    }
    public int obtenerMediaAritmetica(int x,int y){
        int[] ventana=obtenerVentana(x,y);
        int suma=0;
        for(int i=0;i<ventana.length;i++){
            suma+=ventana[i];
        }
        return suma/(ventana.length);
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
