/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import modelo.Aritmeticas;
import modelo.Convolucion;
import modelo.ImageBufferedImage;

/**
 *
 * @author jhona
 */
public class PanelPasaBajas extends JPanel {
    private Image imagen;
    private Image imagenGrises;
    private Image imagenFiltrada;
    private BufferedImage buffered;
    private ImageBufferedImage imageBuffered;
    private PanelImagenHistograma panelImagen;
    private PanelImagenHistograma panelImagenFiltrada;
    private JComboBox<String> comboPasaAltas;
    private JCheckBox checkNegativo;
    private JButton botonAplicar;
    private PanelMascarasBajas panelMascarasBajas;
    private Convolucion convolucion;
    private Image imagenColumnas;
    private Image imagenFilas;
    private  int [][] imagenInt;
    private Aritmeticas aritmeticas;
    private int selector;
    private JTextField alpha;
    private int valorAlpha;
    public PanelPasaBajas(Image imagen){
        this.imagen=imagen;
        this.imagenFiltrada=imagen;
        initComponents();
    }
    public void initComponents(){
        valorAlpha=1;
        imageBuffered= new ImageBufferedImage();
        convolucion = new Convolucion(1);
        buffered= imageBuffered.getBufferedImageColor(imagen);
        imagenGrises=imageBuffered.getImage(buffered, 5);
        imagenInt= imageBuffered.getMatrizImagen(buffered, 5);
        this.setLayout(new BorderLayout());
        panelImagen= new PanelImagenHistograma(imagenGrises);
        panelImagenFiltrada= new PanelImagenHistograma(imagenFiltrada);
        
        JPanel panelTop= new JPanel(new GridLayout(1,4));
        panelTop.add(new JLabel("Panel Pasa Bajas"));
        botonAplicar = new JButton("aplicar Filtro");
        botonAplicar.addActionListener(e->convolucionarImagen(imageBuffered.getMatrizImagen(buffered,5)));
        panelTop.add(botonAplicar);
        JPanel panelDerecha= new JPanel(new GridLayout(2,1));
                panelMascarasBajas = new PanelMascarasBajas();
                panelDerecha.add(panelMascarasBajas);
                JPanel panelDerechaBottom= new JPanel(new GridLayout(4,2));
                    alpha= new JTextField();
                    alpha.setText("1");
                    panelDerechaBottom.add(new JLabel("introduzca el valor de alpha para la intensidad de filtrado "));
                    panelDerechaBottom.add(alpha);
                    
                panelDerecha.add(panelDerechaBottom);
        JPanel panelCentro= new JPanel(new GridLayout(1,3));
            panelCentro.add(panelImagen);
            panelCentro.add(panelImagenFiltrada);
            panelCentro.add(panelDerecha);
        this.add(panelTop,BorderLayout.NORTH);
        this.add(panelCentro,BorderLayout.CENTER);

        

    }
    public void convolucionarImagen(int [][] imagenInt){
        valorAlpha= Integer.parseInt(alpha.getText());
        System.out.println(valorAlpha);
        if(valorAlpha>0){
           panelMascarasBajas.setAlpha(valorAlpha);
        }

        setImagenFiltrada(convolucion(panelMascarasBajas.getMascara(),imagenInt));
    }
    
    public Image convolucion(double [][] mascara,int[][] matrizInt){
        selector=2;
        //String nombre=panelMascaras.getNombreMascara();
        //if(nombre!="Frei-Chen"){selector=1;}
        //System.out.println(imagenInt[100][100]);
        Image nuevaImagen =convolucion.getImageConvolucion(mascara, 
                panelMascarasBajas.getTam(), matrizInt, 1);
        return nuevaImagen;
    }
    public void setImagenFiltrada(Image nuevaImagen){
        panelImagenFiltrada.setImagen(nuevaImagen);
    }
    public void setImagen(Image imagen){
        this.imagen=imagen;
        buffered=imageBuffered.getBufferedImage(imagen);
        imagenGrises=imageBuffered.getImage(buffered, 5);
        panelImagen.setImagen(imagenGrises);
        panelImagenFiltrada.setImagen(imagen);
    }
}
