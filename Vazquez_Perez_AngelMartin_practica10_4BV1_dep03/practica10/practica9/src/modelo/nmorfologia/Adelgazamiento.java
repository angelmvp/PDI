package temporal.morfologia;


/**
 * Clase que implementa la operacion morfologica de adelgazamiento (thinning)
 * 
 * @author Yo
 * @version 1.0
 */
public class Adelgazamiento {
    /**
     * Crea el objeto
     */
    public Adelgazamiento() {
    
    }
    /**
     * Este metodo realiza la operacion binaria de adelgazamiento (thinning)
     * Funcionamiento
     * Si consideramos una imagen F y una máscara M, entonces el 
     * adelgazamiento T se puede expresar como: 
     * T(F,M) = F - golpearFallar(F,M)
     * T = Transformar
     * F = Imagen
     * @param imagen La imagen binaria sobre la que se adelgaza.
     * @param mascara La mascara empleada para adelgazar (thinning). La mascara 
     * es una matriz en dos dimensiones almacenada en un arreglo
     * unidimensional
     * @param tamanioMascara El tamano de la mascara. 
     * [Numero de filas en la mascara en dos dimensiones * matriz]
     */
    public void adelgazarImagenBinaria(MiImagen imagen, int [] mascara, 
            int tamanioMascara){
        GolpeFallo golpeFallo = new GolpeFallo();
        Resta resta = new Resta();
        MiImagen tmp = new MiImagen(imagen);
        golpeFallo.golpearFallarImagenBinaria(tmp, mascara, tamanioMascara);
        resta.restarImagenBinaria(imagen, tmp, imagen);
    }
    /**
     * Este metodo realiza la operacion binaria de adelgazamiento (thinning)
     * Funcionamiento
     * Si consideramos una imagen F y una máscara M, entonces el 
     * adelgazamiento T se puede expresar como: 
     * T(F,M) = F - golpearFallar(F,M)
     * T = Transformar
     * F = Imagen
     * @param imagen La imagen binaria sobre la que se adelgaza.
     * @param mascara La mascara empleada para adelgazar (thinning). La mascara 
     * es una matriz en dos dimensiones almacenada en un arreglo
     * unidimensional
     * @param tamanioMascara El tamano de la mascara. 
     * [Numero de filas en la mascara en dos dimensiones * matriz]
     */
    public void adelgazarImagenBinaria(int [][] imagen, int [] mascara, 
            int tamanioMascara){
        GolpeFallo golpeFallo = new GolpeFallo();
        Resta resta = new Resta();
        //MiImagen tmp = new MiImagen(imagen);
        int alto = imagen.length;
        int ancho = imagen[0].length;
        int [][] tmpImagen = new int[alto][ancho];
        for(int y=0; y<alto; y++) {
            for(int x=0; x<ancho; x++) {
                tmpImagen[y][x] = imagen[y][x];
                }
            }
        
        tmpImagen = golpeFallo.golpearFallarImagenBinaria(
                                tmpImagen, mascara, tamanioMascara);
        resta.restarImagenBinaria(imagen, tmpImagen, imagen);
    }
}
