/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.rsg.tools;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clase que recoge las herramientas correspondientes al uso de CSV.
 * 
 * @author Ramón Sánchez García (chentaco@correo.ugr.es)
 */
public class CSVTools {
    
    /**
     * Clase que abrirá un fichero CSV que contiene shapes y devolverá un array de shapes.
     * @param f El archivo csv con el que se queire trabajar
     * @return conjunto de figuras leidas desde el fichero csv
     */
    
    public static ArrayList<Shape> OpenCSV(File f) {
        ArrayList<Shape> shapes = new ArrayList();
        try {
            CSVReader reader = new CSVReader(new FileReader(f), '\t');
            String[] nextLine;
            Shape s;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
//                System.out.println(nextLine[0] + nextLine[1] + "etc...");
                if (nextLine[0].equals("java.awt.geom.Rectangle2D$Double")) {
//                    System.out.println("Es un rectangulo, con Ancho: "
//                            + nextLine[1] + " y Alto: "
//                            + nextLine[2] + " Coordenada: ("
//                            + nextLine[3] + ", " + nextLine[4] + ")");
                    s = new Rectangle2D.Double(Double.valueOf(nextLine[3]), Double.valueOf(nextLine[4]),
                            Double.valueOf(nextLine[1]), Double.valueOf(nextLine[2]));
                    shapes.add(s);
                } else if (nextLine[0].equals("java.awt.geom.Ellipse2D$Double")) {
//                    System.out.println("Es un circulo, con Ancho: "
//                            + nextLine[1] + " y Alto: "
//                            + nextLine[2] + " Coordenada: ("
//                            + nextLine[3] + ", " + nextLine[4] + ")");
                    s = new Ellipse2D.Double(Double.valueOf(nextLine[3]), Double.valueOf(nextLine[4]),
                            Double.valueOf(nextLine[1]), Double.valueOf(nextLine[2]));
                    shapes.add(s);
                } else if (nextLine[0].equals("java.awt.geom.Path2D$Double")) {

                    String[] splitX = nextLine[5].replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
                    String[] splitY = nextLine[6].replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");

                    int[] xvalues = new int[splitX.length];
                    int[] yvalues = new int[splitY.length];

//
                    // Dado que tendrá el mismo valor, me conformo haciendo un unico bucle
                    for (int w = 0; w < splitX.length; w++) {
                        // ESTASMOS USANDO INTS, OJITO
                        xvalues[w] = Integer.valueOf(splitX[w]);
                        yvalues[w] = Integer.valueOf(splitY[w]);
                    }

                    s = new Path2D.Double(Path2D.Double.WIND_NON_ZERO, xvalues.length);
                    ((Path2D.Double) s).moveTo(xvalues[0], yvalues[0]);

                    for (int i = 1; i < xvalues.length; i++) {
                        ((Path2D.Double) s).lineTo(xvalues[i], yvalues[i]);
                    }
                    ((Path2D.Double) s).closePath();
                    shapes.add(s);
                }
            }

        } catch (Exception ex) {
            System.err.println("Error reading the CSV");
        }
        return shapes;
    }
    
    /**
     * Permite guardar las figuras en un archivo externo
     * 
     * @param f Fichero donde se guardarán las figuras
     * @param shapes Conjunto de figuras que se procederán a exportar a dicho fichero
     */
    public static void SaveCSV(File f, ArrayList<Shape> shapes) {
        try {
            // AUTOMATICO
            //                        Writer writer = new FileWriter(f.getAbsolutePath().concat(".csv"));
            //                        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
            //                        beanToCsv.write(shapes);
            //                        writer.close();
            // MANUAL
//            CSVWriter writer = new CSVWriter(new FileWriter(f.getAbsolutePath().concat(".csv")), '\t');
            CSVWriter writer = new CSVWriter(new FileWriter(f.getAbsolutePath()), '\t');
            // feed in your array (or convert your data to an array)
            String[] entries = "Type#Width#Height#X#Y#Xcoords#Ycoords".split("#");
            writer.writeNext(entries);
            for (int j = 0; j < shapes.size(); j++) {
                Shape sh = shapes.get(j);

                // Cojo los puntos
                double[] coords = new double[6];
                ArrayList<String> xPoints = new ArrayList();
                ArrayList<String> yPoints = new ArrayList();

                // Conversion a area, para evitar coger puntos del GeneralPath al usar subpaths
                Area a = new Area(sh);
                for (PathIterator pi = a.getPathIterator(null); !pi.isDone(); pi.next()) {
                    int type = pi.currentSegment(coords);
                    if (type == 1) {
                        xPoints.add(Integer.toString((int) coords[0]));
                        yPoints.add(Integer.toString((int) coords[1]));
                    }
                }
                // Conversion a string de ambos arrays
                String sx = Arrays.toString(xPoints.toArray(new String[xPoints.size()]));
                String sy = Arrays.toString(yPoints.toArray(new String[yPoints.size()]));

                String[] s = {sh.getClass().getName(),
                    String.valueOf(sh.getBounds2D().getWidth()),
                    String.valueOf(sh.getBounds2D().getHeight()),
                    String.valueOf(sh.getBounds2D().getX()),
                    String.valueOf(sh.getBounds2D().getY()),
                    sx,
                    sy
                };
                writer.writeNext(s);
            }
            writer.close();
        } catch (Exception ex) {
            System.err.println("Error saving the CSV");
        }
    }
}
