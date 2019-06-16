/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.rsg.tools;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

/**
 * Herramientas con histogramas sobre imágenes.
 * 
 * @author Ramón Sánchez García (chentaco@correo.ugr.es)
 */
public class HistogramTool {
   
    /**
     * Método para obtener el histograma de grises de una imagen.
     * 
     * @param image Imagen sobre la que se quiera sacar el histograma de grises
     * @return histograma de grises de la imagen 
     */
    public static int [] getGrayHistogram(BufferedImage image){
        ColorSpace gris = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp op = new ColorConvertOp(gris, null);
        BufferedImage imgray = op.filter(image, null); //null es la salida
        
        int[] salida = new int[256];
        for(int i=0;i<imgray.getWidth();i++){
            for(int j=0;j<imgray.getHeight();j++){
                int valor = image.getRGB(i, j) & 0xff; // en int
                salida[valor]++;
            }
        }
        return salida;
    }
    
}
