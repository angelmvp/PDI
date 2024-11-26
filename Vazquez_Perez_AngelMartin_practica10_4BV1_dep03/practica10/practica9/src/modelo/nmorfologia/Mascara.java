package temporal.morfologia;

/**
 *
 * @author sdelaot
 */
public interface Mascara {
    //Primer conjunto de mascaras
    /**
     * Triangulo abajo
     */
    int [] mascaraTrianguloAbajo = new int[] {
        0, 0, 0,
        2, 1, 2,
        1, 1, 1
    };
    /**
     * Triangulo derecha
     */
    int [] mascaraTrianguloDerecha = new int[] {
        0, 2, 1,
        0, 1, 1,
        0, 2, 1
    };
    /**
     * Triangulo arriba
     */
    int [] mascaraTrianguloArriba = new int[] {
        1, 1, 1,
        2, 1, 2,
        0, 0, 0
    };
    /**
     * Triangulo izquierda
     */
    int [] mascaraTrianguloIzquierda = new int[] {
        1, 2, 0,
        1, 1, 0,
        1, 2, 0
    };

    //Segundo conjunto de mascaras
    /**
     * Arco izquierda inferior
     */
    int [] mascaraArcoIzquierdoInferior = new int[] {
        2, 0, 0,
        1, 1, 0,
        2, 1, 2
    };
    /**
     * Arco derecha inferior
     */
    int [] mascaraArcoDerechoInferior = new int[] {
        0, 0, 2,
        0, 1, 1,
        2, 1, 2
    };
    /**
     * Arco derecha superior
     */
    int [] mascaraArcoDerechaSuperior = new int[] {
        2,1,2,
        0,1,1,
        0,0,2
    };
    /**
     * Arco izquierdo superior
     */
    int [] mascaraArcoIzquierdoSuperior = new int[] {
        2, 1, 2,
        1, 1, 0,
        2, 0, 0
    };
}
