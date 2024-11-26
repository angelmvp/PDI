/**
 **************************************************************************
 *
 * DilatacionBinaria.java ---- BinaryDilateOp.java
 *
 **************************************************************************
 */
package temporal.morfologia;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 * Realiza la operación morfologica de dilatacion sobre una imagen binaria. 
 * "Imagen binaria" se entiende aqui como una imagen en escala de grises con 
 * solo dos valores, uno de los cuales es cero y el otro es doscientos 
 * cincuenta y cinco.
 *
 * @author yo
 * @version 1.0 [1999/08/30]
 */
public class DilatacionBinaria extends MorfologiaBinaria {
    /**
     * Elemento estructurante utilizado para realizar la dilatación.
     */
    private ElementoEstructuranteBinario structElement;
    /**
     * Crea un objeto BinaryDilateOp que utiliza el elemento estructurante 
     * especificado.
     *
     * @param element elemento estructurante
     */
    public DilatacionBinaria(ElementoEstructuranteBinario element) {
        structElement = element;
    }

    /**
     * Realiza dilatacion morfologica de una imagen binaria.
     *
     * @param source imagen fuente
     * @param destination imagen destino, o null
     * @return imagen dilatada.
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
        WritableRaster destinarionRaster = destination.getRaster();

        // Determinar el rango de pixeles para los cuales se puede realizar 
        // la operacion
        Point origin = structElement.getOrigin(null);
        int xmin = Math.max(origin.x, 0);
        int ymin = Math.max(origin.y, 0);
        int xmax = origin.x + w - structElement.getWidth();
        int ymax = origin.y + h - structElement.getHeight();
        xmax = Math.min(w - 1, xmax);
        ymax = Math.min(h - 1, ymax);

        // Intersectar elemento estructurante con imagen de origen
        for(int y = ymin; y <= ymax; ++y) {
            for(int x = xmin; x <= xmax; ++x) {
                if(structElement.hits(srcRaster, x, y)) {
                    destinarionRaster.setSample(x, y, 0, nonZeroValue);
                    }
                }
            }
        return destination;
    }
}
