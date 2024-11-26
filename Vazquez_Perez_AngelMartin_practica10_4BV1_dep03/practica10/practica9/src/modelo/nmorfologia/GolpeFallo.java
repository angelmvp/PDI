package temporal.morfologia;


/**
 * Clase que implementa la operacion morfologica golpear-fallar (hit-miss)
 * 
 * @author Yo
 * @version 1.0
 */
public class GolpeFallo {
    /**
     * Construye el objeto
     */
    public GolpeFallo() {
    
    }
    /**
     * Este metodo realiza la operacion hit-miss en la imagen binaria imagen.
     * Una imagen binaria tiene dos tipos de píxeles: blanco y negro.<br>
     * El píxel BLANCO tiene el valor ARGB (255,255,255,255)<br>
     * El píxel NEGRO tiene el valor ARGB (255,0,0,0)<br>
     * 
     * El arreglo en dos dimensiones de la mascara tiene tres tipos de 
     * valores.<br>
     * 0 para NEGRO, FONDO de la imagen<br>
     * 1 para BLANCO,PRIMER PLANO de la image<br>
     * 2 para NO IMPORTA<br>
     * 
     * @param imagen la imagen en la que se realiza la operacion binaria
     * @param mascara el arreglo en dos dimensiones de la mascara
     * @param tamanioMascara El tamanio cuadrado de la mascara, el numero de
     * filas o alto.
     */
    public void golpearFallarImagenBinaria(MiImagen imagen, int[] mascara, 
            int tamanioMascara) {
        /**
         * Dimension de la imagen.
         */
        int ancho = imagen.getImagenAncho();
        int alto = imagen.getImagenAlto();
        /**
         * Mantiene el resultado de la dilatacion que se copia en la imagen.
         */
        int[] outImagen = new int[ancho * alto];
        //ejecuta golpear-fallar (hit-miss)
        for(int y = 0; y < alto; y++){
            for(int x = 0; x < ancho; x++){
                //la bandera se establece si se encuentra una discrepancia
                boolean bandera = false;   
                for(int ty=y-(tamanioMascara/2), mr=0; 
                        ty<=y+(tamanioMascara/2) && 
                        bandera==false; ty++, mr++) {
                    for(int tx=x-(tamanioMascara/2), mc=0; 
                            tx<=x+(tamanioMascara/2) && 
                            bandera==false; tx++, mc++) {
                        //pixel bajo la mascara
                        if(ty>=0 && ty<alto && tx>=0 && tx<ancho) {
                            //el valor de la mascara no importa
                            if(mascara[mc+mr*tamanioMascara] > 1) {
                                continue;
                                }
                            //Si el pixel en la imagen no es el mismo de la
                            //mascara
                            if(imagen.getRed(tx, ty)!=
                                    (mascara[mc+mr*tamanioMascara]*255)) {
                                bandera = true;
                                outImagen[x+y*ancho] = 0;  //NEGRO
                                break;
                                }
                            }
                        }
                    }
                if(bandera == false) {
                    //todos los píxeles de la imagen debajo de la máscara han 
                    //coincidido
                    //Color de primer plano 
                    outImagen[x+y*ancho] = 255; // BLANCO
                    }
                }
            }
        
        /**
         * Salva el valor del golper-fallo (hit miss) en la imagen.
         */
        for(int y = 0; y < alto; y++){
            for(int x = 0; x < ancho; x++){
                int v = outImagen[x+y*ancho];
                imagen.setPixel(x, y, 255, v, v, v);
                }
            }
    }
    /**
     * Este método realiza la operacion hit-miss en la imagen binaria imagen.
     * Una imagen binaria tiene dos tipos de píxeles: blanco y negro.<br>
     * El píxel BLANCO tiene el valor ARGB (255,255,255,255)<br>
     * El píxel NEGRO tiene el valor ARGB (255,0,0,0)<br>
     * 
     * El arreglo en dos dimensiones de la mascara tiene tres tipos de 
     * valores.<br>
     * 0 para NEGRO, FONDO de la imagen<br>
     * 1 para BLANCO,PRIMER PLANO de la image<br>
     * 2 para NO IMPORTA<br>
     * 
     * @param imagen la imagen en la que se realiza la operacion binaria
     * @param mascara el arreglo en dos dimensiones de la mascara
     * @param tamanioMascara El tamanio cuadrado de la mascara, el numero de
     * filas o alto.
     * @return devuelve la imagen resultado de la dilatacion
     */
    public int[][] golpearFallarImagenBinaria(int [][] imagen, int[] mascara, 
            int tamanioMascara) {
        /**
         * Dimension de la imagen.
         */
        int alto = imagen.length;
        int ancho = imagen[0].length;
        /**
         * Mantiene el resultado de la dilatacion que se copia en la imagen.
         */
        int[][] outImagen = new int[alto][ancho];
        //ejecuta golpear-fallar (hit-miss)
        for(int y = 0; y < alto; y++){
            for(int x = 0; x < ancho; x++){
                //la bandera se establece si se encuentra una discrepancia
                boolean bandera = false;   
                for(int ty=y-(tamanioMascara/2), mr=0; 
                        ty<=y+(tamanioMascara/2) && 
                        bandera==false; ty++, mr++) {
                    for(int tx=x-(tamanioMascara/2), mc=0; 
                            tx<=x+(tamanioMascara/2) && 
                            bandera==false; tx++, mc++) {
                        //pixel bajo la mascara
                        if(ty>=0 && ty<alto && tx>=0 && tx<ancho) {
                            //el valor de la mascara no importa
                            if(mascara[mc+mr*tamanioMascara] > 1) {
                                continue;
                                }
                            //Si el pixel en la imagen no es el mismo de la
                            //mascara
                            //if(ty>=0 && ty<alto-2 && tx>=0 && tx<ancho-2) {
                                // DEBUG
                                //System.out.println("ty " + ty + " tx " + tx);
                                if(imagen[ty][tx]!=
                                        (mascara[mc+mr*tamanioMascara]*255)) {
                                    bandera = true;
                                    //outImagen[x+y*ancho] = 0;  //NEGRO
                                    outImagen[y][x] = 0; // NEGRO
                                    break;
                                    }
                            //    }
                            }
                        }
                    }
                if(bandera == false) {
                    //todos los píxeles de la imagen debajo de la máscara han 
                    //coincidido
                    //Color de primer plano 
                    //outImagen[x+y*ancho] = 255; // BLANCO
                    outImagen[y][x] = 255; // BLANCO
                    }
                }
            }
        /**
         * Salva el valor del golper-fallo (hit miss) en la imagen.
         */
//        for(int y = 0; y < alto; y++){
//            for(int x = 0; x < ancho; x++){
//                int v = outImagen[x+y*ancho];
//                imagen.setPixel(x, y, 255, v, v, v);
//                }
//            }
        return outImagen;
    }
}
