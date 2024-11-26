/**
 * *************************************************************************
 *
 * StandardGreyOp.java
 *
 **************************************************************************
 */
package temporal.morfologia;

import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.ImagingOpException;
import java.awt.image.WritableRaster;

/**
 * Implementa BufferedImageOp para operaciones estandar en imagenes en escala 
 * de grises de 8 bits. Las subclases deben anular la funcion
 * {link #filter(BufferedImage, RenderingHints) filter()} para realizar su
 * procesamiento
 *
 * @author yo
 * @version 1.1 [1999/07/23]
 */
public class EstandarGris implements BufferedImageOp {
    /**
     * Realiza una operacion sobre una imagen. Aqui, la operacion es una simple 
     * copia del origen al destino. Las subclases deben anular este metodo para 
     * producir un comportamiento mas interesante.
     *
     * @param source imagen fuente
     * @param destination imagen destino, o null
     * @return imagen destino.
     */
    @Override
    public BufferedImage filter(BufferedImage source, 
            BufferedImage destination) {
        checkImage(source);
        if(destination == null) {
            destination = createCompatibleDestImage(source, null);
            }
        WritableRaster raster = destination.getRaster();
        source.copyData(raster);
        return destination;
    }

    /**
     * Crea una imagen de destino puesta a cero con las mismas dimensiones y 
     * numero de bandas que la imagen de origen.
     *
     * @param source imagen fuente
     * @param destinationModel ColorModel de la imagen de destino (si es nulo, 
     * se utilizara ColorModel de la imagen de origen)
     */
    @Override
    public BufferedImage createCompatibleDestImage(BufferedImage source,
            ColorModel destinationModel) {
        if(destinationModel == null) {
            destinationModel = source.getColorModel();
            }
        int width = source.getWidth();
        int height = source.getHeight();
        BufferedImage image = new BufferedImage(destinationModel,
                destinationModel.createCompatibleWritableRaster(width, height),
                destinationModel.isAlphaPremultiplied(), null);
        return image;
    }

    /**
     * Determina el cuadro delimitador de la imagen de destino. Aqui se supone 
     * que es identica a la de la imagen de origen.
     *
     * @param source imagen fuente
     * @return cuadro delimitador de la imagen de destino.
     */
    @Override
    public Rectangle2D getBounds2D(BufferedImage source) {
        return source.getRaster().getBounds();
    }

    /**
     * Dado un punto en la imagen de origen, determina el punto correspondiente 
     * en la imagen de destino. Aqui, se supone que sera el mismo punto en la 
     * imagen de origen.
     *
     * @param sourcePoint un punto de la imagen fuente.
     * @param destinationPoint un punto en la imagen de destino, o nulo.
     * @return punto de retorno en la imagen de destino.
     */
    @Override
    public Point2D getPoint2D(Point2D sourcePoint, Point2D destinationPoint) {
        if(destinationPoint == null) {
            destinationPoint = new Point2D.Float();
            }
        destinationPoint.setLocation(sourcePoint.getX(), sourcePoint.getY());
        return destinationPoint;
    }

    /**
     * Devuelce el rendereado
     * @return representacion de sugerencias para esta operacion.
     */
    @Override
    public RenderingHints getRenderingHints() {
        return null;
    }

    /**
     * Prueba la imagen fuente para saber si es posible precesarla.
     *
     * @param source imagen fuente
     * @throws ImagingOpException si la imagen fuente no es de 8-bits en
     * escala de grises.
     */
    public void checkImage(BufferedImage source) {
        if(source.getType() != BufferedImage.TYPE_BYTE_GRAY) {
            throw new ImagingOpException(
                    "la operacion requiere una image gris de 8-bits.");
            }
    }
}
