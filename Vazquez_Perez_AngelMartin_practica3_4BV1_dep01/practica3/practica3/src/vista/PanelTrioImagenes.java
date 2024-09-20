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
import javax.swing.JPanel;

/**
 *
 * @author jhona
 */
public class PanelTrioImagenes extends JPanel{
    private PanelImagen panelImagen1;
    private PanelImagen panelImagen2;
    private PanelImagen panelImagen3;
    private Image imagen1;
    private Image imagen2;
    private Image imagen3;
    private Label etiqueta1;
    private Label etiqueta2;
    private Label etiqueta3;
    public PanelTrioImagenes(Image imagen1,Image imagen2,Image imagen3,
            String etiqueta1,String etiqueta2,String etiqueta3){
        this.imagen1=imagen1;
        this.imagen2=imagen2;
        this.imagen3=imagen3;
        this.etiqueta1= new Label(etiqueta1);
        this.etiqueta2= new Label(etiqueta2);
        this.etiqueta3= new Label(etiqueta3);
        initComponents();
    }
    public void initComponents(){
        panelImagen1= new PanelImagen(imagen1);
        panelImagen2= new PanelImagen(imagen2);
        panelImagen3= new PanelImagen(imagen3);
        JPanel etiquetas = new JPanel (new GridLayout(3,1));
        JPanel imagenes = new JPanel (new GridLayout(3,1));
        this.setLayout(new BorderLayout());
        etiquetas.add(etiqueta1);
        imagenes.add(panelImagen1);
        etiquetas.add(etiqueta2);
        imagenes.add(panelImagen2);
        etiquetas.add(etiqueta3);
        imagenes.add(panelImagen3);
        this.add(etiquetas,BorderLayout.WEST);
        this.add(imagenes,BorderLayout.CENTER);
    }
    
    public void setTrio(Image imagen1,Image imagen2,Image imagen3,
            String etiqueta1,String etiqueta2,String etiqueta3){
        this.imagen1=imagen1;
        this.imagen2=imagen2;
        this.imagen3=imagen3;
        this.etiqueta1.setText(etiqueta1);
        this.etiqueta2.setText(etiqueta2);
        this.etiqueta3.setText(etiqueta3);
        panelImagen1.setImagen(imagen1);
        panelImagen2.setImagen(imagen2);
        panelImagen3.setImagen(imagen3);
        this.repaint();
        this.revalidate();
    }
}
