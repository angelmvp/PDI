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
    private PanelOperacionesAritmeticas panelAritmeticas;
    private PanelOperacionesLogicas  panelLogicas;
    private PanelOperacionesRelacionales panelRelacionales;
    private PanelImagen panelImagen;
    
    private BufferedImage buffered1;
    private BufferedImage buffered2;
    private Image imagen1;
    private Image imagen2;
    private ImageBufferedImage imageBuffered;
    public FrameCompleto() {
        super("Practica 5");        
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
        imagen1=seleccionarImagenUno();
        imagen2=seleccionarImagenDos();

        panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(3, 1, 4, 4));
        JButton botonAritmeticas = new JButton("Operaciones Aritmeticas");
        botonAritmeticas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout card = (CardLayout)panelPrincipal.getLayout();
                card.show(panelPrincipal, "panel aritmeticas");
            }
        });
        JButton botonLogicas = new JButton("Operaciones Logicas");
        botonLogicas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout card = (CardLayout)panelPrincipal.getLayout();
                card.show(panelPrincipal, "panel logicas");
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
        panelBotones.add(botonAritmeticas);
        panelBotones.add(botonLogicas);
        panelBotones.add(botonRelacionales);
        

        contenedor.add(panelBotones, BorderLayout.EAST);
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new CardLayout());
        panelLogicas = new PanelOperacionesLogicas(imagen1,imagen2);
        panelAritmeticas = new PanelOperacionesAritmeticas(imagen1,imagen2);
        panelRelacionales=new PanelOperacionesRelacionales(imagen1,imagen2);
        panelPrincipal.add(panelAritmeticas, "panel aritmeticas");
        panelPrincipal.add(panelLogicas, "panel logicas");
        panelPrincipal.add(panelRelacionales, "panel relacionales");
        contenedor.add(panelPrincipal, BorderLayout.CENTER);
        
        JButton botonCargarUno = new JButton("Seleccionar Imagen 1");
            botonCargarUno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imagen1 = seleccionarImagenUno();
                actualizarPaneles();
                }
            });
        JButton botonCargarDos = new JButton("Seleccionar Imagen 2");
            botonCargarDos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imagen2 =seleccionarImagenDos();
                actualizarPaneles();
                }
            });
        JPanel panelSeleccion = new JPanel(new GridLayout(1,2));
        panelSeleccion.add(botonCargarUno);
        panelSeleccion.add(botonCargarDos);
        contenedor.add(panelSeleccion,BorderLayout.NORTH);
        setSize(800, 700);
        setLocation(222, 22);
        setVisible(true);
    }
    private Image seleccionarImagenUno() {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showOpenDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            try {
                buffered1 = ImageIO.read(archivoSeleccionado);
                if(buffered1==null){
                    System.out.println("imagen nula");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar la imagen", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        Image nuevaImagen;
        nuevaImagen=imageBuffered.getImage(buffered1, 5);
        return nuevaImagen;
    }
    private Image seleccionarImagenDos() {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showOpenDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            try {
                buffered2 = ImageIO.read(archivoSeleccionado);
                if(buffered2==null){
                    System.out.println("imagen nula");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar la imagen", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        Image nuevaImagen;
        nuevaImagen=imageBuffered.getImage(buffered2, 5);
        return nuevaImagen;
    }
    private void actualizarPaneles(){
        panelAritmeticas.setImagenes(imagen1,imagen2);
        panelLogicas.setImagenes(imagen1,imagen2);
        panelRelacionales.setImagenes(imagen1,imagen2);
        
    }
}