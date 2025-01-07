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
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author jhona
 */
public class PanelImagenLabel extends JPanel {
    private PanelImagen panel;
    private Image imagen;
    private Label label;
    private JButton botonGenerar;
    public PanelImagenLabel(Image imagen,Label label){
        this.imagen=imagen;
        this.label=label;
        initComponents();
    }
    public void initComponents(){
        panel=new PanelImagen(imagen);
        botonGenerar = new JButton("Generar Frame");
        this.setLayout(new BorderLayout());
        this.add(panel,BorderLayout.CENTER);
        this.add(label, BorderLayout.NORTH); 
        this.add(botonGenerar,BorderLayout.SOUTH);
        
        botonGenerar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                generarFrame();
            }
        });
    }
    public void setPanelLabel(Image imagen,Label label){
        this.imagen=imagen;
        panel.setImagen(imagen);
        this.remove(this.label);
        this.label=label;
        this.add(this.label,BorderLayout.NORTH);
        this.label.repaint();
        this.repaint();
        this.revalidate();
    }
    public void generarFrame(){
        new FrameImagenManipulacion(this.imagen);
    }
    
}
