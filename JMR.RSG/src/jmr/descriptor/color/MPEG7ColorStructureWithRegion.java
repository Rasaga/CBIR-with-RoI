/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.descriptor.color;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javafx.util.Pair;
import jmr.descriptor.Comparator;
import jmr.descriptor.MediaDescriptor;
import static jmr.descriptor.color.MPEG7ColorStructure.DEFAULT_NUM_LEVELS;
import jmr.media.JMRExtendedBufferedImage;
import jmr.roi.Region;

/**
 * Clase para la creación de descriptores MPEG-7 de Color Estructurado enfocado
 * a regiones. Dichos descriptores heredan de cómo se crean para una imagen sola.
 * La diferencia es que ahora se harán teniendo en cuenta regiones, por los que
 * los métodos deberán adaptarse a dichos cambios.
 * 
 * @author Ramón Sánchez García (chentaco@correo.ugr.es)
 */


public class MPEG7ColorStructureWithRegion extends MPEG7ColorStructure{

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
     * Constructor por defectro para el descriptor MPEG-7 de color
     * estructurado. Utiliza solamente una imagen para ser inicializado.
     * 
     * @param image Imagen utilizada para crear este descriptor.
     */

    public MPEG7ColorStructureWithRegion(BufferedImage image) {
        super(image);
        Region region = new Region(image);
        this.bb=region.getBounds();
        this.centroid=region.getCentroid();
        this.centroidRelative= new Pair(this.centroid.getX()/image.getWidth(),this.centroid.getY()/image.getHeight());
        init(region, DEFAULT_NUM_LEVELS);
        this.setComparator(new DefaultComparator());
    }
    
    /**
     * Constructor del descriptor MPEG-7 estructurado utilizando una region.
     * 
     * @param region La region utilizada para la creación del descriptor.
     */
    
    public MPEG7ColorStructureWithRegion(Region region) {
        super(region.getSource());
        this.bb=region.getBounds();
        this.centroid=region.getCentroid();
        this.centroidRelative= new Pair(this.centroid.getX()/region.getSource().getWidth(),this.centroid.getY()/region.getSource().getHeight());
        init(region, DEFAULT_NUM_LEVELS);
        this.setComparator(new DefaultComparator());
    }
    
    /**
     * Sobrecarga del método de inicialización para descriptores MPEG-7
     * de color estructurado para imagen. Haciendo esto evito que al llamarse
     * utilice el metodo de inicialización del descriptor padre.
     * @param image Imagen utilizad para la creación del descriptor
     * @param qLevels niveles de quantización para la creación del histograma
     */
    
    @Override
    public void init(BufferedImage image, int qLevels) {
        bb = null;
        centroid = null;
    }
    
    /**
     * Metodo de inicialización del descriptor utilizando una region. Aunque es
     * una mala práctica, se encarga de cuantificar TODA la imagen a partir de los
     * niveles de cuantificación que se pasen por parámetro y una vez que se han
     * obtenido los valores cuantificados de toda esta imagen, se procede a obtener
     * el histograma pero solo de la region.
     * 
     * @param region La region utilizada para el cálculo del descriptor
     * @param qLevels el nivel de quantizacion
     */
    
    public void init(Region region, int qLevels){
        // The MPEG7ColorStructure need a JMRExtendedBufferedImage to be calculated
        JMRExtendedBufferedImage JMRimage = null;
        try {
            JMRimage = (JMRExtendedBufferedImage) region.getSource();
        } catch (ClassCastException ex) {
            JMRimage = new JMRExtendedBufferedImage(region.getSource());
        }
        // The color space and the image model must been the suitable ones.
        if (!checkImage(JMRimage)) {
            JMRimage = convertImg(JMRimage);
        }
        this.setLevels(qLevels);
        byte[][] imQ = quantHMMDImage(JMRimage);
        float[] histo = structuredHisto(imQ, region);
        this.histo = reQuantization(histo);        
    }
    
    /**
     * Metodo para la obtención del histograma estructurado. Como dice
     * la metodología para obtener el descriptor de MPEG-7 color estructurado, 
     * he de realizar la búsqueda usando un marco. La diferentecia es que ahora
     * el marco no se hace usando toda la imagen, sino la zona de la región.
     * Por ello, necesito la región para obtener el histograma estructurado.
     * 
     * @param imQ los valores obtenido tras cuantizar la imange
     * @param region La region sobre la que se calculará el histograma
     * @return histograma estructurado
     */
     
     private float[] structuredHisto(byte[][] imQ, Region region) {
        
        int m = 0;
        int wImg = (int) region.getWidth();
        int hImg = (int) region.getHeight();
//        int wImg = region.getSource().getWidth();
//        int hImg = region.getSource().getHeight();
//        int wImg = (int) region.getBounds().getHeight();
//        int hImg = (int) region.getBounds().getWidth();
//        System.out.println("Ancho (columnas): "+wImg+", Height (filas): "+hImg+", X: "+region.getBounds().getX()+", Y: "+region.getBounds().getY());
//        System.out.println("Filas: " + imQ.length + " Columnas: " + imQ[0].length);

        double hw = Math.sqrt(hImg * wImg);
        double p = Math.floor(Math.log(hw) / Math.log(2) - 7.5); //Formula by Manjunath2002
//        System.out.println(p);
        if (p < 0) {
            p = 0; //Minimum size of the division factor to have K=1
        }
        double K = Math.pow(2, p); //Determine the space between each structuring element
        double E = 8 * K; //Determine the size of the moving windows
        // Setting the local temporary and the CDS histograms
        float histo[] = new float[qLevels]; // CSD histograms
        int winHisto[] = new int[qLevels]; // local histo for a specific windows
        for (int i = 0; i < qLevels; i++) {
            histo[i] = 0.0f;
        }
        
//        boolean matrix[][] = new boolean[(int)region.getHeight()][(int)region.getWidth()];
//        for (int f = 0; f < (int)region.getHeight(); f ++) {
//            for (int c = 0; c < (int)region.getWidth(); c ++) {
//                if(region.getShape().contains(region.getLocation().x+c,region.getLocation().y+f)){
//                   matrix[f][c] = true;  
//                }else{
//                   matrix[f][c] = false; 
//                }
//            }
//        }
        
//        for (int y = 0; y < hImg - E; y += K) {
        for (int y = (int) region.getBounds().getY(); y < (region.getBounds().getY()+hImg) - E; y += K) {
//            System.out.println(y);
//            for (int x = 0; x < wImg - E; x += K) {
            for (int x = (int) region.getBounds().getX(); x < (region.getBounds().getX()+wImg) - E; x += K) {
                // Reinitialize the local windows histogram t[m]
                for (m = 0; m < qLevels; m++) {
                    winHisto[m] = 0;
                }
                for (int yy = y; yy < y + E; yy += K) {
                    for (int xx = x; xx < x + E; xx += K) {
                        if(region.getShape().contains(xx,yy)){ // Coodinates (x,y)
//                        if(matrix[yy-region.getLocation().y][xx-region.getLocation().x]==true){
//                            System.out.print(xx+" "+yy+" ");
//                            System.out.println("Y: " + y + ", X: " + x + ", YY: " + yy + ", XX: " + xx);
                            //Obtain the pixel values of the HMMD quantized image
                            m = (int) (imQ[yy][xx] & 0x000000FF); //WARNING imQ is signed byte
                            //pixel value correspond to the bin value in qLevels CSD Histo
                            winHisto[m]++;
                        }
                    }
//                   System.out.println();
                } //End of local histogram for a local windows
                // Increment the color structure histogram for each color present in the structuring element
                for (m = 0; m < qLevels; m++) {
                    if (winHisto[m] > 0) {
                        histo[m]++;
                    }
                }
            }
        }
        //Normalize the histograms by the number of times the windows was shift
        int winShift_X = ((wImg - 1) - (int) E + (int) K);
        int winShift_Y = ((hImg - 1) - (int) E + (int) K);
        int S = (winShift_X / (int) K) * (winShift_Y / (int) K);
        for (m = 0; m < qLevels; m++) {
            histo[m] = histo[m] / S;
        }
        return histo;
    }
     
    /**
     * Metodo para obtener el centroide de la region asociada a esta region.
     *
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
     *
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
     * Clase interna funcional para definir un comparador por defecto para este
     * descriptor.
     */

    static class DefaultComparator implements Comparator<MPEG7ColorStructureWithRegion, Double> {
        /**
         * Metodo apply para comparar dos descriptores MPEG-7 de color estructurado
         * enfocados a regiones.
         *
         * @param t Primer descriptor
         * @param u Segundo descriptor
         * @return Distancia entre los dos descriptores como valor double.
         */
        @Override
        public Double apply(MPEG7ColorStructureWithRegion t, MPEG7ColorStructureWithRegion u) {
            int[] f1, f2;
            if (t.histo == null || u.histo == null) {
                return (null);
            }
            if (t.qLevels == u.qLevels) {
                f1 = t.histo;
                f2 = u.histo;
            } else if (t.qLevels < u.qLevels) {
                f1 = t.histo;
                f2 = resizeCSD(u, t.qLevels); // peta
            } else {
                f1 = resizeCSD(t, u.qLevels); // peta
                f2 = u.histo;
            }
            Double distance = 0.0;
            for (int i = 0; i < f1.length; i++) {
                distance += Math.abs(f1[i] - f2[i]);
            }
            distance /= (256 * f1.length); //Normalization

            return distance;
        }
    }    
}
