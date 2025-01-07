/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.conversor;

import java.awt.BorderLayout;
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
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import modelo.ImageBufferedImage;

/**
 *
 * @author jhona
 */
public class FrameConversor extends JFrame{
    private PanelConversor panelConversor;
    private BufferedImage imagen;
    private Image img;
    private ImageBufferedImage buffered;
    private JComboBox<String> comboConversion;
    public FrameConversor() {
        super("Conversion de imagenes");        
        initComponents();
    }
    public FrameConversor(Image imagen) {
        super("Conversion de imagenes");
        this.img = imagen;
        this.buffered = new ImageBufferedImage();
        this.imagen = buffered.getBufferedImageColor(img);
        initComponents();
    }
    private void initComponents() {
        if (imagen==null){
            seleccionarImagen();
        }
        
        buffered = new ImageBufferedImage();
        img=buffered.getImage(imagen,4 );
        panelConversor= new PanelConversor(img);
        JPanel contenedor= new JPanel();
        contenedor.setLayout(new BorderLayout());
        
            JButton botonCargar = new JButton("Seleccionar Imagen");
            botonCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarImagen();
                Image nuevaImagen = buffered.getImage(imagen,4);
                panelConversor.setImagen(nuevaImagen);
                }
            });
        contenedor.add(panelConversor,BorderLayout.CENTER);
        contenedor.add(botonCargar, BorderLayout.NORTH);
        setContentPane(contenedor);
        setSize(500, 500);
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