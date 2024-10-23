/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author jhona
 */
public class PanelMascarasAltas extends JPanel {
    private JComboBox<String> comboMascara;
    private JTextField[][]  valoresMascaraFilas;
    private JTextField[][]  valoresMascaraColumnas;
    private int columnas;
    private int filas;
    private final double[][] robertsF = {{0, 0, -1}, {0, 1, 0}, {0, 0, 0}};
    private final double[][] robertsC = {{-1, 0, 0}, {0, 1, 0}, {0, 0, 0}};
    private final double[][] prewittF = {{1, 0, -1}, {1, 0, -1}, {1, 0, -1}};
    private final double[][] prewittC = {{-1, -1, -1}, {0, 0, 0}, {1, 1, 1}};
    private final double[][] sobelF = {{1, 0, -1}, {2, 0, -2}, {1, 0, -1}};
    private final double[][] sobelC = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
    private final double[][] freiChenF = {{1, 0, -1}, {Math.sqrt(2), 0, -Math.sqrt(2)}, {1, 0, -1}};
    private final double[][] freiChenC = {{-1, -Math.sqrt(2), -1}, {0, 0, 0}, {1, Math.sqrt(2), 1}};
    private double[][] matrizF;
    private double [][] matrizC;
    private JButton botonAplicarMatriz;
    private JButton botonAplicarTam;
    private JTextField tamMatriz;
    private JPanel panelMatrizFilas;
    private JPanel panelMatrizColumnas;
    public PanelMascarasAltas(){
        columnas=3;
        filas=3;
        initComponents();
    }
    

    public void initComponents() {
        matrizF = new double[columnas][filas];
        matrizC = new double[columnas][filas];
        valoresMascaraFilas = new JTextField[filas][columnas];
        valoresMascaraColumnas = new JTextField[filas][columnas];
        this.setLayout(new BorderLayout());

        comboMascara = new JComboBox<>();
        comboMascara.addItem("Roberts");
        comboMascara.addItem("Prewitt");
        comboMascara.addItem("Sobel");
        comboMascara.addItem("Frei-Chen");
        comboMascara.addItem("Canny");
        comboMascara.addItem("Personalizar");
        
        panelMatrizFilas = new JPanel(new GridLayout(filas, columnas));  
        panelMatrizColumnas = new JPanel (new GridLayout(filas,columnas));
        // Inicializar matrices de texto
        inicializarMatricesTexto();
        JPanel panelMedioLabel = new JPanel(new GridLayout(2,1));
        panelMedioLabel.add(new JLabel("Mascara Filas"));
        panelMedioLabel.add(new JLabel("Mascara Columnas"));
        JPanel panelMedio = new JPanel(new GridLayout(3,1));
            panelMedio.add(panelMatrizFilas);
            panelMedio.add(new Label(""));
            panelMedio.add(panelMatrizColumnas);
        this.add(comboMascara, BorderLayout.NORTH);
        this.add(panelMedioLabel,BorderLayout.WEST);
        this.add(panelMedio, BorderLayout.CENTER);
        
        JPanel panelBottom= new JPanel(new GridLayout(2,2));
        botonAplicarTam= new JButton("establecer tamaño");
        botonAplicarTam.setVisible(false);
        botonAplicarMatriz = new JButton("aplicar");
        botonAplicarMatriz.setVisible(false);
        tamMatriz = new JTextField();
        tamMatriz.setVisible(false);
        
        panelBottom.add(new Label("Tamaño:"));
        panelBottom.add(tamMatriz);
        panelBottom.add(botonAplicarTam);
        panelBottom.add(botonAplicarMatriz);
        this.add(panelBottom,BorderLayout.SOUTH);
        botonAplicarTam.addActionListener(e->establecerTam());
        botonAplicarMatriz.addActionListener(e->setMascaraPersonalizada());
        comboMascara.addActionListener(e -> setMascaraValores());
    }
    public void setMascaraValores() {
        String tipo = comboMascara.getSelectedItem().toString();
        if(tipo=="Personalizar"){
            personalizarMatriz();
        }else if(tipo=="Canny"){
            mascaraCanny();
        }
        else{
        actualizarTamMatricesTexto(3);
        botonAplicarTam.setVisible(false);
        botonAplicarMatriz.setVisible(false);
        tamMatriz.setVisible(false);
            switch (tipo) {
                case "Roberts":
                    matrizF = robertsF;
                    matrizC = robertsC;
                    break;
                case "Prewitt":
                    matrizF = prewittF;
                    matrizC = prewittC;
                    break;
                case "Sobel":
                    matrizF = sobelF;
                    matrizC = sobelC;
                    break;
                case "Frei-Chen":
                    matrizF = freiChenF;
                    matrizC = freiChenC;
                    break;
            }
        }
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (i < matrizF.length && j < matrizF[i].length && tipo!="Personalizar") {
                    valoresMascaraFilas[i][j].setText(String.valueOf(matrizF[i][j]));
                    valoresMascaraColumnas[i][j].setText(String.valueOf(matrizC[i][j]));
                } else {
                    System.out.println("va en "+ i);
                    valoresMascaraColumnas[i][j].setText("");
                    valoresMascaraFilas[i][j].setText("");
                }
            }
        }
        imprimirMatrices();
    }
    public void mascaraCanny(){
        
    }
    public void establecerTam(){
        int tam = Integer.parseInt(tamMatriz.getText());
        System.out.println(tamMatriz.getText());
        actualizarTamMatricesTexto(tam);
        setMascaraValores();
    }
    public void personalizarMatriz(){
        botonAplicarTam.setVisible(true);
        tamMatriz.setVisible(true);
        botonAplicarMatriz.setVisible(true);
    }
    public void setMascaraPersonalizada() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matrizF[i][j] = Double.parseDouble(valoresMascaraFilas[i][j].getText());
                matrizC[i][j] = Double.parseDouble(valoresMascaraColumnas[i][j].getText());
            }
        }
        //imprimirMatrices();
    }
    public void inicializarMatricesTexto(){
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                valoresMascaraFilas[i][j] = new JTextField();
                valoresMascaraColumnas[i][j] = new JTextField();
                panelMatrizFilas.add(valoresMascaraFilas[i][j]);
                panelMatrizColumnas.add(valoresMascaraColumnas[i][j]);
            }
        }
    }
    public void actualizarTamMatricesTexto(int tam){
        filas=tam;
        columnas=tam;
        matrizF = new double[tam][tam];
        matrizC = new double[tam][tam];
        valoresMascaraFilas = new JTextField[tam][tam];
        valoresMascaraColumnas = new JTextField[tam][tam];
        
        panelMatrizFilas.removeAll();
        panelMatrizColumnas.removeAll();
        
        panelMatrizFilas.setLayout(new GridLayout(tam, tam));
        panelMatrizColumnas.setLayout(new GridLayout(tam, tam));
                
        inicializarMatricesTexto();
        
        panelMatrizFilas.revalidate();
        panelMatrizFilas.repaint();
        panelMatrizColumnas.revalidate();
        panelMatrizColumnas.repaint();
    }
    public void imprimirMatrices(){
        System.out.println("FILAAS");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (i < matrizF.length && j < matrizF[i].length) {
                    System.out.println(matrizF[i][j]);
                } else {
                    valoresMascaraColumnas[i][j].setText("");
                    valoresMascaraFilas[i][j].setText("");
                }
            }
        }
        System.out.println("COLUMANS");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (i < matrizF.length && j < matrizF[i].length) {
                    System.out.println(matrizC[i][j]);
                } else {
                    valoresMascaraColumnas[i][j].setText("");
                    valoresMascaraFilas[i][j].setText("");
                }
            }
        } 
    }
    public double[][]getMascaraFilas(){
        return matrizF;
    }
    public double[][]getMascaraColumnas(){
        return matrizC;
    }

    public int getTam(){
        return filas;
    }
    public String getNombreMascara(){
        return comboMascara.getSelectedItem().toString();
    }
}

