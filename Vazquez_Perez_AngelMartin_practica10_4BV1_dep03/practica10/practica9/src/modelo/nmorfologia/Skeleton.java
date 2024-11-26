/**
 ******************************************************************************
 *
 * class Skeleton.java
 *
 * El listado muestra el codigo fuente para implementar la medida de distancia 
 * euclidiana y la transformación del eje medio. La funcion edm calcula la 
 * medida de distancia euclidiana. Recorre la imagen y llama a la funcion 
 * distanciaOcho para hacer la mayor parte del trabajo. 
 * 
 * La funcion distanceEight tiene ocho secciones para calcular ocho distancias 
 * desde cualquier pixel de valor hasta el pixel cero más cercano. La funcion 
 * distanceEight devuelve la distancia mas corta que encontro. Las funciones 
 * mat y matD calculan la transformacion de eje medio de manera similar. La 
 * funcion mat recorre la imagen y llama a la función matD para hacer el 
 * trabajo. La función matD calcula las ocho distancias y registra las dos 
 * distancias mas cortas, si estas dos son iguales, el pixel en cuestion esta 
 * minimamente distante a dos contornos, es parte de la transformacion del eje 
 * medio y hace que la función matD devuelva un valor.
 * 
 * Functions: This class contains 
 *          specialOpening
 *          dilation
 *          thinning
 *          canThin 
 *          specialClosing
 *          erosion
 *          dilateNotJoin 
 *          canDilate
 *          littleLabelAndCheck 
 *           
 *          edm 
 *          distanceEight 
 *          mat
 *          matD
 *
 * Purpose: These functions thin objects in an image, dilate objects, and
 * perform opening and closing without removing or joining objects.
 *
 * External Calls: none
 *
 * Modifications: 21 july 2023 - created 21 August 1998 - modified to work on
 * entire images at once.
 *
 *****************************************************************************
 */
package temporal.morfologia;

/**
 *
 * @author sdelaot
 */
public class Skeleton {
    /**
     * Niveles de gris
     */
    private final int GRAY_LEVELS = 255;
    /**
     * Construye el objeto
     */
    public Skeleton() {
    
    }
    /**
     **************************************************************************
     *
     * specialOpening
     *
     * Opening is erosion followed by dilation. This routine will use the
     * thinning erosion routine. This will not allow an object to erode to
     * nothing.
     *
     * The number parameter specifies how erosions to perform before doing one
     * dilation.
     * 
     * La apertura es erosion seguida de dilatacion. Esta rutina utilizara la 
     * rutina de thinning (erosion) de adelgazamiento. Esto no permitira que 
     * un objeto se erosione hasta quedar en nada.
     * El parámetro number especifica como se deben realizar las erosiones 
     * antes de hacer una dilatación.
     *
     **************************************************************************
     * @param the_image la imagen de entrada
     * @param out_image la imagen de salida
     * @param value el valor
     * @param threshold la sujeccion
     * @param number el numero
     * @param rows numero de las filas
     * @param cols numero de las columnas
     **************************************************************************
     */
    public void specialOpening(int[][] the_image, int[][] out_image,
            int value, int threshold, int number,
            int rows, int cols) {
        int a, b, count, i, j, k;
        thinning(the_image, out_image, value, threshold, 1, rows, cols);
        if(number > 1) {
            count = 1;
            while(count < number) {
                count++;
                thinning(the_image, out_image,
                            value, threshold, 1,
                            rows, cols);
                } /* ends while */
            } /* ends if number > 1 */

        dilation(the_image, out_image, value, threshold, rows, cols);
    } /* ends specialOpening */

    /**
     **************************************************************************
     *
     * dilation
     *
     * This function performs the dilation operation. If a 0 pixel has more 
     * than threshold number of value neighbors, you dilate it by setting it 
     * to value.
     * 
     * Esta funcion realiza la operación de dilatacion. Si un píxel 0 tiene mas 
     * del threshold (umbral) de numero de vecinos de valor, lo dilata al 
     * establecerlo a value.
     *
     **************************************************************************
     * @param the_image la imagen de entrada
     * @param out_image la imagen de salida
     * @param value el valor
     * @param threshold la sujeccion
     * @param rows el numero de filas
     * @param cols el numero de columnas
     */
    public void dilation(int[][] the_image, int[][] out_image,
            int value, int threshold, int rows, int cols) {
        int a, b, count, i, j, k;
        int three = 3;
        /**
         * *************************
         * Loop over image array
         ***************************
         */
        for(i = 0; i < rows; i++) {
            for(j = 0; j < cols; j++) {
                out_image[i][j] = the_image[i][j];
                }
            }
        System.out.printf("\n");
        for(i = 1; i < rows - 1; i++) {
            if((i % 10) == 0) {
                System.out.printf("%3d", i);
                }
            for(j = 1; j < cols - 1; j++) {
                out_image[i][j] = the_image[i][j];
                if(the_image[i][j] == 0) {
                    count = 0;
                    for(a = -1; a <= 1; a++) {
                        for(b = -1; b <= 1; b++) {
                            if(a != 0 && b != 0) {
                                if(the_image[i + a][j + b] == value) {
                                    count++;
                                    }
                                } /* ends avoid the center pixel */
                            } /* ends loop over b */
                        } /* ends loop over a */

                    if(count > threshold) {
                        out_image[i][j] = value;
                        }
                    } /* ends if the_image == 0 */
                } /* ends loop over j */
            } /* ends loop over i */
        /**
         ********************************************
         * fix_edges(out_image, three, rows, cols);
         ********************************************
         */
    } /* ends dilation */

    /**
     **************************************************************************
     *
     * thinning
     *
     * Use a variation of the grass fire wave front approach.
     *
     * Raster scan the image left to right and examine and thin the left edge
     * pixels (a 0 to value transition). Process them normally and "save" the
     * result. Next, raster scan the image right to left and save. Raster scan
     * top to bottom and save. Raster scan bottom to top and save.
     *
     * That is one complete pass.
     *
     * Keep track of pixels thinned for a pass and quit when you make a 
     * complete pass without thinning any pixels.
     *
     **************************************************************************
     * @param the_image la imagen de entrada
     * @param out_image la imagen de salida
     * @param value el valor
     * @param threshold la sujeccion
     * @param once_only solamente por si
     * @param rows el numero de filas
     * @param cols el numero de columnas
     **************************************************************************
     */
    public void thinning(int[][] the_image, int[][] out_image,
            int value, int threshold, int once_only, int rows, int cols) {
        int a, b, big_count, count, i, j, k,
                not_finished;
        for(i = 0; i < rows; i++) {
            for(j = 0; j < cols; j++) {
                out_image[i][j] = the_image[i][j];
                }
            }
        not_finished = 1;
        while(not_finished==1) {
            if(once_only == 1) {
                not_finished = 0;
                }
            big_count = 0;
            /**
             ******************************
             * Scan left to right Look for 
             * 0-value transition
             ******************************
             */
            System.out.printf("\n");
            for(i = 1; i < rows - 1; i++) {
                if((i % 10) == 0) {
                    System.out.printf("%3d", i);
                    }
                for(j = 1; j < cols - 1; j++) {
                    if(the_image[i][j - 1] == 0 && the_image[i][j] == value) {
                        count = 0;
                        for(a = -1; a <= 1; a++) {
                            for(b = -1; b <= 1; b++) {
                                if(the_image[i + a][j + b] == 0) {
                                    count++;
                                    }
                                } /* ends loop over b */
                            } /* ends loop over a */

                        if(count > threshold) {
                            if(canThin(the_image, i, j, value)==1) {
                                out_image[i][j] = 0;
                                big_count++;
                                } /* ends if can_thin */
                            } /* ends if count > threshold */
                        } /* ends if the_image == value */
                    } /* ends loop over j */
                } /* ends loop over i */
            /**
             *************************************
             * Copy the output back to the input.
             *************************************
             */
            for(i = 0; i < rows; i++) {
                for(j = 0; j < cols; j++) {
                    the_image[i][j] = out_image[i][j];
                    }
                }
            /**
             *********************************
             * Scan right to left Do this by
             * scanning left to right and
             * look for value-0 transition.
             *********************************
             */
            System.out.printf("\n");
            for(i = 1; i < rows - 1; i++) {
                if((i % 10) == 0) {
                    System.out.printf("%3d", i);
                    }
                for(j = 1; j < cols - 1; j++) {
                    if(the_image[i][j + 1] == 0 && the_image[i][j] == value) {
                        count = 0;
                        for(a = -1; a <= 1; a++) {
                            for(b = -1; b <= 1; b++) {
                                if(the_image[i + a][j + b] == 0) {
                                    count++;
                                    }
                                } /* ends loop over b */
                            } /* ends loop over a */

                        if(count > threshold) {
                            if(canThin(the_image, i, j, value)==1) {
                                out_image[i][j] = 0;
                                big_count++;
                                } /* ends if can_thin */
                            } /* ends if count > threshold */
                        } /* ends if the_image == value */
                    } /* ends loop over j */
                } /* ends loop over i */
            /**
             **************************************
             * Copy the output back to the input.
             **************************************
             */
            for(i = 0; i < rows; i++) {
                for(j = 0; j < cols; j++) {
                    the_image[i][j] = out_image[i][j];
                    }
                }
            /**
             ***************************
             * Scan top to bottom Look 
             * for 0-value transition
             ***************************
             */
            System.out.printf("\n");
            for(j = 1; j < cols - 1; j++) {
                if((j % 10) == 0) {
                    System.out.printf("%3d", j);
                    }
                for(i = 1; i < rows - 1; i++) {
                    if(the_image[i - 1][j] == 0 && the_image[i][j] == value) {
                        count = 0;
                        for(a = -1; a <= 1; a++) {
                            for(b = -1; b <= 1; b++) {
                                if(the_image[i + a][j + b] == 0) {
                                    count++;
                                    }
                                } /* ends loop over b */
                            } /* ends loop over a */

                        if(count > threshold) {
                            if(canThin(the_image, i, j, value)==1) {
                                out_image[i][j] = 0;
                                big_count++;
                                } /* ends if can_thin */
                            } /* ends if count > threshold */
                        } /* ends if the_image == value */
                    } /* ends loop over i */
                } /* ends loop over j */
            /**
             * *************************************
             * Copy the output back to the input.
             **************************************
             */
            for(i = 0; i < rows; i++) {
                for(j = 0; j < cols; j++) {
                    the_image[i][j] = out_image[i][j];
                    }
                }
            /**
             *********************************
             * Scan bottom to top Do this by
             * scanning top to bottom and
             * look for value-0 transition.
             *********************************
             */
            System.out.printf("\n");
            for(j = 1; j < cols - 1; j++) {
                if((j % 10) == 0) {
                    System.out.printf("%3d", j);
                    }
                for(i = 1; i < rows - 1; i++) {
                    if(the_image[i + 1][j] == 0 && the_image[i][j] == value) {
                        count = 0;
                        for(a = -1; a <= 1; a++) {
                            for(b = -1; b <= 1; b++) {
                                if(the_image[i + a][j + b] == 0) {
                                    count++;
                                    }
                                } /* ends loop over b */
                            } /* ends loop over a */

                        if(count > threshold) {
                            if(canThin(the_image, i, j, value)==1) {
                                out_image[i][j] = 0;
                                big_count++;
                                } /* ends if can_thin */
                            } /* ends if count > threshold */
                        } /* ends if the_image == value */
                    } /* ends loop over i */
                } /* ends loop over j */
            /**
             *************************************
             * Copy the output back to the input.
             *************************************
             */
            for(i = 0; i < rows; i++) {
                for(j = 0; j < cols; j++) {
                    the_image[i][j] = out_image[i][j];
                    }
                }
            /**
             **************************************
             * Now look at the result of this big 
             * pass through the image.
             **************************************
             */
            System.out.printf("\n\nThinned %d pixels", big_count);
            if(big_count == 0) {
                not_finished = 0;
                } 
            else {
                for(i = 0; i < rows; i++) {
                    for(j = 0; j < cols; j++) {
                        the_image[i][j] = out_image[i][j];
                        }
                    }
                } /* ends else */
            } /* ends while not_finished */
        /**
         ***************************************
         * fix_edges(out_image, 3, rows, cols);
         ***************************************
         */
    } /* ends thinning */


    /**
     **************************************************************************
     *
     * canThin
     *
     * Look at the neighbors of the center pixel. If a neighbor == value, then
     * it must have a neighbor == value other than the center pixel. F.11. CODE
     * LISTINGS FOR CHAPTER 11 561
     *
     * Procedure: . Copy the 3x3 area surrounding pixel i,j to a temp array. .
     * Set the center pixel to zero. . Look at each non-zero pixel in temp. . 
     * If you cannot find a non-zero neighbor. . Then you cannot thin.
     *
     **************************************************************************
     * @param the_image la imagen de entrada
     * @param i coordenada y
     * @param j coordenada x
     * @param value el valor
     * @return  devuelve si se puede hacer thining o no
     **************************************************************************
     */
    public int canThin(int[][] the_image, int i, int j, int value) {
        int a, b, c, d, count,
                no_neighbor, one = 1, zero = 0;
        int[][] temp = new int[3][3];
        /**
         **************************************
         * Copy the center pixel and its 
         * neighbors to the temp array.
         **************************************
         */
        for(a = -1; a < 2; a++) {
            for(b = -1; b < 2; b++) {
                temp[a + 1][b + 1] = the_image[i + a][b + j];
                }
            }
        /**
         ***************************************
         * Set the center of temp to 0.
         ***************************************
         */
        temp[1][1] = 0;
        /**
         **************************************
         * Check the non-zero pixels in temp.
         **************************************
         */
        for(a = 0; a < 3; a++) {
            for(b = 0; b < 3; b++) {
                if (temp[a][b] == value) {
                    temp[a][b] = 0;
                    /**
                     ************************************
                     * Check the neighbors of this pixel 
                     * If there is a single non-zero 
                     * neighbor, set no_neighbor = 0.
                     ************************************
                     */
                    no_neighbor = 1;
                    for(c = -1; c < 2; c++) {
                        for(d = -1; d < 2; d++) {
                            if( ((a + c) >= 0) && ((a + c) <= 2) && 
                                ((b + d) >= 0) && ((b + d) <= 2) ) {
                                if(temp[a + c][b + d] == value) {
                                    no_neighbor = 0;
                                    } /* ends if temp == value */
                                } /* ends if part of temp array */
                            } /* ends loop over d */
                        } /* ends loop over c */

                    temp[a][b] = value;
                    /**
                     * *********************************
                     * If the non-zero pixel did not 
                     * have any non-zero neighbors, 
                     * no_neighbor still equals 1, and 
                     * we cannot thin, so return zero.
                     ***********************************
                     */
                    if(no_neighbor==0) {
                        return zero;
                        }
                    } /* ends if temp[a][b] == value */
                } /* ends loop over b */
            } /* ends loop over a */
        /**
         **********************************************************************
         * First, ensure the object is more than two wide. If it is two wide,
         * you will thin out the entire object. Check in all eight directions.
         * If the distance to a zero is 0 or >= 2, then ok you can thin so go 
         * on to the remainder of this routine. If not, you cannot thin so 
         * return zero.
         **********************************************************************
         */
        return one;
    } /* ends canThin */

    /**
     **************************************************************************
     *
     * specialClosing
     *
     * Closing is dilation followed by erosion. This routine will use the
     * dilate_not_join dilation routine. This will not allow two separate
     * objects to join.
     *
     * The number parameter specifies how dilations to perform before doing one
     * erosion.
     *
     **************************************************************************
     * @param the_image la imagen de entrada
     * @param out_image la imagen de salida
     * @param value el valor 
     * @param threshold la sujeccion
     * @param number el numero
     * @param rows el numero de filas
     * @param cols el numero de columnas
     **************************************************************************
     */
    public void specialClosing(int[][] the_image, int[][] out_image,
            int value, int threshold, int number, int rows, int cols) {
        int a, b, count, i, j, k;
        dilateNotJoin(the_image, out_image,
                        value, threshold,
                        rows, cols);
        if(number > 1) {
            count = 1;
            while(count < number) {
                count++;
                dilateNotJoin(the_image, out_image,
                                value, threshold,
                                rows, cols);
                } /* ends while */
            } /* ends if number > 1 */
        erosion(the_image, out_image, value, threshold, rows, cols);
    } /* ends specialClosing */


    /**
     **************************************************************************
     *
     * erosion
     *
     * This function performs the erosion operation. If a value pixel has more
     * than the threshold number of 0 neighbors, you erode it by setting it to
     * 0.
     *
     **************************************************************************
     * @param the_image la imagen de entrada
     * @param out_image la imagen de salida
     * @param value el valor
     * @param threshold la sujeccion
     * @param rows el numero de filas
     * @param cols el numero de columnas
     **************************************************************************
     */
    public void erosion(int[][] the_image, int[][] out_image,
            int value, int threshold, int rows, int cols) {
        int a, b, count, i, j, k;
        /**
         ***************************
         * Loop over image array
         ***************************
         */
        for(i = 0; i < rows; i++) {
            for(j = 0; j < cols; j++) {
                out_image[i][j] = the_image[i][j];
                }
            }
        System.out.printf("\n");
        for(i = 1; i < rows - 1; i++) {
            if((i % 10) == 0) {
                System.out.printf("%3d", i);
                }
            for(j = 1; j < cols - 1; j++) {
                if(the_image[i][j] == value) {
                    count = 0;
                    for(a = -1; a <= 1; a++) {
                        for(b = -1; b <= 1; b++) {
                            if((i + a) >= 0) {
                                if(the_image[i + a][j + b] == 0) {
                                    count++;
                                    }
                                }
                            } /* ends loop over b */
                        } /* ends loop over a */

                    if(count > threshold) {
                        out_image[i][j] = 0;
                        }
                    } /* ends if the_image == value */
                } /* ends loop over j */
            } /* ends loop over i */
        /**
         ***************************************
         * fix_edges(out_image, 3, rows, cols);
         ***************************************
         */
    } /* ends erosion */


    /**
     **************************************************************************
     *
     * dilateNotJoin
     *
     * Use a variation of the grass fire wave front approach.
     *
     * Raster scan the image left to right and examine and dilate the left edge
     * pixels (a value to 0 transition). Process them normally and "save" the
     * result. Next, raster scan the image right to left and save. Raster scan
     * top to bottom and save. Raster scan bottom to top and save.
     *
     * That is one complete pass.
     *
     **************************************************************************
     * @param the_image la imagen de entrada
     * @param out_image la imagen de salida
     * @param value el valor
     * @param threshold la sijeccion
     * @param rows el numero de filas
     * @param cols el numero de columnas
     **************************************************************************
     */
    public void dilateNotJoin(int[][] the_image, int[][] out_image,
            int value, int threshold, int rows, int cols) {
        int a, b, count, i, j, k;
        for(i = 0; i < rows; i++) {
            for(j = 0; j < cols; j++) {
                out_image[i][j] = the_image[i][j];
                }
            }
        /**
         ******************************
         * Scan left to right Look for 
         * value-0 transition
         ******************************
         */
        System.out.printf("\n");
        for(i = 1; i < rows - 1; i++) {
            if((i % 10) == 0) {
                System.out.printf("%3d", i);
                }
            for(j = 1; j < cols - 1; j++) {
                if(the_image[i][j - 1] == value
                        && the_image[i][j] == 0) {
                    count = 0;
                    for(a = -1; a <= 1; a++) {
                        for(b = -1; b <= 1; b++) {
                            if(the_image[i + a][j + b] == value) {
                                count++;
                                }
                            } /* ends loop over b */
                        } /* ends loop over a */

                    if(count > threshold) {
                        if(canDilate(the_image, i, j, value)==1) {
                            out_image[i][j] = value;
                            } /* ends if can_dilate */
                        } /* ends if count > threshold */
                    } /* ends if the_image == value */
                } /* ends loop over j */
            } /* ends loop over i */

        /**
         *************************************
         * Copy the output back to the input. 
         *************************************
         */
        for(i = 0; i < rows; i++) {
            for(j = 0; j < cols; j++) {
                the_image[i][j] = out_image[i][j];
                }
            }
        /**
         ***************************************
         * Scan right to left Do this by 
         * scanning left to right and look for
         * 0-value transition.
         ***************************************
         */
        System.out.printf("\n");
        for(i = 1; i < rows - 1; i++) {
            if((i % 10) == 0) {
                System.out.printf("%3d", i);
                }
            for(j = 1; j < cols - 1; j++) {
                if(the_image[i][j + 1] == value && the_image[i][j] == 0) {
                    count = 0;
                    for(a = -1; a <= 1; a++) {
                        for(b = -1; b <= 1; b++) {
                            if(the_image[i + a][j + b] == value) {
                                count++;
                                }
                            } /* ends loop over b */
                        } /* ends loop over a */

                    if(count > threshold) {
                        if(canDilate(the_image, i, j, value)==1) {
                            out_image[i][j] = value;
                            } /* ends if can_dilate */
                        } /* ends if count > threshold */
                    } /* ends if the_image == value */
                } /* ends loop over j */
            } /* ends loop over i */

        /**
         * ************************************
         * Copy the output back to the input.
         *************************************
         */
        for(i = 0; i < rows; i++) {
            for(j = 0; j < cols; j++) {
                the_image[i][j] = out_image[i][j];
                }
            }
        /**
         * *************************
         * Scan top to bottom Look 
         * for value-0 transition
         ***************************
         */
        System.out.printf("\n");
        for(j = 1; j < cols - 1; j++) {
            if((j % 10) == 0) {
                System.out.printf("%3d", j);
                }
            for(i = 1; i < rows - 1; i++) {
                if(the_image[i - 1][j] == value && the_image[i][j] == 0) {
                    count = 0;
                    for(a = -1; a <= 1; a++) {
                        for(b = -1; b <= 1; b++) {
                            if(the_image[i + a][j + b] == value) {
                                count++;
                                }
                            } /* ends loop over b */
                        } /* ends loop over a */

                    if(count > threshold) {
                        if(canDilate(the_image, i, j, value)==1) {
                            out_image[i][j] = value;
                            } /* ends if can_dilate */
                        } /* ends if count > threshold */
                    } /* ends if the_image == value */
                } /* ends loop over i */
            } /* ends loop over j */

        /**
         **************************************
         * Copy the output back to the input.
         **************************************
         */
        for(i = 0; i < rows; i++) {
            for(j = 0; j < cols; j++) {
                the_image[i][j] = out_image[i][j];
                }
            }
        /**
         * ************************************
         * Scan bottom to top Do this by 
         * scanning top to bottom and look for
         * 0-value transition.
         **************************************
         */
        System.out.printf("\n");
        for(j = 1; j < cols - 1; j++) {
            if((j % 10) == 0) {
                System.out.printf("%3d", j);
                }
            for(i = 1; i < rows - 1; i++) {
                if(the_image[i + 1][j] == value && the_image[i][j] == 0) {
                    count = 0;
                    for(a = -1; a <= 1; a++) {
                        for(b = -1; b <= 1; b++) {
                            if(the_image[i + a][j + b] == value) {
                                count++;
                                }
                            } /* ends loop over b */
                        } /* ends loop over a */

                    if(count > threshold) {
                        if(canDilate(the_image, i, j, value)==1) {
                            out_image[i][j] = value;
                            } /* ends if can_dilate */
                        } /* ends if count > threshold */
                    } /* ends if the_image == value */
                } /* ends loop over i */
            } /* ends loop over j */
        /**
         * ************************************
         * Copy the output back to the input.
         *************************************
         */
        for(i = 0; i < rows; i++) {
            for(j = 0; j < cols; j++) {
                the_image[i][j] = out_image[i][j];
                }
            }
        /**
         ****************************************
         * fix_edges(out_image, 3, rows, cols); 
         * F.11. CODE LISTINGS FOR CHAPTER
         * 11 569
         ****************************************
         */
    } /* ends dilateNotJoin */


    /**
     **************************************************************************
     *
     * canDilate
     *
     * This function decides if you can dilate (set to value) a pixel without
     * joining two separate objects in a 3x3 area.
     *
     * First, you grow regions inside the 3x3 area. Next, check if the center
     * pixel has neighbors with differing values. If it does, you cannot dilate
     * it because that would join two separate objects.
     *
     **************************************************************************
     * @param the_image la imagen
     * @param i la coordenada y
     * @param j la coordenada x
     * @param value el valor
     * @return devuelve si se puede hacer dilatado
     */
    public int canDilate(int[][] the_image, int i, int j, int value) {
        int a, b, c, d, count, found = 0,
                no_neighbor,
                stack_pointer = -1,
                stack_empty = 1,
                pop_a, pop_b,
                one = 1,
                zero = 0;
        int[][] stack = new int[12][2];
        int first_value, label = 2;
        int[][] temp = new int[3][3];
        /**
         * ************************************
         * Copy the center pixel and its 
         * neighbors to the temp array.
         **************************************
         */
        for(a = -1; a < 2; a++) {
            for(b = -1; b < 2; b++) {
                temp[a + 1][b + 1] = the_image[i + a][b + j];
                }
            }
        /**
         * ************************************
         * Grow objects inside the temp array.
         **************************************
         */
        for(a = 0; a < 3; a++) {
            for(b = 0; b < 3; b++) {
                stack_empty = 1;
                stack_pointer = -1;
                if(temp[a][b] == value) {
                    int [] valores = littleLabelAndCheck(
                            temp, stack, label,
                            stack_empty,
                            stack_pointer,
                            a, b, value);
                    stack_empty = valores[0];
                    stack_pointer = valores[1];
                    found = 1;
                    } /* ends if temp == value */

                while(stack_empty == 0) {
                    pop_a = stack[stack_pointer][0]; /* POP */
                    pop_b = stack[stack_pointer][1]; /* POP */
                    --stack_pointer;
                    if(stack_pointer <= 0) {
                        stack_pointer = 0;
                        stack_empty = 1;
                        } /* ends if stack_pointer */
                    int [] valores = littleLabelAndCheck(temp, stack, label,
                             stack_empty,
                             stack_pointer,
                            pop_a, pop_b, value);
                    stack_empty = valores[0];
                    stack_pointer = valores[1];
                    } /* ends while stack_empty == 0 */
                if(found==1) {
                    found = 0;
                    label++;
                    } /* ends if object_found */
                } /* ends loop over b */
            } /* ends loop over a */
        /**
         **************************************
         * Look at the center pixel. If it
         * has two non-zero neigbors whose 
         * pixels are not the same, then you
         * cannot dilate.
         **************************************
         */
        first_value = -1;
        for(a = 0; a < 3; a++) {
            for(b = 0; b < 3; b++) {
                if(temp[a][b] != 0 && first_value == -1) {
                    first_value = temp[a][b];
                    }
                if(temp[a][b] != 0 && first_value != -1) {
                    if(temp[a][b] != first_value) {
                        return (zero);
                        }
                    }
                } /* ends loop over b */
            } /* ends loop over a */
        return one;
    } /* ends canDilate */

    /**
     **************************************************************************
     *
     * littleLabelAndCheck
     *
     * This function labels the objects in in a 3x3 area.
     *
     **************************************************************************
     * @param temp la imagen temporal
     * @param stack la imagen pila
     * @param label la etiqueta
     * @param stack_empty si la pila esta vacia
     * @param stack_pointer el pintero a la pila
     * @param a la coordenada en y
     * @param b la coordenada en x
     * @param value el valor
     * @return  devuelve los valores de pila
     **************************************************************************
     */
    public int [] littleLabelAndCheck(int[][] temp, int[][] stack, int label, 
            int stack_empty, int stack_pointer, int a, int b, int value) {
        //stack[12][2], temp[3][3]
        int c, d;
        temp[a][b] = label;
        for(c = a - 1; c <= a + 1; c++) {
            for(d = b - 1; d <= b + 1; d++) {
                if(c >= 0 && c <= 2 && d >= 0 && d <= 2) {
                    if(temp[c][d] == value) { /* PUSH */
                        stack_pointer = stack_pointer + 1;
                        stack[stack_pointer][0] = c;
                        stack[stack_pointer][1] = d;
                        stack_empty = 0;
                        /*
                         * stack_pointer =  * stack_pointer + 1;
                        stack[ * stack_pointer][0] = c;
                        stack[ * stack_pointer][1] = d;
                         * stack_empty = 0;
                        */
                        } /* ends if temp == value */
                    }
                } /* ends loop over d */
            } /* ends loop over c */
        int [] valores = {
            stack_empty,
            stack_pointer
            };
        return valores;
    } /* ends littleLabelAndCheck */

    /**
     * ************************************************************************
     *
     * edm
     *
     * This function calculates the Euclidean distance measure for objects in 
     * an image. It calculates the distance from any pixel=value to the nearest
     * zero pixel
     *
     **************************************************************************
     * @param the_image la imagen de entrada
     * @param out_image la imagen de salida
     * @param value el valor
     * @param rows el numero de filas
     * @param cols el numero de columnas
     **************************************************************************
     */
    public void edm(int[][] the_image, int[][] out_image,
            int value, int rows, int cols) {
        int a, b, count, i, j, k;
        for(i = 0; i < rows; i++) {
            for(j = 0; j < cols; j++) {
                out_image[i][j] = 0;
                }
            }
        /**
         ***************************
         * Loop over image array
         ***************************
         */
        System.out.printf("\n");
        for(i = 0; i < rows; i++) {
            if((i % 10) == 0) {
                System.out.printf("%3d", i);
                }
            for(j = 0; j < cols; j++) {
                if(the_image[i][j] == value) {
                    out_image[i][j] = distanceEight(the_image,
                            i, j,
                            value,
                            rows, cols);
                    }
                } /* ends loop over j */
            } /* ends loop over i */
    } /* ends edm */

    /**
     **************************************************************************
     *
     * distanceEight
     *
     * This function finds the distance from a pixel to the nearest zero pixel.
     * It search in all eight directions.
     *
     **************************************************************************
     * @param the_image la imagen
     * @param a la coordenada en y
     * @param b la coordenada en c
     * @param value el valor
     * @param rows el numero de filas
     * @param cols el numero de columnas
     * @return devuelve la distancia calculada
     **************************************************************************
     */
    public int distanceEight(int[][] the_image, int a, int b, int value, 
            int rows, int cols) {
        int i, j, measuring;
        int dist1 = 0,
                dist2 = 0,
                dist3 = 0,
                dist4 = 0,
                dist5 = 0,
                dist6 = 0,
                dist7 = 0,
                dist8 = 0,
                result = 0;
        /* straight up */
        measuring = 1;
        i = a;
        j = b;
        while(measuring==1) {
            i--;
            if(i >= 0) {
                if(the_image[i][j] == value) {
                    dist1++;
                    } 
                else {
                    measuring = 0;
                    }
                } 
            else {
                measuring = 0;
                }
            } /* ends while measuring */

        result = dist1;
        /* straight down */
        measuring = 1;
        i = a;
        j = b;
        while(measuring==1) {
            i++;
            if(i <= rows - 1) {
                if(the_image[i][j] == value) {
                    dist2++;
                    } 
                else {
                    measuring = 0;
                    }
                } 
            else {
                measuring = 0;
                }
            } /* ends while measuring */

        if(dist2 <= result) {
            result = dist2;
            }
        /* straight left */
        
        measuring = 1;
        i = a;
        j = b;
        while(measuring==1) {
            j--;
            if(j >= 0) {
                if(the_image[i][j] == value) {
                    dist3++;
                    } 
                else {
                    measuring = 0;
                    }
                } 
            else {
                measuring = 0;
                }
        } /* ends while measuring */

        if(dist3 <= result) {
            result = dist3;
            }
        /* straight right */
        measuring = 1;
        i = a;
        j = b;
        while(measuring==1) {
            j++;
            if(j <= cols - 1) {
                if(the_image[i][j] == value) {
                    dist4++;
                    } 
                else {
                    measuring = 0;
                    }
                } 
            else {
                measuring = 0;
                }
            } /* ends while measuring */

        if(dist4 <= result) {
            result = dist4;
            }
        /* left and up */
        measuring = 1;
        i = a;
        j = b;
        while(measuring==1) {
            j--;
            i--;
            if(j >= 0 && i >= 0) {
                if (the_image[i][j] == value) {
                    dist5++;
                    } 
                else {
                    measuring = 0;
                    }
                } 
            else {
                measuring = 0;
                }
            } /* ends while measuring */

        dist5 = (dist5 * 14) / 10;
        if(dist5 <= result) {
            result = dist5;
            }
        /* right and up */
        measuring = 1;
        i = a;
        j = b;
        while(measuring==1) {
            j++;
            i--;
            if(j <= cols - 1 && i >= 0) {
                if(the_image[i][j] == value) {
                    dist6++;
                    } 
                else {
                    measuring = 0;
                    }
                } 
            else {
                measuring = 0;
                }
            } /* ends while measuring */

        dist6 = (dist6 * 14) / 10;
        if(dist6 <= result) {
            result = dist6;
            }
        /* right and down */
        measuring = 1;
        i = a;
        j = b;
        while(measuring==1) {
            j++;
            i++;
            if(j <= cols - 1 && i <= rows - 1) {
                if(the_image[i][j] == value) {
                    dist7++;
                    } 
                else {
                    measuring = 0;
                    }
                } 
            else {
                measuring = 0;
                }
            } /* ends while measuring */

        dist7 = (dist7 * 14) / 10;
        if (dist7 <= result) {
            result = dist7;
        }
        /* left and down */
        measuring = 1;
        i = a;
        j = b;
        while(measuring==1) {
            j--;
            i++;
            if(j >= 0 && i <= rows - 1) {
                if(the_image[i][j] == value) {
                    dist8++;
                    } 
                else {
                    measuring = 0;
                    }
                } 
            else {
                measuring = 0;
                }
            } /* ends while measuring */

        dist8 = (dist8 * 14) / 10;
        if (dist8 <= result) {
            result = dist8;
        }
        return (result);
    } /* ends distanceEight */

    /**
     **************************************************************************
     *
     * mat
     *
     * This function finds the medial axis transform for objects in an image.
     * The mat are those points that are minimally distant to more than one
     * boundary point.
     *
     **************************************************************************
     * @param the_image la imagen de entrada
     * @param out_image la imagen de salida
     * @param value el valor
     * @param rows el numero de filas 
     * @param cols el numero de columnas
     **************************************************************************
     */
    public void mat(int[][] the_image, int[][] out_image,
            int value, int rows, int cols) {
        int a, b, count, i, j, k,
                length, width;
        for(i = 0; i < rows; i++) {
            for(j = 0; j < cols; j++) {
                out_image[i][j] = 0;
                }
            }
        /**
         * *************************
         * Loop over image array
         ***************************
         */
        System.out.printf("\n");
        for(i = 0; i < rows; i++) {
            if((i % 10) == 0) {
                System.out.printf("%3d", i);
                }
            for(j = 0; j < cols; j++) {
                if(the_image[i][j] == value) {
                    out_image[i][j] = matD(the_image,
                            i, j, value,
                            rows, cols);
                    }
                } /* ends loop over j */
            } /* ends loop over i */
    } /* ends mat */

    /**
     **************************************************************************
     *
     * matD
     * This function helps find the medial axis transform.
     *
     * This function measures the distances from the point to a zero pixel in
     * all eight directions. Look for the two shortest distances in the eight 
     * distances. If the two shortest distances are equal, then the point in 
     * question is minimally distant to more than one boundary point. 
     * Therefore, it is on the medial axis so return a value. Otherwise, return 
     * zero.
     *
     **************************************************************************
     * @param the_image la imagen
     * @param a la coordenada en y
     * @param b la coordenada en x
     * @param value el valor
     * @param rows el numero de filas
     * @param cols el numero de columnas
     * @return devuelve la distancia
     **************************************************************************
     */
    public int matD(int[][] the_image, int a, int b, int value, int rows,
            int cols) {
        int i, j, measuring;
        int dist1 = 0,
                dist2 = 0,
                dist3 = 0,
                dist4 = 0,
                dist5 = 0,
                dist6 = 0,
                dist7 = 0,
                dist8 = 0,
                min1 = GRAY_LEVELS,
                min2 = GRAY_LEVELS,
                result = 0;
        /* straight up */
        measuring = 1;
        i = a;
        j = b;
        while(measuring==1) {
            i--;
            if(i >= 0) {
                if(the_image[i][j] == value) {
                    dist1++;
                    } 
                else {
                    measuring = 0;
                    }
                } 
            else {
                measuring = 0;
                }
            } /* ends while measuring */

        result = dist1;
        min1 = dist1;
        /* straight down */
        measuring = 1;
        i = a;
        j = b;
        while(measuring==1) {
            i++;
            if(i <= rows - 1) {
                if(the_image[i][j] == value) {
                    dist2++;
                    } 
                else {
                    measuring = 0;
                    }
                } 
            else {
                measuring = 0;
                }
            } /* ends while measuring */

        if(dist2 <= result) {
            result = dist2;
            }
        if(dist2 < min1) {
            min2 = min1;
            min1 = dist2;
            } 
        else 
        if (dist2 < min2) {
            min2 = dist2;
            }
        /* straight left */
        measuring = 1;
        i = a;
        j = b;
        while(measuring==1) {
            j--;
            if(j >= 0) {
                if(the_image[i][j] == value) {
                    dist3++;
                    } 
                else {
                    measuring = 0;
                    }
                } 
            else {
                measuring = 0;
                }
            } /* ends while measuring */

        if(dist3 <= result) {
            result = dist3;
            }
        if(dist3 < min1) {
            min2 = min1;
            min1 = dist3;
            } 
        else 
        if (dist3 < min2) {
            min2 = dist3;
            }
        /* straight right */
        measuring = 1;
        i = a;
        j = b;
        while(measuring==1) {
            j++;
            if(j <= cols - 1) {
                if(the_image[i][j] == value) {
                    dist4++;
                    } 
                else {
                    measuring = 0;
                    }
                } 
            else {
                measuring = 0;
                }
            } /* ends while measuring */

        if(dist4 <= result) {
            result = dist4;
            }
        if(dist4 < min1) {
            min2 = min1;
            min1 = dist4;
            } 
        else 
        if(dist4 < min2) {
            min2 = dist4;
            }
        /* left and up */
        measuring = 1;
        i = a;
        j = b;
        while(measuring==1) {
            j--;
            i--;
            if(j >= 0 && i >= 0) {
                if(the_image[i][j] == value) {
                    dist5++;
                    } 
                else {
                    measuring = 0;
                    }
                }
            else {
                measuring = 0;
                }
            } /* ends while measuring */

        dist5 = ((dist5 * 14) + 7) / 10;
        if(dist5 <= result) {
            result = dist5;
            }
        if(dist5 < min1) {
            min2 = min1;
            min1 = dist5;
            } 
        else 
        if (dist5 < min2) {
            min2 = dist5;
            }
        /* right and up */
        measuring = 1;
        i = a;
        j = b;
        while(measuring==1) {
            j++;
            i--;
            if(j <= cols - 1 && i >= 0) {
                if(the_image[i][j] == value) {
                    dist6++;
                    } 
                else {
                    measuring = 0;
                    }
                } 
            else {
                measuring = 0;
                }
            } /* ends while measuring */

        dist6 = ((dist6 * 14) + 7) / 10;
        if(dist6 <= result) {
            result = dist6;
            }
        if(dist6 < min1) {
            min2 = min1;
            min1 = dist6;
            } 
        else 
        if (dist6 < min2) {
            min2 = dist6;
            }
        /* right and down */
        measuring = 1;
        i = a;
        j = b;
        while(measuring==1) {
            j++;
            i++;
            if(j <= cols - 1 && i <= rows - 1) {
                if(the_image[i][j] == value) {
                    dist7++;
                    } 
                else {
                    measuring = 0;
                    }
                } 
            else {
                measuring = 0;
                }
            } /* ends while measuring */

        dist7 = ((dist7 * 14) + 7) / 10;
        if(dist7 <= result) {
            result = dist7;
            }
        if(dist7 < min1) {
            min2 = min1;
            min1 = dist7;
            } 
        else 
        if (dist7 < min2) {
            min2 = dist7;
            }
        /* left and down */
        measuring = 1;
        i = a;
        j = b;
        while(measuring==1) {
            j--;
            i++;
            if(j >= 0 && i <= rows - 1) {
                if(the_image[i][j] == value) {
                    dist8++;
                    } 
                else {
                    measuring = 0;
                    }
                } 
            else {
                measuring = 0;
                }
            } /* ends while measuring */

        dist8 = ((dist8 * 14) + 7) / 10;
        if(dist8 <= result) {
            result = dist8;
            }
        if(dist8 < min1) {
            min2 = min1;
            min1 = dist8;
            } 
        else 
        if(dist8 < min2) {
            min2 = dist8;
            }
        if(min1 == min2) {
            result = value;
            } 
        else {
            result = 0;
            }
        if(min1 == 0) {
            result = 0;
            }
        return result;
    } /* ends matD */
}
