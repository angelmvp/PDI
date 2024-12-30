/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.modificacionHistograma;

import vista.histograma.PanelImagenHistograma;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import modelo.ImageBufferedImage;
import modelo.ModificacionHistograma1;

/**
 *
 * @author jhona
 */
public class PanelModificacion1 extends JPanel{
    private Image imagen;
    private Image imagenGrises;
    private Image imagenModificada;
    private BufferedImage buffered;
    private ImageBufferedImage imageBuffered;
    private PanelImagenHistograma panelImagen;
    private PanelImagenHistograma panelImagenModificada;
    private JButton botonAplicarGrises;
    private JButton botonAplicarColor;
    private ModificacionHistograma1 modificacion1;
    public PanelModificacion1(Image imagen){
        this.imagen=imagen;
        this.imagenModificada=imagen;
        initComponents();
    }
    public void initComponents(){
        imageBuffered= new ImageBufferedImage();
        buffered= imageBuffered.getBufferedImageColor(imagen);
        imagenGrises=imageBuffered.getImage(buffered, 5);
        this.setLayout(new BorderLayout());
        botonAplicarGrises = new JButton("Aplicar en Grises");
        botonAplicarColor= new JButton("Aplicar en Color");
        modificacion1 = new ModificacionHistograma1(imagen);
        panelImagen= new PanelImagenHistograma(imagen);
        panelImagenModificada= new PanelImagenHistograma(imagenModificada);
        JPanel panelTop= new JPanel(new GridLayout(2,2));
            panelTop.add(new JLabel("boton para aplicar el histograma acumulativo en grises"));
            panelTop.add(botonAplicarGrises);
            panelTop.add(new JLabel("boton para aplicar el histograma acumulativo en Color"));
            panelTop.add(botonAplicarColor);
        JPanel panelCentro= new JPanel(new GridLayout(1,2));
            panelCentro.add(panelImagen);
            panelCentro.add(panelImagenModificada);
        this.add(panelTop,BorderLayout.NORTH);
        this.add(panelCentro,BorderLayout.CENTER);
        botonAplicarGrises.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarModificacionGrises();
            }
        });
        botonAplicarColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarModificacionColor();
            }
        });
    }

    public void realizarModificacionGrises() {
        Image imagenModificada = modificacion1.aplicarHistogramaAcumulado();
        panelImagenModificada.setImagen(imagenModificada);
        
    }
    public void realizarModificacionColor(){
        Image imagenModificada = modificacion1.aplicarHistogramaAcumuladoColor();
        panelImagenModificada.setImagen(imagenModificada);
    }

    public void setImagen(Image imagen){
        this.imagen=imagen;
        buffered=imageBuffered.getBufferedImageColor(imagen);
        imagenGrises=imageBuffered.getImage(buffered, 5);
        panelImagen.setImagen(imagen);
        panelImagenModificada.setImagen(imagen);
        modificacion1.setImagen(imagen);
    }
}
