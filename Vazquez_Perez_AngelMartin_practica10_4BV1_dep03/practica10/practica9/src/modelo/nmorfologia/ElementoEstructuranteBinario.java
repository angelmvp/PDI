/**
 * *************************************************************************
 *
 * ElementoEstructuranteBinario.java ---- BinaryStructElement.java
 *
 **************************************************************************
 */
package temporal.morfologia;

import java.awt.Point;
import java.awt.image.Raster;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

public final class ElementoEstructuranteBinario extends ElementoEstructurante 
        implements Cloneable, TipoElementoEstructurante {
    /**
     * Crea SE cuadrado de 3x3.
     * @throws ElementoEstructuranteException si el elemento estructurado esta 
     * mal
     */
    public ElementoEstructuranteBinario() 
            throws ElementoEstructuranteException {
        super(3, 3);
        setPixels();
    }

    /**
     * Crea una instancia de un objeto BinaryStructElement con sus dimensiones
     * especificadas. Todos los pixeles seran puestoas a 1.
     *
     * @param w anchura del SE deseada (mayor a 0)
     * @param h altura del SE deseada (mayor a 0)
     * @throws ElementoEstructuranteException si las dimensiones no son validas.
     */
    public ElementoEstructuranteBinario(int w, int h) 
            throws ElementoEstructuranteException {
        super(w, h);
        setPixels();
    }

    /**
     * Crea una instancia del objeto BinaryStructElement con las dimensiones
     * especificando un origen. Todos los pixeles deben ser puestos a 1.
     *
     * @param w ancho del SE deseado (mayor que 0)
     * @param h ancho del SE deseado (mayor que 0)
     * @param p java.awt.Point objeto que representa el origen del SE
     * @throws ElementoEstructuranteException si las dimensiones no son validas.
     */
    public ElementoEstructuranteBinario(int w, int h, Point p)
            throws ElementoEstructuranteException {
        super(w, h, p);
        setPixels();
    }
public ElementoEstructuranteBinario(String type) 
            throws ElementoEstructuranteException {
        // Pone las dimensiones del SE
        switch (type) {
            case "SQ_3x3":
            case "CROSS_3x3":
            case "LIN_HOR_3x3":
            case "LIN_VER_3x3":
            case "MORE_3x3":
                width = height = 3;
                origin = new Point(1, 1);
                pixel = new int[3][3];
                break;
            case "SQ_5x5":
            case "CROSS_5x5":
            case "DIAMOND_5x5":
            case "DISK_5x5":
            case "MORE_5x5":
                width = height = 5;
                origin = new Point(2, 2);
                pixel = new int[5][5];
                break;
            case "SQ_7x7":
            case "DIAMOND_7x7":
            case "DISK_7x7":
            case "MORE_7x7":
                width = height = 7;
                origin = new Point(3, 3);
                pixel = new int[7][7];
                break;
            case "SQ_9x9":
            case "DIAMOND_9x9":
            case "DISK_9x9":
            case "MORE_9x9":
                width = height = 9;
                origin = new Point(4, 4);
                pixel = new int[9][9];
                break;
            case "SQ_11x11":
            case "DIAMOND_11x11":
            case "DISK_11x11":
            case "MORE_11x11":
                width = height = 11;
                origin = new Point(5, 5);
                pixel = new int[11][11];
                break;
            default:
                System.out.println("ñao ñao");
            }

        // Define SE values
        switch (type) {
            case "LIN_VER_3x3":
                setPixels("010010010");
            case "LIN_HOR_3x3":
                setPixels("000111000");
            case "SQ_3x3":
                setPixels("111111111");
                break;
            case "CROSS_3x3":
                setPixels("010111010");
                break;
            case "MORE_3x3":
                setPixels("010111010");
                break;
            case "SQ_5x5":
                setPixels("1111111111111111111111111");
                break;
            case "CROSS_5x5":
                setPixels("0010000100111110010000100");
                break;
            case "DIAMOND_5x5":
                setPixels("0010001110111110111000100");
                break;
            case "DISK_5x5":
                setPixels("0111011111111111111101110");
                break;
            case "MORE_5x5":
                setPixels("0010000100111110010000100");
                break;
            case "SQ_7x7":
                setPixels("1111111111111111111111111111111111111111111111111");
                break;
            case "DIAMOND_7x7":
                setPixels("0001000001110001111101111111011111000111000001000");
                break;
            case "DISK_7x7":
                setPixels("0011100011111011111111111111111111101111100011100");
                break;
            case "MORE_7x7":
                setPixels("0001000000100000010001111111000100000010000001000");
                break;
            case "SQ_9x9":
                setPixels("111111111111111111111111111111111111" +
                          "111111111" +
                          "111111111111111111111111111111111111");
                break;
            case "DIAMOND_9x9":
                setPixels("000010000000111000001111100011111110" +
                          "111111111" +
                          "011111110001111100000111000000010000");
                break;
            case "DISK_9x9":
                setPixels("001111100011111110111111111111111111" +
                          "111111111" +
                          "111111111111111111011111110001111100");
                break;
            case "MORE_9x9":
                setPixels("000010000000010000000010000000010000" +
                          "111111111" +
                          "000010000000010000000010000000010000");
                break;
            case "SQ_11x11":
                setPixels("11111111111111111111111111111111111111111111" + 
                          "11111111111" +
                          "11111111111" +
                          "11111111111" +
                          "11111111111111111111111111111111111111111111");
                break;
            case "DISK_11x11":
                setPixels("00111111100011111111101111111111111111111111" +
                          "11111111111" +
                          "11111111111" +
                          "11111111111" +
                          "11111111111111111111110111111111000111111100");
                break;
            case "DIAMOND_11x11":
                setPixels("00000100000000011100000001111100000111111100" +
                          "01111111110" +
                          "11111111111" +
                          "01111111110" +
                          "00111111100000111110000000111000000000100000");
                break;
            case "MORE_11x11":
                setPixels("00000100000000001000000000010000000000100000" +
                          "00000100000" +
                          "11111111111" +
                          "00000100000" +
                          "00000100000000001000000000010000000000100000");
                break;
            }
    }

    /**
     * Crea una instancia del objeto BinaryStructElement al leer los datos.
     *
     * @param source Lector de los datos de la fuente del SE
     * @throws java.io.IOException si ocurre un error de I/O.
     * @throws ElementoEstructuranteException si los datos no estan conforme al 
     * formato requerio de las dimensiones, origen o SE no validos.
     */
    public ElementoEstructuranteBinario(Reader source)
            throws IOException, ElementoEstructuranteException {
        // Lee la primer linea y verifica la cabecera del SE
        BufferedReader input = new BufferedReader(source);
        String line = input.readLine();
        if(!line.startsWith("# binary structuring element")) {
            throw new ElementoEstructuranteException(
                    "no hay datos dsiponibles para el SE binario.");
            }
        // Parse width and height parameters
        width = readInt(input, "# width=");
        height = readInt(input, "# height=");
        if(width < 1 || height < 1) {
            throw new ElementoEstructuranteException(
                    "dimensiones de SE no validas");
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
            if(line.length() < width) {
                throw new ElementoEstructuranteException(
                        "SE trunco?");
                }
            for(x = 0; x < width; x++) {
                value = Character.digit(line.charAt(x), 2);
                if(value < 0 || value > 1) {
                    throw new ElementoEstructuranteException(
                            "valor no valido de SE");
                    }
                pixel[y][x] = value;
                }
            }
    }

    /**
     * Clona un elemento estructurante binario
     * 
     * @return a copy of this BinaryStructElement.
     * @throws java.lang.CloneNotSupportedException no acepta clonacion
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        super.clone();
        try {
            ElementoEstructuranteBinario element
                    = new ElementoEstructuranteBinario(width, height, origin);
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
     * Prueba para igualda entre BinaryStructElement.
     *
     * @param object un objeto BinaryStructElement
     * @return true si los SEs son iguales, false otro caso.
     */
    @Override
    public boolean equals(Object object) {
        return (object instanceof ElementoEstructuranteBinario) && 
                super.equals(object);
    }

    /**
     * Pone un pixel en el SE a 1.
     *
     * @param x coordenada en x del pixel del SE
     * @param y coordenada en y del pilxe del SE
     */
    public void setPixel(int x, int y) {
        pixel[y][x] = 1;
    }

    /**
     * Limpia un pixel en el SE (por ejemplo, si da un valor de 0).
     *
     * @param x coordenada en x del pixel del SE
     * @param y coordenada en y del pilxe del SE
     */
    public void clearPixel(int x, int y) {
        pixel[y][x] = 0;
    }

    /**
     * Pone todos los pixeles del SE a 1.
     */
    public void setPixels() {
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                pixel[y][x] = 1;
                }
            }
    }

    /**
     * Copia los valores (0 o 1) desde un arreglo unidimensional en el SE.
     *
     * @param values arreglo de valores a copiae en el SE
     * @throws ElementoEstructuranteException si es arreglo es mas pequenio o
     * si algun valor no es 0 o 1.
     */
    @Override
    public void setPixels(int[] values) throws ElementoEstructuranteException {
        if(values.length < width * height) {
            throw new ElementoEstructuranteException(
                    "pocos valores para los pixeles del SE");
            }
        int i = 0;
        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x, ++i) {
                if(values[i] < 0 || values[i] > 1) {
                    throw new ElementoEstructuranteException(
                            "valor no valido para el SE");
                    }
                pixel[y][x] = values[i];
                }
            }
    }

    /**
     * 
     * Copia los valores desde un arreglo unidimensional en el SE.
     *
     * @param valuesTwoDimension arreglo de valores (0 o 1) a copiae en el SE
     * @throws ElementoEstructuranteException si es arreglo es mas pequenio o
     * si algun valor no es 0 o 1.
     */
    @Override
    public void setPixels(int [][] valuesTwoDimension) 
            throws ElementoEstructuranteException {
        if(valuesTwoDimension.length < height || 
                valuesTwoDimension[0].length < width) {
            throw new ElementoEstructuranteException(
                    "pocos valores para los pixeles del SE");
            }
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                if(valuesTwoDimension[y][x] < 0 || 
                        valuesTwoDimension[y][x] > 1) {
                    throw new ElementoEstructuranteException(
                            "valor no valido para el SE");
                    }
                pixel[y][x] = valuesTwoDimension[y][x];
                }
            }
    }

    /**
     * Copia los valores desde una cadena (String) hacia el SE.
     *
     * @param valuesString Cadena de unos y ceros copiada al SE
     * @throws ElementoEstructuranteException si algun valor no es 1 o 0.
     */
    @Override
    public void setPixels(String valuesString) 
            throws ElementoEstructuranteException {
        if(valuesString.length() != width * height) {
            throw new ElementoEstructuranteException(
                    "valores de cadena no validos");
            }
        int i = 0, value;
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                value = Character.digit(valuesString.charAt(i++), 2);
                if(value < 0 || value > 1) {
                    throw new ElementoEstructuranteException(
                            "valor no valido para el SE");
                    }
                pixel[y][x] = value;
                }
            }
    }

    /**
     * Escribe del SE los valores de pixeles al destino especificado.
     *
     * @param destination conjunto de datos destino del SE
     */
    @Override
    public void writePixels(Writer destination) {
        PrintWriter out = new PrintWriter(new BufferedWriter(destination));
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                out.print(pixel[y][x]);
                }
            out.println();
            }
        out.flush();
    }

    /**
     * Escribe el conjunto de datos de SE, incluida la cabecera, al destino
     * especificado.
     *
     * @param destination Destino a escribir el SE
     * @exception java.io.IOException Si ocurre un error de IO sobre el destino.
     */
    @Override
    public void write(Writer destination) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(destination));
        out.println("# binary structuring element");
        out.println("# width=" + width);
        out.println("# height=" + height);
        out.println("# xorigin=" + origin.x);
        out.println("# yorigin=" + origin.y);
        if(out.checkError()) {
            throw new IOException("error de escritura de cabecera del SE.");
            }
        writePixels(destination);
    }

    /**
     * Prueba si el SE encaja dentro de una imagen en las coordenadas 
     * especificadas. El SE se coloca de manera que su origen se encuentre en 
     * estas coordenadas. Si, para cada pixel SE establecido en 1, el pixel de 
     * la imagen correspondiente es distinto de cero, se dice que el SE encaja 
     * en ese punto.
     * <p>
     * Nota: ¡este metodo no comprueba si los píxeles SE se encuentran mas 
     * alla de los limites de la imagen!</p>
     *
     * @param raster Raster de la imagen en el cual el SE sera instalado
     * @param x x coordenada del pixel donde el SE sera reemplazado
     * @param y y coordenada del pixel donde el SE sera reemplazado
     * @return true si el SE se llena, false otro caso.
     */
    public boolean fits(Raster raster, int x, int y) {
        int j, k, m, n;
        for(k = 0, n = y - origin.y; k < height; k++, n++) {
            for(j = 0, m = x - origin.x; j < width; j++, m++) {
                if(pixel[k][j] == 1 && raster.getSample(m, n, 0) == 0) {
                    return false;
                    }
                }
            }
        return true;
    }

    /**
     * Tests whether the SE fits within the complement of an image at the
     * specified coordinates. The SE is positioned so that its origin lies at
     * these coordinates. If, for all SE pixels set to 1, the corresponding
     * image pixel is zero, the SE is said to fit the complement of the image at
     * that point.
     * <p>
     * Note: this method does <em>not</em> check whether SE pixels lie beyond
     * the bounds of the image!</p>
     *
     * @param raster Raster of the image into which the SE will be fitted
     * @param x x coordinate of pixel where SE will be placed
     * @param y y coordinate of pixel where SE will be placed
     * @return true if the SE fits the complement of the image, false otherwise.
     */
    public boolean fitsComplement(Raster raster, int x, int y) {
        int j, k, m, n;
        for(k = 0, n = y - origin.y; k < height; k++, n++) {
            for(j = 0, m = x - origin.x; j < width; j++, m++) {
                if(pixel[k][j] == 1 && raster.getSample(m, n, 0) != 0) {
                    return false;
                    }
                }
            }
        return true;
    }

    /**
     * Tests whether the SE hits an image when it is placed at the specified
     * coordinates. The SE is positioned so that its origin lies at these
     * coordinates. If, for any of the SE pixels set to 1, the corresponding
     * image pixel is non-zero, the SE is said to hit the image at that point.
     * <p>
     * Note: this method does <em>not</em> check whether SE pixels lie beyond
     * the bounds of the image!</p>
     *
     * @param raster Raster of the image with which the SE will be intersected
     * @param x x coordinate of pixel where SE will be placed
     * @param y y coordinate of pixel where SE will be placed
     * @return true if the SE hits, false otherwise.
     */
    public boolean hits(Raster raster, int x, int y) {
        int j, k, m, n;
        for(k = 0, n = y - origin.y; k < height; k++, n++) {
            for(j = 0, m = x - origin.x; j < width; j++, m++) {
                if(pixel[k][j] == 1 && raster.getSample(m, n, 0) != 0) {
                    return true;
                    }
                }
            }
        return false;
    }

    /**
     * @return a version of this BinaryStructElement that has been rotated
     * through 180 degrees.
     */
    public ElementoEstructuranteBinario getRotatedVersion() {
        try {
            ElementoEstructuranteBinario element
                    = new ElementoEstructuranteBinario(width, height, origin);
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
     * Genera codigo hash
     * 
     * @return devuelve el codigo generado
     */
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }
}
