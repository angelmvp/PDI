package vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JOptionPane;
import modelo.Equalizacion;
import modelo.Histograma;
import modelo.ImageBufferedImage;

public class PanelEqualizacion extends JPanel {
    private PanelImagenHistograma panelImagenHistograma;
    private Histograma histograma;
    private Image imagen;
    private JComboBox<String> tipoEcualizacion;
    private JSlider sliderMax;
    private JTextField valorMax;
    private JSlider sliderMin;
    private JTextField valorMin;
    private JSlider sliderAlpha;
    private JTextField alpha;
    private Equalizacion ecualizacion;
    private ImageBufferedImage imageBuffered;
    private JSlider sliderPotencia;
    private JTextField potencia;
    private double[] piac;
    
    public PanelEqualizacion(Image imagen){
        this.imagen = imagen;
        initComponents();
    }
    
    public void initComponents(){
        imageBuffered = new ImageBufferedImage();
        panelImagenHistograma = new PanelImagenHistograma(imagen);
        histograma = panelImagenHistograma.getHistograma();
        piac  = histograma.getPiac();
        ecualizacion = new Equalizacion(imagen, piac);
        
        tipoEcualizacion = new JComboBox<>();
        tipoEcualizacion.addItem("Uniforme");
        tipoEcualizacion.addItem("Exponencial");
        tipoEcualizacion.addItem("Rayleigh");
        tipoEcualizacion.addItem("Hiperbolica Raices");
        tipoEcualizacion.addItem("Hiperbolica Logaritmica");

        JPanel panelSliders = new JPanel(new GridLayout(2, 3));
            sliderMin = new JSlider(0, 255, 0);
            valorMin = new JTextField("0");
            sliderMax = new JSlider(1, 255, 100);
            valorMax = new JTextField("1");
            panelSliders.add(new Label("Fmin"));
            panelSliders.add(valorMin);            
            panelSliders.add(sliderMin);
            panelSliders.add(new Label("Fmax"));
            panelSliders.add(valorMax);
            panelSliders.add(sliderMax);

        
        JPanel panelTipo = new JPanel(new GridLayout(2, 1));
            panelTipo.add(new Label("tipo de ecualizacion"));
            panelTipo.add(tipoEcualizacion);
        
        JPanel panelAlphaPotencia = new JPanel(new GridLayout(2, 3));
            sliderAlpha= new JSlider(1,100,1);
            sliderPotencia=new JSlider(1,1000,1);
            alpha = new JTextField();
            potencia = new JTextField();
            panelAlphaPotencia.add(new Label("Alpha:"));
            panelAlphaPotencia.add(sliderAlpha);
            panelAlphaPotencia.add(alpha);
            panelAlphaPotencia.add(new Label("Potencia:"));
            panelAlphaPotencia.add(sliderPotencia);
            panelAlphaPotencia.add(potencia);
            alpha.setVisible(false);
            potencia.setVisible(false);
        JPanel panelTop = new JPanel(new GridLayout(1, 3));
        panelTop.add(panelTipo);
        panelTop.add(panelSliders);
        panelTop.add(panelAlphaPotencia);
        this.setLayout(new BorderLayout());
        this.add(panelTop, BorderLayout.NORTH);
        this.add(panelImagenHistograma, BorderLayout.CENTER);


        
        sliderMin.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                valorMin.setText(String.valueOf(sliderMin.getValue()));
                equalizar();
            }
        });

        sliderMax.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                valorMax.setText(String.valueOf(sliderMax.getValue()));
                equalizar();
            }
        });
        sliderAlpha.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                alpha.setText(String.valueOf(sliderAlpha.getValue()));
                equalizar();
            }
        });

        sliderPotencia.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                potencia.setText(String.valueOf(sliderPotencia.getValue()));
                equalizar();
            }
        });
        valorMin.addActionListener(e -> equalizar());
        valorMax.addActionListener(e -> equalizar());
        alpha.addActionListener(e -> equalizar());
        potencia.addActionListener(e -> equalizar());

        tipoEcualizacion.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String tipo = (String) tipoEcualizacion.getSelectedItem();
                    switch (tipo) {
                        case "Rayleigh" :
                        case "Exponencial":
                            alpha.setVisible(true);
                            alpha.setText("1");
                            potencia.setVisible(false);
                            break;
                        case "Hiperbolica Raices":
                            alpha.setVisible(false);
                            potencia.setVisible(true);
                            potencia.setText("1");
                            break;
                        default:
                            alpha.setVisible(false);
                            potencia.setVisible(false);
                            break;
                    }
                    equalizar();
                    revalidate();
                    repaint();
                }
            }
        });
    }
    
    public void equalizar() {
        try {
            int fmin = Integer.parseInt(valorMin.getText());
            int fmax = Integer.parseInt(valorMax.getText());

            if(fmin > fmax) {
                System.out.println("Fmin no puede ser mayor que Fmax.");
                JOptionPane.showMessageDialog(null, "El valor minimo no puede ser Mayor a l valor Maximo");
                return;
            }

            Image nuevaImagen = null;
            String tipo = (String) tipoEcualizacion.getSelectedItem();
            double valorAlpha;
            switch (tipo) {
                case "Uniforme":
                    nuevaImagen = ecualizacion.aplicarEcualizacion(1, fmin, fmax, 0,0);
                    System.out.println("uniforme");
                    break;
                case "Exponencial":
                    valorAlpha = Double.parseDouble(alpha.getText())/1000;
                    if(valorAlpha==0.0){
                        System.out.println("no puede ser  alpha 0 ");
                        return;
                    }
                    nuevaImagen = ecualizacion.aplicarEcualizacion(2, fmin, fmax, valorAlpha,0);
                    System.out.println("exponencial");
                    break;
                case "Rayleigh":
                    valorAlpha = Double.parseDouble(alpha.getText());
                    nuevaImagen = ecualizacion.aplicarEcualizacion(3, fmin, fmax, valorAlpha,0);
                    break;
                case "Hiperbolica Raices":
                    double valorPotencia = Double.parseDouble(potencia.getText()) /1000;
                    if(valorPotencia==0.0){
                        System.out.println("no puede ser  la potencia 0 ");
                        return;
                    }
                    
                    nuevaImagen = ecualizacion.aplicarEcualizacion(4, fmin, fmax, 0,valorPotencia);
                    break;
                case "Hiperbolica Logaritmica":
                    if(fmin==0){
                        System.out.println("F minimo no puede ser 0");
                        return;
                    }
                    nuevaImagen = ecualizacion.aplicarEcualizacion(5, fmin, fmax, 0,0);
                    break;
                default:
                    System.out.println("Tipo de ecualización no reconocido.");
                    break;
            }

            if (nuevaImagen != null) {
                panelImagenHistograma.setImagen(nuevaImagen);
                revalidate();
                repaint();
            }

        } catch (NumberFormatException e) {
            System.out.println("Por favor ingresa valores numéricos válidos.");
        }
    }
    public void setImagen(Image nuevaImagen){
        this.imagen=nuevaImagen;
        panelImagenHistograma.setImagen(nuevaImagen);
        histograma = panelImagenHistograma.getHistograma();
        piac  = histograma.getPiac();
        ecualizacion.setImagen(nuevaImagen,piac);
        equalizar();
    }

}