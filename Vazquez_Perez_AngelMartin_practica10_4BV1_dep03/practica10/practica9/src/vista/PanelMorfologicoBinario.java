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
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import modelo.ES;
import modelo.ImageBufferedImage;
import modelo.MorfologicaBinaria;
import modelo.Ruido;

/**
 *
 * @author jhona
 */
public class PanelMorfologicoBinario extends JPanel{
    private Image imagen;
    private Image imagenGrises;
    private Image imagenModificada;
    private int [][] imagenInt;
    private BufferedImage buffered;
    private ImageBufferedImage imageBuffered;
    private PanelImagenHistograma panelImagen;
    private PanelImagenHistograma panelImagenModificada;
    private MorfologicaBinaria morfologicaBinaria;
    private JComboBox<String> comboEleccion;
    private JComboBox<String> comboTamES;
    private JComboBox<String> comboTipoES;
    private JButton botonAplicar;
    private JPanel panelOpcional;
    private ES es;
    public PanelMorfologicoBinario(Image imagen){
        this.imagen=imagen;
        this.imagenModificada=imagen;
        initComponents();
    }
    public void initComponents(){
        imageBuffered= new ImageBufferedImage();
        buffered= imageBuffered.getBufferedImageColor(imagen);
        imagenGrises=imageBuffered.getImage(buffered, 5);
        this.setLayout(new BorderLayout());
        panelImagen= new PanelImagenHistograma(imagenGrises);
        panelImagenModificada= new PanelImagenHistograma(imagenModificada);
        morfologicaBinaria = new MorfologicaBinaria(imagen);
        imagenInt = imageBuffered.getMatrizImagen(buffered,5);
        es= new ES(imagenInt,3);
        JPanel panelTop= new JPanel(new GridLayout(1,5));
            comboEleccion = new JComboBox<>();
            comboEleccion.addItem("Erosion");
            comboEleccion.addItem("Dilatacion");
            comboEleccion.addItem("Apertura");
            comboEleccion.addItem("Clausura");
            panelTop.add(new Label("Escoja el tipo de operacion Morfologica a realizar"));
            panelTop.add(comboEleccion);
        comboTamES = new JComboBox<>();
        comboTamES.addItem("3x3");
        comboTamES.addItem("5x5");
        comboTamES.addItem("7x7");
        comboTamES.addItem("9x9");
        comboTamES.addItem("11x11");
        comboTipoES = new JComboBox<>();
        comboTipoES.addItem("cuadrada");
        comboTipoES.addItem("horizontal");
        comboTipoES.addItem("vertical");
        comboTipoES.addItem("diamante");
        comboTipoES.addItem("cruz");
        comboTipoES.addItem("equis");
        panelTop.add(comboTipoES);
        panelTop.add(comboTamES);
        JPanel panelCentro= new JPanel(new GridLayout(1,2));
            panelCentro.add(panelImagen);
            panelCentro.add(panelImagenModificada);
        this.add(panelTop,BorderLayout.NORTH);
        this.add(panelCentro,BorderLayout.CENTER);
        botonAplicar = new JButton("Aplicar");
        panelTop.add(botonAplicar);
        botonAplicar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aplicarMorfologia();
            }
        });
        
           }
    public void aplicarMorfologia(){
        String tipo = (String)comboEleccion.getSelectedItem();
        String tipoES = obtenerTipoES();
        int tamES=obtenerTamES();
        es.setTamMascara(tamES);
        es.setTipoMascara(tipoES);
        Image nuevaImagen=null;
        switch(tipo){
                case "Erosion":
                    nuevaImagen = es.aplicarErosion();
                    break;
                case "Dilatacion":
                    nuevaImagen = es.aplicarDilatacion();
                    break;
                case "Apertura":
                    nuevaImagen = es.aplicarApertura();
                    break;
                case "Clausura":
                    nuevaImagen = es.aplicarClausura();
                    break;
        }
        this.panelImagenModificada.setImagen(nuevaImagen);
    }
    public String  obtenerTipoES(){
        String tipo = (String)comboTipoES.getSelectedItem();
             return tipo;       
    }
    public int obtenerTamES(){
        String tam = (String)comboTamES.getSelectedItem();
        switch (tam){
            case "3x3":
                    return 3;
            case "5x5":
                    return 5;
            case "7x7":
                    return 7;
            case "9x9":
                    return 9;
            case "11x11":
                    return 11;
        }
        return 1;
    }
    public void setImagen(Image imagen){
        this.imagen=imagen;
        buffered=imageBuffered.getBufferedImageColor(imagen);
        imagenGrises=imageBuffered.getImage(buffered, 5);
        panelImagen.setImagen(imagenGrises);
        panelImagenModificada.setImagen(imagen);
        imagenInt = imageBuffered.getMatrizImagen(buffered, 5);
        es.setMatrizImagen(imagenInt);
        aplicarMorfologia();
    }
}
