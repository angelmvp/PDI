package vista;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;

public class PanelHistograma extends JPanel{
    private double[] datosDouble;
    private int[] datosInt;
    private Color color;
    private int longitud;
    
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
        this.setSize(255,255);
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
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        if (datosDouble != null) {
            longitud = datosDouble.length;
            int anchoBarra = Math.max(1, width / longitud);
            g2d.setColor(color);
            for (int i = 0; i < longitud; i++) {
                int alturaBarra = (int) (datosDouble[i] * height);
                g2d.fillRect(i * anchoBarra, height - alturaBarra, anchoBarra, alturaBarra);
            }
        } else if (datosInt != null) {
            longitud = datosInt.length;
            int anchoBarra = 1;
            g2d.setColor(color);
            for (int i = 0; i < longitud; i++) {
                int alturaBarra = (int) ((double) datosInt[i] / 255 * height);
                g2d.fillRect(i * anchoBarra, height - alturaBarra, anchoBarra, alturaBarra);
            }
        }
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
        return color.toString() ;
    }
}
