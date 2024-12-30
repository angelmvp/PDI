package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class PanelImagen extends JPanel {
    private CanvasImagen canvas;
    private JButton botonGuardarImagen;
    private JButton botonGenerarFrame;
    private Image imagen;
    public PanelImagen(Image imagen) {
        this.imagen=imagen;
        initComponents(imagen);
    }

    private void initComponents(Image imagen) {
        // pa guardar la imagen
        botonGuardarImagen = new JButton("Guardar");
        botonGuardarImagen.setPreferredSize(new Dimension(100, 25));  // Tamaño pequeño
        botonGuardarImagen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarImagen();
            }
        });
        //pa crear un nuevo Frame
        botonGenerarFrame = new JButton("Procesar Imagen");
        botonGenerarFrame.setPreferredSize(new Dimension(100, 25));  // Tamaño pequeño
        botonGenerarFrame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarFrame();
            }
        });
        // Configuración del canvas
        canvas = new CanvasImagen(imagen);
        this.setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1,2,0,0));
        panelBotones.add(botonGuardarImagen);
        panelBotones.add(botonGenerarFrame);
        this.add(panelBotones,BorderLayout.SOUTH);

        this.setSize(imagen.getWidth(null), imagen.getHeight(null));
    }

    public void setImagen(Image imagen) {
        canvas.setImagen(imagen);
        this.imagen=imagen;
    }

    private void guardarImagen() {
        // Abrir el diálogo para elegir la ubicación y el nombre del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Imagen");
        
        // Filtrar solo archivos de imagen
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PNG Image", "png"));
        
        int userSelection = fileChooser.showSaveDialog(this);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            // Agregar extensión ".png" si no se especifica
            if (!fileToSave.getAbsolutePath().endsWith(".png")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".png");
            }
            try {
                ImageIO.write(canvas.getBufferedImage(), "png", fileToSave);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    private void generarFrame(){
        new FrameAllIn(this.imagen);
    }
}
