/**
 * *************************************************************************
 *
 * GreyErodeOp.java
 *
 **************************************************************************
 */
package temporal.morfologia;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 * Realiza la operacion morfologica de erosion en una imagen de niveles de gris.
 * 
 * @author yo
 * @version 1.0 [1999/08/31]
 */
public class ErosionGris extends EstandarGris {
    /**
     * Elemento estructurante utilizado para realizar la erosion.
     */
    private ElementoEstructuranteGris structElement;
    /**
     * Bandera para indicar cuando los valores de salida seran reescalados.
     */
    private boolean rescaling;
    /**
     * Crea un objeto ErosionGris que usa el elemento estructurante 
     * especificado. Los valores de salida seran truncados o reescalados
     * al rango de 0-255.
     *
     * @param element elemento estructurante
     */
    public ErosionGris(ElementoEstructuranteGris element) {
        this(element, false);
    }

    /**
     * Crea un objeto ErosionGris con el elemento estructurante especificado. 
     * Los valores de salida seran truncados o reescalados, acorde al valor de
     * la bandera booleana.
     *
     * @param element elemento estructurante
     * @param rescale bandera que indica cuando el reescalado es requerido.
     */
    public ErosionGris(ElementoEstructuranteGris element, boolean rescale) {
        structElement = element;
        rescaling = rescale;
    }

    /**
     * Realiza la erosion morfologica de una imagen en escala de grises.
     *
     * @param source imagen fuente
     * @param destination imagen destino, o null
     * @return imagen erosionada.
     */
    @Override
    public BufferedImage filter(BufferedImage source, BufferedImage destination) {
        checkImage(source);
        if(destination == null) {
            destination = createCompatibleDestImage(source, null);
            }

        int w = source.getWidth();
        int h = source.getHeight();
        Raster srcRaster = source.getRaster();
        WritableRaster destinationRaster = destination.getRaster();
        // Determina el rango de los pixeles para la que la operacion pueda
        // ser realizada
        Point origin = structElement.getOrigin(null);
        int xmin = Math.max(origin.x, 0);
        int ymin = Math.max(origin.y, 0);
        int xmax = origin.x + w - structElement.getWidth();
        int ymax = origin.y + h - structElement.getHeight();
        xmax = Math.min(w - 1, xmax);
        ymax = Math.min(h - 1, ymax);

        // Realiza la operacion
        if(rescaling) {
            // Almacena los valores de salida y determina el rango
            int[][] result = new int[h][w];
            int minimum = Integer.MAX_VALUE;
            int maximum = Integer.MIN_VALUE;
            for(int y = ymin; y <= ymax; ++y) {
                for(int x = xmin; x <= xmax; ++x) {
                    result[y][x] = structElement.below(srcRaster, x, y);
                    if(result[y][x] < minimum) {
                        minimum = result[y][x];
                        }
                    if(result[y][x] > maximum) {
                        maximum = result[y][x];
                        }
                    }
                }
            // Escribe los valores reescalados a la imagen destino.
            double scale = 255.0 / (maximum - minimum);
            for(int y = ymin; y <= ymax; ++y) {
                for(int x = xmin; x <= xmax; ++x) {
                    destinationRaster.setSample(x, y, 0,
                            Math.round(scale * (result[y][x] - minimum)));
                    }
                }
            } 
        else {
            int distance;
            for(int y = ymin; y <= ymax; ++y) {
                for(int x = xmin; x <= xmax; ++x) {
                    distance = structElement.below(srcRaster, x, y);
                    destinationRaster.setSample(x, y, 0, 
                            Math.max(0, Math.min(255, distance)));
                    }
                }
            }
        return destination;
    }
}
