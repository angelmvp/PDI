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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import modelo.Convolucion;
import modelo.FiltrosNoLineales;
import modelo.ImageBufferedImage;

/**
 *
 * @author jhona
 */
public class PanelFiltroNoLineal extends JPanel {
    private Image imagen;
    private Image imagenFiltrada;
    private ImageBufferedImage imageBuffered;
    private PanelImagenHistograma panelImagen;
    private PanelImagenHistograma panelImagenFiltrada;
    private JComboBox<String> comboFiltro;
    private JButton botonAplicar;
    private int [][] imagenInt;
    private BufferedImage buffered;
    private FiltrosNoLineales filtrosNoLineales;
    private JTextField textoP;
    private Label instruccionesP;
    private JPanel panelOpcional;
    public PanelFiltroNoLineal(Image imagen){
        this.imagen=imagen;
        this.imagenFiltrada=imagen;
        initComponents();
    }
    public void initComponents(){
        imageBuffered= new ImageBufferedImage();
        buffered= imageBuffered.getBufferedImageColor(imagen);
        Image imagenGrises=imageBuffered.getImage(buffered, 5);
        this.setLayout(new BorderLayout());
        botonAplicar = new JButton("Aplicar");
        panelImagen= new PanelImagenHistograma(imagenGrises);
        panelImagenFiltrada= new PanelImagenHistograma(imagenFiltrada);
        imagenInt = imageBuffered.getMatrizImagen(buffered, 5);
        filtrosNoLineales = new FiltrosNoLineales(imagenInt,3);
        comboFiltro = new JComboBox<>();
        comboFiltro.addItem("Filtro de Mediana");
        comboFiltro.addItem("Filtro de Maximo");
        comboFiltro.addItem("Filtro de Minimo");
        comboFiltro.addItem("Filtro de punto medio");
        comboFiltro.addItem("Filtro inferior armonico");
        comboFiltro.addItem("Filtro inferior contra armonico");
        comboFiltro.addItem("Filtro inferior geometrico");
        comboFiltro.addItem("Filtro maximo-minimo");
        comboFiltro.addItem("Filtro de la media aritmetica");
        comboFiltro.addItem("Filtro de Alfa Trimmed");
        JPanel panelTop= new JPanel(new GridLayout(1,4));
            panelTop.add(new JLabel("Panel De filtro No lineales "));
            panelTop.add(comboFiltro);
            botonAplicar = new JButton("aplicar Filtro");
            botonAplicar.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    aplicarFiltro();
                }
            });
            panelTop.add(botonAplicar);
        textoP = new JTextField();
        textoP.setVisible(false);
        textoP.setText("1");
        instruccionesP = new Label("Introduzca el valor de P");
        instruccionesP.setVisible(false);
        panelOpcional = new JPanel(new GridLayout(1, 2));
        panelOpcional.add(instruccionesP);
        panelOpcional.add(textoP);
        panelOpcional.setVisible(false);

        comboFiltro.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String filtroSeleccionado = (String) comboFiltro.getSelectedItem();
                    if ("Filtro de Alfa Trimmed".equals(filtroSeleccionado) ||
                            "Filtro inferior contra armonico".equals(filtroSeleccionado) ) {
                        panelOpcional.setVisible(true);
                        instruccionesP.setVisible(true);
                        textoP.setVisible(true);
                    } else {
                        panelOpcional.setVisible(false);
                        instruccionesP.setVisible(false);
                        textoP.setVisible(false);
                    }
                }
            }
        });

        panelTop.add(panelOpcional);
        JPanel panelCentro= new JPanel(new GridLayout(1,2));
            panelCentro.add(panelImagen);
            panelCentro.add(panelImagenFiltrada);
        this.add(panelTop,BorderLayout.NORTH);
        this.add(panelCentro,BorderLayout.CENTER);
        
    }
    public void aplicarFiltro(){
        String filtroElegido = (String) comboFiltro.getSelectedItem();
        Image nuevaImagen = null;
        switch (filtroElegido) {
            case "Filtro de Alfa Trimmed":
                System.out.println("alfa trimmed");
                int valorP = Integer.parseInt(textoP.getText());
                nuevaImagen=filtrosNoLineales.aplicarFiltrodeAlfaTrimmed(valorP);
                break;
            case "Filtro de Mediana":
                System.out.println("mediana");
                nuevaImagen = filtrosNoLineales.aplicarFiltroMediana();
                break;
            case "Filtro de Maximo":
                System.out.println("maximo");
                nuevaImagen = filtrosNoLineales.aplicarFiltroMaximo();
                break;
            case "Filtro de Minimo":
                System.out.println("minimo");
                nuevaImagen = filtrosNoLineales.aplicarFiltroMinimo();
                break;
            case "Filtro de punto medio":
                System.out.println("medio");
                nuevaImagen = filtrosNoLineales.aplicarFiltroPuntoMedio();
                break;
            case "Filtro inferior armonico":
                System.out.println("inferior armonico");
                nuevaImagen = filtrosNoLineales.aplicarFiltroInferiorArmonico();
                break;
            case "Filtro inferior contra armonico": 
                System.out.println("inferior contra armonico");
                double valorPdouble= Double.parseDouble(textoP.getText());
                nuevaImagen = filtrosNoLineales.aplicarFiltroInferiorContraArmonico(valorPdouble);
                break;
            case "Filtro inferior geometrico":
                System.out.println("geometrico");
                nuevaImagen = filtrosNoLineales.aplicarFiltroInferiorGeometrico();
                break;
            case "Filtro maximo-minimo":
                nuevaImagen = filtrosNoLineales.aplicarFiltroMaximoMinimo();
                break;
            case "Filtro de la media aritmetica":
                nuevaImagen = filtrosNoLineales.aplicarFiltroMediaAritmetica();
                break;
        }
        panelImagenFiltrada.setImagen(nuevaImagen);
    }

    public void setImagen(Image imagen){
        this.imagen=imagen;
        buffered= imageBuffered.getBufferedImageColor(imagen);
        Image imagenGrises=imageBuffered.getImage(buffered, 5);
        this.setLayout(new BorderLayout());
        panelImagen.setImagen(imagen);
        panelImagenFiltrada.setImagen(imagen);
        imagenInt = imageBuffered.getMatrizImagen(buffered, 5);
        filtrosNoLineales.setMatrizImagen(imagenInt);
        aplicarFiltro();
    }
}
