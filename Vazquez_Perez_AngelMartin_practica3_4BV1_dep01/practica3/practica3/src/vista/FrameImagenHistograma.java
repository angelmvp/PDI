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
import javax.swing.JPanel;
import modelo.Histograma;

/**
 *
 * @author jhona
 */
public class FrameImagenHistograma extends JFrame {
    private PanelImagen panelImagen;
    private Image img;
    private Histograma histograma;
    private final PanelHistograma panelHistograma;
    public FrameImagenHistograma(Image img,PanelHistograma panelHistograma,Histograma histograma){
        this.img=img;
        this.panelHistograma=panelHistograma;
        this.histograma=histograma;
        setTitle("Visor de imagen y frame de color " + panelHistograma.getColor());
        initComponents(img);
    }
    private void initComponents(Image imagen) {
        Container contenedor = this.getContentPane();
        contenedor.setLayout(new GridLayout(2,2));
        JPanel panelInformacion = new JPanel();
        panelInformacion.add(new Label("Media: " +Double.toString(histograma.getMedia())));
        panelInformacion.add(new Label("Varianza: " +Double.toString(histograma.getVarianza())));
        panelInformacion.add(new Label("Asimetria: " +Double.toString(histograma.getAsimetria())));
        panelInformacion.add(new Label("Energia1: " +Double.toString(histograma.getEnergia())));
        panelInformacion.add(new Label("Entropia: " +Double.toString(histograma.getEntropia())));
        panelImagen = new PanelImagen(imagen);
        contenedor.add(panelImagen);
        contenedor.add(panelHistograma);
        contenedor.add(panelInformacion);
        this.setSize(imagen.getWidth(null), imagen.getHeight(null));
        this.setVisible(true);
    }
}
