/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import javax.swing.JFrame;
import java.awt.Color;


/**
 *
 * @author jhona
 */
public class FrameHistograma extends JFrame{
    private PanelHistograma panelHistograma;
    private Color color;    
    public FrameHistograma(){
        initcomponents();
    }
    public void initcomponents(){
        double[] probabilidades = new double[255];
        for (int i = 0; i < probabilidades.length; i++) {
            probabilidades[i] = (Math.random()); 
        }
        panelHistograma= new PanelHistograma(probabilidades,color);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panelHistograma);
        this.setSize(500,500);
        this.setVisible(true);
    }
    
}
