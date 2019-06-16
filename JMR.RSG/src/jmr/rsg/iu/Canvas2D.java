/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.rsg.iu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;

/**
 * Clase Lienzo para el dibujo de figuras.
 * 
 * @author Ramón Sánchez García (chentaco@correo.ugr.es)
 */
public class Canvas2D extends javax.swing.JPanel {
    
    private ArrayList<Shape> vShape = new ArrayList();
    // Auxiliar point
    private Point2D pAux = null;
    
    // Aditional configuration
    // Forma para el contorno del lienzo, por defecto 300x300
    private Shape border = new Rectangle2D.Float(0, 0, 300, 300);
    // y su contorno
    // Vamos a crear un trazo discontinuo de trazo 10
    float DiscontinuousPattern[] = {5.0f, 5.0f};
    private Stroke borderType = new BasicStroke(1.0f,
            BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_MITER, 1.0f,
            DiscontinuousPattern, 0.0f);
    
    private Color miColor = Color.PINK;
    
    /**
     * Creates new form Canvas2D
     */
    private int toolNumber = 1;
    
    private Shape lastShape = null;
    
    
    public Canvas2D() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        this.createShape(evt.getPoint());
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        this.updateShape(evt.getPoint());
        this.repaint();
    }//GEN-LAST:event_formMouseDragged

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        this.formMouseDragged(evt);
        Shape s = vShape.get(vShape.size() - 1);
        if (this.toolNumber == 0) {
            ((Path2D.Double) s).closePath();
        }
        this.lastShape = s;
    }//GEN-LAST:event_formMouseReleased

    /**
     * Creará una figura.
     * 
     * @param p el punto con el que empezará la figura 
     */
    private void createShape(Point p) {
        Shape v = null;
        if(toolNumber==0){
            v = new Path2D.Double();
            ((Path2D)v).moveTo(p.x, p.y);
        }else if(toolNumber==1){
            v = new Rectangle2D.Double(p.x,p.y,0,0);
        }else if(toolNumber==2){
            v = new Ellipse2D.Double(p.x,p.y,0,0);
        }
        vShape.add(v);
        pAux = p;
    }
   
    /**
     * Actualizará una figura
     * 
     * @param p el punto que hará que se modifique dicha figura
     */

    private void updateShape(Point p) {
        if (vShape != null) {
            Shape s = vShape.get(vShape.size() - 1);
            if (toolNumber == 0) {
                ((Path2D) s).lineTo(p.x, p.y);
            } else if (toolNumber == 1) {
                ((RectangularShape)s).setFrameFromDiagonal(pAux,p);
            } else if (toolNumber == 2) {
                ((RectangularShape)s).setFrameFromDiagonal(pAux,p);
            }
        }
    }
    
    /**
     * Método de dibujo de figuras.
     * 
     * @param g Variable graphics para el dibujado
     */
 
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        Stroke defaultStroke = g2d.getStroke();
        this.paintBorder(g2d);
        g2d.setStroke(defaultStroke);
        g2d.setPaint(miColor);
        for (Shape s : vShape) {
            g2d.draw(s);
        }
    }
    
    /**
     * Modificador del color de las figuras del lienzo.
     * 
     * @param color Nuevo color
     */
    public void setColor(Color color){
        this.miColor = color;
    }

    
//    @Override
//    public void paint(Graphics g){
//        super.paint(g);
//        Graphics2D g2d = (Graphics2D)g;
//        this.paintBorder(g2d);
//        for(RFigura2D s:vRegions){
//            s.dibujarFigura(g2d);
//        }
//    }
    
//    public ArrayList<RegionComun> getArrayRegions() {
//        return this.vRegions;
//    }
//    
//    public void addRegion(RegionComun region){
//        this.vRegions.add(region);
//    }
//   
    /**
     * Método para borrar las figuras dibujadas en el lienzo.
     * 
     */
    
    public void clear(){
        this.vShape = new ArrayList();
    }
    
    /**
     * Metodo para obtener la última figura dibujada.
     * 
     * @return La última figura dibujada
     */
    
    public Shape getLastShape(){
        return this.lastShape;
    }
    
    /**
     * Añade una nueva figura a la lista de figuras.
     * 
     * @param s Figura a añadir
     */
    
    public void addShape(Shape s){
        this.vShape.add(s);
    }
    
    /**
     * Obtiene la lista de figuras dibujadas.
     * 
     * @return lista de figuras del lienzo
     */
    
    public ArrayList<Shape> getShapes(){
        return this.vShape;
    }
    
    /**
     * Actualiza el tamaño del borde del lienzo
     * 
     * @param alto nueva dimensión de altura
     * @param ancho nueva dimensión de anchura
     */
    
    public void updateBorder(int alto, int ancho) {
        // cambiare el tamaño del lienzo, y usare 0, 0 como coordenada
        ((Rectangle2D.Float) border).setFrame(0, 0, alto, ancho);
    }
    
    /**
     * Actualiza el tamao del borde del lienzo
     * 
     * @param r nuevo objeto rectangular que hará de nuevo borde
     */
    
    public void updateBorder(Rectangle r) {
        // cambiare el tamaño del lienzo, y usare 0, 0 como coordenada
        ((Rectangle2D.Float) border).setFrame(r);
        this.repaint();
    }
    
    /**
     * Se encarga de pintar el borde del lienzo.
     * 
     * @param g2d
     */

    private void paintBorder(Graphics2D g2d) {
        g2d.setStroke(borderType); // trazo discontinuo del contorno
        g2d.draw(border); // lo dibujo
        g2d.setClip(border); // y el clip de la zona
    }

//    public RegionComun getMiRegion(){
//        return miRegion;
//    }
    
    /**
     * Método para indicar el tipo de figura que se pintará en el lienzo.
     * 
     * @param i variable entera que hace referencia al tipo de figura.
     */
    
    public void setTool(int i){
        this.toolNumber=i;
    }
    
//    public RegionComun[] getRegiones(){
//        return vRegions.toArray(new RegionComun[vRegions.size()]);
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}