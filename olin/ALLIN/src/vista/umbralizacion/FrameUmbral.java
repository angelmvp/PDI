/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.umbralizacion;

import vista.umbralizacion.PanelUmbral;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author jhona
 */
public class FrameUmbral extends JFrame{
   private PanelUmbral panelUmbral;
   
   public FrameUmbral(PanelUmbral panelUmbral){
       this.panelUmbral=panelUmbral;
       initComponents();
   }
   
   public void initComponents(){
       Container contenedor = this.getContentPane();
        contenedor.setLayout(new BorderLayout());
        contenedor.add(panelUmbral, BorderLayout.CENTER);
        setSize(1200, 700);
        setLocation(100, 50);
        setVisible(true);
   }
}
