package temporal.morfologia;


/**
 * Clase que crea una imagen mas delgada esqueleto. Utiliza el concepto 
 * morfologico de adelgazamiento (thinning).
 * 
 * @author Yo
 * @version 1.0
 */
public class Esqueletizado implements Mascara {
    /**
     * Imagen delgada hasta que no se produzca ningun cambio.
     */
    private MiImagen tmpImg;
    /**
     * Imagen entera temporal, se adelgaza hasta que no se produzca ningun 
     * cambio.
     */
    private int [][] tmpImagen;
    /**
     * Crea el objeto de esqueletizado
     */
    public Esqueletizado() {
    
    }
    /**
     * Este metodo realiza la operación de esqueletizacion (adelgazamiento o
     * thinning) en la imagen binaria imagen.
     * 
     * @param imagen La imagen a adelgazar (thinning).
     */
    public void esqueletizarImagenBinaria(MiImagen imagen) {
        
        int count = 0;
        Adelgazamiento adelgazamiento = new Adelgazamiento();
        int tamanio = 3;
        do{
            tmpImg = new MiImagen(imagen);
            
            //adelgaza con la mascara uno mascaraTrianguloAbajo
            adelgazamiento.adelgazarImagenBinaria(
                    imagen, mascaraTrianguloAbajo,        tamanio);
            
            //adelgaza con la mascara cinco mascaraArcoIzquierdoInferior
            adelgazamiento.adelgazarImagenBinaria(
                    imagen, mascaraArcoIzquierdoInferior, tamanio);
            
            //adelgaza con el resto de las mascaras
            adelgazamiento.adelgazarImagenBinaria(
                    imagen, mascaraTrianguloDerecha,      tamanio);
            adelgazamiento.adelgazarImagenBinaria(
                    imagen, mascaraTrianguloArriba,       tamanio);
            adelgazamiento.adelgazarImagenBinaria(
                    imagen, mascaraTrianguloIzquierda,    tamanio);
            adelgazamiento.adelgazarImagenBinaria(
                    imagen, mascaraArcoDerechoInferior,   tamanio);
            adelgazamiento.adelgazarImagenBinaria(
                    imagen, mascaraArcoDerechaSuperior,   tamanio);
            adelgazamiento.adelgazarImagenBinaria(
                    imagen, mascaraArcoIzquierdoSuperior, tamanio);
            
            count++;
            System.out.println("Ciclo de esqueletizado : " + count + " veces.");
        } while(imagen.isEqual(tmpImg) == false);   
    }
    /**
     * Este metodo realiza la operacion de esqueletizacion (adelgazamiento o
     * thinning) en la imagen binaria imagen.
     * 
     * @param imagen La imagen a adelgazar (thinning).
     */
    public void esqueletizarImagenBinaria(int [][] imagen) {
        
        int count = 0;
        Adelgazamiento adelgazamiento = new Adelgazamiento();
        int tamanio = 3;
        int alto = imagen.length;
        int ancho = imagen[0].length;
        tmpImagen = new int[alto][ancho];
        do {
            //tmpImg = new MiImagen(imagen);
            for(int y=0; y<alto; y++) {
                for(int x=0; x<ancho; x++) {
                    tmpImagen[y][x] = imagen[y][x];
                    }
                }
            //adelgaza con la mascara uno mascaraTrianguloAbajo
            adelgazamiento.adelgazarImagenBinaria(
                    imagen, mascaraTrianguloAbajo,        tamanio);
            
            //adelgaza con la mascara cinco mascaraArcoIzquierdoInferior
            adelgazamiento.adelgazarImagenBinaria(
                    imagen, mascaraArcoIzquierdoInferior, tamanio);
            
            //adelgaza con el resto de las mascaras
            adelgazamiento.adelgazarImagenBinaria(
                    imagen, mascaraTrianguloDerecha,      tamanio);
            adelgazamiento.adelgazarImagenBinaria(
                    imagen, mascaraTrianguloArriba,       tamanio);
            adelgazamiento.adelgazarImagenBinaria(
                    imagen, mascaraTrianguloIzquierda,    tamanio);
            adelgazamiento.adelgazarImagenBinaria(
                    imagen, mascaraArcoDerechoInferior,   tamanio);
            adelgazamiento.adelgazarImagenBinaria(
                    imagen, mascaraArcoDerechaSuperior,   tamanio);
            adelgazamiento.adelgazarImagenBinaria(
                    imagen, mascaraArcoIzquierdoSuperior, tamanio);
            
            count++;
            System.out.println("Ciclo de esqueletizado : " + count + " veces.");
        } while(isEqual(imagen, tmpImagen) == false); 
        // vaciamos la imagen binarizada en la temporal
        for(int y=0; y<alto; y++) {
            for(int x=0; x<ancho; x++) {
                tmpImagen[y][x] = imagen[y][x];
                }
            }
    }

    /**
     * Devuelve la imagel
     * @return devuelve la imagen temporal
     */
    public int[][] getTmpImagen() {
        return tmpImagen;
    }
    
    /**
     * Este metodo compara si dos imagenes int [][] son iguales.
     * Si se tiene dos imagenes img1 e img2
     * El llamado isEqual(img1, img2) devuelve true si img1 e img2 son iguales 
     * en caso contrario devuelve false.
     * 
     * @param imagenUno La imagen uno a verificar con la imagen dos.
     * @param imagenDos La imagen dos a verificar con la imagen uno.
     * @return true si las dos imagenes son iguales, si no false.
     */
    boolean isEqual(int [][] imagenUno, int [][] imagenDos) {
        int altoUno = imagenUno.length;
        int anchoUno = imagenUno[0].length;
        int altoDos = imagenDos.length;
        int anchoDos = imagenDos[0].length;
        //verifica las dimensiones
        if(anchoUno!=anchoDos || altoUno!=altoDos) {
            return false;
            }
        
        //verifica los valores de pixel de cada imagen
        for(int y=0; y<altoUno; y++) {
            for(int x=0; x<anchoUno; x++) {
                if(imagenUno[y][x]!= imagenDos[y][x]) {
                    return false;
                    }
                }
            }
        
        return true;
    }
}
