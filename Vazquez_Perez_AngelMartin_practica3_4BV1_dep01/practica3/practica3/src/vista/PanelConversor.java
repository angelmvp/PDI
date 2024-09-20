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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import modelo.ConversorRGB;
import modelo.ImageBufferedImage;

/**
 *
 * @author jhona
 */
public class PanelConversor extends JPanel{
    private PanelImagen panelImagenOriginal;
    private PanelImagen panelImagenModificada;
    private PanelTrioImagenes panelImagenes;
    private JComboBox<String> comboConversion;
    private Image image;
    private BufferedImage buffered;
    private ImageBufferedImage imageBuffered;
    private ConversorRGB conversor;
    private JButton botonGenerar;
    public PanelConversor(Image image){
        this.image=image;
        initComponents();
    }
    public void initComponents(){
        imageBuffered = new ImageBufferedImage();
        buffered=imageBuffered.getBufferedImageColor(image);
        panelImagenOriginal= new PanelImagen(image);
        panelImagenModificada = new PanelImagen(image);
        panelImagenes= new PanelTrioImagenes(image,image,image,"original","original","original");
        conversor= new ConversorRGB(buffered);
        comboConversion= new JComboBox<>();
        comboConversion.addItem("RGB to CMY");
        comboConversion.addItem("RGB to CMY canales");
        comboConversion.addItem("RGB to CMY canales Gris");
        comboConversion.addItem("RGB to HIS");
        comboConversion.addItem("RGB to HSV");
        comboConversion.addItem("RGB to YIQ");
        comboConversion.addItem("RGB to LAB");
        comboConversion.addItem("RGB TO RGB canales");
        comboConversion.addItem("RGB TO RGB canales Gris");
        botonGenerar = new JButton("Generar Frame");
        this.setLayout(new BorderLayout());
        JPanel top = new JPanel(new GridLayout(2,2));
        top.add(new Label("Elija a que convertir su imagen RGB"));
        top.add(comboConversion);
        top.add(new Label("IMAGEN ORIGINAL"));
        top.add(new Label("IMAGEN MODIFICADA"));
        JPanel centro = new JPanel ( new GridLayout(1,2));
        centro.add(panelImagenOriginal);
        centro.add(panelImagenes);
        this.add(top,BorderLayout.NORTH);
        this.add(centro,BorderLayout.CENTER);
        
        comboConversion.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                actualizarConversion();
                panelImagenes.repaint();
            }
            });
            botonGenerar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                generarFrame();
            }             
            });
      
    }

public void actualizarConversion() {
    String opcion = (String) comboConversion.getSelectedItem();
    System.out.println(opcion);
    Image nuevaImagen = null;

    if (opcion.equals("RGB to CMY")) {
        nuevaImagen = conversor.toCMY();
        panelImagenModificada.setImagen(nuevaImagen);
    } else if (opcion.equals("RGB to CMY canales")) {
        Image[] cmyCanales = conversor.toCMYCanales();
        panelImagenes.setTrio(cmyCanales[0], cmyCanales[1], cmyCanales[2], "C", "M", "Y");
    } else if (opcion.equals("RGB to CMY canales Gris")) {
        Image[] cmyCanalesGris = conversor.toCMYCanalesGris();
        panelImagenes.setTrio(cmyCanalesGris[0], cmyCanalesGris[1], cmyCanalesGris[2], 
                "C (Gris)", "M (Gris)", "Y (Gris)");
    } else if (opcion.equals("RGB to HIS")) {
        Image[] his = conversor.toHIS();
        panelImagenes.setTrio(his[0], his[1], his[2], "H", "I", "S");
    } else if (opcion.equals("RGB to HSV")) {
        Image[] hsv = conversor.toHSV();
        panelImagenes.setTrio(hsv[0], hsv[1], hsv[2], "H", "S", "V");
    } else if (opcion.equals("RGB to YIQ")) {
        Image[] yiq = conversor.toYIQ();
        panelImagenes.setTrio(yiq[0], yiq[1], yiq[2], "Y", "I", "Q");
    } else if (opcion.equals("RGB to LAB")) {
        Image[] lab = conversor.toLAB();
        panelImagenes.setTrio(lab[0], lab[1], lab[2], "L", "A", "B");
    } else if (opcion.equals("RGB TO RGB canales")) {
        Image[] rgbCanales = conversor.toRGBCanales();
        panelImagenes.setTrio(rgbCanales[0], rgbCanales[1], rgbCanales[2], "R", "G", "B");
    } else if (opcion.equals("RGB TO RGB canales Gris")) {
        Image[] rgbCanalesGris = conversor.toRGBCanalesGris();
        panelImagenes.setTrio(rgbCanalesGris[0], rgbCanalesGris[1], rgbCanalesGris[2],
                "R (Gris)", "G (Gris)", "B (Gris)");
    }

}
public void setImagen(Image imagen){
   panelImagenOriginal.setImagen(imagen);
   this.image=imagen;
   buffered=imageBuffered.getBufferedImageColor(image);
   panelImagenes.setTrio(imagen, imagen, imagen, "", "", "");
   conversor.setImagen(imagen);
   actualizarConversion();
   
   repaint();
   revalidate();
}
public void generarFrame(){
    
}

    
    
    
    

    
}
