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
public class Histograma {
    private int [][] imagenInt;
    private int [] hi;
    private int [] hiac;
    private double [] pi;
    private double [] piac;
    private final int L = 255;
    private double media;
    private double varianza;
    private double asimetria;
    private double energia;
    private double entropia;
    public Histograma(int [][] imagenInt) {
        this.imagenInt = imagenInt;
        hi = new int[L];
        hiac = new int[L];
        pi = new double[L];
        piac = new double[L];
    }
    private void obtenerHistograma() {
        for(int y=0; y<imagenInt.length; y++) {
            for(int x=0; x<imagenInt[y].length; x++) {
                int pixel = imagenInt[y][x];
                hi[pixel]++;
                }
            }
    }
    private void calcularProbabilidadI() {
        int alto = imagenInt.length;
        int ancho = imagenInt[0].length;
        for(int y=0; y<L; y++) {
            pi[y] = (double)hi[y] / (double)(alto*ancho);
            }
    }
    private void calcularMedia() {
        media = 0.0;
        double sumatoria = 0.0;
        for(int y=0; y<L; y++) {
            sumatoria += y*pi[y];
            }
        media = sumatoria;
    }
    private void calcularVarianza() {
        varianza = 0.0;
        double sumatoria = 0.0;
        for(int y=0; y<L; y++) {
            sumatoria += Math.pow((y-media), 2.0)*pi[y];
            }
        varianza = sumatoria;
    }
    private void calcularAsimetria() {
        asimetria = 0.0;
        double sumatoria = 0.0;
        for(int y=0; y<L; y++) {
            sumatoria += Math.pow((y-media), 3.0)*pi[y];
            }
        asimetria = sumatoria;
    }
    private void calcularEnergia() {
        energia = 0.0;
        double sumatoria = 0.0;
        for(int y=0; y<L; y++) {
            sumatoria += Math.pow(pi[y], 2.0);
            }
        energia = sumatoria;
    }
    private void calcularEntropia() {
        entropia = 0.0;
        double sumatoria = 0.0;
        for(int y=0; y<L; y++) {
            sumatoria += pi[y]*logDos(pi[y]);
            }
        entropia = -sumatoria;
    }
    public double logDos(double x) {
        double numeroUno = Math.log(x);
        if(x==0.0) {
            numeroUno = 1;
            }
        double numeroDos = Math.log(2);
        //System.out.println( x + "\t" + numeroUno + "\t" + numeroDos);
        return numeroUno / numeroDos;
    }
    private void obtenerHistogramaAcumulativo() {
        for(int i=0; i<L; i++) {
            hiac[i] = 0;
            }
        hiac[0] = hi[0];
        for(int i=1; i<L; i++) {
            hiac[i] = hiac[i-1] + hi[i];
            }
    }
    private void obtenerHistogramaPiAcumulativo() {
        for(int i=0; i<L; i++) {
            piac[i] = 0;
            }
        piac[0] = pi[0];
        for(int i=1; i<L; i++) {
            piac[i] = piac[i-1] + pi[i];
            }
    }
    public void ejecutarTodo() {
        obtenerHistograma();
        calcularProbabilidadI();
        calcularMedia();
        calcularVarianza();
        calcularAsimetria();
        calcularEnergia();
        calcularEntropia();
        obtenerHistogramaAcumulativo();
        obtenerHistogramaPiAcumulativo();
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("El histograma es: ").append("\n");
        for(int i=0; i<L; i++) {
            builder.append("hi[").append(i).append("]=").append(hi[i]);
            builder.append(" ");
            if(i%8==0 && i!=0) {
                builder.append("\n");
                }
            }
        builder.append("\n\n");
        builder.append("La probabilidad del histograma: ").append("\n");
        for(int i=0; i<L; i++) {
            builder.append("pi[").append(i).append("]=").append(pi[i]);
            builder.append(" ");
            if(i%4==0 && i!=0) {
                builder.append("\n");
                }
            }
        builder.append("\n\n");
        builder.append("Alto imagen: ").append(imagenInt.length).append("\n");
        builder.append("Ancho imagen: ").append(imagenInt[0].length).append("\n");
        builder.append("La media es: ").append(media).append("\n");
        builder.append("La varianza es: ").append(varianza).append("\n");
        double varianzaDos = Math.sqrt(varianza);
        builder.append("La varianza es: ").append(varianzaDos).append("\n");
        builder.append("La asimetria es: ").append(asimetria).append("\n");
        builder.append("La energia es: ").append(energia).append("\n");
        builder.append("La entropia es: ").append(entropia).append("\n");
        builder.append("\n\n");
        builder.append("El histograma acumulado es: ").append("\n");
        for(int i=0; i<L; i++) {
            builder.append("hiac[").append(i).append("]=").append(hiac[i]);
            builder.append(" ");
            if(i%8==0 && i!=0) {
                builder.append("\n");
                }
            }
        builder.append("\n\n");
        builder.append("La probabilidad de histograma acumulado es: \n");
        for(int i=0; i<L; i++) {
            builder.append("piac[").append(i).append("]=").append(piac[i]);
            builder.append(" ");
            if(i%4==0 && i!=0) {
                builder.append("\n");
                }
            }
        return builder.toString();
    }
    public int[] getHi(){
        return hi;
    }
    public int[] getHiac(){
        return hiac;
    }
    public double[] getPi() {
        return pi;
    }
    public double[] getPiac() {
        return piac;
    }

    public int getL() {
        return L;
    }
    public void setNuevaMatriz(int [][] nuevaMatriz){
        this.imagenInt=nuevaMatriz;
        reiniciar();
        ejecutarTodo();
    }
    public void reiniciar(){
    for (int i = 0; i < L; i++) {
        hi[i] = 0;
        hiac[i] = 0;
        pi[i] = 0.0;
        piac[i] = 0.0;
    }
    media = 0.0;
    varianza = 0.0;
    asimetria = 0.0;
    energia = 0.0;
    entropia = 0.0;
    }
}



