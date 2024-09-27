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
    private PanelImagen panelImagen;
    private PanelCanales panelCanales;
    private PanelImagenHistograma panelImagenHistograma;
    private BufferedImage imagen;
    private Image img;
    private ImageBufferedImage buffered;
    public FrameCompleto() {
        super("Practica 2");        
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
        img=buffered.getImage(imagen,4 );
        //imagen= buffered.getBufferedImage(img);
        panelImagenHistograma= new PanelImagenHistograma(img);
        JPanel contenedor= new JPanel();
        contenedor.setLayout(new BorderLayout());
        
            JButton botonCargar = new JButton("Seleccionar Imagen");
            botonCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarImagen();
                Image nuevaImagen = buffered.getImage(imagen,4);
                panelImagenHistograma.setImagen(nuevaImagen);
                }
            });
//
        contenedor.add(botonCargar, BorderLayout.NORTH);
        contenedor.add(panelImagenHistograma, BorderLayout.CENTER);
        setContentPane(contenedor);
        setSize(900, 500);
        setLocation(222, 222);
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
}
