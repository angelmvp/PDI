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

/**
 *
 * @author jhona
 */
public class FrameCompleto extends JFrame{
    private JPanel panelBotones;
    private JPanel panelPrincipal;
    private PanelRuido panelRuido;
    private PanelFiltroNoLineal panelFiltroNoLineal;
    //private PanelPasaBajas panelPasaBajas;
    private PanelImagen panelImagen;
    private BufferedImage buffered;
    private Image imagen;
    private ImageBufferedImage imageBuffered;
    private String panelActual;
    private PanelUmbral panelUmbral;
    public FrameCompleto() {
        super("Practica 9");        
        initComponents();
      addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0); 
            }
        });
    }
    private void initComponents() {
        imageBuffered = new ImageBufferedImage();
        seleccionarImagen();
        panelActual="pasaAltas";
        panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(3, 1, 4, 4));
        JButton botonRuido = new JButton("Ruido ");
        botonRuido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout card = (CardLayout)panelPrincipal.getLayout();
                card.show(panelPrincipal, "panel ruido");
                panelActual="ruido";
            }
        });
        JButton botonNoLineal = new JButton("Filtros no Lineales ");
        botonNoLineal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("filtros nos linealses");
                CardLayout card = (CardLayout)panelPrincipal.getLayout();
                card.show(panelPrincipal, "panel no lineal");
                panelActual="nolineal";
            }
        });
        Container contenedor = this.getContentPane();
        contenedor.setLayout(new BorderLayout());
        panelBotones.add(botonRuido);
        panelBotones.add(botonNoLineal);
        contenedor.add(panelBotones, BorderLayout.EAST);
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new CardLayout());
        panelRuido = new PanelRuido(imagen);
        panelFiltroNoLineal = new PanelFiltroNoLineal(imagen);
        panelPrincipal.add(panelRuido, "panel ruido");
        panelPrincipal.add(panelFiltroNoLineal, "panel no lineal");
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
//    public void crearPanelBinarizacion(){
//        System.out.println(panelActual);
//        if(panelActual=="pasaBajas"){
//            System.out.println("se creo pasabajas");
//            panelUmbral= new PanelUmbral(panelPasaBajas.getImagenFiltrada());
//            new FrameUmbral(panelUmbral);
//        }else if (panelActual=="pasaAltas"){
//            System.out.println("se creo pasa altas");
//            panelUmbral=new PanelUmbral (panelPasaAltas.getImagenFiltrada());
//            new FrameUmbral(panelUmbral);
//        }else{
//            return;
//        }
//    }
    private void actualizarPaneles(){
        panelRuido.setImagen(imagen);
        panelFiltroNoLineal.setImagen(imagen);   
    }
}