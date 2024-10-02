package vista;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class PanelHistograma extends JPanel{
    private double[] datosDouble;
    private int[] datosInt;
    private Color color;
    
    public PanelHistograma(int[] datosInt, Color color){
        this.datosInt = datosInt;
        this.color = color;
        this.datosDouble = null;
        repaint();
        initcomponents();
    }
    
    public PanelHistograma(double[] datosDouble, Color color){
        this.datosDouble = datosDouble;
        this.color = color;
        this.datosInt = null; 
        repaint();
        initcomponents();
    }
    public void initcomponents(){
        this.setSize(300,300);
    }
    public PanelHistograma(PanelHistograma panel) {
        if (panel.datosDouble != null) {
            this.datosDouble = panel.datosDouble.clone();
        }
        if (panel.datosInt != null) {
            this.datosInt = panel.datosInt.clone(); 
        }
        this.color = panel.color;
        panel.repaint();
    }
        @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int padding = 60; 
        int labelPadding = 40; 
        int height = getHeight();
        int width;
        double maxVal=0;

        if (datosInt != null) {
            width = getWidth() / datosInt.length;
            maxVal = getMax(datosInt);

            g.drawLine(padding, height - padding, padding, padding); // Eje Y
            g.drawLine(padding, height - padding, getWidth() - padding, height - padding); // Eje X

            
            g.setColor(color);
            for (int i = 0; i < datosInt.length; i++) {
                int barHeight = (int) ((datosInt[i] / maxVal) * (height - 2 * padding));
                g.fillRect(padding + (i * width), height - padding - barHeight, width, barHeight);
            }

        } else if (datosDouble != null) {
            width = getWidth() / datosDouble.length;
            maxVal = getMax(datosDouble);

            g.drawLine(padding, height - padding, padding, padding); // Eje Y
            g.drawLine(padding, height - padding, getWidth() - padding, height - padding); // Eje X
            g.setColor(color);
            for (int i = 0; i < datosDouble.length; i++) {
                int barHeight = (int) ((datosDouble[i] / maxVal) * (height - 2 * padding));
                g.fillRect(padding + (i * width), height - padding - barHeight, width, barHeight);
            }
        }

        // Etiquetas del eje Y (Frecuencia)
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        int numberYDivisions = 6;
        for (int i = 0; i <= numberYDivisions; i++) {
            int yPos = height - padding - (i * (height - 2 * padding) / numberYDivisions);
            double yValue = maxVal * i / numberYDivisions;
            g.drawLine(padding, yPos, padding , yPos); 
            g.drawString(String.format("%.1f", yValue), padding - labelPadding, yPos + 5); // Etiquetas del eje Y
        }
    }

    private double getMax(int[] data) {
        int max = 0;
        for (int value : data) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
    private double getMax(double[] data) {
        double max = 0;
        for (double value : data) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public void setDatos(double[] nuevosDatos, Color color){
        this.datosDouble = nuevosDatos;
        this.datosInt = null;
        this.color = color;
        this.repaint();
    }

    public void setDatos(int[] nuevosDatos, Color color){
        this.datosInt = nuevosDatos;
        this.datosDouble = null;
        this.color = color;
        this.repaint();
    }
    public String getColor(){
        if(color==null){
            color=Color.GRAY;
        }
        return color.toString() ;
    }
}
