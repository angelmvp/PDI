    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package vista;
import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.GridLayout;
import vista.conversor.FrameConversor;
import vista.filtrosLineales.FrameFiltrosLineales;
import vista.filtrosNoLineales.FrameFiltrosNoLineales;
import vista.histograma.FramePracticaHistograma;
import vista.modificacionHistograma.FrameModificacionHistograma;
import vista.operaciones.FrameOperaciones;
import vista.practica1.FramePractica1;
import vista.ruido.FrameRuido;
import vista.umbralizacion.FrameUmbralizacion;

public class FrameAllIn extends JFrame {
    private Image imagen;
    private PanelImagen panelImagen;

    public FrameAllIn() {
        initComponents();
    }

    public FrameAllIn(Image imagen) {
        this.imagen = imagen;
        initComponents();
    }

    private void initComponents() {
        if(imagen==null){
            seleccionarImagen();
        }   
         
        setTitle("Programa de Procesamiento de Imágenes");
        setSize(800, 600);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JPanel panelBotones = new JPanel(new GridLayout(3, 3, 0, 0));

        JButton botonPractica1 = new JButton("Practica 1");
        JButton botonHistograma = new JButton("Histograma");
        JButton botonConversor = new JButton("Conversor");
        JButton botonUmbralizacion = new JButton("Umbralizacion");
        JButton botonOperaciones = new JButton("Operaciones");
        JButton botonModificacionHistograma = new JButton("Modificación Histograma");
        JButton botonRuido = new JButton("Ruido");
        JButton botonFiltrosLineales = new JButton("Filtros Lineales");
        JButton botonFiltrosNoLineales = new JButton("Filtros No Lineales");
        
        panelBotones.add(botonPractica1);
        panelBotones.add(botonHistograma);
        panelBotones.add(botonConversor);
        panelBotones.add(botonUmbralizacion);
        panelBotones.add(botonOperaciones);
        panelBotones.add(botonModificacionHistograma);
        panelBotones.add(botonRuido);
        panelBotones.add(botonFiltrosLineales);
        panelBotones.add(botonFiltrosNoLineales);

        botonConversor.addActionListener(e -> new FrameConversor(imagen));
        botonFiltrosLineales.addActionListener(e -> new FrameFiltrosLineales(imagen));
        botonFiltrosNoLineales.addActionListener(e -> new FrameFiltrosNoLineales(imagen));
        botonHistograma.addActionListener(e -> new FramePracticaHistograma(imagen));
        botonModificacionHistograma.addActionListener(e -> new FrameModificacionHistograma(imagen));
        botonOperaciones.addActionListener(e -> new FrameOperaciones(imagen));
        botonPractica1.addActionListener(e -> new FramePractica1(imagen));
        botonRuido.addActionListener(e -> new FrameRuido(imagen));
        botonUmbralizacion.addActionListener(e -> new FrameUmbralizacion(imagen));

        panelImagen= new PanelImagen(imagen);
        this.add(panelImagen,BorderLayout.CENTER);
        this.add(panelBotones, BorderLayout.NORTH);
        this.setVisible(true);
    }

    private void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            try {
                imagen = ImageIO.read(archivoSeleccionado);
                if (imagen != null) {
                    System.out.println("bien");
                } else {
                    System.out.println("Imagen nula");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar la imagen", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, "Has seleccionado: " + mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}
