/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import modelo.ConversortoRGB;

/**
 *
 * @author jhona
 */
public class PanelConversion extends JPanel {
    private Image imagen;
    private Image imagenConvertida;
    private JComboBox<String> comboTipoImagen;
    private PanelImagen panelImagenOriginal;
    private PanelImagen panelImagenConvertida;
    private JButton botonConvertir;
    private ConversortoRGB conversortoRGB;
    public PanelConversion(Image imagen){
        this.imagen=imagen;
        initComponents();
    }
    public void initComponents(){
        conversortoRGB= new ConversortoRGB(imagen);
        imagenConvertida=imagen;
            comboTipoImagen= new JComboBox<>();
            comboTipoImagen.addItem("CMY to RGB");
            comboTipoImagen.addItem("HSI to RGB");
            comboTipoImagen.addItem("HSV to RGB");
            comboTipoImagen.addItem("YIQ to RGB");
            comboTipoImagen.addItem("LAB to RGB");

            JPanel panelConversionTop = new JPanel(new GridLayout(1,2));
            panelConversionTop.add(comboTipoImagen);
            botonConvertir = new JButton("invertir Conversion");
            panelConversionTop.add(botonConvertir);  
            this.setLayout(new BorderLayout());
            JPanel panelCentro= new JPanel(new GridLayout(1,2));
            panelImagenOriginal=new PanelImagen(imagen);
            panelImagenConvertida=new PanelImagen(imagenConvertida);
            panelCentro.add(panelImagenOriginal);
            panelCentro.add(panelImagenConvertida);
            this.add(panelCentro,BorderLayout.CENTER);
            this.add(panelConversionTop,BorderLayout.NORTH);
            botonConvertir.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                convertirImagen();
            }
        });
    }
    public void convertirImagen(){
        String opcion = (String)comboTipoImagen.getSelectedItem();
        Image nuevaImagen=null;
        if(opcion=="CMY to RGB"){
            nuevaImagen= conversortoRGB.CMYtoRGB();
        }else if (opcion=="HSI to RGB"){
            nuevaImagen= conversortoRGB.HSItoRGB();
        }else if (opcion=="HSV to RGB"){
            nuevaImagen= conversortoRGB.HSVtoRGB();
            System.out.println("ldkajfladk");
        }else if (opcion=="YIQ to RGB"){
            nuevaImagen= conversortoRGB.YIQtoRGB();
        }else if (opcion=="LAB to RGB"){
            nuevaImagen= conversortoRGB.LABtoRGB();
        }
        this.setImagen(nuevaImagen);
    }
    public void setImagen(Image imagen){
        this.imagenConvertida=imagen;
        panelImagenConvertida.setImagen(imagenConvertida);
    }
}
