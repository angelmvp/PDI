/**
 * *************************************************************************
 *
 * DilatacionGris.java ---- GreyDilateOp.java
 *
 **************************************************************************
 */
package temporal.morfologia;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 * Realiza la operacion morfologica de dilatacion en una imagen en escala de 
 * grises.
 *
 * @author yo
 * @version 1.0 [1999/08/31]
 */
public class DilatacionGris extends EstandarGris {
    /**
     * Elemento estructurante usado para realizar la erosion.
     */
    private ElementoEstructuranteGris structElement;
    /**
     * Bandera que indica cuando los valores de salida son reescalados.
     */
    private boolean rescaling;
    /**
     * Crea un objeto DilatacionGris con el elemento estructurante 
     * especificado. Los valores de salida seran truncados o reescalados al
     * rango 0-255.
     *
     * @param element elemento estructurante
     */
    public DilatacionGris(ElementoEstructuranteGris element) {
        this(element, false);
    }

    /**
     * Crea un objeto DilatacionGris con el elemento estructurante 
     * especificado. Los valores de salida seran truncados o reescalados de 
     * acuerdo al valor de la bandera bolleana.
     *
     * @param element elemento estructurante
     * @param rescale bandera que indica cuando el reescalado es requerido
     */
    public DilatacionGris(ElementoEstructuranteGris element, boolean rescale) {
        structElement = element;
        rescaling = rescale;
    }

    /**
     * Realiza la operacion morfologica de dilatacion a una imagen en niveles 
     * de gris.
     *
     * @param source imagen fuente
     * @param destination imagen destino, o null
     * @return devuelve la imagen dilatada.
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
        // Determina el rango de los pixeles para los cuales la operacion
        // se llevara a cabo
        Point origin = structElement.getOrigin(null);
        int xmin = Math.max(origin.x, 0);
        int ymin = Math.max(origin.y, 0);
        int xmax = origin.x + w - structElement.getWidth();
        int ymax = origin.y + h - structElement.getHeight();
        xmax = Math.min(w - 1, xmax);
        ymax = Math.min(h - 1, ymax);
        // Realiza la operacion
        if(rescaling) {
            // Almacena los valores de salida y determina su rango
            int[][] result = new int[h][w];
            int minimum = Integer.MAX_VALUE;
            int maximum = Integer.MIN_VALUE;
            for(int y = ymin; y <= ymax; ++y) {
                for(int x = xmin; x <= xmax; ++x) {
                    result[y][x] = structElement.above(srcRaster, x, y);
                    if(result[y][x] < minimum) {
                        minimum = result[y][x];
                        }
                    if(result[y][x] > maximum) {
                        maximum = result[y][x];
                        }
                    }
                }
            // Escribe los valores reescalados en la imagen destino
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
                    distance = structElement.above(srcRaster, x, y);
                    destinationRaster.setSample(x, y, 0, 
                            Math.max(0, Math.min(255, distance)));
                    }
                }
            }
        return destination;
    }
}
