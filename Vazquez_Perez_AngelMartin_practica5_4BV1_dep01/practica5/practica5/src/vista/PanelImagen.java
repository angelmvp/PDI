/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.BorderLayout;
import java.awt.Image;
import javax.swing.JPanel;
import java.awt.*;

/**
 *
 * @author Saul
 */
public class PanelImagen extends JPanel {
    private CanvasImagen canvas;
    public PanelImagen(Image imagen) {
        initComponents(imagen);
    }
    private void initComponents(Image imagen) {
        canvas = new CanvasImagen(imagen);
        this.setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);
        this.setSize(imagen.getWidth(null), imagen.getHeight(null));
    }
    public void setImagen(Image imagen){
        canvas.setImagen(imagen);
    }
    
}
