/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.filtrosLineales;

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
import vista.umbralizacion.FrameUmbral;
import vista.umbralizacion.PanelUmbral;

/**
 *
 * @author jhona
 */
public class FrameFiltrosLineales extends JFrame{
    private JPanel panelBotones;
    private JPanel panelPrincipal;
    private PanelPasaAltas panelPasaAltas;
    private PanelPasaBajas panelPasaBajas;
    private PanelImagen panelImagen;
    private BufferedImage buffered;
    private Image imagen;
    private ImageBufferedImage imageBuffered;
    private String panelActual;
    private PanelUmbral panelUmbral;
    public FrameFiltrosLineales() {
        super("Filtros Lineales");        
        initComponents();
    }
    public FrameFiltrosLineales(Image imagen){
       super("Filtros Lineales");
        this.imagen=imagen;
       imageBuffered = new ImageBufferedImage();
       this.buffered=imageBuffered.getBufferedImageColor(imagen);
       initComponents();
    }
    private void initComponents() {
        imageBuffered = new ImageBufferedImage();
        if(imagen==null){
            seleccionarImagen();
        }
        panelActual="pasaAltas";
        panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(3, 1, 4, 4));
        JButton botonPasaAltas = new JButton("Pasa Altas");
        botonPasaAltas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout card = (CardLayout)panelPrincipal.getLayout();
                card.show(panelPrincipal, "panel pasa altas");
                panelActual="pasaAltas";
            }
        });
        JButton botonPasaBajas = new JButton("Pasa Bajas");
        botonPasaBajas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout card = (CardLayout)panelPrincipal.getLayout();
                card.show(panelPrincipal, "panel pasa bajas");
                panelActual="pasaBajas";
            }
        });
        JButton botonRelacionales = new JButton("Operaciones RElacionales");
        botonRelacionales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout card = (CardLayout)panelPrincipal.getLayout();
                card.show(panelPrincipal, "panel relacionales");
            }
        });
        Container contenedor = this.getContentPane();
        contenedor.setLayout(new BorderLayout());
        panelBotones.add(botonPasaAltas);
        panelBotones.add(botonPasaBajas);

        contenedor.add(panelBotones, BorderLayout.EAST);
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new CardLayout());
        panelPasaAltas = new PanelPasaAltas(imagen);
        panelPasaBajas= new PanelPasaBajas(imagen);
        panelPrincipal.add(panelPasaAltas, "panel pasa altas");
        panelPrincipal.add(panelPasaBajas, "panel pasa bajas");
        //panelPrincipal.add(panelRelacionales, "panel relacionales");
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
        JButton botonBinarizar = new JButton("Aplicar binarizacion");
        contenedor.add(botonBinarizar, BorderLayout.SOUTH);
        botonBinarizar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                crearPanelBinarizacion();
            }
        });
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
    public void crearPanelBinarizacion(){
        System.out.println(panelActual);
        if(panelActual=="pasaBajas"){
            System.out.println("se creo pasabajas");
            panelUmbral= new PanelUmbral(panelPasaBajas.getImagenFiltrada());
            new FrameUmbral(panelUmbral);
        }else if (panelActual=="pasaAltas"){
            System.out.println("se creo pasa altas");
            panelUmbral=new PanelUmbral (panelPasaAltas.getImagenFiltrada());
            new FrameUmbral(panelUmbral);
        }else{
            return;
        }
    }
    private void actualizarPaneles(){
        panelPasaBajas.setImagen(imagen);
        panelPasaAltas.setImagen(imagen);
        
    }
}