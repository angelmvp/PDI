/**
 * *************************************************************************
 *
 * StructElementTypes.java
 *
 **************************************************************************
 */
package temporal.morfologia;

/**
 * Constants specifying some common types of binary structuring element.
 * 
 * @author yo
 * @version 1.0 [1999/08/29]
 */
public interface TipoElementoEstructurante {
    /**
     * Elemento estructurante de crus 3 x 3
     */
    int CROSS_3x3   =  1;
    /**
     * Elemento estructurante de crus 5 x 5
     */
    int CROSS_5x5   =  2;
    /**
     * Elemento estructurante de diamante 5 x 5
     */
    int DIAMOND_5x5 =  3;
    /**
     * Elemento estructurante de diamante 7 x 7
     */
    int DIAMOND_7x7 =  4;
    /**
     * Elemento estructurante de disco 5 x 5
     */
    int DISK_5x5    =  5;
    /**
     * Elemento estructurante de disco 7 x 7
     */
    int DISK_7x7    =  6;
    /**
     * Elemento estructurante de cuadrado 3 x 3
     */
    int SQ_3x3      =  7;
    /**
     * Elemento estructurante de linea horizontal 3 x 3
     */
    int LIN_HOR_3x3 =  8;
    /**
     * Elemento estructurante de linea vertical 3 x 3
     */
    int LIN_VER_3x3 =  9;
    /**
     * Elemento estructurante de cuadrado 5 x 5
     */
    int SQ_5x5      = 10;
    /**
     * Elemento estructurante de diamante 9 x 9
     */
    int DIAMOND_9x9 =  11;
    /**
     * Elemento estructurante de diamante 11 x 11
     */
    int DIAMOND_11x11 =  12;
    /**
     * Elemento estructurante de disco 9 x 9
     */
    int DISK_9x9      =  13;
    /**
     * Elemento estructurante de disco 11 x 11
     */
    int DISK_11x11    =  14;
    /**
     * Elemento estructurante de cuadrado 7 x 7
     */
    int SQ_7x7        =  15;
    /**
     * Elemento estructurante de cuadrado 9 x 9
     */
    int SQ_9x9        =  16;
    /**
     * Elemento estructurante de cuadrado 11 x 11
     */
    int SQ_11x11        =  17;
    /**
     * Elemento estructurante de suma 3 x 3
     */
    int MORE_3x3        =  19;
    /**
     * Elemento estructurante de suma 5 x 5
     */
    int MORE_5x5        =  20;
    /**
     * Elemento estructurante de suma 7 x 7
     */
    int MORE_7x7        =  21;
    /**
     * Elemento estructurante de suma 9 x 9
     */
    int MORE_9x9        =  22;
    /**
     * Elemento estructurante de suma 11 x 11
     */
    int MORE_11x11      =  23;
}
