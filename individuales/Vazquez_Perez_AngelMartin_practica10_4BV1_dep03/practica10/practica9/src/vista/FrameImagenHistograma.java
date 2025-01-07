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
        setTitle("Visor de imagen e histograma " );
        initComponents(img);
    }
    public FrameImagenHistograma(Image img,PanelHistograma panelHistograma){
        this.img=img;
        this.panelHistograma=panelHistograma;
        histograma=null;
        initComponents(img);
    }
    private void initComponents(Image imagen) {
        Container contenedor = this.getContentPane();
        contenedor.setLayout(new GridLayout(1,2));
        JPanel panelIzquierdo=new JPanel(new BorderLayout());
        if(histograma!=null){
        JPanel panelInformacion = new JPanel(new GridLayout(2,3));
        panelInformacion.add(new Label("Media: " + String.format("%.2f", histograma.getMedia())));
        panelInformacion.add(new Label("Varianza: " + String.format("%.2f", histograma.getVarianza())));
        panelInformacion.add(new Label("Asimetría: " + String.format("%.2f", histograma.getAsimetria())));
        panelInformacion.add(new Label("Energía: " + String.format("%.2f", histograma.getEnergia())));
        panelInformacion.add(new Label("Entropía: " + String.format("%.2f", histograma.getEntropia())));
        panelIzquierdo.add(panelInformacion,BorderLayout.NORTH);
        }

        panelImagen = new PanelImagen(imagen);
        panelIzquierdo.add(panelImagen,BorderLayout.CENTER);
        contenedor.add(panelIzquierdo);
        contenedor.add(panelHistograma);
        this.setSize(800, 400);
        this.setVisible(true);
    }
}
