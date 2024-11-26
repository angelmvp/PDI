/**
 * *************************************************************************
 *
 * ElementoEstructurante.java ---- StructElement.java
 *
 **************************************************************************
 */
package temporal.morfologia;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Objects;

/**
 * Clase base abstracta para el elemento estructurante usado en el
 * procesamiento morfologico de imagen.
 *
 * @author yo
 * @version 2.0 (2023/07/21) [1999/08/31]
 */
public abstract class ElementoEstructurante {
    /**
     * Ancho del elemento estructurante.
     */
    protected int width;
    /**
     * Alto del elemento estructurante.
     */
    protected int height;
    /**
     * Origen del elemento estructurante relativo a la esquina superior 
     * izquierda.
     */
    protected Point origin;
    /**
     * Informacion del elemento estructurante (1 o 0 para un SE binario o
     * enteroa arbitrarios pata SE gris).
     */
    protected int[][] pixel;
    /**
     * Constructor
     * @throws temporal.morfologia.ElementoEstructuranteException si el se esta 
     * mal creado
     */
    public ElementoEstructurante() throws ElementoEstructuranteException {
        this(3, 3);
    }

    /**
     * Crea un SE con las dimensiones especificadas.
     *
     * @param w ancho deseado del SE
     * @param h alto deseado del SE
     * @throws ElementoEstructuranteException por si las dimensiones del SE no 
     * son validas.
     */
    public ElementoEstructurante(int w, int h) 
            throws ElementoEstructuranteException {
        if(w < 1 || h < 1) {
            throw new ElementoEstructuranteException(
                    "invalid structuring element dimensions");
            }
        width = w;
        height = h;
        origin = new Point(w / 2, h / 2);
        pixel = new int[h][w];
    }

    /**
     * Crea un SE don las dimensiones y origen especificado.
     *
     * @param w ancho deseado del SE
     * @param h alto deseado del SE
     * @param p posicion del origen del SE
     * @throws ElementoEstructuranteException por si las dimensiones del SE no 
     * son validas.
     */
    public ElementoEstructurante(int w, int h, Point p) 
            throws ElementoEstructuranteException {
        this(w, h);
        setOrigin(p);
    }

    /**
     * Prueba para ver si dos SE son iguales.
     *
     * @return devuelve true si son iguales o false en otro caso.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ElementoEstructurante) {
            ElementoEstructurante element = (ElementoEstructurante) obj;
            if(width != element.width
                    || height != element.height
                    || (!origin.equals(element.origin))) {
                return false;
                }
            for(int y = 0; y < height; y++) {
                for(int x = 0; x < width; x++) {
                    if(pixel[y][x] != element.pixel[y][x]) {
                        return false;
                        }
                    }
                }
            return true;
            } 
        else {
            return false;
            }
    }

    /**
     * Crea el codigo has de la clase
     * @return devuelve el key del hash
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.width;
        hash = 71 * hash + this.height;
        hash = 71 * hash + Objects.hashCode(this.origin);
        hash = 71 * hash + Arrays.deepHashCode(this.pixel);
        return hash;
    }

    /**
     * Crea la cadena (String) que indica el tipo de elemento estructurante, 
     * sus dimensiones y su origen.
     *
     * @return devuelve un String que tiene la informacion del SE.
     */
    @Override
    public String toString() {
        return getClass().getName() + ": size " + width + "x" + height
                + ", origin (" + origin.x + "," + origin.y + ")";
    }

    /**
     * Devuelve la anchura del elemento estructurado
     * 
     * @return the width of the SE.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Devuelve la altura del elemento estructurado
     * 
     * @return the height of the SE.
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param p el punto de origen
     * 
     * @return el origen del SE.
     */
    public Point getOrigin(Point p) {
        if(p != null) {
            p.x = origin.x;
            p.y = origin.y;
            return p;
            } 
        else {
            return new Point(origin.x, origin.y);
            }
    }

    /**
     * Cambia el origen del SE.
     *
     * @param p uno objeto java.awt.Point orepresenta el nuevo origen.
     */
    public void setOrigin(Point p) {
        origin.x = p.x;
        origin.y = p.y;
    }

    /**
     * Devuelve el valor del pixel especifico en el SE.
     *
     * @param x coordenada x del pixel del SE.
     * @param y coordenada y del pixel del SE.
     * @return devuelve el valor especificado del pixel del SE.
     */
    public int getPixel(int x, int y) {
        return pixel[y][x];
    }

    /**
     * Inicializa el conjunto de pixeles del SE usando valores de un arreglo en 
     * una-dimension.
     *
     * @param value el valor de los pixeles en el arreglo para el SE
     * @throws ElementoEstructuranteException si el elemento estructurado esta 
     * mal creado
     */
    public abstract void setPixels(int[] value)
            throws ElementoEstructuranteException;

    /**
     * Inicializa los pixeles del SE usando valolres en un arreglo de
     * dos-dimensiones.
     *
     * @param value arreglo de valores de los pixeles del SE.
     * @throws ElementoEstructuranteException si el elemento estructurado esta 
     * mal creado
     */
    public abstract void setPixels(int[][] value)
            throws ElementoEstructuranteException;

    /**
     * Inicializa los pixeles del SE usando los valores de un String.
     *
     * @param valueString el conjunto de valores de pixeles desde un String 
     * para el SE, dados fila por fila
     * @throws ElementoEstructuranteException si el elemento estructurado esta 
     * mal creado
     */
    public abstract void setPixels(String valueString)
            throws ElementoEstructuranteException;

    /**
     * Escribe le conjunto de pixeles del SE en el destino especificado.
     *
     * @param destination objeto Writer actua como destino de los datos del SE.
     */
    public abstract void writePixels(Writer destination);

    /**
     * Escribe el conjunto de datos del SE, incluye la cabecera, al destino
     * especificado.
     *
     * @param destination objeto Writer actua como destino para los datos del
     * SE
     * @throws java.io.IOException error de entrada del flujo
     */
    public abstract void write(Writer destination) throws IOException;
    /**
     * Lee el valor del parametro especificado del SE desde la fuente 
     * especificada.
     *
     * @param source Es el lector que actua como fuente del SE
     * @param key cadena String de la forma "# param=", iguala con lo que lee
     * desde la entrada
     * 
     * @return valor entero para el parametro dado.
     * @throws java.io.IOException si ocurre un error de I/O.
     * @throws ElementoEstructuranteException si la cadena (String) no tiene
     * los valores clave especificado por key.
     */
    protected int readInt(BufferedReader source, String key)
            throws IOException, ElementoEstructuranteException {
        String line = source.readLine();
        if(!line.startsWith(key)) {
            throw new ElementoEstructuranteException(
                    "no se pueden parsear los valores del elemento "
                            + "estructurante.");
            }
        int i = line.indexOf('=');
        return Integer.parseInt(line.substring(i + 1));
    }
    /**
     * Devuelve el punto de origen del elemento estructurante
     * 
     * @return devuelve la coordenada origen del elemento estricturanye 
     */
    public Point getOrigin() {
        return origin;
    }
    /**
     * Devuelve los pixeles del elemento estructurante
     * 
     * @return devuelve el conjunto de pixeles del elemento estructurante
     */
    public int [][] getPixel() {
        return pixel;
    }
}
