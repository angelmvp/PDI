/**
 * *************************************************************************
 *
 * CierreGris.java ---- GreyCloseOp.java
 *
 **************************************************************************
 */
package temporal.morfologia;

import java.awt.image.BufferedImage;

/**
 * Realiza la operacion morfologica de cierre en una imagen en escala de grises.
 *
 * @author yo
 * @version 1.0 [1999/08/31]
 */
public class CierreGris extends EstandarGris {
    /**
     * Elemento estructurante para realizar la operacion de cierre.
     */
    private ElementoEstructuranteGris structElement;
    /**
     * Bandera que indica cuando los valores de salida son reescalados.
     */
    private boolean rescaling;
    /**
     * Crea un objeto GreyCloseOp que emplea el elemento estructurante
     * especificado. Los valores de salida seran truncados al rango de 0-255.
     *
     * @param element elemento estructurante
     * 
     */
    public CierreGris(ElementoEstructuranteGris element) {
        this(element, false);
    }

    /**
     * Crea un objeto GreyCloseOp que emplea el elemento estructurante. 
     * Los valores de salida seran trncados o reescalados, acorde al valor del 
     * parametro booleano.
     *
     * @param element elemento estructurante a ser empleado
     * @param rescale bandera que indica cuando los valores seran reescalados
     */
    public CierreGris(ElementoEstructuranteGris element, boolean rescale) {
        structElement = element;
        rescaling = rescale;
    }

    /**
     * REaliza el cierre morfologico de una imagen en escala de grises.
     *
     * @param source imagen fuente
     * @param destination imagen destino, o null
     * @return devuelve el cierre de la imagen en un objeto BufferedImage.
     */
    @Override
    public BufferedImage filter(BufferedImage source, 
            BufferedImage destination) {
        DilatacionGris dilateOp = new DilatacionGris(structElement, rescaling);
        BufferedImage dilatedImage = dilateOp.filter(source, null);
        ErosionGris erodeOp = new ErosionGris(structElement, rescaling);
        if(destination == null) {
            destination = createCompatibleDestImage(source, null);
            }
        return erodeOp.filter(dilatedImage, destination);
    }
}
