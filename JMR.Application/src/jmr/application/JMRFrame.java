package jmr.application;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import jmr.db.ListDB;
import jmr.db.ListDBSegmentedSimple;
import jmr.descriptor.Comparator;
import jmr.descriptor.ComparatorAvg;
import jmr.descriptor.ComparatorAvgEqual;
import jmr.descriptor.ComparatorAvgEqualNoDuples;
import jmr.descriptor.ComparatorAvgNoDuples;
import jmr.descriptor.ComparatorMax;
import jmr.descriptor.ComparatorMaxEqual;
import jmr.descriptor.ComparatorMaxEqualNoDuples;
import jmr.descriptor.ComparatorMaxNoDuples;
import jmr.descriptor.ComparatorMin;
import jmr.descriptor.ComparatorMinEqual;
import jmr.descriptor.SegmentedDescriptor;
import jmr.descriptor.color.MPEG7ColorStructureWithRegion;
import jmr.descriptor.color.MPEG7ScalableColorWithRegion;
import jmr.descriptor.color.SingleColorDescriptorWithRegion;
import jmr.result.ResultMetadata;
import jmr.roi.Region;
import jmr.rsg.tools.CSVTools;

/**
 * Ventana principal de la aplicación JMR
 * 
 * @author Jesús Chamorro Martínez (jesus@decsai.ugr.es)
 * @author Ramón Sánchez García (chentaco@correo.ugr.es) 
 */
public class JMRFrame extends javax.swing.JFrame {
    /**
     * Crea una ventana principal
     */
    public JMRFrame() {
        initComponents();
        setIconImage((new ImageIcon(getClass().getResource("/icons/iconoJMR.png"))).getImage());
        //Desactivamos botonos de BD
        this.botonCloseDB.setEnabled(false);
        this.botonSaveDB.setEnabled(false);
        this.botonAddRecordDB.setEnabled(false);
        this.botonSearchDB.setEnabled(false);
    }
    
        
    /**
     * Devuelve el título de la ventana interna selecionada
     * 
     * @return el título de la ventana interna selecionada
     */
    private String getSelectedFrameTitle(){
        String title = "";
        JInternalFrame vi = escritorio.getSelectedFrame();
        if (vi != null) {
            title = vi.getTitle();
        }
        return title;
    }
    
    
        /**
     * Sitúa la ventana interna <tt>vi</tt> debajo de la ventana interna activa.
     *
     * @param vi la ventana interna
     */
    private void locateInternalFrame(JInternalFrame vi, boolean adjustSize) {
        JInternalFrame vSel = escritorio.getSelectedFrame();
        if (vSel != null) {
            vi.setLocation(vSel.getX() + 20, vSel.getY() + 20);
            if(adjustSize) vi.setSize(vSel.getSize());
        }
    }
   
    /**
     * Muestra la ventana interna <tt>vi</tt>
     *
     * @param vi la ventana interna
     */
    public void showInternalFrame(JInternalFrame vi, boolean adjustSize) {
        this.locateInternalFrame(vi, adjustSize);
        this.escritorio.add(vi);
        vi.setVisible(true);
//        try {
//            vi.setMaximum(true);
//        } catch (Exception e) {
//            System.out.println("No se pudo maximizar.");
//        }
    }
    
    /**
     * Muestra la ventana interna <tt>vi</tt>
     *
     * @param vi la ventana interna
     */
    public void showInternalFrame(JInternalFrame vi) {
        this.showInternalFrame(vi,true);
    }
    
    
//    /**
//     * Sitúa la ventana interna <tt>vi</tt> debajo de la ventana interna activa 
//     * y con el mismo tamaño.
//     * 
//     * @param vi la ventana interna
//     */
//    private void locateInternalFrame(JInternalFrame vi) {
//        JInternalFrame vSel = escritorio.getSelectedFrame();
//        if (vSel != null) {
//            vi.setLocation(vSel.getX() + 20, vSel.getY() + 20);
//            vi.setSize(vSel.getSize());
//        }
//    }
//    
//    /**
//     * Muestra la ventana interna <tt>vi</tt> 
//     * 
//     * @param vi la ventana interna
//     */
//    public void showInternalFrame(JInternalFrame vi) {        
//        if(vi instanceof ImageInternalFrame){
//            ((ImageInternalFrame)vi).setGrid(this.verGrid.isSelected());
//            ((ImageInternalFrame)vi).addPixelListener(new ManejadorPixel());
//        }
//        this.locateInternalFrame(vi);
//        this.escritorio.add(vi);
//        vi.setVisible(true);
//    }  
    
    /**
     * Devuelve el panel donde se muestra información de salida.
     *
     * @return el panel de informacion
     */
    public JEditorPane getOutputPanel(){
         return editorOutput;
    }
    
    /**
     * Edita el texto en la barra de coordenadas.
     *
     * @param texto la cordenada
     */
    public void setTextoCoordenada(String texto){
        posicionPixel.setText(texto);
    }
    
    /*
     * Código generado por Netbeans para el diseño del interfaz
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupMenuPanelOutput = new javax.swing.JPopupMenu();
        clear = new javax.swing.JMenuItem();
        popupMenuSeleccionDescriptores = new javax.swing.JPopupMenu();
        colorEstructurado = new javax.swing.JRadioButtonMenuItem();
        colorEscalable = new javax.swing.JRadioButtonMenuItem();
        colorMedio = new javax.swing.JRadioButtonMenuItem();
        popupMenuSeleccionDescriptoresDB = new javax.swing.JPopupMenu();
        colorEstructuradoDB = new javax.swing.JRadioButtonMenuItem();
        colorEscalableDB = new javax.swing.JRadioButtonMenuItem();
        colorMedioDB = new javax.swing.JRadioButtonMenuItem();
        grupoBotonesHerramientas = new javax.swing.ButtonGroup();
        grupoDescriptoresDB = new javax.swing.ButtonGroup();
        grupoDescriptores = new javax.swing.ButtonGroup();
        grupoIncursion = new javax.swing.ButtonGroup();
        splitPanelCentral = new javax.swing.JSplitPane();
        escritorio = new javax.swing.JDesktopPane();
        showPanelInfo = new javax.swing.JLabel();
        panelTabuladoInfo = new javax.swing.JTabbedPane();
        panelOutput = new javax.swing.JPanel();
        scrollEditorOutput = new javax.swing.JScrollPane();
        editorOutput = new javax.swing.JEditorPane();
        panelBarraHerramientas = new javax.swing.JPanel();
        barraArchivo = new javax.swing.JToolBar();
        botonAbrir = new javax.swing.JButton();
        botonGuardar = new javax.swing.JButton();
        barraHerramientas = new javax.swing.JToolBar();
        botonRectangulo = new javax.swing.JToggleButton();
        botonElipse = new javax.swing.JToggleButton();
        botonTrazo = new javax.swing.JToggleButton();
        botonOtrosColores = new javax.swing.JButton();
        botonBorrarShapes = new javax.swing.JButton();
        panelColor = new javax.swing.JPanel();
        barraBD = new javax.swing.JToolBar();
        botonNewDB = new javax.swing.JButton();
        botonOpenDB = new javax.swing.JButton();
        botonSaveDB = new javax.swing.JButton();
        botonCloseDB = new javax.swing.JButton();
        botonAddRecordDB = new javax.swing.JButton();
        botonSearchDB = new javax.swing.JButton();
        barraDescriptores = new javax.swing.JToolBar();
        botonCompara = new javax.swing.JButton();
        barraComparadores = new javax.swing.JToolBar();
        comboTipoComparator = new javax.swing.JComboBox<>();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        botonIncursionSimple = new javax.swing.JToggleButton();
        botonEqual = new javax.swing.JToggleButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        checkPosicion = new javax.swing.JCheckBox();
        checkSinRepeticiones = new javax.swing.JCheckBox();
        barraEstado = new javax.swing.JPanel();
        posicionPixel = new javax.swing.JLabel();
        infoDB = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        menuAbrir = new javax.swing.JMenuItem();
        menuGuardar = new javax.swing.JMenuItem();
        separador1 = new javax.swing.JPopupMenu.Separator();
        closeAll = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        verHerramientasDibujado = new javax.swing.JCheckBoxMenuItem();
        verHerramientasDB = new javax.swing.JCheckBoxMenuItem();
        verBarraEstado = new javax.swing.JCheckBoxMenuItem();
        menuTools = new javax.swing.JMenu();
        aniadirDirectorio = new javax.swing.JMenuItem();
        mostrarDatabase = new javax.swing.JMenuItem();

        popupMenuPanelOutput.setAlignmentY(0.0F);
        popupMenuPanelOutput.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        clear.setText("Clear");
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });
        popupMenuPanelOutput.add(clear);

        grupoDescriptores.add(colorEstructurado);
        colorEstructurado.setSelected(true);
        colorEstructurado.setText("Structured color With Regions");
        popupMenuSeleccionDescriptores.add(colorEstructurado);

        grupoDescriptores.add(colorEscalable);
        colorEscalable.setText("Scalable color With Regions");
        popupMenuSeleccionDescriptores.add(colorEscalable);

        grupoDescriptores.add(colorMedio);
        colorMedio.setText("Mean color With Regions");
        popupMenuSeleccionDescriptores.add(colorMedio);

        grupoDescriptoresDB.add(colorEstructuradoDB);
        colorEstructuradoDB.setText("Structured color With Regions");
        popupMenuSeleccionDescriptoresDB.add(colorEstructuradoDB);

        grupoDescriptoresDB.add(colorEscalableDB);
        colorEscalableDB.setText("Scalable color With Regions");
        colorEscalableDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorEscalableDBActionPerformed(evt);
            }
        });
        popupMenuSeleccionDescriptoresDB.add(colorEscalableDB);

        grupoDescriptoresDB.add(colorMedioDB);
        colorMedioDB.setSelected(true);
        colorMedioDB.setText("Mean color With Regions");
        popupMenuSeleccionDescriptoresDB.add(colorMedioDB);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java Multimedia Retrieval With Regions of Interest");
        setName("ventanaPrincipal"); // NOI18N

        splitPanelCentral.setDividerLocation(1.0);
        splitPanelCentral.setDividerSize(3);
        splitPanelCentral.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        splitPanelCentral.setPreferredSize(new java.awt.Dimension(0, 0));
        splitPanelCentral.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                splitPanelCentralPropertyChange(evt);
            }
        });

        escritorio.setBackground(java.awt.Color.lightGray);
        escritorio.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        showPanelInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/desplegar20.png"))); // NOI18N
        showPanelInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                showPanelInfoMousePressed(evt);
            }
        });

        escritorio.setLayer(showPanelInfo, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout escritorioLayout = new javax.swing.GroupLayout(escritorio);
        escritorio.setLayout(escritorioLayout);
        escritorioLayout.setHorizontalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, escritorioLayout.createSequentialGroup()
                .addGap(0, 951, Short.MAX_VALUE)
                .addComponent(showPanelInfo))
        );
        escritorioLayout.setVerticalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, escritorioLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(showPanelInfo))
        );

        splitPanelCentral.setTopComponent(escritorio);

        panelTabuladoInfo.setMinimumSize(new java.awt.Dimension(0, 0));
        panelTabuladoInfo.setPreferredSize(new java.awt.Dimension(0, 0));

        panelOutput.setMinimumSize(new java.awt.Dimension(0, 0));
        panelOutput.setPreferredSize(new java.awt.Dimension(0, 0));
        panelOutput.setLayout(new java.awt.BorderLayout());

        scrollEditorOutput.setBorder(null);
        scrollEditorOutput.setMinimumSize(new java.awt.Dimension(0, 0));

        editorOutput.setBorder(null);
        editorOutput.setMinimumSize(new java.awt.Dimension(0, 0));
        editorOutput.setPreferredSize(new java.awt.Dimension(0, 0));
        editorOutput.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                editorOutputMouseReleased(evt);
            }
        });
        scrollEditorOutput.setViewportView(editorOutput);

        panelOutput.add(scrollEditorOutput, java.awt.BorderLayout.CENTER);

        panelTabuladoInfo.addTab("Output", panelOutput);

        splitPanelCentral.setBottomComponent(panelTabuladoInfo);

        getContentPane().add(splitPanelCentral, java.awt.BorderLayout.CENTER);

        panelBarraHerramientas.setAlignmentX(0.0F);
        panelBarraHerramientas.setAlignmentY(0.0F);
        panelBarraHerramientas.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        barraArchivo.setRollover(true);
        barraArchivo.setAlignmentX(0.0F);

        botonAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/open24.png"))); // NOI18N
        botonAbrir.setToolTipText("Open");
        botonAbrir.setFocusable(false);
        botonAbrir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonAbrir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAbrirActionPerformed(evt);
            }
        });
        barraArchivo.add(botonAbrir);

        botonGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save24.png"))); // NOI18N
        botonGuardar.setToolTipText("Save");
        botonGuardar.setFocusable(false);
        botonGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarActionPerformed(evt);
            }
        });
        barraArchivo.add(botonGuardar);

        panelBarraHerramientas.add(barraArchivo);

        barraHerramientas.setRollover(true);

        grupoBotonesHerramientas.add(botonRectangulo);
        botonRectangulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/rectangulo.png"))); // NOI18N
        botonRectangulo.setSelected(true);
        botonRectangulo.setToolTipText("Draw a Rectangle");
        botonRectangulo.setFocusable(false);
        botonRectangulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonRectangulo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonRectangulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRectanguloActionPerformed(evt);
            }
        });
        barraHerramientas.add(botonRectangulo);

        grupoBotonesHerramientas.add(botonElipse);
        botonElipse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/elipse.png"))); // NOI18N
        botonElipse.setToolTipText("Draw a Ellipse");
        botonElipse.setFocusable(false);
        botonElipse.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonElipse.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonElipse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonElipseActionPerformed(evt);
            }
        });
        barraHerramientas.add(botonElipse);

        grupoBotonesHerramientas.add(botonTrazo);
        botonTrazo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/pencil (1).png"))); // NOI18N
        botonTrazo.setToolTipText("Draw a custom Path");
        botonTrazo.setFocusable(false);
        botonTrazo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonTrazo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonTrazo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonTrazoActionPerformed(evt);
            }
        });
        barraHerramientas.add(botonTrazo);

        botonOtrosColores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconDCD24.png"))); // NOI18N
        botonOtrosColores.setToolTipText("Choose a Color...");
        botonOtrosColores.setFocusable(false);
        botonOtrosColores.setBackground(Color.PINK);
        botonOtrosColores.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonOtrosColores.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonOtrosColores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonOtrosColoresActionPerformed(evt);
            }
        });
        barraHerramientas.add(botonOtrosColores);

        botonBorrarShapes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete.png"))); // NOI18N
        botonBorrarShapes.setToolTipText("Delete all shapes");
        botonBorrarShapes.setFocusable(false);
        botonBorrarShapes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonBorrarShapes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonBorrarShapes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBorrarShapesActionPerformed(evt);
            }
        });
        barraHerramientas.add(botonBorrarShapes);

        panelColor.setLayout(new java.awt.BorderLayout());
        barraHerramientas.add(panelColor);

        panelBarraHerramientas.add(barraHerramientas);

        barraBD.setRollover(true);

        botonNewDB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/database.png"))); // NOI18N
        botonNewDB.setToolTipText("Create a new database");
        botonNewDB.setBorderPainted(false);
        botonNewDB.setComponentPopupMenu(popupMenuSeleccionDescriptoresDB);
        botonNewDB.setFocusable(false);
        botonNewDB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonNewDB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonNewDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNewDBActionPerformed(evt);
            }
        });
        barraBD.add(botonNewDB);

        botonOpenDB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/openDB.png"))); // NOI18N
        botonOpenDB.setToolTipText("Open a database");
        botonOpenDB.setFocusable(false);
        botonOpenDB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonOpenDB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonOpenDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonOpenDBActionPerformed(evt);
            }
        });
        barraBD.add(botonOpenDB);

        botonSaveDB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/saveDB.png"))); // NOI18N
        botonSaveDB.setToolTipText("Save the database");
        botonSaveDB.setFocusable(false);
        botonSaveDB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonSaveDB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonSaveDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSaveDBActionPerformed(evt);
            }
        });
        barraBD.add(botonSaveDB);

        botonCloseDB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/deleteBD.png"))); // NOI18N
        botonCloseDB.setToolTipText("Close the database");
        botonCloseDB.setFocusable(false);
        botonCloseDB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonCloseDB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonCloseDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCloseDBActionPerformed(evt);
            }
        });
        barraBD.add(botonCloseDB);

        botonAddRecordDB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/addBD.png"))); // NOI18N
        botonAddRecordDB.setFocusable(false);
        botonAddRecordDB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonAddRecordDB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonAddRecordDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAddRecordDBActionPerformed(evt);
            }
        });
        barraBD.add(botonAddRecordDB);

        botonSearchDB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/seacrhDB.png"))); // NOI18N
        botonSearchDB.setFocusable(false);
        botonSearchDB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonSearchDB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonSearchDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSearchDBActionPerformed(evt);
            }
        });
        barraBD.add(botonSearchDB);

        panelBarraHerramientas.add(barraBD);

        barraDescriptores.setRollover(true);

        botonCompara.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/compare24.png"))); // NOI18N
        botonCompara.setToolTipText("Compare");
        botonCompara.setComponentPopupMenu(popupMenuSeleccionDescriptores);
        botonCompara.setFocusable(false);
        botonCompara.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonCompara.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonCompara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonComparaActionPerformed(evt);
            }
        });
        barraDescriptores.add(botonCompara);

        panelBarraHerramientas.add(barraDescriptores);

        barraComparadores.setRollover(true);

        comboTipoComparator.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "At least one region", "Greater number of regions", "Average" }));
        comboTipoComparator.setToolTipText("Select the type of Comparator");
        comboTipoComparator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTipoComparatorActionPerformed(evt);
            }
        });
        barraComparadores.add(comboTipoComparator);
        barraComparadores.add(jSeparator4);

        grupoIncursion.add(botonIncursionSimple);
        botonIncursionSimple.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/contains.png"))); // NOI18N
        botonIncursionSimple.setSelected(true);
        botonIncursionSimple.setToolTipText("Choose Simple Incursion");
        botonIncursionSimple.setAutoscrolls(true);
        botonIncursionSimple.setFocusable(false);
        botonIncursionSimple.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonIncursionSimple.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonIncursionSimple.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonIncursionSimpleActionPerformed(evt);
            }
        });
        barraComparadores.add(botonIncursionSimple);

        grupoIncursion.add(botonEqual);
        botonEqual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/equals.png"))); // NOI18N
        botonEqual.setToolTipText("Choose Comparator Equal");
        botonEqual.setFocusable(false);
        botonEqual.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonEqual.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonEqual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEqualActionPerformed(evt);
            }
        });
        barraComparadores.add(botonEqual);
        barraComparadores.add(jSeparator3);

        checkPosicion.setText("Position");
        checkPosicion.setFocusable(false);
        checkPosicion.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        checkPosicion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        checkPosicion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkPosicionActionPerformed(evt);
            }
        });
        barraComparadores.add(checkPosicion);

        checkSinRepeticiones.setText("No Dupples");
        checkSinRepeticiones.setFocusable(false);
        checkSinRepeticiones.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        checkSinRepeticiones.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        checkSinRepeticiones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkSinRepeticionesActionPerformed(evt);
            }
        });
        barraComparadores.add(checkSinRepeticiones);

        panelBarraHerramientas.add(barraComparadores);

        getContentPane().add(panelBarraHerramientas, java.awt.BorderLayout.PAGE_START);

        barraEstado.setLayout(new java.awt.BorderLayout());

        posicionPixel.setText("  ");
        barraEstado.add(posicionPixel, java.awt.BorderLayout.LINE_START);

        infoDB.setText("Not open");
        barraEstado.add(infoDB, java.awt.BorderLayout.EAST);

        getContentPane().add(barraEstado, java.awt.BorderLayout.SOUTH);

        menuArchivo.setText("File");

        menuAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/open16.png"))); // NOI18N
        menuAbrir.setText("Open");
        menuAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAbrirActionPerformed(evt);
            }
        });
        menuArchivo.add(menuAbrir);

        menuGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save16.png"))); // NOI18N
        menuGuardar.setText("Save");
        menuGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGuardarActionPerformed(evt);
            }
        });
        menuArchivo.add(menuGuardar);
        menuArchivo.add(separador1);

        closeAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/closeall16.png"))); // NOI18N
        closeAll.setText("Close all");
        closeAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeAllActionPerformed(evt);
            }
        });
        menuArchivo.add(closeAll);

        menuBar.add(menuArchivo);

        jMenu1.setText("View");

        verHerramientasDibujado.setSelected(true);
        verHerramientasDibujado.setText("View Draw Tools");
        verHerramientasDibujado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verHerramientasDibujadoActionPerformed(evt);
            }
        });
        jMenu1.add(verHerramientasDibujado);

        verHerramientasDB.setSelected(true);
        verHerramientasDB.setText("View Database Tools");
        verHerramientasDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verHerramientasDBActionPerformed(evt);
            }
        });
        jMenu1.add(verHerramientasDB);

        verBarraEstado.setSelected(true);
        verBarraEstado.setText("View Status Bar");
        verBarraEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verBarraEstadoActionPerformed(evt);
            }
        });
        jMenu1.add(verBarraEstado);

        menuBar.add(jMenu1);

        menuTools.setText("Tools");

        aniadirDirectorio.setText("Add a Directory to the Database...");
        aniadirDirectorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aniadirDirectorioActionPerformed(evt);
            }
        });
        menuTools.add(aniadirDirectorio);

        mostrarDatabase.setText("Show Database....");
        mostrarDatabase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrarDatabaseActionPerformed(evt);
            }
        });
        menuTools.add(mostrarDatabase);

        menuBar.add(menuTools);

        setJMenuBar(menuBar);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void menuAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAbrirActionPerformed
        BufferedImage img;
        JFileChooser dlg = new JFileChooser();
        // voy a asegurarme de que no haya filtro "todo tipo de archivo"
        dlg.setAcceptAllFileFilterUsed(false);
        // Creamos el filtro, en este caso, filtro de imagenes
        FileFilter filtro = new FileNameExtensionFilter("Images (jpg, jpeg, png, gif, bmp)",
                "jpg","jpeg","png","gif","bmp");
        // Y la añado
        dlg.addChoosableFileFilter(filtro);
        dlg.setMultiSelectionEnabled(true);
        int resp = dlg.showOpenDialog(this);
        if (resp == JFileChooser.APPROVE_OPTION) {
            try {                
                File files[] = dlg.getSelectedFiles();              
                for (File f : files) {
                    img = ImageIO.read(f);
                    if (img != null) {
//                        ImageInternalFrame vi = new JMRImageInternalFrame(this, img, f.toURI().toURL());
                        InternalWindowImageDrawable vi = new InternalWindowImageDrawable(this);
                        vi.setImage(img);
                        vi.setTitle(f.getName());
                        vi.setURL(f.toURI().toURL());
                        // Buscamos si tiene CSV asociado
                        // Cojo el nombre de dicha imagen para buscar el CSV correspondiente
                        String nameCSV = f.getAbsolutePath().replaceFirst("[.][^.]+$", "").concat(".csv");
                        File fileCSV = new File(nameCSV);
                        // En caso de que exista, voy a abrirlo y extraer los shapes y guardarlos para imprimirlos en el lienzo
                        if (fileCSV.exists()) {
                            // EJECUTAR ALGORITMO QUE ABRE UN FILE.CSV Y DEVUELVE UNOS SHAPES
                            vi.setCSV(fileCSV);
                            ArrayList<Shape> shapes = CSVTools.OpenCSV(fileCSV);
                            for (int i = 0; i < shapes.size(); i++) {
                                vi.getCanvas2DImage().addShape(shapes.get(i));
                            }
                        }
                        
                        vi.getCanvas2DImage().resizeFrame(vi.getScrollPanel().getBounds());
                        
                        
                        vi.addComponentListener(new ComponentAdapter() {
                            @Override
                            public void componentResized(final ComponentEvent e) {
                                super.componentResized(e);
                                
                                vi.getCanvas2DImage().resizeFrame(vi.getScrollPanel().getBounds());
                                //vi.getCanvas2DImage().resizeFrame(vi.getContentPane().getBounds());
//                                vi.getCanvas2DImage().resizeFrame(vi.getSize());
//                                System.out.println("Resizing");
                            }
                        });
                        this.showInternalFrame(vi);
                        vi.getScrollPanel().getViewport().addChangeListener(new ListenAdditionsScrolled() {
                            @Override
                            public void stateChanged(ChangeEvent e) {
                                if(vi.getContentPane()!=null){
                                    int v1 = vi.getScrollPanel().getHorizontalScrollBar().getValue();
                                    int v2 = vi.getScrollPanel().getVerticalScrollBar().getValue();
//                                    System.out.println(v1+", "+v2);
                                    Rectangle rr = vi.getScrollPanel().getBounds();
                                    rr.x = v1;
                                    rr.y = v2;
                                    vi.getCanvas2DImage().resizeFrame(rr);
//                                System.out.println(vi.getContentPane().getLocationOnScreen().x);
//                                    vi.getCanvas2DImage().resizeFrame(vi.getContentPane().getBounds());
                                }
                            }
                        });
//                        this.showInternalFrame(vi);
                    }
                }               
            } catch (Exception ex) {
                JOptionPane.showInternalMessageDialog(escritorio, "Error in image opening", "Image", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_menuAbrirActionPerformed

    private void menuGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGuardarActionPerformed
//        BufferedImage img = this.getSelectedImage();
//        if (img != null) {
//            JFileChooser dlg = new JFileChooser();
//            int resp = dlg.showSaveDialog(this);
//            if (resp == JFileChooser.APPROVE_OPTION) {
//                File f = dlg.getSelectedFile();
//                try {
//                    ImageIO.write(img, "png", f);
//                    escritorio.getSelectedFrame().setTitle(f.getName());
//                } catch (Exception ex) {
//                    JOptionPane.showInternalMessageDialog(escritorio, "Error in image saving", "Image", JOptionPane.INFORMATION_MESSAGE);
//                }
//            }
//        }      
        JFileChooser dlg = new JFileChooser();
        dlg.setAcceptAllFileFilterUsed(false);
        FileFilter filtroCSV = new FileNameExtensionFilter("File CSV", "csv", "CSV");
        dlg.addChoosableFileFilter(filtroCSV);

        int resp = dlg.showSaveDialog(this);
        if (resp == JFileChooser.APPROVE_OPTION) {
            try {
                InternalWindowImageDrawable vi = (InternalWindowImageDrawable) escritorio.getSelectedFrame();
                if (vi != null && vi.getCanvas2DImage() != null) {
                    BufferedImage img = vi.getImage();
                    if (img != null && !vi.getCanvas2DImage().getShapes().isEmpty()) {

                        ArrayList<Shape> shapes = vi.getCanvas2DImage().getShapes();
                        if (!shapes.isEmpty()) {
                            File f = dlg.getSelectedFile();
                            // ALGORITMO
                            CSVTools.SaveCSV(f,shapes);       
                        }
                    }
                }
            } catch (Exception ex) {
                System.err.println("No se pudo guardar el fichero");
            }
        }
    }//GEN-LAST:event_menuGuardarActionPerformed

    private void botonAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAbrirActionPerformed
        this.menuAbrirActionPerformed(evt);
    }//GEN-LAST:event_botonAbrirActionPerformed

    private void botonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarActionPerformed
        this.menuGuardarActionPerformed(evt);
    }//GEN-LAST:event_botonGuardarActionPerformed
    
    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
        this.editorOutput.setText("");
    }//GEN-LAST:event_clearActionPerformed

    private void splitPanelCentralPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_splitPanelCentralPropertyChange
        if (evt.getPropertyName().equals("dividerLocation")) {
            float dividerLocation = (float) splitPanelCentral.getDividerLocation() / splitPanelCentral.getMaximumDividerLocation();
            if (dividerLocation >= 1) {//Está colapsada
                showPanelInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/desplegar20.png")));
            } else {
                showPanelInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cerrar16.png")));
            }
        }
    }//GEN-LAST:event_splitPanelCentralPropertyChange

    private void editorOutputMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editorOutputMouseReleased
        if(evt.isPopupTrigger()){
            Point p = this.scrollEditorOutput.getMousePosition();
            this.popupMenuPanelOutput.show(this.panelOutput,p.x,p.y);
        }
    }//GEN-LAST:event_editorOutputMouseReleased

    private void showPanelInfoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showPanelInfoMousePressed
        float dividerLocation = (float)splitPanelCentral.getDividerLocation()/splitPanelCentral.getMaximumDividerLocation();
        if(dividerLocation>=1) {//Está colapsada
            splitPanelCentral.setDividerLocation(0.8);
        } else{
            splitPanelCentral.setDividerLocation(1.0);
        }
    }//GEN-LAST:event_showPanelInfoMousePressed

    private void closeAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeAllActionPerformed
       escritorio.removeAll();
       escritorio.repaint();
    }//GEN-LAST:event_closeAllActionPerformed

    private void botonComparaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonComparaActionPerformed
        InternalWindowImageDrawable v = (InternalWindowImageDrawable) escritorio.getSelectedFrame();
        InternalWindowImageDrawable viAnalyzed;
//        JMRImageInternalFrame viAnalyzed, viQuery = this.getSelectedImageFrame();
        if (v != null && v.getCanvas2DImage() != null) {
            SegmentedDescriptor sg1 = null;
            java.awt.Cursor cursor = this.getCursor();
            setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
            Class descriptorLocal = null;
            if (this.colorEstructurado.isSelected()) {
                descriptorLocal = MPEG7ColorStructureWithRegion.class;
            } else if (this.colorEscalable.isSelected()) {
                descriptorLocal = MPEG7ScalableColorWithRegion.class;
            } else if (this.colorMedio.isSelected()) {
                descriptorLocal = SingleColorDescriptorWithRegion.class;
            }
            if (descriptorLocal != null) {
                ArrayList<Shape> shapes = v.getCanvas2DImage().getShapes();
                
                if (!shapes.isEmpty()) {
                    Region[] regiones = new Region[shapes.size()];
                    for (int i = 0; i < shapes.size(); i++) {
                        regiones[i] = new Region(v.getImage(), shapes.get(i));
                    }
                    sg1 = new SegmentedDescriptor(descriptorLocal, regiones);
                } else {
                    Region r = new Region(v.getImage());
                    sg1 = new SegmentedDescriptor(descriptorLocal, r);
                }
                sg1.setComparator(comparador);
            }
            
            List<ResultMetadata> resultList = new LinkedList<>();
            Double compare = null;
            String text = editorOutput.getText();
            JInternalFrame ventanas[] = escritorio.getAllFrames();            
            for (JInternalFrame vi : ventanas) {                
                if (vi instanceof InternalWindowImageDrawable) {
                    viAnalyzed = (InternalWindowImageDrawable) vi;
                    if (viAnalyzed.getCanvas2DImage() != null && sg1 != null) {
                        if (descriptorLocal != null) {
                            ArrayList<Shape> shapes = viAnalyzed.getCanvas2DImage().getShapes();
                            SegmentedDescriptor sg2;
                            if (!shapes.isEmpty()) {
                                Region[] regiones = new Region[shapes.size()];
                                for (int i = 0; i < shapes.size(); i++) {
                                    regiones[i] = new Region(viAnalyzed.getImage(), shapes.get(i));
                                }
                                sg2 = new SegmentedDescriptor(descriptorLocal, regiones);
                            } else {
                                Region r = new Region(viAnalyzed.getImage());
                                sg2 = new SegmentedDescriptor(descriptorLocal, r);
                            }
                            compare = (Double) sg1.compare(sg2);
                            text += "Dist(" + v.getTitle() + "," + viAnalyzed.getTitle() + ") = ";
                            text += compare != null ? compare + "\n" : "No calculado\n";
                            resultList.add(new ResultMetadata(compare, (BufferedImage)sg2.getSource()));
                        }
                    }
                }
            }
            this.editorOutput.setText(text);            
            setCursor(cursor);
            //Creamas la ventana interna con los resultados
            resultList.sort(null);
            ImageListInternalFrame listFrame = new ImageListInternalFrame(resultList);
            this.escritorio.add(listFrame);
            listFrame.setVisible(true);            
        }
    }//GEN-LAST:event_botonComparaActionPerformed
    
    /**
     * Metodo para actualizar los botones referentes a la base de datos
     *
     * @param closed variable booleana 
     */
    private void setDataBaseButtonStatus(boolean closed){
        this.botonNewDB.setEnabled(closed);
        this.botonOpenDB.setEnabled(closed);
        this.botonCloseDB.setEnabled(!closed);
        this.botonSaveDB.setEnabled(!closed);
        this.botonAddRecordDB.setEnabled(!closed);
        this.botonSearchDB.setEnabled(!closed);
    }
    
    private Class[] getDBDescriptorClasses(){
        ArrayList<Class> outputL = new ArrayList<>();
        if (this.colorEstructuradoDB.isSelected())
            outputL.add(MPEG7ColorStructureWithRegion.class);
        if (this.colorEscalableDB.isSelected())
            outputL.add(MPEG7ScalableColorWithRegion.class);
        if (this.colorMedioDB.isSelected())
            outputL.add(SingleColorDescriptorWithRegion.class);
        Class output[] = new Class[outputL.size()];
        for(int i=0; i<outputL.size(); i++)
            output[i] = outputL.get(i);
        return output;
    }
    
    private void botonNewDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNewDBActionPerformed
//        // Creamos la base de datos vacía
//        database = new ListDB(getDBDescriptorClasses());
//        // Activamos/desactivamos botones
//        setDataBaseButtonStatus(false);
//        updateInfoDBStatusBar("New DB (not saved)");
        this.databaseSimple = new ListDBSegmentedSimple(getDBDescriptorClasses()[0]);
        this.resultadoDatabase = new InternalWindowImageListPanel(this);
        this.escritorio.add(resultadoDatabase);
//        this.resultadoDatabase.setTitle(getDBDescriptorClasses()[0].getSimpleName());
//        this.resultadoDatabase.setVisible(true);
        setDataBaseButtonStatus(false);
        updateInfoDBStatusBar("New DB (not saved)");
    }//GEN-LAST:event_botonNewDBActionPerformed

    private void botonCloseDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCloseDBActionPerformed
//        database.clear();
//        database = null;
//        // Activamos/desactivamos botones
//        setDataBaseButtonStatus(true);
//        updateInfoDBStatusBar(null);
        databaseSimple.clear();
        databaseSimple = null;
//        if(resultadoDatabase.isVisible() && resultadoDatabase!=null)
//            resultadoDatabase.dispose();
        // Activamos/desactivamos botones
        setDataBaseButtonStatus(true);
        updateInfoDBStatusBar(null);
    }//GEN-LAST:event_botonCloseDBActionPerformed

    private void botonAddRecordDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAddRecordDBActionPerformed
//        if (database != null) {
//            java.awt.Cursor cursor = this.getCursor();
//            setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
//            //Incorporamos a la BD todas las imágenes del escritorio
//            JInternalFrame ventanas[] = escritorio.getAllFrames();
//            JMRImageInternalFrame viAnalyzed;
//            for (JInternalFrame vi : ventanas) {
//                if (vi instanceof JMRImageInternalFrame) {
//                    viAnalyzed = (JMRImageInternalFrame) vi;
//                    database.add(viAnalyzed.getImage(),viAnalyzed.getURL());
//                }
//            }
//            setCursor(cursor);
//            updateInfoDBStatusBar("Updated DB (not saved)");
//        }

        if (databaseSimple != null) {
            java.awt.Cursor cursor = this.getCursor();
            setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
            //Incorporamos a la BD todas las imágenes del escritorio
            JInternalFrame ventanas[] = escritorio.getAllFrames();
            InternalWindowImageDrawable viAnalyzed;
            for (JInternalFrame vi : ventanas) {
                if (vi instanceof InternalWindowImageDrawable) {
                    viAnalyzed = (InternalWindowImageDrawable) vi;
                    ListDBSegmentedSimple.Record registro;
                    if(viAnalyzed.getCanvas2DImage().getShapes().isEmpty()){
                        Region r = new Region(viAnalyzed.getImage());
                        registro = databaseSimple.new Record(viAnalyzed.getURL(),r);
                    }else{
                        ArrayList<Shape> shapes = viAnalyzed.getCanvas2DImage().getShapes();
                        Region[] regiones = new Region[shapes.size()];
                        for (int i = 0; i < shapes.size(); i++) {
                            regiones [i] = new Region(viAnalyzed.getImage(), shapes.get(i));
                        }
                        registro = databaseSimple.new Record(viAnalyzed.getURL(),regiones);          
                    }
                    databaseSimple.add(registro);
                }
            }
//            this.mostrarDatabase();
            setCursor(cursor);
            updateInfoDBStatusBar("Updated DB (not saved)");
        }
    }//GEN-LAST:event_botonAddRecordDBActionPerformed

    private void botonSearchDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSearchDBActionPerformed
        InternalWindowImageDrawable vi = null;
        if (this.escritorio.getSelectedFrame() instanceof InternalWindowImageDrawable) {
            vi = (InternalWindowImageDrawable) escritorio.getSelectedFrame();
        }
        if (vi != null && databaseSimple != null) {
            java.awt.Cursor cursor = this.getCursor();
            setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
            BufferedImage img = vi.getCanvas2DImage().getImage();
            if (img != null) {
                ArrayList<Shape> shapes = vi.getCanvas2DImage().getShapes();
                ListDBSegmentedSimple.Record record;
                if (!shapes.isEmpty()) {
                    Region[] regiones = new Region[shapes.size()];
                    for (int i = 0; i < shapes.size(); i++) {
                        regiones[i] = new Region(img, shapes.get(i));
                    }
                    record = databaseSimple.new Record(regiones);
                } else {
                    Region r = new Region(img);
                    record = databaseSimple.new Record(r);
                }
                record.setComparator(comparador);
                List<ResultMetadata<Double, ListDBSegmentedSimple.Record>> queryMetadata = databaseSimple.queryMetadata(record);
                InternalWindowImageListPanel v = new InternalWindowImageListPanel(this);
                int limite = 20;
                if(queryMetadata.size()<20){
                    limite=queryMetadata.size();
                }
                
                List<ResultMetadata> testing = new ArrayList();
                for (int i = 0; i < limite; i++) {
                    BufferedImage img2 = (BufferedImage) queryMetadata.get(i).getMetadata().getSource();
                    if(img2!=null){                       
//                        v.setImage(img);
                    }else{
                        if (queryMetadata.get(i).getMetadata().getImageLocator() != null) {
                            try {
                                img2 = ImageIO.read(queryMetadata.get(i).getMetadata().getImageLocator());
//                                v.setImage(img);
                            } catch (Exception ex) {
                                System.out.println("No existe dicha url");
                            }
                        }
                    }
                    testing.add(new ResultMetadata(queryMetadata.get(i).getMetadata().getImageLocator().toString(),img2));
                }
                v.addList(testing);
                this.escritorio.add(v);
                v.setTitle(databaseSimple.getDescriptorClass().getSimpleName()+" "+comparador.getClass().getSimpleName()+ " Posicion: "+posicion);
                v.setVisible(true);
            }
            setCursor(cursor);
        }

//        if (database != null) {
//            BufferedImage img = this.getSelectedImage();            
//            if (img != null) {                   
//                List<ListDB<BufferedImage>.Record> queryResult = database.query(img, 10);                
//                ImageListInternalFrame listFrame = new ImageListInternalFrame();
//                for(ListDB.Record r: queryResult){                         
//                    listFrame.add(r.getLocator(),r.getLocator().getFile());                   
//                }                
//                this.escritorio.add(listFrame);
//                listFrame.setVisible(true);
//            }
//            //Opcion con metadata
//            /*if (img != null) {
//                ListDB.Record q_record = database.new Record(img);
//                List<ResultMetadata> resultList = database.queryMetadata(q_record);
//                ImageListInternalFrame listFrame = new ImageListInternalFrame();
//                java.net.URL url; 
//                String label;
//                for(ResultMetadata r: resultList){   
//                    url = ((ListDB.Record)r.getMetadata()).getLocator();
//                    label = "Distance: "+r.getResult()+", File:"+url.getFile();
//                    listFrame.add(url,label);                   
//                }      
//                this.escritorio.add(listFrame);
//                listFrame.setVisible(true);
//            }*/
//        }
    }//GEN-LAST:event_botonSearchDBActionPerformed

    /**
     * Actualiza la barra de información de la base de datos.
     *
     * @param fichero
     */
    private void updateInfoDBStatusBar(String fichero) {
//        String infoDB = "Not open";
//        if (database != null) {
//            infoDB = fichero + " [#" + database.size() + "] [";
//            for (Class c : database.getDescriptorClasses()) {
//                infoDB += c.getSimpleName() + ",";
//            }
//            infoDB = infoDB.substring(0, infoDB.length() - 1) + "]";
//        }
//        this.infoDB.setText(infoDB);
        String infoDB = "Not open";
        if (databaseSimple != null) {
            infoDB = fichero + " [#" + databaseSimple.size() + "] [";
            infoDB += databaseSimple.getDescriptorClass().getSimpleName() + ",";
            infoDB = infoDB.substring(0, infoDB.length() - 1) + "]";
        }
        this.infoDB.setText(infoDB);
    }
    
    private void botonOpenDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonOpenDBActionPerformed
//        File file = new File("prueba.jmr.db");
//        try {
//            database = ListDB.open(file);
//            setDataBaseButtonStatus(false);
//            updateInfoDBStatusBar(file.getName());  
//        } catch (IOException | ClassNotFoundException ex) {
//            System.err.println(ex);
//        }         
        JFileChooser dlg = new JFileChooser();
        dlg.setAcceptAllFileFilterUsed(false);
        FileFilter filtro = new FileNameExtensionFilter("Files DAT", "dat", "DAT");
        dlg.addChoosableFileFilter(filtro);
        int resp = dlg.showOpenDialog(this);
        if (resp == JFileChooser.APPROVE_OPTION) {
            try {
                java.awt.Cursor cursor = this.getCursor();
                setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
                File f = dlg.getSelectedFile();
                databaseSimple = ListDBSegmentedSimple.open(f);
                setDataBaseButtonStatus(false);
                updateInfoDBStatusBar(f.getName());
                setCursor(cursor);
                
//                System.out.println(databaseSimple.toString());
//                this.mostrarDatabase();
            } catch (Exception ex) {
                System.err.println("Error reading the DAT");
            }
        }
    }//GEN-LAST:event_botonOpenDBActionPerformed

    private void botonSaveDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSaveDBActionPerformed
//        File file = new File("prueba.jmr.db");
//        try {
//            database.save(file);
//            updateInfoDBStatusBar(file.getName());
//        } catch (IOException ex) {
//            System.err.println(ex.getLocalizedMessage());
//        }    
        if (databaseSimple != null) {
            JFileChooser dlg = new JFileChooser();
            dlg.setAcceptAllFileFilterUsed(false);
            FileFilter filtroOBJ = new FileNameExtensionFilter("File DAT", "dat", "DAT");
            dlg.addChoosableFileFilter(filtroOBJ);

            int resp = dlg.showSaveDialog(this);
            if (resp == JFileChooser.APPROVE_OPTION) {
                try {
                    databaseSimple.save(dlg.getSelectedFile());
                } catch (Exception ex) {
                    System.err.println("Can't save database");
                }
            }
        }
    }//GEN-LAST:event_botonSaveDBActionPerformed

    private void botonRectanguloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRectanguloActionPerformed
        InternalWindowImageDrawable vi = (InternalWindowImageDrawable) escritorio.getSelectedFrame();
        if (vi != null && vi.getCanvas2DImage()!=null) {
            vi.getCanvas2DImage().setTool(1);
        }   
    }//GEN-LAST:event_botonRectanguloActionPerformed

    private void botonElipseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonElipseActionPerformed
        InternalWindowImageDrawable vi = (InternalWindowImageDrawable) escritorio.getSelectedFrame();
        if (vi != null && vi.getCanvas2DImage()!=null) {
            vi.getCanvas2DImage().setTool(2);
        }   
    }//GEN-LAST:event_botonElipseActionPerformed

    private void botonTrazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonTrazoActionPerformed
        InternalWindowImageDrawable vi = (InternalWindowImageDrawable) escritorio.getSelectedFrame();
        if (vi != null && vi.getCanvas2DImage()!=null) {
            vi.getCanvas2DImage().setTool(0);
        }     
    }//GEN-LAST:event_botonTrazoActionPerformed

    private void botonOtrosColoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonOtrosColoresActionPerformed
        Color nuevo = JColorChooser.showDialog(
                null,
                "Elige el nuevo color del trazo",
                this.botonOtrosColores.getBackground());
        this.botonOtrosColores.setBackground(nuevo);
        this.panelColor.setBackground(nuevo);
        JInternalFrame[] allFrames = escritorio.getAllFrames();
        for (JInternalFrame allFrame : allFrames) {
            if (allFrame instanceof InternalWindowImageDrawable) {
                ((InternalWindowImageDrawable) allFrame).getCanvas2DImage().setColor(nuevo);
            }
        }
        this.repaint();
    }//GEN-LAST:event_botonOtrosColoresActionPerformed

    private void comboTipoComparatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTipoComparatorActionPerformed
        this.updateComparator();
    }//GEN-LAST:event_comboTipoComparatorActionPerformed

    private void botonIncursionSimpleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonIncursionSimpleActionPerformed
        this.updateComparator();
    }//GEN-LAST:event_botonIncursionSimpleActionPerformed

    private void botonEqualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEqualActionPerformed
        this.updateComparator();
    }//GEN-LAST:event_botonEqualActionPerformed

    private void checkPosicionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkPosicionActionPerformed
        this.updateComparator();
    }//GEN-LAST:event_checkPosicionActionPerformed

    private void checkSinRepeticionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkSinRepeticionesActionPerformed
        this.updateComparator();
    }//GEN-LAST:event_checkSinRepeticionesActionPerformed

    private void botonBorrarShapesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBorrarShapesActionPerformed
        InternalWindowImageDrawable vi = (InternalWindowImageDrawable) escritorio.getSelectedFrame();
        if (vi != null && vi.getCanvas2DImage()!=null) {
            // Clear vShape
            vi.getCanvas2DImage().clear();
            this.repaint();
        }  
    }//GEN-LAST:event_botonBorrarShapesActionPerformed

    private void colorEscalableDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorEscalableDBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_colorEscalableDBActionPerformed

    private void mostrarDatabaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrarDatabaseActionPerformed
        java.awt.Cursor cursor = this.getCursor();
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        this.mostrarDatabase();
        setCursor(cursor);
    }//GEN-LAST:event_mostrarDatabaseActionPerformed

    private void aniadirDirectorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aniadirDirectorioActionPerformed
        if (this.databaseSimple != null) {
            JFileChooser dlg = new JFileChooser();
            dlg.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int resp = dlg.showOpenDialog(this);

            if (resp == JFileChooser.APPROVE_OPTION) {
                try {
                    java.awt.Cursor cursor = this.getCursor();
                    setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
                    File f = dlg.getSelectedFile();
                    File[] archivos = f.listFiles();

                    for (File file : archivos) {
                        if (file.isFile()) {
                            BufferedImage img = ImageIO.read(file);
                            if (img != null) {
                                ListDBSegmentedSimple.Record registro;
                                String nameCSV = file.getPath().replaceFirst("[.][^.]+$", "").concat(".csv");
                                File fileCSV = new File(nameCSV);
                                if (fileCSV.exists()) {
                                    ArrayList<Shape> shapes = CSVTools.OpenCSV(fileCSV);
                                    Region[] regiones = new Region[shapes.size()];
                                    for (int k = 0; k < shapes.size(); k++) {
                                        regiones[k] = new Region(img, shapes.get(k));
                                    }
                                    registro = databaseSimple.new Record(file.toURI().toURL(), regiones);
                                    databaseSimple.add(registro);
//                                    System.out.println(file.getName()+ " añadido");
//                                    resultadoDatabase.setImage(img,file.getName());
                                    resultadoDatabase.updateUI();
                                }else{
                                    Region r = new Region(img);
                                    registro = databaseSimple.new Record(file.toURI().toURL(), r);
                                    databaseSimple.add(registro);
//                                    System.out.println(file.getName() + " añadido SIN SHAPES.");
//                                    resultadoDatabase.setImage(img);
                                    resultadoDatabase.updateUI();
                                }
                            }
                        }
                    }
                    setCursor(cursor);
                    updateInfoDBStatusBar("New DB (not saved)");
                } catch (Exception ex) {
                    System.err.println("Error with the directory");
                }
            }
        }
    }//GEN-LAST:event_aniadirDirectorioActionPerformed

    private void verHerramientasDibujadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verHerramientasDibujadoActionPerformed
        this.barraHerramientas.setVisible(this.verHerramientasDibujado.isSelected());
    }//GEN-LAST:event_verHerramientasDibujadoActionPerformed

    private void verHerramientasDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verHerramientasDBActionPerformed
        this.barraBD.setVisible(this.verHerramientasDB.isSelected());
    }//GEN-LAST:event_verHerramientasDBActionPerformed

    private void verBarraEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verBarraEstadoActionPerformed
        this.barraEstado.setVisible(this.verBarraEstado.isSelected());
    }//GEN-LAST:event_verBarraEstadoActionPerformed
    
    /**
     * Imprime por pantalla una imagen que se pase por parámetro.
     *
     * @param img La imagen que se desee imprimir por pantalla
     */
    
    public void imprimirImagen(BufferedImage img) {
        InternalWindowImageDrawable vi = new InternalWindowImageDrawable(this);
        vi.setImage(img);
        vi.setSize(img.getWidth(),img.getHeight());
        this.escritorio.add(vi);
        vi.setVisible(true);
    }
    
        /**
     * Imprime por pantalla una imagen que se pase por parámetro, junto el csv
     *
     * @param img La imagen que se desee imprimir por pantalla
     * @param str La url de la imagen
     */
    
    public void imprimirImagen(BufferedImage img, String str) {
//        System.out.println(str);
        String sub = str.substring(6);
//        System.out.println(sub);
        String nameCSV = sub.replaceFirst("[.][^.]+$", "").concat(".csv");
        String replace = nameCSV.replace("%20", " ");
//        System.out.println(replace);
        File fileCSV = new File(replace);
        InternalWindowImageDrawable vi = new InternalWindowImageDrawable(this);
        vi.setImage(img);
        vi.setSize(img.getWidth(),img.getHeight());
        if (fileCSV.exists()) {
            // EJECUTAR ALGORITMO QUE ABRE UN FILE.CSV Y DEVUELVE UNOS SHAPES
//            System.out.println("Existe");
            vi.setCSV(fileCSV);
            ArrayList<Shape> shapes = CSVTools.OpenCSV(fileCSV);
            for (int i = 0; i < shapes.size(); i++) {
                vi.getCanvas2DImage().addShape(shapes.get(i));
            }
        }
                        
        this.escritorio.add(vi);
        vi.setVisible(true);
    }
    
    /**
     * Métodos para actualizar el tipo de comparador.
     * Dependiendo de las opciones elegidas, se actualizará el tipo de comparador usado.
     */
    
    private void updateComparator() {

        posicion = this.checkPosicion.isSelected();

        switch (this.comboTipoComparator.getSelectedIndex()) {
            case 0:
                if (!this.checkSinRepeticiones.isSelected()) {
                    if (this.botonIncursionSimple.isSelected()) {
                        this.comparador = new ComparatorMin(posicion);
                    } else if (this.botonEqual.isSelected()) {
                        this.comparador = new ComparatorMinEqual(posicion);
                    }
                } else {
                    if (this.botonIncursionSimple.isSelected()) {
                        this.comparador = new ComparatorMin(posicion);
                    } else if (this.botonEqual.isSelected()) {
                        this.comparador = new ComparatorMinEqual(posicion);
                    }
                }
                break;
            case 1:
                if (!this.checkSinRepeticiones.isSelected()) {
                    if (this.botonIncursionSimple.isSelected()) {
                        this.comparador = new ComparatorMax(posicion);
                    } else if (this.botonEqual.isSelected()) {
                        this.comparador = new ComparatorMaxEqual(posicion);
                    }
                } else {
                    if (this.botonIncursionSimple.isSelected()) {
                        this.comparador = new ComparatorMaxNoDuples(posicion);
                    } else if (this.botonEqual.isSelected()) {
                        this.comparador = new ComparatorMaxEqualNoDuples(posicion);
                    }
                }
                break;
            case 2:
                if (!this.checkSinRepeticiones.isSelected()) {
                    if (this.botonIncursionSimple.isSelected()) {
                        this.comparador = new ComparatorAvg(posicion);
                    } else if (this.botonEqual.isSelected()) {
                        this.comparador = new ComparatorAvgEqual(posicion);
                    }
                } else {
                    if (this.botonIncursionSimple.isSelected()) {
                        this.comparador = new ComparatorAvgNoDuples(posicion);
                    } else if (this.botonEqual.isSelected()) {
                        this.comparador = new ComparatorAvgEqualNoDuples(posicion);
                    }
                }
                break;
            default:
                break;
        }
    }
    
    /**
     * Métodos para mostrar las imágenes de la base de datos.
     * Crea una lista con todas las imágenes que hay en la base de datos.
     * Tiende a fallar con bases de datos muy grandes.
     */
    
    private void mostrarDatabase() {
        if (this.databaseSimple != null) {
            this.resultadoDatabase = new InternalWindowImageListPanel(this);
            for (int i = 0; i < databaseSimple.size(); i++) {
                if (databaseSimple.get(i).getImageLocator() != null) {
                    try {
                        BufferedImage img = ImageIO.read(databaseSimple.get(i).getImageLocator());
//                        File f = Paths.get(databaseSimple.get(i).getImageLocator().toURI()).toFile();
                        resultadoDatabase.setImage(img);
                        
                    } catch (Exception ex) {
                        System.err.println("Error reading the URL");
                    }
                }
            }
            
            this.escritorio.add(resultadoDatabase);
//            System.out.println(databaseSimple.getDescriptorClass().getSimpleName());
//            System.out.println(databaseSimple.getComparator().getClass().getSimpleName());
            resultadoDatabase.setTitle(databaseSimple.getDescriptorClass().getSimpleName());
            this.resultadoDatabase.setVisible(true);
        }
    }
        
    public class ListenAdditionsScrolled implements ChangeListener {
        public void stateChanged(ChangeEvent e) {}
    }
    
     /**
     * Devuelve el escritorio de la aplicación.
     *
     * @return el escritorio
     */
    public javax.swing.JDesktopPane getDesktop(){
        return this.escritorio;
    }
    
    // Variables no generadas automáticamente 
    ListDB<BufferedImage> database = null; 
    ListDBSegmentedSimple databaseSimple = null;
    InternalWindowImageListPanel resultadoDatabase = null;
    
    private boolean posicion = false;
    Comparator comparador = new ComparatorMin(false);
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aniadirDirectorio;
    private javax.swing.JToolBar barraArchivo;
    private javax.swing.JToolBar barraBD;
    private javax.swing.JToolBar barraComparadores;
    private javax.swing.JToolBar barraDescriptores;
    private javax.swing.JPanel barraEstado;
    private javax.swing.JToolBar barraHerramientas;
    private javax.swing.JButton botonAbrir;
    private javax.swing.JButton botonAddRecordDB;
    private javax.swing.JButton botonBorrarShapes;
    private javax.swing.JButton botonCloseDB;
    private javax.swing.JButton botonCompara;
    private javax.swing.JToggleButton botonElipse;
    private javax.swing.JToggleButton botonEqual;
    private javax.swing.JButton botonGuardar;
    private javax.swing.JToggleButton botonIncursionSimple;
    private javax.swing.JButton botonNewDB;
    private javax.swing.JButton botonOpenDB;
    private javax.swing.JButton botonOtrosColores;
    private javax.swing.JToggleButton botonRectangulo;
    private javax.swing.JButton botonSaveDB;
    private javax.swing.JButton botonSearchDB;
    private javax.swing.JToggleButton botonTrazo;
    private javax.swing.JCheckBox checkPosicion;
    private javax.swing.JCheckBox checkSinRepeticiones;
    private javax.swing.JMenuItem clear;
    private javax.swing.JMenuItem closeAll;
    private javax.swing.JRadioButtonMenuItem colorEscalable;
    private javax.swing.JRadioButtonMenuItem colorEscalableDB;
    private javax.swing.JRadioButtonMenuItem colorEstructurado;
    private javax.swing.JRadioButtonMenuItem colorEstructuradoDB;
    private javax.swing.JRadioButtonMenuItem colorMedio;
    private javax.swing.JRadioButtonMenuItem colorMedioDB;
    private javax.swing.JComboBox<String> comboTipoComparator;
    private javax.swing.JEditorPane editorOutput;
    private javax.swing.JDesktopPane escritorio;
    private javax.swing.ButtonGroup grupoBotonesHerramientas;
    private javax.swing.ButtonGroup grupoDescriptores;
    private javax.swing.ButtonGroup grupoDescriptoresDB;
    private javax.swing.ButtonGroup grupoIncursion;
    private javax.swing.JLabel infoDB;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JMenuItem menuAbrir;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem menuGuardar;
    private javax.swing.JMenu menuTools;
    private javax.swing.JMenuItem mostrarDatabase;
    private javax.swing.JPanel panelBarraHerramientas;
    private javax.swing.JPanel panelColor;
    private javax.swing.JPanel panelOutput;
    private javax.swing.JTabbedPane panelTabuladoInfo;
    private javax.swing.JPopupMenu popupMenuPanelOutput;
    private javax.swing.JPopupMenu popupMenuSeleccionDescriptores;
    private javax.swing.JPopupMenu popupMenuSeleccionDescriptoresDB;
    public javax.swing.JLabel posicionPixel;
    private javax.swing.JScrollPane scrollEditorOutput;
    private javax.swing.JPopupMenu.Separator separador1;
    private javax.swing.JLabel showPanelInfo;
    public javax.swing.JSplitPane splitPanelCentral;
    private javax.swing.JCheckBoxMenuItem verBarraEstado;
    private javax.swing.JCheckBoxMenuItem verHerramientasDB;
    private javax.swing.JCheckBoxMenuItem verHerramientasDibujado;
    // End of variables declaration//GEN-END:variables

}
