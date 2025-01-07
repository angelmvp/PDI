/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author Saul
 */
public class CanvasImagen extends Canvas {
    private Image imagen;
    public CanvasImagen(Image imagen) {
        this.imagen = imagen;
    }
    public void setImagen(Image imagen) {
        this.imagen = imagen;
        repaint();
    }
    @Override
    public void paint(Graphics g) {

        if (imagen != null) {
            // Obtiene el tamaño del panel
            int panelWidth = this.getWidth();
            int panelHeight = this.getHeight();
            
            // Obtiene las dimensiones originales de la imagen
            int imageWidth = imagen.getWidth(this);
            int imageHeight = imagen.getHeight(this);
            
            // Calcula las nuevas dimensiones manteniendo la relación de aspecto
            float aspectRatio = (float) imageWidth / imageHeight;
            int newWidth = panelWidth;
            int newHeight = (int) (panelWidth / aspectRatio);
            
            // Si la nueva altura excede el tamaño del panel, ajusta por la altura
            if (newHeight > panelHeight) {
                newHeight = panelHeight;
                newWidth = (int) (panelHeight * aspectRatio);
            }
            
            // Dibuja la imagen redimensionada y centrada
            int x = (panelWidth - newWidth) / 2;
            int y = (panelHeight - newHeight) / 2;
            g.drawImage(imagen.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), x, y, this);
        }
    }
    public BufferedImage getBufferedImage() {
        if (imagen instanceof BufferedImage) {
            return (BufferedImage) imagen;
        } else {
            // Convertir a BufferedImage si la imagen no es ya un BufferedImage
            BufferedImage bufferedImage = new BufferedImage(imagen.getWidth(null), imagen.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics g = bufferedImage.getGraphics();
            g.drawImage(imagen, 0, 0, null);
            g.dispose();
            return bufferedImage;
        
    }
    }
}