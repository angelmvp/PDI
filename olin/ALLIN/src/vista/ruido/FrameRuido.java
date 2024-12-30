/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.ruido;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Image;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author jhona
 */
public class FrameRuido extends JFrame{
    private Image imagen;
    public FrameRuido(){
        initComponents();
    }
    public FrameRuido(Image imagen){
      super("Ruido");    
      this.imagen=imagen;
      initComponents();
    }
    private void initComponents() {
        if(imagen==null){
            seleccionarImagen();
        }  
        PanelRuido panelRuido = new PanelRuido(imagen);
        this.add(panelRuido);
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
}
