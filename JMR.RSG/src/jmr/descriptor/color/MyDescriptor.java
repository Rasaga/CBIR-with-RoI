/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.descriptor.color;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Iterator;
import javafx.util.Pair;
import jmr.descriptor.Comparator;
import jmr.descriptor.MediaDescriptorAdapter;
import jmr.roi.Region;

/**
 * Descriptor con el filtro de luminosidad media. 
 * Filtro para calcular la luminosidad media de una imagen, para ello
 * calculare la luminosidad de cada pixeles y haré una media
 * La formula de la luminosidad es 0.21 R + 0.72 G + 0.07 B.
 * 
 * @author Ramón Sánchez García (chentaco@correo.ugr.es)
 */
public class MyDescriptor extends MediaDescriptorAdapter<BufferedImage> implements Serializable{
    
    /**
     * BoundingBox of the RegionComun.
     */
    private Rectangle2D bb;

    /**
     * Centroid of the RegionComun.
     */
    
    private Point2D centroid;
    
    /**
     * Centroide relativo.
     */
    private Pair<Double,Double> centroidRelative;   
    
    /**
    * Valor double que representa el porcentaje de luminosidad media.
    */
    
    private Double meanValor;
    
    protected Comparator comparator = null;
    
    /**
     * Constructor del descriptor a partir de una sola imagen.
     * 
     * @param image imagen que se usará para la construcción del descriptor.
     */
    public MyDescriptor(BufferedImage image) {
        super(image, new DefaultComparator()); //Implicit call to init
        Region region = new Region(image);
        this.bb=region.getBounds();
        this.centroid=region.getCentroid();
        this.centroidRelative= new Pair(this.centroid.getX()/image.getWidth(),this.centroid.getY()/image.getHeight());
        this.meanValor = meanLuminosity(region);
    }
    
    /**
     * Constructor del descriptor de luminosidad media a partir de una region.
     * 
     * @param region utilizada para la construccion del descriptor
     */
    
    public MyDescriptor(Region region) {
        super(region.getSource(), new DefaultComparator()); //Implicit call to init
        this.bb=region.getBounds();
        this.centroid=region.getCentroid();
        this.centroidRelative= new Pair(this.centroid.getX()/region.getSource().getWidth(),this.centroid.getY()/region.getSource().getHeight());
        this.meanValor = meanLuminosity(region);
    } 
    
//    public MyDescriptor(BufferedImage image, RegionComun region){
//        this(image);
//        this.bb=region.getBounds();
//        this.centroid=region.calculateCentroid();
//        this.meanValor = meanLuminosity(image,region);
//    }
  
    /**
     * Metodo de inicialización a partir de una imagen.
     * 
     * @param image Imagen utilizada para la inicializacion.
     */
    @Override
    public void init(BufferedImage image){
        //region = null;
        bb = null;
        centroid = null;
    }
    
    /**
     * Metodo para obtener el valor medio.
     * 
     * @return valor medio
     */

    public Double getMeanValor(){
        return meanValor;
    } 
    
    /**
     * Método para calcular la luminosidad media de una region.
     * 
     * @param region a la que se le calculará la luminosidad media
     * @return el valor de la luminosidad media.
     */
    
    private double meanLuminosity(Region region) {
        double mean = 0;
        double imageSize;

        imageSize = 0;
        Iterator<Color> it = region.iteradorPersonalizado();
        Color color;
        while (it.hasNext()) {
            color = it.next();
            mean += color.getRed() * 0.21 + color.getGreen() * 0.72 + color.getBlue() * 0.07;
            imageSize++;
        }
        mean /= imageSize;
        return mean;
    }
    
    /**
     * Devuelve la posición del centroide en el plano
     * 
     * @return punto que indica el centroide
     */
    public Point2D getCentroid(){
        return this.centroid;
    }
    
    /**
     * Devuelve el centroide relativo en función de la imagen.
     * 
     * @return par de valores que indican el centroide relativo.
     */
    public Pair<Double,Double> getCentroidRelative(){
        return this.centroidRelative;
    }
    
    /**
     * Sobrecarga del metodo toString para obtener una cadena de texto el valor
     * de luminosidad media
     * 
     * @return cadena de texto con dicha información
     */

    @Override
    public String toString(){
        return "MyDescriptor (MeanLuminosity): [" + this.meanValor +"]";
    }
    
    /**
     * Clase interna funcional implementando el comparador por defecto entre 
     * descriptores de luminosidad media.
     */
    static class DefaultComparator implements Comparator<MyDescriptor, Double> {
        /**
         * Metodo apply para la comparación entre descriptores de luminosidad media.
         * @param t Primer descriptor
         * @param u Segundo descriptor
         * @return diferencia entre distancias
         */
        @Override
        public Double apply(MyDescriptor t, MyDescriptor u) {
            // TO DO?
            return Math.abs(t.getMeanValor() - u.getMeanValor());
        }    
    }
    
}
