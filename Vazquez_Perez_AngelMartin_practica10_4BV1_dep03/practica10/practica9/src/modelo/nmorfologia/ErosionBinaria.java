/**
 * *************************************************************************
 *
 * BinaryErodeOp.java
 *
 **************************************************************************
 */
package temporal.morfologia;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 * Realiza la operacion morfologica de erosion sobre una imagen binaria. Aqui 
 * se entiende por "imagen binaria" una imagen en escala de grises con solo 
 * dos valores, uno de los cuales es cero y el otro 255.
 *
 * @version 1.0 [1999/08/30]
 */
public class ErosionBinaria extends MorfologiaBinaria {
    /**
     * Elemento estructurante utilizado para realizar la erosion.
     */
    private ElementoEstructuranteBinario structElement;
    /**
     * Crea un objeto BinaryErodeOp que utiliza el elemento estructurante 
     * especificado.
     *
     * @param element elemento estructurante
     */
    public ErosionBinaria(ElementoEstructuranteBinario element) {
        structElement = element;
    }

    /**
     * Realiza la operacion morfologica de erosion de una imagen binaria.
     *
     * @param source imagen fuente
     * @param destination imagen destino, o null
     * @return imagen erosionada.
     */
    @Override
    public BufferedImage filter(BufferedImage source, 
            BufferedImage destination) {
        checkImage(source);
        if(destination == null) {
            destination = createCompatibleDestImage(source, null);
            }

        int w = source.getWidth();
        int h = source.getHeight();
        Raster srcRaster = source.getRaster();
        WritableRaster destinationRaster = destination.getRaster();

        // Derermina el rango de los pixeles en los cuales se puede realizar la 
        // operacion
        Point origin = structElement.getOrigin(null);
        int xmin = Math.max(origin.x, 0);
        int ymin = Math.max(origin.y, 0);
        int xmax = origin.x + w - structElement.getWidth();
        int ymax = origin.y + h - structElement.getHeight();
        xmax = Math.min(w - 1, xmax);
        ymax = Math.min(h - 1, ymax);

        // LLena el elemento estructurante sobre la imagen
        for(int y = ymin; y <= ymax; ++y) {
            for(int x = xmin; x <= xmax; ++x) {
                if(structElement.fits(srcRaster, x, y)) {
                    destinationRaster.setSample(x, y, 0, nonZeroValue);
                    }
                }
            }
        return destination;
    }
}
