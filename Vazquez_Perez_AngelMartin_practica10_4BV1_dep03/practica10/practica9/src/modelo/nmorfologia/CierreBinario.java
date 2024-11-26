/**
 * *************************************************************************
 *
 * CierreBinario.java ---- BinaryCloseOp.java
 *
 **************************************************************************
 */
package temporal.morfologia;

import java.awt.image.BufferedImage;

/**
 * Realiza la operacion morfologica de cierre sobre una imagen binaria. Aqui 
 * se entiende por "imagen binaria" una imagen en escala de grises con solo 
 * dos valores, uno de los cuales es cero y el otro 255.
 *
 * @author yo
 * @version 1.0 [1999/08/30]
 */
public class CierreBinario extends MorfologiaBinaria {
    /**
     * Elemento estructurante utilizado para realizar el cierre.
     */
    private ElementoEstructuranteBinario structElement;
    /**
     * Crea un objeto BinaryCloseOp que utiliza el elemento estructurante 
     * especificado.
     *
     * @param element elemento estructurante
     */
    public CierreBinario(ElementoEstructuranteBinario element) {
        structElement = element.getRotatedVersion();
    }
    /**
     * Realiza el cierre morfológico de una imagen binaria.
     *
     * @param source imagen fuente
     * @param destination imagen destino, o null
     * @return cierre de la imagen.
     */
    @Override
    public BufferedImage filter(BufferedImage source, 
            BufferedImage destination) {
        DilatacionBinaria dilateOp = new DilatacionBinaria(structElement);
        BufferedImage dilatedImage = dilateOp.filter(source, null);
        ErosionBinaria erodeOp = new ErosionBinaria(structElement);
        if(destination == null) {
            destination = createCompatibleDestImage(source, null);
            }
        return erodeOp.filter(dilatedImage, destination);
    }
}
