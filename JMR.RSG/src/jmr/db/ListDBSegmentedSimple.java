package jmr.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import jmr.descriptor.Comparator;
import jmr.descriptor.SegmentedDescriptor;
import jmr.result.ResultMetadata;
import jmr.roi.Region;

/**
 * Clase para almacenar los descriptores. Actuará como base de datos.
 * 
 * @author Ramón
 * @param <T> tipo de elementos que se almacena en la base de datos.
 */

public class ListDBSegmentedSimple<T> implements Serializable{
 
    private ArrayList<Record> database = null;
    private Class descriptorClass = null;
    private Record orderReference = null;
//    private Comparator comparador = null;
    
    

    /**
     * Constructor de la base de datos. 
     * @param descriptorClass La clase el tipo de descriptor que se 
     * utilizará.
     */
    public ListDBSegmentedSimple(Class descriptorClass){
        this.descriptorClass = descriptorClass;
        database = new ArrayList();
    }
    
    /**
     * Añade un Segmented Descriptor a la base de datos.
     * @param record Segmented Descriptor que se añadirá a la 
     * base de datos de descriptores.
     * @return variable booleana infomrando de si pudo o no añadir la 
     * entrada.
     */
    public boolean add(Record record) {
        if (!record.isCompatible()) {
            throw new InvalidParameterException("The new record does not share the database structure.");
        }
        return database.add(record);
    }
    
//    public boolean add(T media){
//        Record record = new Record(media);
//        return database.add(record);
//    }
    
//    public boolean add(T media, URL locator){
//        Record record = new Record(media, locator);
//        return database.add(record);
//    }
    
    /**
     * Añade una o varias regiones a la base de datos. Crea un
     * segmented descriptor a partir de ellas.
     * @param region La o las regiones usada de entrada
     * @return Variable booleana indicando si pudo o no añadirlas
     */
    public boolean add(Region... region) {
        Record record = new Record(region);
        return database.add(record);
    }

    /**
     * Añade un descriptor segmentado en una determinada posición.
     * @param index El indice donde deseo añadir el descriptor.
     * @param record El descriptor segmentado.
     */
    public void add(int index, Record record) {
        if (!record.isCompatible()) {
            throw new InvalidParameterException("The new record does not share the data base structure.");
        }
        database.add(index, record);
    }
    
//    public void add(int index, T media) {
//        Record record = new Record(media);
//        database.add(index, record);
//    }

    /**
     * Reemplaza un descriptor en una determinada posicion.
     * @param index posicion deseada
     * @param record el descriptor nuevo
     * @return devuelve el elemento anterior
     */
    public Record set(int index, Record record) {
        if (!record.isCompatible()) {
            throw new InvalidParameterException("The new record does not share the data base structure.");
        }
        return database.set(index, record);
    }

//    public Record set(int index, T media) {
//        Record record = new Record(media);
//        return database.set(index, record);
//    }
    
    /**
     * Obtiene el descriptor en una determinada posicion
     * @param index el indice del descriptor que deseo
     * @return el descriptor que hay en dicha posicion
     */
    public Record get(int index) {
        return database.get(index);
    }

    /**
     * Elimina un descriptor de una posicion
     * @param index el indice del descriptor a eliminar
     * @return el descriptor eliminado
     */
    public Record remove(int index) {
        return database.remove(index);
    }
    
    /**
     * Elmina todos los elementos de la base de datos
     */

    public void clear() {
        database.clear();
    }

    /**
     * Devuelve el tamaño de la base de datos
     * @return valor del tamaño de la base de datos de descriptores
     */
    public int size() {
        return database.size();
    }
    
    /**
     * Comprueba si la base de datos está vacia o no
     * @return variable booleana informando de si esta tiene contenido o no
     */

    public boolean isEmpty() {
        return database.isEmpty();
    }
    
    /**
     * Devuelve el tipo de descriptor utilizado en esta base de datos
     * @return la clase del descriptor usado en esta base de datos
     */
    
    public Class getDescriptorClass(){ 
        return this.descriptorClass;
    }
    
    /**
     * Cambia el descriptor utilizado como referencia por otro nuevo.
     * 
     * @param orderReference el nuevo descriptor que usa como referencia
     */
    
    public void setOrderReference(Record orderReference){
        if (!orderReference.isCompatible()) {
            throw new InvalidParameterException("The new record does not share the data base structure.");
        }
        this.orderReference = orderReference;
    }

    /**
     * Devuelve el descriptor utilizado como referencia
     * 
     * @return descriptor referente
     */
    Record getOrderReference(){
        return this.orderReference;
    }
    
    /**
     * Devuelve una lista ordenada de los descriptores de la base de datos
     * comparado con un descriptor que se usa como entrada. 
     * @param queryRecord El descriptor segmentado que se desea buscar
     * semejantes
     * @return Lista de descriptores semejanes, ordenada de menor a mayor según
     * semejanza, con el descriptor usado como parámetro.
     */
    
    public List<Record> query(Record queryRecord){
        if (!queryRecord.isCompatible()) {
            throw new InvalidParameterException("The query record does not share the data base structure.");
        }
        List output = (List)database.clone(); // Shallow copy 
        // The output will be ordered by using the query record as reference  
        Record actual_orderReference = orderReference;
        this.orderReference = queryRecord;
        output.sort(null);
        this.orderReference = actual_orderReference;
        
        return output;
    }
    
//    public List<Record> query(T queryMedia){
//        Record queryRecord = new Record(queryMedia);
//        return this.query(queryRecord);       
//    }
    
    public List<Record> query(Record queryRecord, int size){
        if (!queryRecord.isCompatible()) {
            throw new InvalidParameterException("The query record does not share the data base structure.");
        }
        List output = query(queryRecord);
        return output.subList(0, size);
        
        //Record actual_orderReference = orderReference;
        //this.orderReference = queryRecord;         
        //size = Math.min(Math.max(size,0), database.size());
        //TreeSet<Record> output = new TreeSet(database.subList(0,size)); //Sorted    
        //Record current, last = output.last(); // Highest element currently in output
        //for(int i=size; i<database.size(); i++){
        //    current = database.get(i);  
        //    if(last.compareTo(current)>0) {
        //        output.remove(last);                
        //        output.add(database.get(i)); // Added in a sorted way
        //        last = output.last();
        //    }
        //}
        //this.orderReference = actual_orderReference;
        //return new ArrayList(output);
        // NOTE: the previous implementation is faster than the easiest following
        // code: (1) call to query(queryRecord) and (2) return the sublist by
        // calling subList(0,size). If the size of the database (n) is very high,  
        // the sorting in the query method (O(n·logn)) have more cost than the  
        // insertion made in this implementation (O(n·m), with m<<n) For example,
        // if m=10 (the size parameter), this implementaion is faster for n>1024.
    }
    
//    public List<Record> query(T queryMedia, int size){
//        Record queryRecord = new Record(queryMedia);
//        return this.query(queryRecord, size);        
//    }
    /**
     * Devuelve una lista ordenada de menor a mayor con las distancias obtenidas
     * de comparar un segmented descriptor (Record) con la base de datos.
     * @param queryRecord El segmented descriptor usado como entrada
     * @return Una lista ordenada de menor a mayor semejanza, 
     * con los descriptores de la base de datos
     */
    public List<ResultMetadata<Double,Record>> queryMetadata(Record queryRecord){ 
        if (!queryRecord.isCompatible()) {
            throw new InvalidParameterException("The query record does not share the data base structure.");
        }
        List output = new ArrayList<>();
        Object distance;
//        queryRecord.setComparator(comparador);
        for(Record r: database){
//            r.setComparator(comparador);
            distance = queryRecord.compare(r);
            output.add(new ResultMetadata(distance, r));
        }
        output.sort(null);
        return output;
    }
    
    /**
     * Crear el objeto de la base de datos a partir de un fichero previamente
     * guardado.
     * @param file El fichero donde se almacena la informacion de la base
     * de datos
     * @return Un objeto ListDBSegmentedSimple con la información del fichero
     * leido
     * @throws FileNotFoundException No se encuentre el fichero
     * @throws IOException Error de entrada/salida
     * @throws ClassNotFoundException No se encuentra la clase
     */
    
    static public ListDBSegmentedSimple open(File file) throws FileNotFoundException, 
            IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        ListDBSegmentedSimple database = (ListDBSegmentedSimple) ois.readObject();
        ois.close();
        return database;
    }
    
    /**
     * Mecanimso para almacenar el objeto como un fichero.
     * @param file El fichero donde se guardará
     * @throws FileNotFoundException No se encuentra el fichero
     * @throws IOException Error de entrada y salida
     */
    
    public void save(File file) throws FileNotFoundException, IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(this);
        oos.close();
    }
    
    /**
     * Método para obtener una cadena de texto con la información de la base
     * de datos.
     * @return Cadena de texto con la información de la base de datos.
     */

    @Override
    public String toString(){
        String output ="";
        output += this.database.toString();
        return output;
    }
    
//    public void setComparator(Comparator comparador){
//        this.comparador=comparador;
//    }
//    
//    public Comparator getComparator(){
//        return this.comparador;
//    }

    /**
     * Clase interna para crear Descriptores Segmentados como Record, que además
     * almacenan la url del csv y de la imagen.
     */
    public class Record extends SegmentedDescriptor<T> implements Comparable<Record>{
        
        private URL imageLocator=null;
        private URL csvLocator=null;
//        private Comparator comparador = null;
        
        /**
         * Constructor por defecto a partir de las regiones y el tipo de clase
         * descriptora utilizado a la hora de crear la base de datos.
         * @param region Region o regiones usadas
         */
        public Record(Region...region){
            super(descriptorClass,region);
        }
        
        /**
         * Constructor creado a partir de url de la imagen y un conjunto 
         * de regiones.
         * @param imageLocator la url de la imagen usada
         * @param region el conjunto de regiones
         */
        public Record(URL imageLocator, Region...region){
            super(descriptorClass,region);
            this.imageLocator = imageLocator;
        }
        
        /**
         * Constructor que utiliza la url de la imagen, csv y además
         * las regiones para crear el descriptor.
         * @param imageLocator url de la imagen
         * @param csvLocator url del csv
         * @param region conjunto de regiones
         */
        public Record(URL imageLocator, URL csvLocator, Region...region){
            super(descriptorClass,region);
            this.imageLocator = imageLocator;
            this.csvLocator = csvLocator;
        }

//        public Record(T media, URL locator) {
//            this(media);
//            this.locator = locator;
//        }
//
//        public Record(DescriptorList<T> descriptors) {
//            super(null);
//            // Is 'descriptors' compatible with the database?
//            boolean compatible = (descriptors.size() == descriptorClasses.length);
//            for (int i = 0; i < descriptors.size() && compatible; i++) {
//                if (descriptors.get(i).getClass() != descriptorClasses[i]) {
//                    compatible = false;
//                }
//            }
//            // If it is compatible, the given descriptors are added to this 
//            // record
//            if (compatible) {
//                for (int i = 0; i < descriptors.size(); i++) {
//                    this.add(descriptors.get(i));
//                }
//            }
//        }
        

//        public Record(DescriptorList<T> descriptors, URL locator){
//            this(descriptors);
//            this.locator = locator;
//        }

        /**
         * Metodo que devuelve la url de la imagen.
         * @return url de la imagen
         */
        public URL getImageLocator() {
            return imageLocator;
        }
        
        /**
         * Metodo que devuelve la url del fichero csv
         * @return url del fichero csv
         */
        public URL getCSVLocator(){
            return csvLocator;
        }
        
//        private void initDescriptors(Class... descriptorClasses) {
//            MediaDescriptor<T> descriptor;
//            for (Class c : descriptorClasses) {
//                descriptor = MediaDescriptorFactory.getInstance(c, this.source);
//                this.add(descriptor);
//            }
//        }
        
        /**
         * Comprueba que el record se compatible con la base de datos
         * @return true o false, dependiendo de si es compatible o no
         */
        public boolean isCompatible() {
            return this.getTileDescriptorClass() == descriptorClass;
        }                

        /**
         * Compara el Record actual con otro
         * @param o Otro record
         * @return devuelve un valor entero dependiendo de la comparacion hecha
         */
        @Override
        public int compareTo(Record o) {
            if (orderReference == null) {
                return 0;
            }
            Double d1 = this.compare(orderReference);
            Double d2 = o.compare(orderReference);
            return d1.compareTo(d2);
        }
    }
}
