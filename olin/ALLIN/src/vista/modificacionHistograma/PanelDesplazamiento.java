/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.modificacionHistograma;

import vista.histograma.PanelImagenHistograma;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import modelo.Histograma;
import modelo.ImageBufferedImage;
import modelo.ModificacionHistograma1;

/**
 *
 * @author jhona
 */
public class PanelDesplazamiento extends JPanel {
    private JSlider sliderDesplazamiento;
    private Image imagen;
    private PanelImagenHistograma panelImagenHistograma;
    private ImageBufferedImage imageBuffered;
    private BufferedImage buffered;
    private int [][] imagenInt;
    private ModificacionHistograma1 modificacion;
    public PanelDesplazamiento(Image imagen){
        this.imagen=imagen;
        initComponents();
    }
    public void initComponents(){
        imageBuffered = new ImageBufferedImage();
        buffered=imageBuffered.getBufferedImageColor(imagen);
        imagenInt= imageBuffered.getMatrizImagen(buffered,5);
        modificacion = new ModificacionHistograma1(imagen);
        panelImagenHistograma = new PanelImagenHistograma(imagen);
        sliderDesplazamiento= new JSlider(-255,255,0);
        sliderDesplazamiento.setMajorTickSpacing(35);
        sliderDesplazamiento.setPaintTicks(true);
        sliderDesplazamiento.setPaintLabels(true);
        JPanel panelTop = new JPanel(new GridLayout(1,2));
        panelTop.add(new JLabel("arrastrar el Slider para desplazar a el histograma"));
        panelTop.add(sliderDesplazamiento);
        this.setLayout(new BorderLayout());
        this.add(panelTop,BorderLayout.NORTH);
        this.add(panelImagenHistograma,BorderLayout.CENTER);
        sliderDesplazamiento.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                modificarDesplazamiento();
            }
        });
    }
    public void modificarDesplazamiento(){
        int n=sliderDesplazamiento.getValue();
        panelImagenHistograma.setImagen(modificacion.desplazamiento(n));
        this.repaint();
        this.revalidate();
    }
    public void setImagen(Image imagen){
        this.imagen=imagen;
        buffered=imageBuffered.getBufferedImage(imagen);
        imagenInt= imageBuffered.getMatrizImagen(buffered,5);
        modificacion = new ModificacionHistograma1(imagen);
        panelImagenHistograma.setImagen(imagen);
        modificacion.setImagen(imagen);
    }
    
    
}
