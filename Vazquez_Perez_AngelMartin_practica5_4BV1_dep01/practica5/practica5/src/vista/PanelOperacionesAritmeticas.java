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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import modelo.Aritmeticas;
import modelo.ImageBufferedImage;

/**
 *
 * @author jhona
 */
public class PanelOperacionesAritmeticas extends JPanel {
    private PanelImagenLabel panel1;
    private PanelImagenLabel panel2;
    private PanelImagenLabel panelResultado;
    private Image imagen1;
    private Image imagen2;
    private Image imagenResultado;
    private ImageBufferedImage imageBuffered;
    private JComboBox<String> comboOperacion;
    private Aritmeticas aritmeticas;
    
    public PanelOperacionesAritmeticas(Image imagen1,Image imagen2){
        this.imagen1=imagen1;
        this.imagen2=imagen2;
        initComponents();
    }
    public void initComponents(){
        imageBuffered = new ImageBufferedImage();
        this.setLayout(new BorderLayout());
        JPanel panelTop= new JPanel(new GridLayout(1,2));
        panelTop.add(new Label("seleccione su operacion"));
        comboOperacion = new JComboBox<>();
        comboOperacion.addItem("Suma");
        comboOperacion.addItem("Resta");
        comboOperacion.addItem("Multiplicacion");
        comboOperacion.addItem("Division");
        panelTop.add(comboOperacion);
        JPanel panelCentro= new JPanel(new GridLayout(1,3));
        panel1=new PanelImagenLabel(imagen1,new Label("imagen 1"));
        panel2=new PanelImagenLabel(imagen2,new Label("imagen 2"));
        panelResultado= new PanelImagenLabel(imagen1,new Label("Imagen de Resultado"));
        panelCentro.add(panel1);
        panelCentro.add(panel2);
        panelCentro.add(panelResultado);
        this.add(panelTop,BorderLayout.NORTH);
        this.add(panelCentro,BorderLayout.CENTER);
        aritmeticas= new Aritmeticas(imagen1,imagen2);
        actualizarOperacion();
        comboOperacion.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                actualizarOperacion();
            }
        });
    }
    public void actualizarOperacion(){
        String operacion = (String)comboOperacion.getSelectedItem();
        Image nuevaImagen=null;
        if (operacion=="Suma"){
            nuevaImagen=aritmeticas.SumaDeImagenes();
        }else if (operacion=="Resta"){
            nuevaImagen=aritmeticas.RestaDeImagenes();
        }else if (operacion=="Multiplicacion"){
            nuevaImagen=aritmeticas.MultiplicacionDeImagenes();
        }else{
            nuevaImagen=aritmeticas.DivisionDeImagenes();
        }
        actualizarPanelResultado(nuevaImagen, operacion);
    }
    public void actualizarPanelResultado(Image nuevaImagen, String operacion){
        imagenResultado=nuevaImagen;
        panelResultado.setPanelLabel(imagenResultado,new Label("Imagenes " +operacion) );
    }
    public void setImagenes(Image imagen1,Image imagen2){
        this.imagen1=imagen1;
        this.imagen2=imagen2;
        panel1.setPanelLabel(imagen1, new Label("imagen 1"));
        panel2.setPanelLabel(imagen2,new Label("imagen 2"));
        actualizarImagenes();
        actualizarOperacion();

    }
    public void actualizarImagenes(){
        aritmeticas.setImagenes(imagen1,imagen2);
    }
}
