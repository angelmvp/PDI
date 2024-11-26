package temporal.morfologia;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import javax.swing.JFrame;

/**
 *
 * @author sdelaot
 */
public class Esqueletizador {
    /**
     * Imagen binaria de entrada
     */
    private int [][] imgBinaria;
    /**
     * Imagen de esqueleto de salida
     */
    private Image imgEsqueleto;
    /**
     * Imagen binaria de esqueleto
     */
    private int [][] imgBinEsq;
    /**
     * Ancho de la imagen
     */
    private int ancho;
    /**
     * alto de la imagen
     */
    private int alto;
    /**
     * Crea un objeto con la imagen binaria
     * @param imgBinaria imagen binaria
     */
    public Esqueletizador(int[][] imgBinaria) {
        this.imgBinaria = imgBinaria;
        alto = imgBinaria.length;
        ancho = imgBinaria[0].length;
    }
    /**
     * Devuelve la imagen
     * 
     * @return devuelve un objeto Image
     */
    public Image getImagen() {
        int [] pixeles = new int[alto*ancho];
        int k = 0;
        
        for( int y=0; y<alto; y++ ) {
            for( int x=0; x<ancho; x++ ) {
                int col = imgBinEsq[y][x];
                Color cl = new Color( col, col, col );
                pixeles[k++] = cl.getRGB();
                }
            }
        JFrame frameTmp = new JFrame();
	imgEsqueleto = 
		frameTmp.createImage(new MemoryImageSource( ancho, 
			alto, pixeles, 0, ancho ) );
        return imgEsqueleto;
    }
    /**
     * Encuentra el esqueleto
     */
    public void encontrarEsqueleto() {
        int [][] ventana = null;
        int [][] imgTemporal = new int[alto][ancho];
        int p = 0;
        // primera sub-iteracion
        for( int y=0; y<imgBinaria.length; y++ ) {
            for( int x=0; x<imgBinaria[y].length; x++ ) {
                ventana = asignarImagen( imgBinaria, x, y, 3 );
                //System.out.print( p++ + ": " );
                //imprimirVentana( ventana );
                if( pasarPorSubIteracionUno( ventana ) ) {
                    imgTemporal[y][x] = 255;//imgBinaria[y][x];
                    //System.out.print( " " + imgTemporal[y][x] );
                    }
                else {
                    imgTemporal[y][x] = imgBinaria[y][x];
                    }
                }
            }
        p = 0;
        imgBinEsq = new int[alto][ancho];
        // segunda sub-iteracion
        for( int y=0; y<imgTemporal.length; y++ ) {
            for( int x=0; x<imgTemporal[y].length; x++ ) {
                ventana = asignarImagen( imgTemporal, x, y, 3 );
                //System.out.print( p++ + ": " );
                //imprimirVentana( ventana );
                if( pasarPorSubIteracionDos( ventana ) ) {
                    imgBinEsq[y][x] = 255;//imgTemporal[y][x];
                    //System.out.print( " " + imgBinEsq[y][x] );
                    }
                else {
                    imgBinEsq[y][x] = imgTemporal[y][x];
                    //System.out.print( " " + imgBinEsq[y][x] );
                    }
                }
            }
    }
    /**
     * Imprime la ventana
     * 
     * @param ventana la ventana que se imprime
     */
    private void imprimirVentana( int [][] ventana ) {
        for( int y=0; y<ventana.length; y++ ) {
            for( int x=0; x<ventana[y].length; x++ ) {
                System.out.print( " " + ventana[y][x] );
                }
            }
        //System.out.println( );
    }
    /**
     * Toma un pedazo de la imagen del tamanio de la mascara, segun donde vaya 
     * moviendose la mascara, es de donde se toma la imagen:
     *
     * @param matriz la imagen de donde se toma la subimagen a convolucionar
     * @param x la posicion en X del pixel central de la subimagen
     * @param y la posicion en Y del pixel central de la subimagen
     * @param tam el tamanio de la subimagen en pixeles
     *
     * @return devuelve la subimagen en un arreglo unidimensional de enteros
     */
    public int [][] asignarImagen( int [][] matriz, int x, int y, int tam ) {
        int ct = 0;
        int [] subImg = null;
        int [][] imgBin = null;
        try {
            subImg = new int[tam*tam];
            for( int n=-tam/2; n<=tam/2; n++ ) {
                for( int m=-tam/2; m<=tam/2; m++, ct++ ) {
                    if( (y+n)<0 && (x+m)<0 ) {
                        subImg[ct] = matriz[alto+n][ancho+m];
                        }
                    else
                    if( (y+n)<0 && x+m<ancho ) {
                        subImg[ct] = matriz[alto+n][x+m];
                        }
                    else
                    if( (x+m)<0 && y+n<alto ) {
                        subImg[ct] = matriz[y+n][ancho+m];
                        }
                    else
                    if( x+m==ancho && y+n==alto ) {
                        subImg[ct] = matriz[0][0];
                        }
                    else
                    if( x+m>=ancho ) {
                        subImg[ct] = matriz[y][m];
                        }
                    else
                    if( y+n>=alto ) {
                        subImg[ct] = matriz[n][x];
                        }
                    else {
                            subImg[ct] = matriz[y+n][x+m];
                            }
                        }
                    }
            imgBin = new int[tam][tam];
            int k = 0;
            for( int n=0; n<tam; n++ ) {
                for( int m=0;m<tam; m++ ) {
                    if( subImg[k]==255 ) {
                        imgBin[n][m] = 1;
                        }
                    else {
                        imgBin[n][m] = subImg[k];
                        }
                    k++;
                    }
                }
        } catch( NegativeArraySizeException e ) {
            System.out.println( " Error en asignarImagen()" +
                                            " tam es negativo " + e );
        } catch( ArrayIndexOutOfBoundsException e ) {
            System.out.println( " Error en asignarImagen()" +
                                            " indice fuera de rango " + e );
        } catch( NullPointerException e ) {
            System.out.println( " Error en asignarImagen()" + e );
        }
        //return subImg;
        return imgBin;
    }
    /**
     * Para la subestacion uno
     * 
     * @param ventana la ventana que se verifica
     * 
     * @return devuelve true si es parte del esqueleto, false en caso contrario
     */
    private boolean pasarPorSubIteracionUno( int [][] ventana ) {
        boolean eliminarPixel = false;
        int Bp1 = pruebaBP1( ventana );
        int Ap1 = pruebaAP1( ventana );
        int pA = probarA( ventana );
        int pB = probarB( ventana );
//        System.out.println( " 1) " + Bp1 + " 2) " + Ap1 + 
//                            " 3) " + pA + " 4) " + pB );
        if( (Bp1>=2 && Bp1<=6) && Ap1==1 && pA==0 && pB==0 ) {
            // no es del esqueleto
            eliminarPixel = true;
            }
        return eliminarPixel;
    }
    /**
     * Para la subestacion dos
     * 
     * @param ventana la ventana que se verifica
     * 
     * @return devuelve true si es parte del esqueleto, false en caso contrario
     */
    private boolean pasarPorSubIteracionDos( int [][] ventana ) {
        boolean eliminarPixel = false;
        int Bp1 = pruebaBP1( ventana );
        int Ap1 = pruebaAP1( ventana );
        int pC = probarC( ventana );
        int pD = probarD( ventana );
//        System.out.println( " 1) " + Bp1 + " 2) " + Ap1 + 
//                            " 3) " + pC + " 4) " + pD );
        if( (Bp1>=2 && Bp1<=6) && Ap1==1 && pC==0 && pD==0 ) {
            // no es del esqueleto, se elimina
            eliminarPixel = true;
            }
        return eliminarPixel;
    }
    /**
     * Prueba BP1 la ventana
     * 
     * @param ventana ventana a probar
     * 
     * @return devuelve la suma de los BPs
     */
    private int pruebaBP1( int [][] ventana ) {
        int P1 = ventana[1][1];
        int P2 = ventana[0][1];
        int P3 = ventana[0][2];
        int P4 = ventana[1][2];
        int P5 = ventana[2][2];
        int P6 = ventana[2][1];
        int P7 = ventana[2][0];
        int P8 = ventana[1][0];
        int P9 = ventana[0][0];
        int BP1 = P2 + P3 + P4 + P5 + P6 + P7 + P8 + P9;
        return BP1;
    }
    /**
     * Prueba AP1 la ventana
     * 
     * @param ventana ventana a probar
     * 
     * @return devuelve el conteo de los APs 
     */
    private int pruebaAP1( int [][] ventana ) {
        int P1 = ventana[1][1];
        int P2 = ventana[0][1];
        int P3 = ventana[0][2];
        int P4 = ventana[1][2];
        int P5 = ventana[2][2];
        int P6 = ventana[2][1];
        int P7 = ventana[2][0];
        int P8 = ventana[1][0];
        int P9 = ventana[0][0];
        int AP1 = 0;
        boolean [] p = new boolean[9];
        for( int n=0; n<p.length; n++ ) {
            p[n] = false;
            }
        if( P1==1 ) {
            p[0] = true;
            }
        if( P2==1 ) {
            p[1] = true;
            }
        if( P3==1 ) {
            p[2] = true;
            }
        if( P4==1 ) {
            p[3] = true;
            }
        if( P5==1 ) {
            p[4] = true;
            }
        if( P6==1 ) {
            p[5] = true;
            }
        if( P7==1 ) {
            p[6] = true;
            }
        if( P8==1 ) {
            p[7] = true;
            }
        if( P9==1 ) {
            p[8] = true;
            }
        if ((!p[0]) && p[1]) AP1++; 
        if ((!p[1]) && p[2]) AP1++; 
        if ((!p[2]) && p[3]) AP1++; 
        if ((!p[3]) && p[4]) AP1++; 
        if ((!p[4]) && p[5]) AP1++; 
        if ((!p[5]) && p[6]) AP1++; 
        if ((!p[6]) && p[7]) AP1++; 
        if ((!p[7]) && p[0]) AP1++;
        return AP1;
    }
    /**
     * Prueba A
     * @param ventana la ventana a probar
     * @return devuelve el producto de P2, P4 y P6
     */
    private int probarA( int [][] ventana ) {
        int P2 = ventana[0][1];
        int P4 = ventana[1][2];
        int P6 = ventana[2][1];
        
        int prueba = P2 * P4 * P6;
        return prueba;
    }
    /**
     * Prueba B
     * @param ventana la ventana a probar
     * @return devuelve el producto de P4, P8 y P6
     */
    private int probarB( int [][] ventana ) {
        int P4 = ventana[1][2];
        int P6 = ventana[2][1];
        int P8 = ventana[1][0];
        int prueba = P4 * P8 * P6;
        return prueba;
    }
    /**
     * Prueba C
     * @param ventana la ventana a probar
     * @return devuelve el producto de P2, P4 y P8
     */
    private int probarC( int [][] ventana ) {
        int P2 = ventana[0][1];
        int P4 = ventana[1][2];
        int P8 = ventana[1][0];
        int prueba = P2 * P4 * P8;
        return prueba;
    }
    /**
     * Prueba D
     * @param ventana la ventana a probar
     * @return devuelve el producto de P2, P6 y P8
     */
    private int probarD( int [][] ventana ) {
        int P2 = ventana[0][1];
        int P6 = ventana[2][1];
        int P8 = ventana[1][0];
        int prueba = P2 * P6 * P8;
        return prueba;
    }
}
