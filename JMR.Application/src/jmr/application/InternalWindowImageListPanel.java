/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.application;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.List;
import jmr.iu.ImageListPanel;
import jmr.result.ResultMetadata;

/**
 * Ventana Interna con un listado de imágenes.
 * 
 * @author Ramón Sánchez García (chentaco@correo.ugr.es)
 */
public class InternalWindowImageListPanel extends javax.swing.JInternalFrame {

    private BufferedImage image = null;
    private JMRFrame vp = null;
    
    /**
     * Creates new form InternalWindowImageListPanel
     * @param vp Ventana principal a la que pertenece
     */
    public InternalWindowImageListPanel(JMRFrame vp) {
        initComponents();
        this.vp = vp;
    }
    
    /**
     * Añade la imagen con su nombre a la lista.
     * 
     * @param img Imagen que se añade
     * @param label Nombre de la imagen
     */
    public void setImage(BufferedImage img, String label) {
        this.imageListPanel1.add(img, label, false);
    }
    
    /**
     * Añade una imagen a la lista
     * 
     * @param img Imagen que se añade
     */
    
    public void setImage(BufferedImage img){
        this.imageListPanel1.add(img);
    }
    
    /**
     * Añade una lista de Metadatos a la lista
     * 
     * @param list lista de metadatos que se añade
     */
    
    public void addList(List<ResultMetadata> list){
        this.imageListPanel1.add(list);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imageListPanel1 = new jmr.iu.ImageListPanel(null, new Dimension(100,100));

        setClosable(true);

        imageListPanel1.addImageSelectionListener(new ImageListPanel.ImageSelectionListener() {
            public void imageSelected(ImageListPanel.ImageSelectionEvent evt) {
                //System.out.println(evt.getLabel());
                image = (BufferedImage) evt.getSource();
                if(image!=null){
                    vp.imprimirImagen(image);
                }
            }
        });
        getContentPane().add(imageListPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private jmr.iu.ImageListPanel imageListPanel1;
    // End of variables declaration//GEN-END:variables
}
