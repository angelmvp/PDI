/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.ruido;

import vista.histograma.PanelImagenHistograma;
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
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import modelo.ImageBufferedImage;
import modelo.Ruido;

/**
 *
 * @author jhona
 */
public class PanelRuido extends JPanel {
    private Image imagen;
    private Image imagenGrises;
    private Image imagenRuido;
    private BufferedImage buffered;
    private ImageBufferedImage imageBuffered;
    private PanelImagenHistograma panelImagen;
    private PanelImagenHistograma panelImagenRuido;
    private JButton botonAplicar;
    private JComboBox<String> comboEleccion;
    private Ruido ruido;
    private JTextField texto1;
    private JSlider slider1;
    private JTextField texto2;
    private JSlider slider2;
    private Label instrucciones1;
    private Label instrucciones2;
    private JPanel panelOpcional;
    public PanelRuido(Image imagen){
        this.imagen=imagen;
        this.imagenRuido=imagen;
        initComponents();
    }
    public void initComponents(){
        imageBuffered= new ImageBufferedImage();
        buffered= imageBuffered.getBufferedImageColor(imagen);
        imagenGrises=imageBuffered.getImage(buffered, 5);
        this.setLayout(new BorderLayout());
        botonAplicar = new JButton("Aplicar");
        panelImagen= new PanelImagenHistograma(imagenGrises);
        panelImagenRuido= new PanelImagenHistograma(imagenRuido);
        ruido = new Ruido(imagen);
        JPanel panelTop= new JPanel(new GridLayout(1,4));
            comboEleccion = new JComboBox<>();
            comboEleccion.addItem("Ruido Coherente");
            comboEleccion.addItem("Ruido Gamma");
            comboEleccion.addItem("Ruido Gaussiano");
            comboEleccion.addItem("Ruido ExponencialNegativo");
            comboEleccion.addItem("Ruido Rayleigh");
            comboEleccion.addItem("Ruido Sal y pimienta");
            comboEleccion.addItem("Ruido Uniforme");
            comboEleccion.addItem("Ruido SOLO SAL");
            comboEleccion.addItem("Ruido SOLO PIMIENTA");
            panelTop.add(new Label("Escoja el tipo de ruido"));
            panelTop.add(comboEleccion);
            panelTop.add(botonAplicar);
            panelOpcional = new JPanel(new GridLayout(3,2));
                instrucciones1 = new Label("valor de u0");
                instrucciones2 = new Label("valor de y0");
                instrucciones1.setVisible(true);
                instrucciones2.setVisible(true);
                texto1=new JTextField("1");
                texto1.setVisible(true);
                texto2=new JTextField("2");
                texto2.setVisible(true);
                slider1 = new JSlider(-180, 180, 0);
                slider1.setMajorTickSpacing(60);
                slider1.setPaintTicks(true);
                slider1.setPaintLabels(true);
                slider2 = new JSlider(-180, 180, 1);
                slider2.setMajorTickSpacing(60);
                slider2.setPaintTicks(true);
                slider2.setPaintLabels(true);
                panelOpcional.add(instrucciones1);
                panelOpcional.add(instrucciones2);
                panelOpcional.add(slider1);
                panelOpcional.add(slider2);
                panelOpcional.add(texto1);
                panelOpcional.add(texto2);
            panelTop.add(panelOpcional);
        JPanel panelCentro= new JPanel(new GridLayout(1,2));
            panelCentro.add(panelImagen);
            panelCentro.add(panelImagenRuido);
        this.add(panelTop,BorderLayout.NORTH);
        this.add(panelCentro,BorderLayout.CENTER);
        botonAplicar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aplicarRuido();
            }
        });
        comboEleccion.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String ruidoSeleccionado = (String) comboEleccion.getSelectedItem();
                    if ("Ruido Coherente".equals(ruidoSeleccionado) ) {
                        instrucciones1.setText("valor xo");
                        instrucciones2.setText("valor yo");
                        slider1.setMaximum(180);
                        slider1.setMinimum(-180);
                        slider1.setMajorTickSpacing(50);
                        slider2.setMaximum(180);
                        slider2.setMinimum(-180);
                        cambiarOpcional1(true);
                        cambiarOpcional2(true);
                    } else if ("Ruido Gaussiano".equals(ruidoSeleccionado)){
                        instrucciones1.setText("varianza");
                        instrucciones2.setText("media");
                        slider1.setMaximum(200);
                        slider1.setMinimum(0);
                        slider1.setMajorTickSpacing(50);
                        slider2.setMaximum(10);
                        slider2.setMinimum(0);
                        slider2.setMajorTickSpacing(2);
                        cambiarOpcional1(true);
                        cambiarOpcional2(true);
                    }else if("Ruido Uniforme".equals(ruidoSeleccionado) ){
                        instrucciones1.setText("valor min");
                        instrucciones2.setText("valor max");
                        slider1.setMaximum(200);
                        slider1.setMajorTickSpacing(50);
                        slider2.setMaximum(200);
                        slider2.setMajorTickSpacing(50);
                        cambiarOpcional1(true);
                        cambiarOpcional2(true);
                    }else if ("Ruido Sal y pimienta".equals(ruidoSeleccionado) ){
                        cambiarOpcional1(false);
                        cambiarOpcional2(false);
                    }else if ("Ruido Rayleigh".equals(ruidoSeleccionado)){
                        instrucciones1.setText("valor sigma");
                        cambiarOpcional1(true);
                        cambiarOpcional2(false);
                    }else{
                        slider1.setMaximum(200);
                        slider1.setMinimum(0);
                        slider1.setMajorTickSpacing(50);
                        slider2.setMaximum(200);
                        slider2.setMinimum(0);
                        slider2.setMajorTickSpacing(50);
                        cambiarOpcional1(true);
                        cambiarOpcional2(false);
                    }
                }
            }
        });
        slider1.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                texto1.setText(String.valueOf(slider1.getValue())); 
                texto2.setText(String.valueOf(slider2.getValue())); 
                aplicarRuido();
            }  
        });
        slider2.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                texto1.setText(String.valueOf(slider1.getValue())); 
                texto2.setText(String.valueOf(slider2.getValue())); 
                aplicarRuido();
            }  
        });
     }

    public void aplicarRuido() {
        String eleccion = comboEleccion.getSelectedItem().toString();
        Image nuevaImagenRuido=null;
        System.out.println(eleccion);
        int varianza;
        int media;
        int sigma;
        switch(eleccion){
            case "Ruido Coherente":
                int xo= Integer.parseInt(texto1.getText());
                int yo= Integer.parseInt(texto2.getText());
                nuevaImagenRuido = ruido.aplicarRuidoCoherente(xo,yo);
                break;
            case "Ruido Gamma":
                System.out.println("ruidosdifjao");
                nuevaImagenRuido =ruido.aplicarRuidoGamma();
                break;
            case"Ruido Gaussiano":
                varianza= Integer.parseInt(texto1.getText());
                media= Integer.parseInt(texto2.getText());
                nuevaImagenRuido =ruido.aplicarRuidoGaussiano(media,varianza);
                break;
            case"Ruido ExponencialNegativo":
                varianza =  Integer.parseInt(texto1.getText());
                nuevaImagenRuido =ruido.aplicarRuidoExponencialNegativo(varianza);
                break;
            case"Ruido Rayleigh":
                sigma= Integer.parseInt(texto1.getText());
                nuevaImagenRuido =ruido.aplicarRuidoRayleigh(sigma);
                break;
            case"Ruido Sal y pimienta":
                nuevaImagenRuido =ruido.aplicarRuidoSalyPimienta();
                break;
            case "Ruido Uniforme":
                int min= Integer.parseInt(texto1.getText());
                int max=Integer.parseInt(texto2.getText());
                if(min>=max){
                    System.out.println("debe ser mayor max");
                    return ;
                }
                nuevaImagenRuido =ruido.aplicarRuidoUniforme(min,max);
                break;
            case "Ruido SOLO SAL":
                   nuevaImagenRuido=ruido.aplicarRuidoSal();
                   break;
            case "Ruido SOLO PIMIENTA":
                   nuevaImagenRuido=ruido.aplicarRuidoPimienta();
                   break;   
              
        }
        panelImagenRuido.setImagen(nuevaImagenRuido);
        
    }
    private void cambiarOpcional1(boolean boleano){
        slider1.setVisible(boleano);
        instrucciones1.setVisible(boleano);
        texto1.setVisible(boleano);
    }
    private void cambiarOpcional2(boolean boleano){
        slider2.setVisible(boleano);
        instrucciones2.setVisible(boleano);
        texto2.setVisible(boleano);
    }
    public void setImagen(Image imagen){
        this.imagen=imagen;
        buffered=imageBuffered.getBufferedImageColor(imagen);
        imagenGrises=imageBuffered.getImage(buffered, 5);
        panelImagen.setImagen(imagenGrises);
        panelImagenRuido.setImagen(imagen);
        ruido.setImagen(imagen);
        aplicarRuido();
    }
}
