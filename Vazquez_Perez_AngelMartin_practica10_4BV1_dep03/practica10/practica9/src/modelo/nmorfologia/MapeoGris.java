/**
 * *************************************************************************
 *
 * MapeoGris.java ---- GreyMapOp.java
 *
 **************************************************************************
 */
package temporal.morfologia;

import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.LookupOp;

/**
 * Realiza un mapeo arbitrario de niveles de gris en un objeto BufferedImage,
 * empleando una tabla lut. La imagen resultante esta en 8 bits de resolucion
 * en niveles de gris.
 * <p>
 * Esta es una clase abstracta; las subclases concretas deberan implementar
 * el metodo {@link #computeMapping(int, int) computeMapping()}, el cual
 * genera los datos para la lut.</p>
 *
 * @author yo
 * @version 2.1 [1999/07/23]
 */
public abstract class MapeoGris extends EstandarGris {
    /**
     * Lookup tabla (datos).
     */
    protected byte [] table = new byte[256];
    /**
     * devuelve la tabla lut.
     *
     * @param i indice a la tabla lut buscado
     * @return devuelve el valor almacenado en el indice especificado.
     */
    public int getTableEntry(int i) {
        if(table[i]<0) {
            return 256 + (int) table[i];
            } 
        else {
            return (int) table[i];
            }
    }

    /**
     * Modifica la entrada a la tabla lut en el indice indicado.
     *
     * @param i indice a la tabla lut
     * @param value valor a almacenar en el indice especificado (fuerza a que 
     * este en el rango 0-255 si es necesario)
     */
    protected void setTableEntry(int i, int value) {
        if(value<0) {
            table[i] = (byte) 0;
            } 
        else 
        if(value>255) {
            table[i] = (byte) 255;
            } 
        else {
            table[i] = (byte) value;
            }
    }

    /**
     * Calcula el mapeo del nivel de gris con limites superior e inferior.
     *
     * @param low limite inferior, mapea a0
     * @param high limite superior, mapea a 255
     */
    public abstract void computeMapping(int low, int high);

    /**
     * Calcula un mapeo de nivel de gris en el reango 0 a 255.
     */
    public void computeMapping() {
        computeMapping(0, 255);
    }

    /**
     * Realiza el mapeo de niveles de gris en una imagen.
     *
     * @param source imagen fuente
     * @param destination imagen destino, o null
     * @return devuelve la imagen mapeada.
     */
    @Override
    public BufferedImage filter(BufferedImage source, 
            BufferedImage destination) {
        checkImage(source);
        if(destination==null) {
            destination = createCompatibleDestImage(source, null);
            }
        LookupOp operation = new LookupOp(new ByteLookupTable(0, table), null);
        operation.filter(source, destination);
        return destination;
    }
}
