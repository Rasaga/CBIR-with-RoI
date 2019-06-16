/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.rsg.tools;

import java.util.Comparator;
import javafx.util.Pair;

/**
 * Clase para permitir comparar un conjunto de Pair a partir del valor
 * de su clave.
 * 
 * @author Ramón Sánchez García (chentaco@correo.ugr.es)
 */
public class CompareArrayTools implements Comparator<Pair>{
    /**
     * Compara dos valores Pair en función del valor de su clave
     * @param p1 primer Pair
     * @param p2 segundo Pair
     * @return valor entero que representa si es menor o mayor
     */
    @Override
    public int compare(Pair p1, Pair p2) {
        return ((Double) p1.getKey()).compareTo((Double) p2.getKey());
    }
}
