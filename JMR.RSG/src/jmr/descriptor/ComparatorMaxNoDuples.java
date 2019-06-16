/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.descriptor;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import javafx.util.Pair;
import javax.imageio.ImageIO;
import jmr.db.ListDBSegmentedSimple;
import jmr.descriptor.color.MPEG7ColorStructureWithRegion;
import jmr.descriptor.color.MPEG7ScalableColorWithRegion;
import jmr.descriptor.color.MyDescriptor;
import jmr.descriptor.color.SingleColorDescriptorWithRegion;

/**
 * Clase para la creación del comparador Maximo sin repeticiones.
 * 
 * @author Ramón Sánchez García (chentaco@correo.ugr.es)
 */
public class ComparatorMaxNoDuples implements Comparator<SegmentedDescriptor, Double> {
    
    boolean position;

    /**
     * Constructor de la clase de comparador maximo sin repeticiones.
     * @param position variable booleana para tomar en cuenta la posición
     * de la región o no.
     */    
    public ComparatorMaxNoDuples(boolean position){
        super();
        this.position = position;
    }

    /**
     * Sobercarga del metodo apply para la comparación entre dos 
     * descriptores segmentados en función del maximo sin repeticiones.
     * @param t Primer descriptor segmentado
     * @param u Segundo descriptor segmentado, con el que se compara el primero
     * @return valor double que representa la diferencia o distancia entre los
     * comparadores.
     */     
    @Override
    public Double apply(SegmentedDescriptor t, SegmentedDescriptor u) { 

        MediaDescriptor m1, m2;

        Double min;
        Double maxFinal = -1.0;
        int posicionEncontrada;
        
        // Copias de los array, debido a como funciona mi algoritmo, uso copias de ellos
        ArrayList<MediaDescriptor<BufferedImage>> clon1 = new ArrayList(t.getDescriptorList());
        ArrayList<MediaDescriptor<BufferedImage>> clon2 = new ArrayList(u.getDescriptorList());
        
        Double minAbsoluto = null;
        int r1 = -1;
        int r2 = -1;
        
        // 1º Usaré el bucle para buscar el mejor resultado en cada pasada hasta que los clon estén vacios
        while (!clon1.isEmpty() && !clon2.isEmpty()) {
            for (int i = 0; i < clon1.size(); i++) {
                min = null;
                posicionEncontrada = -1;
                for (int j = 0; j < clon2.size(); j++) {
                    try {
                        m1 = (MediaDescriptor) clon1.get(i);
                        m2 = (MediaDescriptor) clon2.get(j);
                        if (min == null || (Double) m1.compare(m2) < min) {
                            min = (Double) m1.compare(m2);
                            posicionEncontrada = j;

                            // Para tener en cuenta el centroide
                        if (position == true) {
                            Point2D p1 = null;
                            Point2D p2 = null;
                            Pair<Double, Double> par1 = null;
                            Pair<Double, Double> par2 = null;
                            if (m1 instanceof MPEG7ColorStructureWithRegion) {
                                p1 = ((MPEG7ColorStructureWithRegion) m1).getCentroid();
                                p2 = ((MPEG7ColorStructureWithRegion) m2).getCentroid();
                                par1 = ((MPEG7ColorStructureWithRegion) m1).getCentroidRelative();
                                par2 = ((MPEG7ColorStructureWithRegion) m2).getCentroidRelative();
                            } else if (m1 instanceof MPEG7ScalableColorWithRegion) {
                                p1 = ((MPEG7ScalableColorWithRegion) m1).getCentroid();
                                p2 = ((MPEG7ScalableColorWithRegion) m2).getCentroid();
                                par1 = ((MPEG7ScalableColorWithRegion) m1).getCentroidRelative();
                                par2 = ((MPEG7ScalableColorWithRegion) m2).getCentroidRelative();
                            } else if (m1 instanceof SingleColorDescriptorWithRegion) {
                                p1 = ((SingleColorDescriptorWithRegion) m1).getCentroid();
                                p2 = ((SingleColorDescriptorWithRegion) m2).getCentroid();
                                par1 = ((SingleColorDescriptorWithRegion) m1).getCentroidRelative();
                                par2 = ((SingleColorDescriptorWithRegion) m2).getCentroidRelative();
                            } else if (m1 instanceof MyDescriptor) {
                                p1 = ((MyDescriptor) m1).getCentroid();
                                p2 = ((MyDescriptor) m2).getCentroid();
                                par1 = ((MyDescriptor) m1).getCentroidRelative();
                                par2 = ((MyDescriptor) m2).getCentroidRelative();
                            }
                            if (p1 != null && p2 != null) {
//                                System.out.println("Punto 1: " + p1.toString() + " Punto 2: " + p2.toString());
                                BufferedImage img1 = (BufferedImage) m1.getSource();
                                BufferedImage img2 = (BufferedImage) m2.getSource();

                                double value;

                                if (img1 != null && img2 != null) {
                                    value = this.calculateEuclides(p1, p2, img1, img2);
                                    double norma = this.normalizarValor(value);
                                    min = min * norma;
                                } else {
                                    value = this.calculateEuclides(par1, par2);
                                    double norma = this.normalizarValor(value);
                                    min = min * norma;
                                }
                            }
                        }
                        }
                    } catch (ClassCastException e) {
                        throw new InvalidParameterException("The comparision between descriptors is not interpetrable as a double value.");
                    } catch (Exception e) {
                        throw new InvalidParameterException("The descriptors are not comparables.");
                    }
                }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // 2º. Compruebo que dicho valor sea el más pequeño de los encontrados
                if(minAbsoluto==null || min<minAbsoluto){
                    minAbsoluto = min;
                    r1 = i;
                    r2 = posicionEncontrada;
                }              
            }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // 3º. Quitamos la region minima encontrada
            clon1.remove(r1);
            clon2.remove(r2);
            // 4º. Compruebo que dicho valor sea mejor que el anterior
            if(minAbsoluto>maxFinal){
                maxFinal = minAbsoluto;
            }
            // 5º. Reseteo el minAbsoluto para buscar uno nuevo
            minAbsoluto = null;
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////            
        }

//        System.out.println(posicionesOcupadas.toString());
//        System.out.println(Arrays.toString(losMinimos));
//        System.out.println(porComprobar.toString());
//        System.out.println(pendientes.toString());
        return maxFinal;
    }
    
    
    
    /**
     * Cálculo de la distancia euclidea entre los centros de dos regiones.
     * 
     * @param p1 Centroide de la primera región.
     * @param p2 Centroide de la segunda region.
     * @param img1 Imagen asociada a la primera region.
     * @param img2 Imagen asociada a la segunda region.
     * 
     * @return La distancia euclida como valor double.
     */      
    private double calculateEuclides(Point2D p1, Point2D p2, BufferedImage img1, BufferedImage img2) {
        double result = Math.sqrt(Math.pow((p1.getX() / img1.getWidth() - p2.getX() / img2.getWidth()), 2)
                + Math.pow((p1.getY() / img1.getHeight() - p2.getY() / img2.getHeight()), 2));
        return result;
    }

    /**
     * Calculo de la distancia euclida entre los centros relativos de dos regiones.
     * @param p1 Par de valores que indican la posicion relativa del centro de la primer region.
     * @param p2 Par de valores que indican la posicion relativa del centro de la segunda region.
     * @return distancia euclida entre centros
     */     
    private double calculateEuclides(Pair<Double,Double> p1, Pair<Double,Double> p2){
        return (Math.sqrt(Math.pow(p1.getKey()-p2.getKey(),2)+Math.pow(p1.getValue()-p2.getValue(),2)));
    }

    /**
     * Se encarga de aplicar el valor calculado a una función normalizadora.
     * @param x El valor a normalizar
     * @return el valor normalizado
     */    
    private double normalizarValor(double x) {

        double Max = Math.sqrt(2);
        double alfa = 0.1;
        double beta = 0.5;

        double x1 = alfa * Max;
        double x2 = beta * Max;

        double y1 = 1.0;
        double y2 = 0.1;

        double y;

        if (x <= x1) {
            y = y1;
        } else if (x >= x2) {
            y = y2;
        } else {
            double m = (y2 - y1) / (x2 - x1);
            y = m * (x - x1) + y1;
        }

        return y;
    }
    
}
