/**
 * *************************************************************************
 *
 * BinaryMorphologicalOp.java
 *
 **************************************************************************
 */
package temporal.morfologia;

import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.awt.image.Raster;

/**
 * Adapta EstandarGris para operaciones binarias morfologicas. El metodo
 * checkImage() es oculto y verifica que la imagen fuente sea una imagen binaria
 * o en escala de grises con no mas de dos valores de gris.
 *
 * @author yo
 * @version 1.0 [1999/08/30]
 */
public class MorfologiaBinaria extends EstandarGris {
    /**
     * Valor distinto de cero asignado a los pixeles de la imagen de salida.
     */
    protected int nonZeroValue;
    /**
     * Verifica que la image a procesar este correcta - binaria o dos niveles
     * o a 8 bits en escala de grises con valores de 0 como uno de los valores.
     *
     * @param image imagen a ser procesada.
     * @throws ImagingOpException si la imagen no tiene el tipo correcto.
     */
    @Override
    public void checkImage(BufferedImage image) {
        if (image.getType() == BufferedImage.TYPE_BYTE_BINARY) {
            nonZeroValue = 1;
            } 
        else 
        if (image.getType() == BufferedImage.TYPE_BYTE_GRAY) {
            nonZeroValue = 255;
            Raster raster = image.getRaster();
            int[] histogram = new int[256];
            for(int y = 0; y < image.getHeight(); ++y) {
                for(int x = 0; x < image.getWidth(); ++x) {
                    ++histogram[raster.getSample(x, y, 0)];
                    }
                }
            int n = 0;
            for(int i = 1; i < 256; ++i) {
                if(histogram[i] > 0) {
                    ++n;
                    if(n > 1) {
                        throw new ImagingOpException(
                                "imagen debe ser binaria, o gris con <= "
                                        + "un valor distinto de cero");
                        }
                    }
                }
            } 
        else {
            throw new ImagingOpException("tipo de imagen no valido " + 
                    image.getType() + " " + BufferedImage.TYPE_BYTE_BINARY);
            }
    }
}
