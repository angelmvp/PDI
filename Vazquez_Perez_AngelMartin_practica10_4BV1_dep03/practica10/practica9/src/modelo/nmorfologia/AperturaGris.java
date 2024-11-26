/***************************************************************************

  AperturaGris.java ---- GreyOpenOp.java

***************************************************************************/
package temporal.morfologia;


import java.awt.image.*;

/**
 * Realiza la operacion morfologica de apertura en una imagen en niveles de 
 * gris.
 *
 * @author yo
 * @version 1.0 [1999/08/31]
 */

public class AperturaGris extends EstandarGris {
  /** 
   * Elemento estructurante para realizar la apertura. 
   */
  private ElementoEstructuranteGris structElement;

  /** 
   * Bandera que indica cuando los valores deben ser reescalados. 
   */
  private boolean rescaling;

  /**
   * Crea un objeto AperturaGris que emplea el elemento estructurante
   * especificado. Para la salida se truncan los valores al rango 0 - 255.
   * @param element eemento estructurante
   */
  public AperturaGris(ElementoEstructuranteGris element) {
    this(element, false);
  }

  /**
   * Crea un objeto AperturaGris que emplea el elemento estructurante
   * especificado.
   * La salida se trunca o reescala de acuerdo al valor booleano del parametro.
   * @param element elemento estructurante
   * @param rescale bandera que indica cuando los valores son reescalados.
   */
  public AperturaGris(ElementoEstructuranteGris element, boolean rescale) {
    structElement = element;
    rescaling = rescale;
  }


  /**
   * Realiza la apertura morfologica a una imagen en niveles de grises.
   * @param source imagen fuente
   * @param destination imagen destino, o null
   * @return devuelve la imagen aperturada.
   */
  @Override
  public BufferedImage filter(BufferedImage source, BufferedImage destination) {
    ErosionGris erodeOp = new ErosionGris(structElement, rescaling);
    BufferedImage erodedImage = erodeOp.filter(source, null);
    DilatacionGris dilateOp = new DilatacionGris(structElement, rescaling);
    if(destination == null) {
        destination = createCompatibleDestImage(source, null);
        }
    return dilateOp.filter(erodedImage, destination);
  }
}
