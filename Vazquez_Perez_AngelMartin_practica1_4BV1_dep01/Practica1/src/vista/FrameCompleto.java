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
import modelo.LectorDeImagen;

/**
 *
 * @author jhona
 */
public class FrameCompleto extends JFrame{
    private JPanel panelBotones;
    private JPanel panelPrincipal;
    private PanelBrillo panelBrillo;
    private PanelContraste panelContraste;
    private PanelImagen panelImagen;
    private PanelCanales panelCanales;
    private BufferedImage imagen;
    private ImageBufferedImage buffered;
    public FrameCompleto() {
        super("Practica 1");        
        initComponents();
      addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0); 
            }
        });
    }
    private void initComponents() {
        seleccionarImagen();
        buffered = new ImageBufferedImage();
        panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(3, 1, 4, 4));
        JButton botonBrillo = new JButton("MODIFICAR BRILLO");
        botonBrillo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout card = (CardLayout)panelPrincipal.getLayout();
                card.show(panelPrincipal, "panel brillo");
            }
        });
        JButton botonContraste = new JButton("MODIFICAR CONTRASTE");
        botonContraste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout card = (CardLayout)panelPrincipal.getLayout();
                card.show(panelPrincipal, "panel contraste");
            }
        });
        JButton botonCanales = new JButton("Ajustar Color");
        botonCanales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout card = (CardLayout)panelPrincipal.getLayout();
                card.show(panelPrincipal, "panel canales");
            }
        });
        Container contenedor = this.getContentPane();
        contenedor.setLayout(new BorderLayout());
        panelBotones.add(botonCanales);
        panelBotones.add(botonBrillo);
        panelBotones.add(botonContraste);
        
            JButton botonCargar = new JButton("Seleccionar Imagen");
            botonCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarImagen();
                Image nuevaImagen = buffered.getImage(imagen,4);
                panelImagen.setImagen(nuevaImagen);
                actualizarPaneles();
                }
            });

        contenedor.add(botonCargar, BorderLayout.NORTH);
        contenedor.add(panelBotones, BorderLayout.EAST);
        panelImagen= new PanelImagen(buffered.getImage(imagen,4));
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new CardLayout());
        panelContraste = new PanelContraste(imagen);
        panelBrillo = new PanelBrillo(imagen);
        panelCanales=new PanelCanales(imagen);
        panelPrincipal.add(panelImagen, "panel original");
        panelPrincipal.add(panelBrillo, "panel brillo");
        panelPrincipal.add(panelContraste, "panel contraste");
        panelPrincipal.add(panelCanales, "panel canales");
        contenedor.add(panelPrincipal, BorderLayout.CENTER);
        
        setSize(800, 700);
        setLocation(222, 22);
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
        panelBrillo.setImagen(imagen);
        panelContraste.setImagen(imagen);
        panelCanales.setImagen(imagen);
        
    }
}
