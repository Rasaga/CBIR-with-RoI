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
import java.util.Iterator;
import javafx.util.Pair;
import jmr.descriptor.Comparator;
import jmr.roi.Region;

/**
 * Descriptor del color medio enfocado a regiones.
 * 
 * @author Ramón Sánchez García.
 */
public class SingleColorDescriptorWithRegion extends SingleColorDescriptor {
    
    /**
     * In the heredable class, I will save the region, and inizialize it in the init.
     */
    //RegionComun region;
    
    /**
     * BoundingBox of the RegionComun.
     */
    private Rectangle2D bb;

    /**
     * Centroid of the RegionComun.
     */
    
    private Point2D centroid;
    private Pair<Double,Double> centroidRelative;
    
//    public SingleColorDescriptorWithRegion(BufferedImage image, Region region){
//        super(image);
    // Ver si ambas imagenes son iguales
//    }
    
    /**
     * Constructor por defecto del descriptor de color medio.
     * 
     * @param image Imagen con la que se creará el descriptor
     */
    public SingleColorDescriptorWithRegion(BufferedImage image) {
        super(image);
        Region region = new Region(image);
        this.color = mean(region);
        this.bb = region.getBounds();
        this.centroid = region.getCentroid();
        this.centroidRelative= new Pair(this.centroid.getX()/image.getWidth(),this.centroid.getY()/image.getHeight());
    }
    
    /**
     * Constructor del descriptor de color medio a partir de una region
     * 
     * @param region mi region utilizada para la creación del descriptor
     */
    public SingleColorDescriptorWithRegion(Region region){
        super(region.getSource());
        this.color = mean(region);
        this.bb = region.getBounds();
        this.centroid = region.getCentroid();
//        System.out.println(centroid);
        this.centroidRelative= new Pair(this.centroid.getX()/region.getSource().getWidth(),this.centroid.getY()/region.getSource().getHeight());
    }
    
    /**
     * Metodo de inicialización para una imagen
     * 
     * @param image la imagen utilizada para la inicializacion
     */
    @Override
    public void init(BufferedImage image){
        //region = null;
        bb = null;
        centroid = null;
    }
    
    /**
     * Metodo para calcular el color medio.
     * 
     * @param region se utilizará para calcular el color medio a partir de ella.
     * @return color medio de la region
     */
    private Color mean(Region region){
        Color pixelColor;
        float mean[] = {0.0f, 0.0f, 0.0f}; //RGB
        double imageSize = 0;
        Iterator<Color> it = region.iteradorPersonalizado();

           while (it.hasNext()) {         
                pixelColor = it.next();
                mean[0] += pixelColor.getRed();
                mean[1] += pixelColor.getGreen();
                mean[2] += pixelColor.getBlue();
                imageSize++;
            }
        mean[0] /= imageSize;
        mean[1] /= imageSize;
        mean[2] /= imageSize;
        
        return new Color((int) mean[0], (int) mean[1], (int) mean[2]);
    }
    
    /**
     * Metodo para obtener el centroide de la region asociada a esta region.
     * @return el punto referente al centro
     */
    public Point2D getCentroid(){
        return this.centroid;
    }
    
    /**
     * Metodo para obtener el centro relativo.
     * @return par de valores referente al centro de la figura
     */
    
    public Pair<Double,Double> getCentroidRelative(){
        return this.centroidRelative;
    }
    
    /**
     * Clase interna funcional sore el comparador entre descriptores de color.
     */
    
    static class DefaultComparator implements Comparator<SingleColorDescriptorWithRegion, Double> {
        /**
         * Devuelve la distancia entre los dos descriptores de color medio.
         * @param t Primero descriptor
         * @param u Segundo descriptor
         * @return la distancia calculada entre descriptores
         */
        @Override
        public Double apply(SingleColorDescriptorWithRegion t, SingleColorDescriptorWithRegion u) {
            Color c1 = t.color, c2 = u.color;
            double rDif = Math.pow(c1.getRed()-c2.getRed(),2);
            double gDif = Math.pow(c1.getGreen()-c2.getGreen(),2);
            double bDif = Math.pow(c1.getBlue()-c2.getBlue(),2);
            return Math.sqrt(rDif+gDif+bDif);
        }    
    }
}
