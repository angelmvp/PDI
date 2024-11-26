/**
 * *************************************************************************
 *
 * ElementoEstructuranteGris.java ---- GreyStructElement.java
 *
 ***************************************************************************
 */
package temporal.morfologia;

import java.awt.Point;
import java.awt.image.Raster;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

/**
 * Un elemento estructurante en niveles de gris para el procesamiento 
 * morfologico de imagen.
 *
 * @author yo
 * @version 1.0 [1999/08/31]
 */
public final class ElementoEstructuranteGris extends ElementoEstructurante 
        implements Cloneable {
    /**
     * Crea un SE cuadrado de 3x3 con el valor del pixel especificados en 
     * value.
     *
     * @param value el conjunto de pixeles del SE
     * @throws ElementoEstructuranteException si el elemento estructurado esta 
     * mal
     */
    public ElementoEstructuranteGris(int value) 
            throws ElementoEstructuranteException {
        super(3, 3);
        setPixels(value);
    }

    /**
     * Crea un SE rectangular (GreyStructElement) con las dimensiones 
     * especificadas y el valor del pixel en value.
     *
     * @param w ancho deseado del SE (mayor que 0)
     * @param h alto deseado del SE (mayor que 0)
     * @param value valor del pixel del SE
     * @throws ElementoEstructuranteException si las dimensiones no son validas.
     */
    public ElementoEstructuranteGris(int w, int h, int value)
            throws ElementoEstructuranteException {
        super(w, h);
        setPixels(value);
    }

    /**
     * Crea una instancia de ElementoEstructuranteGris con las dimensiones
     * especificadas, origen y valor de pixel.
     *
     * @param w desired SE width (greater than 0)
     * @param h desired SE height (greater than 0)
     * @param p java.awt.Point object representing origin of SE
     * @param value SE pixel value
     * @throws ElementoEstructuranteException if dimensions are invalid.
     */
    public ElementoEstructuranteGris(int w, int h, Point p, int value)
            throws ElementoEstructuranteException {
        super(w, h, p);
        setPixels(value);
    }

    /**
     * Creates an instance of a GreyStructElement by reading data.
     *
     * @param source Reader representing source of SE data
     * @throws IOException if there was an I/O error.
     * @throws ElementoEstructuranteException if the data do not conform to the
     * required format or the dimensions, origin or SE values are not valid.
     */
    public ElementoEstructuranteGris(Reader source)
            throws IOException, ElementoEstructuranteException {
        // Read first line and check for a valid SE header
        BufferedReader input = new BufferedReader(source);
        String line = input.readLine();
        if(!line.startsWith("# grey structuring element")) {
            throw new ElementoEstructuranteException(
                "no hay elemento estructurante con datos validos disponible.");
            }
        // Parse width and height parameters
        width = readInt(input, "# width=");
        height = readInt(input, "# height=");
        if(width < 1 || height < 1) {
            throw new ElementoEstructuranteException(
                    "dimensiones del elemeto estructurante no valido.");
            }
        pixel = new int[height][width];
        // Parse SE origin
        origin = new Point(0, 0);
        origin.x = readInt(input, "# xorigin=");
        origin.y = readInt(input, "# yorigin=");
        // Read SE values into array
        int x, y, value;
        for(y = 0; y < height; y++) {
            line = input.readLine();
            StringTokenizer parser = new StringTokenizer(line);
            if(parser.countTokens() < width) {
                throw new ElementoEstructuranteException(
                        "Elemento estructurante truncado?");
                }
            for(x = 0; x < width; x++) {
                pixel[y][x] = Integer.parseInt(parser.nextToken());
                }
            }
    }

    /**
     * @return a copy of this GreyStructElement.
     * @throws java.lang.CloneNotSupportedException no soporta clonacion
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        super.clone();
        try {
            ElementoEstructuranteGris element
                    = new ElementoEstructuranteGris(width, height, origin, 0);
            for(int y = 0; y < height; y++) {
                for(int x = 0; x < width; x++) {
                    element.pixel[y][x] = pixel[y][x];
                    }
                }
            return element;
        } catch (ElementoEstructuranteException e) {
            return null;
        }
    }

    /**
     * Prueba la igualldad de este SE con otro ElementoEstructuranteGris.
     *
     * @param object un objeto ElementoEstructuranteGris
     * @return true si los SEs son iguales, false en otro caso.
     */
    @Override
    public boolean equals(Object object) {
        return (object instanceof ElementoEstructuranteGris) && 
                super.equals(object);
    }

    /**
     * Pone un valor de pixel en el SE en la coordenada (x, y) especificada.
     *
     * @param x coordenada en x del pixel de SE.
     * @param y coordenada en y del pixel de SE.
     * @param value nuevo valor del pixel para SE.
     */
    public void setPixel(int x, int y, int value) {
        pixel[y][x] = value;
    }

    /**
     * Pone todos los pixeles del SE con el valor especificado.
     *
     * @param value nievo valor de todos los pixeles del SE.
     */
    public void setPixels(int value) {
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                pixel[y][x] = value;
                }
            }
    }

    /**
     * Copia los valores de los pixeles en una dimenion del arreglo hacia el SE.
     *
     * @param value valor del arreglo de pixeles a ser copiados al SE
     * @throws ElementoEstructuranteException si el elemento estructurado esta 
     * mal
     */
    @Override
    public void setPixels(int[] value) throws ElementoEstructuranteException {
        if(value.length < width * height) {
            throw new ElementoEstructuranteException(
                    "pocos valores de pixeles del elemento estructurante.");
            }
        int i = 0;
        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x, ++i) {
                pixel[y][x] = value[i];
                }
            }
    }

    /**
     * Copies values from a two-dimensional array into the SE.
     *
     * @param value array of values to be copied into SE
     * @throws ElementoEstructuranteException si el elemento estructurado esta 
     * mal
     */
    @Override
    public void setPixels(int[][] value) throws ElementoEstructuranteException {
        if(value.length < height || value[0].length < width) {
            throw new ElementoEstructuranteException(
                    "pocos valores de pixeles del elemento estructurante.");
            }
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                pixel[y][x] = value[y][x];
                }
            }
    }

    /**
     * Copia los valores desde un String hacia el SE.
     *
     * @param valuesString cadena String de valores a ser copiados en el SE
     * @throws ElementoEstructuranteException si la cadena String no contiene
     * los valores.
     */
    @Override
    public void setPixels(String valuesString) 
            throws ElementoEstructuranteException {
        StringTokenizer parser = new StringTokenizer(valuesString);
        if(parser.countTokens() != width * height) {
            throw new ElementoEstructuranteException(
                    "Valores de String no validos.");
            }
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                pixel[y][x] = Integer.parseInt(parser.nextToken());
                }
            }
    }

    /**
     * Escribe los valores de los pixeles de SE al destino especiificado.
     *
     * @param destination escribe el conjunto de datos del SE
     */
    @Override
    public void writePixels(Writer destination) {
        PrintWriter out = new PrintWriter(new BufferedWriter(destination));
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                out.print(HerramientaString.rightJustify(pixel[y][x], 5));
                }
            out.println();
            }
        out.flush();
    }

    /**
     * Escribe los datos del SE, incluyendo a la cabecera, al destino 
     * especificado.
     *
     * @param destination destino de escritura del SE
     * @exception java.io.IOException si ocurre un error al escribir la
     * cabecera.
     */
    @Override
    public void write(Writer destination) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(destination));
        out.println("# grey structuring element");
        out.println("# width=" + width);
        out.println("# height=" + height);
        out.println("# xorigin=" + origin.x);
        out.println("# yorigin=" + origin.y);
        if(out.checkError()) {
            throw new IOException("error al escribir la cabecera del SE.");
            }
        writePixels(destination);
    }

    /**
     * Calcula la distancia maxima que se puede empujar al SE mientras 
     * permanece debajo de una imagen. Esto es equivalente a obtener la 
     * distancia minima entre los valores de pixeles de la imagen y los 
     * valores de los pixeles del SE en el dominio del SE.
     * <p>
     * Nota: este metodo <em>no</em> verifica cuando los pixeles del SE salen
     * mas alla de los limites de la imagen!</p>
     *
     * @param raster Raster de la imagen con la que se operara el SE.
     * @param x coordenada x del pixel donde el SE sera reemplazado
     * @param y coordenada y del pixel donde el SE sera reemplazado
     * @return maxima distancia que el SE puede ser empujado hacia arriba.
     */
    public int below(Raster raster, int x, int y) {
        int distance, maxDistance = Integer.MAX_VALUE;
        int j, k, m, n;
        for(k = 0, n = y - origin.y; k < height; k++, n++) {
            for(j = 0, m = x - origin.x; j < width; j++, m++) {
                distance = raster.getSample(m, n, 0) - pixel[k][j];
                maxDistance = Math.min(distance, maxDistance);
                }
            }
        return maxDistance;
    }

    /**
     * Calcula la distancia minima necesaria que ha de rotar el SE para empujar 
     * hacia arriba a la imagen.
     * <p>
     * Note: este metodo <em>no</em> verifica cuando los pixeles del SE pixels 
     * no se encuentran en los limites de la imagen!</p>
     *
     * @param raster Raster de la imagen en la cual opera el SE
     * @param x coordenada x del pixel donde el SE sera reemplazado
     * @param y coordenada y del pixel donde el SE sera reemplazado
     * @return distunacia minima de empujar una distancia hacia arriba 
     * 'push-up'.
     */
    public int above(Raster raster, int x, int y) {
        int distance, minDistance = Integer.MIN_VALUE;
        int j, k, m, n;
        for(k = height - 1, n = y - origin.y; k >= 0; k--, n++) {
            for(j = width - 1, m = x - origin.x; j >= 0; j--, m++) {
                distance = raster.getSample(m, n, 0) + pixel[k][j];
                minDistance = Math.max(distance, minDistance);
                }
            }
        return minDistance;
    }

    /**
     * Rota al elemento estructurante
     * 
     * @return una version del ElementoEstructurante que ha sido rotado hasta
     * los 180 grados.
     */
    public ElementoEstructuranteGris getRotatedVersion() {
        try {
            ElementoEstructuranteGris element
                    = new ElementoEstructuranteGris(width, height, origin, 0);
            int[][] rotated = new int[height][width];
            for(int y = 0; y < height; ++y) {
                for(int x = 0; x < width; ++x) {
                    rotated[y][x] = pixel[height - y - 1][width - x - 1];
                    }
                }
            element.setPixels(rotated);
            return element;
        } catch (ElementoEstructuranteException e) {
            return null;
        }
    }

    /**
     * Genera el codigo has de la clase
     * @return devuelve el key hash de la clase
     */
    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }
}
