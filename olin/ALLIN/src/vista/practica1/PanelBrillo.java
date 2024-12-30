package vista.practica1;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import modelo.ImageBufferedImage;
import vista.FrameImagen;
import vista.PanelImagen;

public class PanelBrillo extends JPanel {
    private JSlider brillo;
    private BufferedImage buffered;
    private PanelImagen panelImagen;  
    public ImageBufferedImage imagen;

    public PanelBrillo(BufferedImage buffered) {
        this.buffered=buffered;
        initcomponents();
    }

    public void initcomponents() {
        imagen = new ImageBufferedImage();
        JPanel panelTop = new JPanel(new GridLayout(2,1));
        this.brillo = new JSlider(-255, 255, 0);
        this.brillo.setMajorTickSpacing(50);
        this.brillo.setPaintTicks(true);
        this.brillo.setPaintLabels(true);

        this.setLayout(new BorderLayout());
        panelTop.add(new Label("Modificar Brillo"));
        panelTop.add(brillo);
        add(panelTop,BorderLayout.NORTH);
        panelImagen = new PanelImagen(imagen.getImage(buffered, 5));
        add(panelImagen,BorderLayout.CENTER);
        JButton botonModificar = new JButton("Generar Frame");
        botonModificar.addActionListener(new modificarBrillo());
        add(botonModificar,BorderLayout.SOUTH);
        
        brillo.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int numero = brillo.getValue();
                Image nuevaImagen =  imagen.modificarBrillo(buffered, numero);
                panelImagen.setImagen(nuevaImagen);
            }
        });

    }

    private class modificarBrillo implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int numero = brillo.getValue();
                FrameImagen frame = new FrameImagen(imagen.modificarBrillo(buffered, numero));
            } catch (NumberFormatException ex) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
    }
     public void setImagen(BufferedImage img){
        this.buffered=img;
        panelImagen.setImagen(imagen.getImage(img,4));
    }
}
