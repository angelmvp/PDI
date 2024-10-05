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
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import modelo.ImageBufferedImage;
import modelo.Umbralizacion;

/**
 *
 * @author jhona
 */
public class PanelUmbral extends JPanel {
    private Image imagen;
    private Image imagenGrises;
    private Image imagenUmbralizada;
    private BufferedImage buffered;
    private ImageBufferedImage imageBuffered;
    private PanelImagenHistograma panelImagen;
    private PanelImagenHistograma panelImagenUmbralizada;
    private JSlider sliderPrimerUmbral;
    private JSlider sliderSegundoUmbral;
    private JSlider sliderTercerUmbral;
    private JCheckBox checkNegativo;
    private JButton botonAplicar;
    private Umbralizacion umbralizacion;
    public PanelUmbral(Image imagen){
        this.imagen=imagen;
        this.imagenUmbralizada=imagen;
        initComponents();
    }
    public void initComponents(){
        imageBuffered= new ImageBufferedImage();
        buffered= imageBuffered.getBufferedImage(imagen);
        imagenGrises=imageBuffered.getImage(buffered, 4);
        this.setLayout(new BorderLayout());
        sliderPrimerUmbral=new JSlider(0,255,0);
        sliderPrimerUmbral.setMajorTickSpacing(50);
        sliderPrimerUmbral.setPaintTicks(true);
        sliderPrimerUmbral.setPaintLabels(true);
        sliderSegundoUmbral=new JSlider(-1,255,-1);
        sliderSegundoUmbral.setMajorTickSpacing(50);
        sliderSegundoUmbral.setPaintTicks(true);
        sliderSegundoUmbral.setPaintLabels(true);
        sliderTercerUmbral=new JSlider(-1,255,-1);
        sliderTercerUmbral.setMajorTickSpacing(50);
        sliderTercerUmbral.setPaintTicks(true);
        sliderTercerUmbral.setPaintLabels(true);
        checkNegativo = new JCheckBox("Negativo de La imagen");
        botonAplicar = new JButton("Aplicar");
        panelImagen= new PanelImagenHistograma(imagenGrises);
        panelImagenUmbralizada= new PanelImagenHistograma(imagenUmbralizada);
        umbralizacion= new Umbralizacion(buffered);
        JPanel panelTop= new JPanel(new GridLayout(3,4));
            panelTop.add(new Label("Primer umbral"));
            panelTop.add(new Label("Segundo umbral"));
            panelTop.add(new Label("Tercer umbral"));
            panelTop.add(sliderPrimerUmbral);
            panelTop.add(sliderSegundoUmbral);
            panelTop.add(sliderTercerUmbral);
            panelTop.add(new Label("Si el Slider esta en 0 entonces no se activa ese canal de umbral"));
            panelTop.add(checkNegativo);
            panelTop.add(botonAplicar);
        JPanel panelCentro= new JPanel(new GridLayout(1,2));
            panelCentro.add(panelImagen);
            panelCentro.add(panelImagenUmbralizada);
        this.add(panelTop,BorderLayout.NORTH);
        this.add(panelCentro,BorderLayout.CENTER);
        botonAplicar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajustarUmbral();
            }
        });
        checkNegativo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                negativoImagen();
            }
        });
    }

    public void negativoImagen(){
        if(checkNegativo.isSelected()){
            Image nuevaImagen = umbralizacion.negativoImagen();
            panelImagenUmbralizada.setImagen(nuevaImagen);
        }else{
            ajustarUmbral();
        }
        
    }
    public void ajustarUmbral() {
        int umbral1=(int)sliderPrimerUmbral.getValue();
        int umbral2=(int)sliderSegundoUmbral.getValue();
        int umbral3=(int)sliderTercerUmbral.getValue();
        if (umbral2 != -1 && umbral1 > umbral2) {
            System.out.println("El primer umbral no puede ser mayor que el segundo.");
            return;
        }
        if (umbral3 != -1 && umbral2 > umbral3) {
            System.out.println("El segundo umbral no puede ser mayor que el tercero.");
            return;
        }
        if (umbral2 == -1 && umbral3 == -1) {
            imagenUmbralizada = umbralizacion.ajustarUmbral(umbral1);
        } else if (umbral3 == -1) {
            imagenUmbralizada = umbralizacion.ajustarUmbral(umbral1, umbral2);
        } else if (umbral2 == -1 && umbral3 != -1) {
            System.out.println("No puedes activar el tercer umbral sin activar el segundo.");
            return;
        } else {
            imagenUmbralizada = umbralizacion.ajustarUmbral(umbral1, umbral2, umbral3);
        }
        panelImagenUmbralizada.setImagen(imagenUmbralizada);
        
    }

    public void setImagen(Image imagen){
        this.imagen=imagen;
        buffered=imageBuffered.getBufferedImage(imagen);
        imagenGrises=imageBuffered.getImage(buffered, 5);
        panelImagen.setImagen(imagenGrises);
        panelImagenUmbralizada.setImagen(imagen);
        umbralizacion.setImagen(imagen);
        ajustarUmbral();
    }
}
