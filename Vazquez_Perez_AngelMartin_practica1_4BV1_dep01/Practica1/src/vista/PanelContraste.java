package vista;

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
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PanelContraste extends JPanel {
    private JSlider contraste;
    private BufferedImage buffered;
    private PanelImagen panelImagen;  
    public ImageBufferedImage imagen;

    public PanelContraste(BufferedImage buffered) {
        this.buffered = buffered;
  
        initcomponents();
    }

    public void initcomponents() {
        imagen = new ImageBufferedImage();
        JPanel panelTop = new JPanel(new GridLayout(2,1));
        this.contraste = new JSlider(0, 200, 0);
        this.contraste.setMajorTickSpacing(50);
        this.contraste.setPaintTicks(true);
        this.contraste.setPaintLabels(true);

        this.setLayout(new BorderLayout());
        panelTop.add(new Label("Modificar Contraste"));
        panelTop.add(contraste);
        add(panelTop,BorderLayout.NORTH);
        panelImagen = new PanelImagen(imagen.getImage(buffered, 5));
        add(panelImagen,BorderLayout.CENTER);
        JButton botonModificar = new JButton("Generar Frame");
        botonModificar.addActionListener(new modificarContraste());
        add(botonModificar,BorderLayout.SOUTH);
        
        contraste.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                double numero = contraste.getValue();
                numero= numero /100;
                Image nuevaImagen =  imagen.modificarContraste(buffered, numero);
                panelImagen.setImagen(nuevaImagen);
            }
        });

    }

    private class modificarContraste implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double numero = contraste.getValue();
                numero= numero /100;
                FrameImagen frame = new FrameImagen(imagen.modificarContraste(buffered, numero));
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
