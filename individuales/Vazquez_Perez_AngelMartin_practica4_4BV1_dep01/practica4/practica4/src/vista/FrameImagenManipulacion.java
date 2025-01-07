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
    private JPanel panelPrincipal;
    private BufferedImage buffered;
    private ImageBufferedImage imageBuffered;
    private JPanel panelBotones;
    //private PanelConversion panelConversion;
    private PanelImagenHistograma panelImagenHistograma;

    public FrameImagenManipulacion(Image imagen) {
        this.imagen=imagen;
        initComponents();
    }
    public void initComponents(){
        imageBuffered = new ImageBufferedImage();
        buffered=imageBuffered.getBufferedImageColor(imagen);
        panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(3, 1, 4, 4));

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
        //panelConversion= new PanelConversion(imagen);
        panelImagenHistograma=new PanelImagenHistograma(imagen);
        Container contenedor = this.getContentPane();
        contenedor.setLayout(new BorderLayout());
        panelBotones.add(botonConversion);
        panelBotones.add(botonHistograma);
        contenedor.add(panelBotones, BorderLayout.EAST);
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new CardLayout());
        //panelPrincipal.add(panelConversion, "panel conversion");
        panelPrincipal.add(panelImagenHistograma, "panel histograma");
        contenedor.add(panelPrincipal, BorderLayout.CENTER);       
        setSize(800, 1500);
        setLocation(100,2);
        setVisible(true);
    }
    
}
