/**
 * *************************************************************************
 *
 * OperationException.java
 *
 **************************************************************************
 */
package temporal.morfologia;

/**
 * Excepcion lanzada por las clases que realizan las operaciones de 
 * procesamiento morfologico de imagen.
 *
 * @author yo
 * @version 1.0 [1999/06/21]
 */
public class OperacionException extends Exception {
    /**
     * Excepcion de la operacion
     */
    public OperacionException() {
        super();
    }
    /**
     * Excepcion de la operacion con mensaje
     * @param message el mensaje de excepcion
     */
    public OperacionException(String message) {
        super(message);
    }
}
