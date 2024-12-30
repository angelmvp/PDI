/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.modificacionHistograma;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import modelo.ImageBufferedImage;
import vista.PanelImagen;

/**
 *
 * @author jhona
 */
public class FrameModificacionHistograma extends JFrame{
    private JPanel panelBotones;
    private JPanel panelPrincipal;
    private PanelDesplazamiento panelDesplazamiento;
    private PanelEqualizacion panelEcualizacion;
    private PanelModificacion1 panelmodificacion1;
    private PanelImagen panelImagen;
    private BufferedImage buffered;
    private Image imagen;
    private ImageBufferedImage imageBuffered;
    public FrameModificacionHistograma() {
        super("Practica  6 y 7");        
        initComponents();
      addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0); 
            }
        });
    }
    public FrameModificacionHistograma(Image imagen){
        super("Practica  6 y 7");        
        this.imagen = imagen;
        this.imageBuffered = new ImageBufferedImage();
        this.buffered = imageBuffered.getBufferedImageColor(imagen);
        initComponents();
    }
    private void initComponents() {
        imageBuffered = new ImageBufferedImage();
        if (imagen==null){
            seleccionarImagen();
        }
        panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(3, 1, 4, 4));
        JButton botonDesplazamiento = new JButton("Desplazamiento De histograma");
        botonDesplazamiento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout card = (CardLayout)panelPrincipal.getLayout();
                card.show(panelPrincipal, "panel desplazamiento");
            }
        });
        JButton botonEcualizacion = new JButton("Equalizacion");
        botonEcualizacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout card = (CardLayout)panelPrincipal.getLayout();
                card.show(panelPrincipal, "panel ecualizacion");
            }
        });
        JButton botonModificacion = new JButton("PanelModificacion1");
        botonModificacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout card = (CardLayout)panelPrincipal.getLayout();
                card.show(panelPrincipal, "panel modificacion");
            }
        });
        Container contenedor = this.getContentPane();
        contenedor.setLayout(new BorderLayout());
        panelBotones.add(botonModificacion);
        panelBotones.add(botonDesplazamiento);
        panelBotones.add(botonEcualizacion);
        

        contenedor.add(panelBotones, BorderLayout.EAST);
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new CardLayout());
        panelDesplazamiento = new PanelDesplazamiento(imagen);
        panelEcualizacion= new PanelEqualizacion(imagen);
        panelmodificacion1= new PanelModificacion1(imagen);
        panelPrincipal.add(panelDesplazamiento, "panel desplazamiento");
        panelPrincipal.add(panelEcualizacion, "panel ecualizacion");
        panelPrincipal.add(panelmodificacion1, "panel modificacion");
        contenedor.add(panelPrincipal, BorderLayout.CENTER);
        
        JButton botonCargar = new JButton("Seleccionar Imagen");
            botonCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarImagen();
                actualizarPaneles();
                }
            });
        JPanel panelSeleccion = new JPanel(new GridLayout(1,2));
        panelSeleccion.add(botonCargar);
        contenedor.add(panelSeleccion,BorderLayout.NORTH);
        setSize(1200, 700);
        setLocation(22, 22);
        setVisible(true);
    }
    private void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showOpenDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            try {
                imagen = ImageIO.read(archivoSeleccionado);
                if(imagen==null){
                    System.out.println("imagen nula");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar la imagen", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void actualizarPaneles(){
        panelEcualizacion.setImagen(imagen);
        panelDesplazamiento.setImagen(imagen);
        panelmodificacion1.setImagen(imagen);
        
    }
}