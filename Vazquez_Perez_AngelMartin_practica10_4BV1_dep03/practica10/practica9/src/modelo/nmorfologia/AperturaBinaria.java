/**
 * *************************************************************************
 *
 * AperturaBinaria.java ---- BinaryOpenOp.java
 *
 **************************************************************************
 */
package temporal.morfologia;

import java.awt.image.BufferedImage;

/**
 * Realiza la operacion morfologica de apertura en una imagen binaria. 'Imagen
 * binaria' se toma como una imagen en escala de grises, solo con dos valores
 * y son cero 0 y 255.
 *
 * @author yo
 * @version 1.0 [1999/08/30]
 */
public class AperturaBinaria extends MorfologiaBinaria {
    /**
     * Elemento estructurante para realizar la apertura.
     */
    private ElementoEstructuranteBinario structElement;
    /**
     * Crea un objeto de AperturaBinaria que emplea el elemento estructurante
     * especificado.
     *
     * @param element elemento estructurante
     */
    public AperturaBinaria(ElementoEstructuranteBinario element) {
        structElement = element;
    }
    /**
     * Realiza la operacion morfologica de apertura de una imagen binaria.
     *
     * @param source imagen fuente
     * @param destination imagen destino, o null
     * @return imagen aperturada.
     */
    @Override
    public BufferedImage filter(BufferedImage source, 
            BufferedImage destination) {
        ErosionBinaria erodeOp = new ErosionBinaria(structElement);
        BufferedImage erodedImage = erodeOp.filter(source, null);
        DilatacionBinaria dilateOp = new DilatacionBinaria(structElement);
        if(destination == null) {
            destination = createCompatibleDestImage(source, null);
            }
        return dilateOp.filter(erodedImage, destination);
    }
}
