/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author jhona
 */
public class ElementoEstructurante {
    private String tipoES;
    private int tamES;
    private int ancho;
    private int alto;
    private int[][] copiaMatriz;
    public ElementoEstructurante(int[][] imagenInt, int tam, String tipo){
        this.copiaMatriz=imagenInt;
        this.tamES=tam;
        this.tipoES=tipo;
        initComponents();
    }
    private void initComponents(){
        alto=copiaMatriz.length;
        ancho=copiaMatriz[0].length;
    }
    public int[] obtenerVentanaES(int x, int y) {
        switch(tipoES){
                case "cuadrada":
                    return obtenerESCuadrado(x,y);
                case "horizontal":
                    return obtenerESHorizontal(x,y);
                case "vertical":
                    return obtenerESVertical(x,y);
                case "cruz":
                    return obtenerESCruz(x,y);
                case "equis":
                    return obtenerESEquis(x,y);
                case "diamante":
                    return obtenerESDiamante(x,y);
        }
        return null;
    }
    private int[] obtenerESCuadrado(int x, int y) {
        int mitad = tamES / 2;
        int[] ventana = new int[tamES * tamES];
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
    public int[] obtenerESHorizontal(int x, int y) {
        int mitad = tamES / 2;
        int[] ventana = new int[tamES];
        int index = 0;
        for (int i = -mitad; i <= mitad; i++) {
                int posX = Math.min(Math.max(x + i, 0), ancho - 1);
                ventana[index++] = copiaMatriz[y][posX];
        }
        return ventana;
    }
    public int[] obtenerESVertical(int x, int y) {
        int mitad = tamES / 2;
        int[] ventana = new int[tamES];
        int index = 0;
        for (int i = -mitad; i <= mitad; i++) {
                int posY = Math.min(Math.max(y + i, 0), alto - 1);
                ventana[index++] = copiaMatriz[posY][x];
        }
        return ventana;
    }
    public int[] obtenerESCruz(int x, int y) {
        int mitad= tamES/2;
        int[] ventana= new int[tamES*2-1];
        int[] horizontal = obtenerESHorizontal(x,y);
        int[] vertical = obtenerESVertical(x,y);
        for (int i = 0; i < tamES; i++) { 
            ventana[i]=horizontal[i];
        }
        ventana[mitad]=vertical[0];
        int index=1;
        for (int i = tamES; i < tamES*2-1; i++) { 
            ventana[i]=vertical[index];
            index++;
        } 
        return ventana;
    }
    public int[] obtenerESEquis(int x, int y) {
        int mitad = tamES / 2;
        int[] ventana = new int[tamES * 2 - 1]; // Ajustamos el tamaño para evitar un índice extra
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
    public int[] obtenerESEquis(int x, int y,int tam) {
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


    public int[] obtenerESDiamante(int x, int y) {
        int mitad = tamES / 2;
        if(tamES==3){
            return obtenerESCruz(x,y);
        }
        int[] cruz = obtenerESCruz(x,y);
        int[] equis = obtenerESEquis(x,y,tamES-2);
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
    
    
    
    
    public void setTipoES(String tipoMascara){
        this.tipoES=tipoMascara;
    }
    public void setTamES(int tam){
        this.tamES=tam;
    }
    public void setMatrizImagen(int[][] imagenInt){
        this.copiaMatriz=imagenInt;
        initComponents();
    }
}
