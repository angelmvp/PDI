/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import modelo.ImageBufferedImage;

/**
 *
 * @author jhona
 */
public class PanelImagenCargar extends JPanel {
    private PanelImagenLabel panelImagen;
    private JButton botonCargar;
    private BufferedImage imagen;
    private Image img;
    private ImageBufferedImage buffered;
    private Label labelImagen;
    private String etiquetaImagen;
    public PanelImagenCargar(String etiqueta){
        etiquetaImagen=etiqueta;
        labelImagen=new Label(etiquetaImagen);
        initComponents();
    }
    public void initComponents(){
        seleccionarImagen();
        buffered= new ImageBufferedImage();
        img=buffered.getImage(imagen,5);
        this.setLayout(new BorderLayout());
        panelImagen = new PanelImagenLabel(imagen,labelImagen);
            botonCargar = new JButton("Seleccionar Imagen");
            botonCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarImagen();
                Image nuevaImagen = buffered.getImage(imagen,5);
                img=nuevaImagen;
                panelImagen.setPanelLabel(nuevaImagen, labelImagen);
                }
            });
           System.out.println("imagen añadida");
        this.add(botonCargar,BorderLayout.NORTH);
        this.add(panelImagen,BorderLayout.CENTER);
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
                
            }
        }
    }
    public void setImagen(Image img){
        this.img=img;
        this.imagen=buffered.getBufferedImage(img);
    }
    public Image getImagen(){
        if(img==null){
            System.out.println("es nulo");
        }
        return img;
    }
}

