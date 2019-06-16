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
import java.awt.image.Raster;
import java.util.Arrays;
import javafx.util.Pair;
import jmr.descriptor.Comparator;
import jmr.descriptor.MediaDescriptor;
import jmr.media.JMRExtendedBufferedImage;
import jmr.roi.Region;

/**
 * Clase para la creación de descriptores MPEG-7 de Color Escalable enfocado
 * a regiones. Dichos descriptores heredan de cómo se crean para una imagen sola.
 * La diferencia es que ahora se harán teniendo en cuenta regiones, por los que
 * los métodos deberán adaptarse a dichos cambios.
 * 
 * @author Ramón Sánchez García (chentaco@correo.ugr.es)
 */
public class MPEG7ScalableColorWithRegion extends MPEG7ScalableColor{
    
    /**
     * BoundingBox of the RegionComun.
     */
    private Rectangle2D bb;

    /**
     * Centroid of the RegionComun.
     */
    
    private Point2D centroid;
    private Pair<Double,Double> centroidRelative;
    
    /**
     * A comparator for the descriptor. It uses a standard functional interface, 
     * allowing lambda expressions 
     */
    protected Comparator comparator = null;
    
    /**
     * Constructor por defecto. Creará un descriptor de este tipo a partir de 
     * una sola imagen.
     * @param image Imagen que se utiliza para crear el descriptor.
     */
 
    public MPEG7ScalableColorWithRegion(BufferedImage image) {
        super(image);
        Region region = new Region(image);
        this.bb = region.getBounds();
        this.centroid = region.getCentroid();
        this.centroidRelative= new Pair(this.centroid.getX()/image.getWidth(),this.centroid.getY()/image.getHeight());
        initHistogram(region);
        this.setComparator(new DefaultComparator());
    }    
    
    /**
     * Constructor del descriptor MPEG-7 de color escalable a partir de una region.
     * 
     * @param region Region utilizada para la creación del descriptor.
     */
    
    public MPEG7ScalableColorWithRegion(Region region) {
        super(region.getSource());
        this.bb = region.getBounds();
        this.centroid = region.getCentroid();
        this.centroidRelative= new Pair(this.centroid.getX()/region.getSource().getWidth(),this.centroid.getY()/region.getSource().getHeight());
        initHistogram(region);
        this.setComparator(new DefaultComparator());
    }
    
    /**
     * Constructor del descriptor escalable MPEG-7 a partir de regiones y 
     * variando sus coeficientes
     * 
     * @param region region que se usará para crear el descriptor
     * @param numCoeffients nuevo numero de coeficientes para los cálculos
     * @param numBitplanes numero de bit para los calculos
     */
    
    public MPEG7ScalableColorWithRegion(Region region, int numCoeffients, int numBitplanes) {
        super(region.getSource(),numCoeffients,numBitplanes);
        this.bb = region.getBounds();
        this.centroid = region.getCentroid();
        this.centroidRelative= new Pair(this.centroid.getX()/region.getSource().getWidth(),this.centroid.getY()/region.getSource().getHeight());
        initHistogram(region);
        this.setComparator(new DefaultComparator());
    }
    
    /**
     * Constructor del descriptor escalable MPEG-7 a partir de una imagen y 
     * variando sus coeficientes.
     * 
     * @param image Imagen que se usará para crear el descriptor
     * @param numCoeffients nuevo numero de coeficientes para los cálculos
     * @param numBitplanes numero de bit para los calculos
     */

    public MPEG7ScalableColorWithRegion(BufferedImage image, int numCoeffients, int numBitplanes) {
        super(image,numCoeffients,numBitplanes);
        init(image);
        this.setComparator(new DefaultComparator());
    }
    
    /**
     * Sobrecarga para el método de inicialización del descriptor 
     * enfocado a una sola imagen. Haciendo esto evito que al llamarse
     * utilice el metodo de inicialización del descriptor padre.
     * 
     * @param image imagen usada de entrada
     */
    @Override
    public void init(BufferedImage image){
        bb = null;
        centroid = null;        
    }

    /**
     * Inicializa el histogama asociado a este descriptor.
     *
     * @param region la region de la imagen
     */
    private void initHistogram(Region region) {
        JMRExtendedBufferedImage JMRimage = null;
        try {
            JMRimage = (JMRExtendedBufferedImage) region.getSource();
        } catch (ClassCastException ex) {
            JMRimage = new JMRExtendedBufferedImage(region.getSource());
        }
        if (!checkImage(JMRimage)) {
            JMRimage = convertImg(JMRimage);
        }
        
        Raster imRst = JMRimage.getRaster();
        float[] pixel = new float[3];
        int i, j, k;
        int[][][] histoMx = new int[H_BINS][S_BINS][V_BINS]; // By default, filled with 0
        Region.miIterador it = region.new miIterador();
//        Point2D p = it.next();
        Color p;
        while (it.hasNext()) {
            p = it.next();
            imRst.getPixel((int) it.getX(), (int) it.getY(), pixel); // ???????????????
            i = (int) (pixel[0] / H_SCALE); //H in bin levels
            j = (int) (pixel[1] / S_SCALE); //S in bin levels
            k = (int) (pixel[2] / V_SCALE); //V in bin levels
            histoMx[i][j][k]++;     
        }
        int[] histoVec = histoMx2histoVec(histoMx);
        QuantizeHistogram(histoVec);
        this.histoHaar = HaarTransform(histoVec);
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
     * @return par de valores referente al centro relativo de la region segun
     * el tamaño de la imagen
     */
    
    public Pair<Double,Double> getCentroidRelative(){
        return this.centroidRelative;
    }

//    @Override
//    public String toString() {
//        return "MPEG7ScalableColorRoI: " + Arrays.toString(this.histoHaar);
//    }
    
    /**
     * Cambia el tipo de comparador utilizado.
     * 
     * @param comparator el nuevo comparador que se utilizará 
     */
    final public void setComparator(Comparator comparator){
        this.comparator = comparator;
    }
    
    /**
     * Método para permitir la comparación entre este tipo de descriptor.
     * @param desc Descriptor con el que se comparará este
     * @return valor doble obtenido tras la comparación de descriptores.
     */
    @Override
    public Double compare(MediaDescriptor desc){
        if (comparator == null) {
            throw new NullPointerException("Comparator is null.");
        }
        return (Double) comparator.apply(this, desc);
    }
    
    /**
     * Clase interna funcional para definir un comparador por defecto.
     */

    static class DefaultComparator implements Comparator<MPEG7ScalableColorWithRegion, Double> {
        /**
         * Metodo apply para comparar dos descriptores MPEG-7 de color escalable
         * enfocados a regiones.
         * @param t Primer descriptor
         * @param u Segundo descriptor
         * @return Distancia entre los dos descriptores como valor double.
         */
        
        @Override
        public Double apply(MPEG7ScalableColorWithRegion t, MPEG7ScalableColorWithRegion u) {
        if (u.nofBitPlanesDiscarded != t.nofBitPlanesDiscarded || 
            u.nofCoefficients != t.nofCoefficients) {
            return null;
        }        
        if (u.histoHaar == null || t.histoHaar == null) {
            return null;
        }
        double diffsum = 0;
        for (int i = 0; i < t.nofCoefficients; i++) {
            diffsum += Math.abs(t.histoHaar[i] - u.histoHaar[i]);
        }
        return diffsum;
        }    
    } 
}
