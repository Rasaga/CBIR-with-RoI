/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.application;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import jmr.rsg.iu.Canvas2DImage;

/**
 * Ventana Interna Dibujable de la aplicación JMR
 * 
 * @author Ramón Sánchez García (chentaco@correo.ugr.es)
 */
public class InternalWindowImageDrawable extends javax.swing.JInternalFrame {

    /**
     * Creates new form InternalWindowImageDrawable
     */
    private JMRFrame mw = new JMRFrame();
    private Point coordenada = new Point();
    private Cursor cursorCruz = new Cursor(Cursor.CROSSHAIR_CURSOR);
    private File csv = null;    
    private URL urlImage = null;
    
    /**
     * Constructor de la Ventana Interna dibuable. 
     *
     * @param mw La ventana de la aplicación a la que pertenece
     */
    
    public InternalWindowImageDrawable(JMRFrame mw) {
        initComponents();
        this.mw = mw;
    }
    
    /**
     * Devuelve el lienzo de dibujo de esta ventana
     *
     * @return lienzo de dibujo
     */
    
    public Canvas2DImage getCanvas2DImage(){
        return this.canvas2DImage1;
    }
    
    /**
     * Devuelve la imagen de esta ventana interna
     *
     * @return imagen de la ventana
     */
    
    public BufferedImage getImage(){
        return this.canvas2DImage1.getImage();
    }
    
    /**
     * Asigna una imagen a la ventana actual
     *
     * @param img la imagen que quiero asignar
     */
    
    public void setImage(BufferedImage img){
        this.canvas2DImage1.setImage(img);
    }
    
    /**
     * Asigna un archivo csv a la imagen de la ventana ctual
     *
     * @param f Archivo csv que se asignará a la ventana
     */
    
    public void setCSV(File f){
        this.csv = f;
    }
    
    /**
     * Devuelve el csv asociado a esta imagen
     *
     * @return el archivo csv
     */
    
    public File getCSV(){
        return this.csv;
    }
    
    /**
     * Devuelve el panel deslizable de la ventana
     *
     * @return panel deslizable
     */
    
    public javax.swing.JScrollPane getScrollPanel(){
        return this.scrollPanel;
    }
    
    /**
     * Modifica la url de la imagen del panel
     *
     * @param url nueva dirección de la imagen
     */
    
    public void setURL(URL url){
        this.urlImage = url;
    }
    
    /**
     * Devuelve la URL de la imagen en la ventana.
     *
     * @return url de la imagen
     */
    
    public URL getURL(){
        return this.urlImage;
    }
    
    
//    public void updateMainWindow() {
//        // Comprobamos si hay figuras/Roi
//        // Si hay figuras, pero no RoI, habilitamos el boton de aniadir
//        // si además hay RoI, habilitamos el resto de botones
//        if (!this.canvas2DImage1.getArrayRegions().isEmpty()) {
//            if (this.canvas2DImage1.getRoI()==null || this.canvas2DImage1.getRoI().getNumRegions()==0) {
//                //demas deshabilitados
//                this.updateMainWindow(2);
//            }else{
//                this.updateMainWindow(3);
//            }
//        } else {
//            this.updateMainWindow(1);
//        }
//    }
    
//    public void updateMainWindow(int value){
//        // ALL DISABLED
//        if (value == 0) {
//            this.mw.getButtonRegion().setEnabled(false);
//            this.mw.getButtonCreate().setEnabled(false);
//            this.mw.getButtonDelete().setEnabled(false);
////            this.mw.getButtonCompare().setEnabled(false);
////            this.mw.getButtonSearch().setEnabled(false);
//        // REGION TOOL ENABLED
//        }else if(value==1){
//            this.mw.getButtonRegion().setEnabled(true);
//            this.mw.getButtonCreate().setEnabled(false);
//            this.mw.getButtonDelete().setEnabled(false);
////            this.mw.getButtonCompare().setEnabled(false);
////            this.mw.getButtonSearch().setEnabled(false);
//        // REGION AND CREATE BUTTON ENABLED    
//        }else if(value==2){
//            this.mw.getButtonRegion().setEnabled(true);
//            this.mw.getButtonCreate().setEnabled(true);
//            this.mw.getButtonDelete().setEnabled(true);
////            this.mw.getButtonCompare().setEnabled(false);
////            this.mw.getButtonSearch().setEnabled(false);
//        // ALL ENABLED    
//        }else if(value==3){
//            this.mw.getButtonRegion().setEnabled(true);
//            this.mw.getButtonCreate().setEnabled(true);
//            this.mw.getButtonDelete().setEnabled(true);
////            this.mw.getButtonCompare().setEnabled(true);
////            this.mw.getButtonSearch().setEnabled(true);
//        }else{
//            System.out.println("ERROR");
//        }
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPanel = new javax.swing.JScrollPane();
        canvas2DImage1 = new jmr.rsg.iu.Canvas2DImage();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameActivated(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        canvas2DImage1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                canvas2DImage1MouseMoved(evt);
            }
        });
        canvas2DImage1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                canvas2DImage1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                canvas2DImage1MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                canvas2DImage1MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout canvas2DImage1Layout = new javax.swing.GroupLayout(canvas2DImage1);
        canvas2DImage1.setLayout(canvas2DImage1Layout);
        canvas2DImage1Layout.setHorizontalGroup(
            canvas2DImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 455, Short.MAX_VALUE)
        );
        canvas2DImage1Layout.setVerticalGroup(
            canvas2DImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 347, Short.MAX_VALUE)
        );

        scrollPanel.setViewportView(canvas2DImage1);

        getContentPane().add(scrollPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
//        this.updateMainWindow();
//        this.updateRoIfromIV();
    }//GEN-LAST:event_formInternalFrameActivated

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // Delete everything
//        this.getCanvas2DImage().clear(); // <- necesario? se cierra el lienzo
        // CAMBIARLO A PRIVATE?
//        this.updateMainWindow(0);
    }//GEN-LAST:event_formInternalFrameClosing

    private void canvas2DImage1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_canvas2DImage1MouseReleased
//        this.updateMainWindow();
    }//GEN-LAST:event_canvas2DImage1MouseReleased

    private void canvas2DImage1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_canvas2DImage1MouseMoved
//        coordenada = evt.getPoint();
//        this.mw.setTextoCoordenada("(" + (int) coordenada.getX() + ", "
//                + (int) coordenada.getY() + ")");
          this.mw.setTextoCoordenada(this.positionChange(evt));
    }//GEN-LAST:event_canvas2DImage1MouseMoved

    private void canvas2DImage1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_canvas2DImage1MouseEntered
        this.canvas2DImage1MouseMoved(evt);
        this.setCursor(cursorCruz);
    }//GEN-LAST:event_canvas2DImage1MouseEntered

    private void canvas2DImage1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_canvas2DImage1MouseExited
        this.mw.setTextoCoordenada("");
        this.setCursor(null);
    }//GEN-LAST:event_canvas2DImage1MouseExited

    /**
     * Obtiene la información del pixel de la imagen donde está el cursor.
     *
     * @param evt posición donde se encuentra el cursor
     * @return cadena de texto con la información de la posición y de los pixeles
     */
    
    private String positionChange(java.awt.event.MouseEvent evt) {
        String text = " ";
        Point p = evt.getPoint();
        BufferedImage img = this.canvas2DImage1.getImage();
        if (p != null && img != null && p.x < img.getWidth() && p.y < img.getHeight()) {
//                Color c = img.getRGB(p.x, p.y);
            int pixel = img.getRGB(p.x, p.y);

            Integer alpha = (pixel >> 24) & 0xff;
            int red = (pixel >> 16) & 0xff;
            int green = (pixel >> 8) & 0xff;
            int blue = (pixel >> 0) & 0xff;
            
            text = "(" + p.x + "," + p.y + ") : [" + red + "," + green + "," + blue;
            text += alpha == 255 ? "]" : ("," + alpha + "]");
        }
        return text;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private jmr.rsg.iu.Canvas2DImage canvas2DImage1;
    private javax.swing.JScrollPane scrollPanel;
    // End of variables declaration//GEN-END:variables
}
