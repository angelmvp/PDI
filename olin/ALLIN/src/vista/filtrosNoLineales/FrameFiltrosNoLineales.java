/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.filtrosNoLineales;

import vista.filtrosNoLineales.PanelFiltroNoLineal;
import vista.ruido.PanelRuido;
import vista.umbralizacion.PanelUmbral;
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
public class FrameFiltrosNoLineales extends JFrame{
    private PanelFiltroNoLineal panelFiltroNoLineal;
    //private PanelPasaBajas panelPasaBajas;
    private BufferedImage buffered;
    private Image imagen;
    private ImageBufferedImage imageBuffered;
    public FrameFiltrosNoLineales() {
        super("Filtros No Lnieales");        
        initComponents();
    }
    public FrameFiltrosNoLineales(Image imagen){
       super("Filtros No Lnieales");  
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
        Container contenedor = this.getContentPane();
        contenedor.setLayout(new BorderLayout());
        panelFiltroNoLineal = new PanelFiltroNoLineal(imagen);
        contenedor.add(panelFiltroNoLineal, BorderLayout.CENTER);
        
        JButton botonCargar = new JButton("Seleccionar Imagen");
            botonCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarImagen();
                actualizarPaneles();
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
    private void actualizarPaneles(){
        panelFiltroNoLineal.setImagen(imagen);   
    }
}