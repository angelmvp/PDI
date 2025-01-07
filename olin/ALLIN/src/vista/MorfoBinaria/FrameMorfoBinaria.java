/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.MorfoBinaria;

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

/**
 *
 * @author jhona
 */
public class FrameMorfoBinaria extends JFrame{
    private PanelMorfologicoBinario panelMorfologicoBinario;
    private Image imagen;
    public FrameMorfoBinaria() {
        super("Morfologia Binaria");        
        initComponents();
    }
    public FrameMorfoBinaria(Image imagen) {
        super("Morfologia Binaria");        
        this.imagen=imagen;
        initComponents();
    }
    private void initComponents() {
        if(imagen==null){
            seleccionarImagen();
        }
        
        Container contenedor = this.getContentPane();
        contenedor.setLayout(new BorderLayout());
        panelMorfologicoBinario = new PanelMorfologicoBinario(imagen);
        contenedor.add(panelMorfologicoBinario, BorderLayout.CENTER);
        
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
        panelMorfologicoBinario.setImagen(imagen);
    }
}