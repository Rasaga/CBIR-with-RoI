/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.descriptor;

import java.io.Serializable;

/**
 * Clase para la creación del comparador Igual Máximo.
 * 
 * @author Ramón Sánchez García (chentaco@correo.ugr.es)
 */
public class ComparatorMaxEqual implements Comparator<SegmentedDescriptor, Double>, Serializable {
    
    boolean position;

    /**
     * Constructor de la clase de comparador maximo usando igualdad.
     * @param position variable booleana para tomar en cuenta la posición
     * de la región o no.
     */    
    public ComparatorMaxEqual(boolean position){
        super();
        this.position = position;
    }

    /**
     * Sobercarga del metodo apply para la comparación entre dos 
     * descriptores segmentados en función del maximo y del numero de regiones.
     * Se hace una comparación doble (t con u y u con t)
     * @param t Primer descriptor segmentado
     * @param u Segundo descriptor segmentado, con el que se compara el primero
     * @return valor double que representa la diferencia o distancia entre los
     * comparadores.
     */      
    @Override
    public Double apply(SegmentedDescriptor t, SegmentedDescriptor u) {
        Double resultado;
        if (t.size() == u.size()) {
            ComparatorMaxNoDuples p = new ComparatorMaxNoDuples(position);
            resultado = Math.max(p.apply(t, u),p.apply(u, t));
        } else {
            resultado = Double.POSITIVE_INFINITY;
        }
        System.out.println(resultado);
        return resultado;
    }
    
}
