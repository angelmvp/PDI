/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.umbralizacion;

import vista.umbralizacion.PanelUmbral;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;

/**
 *
 * @author jhona
 */
public final class FrameUmbral extends JFrame{
   private final PanelUmbral panelUmbral;
   
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
