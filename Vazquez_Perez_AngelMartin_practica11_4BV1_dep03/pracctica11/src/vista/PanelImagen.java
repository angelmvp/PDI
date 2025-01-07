package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

    public PanelImagen(Image imagen) {
        initComponents(imagen);
    }

    private void initComponents(Image imagen) {
        // Configuración del botón para guardar imagen
        botonGuardarImagen = new JButton("Guardar");
        botonGuardarImagen.setPreferredSize(new Dimension(100, 25));  // Tamaño pequeño
        botonGuardarImagen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarImagen();
            }
        });

        // Configuración del canvas
        canvas = new CanvasImagen(imagen);
        this.setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);
        add(botonGuardarImagen, BorderLayout.SOUTH);

        this.setSize(imagen.getWidth(null), imagen.getHeight(null));
    }

    public void setImagen(Image imagen) {
        canvas.setImagen(imagen);
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
}
