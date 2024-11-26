/**
 * *************************************************************************
 *
 * StringTools.java
 *
 **************************************************************************
 */
package temporal.morfologia;

/**
 * A class containing various static utility methods for handling strings.
 *
 * @author yo
 * @version 1.1 [1999/07/23]
 * @see java.lang.String
 * @see java.lang.StringBuffer
 */
public final class HerramientaString {
    /**
     * Justificacion derecha
     * @param value el valor
     * @param fieldWidth el ancho
     * @return devuelve la cadena
     */
    public static String rightJustify(int value, int fieldWidth) {
        return rightJustify(String.valueOf(value), fieldWidth);
    }
    /**
     * Justificacion derecha
     * @param value el valor
     * @param fieldWidth el ancho del campo
     * @return devuelve la cadena
     */
    public static String rightJustify(float value, int fieldWidth) {
        return rightJustify(String.valueOf(value), fieldWidth);
    }
    /**
     * Justificacion derecha
     * @param string el valor
     * @param fieldWidth el ancho
     * @return devuelve la cadena
     */
    public static String rightJustify(String string, int fieldWidth) {
        StringBuilder field = new StringBuilder();
        field.setLength(fieldWidth);
        for (int i = 0; i < fieldWidth; ++i) {
            field.setCharAt(i, ' ');
            }
        field.replace(fieldWidth - string.length(), fieldWidth, string);
        return field.toString();
    }

}
