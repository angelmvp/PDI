/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import modelo.ImageBufferedImage;

/**
 *
 * @author jhona
 */
public class FrameImagenManipulacion extends JFrame {
    private Image imagen;
    private PanelImagen panelImagenOriginal;
    private PanelImagen panelImagenConvertida;
    private JPanel panelPrincipal;
    private BufferedImage buffered;
    private ImageBufferedImage imageBuffered;
    private JPanel panelBotones;
    private JPanel panelConversion;
    private PanelImagenHistograma panelImagenHistograma;
    public FrameImagenManipulacion(Image imagen) {
        this.imagen=imagen;
        initComponents();
    }
    public void initComponents(){
        imageBuffered = new ImageBufferedImage();
        buffered=imageBuffered.getBufferedImageColor(imagen);
        panelImagenOriginal=new PanelImagen(imagen);
        panelImagenConvertida= new PanelImagen(imagen);
        panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(3, 1, 4, 4));
            panelConversion = new JPanel();
            panelConversion.setLayout(new BorderLayout());
            JPanel panelCentro= new JPanel(new GridLayout(1,2));
            panelCentro.add(panelImagenOriginal);
            panelCentro.add(panelImagenConvertida);
            JButton botonConvertir = new JButton("invertir Conversion");
            panelConversion.add(panelCentro,BorderLayout.CENTER);
            panelConversion.add(botonConvertir, BorderLayout.NORTH);           
        JButton botonConversion = new JButton("Conversion");
        botonConversion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout card = (CardLayout)panelPrincipal.getLayout();
                card.show(panelPrincipal, "panel conversion");
            }
        });
        JButton botonHistograma = new JButton("Histograma");
        botonHistograma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout card = (CardLayout)panelPrincipal.getLayout();
                card.show(panelPrincipal, "panel histograma");
            }
        });
        panelImagenHistograma=new PanelImagenHistograma(imagen);
        Container contenedor = this.getContentPane();
        contenedor.setLayout(new BorderLayout());
        panelBotones.add(botonConversion);
        panelBotones.add(botonHistograma);
        contenedor.add(panelBotones, BorderLayout.EAST);
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new CardLayout());
        //panelHistograma = new PanelHistograma(imagen);
        panelPrincipal.add(panelConversion, "panel conversion");
        panelPrincipal.add(panelImagenHistograma, "panel histograma");
        contenedor.add(panelPrincipal, BorderLayout.CENTER);       
        setSize(800, 700);
        setLocation(500,2);
        setVisible(true);
    }
    
}
