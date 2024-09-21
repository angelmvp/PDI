/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import modelo.Histograma;
import modelo.ImageBufferedImage;

/**
 *
 * @author jhona
 */

public class PanelImagenHistograma extends JPanel {
    private int [][] imagenInt;
    private JButton botonGenerar;
    private PanelHistograma panelHistograma;
    private Histograma histograma;
    private JComboBox<String> tipoHistograma;
    private JComboBox<String> tipoImagen;
    private Image img;
    private PanelImagen panelImagen;
    private ImageBufferedImage bufferedImage;
    private BufferedImage imagen;
    private Color color;
    public PanelImagenHistograma(Image img){
        this.img=img;
        initcomponents();
    }
    public void initcomponents(){
        bufferedImage = new ImageBufferedImage();
        imagen=bufferedImage.getBufferedImageColor(img);
        imagenInt= bufferedImage.getMatrizImagen(imagen,5);
        histograma= new Histograma(imagenInt);
        histograma.ejecutarTodo();
        panelImagen = new PanelImagen(img);
        panelHistograma= new PanelHistograma(histograma.getPi(),Color.GRAY);
        this.setLayout(new BorderLayout());
        botonGenerar= new JButton("Generar Histograma");
        tipoHistograma = new JComboBox<>();
        tipoHistograma.addItem("Histograma");
        tipoHistograma.addItem("Histograma acumulado");
        tipoHistograma.addItem("Probabilidad");
        tipoHistograma.addItem("Probabilidad acumulada");
        tipoImagen=new JComboBox<>();
        tipoImagen.addItem("Escala de Grises");
        tipoImagen.addItem("Roja");
        tipoImagen.addItem("Verde");
        tipoImagen.addItem("Azul");
        
        JPanel panelTop = new JPanel();
        panelTop.add(tipoImagen);
        panelTop.add(tipoHistograma);
        panelTop.add(botonGenerar);
        JPanel panelCentro = new JPanel(new GridLayout(1,2));
        panelCentro.add(panelImagen,BorderLayout.WEST);
        panelCentro.add(panelHistograma,BorderLayout.CENTER);
        this.add(panelTop,BorderLayout.NORTH);
        this.add(panelCentro,BorderLayout.CENTER);

            botonGenerar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                generarFrame();
            }             
            });
            tipoHistograma.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                actualizarHistograma();
            }
            });
            tipoImagen.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                actualizarImagen();
            }
            });
    }
    public void setImagen(Image img) {
        this.img = img;
        panelImagen.setImagen(img);
        imagen=bufferedImage.getBufferedImageColor(img);
        actualizarImagen();                    
    }
    public int obtenerCanal(String opcion ){
        int canal=5;
        if(opcion=="Escala de Grises"){
            canal=5;
            color=Color.gray;
        }else if(opcion=="Roja"){
            canal=1;
            color=Color.RED;
        }else if(opcion=="Verde"){
            canal=2;
            color=Color.GREEN;
        }else if(opcion=="Azul"){
            canal=3;
            color=Color.BLUE;
        }
        return canal;
    }
    public void actualizarImagen(){
        int canal= obtenerCanal((String) tipoImagen.getSelectedItem());
        imagenInt=bufferedImage.getMatrizImagen(imagen, canal);
        histograma.setNuevaMatriz(imagenInt);
        if(canal==5){
            panelImagen.setImagen(bufferedImage.getImage(imagen, canal));
        }else{
            panelImagen.setImagen(img);
        }
        actualizarHistograma();
    }
    public void actualizarHistograma(){
        String seleccion = (String) tipoHistograma.getSelectedItem();
        double[] nuevosDatos = null;
        int [] nuevosDatosint =null;
        switch (seleccion) {
            case "Histograma":
                nuevosDatosint =  histograma.getHi(); 
                break;
            case "Histograma acumulado":
                nuevosDatosint = histograma.getHiac();
                break;
            case "Probabilidad":
                nuevosDatos = histograma.getPi();
                break;
            case "Probabilidad acumulada":
                nuevosDatos = histograma.getPiac();
                break;
        }

        if (nuevosDatos != null) {
            panelHistograma.setDatos(nuevosDatos, color);
        }else{
            panelHistograma.setDatos(nuevosDatosint,color);
        }
    }
    public void generarFrame(){
       PanelHistograma nuevoPanel = new PanelHistograma(this.panelHistograma);
       //FrameImagenHistograma frameImgHistograma= new FrameImagenHistograma(img,nuevoPanel);
    }
    public void imprimirArreglo(int[] datos){
            for(int x=0; x<200; x++){
                System.out.println(datos[x]);
            }
    }
    public void imprimir(){
         for(int y=0; y<imagen.getHeight();y++){
            for(int x=0; x<imagen.getWidth(); x++){
                if(imagenInt[y][x]>253){
                System.out.println(imagenInt[y][x]);
            }
                
            }
        }
    }
}
