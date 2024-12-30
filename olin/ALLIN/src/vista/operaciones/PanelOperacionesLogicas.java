/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.operaciones;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import modelo.ImageBufferedImage;
import modelo.Logicas;
import vista.PanelImagenLabel;

/**
 *
 * @author jhona
 */
public class PanelOperacionesLogicas extends JPanel {
    private PanelImagenLabel panel1;
    private PanelImagenLabel panel2;
    private PanelImagenLabel panelResultado;
    private Image imagen1;
    private Image imagen2;
    private Image imagenResultado;
    private ImageBufferedImage imageBuffered;
    private JComboBox<String> comboOperacion;
    private Logicas logicas;
    private JCheckBox checkNot;
    
    public PanelOperacionesLogicas(Image imagen1,Image imagen2){
        this.imagen1=imagen1;
        this.imagen2=imagen2;
        initComponents();
    }
    public void initComponents(){
        imageBuffered = new ImageBufferedImage();
        this.setLayout(new BorderLayout());
        JPanel panelTop= new JPanel(new GridLayout(2,2));
        panelTop.add(new Label("seleccione su operacion logica"));
        comboOperacion = new JComboBox<>();
        comboOperacion.addItem("AND");
        comboOperacion.addItem("OR");
        comboOperacion.addItem("XOR");
        comboOperacion.addItem("NOT");
        panelTop.add(comboOperacion);
        JPanel panelCentro= new JPanel(new GridLayout(1,3));
        panel1=new PanelImagenLabel(imagen1,new Label("imagen 1"));
        panel2=new PanelImagenLabel(imagen2,new Label("imagen 2"));
        panelResultado= new PanelImagenLabel(imagen1,new Label("Imagen de Resultado"));
        panelCentro.add(panel1);
        panelCentro.add(panel2);
        checkNot = new JCheckBox("Marcar para negar la segunda imagen");
        panelTop.add(checkNot);
        panelCentro.add(panelResultado);
        this.add(panelTop,BorderLayout.NORTH);
        this.add(panelCentro,BorderLayout.CENTER);
        logicas= new Logicas(imagen1,imagen2);

        actualizarOperacion();
        comboOperacion.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                actualizarOperacion();
            }
        });
        checkNot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarOperacion();
            }
        });
    }
    public void actualizarOperacion(){
        String operacion = (String)comboOperacion.getSelectedItem();
        Image nuevaImagen=null;
        if (operacion=="AND"){
            nuevaImagen=logicas.ANDimagenes();
        }else if (operacion=="OR"){
            nuevaImagen=logicas.ORimagenes();
        }else if (operacion=="NOT"){
            if(checkNot.isSelected())
                nuevaImagen=logicas.NOTimagenes(2);
            else{
                nuevaImagen=logicas.NOTimagenes(1);
            }
        }else{
            nuevaImagen=logicas.XORimagenes();
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
        logicas.setImagenes(imagen1,imagen2);
    }
}
