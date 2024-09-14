/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import javax.swing.JFrame;
import modelo.Histograma;

/**
 *
 * @author jhona
 */
public class FrameImagenHistograma extends JFrame {
    private PanelImagen panelImagen;
    private Image img;
    private PanelHistograma panelHistograma;
    public FrameImagenHistograma(Image img,PanelHistograma panelHistograma){
        this.img=img;
        this.panelHistograma=panelHistograma;
        int ancho = img.getWidth(null);
        int alto = img.getHeight(null);
        setTitle("Visor de imagen y frame");
        initComponents(img);
    }
    private void initComponents(Image imagen) {
        Container contenedor = this.getContentPane();
        contenedor.setLayout(new GridLayout(1,2));
        panelImagen = new PanelImagen(imagen);
        contenedor.add(panelImagen);
        contenedor.add(panelHistograma);
        this.setSize(imagen.getWidth(null), imagen.getHeight(null));
        this.setVisible(true);
    }
}
