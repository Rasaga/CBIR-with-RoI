/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.db;

import java.awt.Shape;
import java.awt.image.BufferedImage;
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
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import jmr.descriptor.Comparator;
import jmr.descriptor.ComparatorMin;
import jmr.descriptor.MediaDescriptor;
import jmr.descriptor.SegmentedDescriptor;
import jmr.descriptor.color.SingleColorDescriptorWithRegion;
import jmr.result.ResultMetadata;
import jmr.roi.Region;
import jmr.rsg.tools.CSVTools;

/**
 *
 * @author Ramón
 */
public class ListDBSegmented<T> implements Serializable{
    

    private ArrayList<RecordSegmented> database = null;
    private Class descriptorClasses[] = null;
    private RecordSegmented orderReference = null;
    private Comparator comparador = null;
    

    public ListDBSegmented(Comparator comparador, Class... descriptorClasses){
        this.descriptorClasses = descriptorClasses;
        this.comparador = comparador;
        database = new ArrayList();
    }
    
    public boolean add(RecordSegmented record) {
        return database.add(record);
    }
    
//    public boolean add(T media){
//        RecordSegmented record = new Record(media);
//        return database.add(record);
//    }
//    
//    public boolean add(T media, URL locator){
//        Record record = new Record(media, locator);
//        return database.add(record);
//    }
    
//    public boolean add(Region...region){
//        RecordSegmented record = new RecordSegmented(region);
//        return database.add(record);
//    }
    public boolean add(File f){
        RecordSegmented record = new RecordSegmented(f);
        return database.add(record);
    }

    public void add(int index, RecordSegmented record) {
        database.add(index, record);
    }

//    public void add(int index, T media) {
//        Record record = new Record(media);
//        database.add(index, record);
//    }

    public RecordSegmented set(int index, RecordSegmented record) {
        return database.set(index, record);
    }
    
//    public Record set(int index, T media) {
//        Record record = new Record(media);
//        return database.set(index, record);
//    }

    public RecordSegmented get(int index) {
        return database.get(index);
    }

    public RecordSegmented remove(int index) {
        return database.remove(index);
    }

    public void clear() {
        database.clear();
    }

    public int size() {
        return database.size();
    }

    public boolean isEmpty() {
        return database.isEmpty();
    }
    
    public List<Class> getDescriptorClasses(){ 
        return Arrays.asList(this.descriptorClasses);
    }
    
//    public void setOrderReference(RecordSegmented orderReference){
//        if (!orderReference.isCompatible()) {
//            throw new InvalidParameterException("The new record does not share the data base structure.");
//        }
//        this.orderReference = orderReference;
//    }

//    RecordSegmented getOrderReference(){
//        return this.orderReference;
//    }

//    public List<RecordSegmented> query(RecordSegmented queryRecord){
//        if (!queryRecord.isCompatible()) {
//            throw new InvalidParameterException("The query record does not share the data base structure.");
//        }
//        List output = (List)database.clone(); // Shallow copy 
//        // The output will be ordered by using the query record as reference  
//        RecordSegmented actual_orderReference = orderReference;
//        this.orderReference = queryRecord;
//        output.sort(null);
//        this.orderReference = actual_orderReference;
//        
//        return output;
//    }

//    public List<RecordSegmented> query(T queryMedia){
//        RecordSegmented queryRecord = new RecordSegmented(queryMedia);
//        return this.query(queryRecord);       
//    }

//    public List<RecordSegmented> query(RecordSegmented queryRecord, int size){
//        if (!queryRecord.isCompatible()) {
//            throw new InvalidParameterException("The query record does not share the data base structure.");
//        }
//        List output = query(queryRecord);
//        return output.subList(0, size);
//    }

//    public List<RecordSegmented> query(T queryMedia, int size){
//        RecordSegmented queryRecord = new RecordSegmented(queryMedia);
//        return this.query(queryRecord, size);        
//    }

    public List<ResultMetadata<Double,RecordSegmented>> queryMetadata(RecordSegmented queryRecord){ 
        List output = new ArrayList<>();
        Object distance;
        Double total = 0.0;
        queryRecord.setComparator(comparador);
        for(RecordSegmented r: database){
            // para todos los descriptoiresw (set descriptor 1... as con todos) + comparador
            r.setComparator(comparador);
            for(int i = 0 ; i<this.descriptorClasses.length; i++){
                r.setDescriptor(descriptorClasses[i]);
                queryRecord.setDescriptor(descriptorClasses[i]);
                total+=(Double)queryRecord.compare(r)*(Double)queryRecord.compare(r);
            }
//            distance = queryRecord.compare(r);
            
            // distancia será igual a distance+=distance*distance
            // y al final calcular la raiz cuadrada cuando termine de hacerlo para todos
            distance = Math.sqrt(total);
            output.add(new ResultMetadata(distance, r));
            total = 0.0;
        }
        output.sort(null);
        return output;
    } 
    
    static public ListDBSegmented open(File file) throws FileNotFoundException, 
            IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        ListDBSegmented database = (ListDBSegmented) ois.readObject();
        ois.close();
        return database;
    }

    
    public void save(File file) throws FileNotFoundException, IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(this);
        oos.close();
    }

    @Override
    public String toString(){
        String output="";
//        for(int i=0; i<database.size();i++){
//            for(int j=0; j<this.descriptorClasses.length;j++){
//                database.get(i).setDescriptor(descriptorClasses[j]);
//                output += this.database.toString();
//            }
//        }
        for(int i=0; i<this.descriptorClasses.length; i++){
            for(int j=0; j<this.database.size();j++){
                database.get(j).setDescriptor(descriptorClasses[i]);
            }
            output += this.database.toString();
        }
        return output;
    }
//    @Override
//    public String toString(){
//        for(int i= 0; i<database.size();i++){
//            database.get(i).setDescriptor(descriptorClasses[0]);
//        }
//        String output ="";
//        output += this.database.toString();
//        return output;
//    }
    
    public class RecordSegmented extends SegmentedDescriptor<T> implements Comparable<RecordSegmented>{
        
//        private Region[] regiones = null;
        
//        private URL csv = null;
        private File f = null;
        
        public RecordSegmented(File f){
            super();
            this.setRegionDescriptorClass(null, readCSV(f));
            this.f = f;
        }
        
        public RecordSegmented(BufferedImage source, File f){
            super(source);
            this.f = f;
        }
        
        private Region[] readCSV(File f) {
            Region[] r = null;
            try { 
                BufferedImage img = ImageIO.read(f);
                if (this.getSource() == null && img!=null) {
                    this.setSource((T) img);
                }
                String nameCSV = f.getAbsolutePath().replaceFirst("[.][^.]+$", "").concat(".csv");
                File fileCSV = new File(nameCSV);
                if (fileCSV.exists()) {
                    ArrayList<Shape> shapes = CSVTools.OpenCSV(fileCSV);
                    r = new Region[shapes.size()];
                    for (int i = 0; i < shapes.size(); i++) {
                        r[i] = new Region((BufferedImage)this.getSource(), shapes.get(i));
                    }    
                } else {
                    System.out.println("No existe el CSV");
                }
            } catch (Exception ex) {
                System.out.println("Error leyendo el CSV");
            }
            return r;
        }
        
//        public RecordSegmented(Region...region){
//            super(null, region);
//            this.regiones = region;
//        }
        
        
//        
//        public RecordSegmented get(int index) {
//            return database.get(index);
//        }
//        
//        public int size() {
//            return database.size();
//        }
        
//        public boolean isCompatible() {
//            boolean compatible = (this.size() == descriptorClasses.length); // Cada region tiene 3 descrpitores
//            for (int i = 0; i < this.size() && compatible; i++) {
//                if (this.get(i).getClass() != descriptorClasses[i])
//                    compatible = false;
//            }
//            return compatible;
//        } 
        
        public void setDescriptor(Class descriptorClass){
            this.setRegionDescriptorClass(descriptorClass, this.readCSV(f));
        }

        @Override
        public int compareTo(RecordSegmented o) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
}
