/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.descriptor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Objects;
import jmr.roi.Region;

/**
 * Clase que define el conjunto de descriptores de una imagen con regiones.
 * Siguienteo la metodología de Media Descriptor Adapter, necesito alguna forma
 * de agrupar y/u obtener todos los descriptores de una imagen, enfocado a
 * regiones.
 * 
 * @author Ramón Sánchez García (chentaco@correo.ugr.es)
 */
public class SegmentedDescriptor<T> extends MediaDescriptorAdapter<T> implements Serializable {

    /** 
     * Lis of Regions
     */
    //private ArrayList<MediaDescriptor<BufferedImage>> regiones = null; yo pasaré siempre las regiones, preguntar si vednria mejor guardarlas
    
    /**
     * List of descriptors
     */
    private ArrayList<MediaDescriptor<T>> descriptors;
    
    /**
     * The descriptor class for each tile
     */
    private Class<? extends MediaDescriptor> regionDescriptorClass;
    
    /**
     * Constructor por defecto de un segmented descrptor. No tiene ningun
     * tipo de descriptor a usar asociado, solo la imagen.
     * 
     * @param image La image que compondrá este descriptor.
     */
    public SegmentedDescriptor(BufferedImage image){
        super((T)image, new DefaultComparator());
        this.regionDescriptorClass = null;
    }
    
    /**
     * Constructor de creación de esta clase. Utiliza una o varias regiones
     * asociadas a una misma imagen y una clase descriptorea.
     * 
     * @param regionDescriptorClass clase que define el tipo de descriptor a usar
     * @param region region o regiones a utilizar
     */
    
    public SegmentedDescriptor(Class<? extends MediaDescriptor> regionDescriptorClass, Region...region){
        // Comprobamos que todas las regiones tienen la misma imagen, y si es asi, trabajamos
        // lo hago en el set
        super((T)region[0].getSource(), new DefaultComparator());
        this.regionDescriptorClass = regionDescriptorClass;
        this.setRegionDescriptors(regionDescriptorClass,region);
    }
    
    // segmenteddescriptor vacio,o es decir, solo con region?
    // Los siguientes constructores son para pruebas. ////////////////////////
    
    
    public SegmentedDescriptor(BufferedImage image, Class<? extends MediaDescriptor> regionDescriptorClass, Region...region){
        super((T)image, new DefaultComparator());
        this.regionDescriptorClass = regionDescriptorClass;
        this.setRegionDescriptors(regionDescriptorClass,region);
    }
    
    // Cuidado con este, probablemente a eliminar
    public SegmentedDescriptor(){
        super(null, new DefaultComparator());
        this.regionDescriptorClass = null;
    }
    
    //////////////////////////////////////////////////////////////////////////
    
    /**
     * Sobrecarga del metodo init de la clase padre. Evita que se inicialice
     * solo para un tipo de media.
     * 
     * @param media El tipo de elemento sobre el que se usará el descriptor. 
     */
    @Override
    public void init(T media) {
        descriptors = new ArrayList<>();
    }
    
    /**
     * Crea la lista de descriptores, calculando cada uno de ellos a partir
     * de cada reigon.
     * @param descriptorClass la clase referente al tipo de descriptor
     * @param region las regiones que utilizaré para el cálculo de descriptores
     */
    
    private void setRegionDescriptors(Class descriptorClass, Region... region) {
        MediaDescriptor descriptor;
        if (!descriptors.isEmpty()) {
            descriptors.clear();
        }
        if (descriptorClass != null) {
            if (region.length != 0) {
                for (int i = 0; i < region.length; i++) {
                    if (Objects.equals(region[i].getSource(), source)) {
                        descriptor = MediaDescriptorFactory.getInstance(descriptorClass, region[i]);
                        descriptors.add(descriptor);
                    }
                }
            } else {
                descriptor = MediaDescriptorFactory.getInstance(descriptorClass, source);
                descriptors.add(descriptor);
            }
        }
    }
    
    /**
     * Devuelve la clase referente al tipo de descriptor
     * 
     * @return el descriptor de region
     */
    public Class getTileDescriptorClass(){
        return this.regionDescriptorClass;
    }
    
    /**
     * Devuelve un listado de los descriptores calculados.
     * 
     * @return lista de descriptores calculados 
     */
    
    public ArrayList<MediaDescriptor<T>> getDescriptorList(){
        return this.descriptors;
    }
    
    /**
     * Devuelve el tamaño de la lista de descriptores.
     * 
     * @return Valor del tamaño de la lista de descriptores. 
     */
    public int size() {
        return descriptors.size();
    }
    
    /**
     * Devuelve el descriptor de la lista determinado por su
     * posición en esta.
     * @param index la posición del descriptor
     * @return el descriptor en esa posición
     */
    
    public MediaDescriptor<T> get(int index) {
        return descriptors.get(index);
    }
    
    /**
     * Define el descriptor para cada región. Realiza la actualización de los
     * descriptores para cada región.
     *
     * @param tileDescriptorClass la nueva clase descriptora
     * @param regiones las regiones utilizadas
     */
    public void setRegionDescriptorClass(Class tileDescriptorClass, Region...regiones){
        this.regionDescriptorClass = tileDescriptorClass;
        this.setRegionDescriptors(tileDescriptorClass,regiones);
    }
    
    /**
     * Devuelve una representación en cadena de texto de este descriptor.
     *
     * @return una representación string de este descriptor
     */
    @Override
    public String toString(){
        String output ="";
        for(MediaDescriptor descriptor : descriptors){
            output += descriptor.toString()+"\n";
        }
        return output;
    }
    
    /////////// Prueba de serializable
    public void save(File file) throws FileNotFoundException, IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(this);
        oos.close();
    }

    static public SegmentedDescriptor open(File file) throws FileNotFoundException, 
            IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        SegmentedDescriptor sg = (SegmentedDescriptor) ois.readObject();
        ois.close();
        return sg;
    }
    
    /**
     * Clase interna que implementa el comparador  por defecto entre Segmented
     * Descriptor.
     */
    static class DefaultComparator implements Comparator<SegmentedDescriptor, Double> {
        /**
         * Comparador por defecto entre descriptores segmentados. Dicho comparador
         * se basa en (1) comparar descriptores segmentados que tengan el mismo
         * número de descriptores y (2) en una comparación secuencial (el prmero
         * con el primero, el segundo con el segundo, etc).
         * @param t Primer descriptor segmentado
         * @param u Segundo descriptor segmentado, con el que se compararé el primero
         * 
         * @return Devuelve una magnitud, un valor que indica la distancia entre
         * ambos descriptores.
         */
        @Override
        public Double apply(SegmentedDescriptor t, SegmentedDescriptor u) {
            System.out.println("Reliza la comapracion Default");
            if(t.descriptors.size() != u.descriptors.size()){
                throw new InvalidParameterException("The descriptor lists must have the same size.");
            }
            Double item_distance, sum = 0.0;
            MediaDescriptor m1, m2;
            for(int i=0; i<t.descriptors.size(); i++){
                try{
                    m1 = (MediaDescriptor)t.descriptors.get(i);
                    m2 = (MediaDescriptor)u.descriptors.get(i);
                    item_distance = (Double)m1.compare(m2);
                    sum += item_distance*item_distance;
                }
                catch(ClassCastException e){
                    throw new InvalidParameterException("The comparision between descriptors is not interpetrable as a double value.");
                }
                catch(Exception e){
                    throw new InvalidParameterException("The descriptors are not comparables.");
                }                
            }
            return Math.sqrt(sum);  
        }
    }
}


