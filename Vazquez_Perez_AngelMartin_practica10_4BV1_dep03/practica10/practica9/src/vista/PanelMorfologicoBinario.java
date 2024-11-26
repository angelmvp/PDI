/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import modelo.ImageBufferedImage;
import modelo.MorfologicaBinaria;
import modelo.Ruido;

/**
 *
 * @author jhona
 */
public class PanelMorfologicoBinario extends JPanel{
    private Image imagen;
    private Image imagenGrises;
    private Image imagenModificada;
    private BufferedImage buffered;
    private ImageBufferedImage imageBuffered;
    private PanelImagenHistograma panelImagen;
    private PanelImagenHistograma panelImagenModificada;
    private MorfologicaBinaria morfologicaBinaria;
    private JComboBox<String> comboEleccion;
    private JPanel panelOpcional;
    public PanelMorfologicoBinario(Image imagen){
        this.imagen=imagen;
        this.imagenModificada=imagen;
        initComponents();
    }
    public void initComponents(){
        imageBuffered= new ImageBufferedImage();
        buffered= imageBuffered.getBufferedImageColor(imagen);
        imagenGrises=imageBuffered.getImage(buffered, 5);
        this.setLayout(new BorderLayout());
        panelImagen= new PanelImagenHistograma(imagenGrises);
        panelImagenModificada= new PanelImagenHistograma(imagenModificada);
        morfologicaBinaria = new MorfologicaBinaria(imagen);
        JPanel panelTop= new JPanel(new GridLayout(1,4));
            comboEleccion = new JComboBox<>();
            comboEleccion.addItem("Erosión");
            comboEleccion.addItem("Dilatación");
            comboEleccion.addItem("Apertura");
            comboEleccion.addItem("Clausura");
            panelTop.add(new Label("Escoja el tipo de operacion Morfologica a realizar"));
            panelTop.add(comboEleccion);
        JPanel panelCentro= new JPanel(new GridLayout(1,2));
            panelCentro.add(panelImagen);
            panelCentro.add(panelImagenModificada);
        this.add(panelTop,BorderLayout.NORTH);
        this.add(panelCentro,BorderLayout.CENTER);
        comboEleccion.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                aplicarMorfologia();
            }
        });
        
           }
    public void aplicarMorfologia(){
        
    }
    public void setImagen(Image imagen){
        this.imagen=imagen;
        buffered=imageBuffered.getBufferedImageColor(imagen);
        imagenGrises=imageBuffered.getImage(buffered, 5);
        panelImagen.setImagen(imagenGrises);
        panelImagenModificada.setImagen(imagen);
        aplicarMorfologia();
    }
}
