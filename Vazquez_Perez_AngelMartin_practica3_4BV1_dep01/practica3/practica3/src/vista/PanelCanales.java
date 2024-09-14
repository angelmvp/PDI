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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import modelo.ImageBufferedImage;

/**
 *
 * @author jhona
 */
public class PanelCanales extends JPanel {
    private JComboBox<String> c;
    private JCheckBox checkBox;
    private JButton botonSubmit;
    private BufferedImage buffered;
    private PanelImagen panelImagen;  
    public ImageBufferedImage imagen;
    private Image img;

    public PanelCanales(BufferedImage buffered) {
        this.buffered = buffered;
        initcomponents();
    }

    private void initcomponents() {
        imagen = new ImageBufferedImage();
        JPanel panelTop = new JPanel(new GridLayout(2, 2));
        this.setLayout(new BorderLayout());
        panelTop.add(new Label("Modificar por Canal"));
        c = new JComboBox<>();
        c.addItem("Seleccione un canal");
        c.addItem("Canal Rojo");
        c.addItem("Canal Verde");
        c.addItem("Canal Azul");
        c.addItem("Todos los canales");
        c.addItem("Escala de grises");
        checkBox = new JCheckBox("poner a escala de grises");
        panelTop.add(c);
        panelTop.add(checkBox);
        
        JButton botonSubmit = new JButton("Generar Frame");
        botonSubmit.addActionListener(new generarFrame());
        img=imagen.getImage(buffered,5);
        panelImagen = new PanelImagen(img);
        add(panelImagen, BorderLayout.CENTER);
        add(panelTop, BorderLayout.NORTH);
        add(botonSubmit, BorderLayout.SOUTH);
        
        c.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                actualizarImagenGrises();
            }
        });
        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarImagenGrises();
            }
        });
    }

    private int obtenerValor(String opcion) {
        switch (opcion) {
            case "Canal Rojo":
                return 1;
            case "Canal Verde":
                return 2;
            case "Canal Azul":
                return 3;
            case "Todos los canales":
                return 4;
            case "Escala de grises":
                return 5;
            default:
                return 5;
        }
    }

    private class generarFrame implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String opcion = (String) c.getSelectedItem();
            Image img;
            int canal = obtenerValor(opcion);
            img = imagen.getImage(buffered, canal);
            FrameImagen frame = new FrameImagen(img);
        }
    }
    public void actualizarImagenColor(){
        String opcion = (String) c.getSelectedItem();
        int canal = obtenerValor(opcion);
        img = imagen.getImage(buffered, canal);

        panelImagen.setImagen(img);
    }
    public void actualizarImagenGrises() {
    }
     public void setImagen(BufferedImage img){
        this.buffered=img;
        panelImagen.setImagen(imagen.getImage(img,4));
    }
}
