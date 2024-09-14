package practica1;

import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import modelo.ImageBufferedImage;
import modelo.LectorDeImagen;
import vista.FrameImagen;
import vista.PanelBrillo;

/**
 *
 * @author jhona
 */
public class Practica1 {

    /**
     * @param args the command line arguments
     */
    public static void iniicar(String[] args) {
        String path = "C:\\Users\\jhona\\Desktop\\ESCOM_4\\"
                + "PDI\\Vazquez_Perez_AngelMartin_practica1_4BV1_dep01\\img/";
        String archivoImagen = "bauer.jfif";
        int queCanal = 5;
        LectorDeImagen lector = new LectorDeImagen(path, archivoImagen);
        lector.leerBufferedImagen();
        BufferedImage imagen=lector.getBufferedImagen();
        ImageBufferedImage buffered = new ImageBufferedImage();

        // frame = new FrameImagen(
          //                    buffered.getImage(
            //                   imagen, queCanal));

    }
    
}
