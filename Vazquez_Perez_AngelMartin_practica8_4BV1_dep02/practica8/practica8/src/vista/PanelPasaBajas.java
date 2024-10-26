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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
    private JSlider sliderAlpha;
    private double bias;
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
        botonAplicar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                convolucionarImagen(imageBuffered.getMatrizImagen(buffered,5));
                alpha.setText( "Bias: " + String.format("%.2f", obtenerBias()));
            }
        });
        panelTop.add(botonAplicar);
        sliderAlpha = new JSlider(0,50,1);
        sliderAlpha.setMajorTickSpacing(50);
        sliderAlpha.setPaintTicks(true);
        sliderAlpha.setPaintLabels(true);
        JPanel panelDerecha= new JPanel(new GridLayout(2,1));
                panelMascarasBajas = new PanelMascarasBajas();
                panelDerecha.add(panelMascarasBajas);
                JPanel panelDerechaBottom= new JPanel(new GridLayout(4,2));
                    alpha= new JTextField();
                    alpha.setText( "Bias: " + String.format("%.2f", obtenerBias()));
                    //panelDerechaBottom.add(new JLabel("introduzca el valor de alpha para la intensidad de filtrado "));
                    //panelDerechaBottom.add(sliderAlpha);
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
        valorAlpha= sliderAlpha.getValue();
        if(valorAlpha>0){
           panelMascarasBajas.setAlpha(valorAlpha);
        }

        setImagenFiltrada(convolucion(panelMascarasBajas.getMascara(),imagenInt));
        //System.out.println(panelMascarasBajas.getMascara()[2][2]);
    }
    
    public Image convolucion(double [][] mascara,int[][] matrizInt){
        selector=1;
        double bias = obtenerBias();
        convolucion.setBias(bias);
        alpha.setText( "Bias: " + String.format("%.2f", obtenerBias()));
        Image nuevaImagen =convolucion.getImageConvolucionInt(mascaradobuletoint(mascara), 
                panelMascarasBajas.getTam(), matrizInt, selector);
        return nuevaImagen;
    }
    public int[][] mascaradobuletoint(double[][] mascara){
        int[][] nuevaMascara= new int[mascara.length][mascara[0].length];
        for(int i=0; i<mascara.length;i++){
            for(int j=0;j<mascara[0].length;j++){
                nuevaMascara[i][j] = (int)mascara[i][j];
            }
        }
        return nuevaMascara;
    }
    public double obtenerBias(){
        double bias = 1/panelMascarasBajas.getBias();
        return bias;
    }
    public void setImagenFiltrada(Image nuevaImagen){
        imagenFiltrada=nuevaImagen;
        panelImagenFiltrada.setImagen(nuevaImagen);
    }
    public void setImagen(Image imagen){
        this.imagen=imagen;
        buffered=imageBuffered.getBufferedImage(imagen);
        imagenGrises=imageBuffered.getImage(buffered, 5);
        panelImagen.setImagen(imagenGrises);
        panelImagenFiltrada.setImagen(imagen);
    }
    public Image getImagenFiltrada(){
        return imagenFiltrada;
    }
}
