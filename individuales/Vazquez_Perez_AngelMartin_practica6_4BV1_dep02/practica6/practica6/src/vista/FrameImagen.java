/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Image;
import javax.swing.JFrame;

/**
 *
 * @author Saul
 */
public class FrameImagen extends JFrame {
    private PanelImagen panel;
    public FrameImagen(Image imagen) {
        int ancho = imagen.getWidth(null);
        int alto = imagen.getHeight(null);
        setTitle("Visor de imagen " + ancho + " x " + alto + " pixeles.");
        initComponents(imagen);
    }
    private void initComponents(Image imagen) {
        Container contenedor = this.getContentPane();
        contenedor.setLayout(new BorderLayout());
        panel = new PanelImagen(imagen);
        contenedor.add(panel, BorderLayout.CENTER);
        this.setSize(imagen.getWidth(null), imagen.getHeight(null));
        this.setVisible(true);
    }
}
