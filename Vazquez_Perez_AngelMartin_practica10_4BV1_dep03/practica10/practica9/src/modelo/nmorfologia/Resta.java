package temporal.morfologia;

/**
 * Esta clase realiza la resta de pixeles de dos imagenes en una tercera.
 * 
 * @author Yo
 * @version 1.0
 */
public class Resta {
    /**
     * Crea el objeto
     */
    public Resta() {
    
    }
    /**
     * Este metodo resta los pixeles de la imagen binaria. Toma dos imágenes 
     * binarias de igual tamano y resta sus pixeles. El resultado de la resta 
     * se guarda en resultImg, que tambien tiene el mismo tamano.
     * 
     * @param imagenUno La imagen uno.
     * @param imagenDos La imagen dos.
     * @param imagenResultante Retiene el resultado de resta de imagenUno e 
     * imagenDos.
     */
    public void restarImagenBinaria(MiImagen imagenUno, MiImagen imagenDos, 
            MiImagen imagenResultante){
        /**
         * Tenga en cuenta que la sustraccion de pixeles de la imagen en 
         * escala de grises y binaria es casi la misma. Entonces, llamando al 
         * metodo Image() en escala de grises
         */
        grayscaleImage(imagenUno, imagenDos, imagenResultante);
    }
    
    /**
     * This method will subtract the pixels of the grayscale image.
     * It takes two equal size grayscale images and subtracts their pixels.
     * The result of subtraction is saved in resultImg which is also of the 
     * same size.
     * 
     * @param sourceImg1 primera imagen.
     * @param sourceImg2 segunda imagen.
     * @param resultImg aqui se almacena la resta de la imagen uno y la imagen 
     * dos.
     */
    public void grayscaleImage(MiImagen sourceImg1, MiImagen sourceImg2, 
            MiImagen resultImg){
        //image dimension - common for all the three images
        int ancho = sourceImg1.getImagenAncho();
        int alto = sourceImg1.getImagenAlto();
        
        //variable
        int p, resultado;
        
        /**
         * resta de pixeles
         * Imagen en estala de gris tiene el mismo valor para cada canal RGB
         * tomado.
         */
        for(int y = 0; y < alto; y++){
            for(int x = 0; x < ancho; x++){
                p = Math.abs(sourceImg1.getBlue(x, y) - 
                             sourceImg2.getBlue(x, y));
                resultado = (p<0) ? 0 : p;
                resultImg.setPixel(x, y, 255, resultado, resultado, resultado);
            }
        }
    }
    
    /**
     * Este metodo resta los pixeles de la imagen binaria. Toma dos imágenes 
     * binarias de igual tamano y resta sus pixeles. El resultado de la resta 
     * se guarda en resultImg, que tambien tiene el mismo tamano.
     * 
     * @param imagenUno La imagen uno.
     * @param imagenDos La imagen dos.
     * @param imagenResultante Retiene el resultado de resta de imagenUno e 
     * imagenDos.
     */
    public void restarImagenBinaria(int[][] imagenUno, int[][] imagenDos, 
            int[][] imagenResultante){
        /**
         * Tenga en cuenta que la sustraccion de pixeles de la imagen en 
         * escala de grises y binaria es casi la misma. Entonces, llamando al 
         * metodo Image() en escala de grises
         */
        grayscaleImage(imagenUno, imagenDos, imagenResultante);
    }
    
    /**
     * Este metodo restara los pixeles de la imagen en escala de grises.
     * Toma dos imagenes en escala de grises del mismo tamaño y resta sus 
     * pixeles. El resultado de la resta se guarda en resultImg, que 
     * también es del mismo tamaño.
     * 
     * @param sourceImg1 La primer imagen.
     * @param sourceImg2 La segunda imagen.
     * @param resultImg Esto contendra la imagen resultante despues de restar 
     * la imagen uno y la imagen dos.
     */
    public void grayscaleImage(int[][] sourceImg1, int[][] sourceImg2, 
            int [][] resultImg) {
        
        //Dimension de la imagen: comun para las tres imagenes.
        //int width = sourceImg1.getImagenAncho();
        //int height = sourceImg1.getImagenAlto();
        int width = sourceImg1[0].length;
        int height = sourceImg1.length;
        
        //variable
        int p, result;
        
        /**
         * resta los pixeles de la imagen en escala de grises tendra el mismo 
         * valor para RGB, asi que tome cualquier componente.
         */
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                //p = Math.abs(sourceImg1.getBlue(x, y) - 
                //             sourceImg2.getBlue(x, y));
                p = Math.abs(sourceImg1[y][x] - sourceImg2[y][x]);
                result = (p<0) ? 0 : p;
                //resultImg.setPixel(x, y, 255, result, result, result);
                resultImg[y][x] = result;
                }
            }
    }
}
