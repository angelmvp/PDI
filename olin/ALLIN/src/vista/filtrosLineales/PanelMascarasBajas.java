/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.filtrosLineales;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelMascarasBajas extends JPanel {
    private JComboBox<String> comboMascara;
    private JTextField[][] valoresMascara;
    private int columnas;
    private int filas;
    private final double[][] gauss = {{1, 2, 1}, {2, 8, 2}, {1, 2, 1}};
    private final double[][] promediador = {{1,1,1},{1,1,1},{1,1,1}};
    private final double[][] suavizador = {{0,0,1,1,1,1,1,0,0},{0,1,1,1,1,1,1,1,0},
                                {1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},
                                {1,1,1,1,9,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},
                                {0,0,1,1,1,1,1,0,0},{0,1,1,1,1,1,1,1,0}};
    private final double[][] sobel = {{1, 0, -1}, {2, 0, -2}, {1, 0, -1}};
    private final double[][] freiChen = {{1, 0, -1}, {Math.sqrt(2), 0, -Math.sqrt(2)}, {1, 0, -1}};
    private double[][] matriz;
    private JButton botonAplicarMatriz;
    private JButton botonAplicarTam;
    private JTextField tamMatriz;
    private JPanel panelMatriz;
    private int alpha;
    private int bias;
    public PanelMascarasBajas() {
        columnas = 3;
        filas = 3;
        alpha = 1;
        initComponents();
    }

    public void initComponents() {
        matriz = new double[columnas][filas];
        valoresMascara = new JTextField[filas][columnas];
        this.setLayout(new BorderLayout());

        comboMascara = new JComboBox<>();
        comboMascara.addItem("gaussiano");
        comboMascara.addItem("promediador");
        comboMascara.addItem("suavizador");
        comboMascara.addItem("Personalizar");

        panelMatriz = new JPanel(new GridLayout(filas, columnas));
        inicializarMatricesTexto();

        JPanel panelMedioLabel = new JPanel(new GridLayout(1, 1));
        panelMedioLabel.add(new JLabel("Mascara"));

        JPanel panelMedio = new JPanel(new GridLayout(2, 1));
        panelMedio.add(panelMatriz);

        this.add(comboMascara, BorderLayout.NORTH);
        this.add(panelMedioLabel, BorderLayout.WEST);
        this.add(panelMedio, BorderLayout.CENTER);

        JPanel panelBottom = new JPanel(new GridLayout(2, 2));
        botonAplicarTam = new JButton("Establecer tamaño");
        botonAplicarTam.setVisible(false);
        botonAplicarMatriz = new JButton("Aplicar");
        botonAplicarMatriz.setVisible(false);
        tamMatriz = new JTextField();
        tamMatriz.setVisible(false);

        panelBottom.add(new Label("Tamaño:"));
        panelBottom.add(tamMatriz);
        panelBottom.add(botonAplicarTam);
        panelBottom.add(botonAplicarMatriz);
        this.add(panelBottom, BorderLayout.SOUTH);

        botonAplicarTam.addActionListener(e -> establecerTam());
        botonAplicarMatriz.addActionListener(e -> setMascaraPersonalizada());
        comboMascara.addActionListener(e -> setMascaraValores());
    }

    public void setMascaraValores() {
        String tipo = comboMascara.getSelectedItem().toString();
        if (tipo.equals("Personalizar")) {
            personalizarMatriz();
        } else {
            actualizarTamMatricesTexto(3);
            botonAplicarTam.setVisible(false);
            botonAplicarMatriz.setVisible(false);
            tamMatriz.setVisible(false);
            switch (tipo) {
                case "gaussiano":
                    matriz = copiarMatriz(gauss);
                    break;
                case "promediador":
                    matriz = copiarMatriz(promediador);
                    break;
                case "suavizador":
                    actualizarTamMatricesTexto(9);
                    matriz=copiarMatriz(suavizador);
            }
        }

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (i < matriz.length && j < matriz[i].length && !tipo.equals("Personalizar")) {
                    valoresMascara[i][j].setText(String.valueOf(matriz[i][j]));
                } else {
                    valoresMascara[i][j].setText("");
                }
            }
        }
        actualizarBias();

    }

    public void establecerTam() {
        int tam = Integer.parseInt(tamMatriz.getText());
        actualizarTamMatricesTexto(tam);
        setMascaraValores();
    }

    public void personalizarMatriz() {
        botonAplicarTam.setVisible(true);
        tamMatriz.setVisible(true);
        botonAplicarMatriz.setVisible(true);
    }

    public void setMascaraPersonalizada() {
        double [][] matriz = new double[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = Double.parseDouble(valoresMascara[i][j].getText());
            }
        }
        this.matriz = copiarMatriz(matriz);
        actualizarBias();
    }
    public void actualizarBias(){
        int suma=0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                suma+= matriz[i][j]; 
            }
        }
        bias = suma;
    }
    public void inicializarMatricesTexto() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                valoresMascara[i][j] = new JTextField();
                panelMatriz.add(valoresMascara[i][j]);
            }
        }
    }

    public void actualizarTamMatricesTexto(int tam) {
        filas = tam;
        columnas = tam;
        matriz = new double[tam][tam];
        valoresMascara = new JTextField[tam][tam];

        panelMatriz.removeAll();
        panelMatriz.setLayout(new GridLayout(tam, tam));

        inicializarMatricesTexto();

        panelMatriz.revalidate();
        panelMatriz.repaint();
    }


    public double[][] copiarMatriz(double[][] original) {
        double[][] copia = new double[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < original[i].length; j++) {
                copia[i][j] = original[i][j];
            }
        }
        return copia;
    }

    public void setAlpha(int alpha) {
        System.out.println(alpha);
        this.alpha = alpha;
        setMascaraValores();
    }

    public double[][] getMascara() {
        return matriz;
    }

    public int getTam() {
        return filas;
    }

    public String getNombreMascara() {
        return comboMascara.getSelectedItem().toString();
    }
    public double getBias(){
        return bias;
    }
}
