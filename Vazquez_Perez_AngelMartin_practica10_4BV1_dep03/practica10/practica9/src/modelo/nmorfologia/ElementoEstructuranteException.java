/**
 * *************************************************************************
 *
 * ElementoEstructuranteException.java ---- StructElementException.java
 *
 ***************************************************************************
 */
package temporal.morfologia;

/**
 * Excepcion para representar elementos estructurantes para trabajar
 * procesamiento morfologico de imahen.
 *
 * @author yo
 * @version 1.1 [1999/08/30]
 */
public class ElementoEstructuranteException extends OperacionException {
    /**
     * Excepcion del elemento estructurante
     */
    public ElementoEstructuranteException() {
        super();
    }
    /**
     * Excepcion del elemento estructurante con mensaje
     * @param message el mensaje de excepcion
     */
    public ElementoEstructuranteException(String message) {
        super(message);
    }
}
