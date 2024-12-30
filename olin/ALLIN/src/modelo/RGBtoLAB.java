/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import javax.swing.JFrame;

/**
 *
 * @author jhona
 */
public class RGBtoLAB {
    private Image img;
    private BufferedImage buffered;
    private ImageBufferedImage imageBuffered;
    private int alto;
    private int ancho;
    private Image imagenLAB;
    public RGBtoLAB(BufferedImage buffered){
        this.buffered=buffered;
        alto=buffered.getHeight();
        ancho=buffered.getWidth();
        imageBuffered = new ImageBufferedImage();
        img=imageBuffered.getImage(buffered,4); 
        initComponents();
    }
    public void initComponents(){
        imagenLAB=this.convertirRGBtoLAB();
    }
    public Image convertirRGBtoLAB(){
        int[][] nuevaImagen= new int [alto][ancho];
        double L, A, B;
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int pixel = buffered.getRGB(x, y);
                int rojo = (pixel & 0x00ff0000) >> 16;
                int verde = (pixel & 0x0000ff00) >> 8;
                int azul = pixel & 0x000000ff;

                // Convert RGB to XYZ
                double[] xyz = convertRGBtoXYZ(rojo, verde, azul);

                // Convert XYZ to LAB
                double[] lab = convertXYZtoLAB(xyz[0], xyz[1], xyz[2]);

                L = lab[0];
                A = lab[1];
                B = lab[2];

                Color color = new Color((int) L, (int) (A + 128), (int) (B + 128));  // Normalize A, B
                nuevaImagen[y][x] = color.getRGB();
            }
        }
        JFrame padre = new JFrame();
        Image imagenNueva = padre.createImage(new MemoryImageSource(ancho,
                alto, imageBuffered.convertirInt2DAInt1D(nuevaImagen, ancho, alto),
                0, ancho));
        System.out.println("se retorno la imagen");
        return imagenNueva;
    }
    public double[] convertRGBtoXYZ(int r, int g, int b) {
        // Normalize RGB values to the range [0, 1]
        double rNorm = r / 255.0;
        double gNorm = g / 255.0;
        double bNorm = b / 255.0;

        // Apply gamma correction
        rNorm = (rNorm > 0.04045) ? Math.pow((rNorm + 0.055) / 1.055, 2.4) : (rNorm / 12.92);
        gNorm = (gNorm > 0.04045) ? Math.pow((gNorm + 0.055) / 1.055, 2.4) : (gNorm / 12.92);
        bNorm = (bNorm > 0.04045) ? Math.pow((bNorm + 0.055) / 1.055, 2.4) : (bNorm / 12.92);

        // Convert to XYZ using the transformation matrix
        // Assumes sRGB color space and D65 illuminant
        double x = rNorm * 0.4124 + gNorm * 0.3576 + bNorm * 0.1805;
        double y = rNorm * 0.2126 + gNorm * 0.7152 + bNorm * 0.0722;
        double z = rNorm * 0.0193 + gNorm * 0.1192 + bNorm * 0.9505;

        return new double[]{x, y, z};
    }
    public double[] convertXYZtoLAB(double x, double y, double z) {
    // Reference white point for D65 illuminant
    double xRef = 0.95047; // X reference
    double yRef = 1.00000; // Y reference
    double zRef = 1.08883; // Z reference

    // Normalize X, Y, Z values
    x = x / xRef;
    y = y / yRef;
    z = z / zRef;

    // Apply the transformation function
    x = (x > 0.008856) ? Math.cbrt(x) : (7.787 * x + 16.0 / 116.0);
    y = (y > 0.008856) ? Math.cbrt(y) : (7.787 * y + 16.0 / 116.0);
    z = (z > 0.008856) ? Math.cbrt(z) : (7.787 * z + 16.0 / 116.0);

    // Calculate L*, a*, b* values
    double L = (116 * y) - 16;
    double a = 500 * (x - y);
    double b = 200 * (y - z);

    return new double[]{L, a, b};
}


    public Image[] obtenerImagenes(){
        Image[] imagenes = new Image[4];
        imagenes[0]=imagenLAB;
        BufferedImage bufferedLAB=imageBuffered.getBufferedImageColor(imagenLAB);
        imagenes[1]=imageBuffered.getImage(bufferedLAB, 6);
        imagenes[2]=imageBuffered.getImage(bufferedLAB, 7);
        imagenes[3]=imageBuffered.getImage(bufferedLAB, 8);
        return imagenes;
    }
    public void setImagen(Image imagen){
        this.img=imagen;
        this.buffered=imageBuffered.getBufferedImageColor(imagen);
        alto=buffered.getHeight();
        ancho=buffered.getWidth();
        initComponents();
    }
}
