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
import modelo.Aritmeticas;
import modelo.Canny;
import modelo.Convolucion;
import modelo.ImageBufferedImage;

/**
 *
 * @author jhona
 */
public class PanelPasaAltas extends JPanel {
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
    private PanelMascarasAltas panelMascarasAltas;
    private PanelMascarasBajas panelMascarasBajas;
    private Convolucion convolucion;
    private Image imagenColumnas;
    private Image imagenFilas;
    private  int [][] imagenInt;
    private Aritmeticas aritmeticas;
    private int selector;
    private Canny canny;
    public PanelPasaAltas(Image imagen){
        this.imagen=imagen;
        this.imagenFiltrada=imagen;
        initComponents();
    }
    public void initComponents(){
        imageBuffered= new ImageBufferedImage();
        convolucion = new Convolucion(1);
        buffered= imageBuffered.getBufferedImageColor(imagen);
        imagenGrises=imageBuffered.getImage(buffered, 5);
        imagenInt= imageBuffered.getMatrizImagen(buffered, 5);
        this.setLayout(new BorderLayout());
        canny= new Canny();
        panelImagen= new PanelImagenHistograma(imagenGrises);
        panelImagenFiltrada= new PanelImagenHistograma(imagenFiltrada);
        comboPasaAltas= new JComboBox<>();
        comboPasaAltas.addItem("Mascaras");
        comboPasaAltas.addItem("Canny");
        
        JPanel panelTop= new JPanel(new GridLayout(1,4));
        panelTop.add(new JLabel("Panel Pasa Altas"));
        panelTop.add(comboPasaAltas);
        botonAplicar = new JButton("aplicar Filtro");
        botonAplicar.addActionListener(e->convolucionarImagen(imageBuffered.getMatrizImagen(buffered,5)));
        panelTop.add(botonAplicar);
        JPanel panelDerecha= new JPanel(new GridLayout(2,1));
                panelMascarasAltas = new PanelMascarasAltas();
                panelDerecha.add(panelMascarasAltas);
        JPanel panelCentro= new JPanel(new GridLayout(1,3));
            panelCentro.add(panelImagen);
            panelCentro.add(panelImagenFiltrada);
            panelCentro.add(panelDerecha);
        this.add(panelTop,BorderLayout.NORTH);
        this.add(panelCentro,BorderLayout.CENTER);

    }
    public void convolucionarImagen(int [][] imagenInt){
        String tipo = (String)comboPasaAltas.getSelectedItem();
        switch (tipo){
                case "Mascaras":
                    convolucionMascarasFilasColumnas(imagenInt);
                    break;
                case "Canny":
                    algoritmoCanny(imagenInt);
        }
    }
    public void convolucionMascarasFilasColumnas(int [][] imagenInt){
        System.out.println(imagenInt[100][100]);
        // Aplicar las convoluciones sin modificar la imagen original
        Image imagenFilas = convolucion(panelMascarasAltas.getMascaraFilas(),imagenInt);
        Image imagenColumnas = convolucion(panelMascarasAltas.getMascaraColumnas(),imagenInt);

        aritmeticas = new Aritmeticas(imagenFilas, imagenColumnas);
        Image nuevaImagen = aritmeticas.SumaDeImagenes();
        setImagenFiltrada(nuevaImagen);
    }
    
    public Image convolucion(double [][] mascara,int[][] matrizInt){
        selector=2;
        String nombre=panelMascarasAltas.getNombreMascara();
        if(nombre!="Frei-Chen"){selector=1;}
        Image nuevaImagen =convolucion.getImageConvolucion(mascara, 
                panelMascarasAltas.getTam(), matrizInt, selector);
        return nuevaImagen;
    }
    public void algoritmoCanny(int [][] imagenInt){
        System.out.println("ldkajfldaksj");
        Image nuevaImagen= canny.getImageCanny(imagenInt);
        setImagenFiltrada(nuevaImagen);
        
    }
    public void setImagenFiltrada(Image nuevaImagen){
        imagenFiltrada=nuevaImagen;
        panelImagenFiltrada.setImagen(nuevaImagen);
    }
    public void setImagen(Image imagen){
        this.imagen=imagen;
        buffered=imageBuffered.getBufferedImageColor(imagen);
        imagenGrises=imageBuffered.getImage(buffered, 5);
        panelImagen.setImagen(imagenGrises);
        panelImagenFiltrada.setImagen(imagen);
    }
    public Image getImagenFiltrada(){
        return imagenFiltrada;
    }
}
