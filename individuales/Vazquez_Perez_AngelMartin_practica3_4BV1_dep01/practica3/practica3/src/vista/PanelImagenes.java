/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import javax.swing.JPanel;

/**
 *
 * @author jhona
 */
public class PanelImagenes extends JPanel{
    private Image imagenColor;
    private Image imagen1;
    private Image imagen2;
    private Image imagen3;
    private Label etiquetaColor;
    private Label etiqueta1;
    private Label etiqueta2;
    private Label etiqueta3;
    private PanelImagenLabel panelImagenLabelColor;
    private PanelImagenLabel panelImagenLabel1;
    private PanelImagenLabel panelImagenLabel2;
    private PanelImagenLabel panelImagenLabel3 ;
    
    public PanelImagenes(Image imagenColor,Image imagen1,Image imagen2,Image imagen3,
            String etiqueta1,String etiqueta2,String etiqueta3,String etiquetaColor){
        this.imagenColor=imagenColor;
        this.imagen1=imagen1;
        this.imagen2=imagen2;
        this.imagen3=imagen3;
        this.etiquetaColor= new Label(etiquetaColor);
        this.etiqueta1= new Label(etiqueta1);
        this.etiqueta2= new Label(etiqueta2);
        this.etiqueta3= new Label(etiqueta3);
        initComponents();
    }
    public void initComponents(){
        panelImagenLabelColor= new PanelImagenLabel(imagenColor,etiquetaColor);
        panelImagenLabel1= new PanelImagenLabel(imagen1,etiqueta1);
        panelImagenLabel2= new PanelImagenLabel(imagen2,etiqueta2);
        panelImagenLabel3= new PanelImagenLabel(imagen3,etiqueta3);
        this.setLayout(new GridLayout(2,2));
        this.add(panelImagenLabelColor);
        this.add(panelImagenLabel1);
        this.add(panelImagenLabel2);
        this.add(panelImagenLabel3);
    }
    
    public void setNuevasImagenes(Image imagenColor,Image imagen1,Image imagen2,Image imagen3,
            String etiquetaColor,String etiqueta1,String etiqueta2,String etiqueta3){
        this.imagenColor=imagenColor;
        this.imagen1=imagen1;
        this.imagen2=imagen2;
        this.imagen3=imagen3;
        this.etiquetaColor= new Label(etiquetaColor);
        this.etiqueta1= new Label(etiqueta1);
        this.etiqueta2= new Label(etiqueta2);
        this.etiqueta3= new Label(etiqueta3);
        panelImagenLabelColor.setPanelLabel(imagenColor, this.etiquetaColor);
        panelImagenLabel1.setPanelLabel(imagen1, this.etiqueta1);
        panelImagenLabel2.setPanelLabel(imagen2, this.etiqueta2);
        panelImagenLabel3.setPanelLabel(imagen3, this.etiqueta3);
        this.repaint();
        this.revalidate();
    }
}
