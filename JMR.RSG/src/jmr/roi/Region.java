/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.roi;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;

/**
 * Clase que se encargará de definir una Region de una imagen.
 * Dicho método se caracteriza de que necesita una imagen y una figura. Una imagen
 * puede tener varias regiones, éste método se encargará de definir una de ellas. 
 * Las regiones vendrán representadas por figuras del tipo Shape. En caso de que no
 * se destaque ninguna de ellas por una figura, la región utilizará el tamaño completo
 * de la imagen para crear la región.
 * 
 * Adicionalmente, esta clase integra un iterador para recorrer dichas regiones pixel
 * a pixel.
 *
 * @author Ramón
 */
public class Region{
    
    private BufferedImage source = null;
    private transient Shape myRegion = null;
    
    /**
     * Crea una nueva region a partir de una imagen. 
     * El shape de la region será el creado a partir del tamaño de la imagen.
     * 
     * @param source imagen utilizada para crear la region
     */
    public Region(BufferedImage source){
        this.source = source;
        /*
        Rectangle(int width, int height)
        Constructs a new Rectangle whose upper-left corner is at (0, 0) in the coordinate space, and whose width and height are specified by the arguments of the same name.
        */
        this.myRegion = new Rectangle(source.getWidth(),source.getHeight());
    }
    
    /**
     * Crea una nueva region a partir de una imagen y una figura.
     * 
     * @param source imagen utilizada para crear la region
     * @param region figura utilizada para identificar la region
     */
    
    public Region(BufferedImage source, Shape region){
        this.source = source;
        this.myRegion = region;
    }
    
    /**
     * Metodo para comprobar si un punto existe dentro de mi region.
     * 
     * @param p punto Point2D que se quiere comprobar que está dentro de mi region
     * @return verdadero o falso en caso de que exista o no
     */
    
    public boolean contains(Point2D p){
        return myRegion.contains(p);
    }
    
    /**
     * Metodo para comprobar si una coordenada está dentro de mi region
     * 
     * @param x coordenada x
     * @param y ordenada y
     * 
     * @return verdadero o falso en caso de que exista o no
     */
    
    public boolean contains(int x, int y){
        return myRegion.contains(x,y);
    }
    
    /**
     * Devuelve la localización de mi region
     * 
     * @return punto donde está mi región localizada
     */
    public Point getLocation(){
        return myRegion.getBounds().getLocation();
    }
    
    /**
     * Devuelve el bounds de mi region
     * 
     * @return rectangulo que contiene la figura de mi region
     */
    
    public Rectangle2D getBounds(){
        return myRegion.getBounds2D();
    }
    
    /**
     * Método que permite calcular el centro de mi region.
     * Descompone la figura en pathiterator y se queda con aquellos puntos que
     * se utilizan para recomponer dicha figura. Dependiendo del tipo de composición
     * se obtendrán uno o varios puntos. Finalmente, a partir de los puntos obtenidos
     * se calculará el punto centra de mi region.
     * 
     * @return el punto que representa el centro de mi region.
     */
    
    public Point2D getCentroid() {
        PathIterator pathIterator = this.myRegion.getPathIterator(new AffineTransform());
        float[] coords = new float[6];
        float count = 0;
        float allX = 0;
        float allY = 0;
        while (!pathIterator.isDone()) {
            switch (pathIterator.currentSegment(coords)) {
//                case PathIterator.SEG_MOVETO:
//                    System.out.printf("move to x1=%f, y1=%f\n",
//                    coords[0], coords[1]);
//                    // No añadiendo este punto me aseguro de que no se añada al principio, sino al final
//                    break;
                case PathIterator.SEG_LINETO:
//                    System.out.printf("line to x1=%f, y1=%f\n",
//                    coords[0], coords[1]);
                    allX += coords[0];
                    allY += coords[1];
                    count += 1;
                    break;
                case PathIterator.SEG_QUADTO:
//                    System.out.printf("quad to x1=%f, y1=%f, x2=%f, y2=%f\n",
//                    coords[0], coords[1], coords[2], coords[3]);
                    allX += coords[0] + coords[2];
                    allY += coords[1] + coords[3];
                    count += 2;
                    break;
                case PathIterator.SEG_CUBICTO:
//                    System.out.printf("cubic to x1=%f, y1=%f, x2=%f, y2=%f, x3=%f, y3=%f\n",
//                    coords[0], coords[1], coords[2], coords[3], coords[4], coords[5]);
                    allX += coords[0] + coords[2] + coords[4];
                    allY += coords[1] + coords[3] + coords[5];
                    count += 3;
                    break;
//                case PathIterator.SEG_CLOSE:
//                    System.out.printf("close\n");
//                    break;
            }
            pathIterator.next();
        }

        Point.Float pt = new Point.Float((allX / count), (allY / count));
//        System.out.println(pt);
        return pt;
    }
    
    /**
     * Obtener el ancho de mi region.
     * 
     * @return ancho de la region
     */
    public double getWidth() {
        return myRegion.getBounds().getWidth();
    }
    
    /**
     * Devuelve el alto de mi region
     * 
     * @return alto de la region
     */

    public double getHeight() {
        return myRegion.getBounds().getHeight();
    }
    
    /**
     * Devuelve la imagen de mi region
     * 
     * @return imagen correspondiente a la region
     */
    
    public BufferedImage getSource(){
        return this.source;
    }
    
    /**
     * Devuelve la figura que compone mi region
     * 
     * @return figura (shape) que delimita mi region
     */
    
    public Shape getShape(){
        return this.myRegion;
    }
    
//    public Point2D.Double calculateCentroid() {
//        return new Point2D.Double(this.getX()+this.getWidth()/2, this.getY()+this.getHeight()/2);
//    } 
    
    /**
     * Método que se encarga de volvar pixel a pixel una region en imagen.
     * El iterador de region se encargará de recorrer la imagen solo en aquella
     * zona donde esté mi figura y devolverá solo la parte de la imagen que
     * está delimitada por dicha figura.
     * 
     * @return imagen delimitada por la region
     */
    
    public BufferedImage volcarImagen(){
        miIterador it = new miIterador();
        BufferedImage resultado = new BufferedImage((int)getWidth(),(int)getHeight(), BufferedImage.TYPE_INT_ARGB);
        Color miColor;
        
        while(it.hasNext()){
            // Busco el siguiente color
            miColor = it.next();
//            System.out.println("X: "+(it.getX()-this.myRegion.getBounds().x)+" Y: "+(it.getY()-this.myRegion.getBounds().y));
            resultado.setRGB(it.getX()-this.myRegion.getBounds().x, it.getY()-this.myRegion.getBounds().y, miColor.getRGB());
        }
        
        return resultado;
    }
    
    /**
     * Iterador personalizado de la clase Region.
     * 
     * @return iterador de region
     */
    
    public Iterator iteradorPersonalizado() {
        return new miIterador();
    }
    
    /**
     * Clase interna iteradora que se mueve por los pixeles de la imagen (color).
     * 
     * Adicionalmente implementa varios métodos indispensables para futuras operaciones.
     * 
     */
    
    public class miIterador implements Iterator<Color> {
        
        Point2D p;
        // Obtengo los bounds de mi figura personalizada
        Rectangle2D misBounds = myRegion.getBounds();
        // De dichos bounds, obtengo el alto y ancho
        double alto = misBounds.getHeight();
        double ancho = misBounds.getWidth();
            
        // De la misma forma, los limites
        double limX = misBounds.getX()+ancho;
        double limY = misBounds.getY()+alto;
        
        private Color color;
        
        double miX;
        double miY;
        
        /**
         * Constructor del iterador, cuyo punto se inicializa donde empieza el rectángulo.
         */
        
        public miIterador() {
            p = new Point2D.Double(-10, -10);
            miX = p.getX();
            miY = p.getY();
//            this.buscarSiguiente();
        }
        
        /**
         * Metodo para comprobar que puede seguir moviendose el iterador.
         * 
         * @return variable booleana que indica si puede o no.
         */
        @Override
        public boolean hasNext(){
            /* Para comprobar que no se ha llegado al final, dicho punto
            no debe ser mayor que la X + anchura ni menor que la Y - altura
            */
            return miX<limX && miY<limY; // Aquí utilizo las variablex miX y miY, que son las que aumentan, para ver si hay siguiente
            // evitando asi que el punto coja valores de más
            // si en su lugar cogiese p.getY() haria una llamada de más, por lo que se obtendria un valor de más
        }
        
        /**
         * Se encarga de buscar el siguiente punto al que mover el iterador.
         * 
         */
        
        public void buscarSiguiente() {
//            double miX = p.getX();
//            double miY = p.getY();
            //miX++;
            //while (!(myRegion.contains(p)) && this.hasNext()){
            // Hago la primera llamada
//            miX++;
//            if(miX>=limX){
//                miY++;
//                miX=misBounds.getX();
//            }
//            if (!(miY>=limY)) {
            
            // Compruebo que dicho punto esté dentro
            do {
//                System.out.println("MiX: "+miX+", miY: "+miY);
                this.p.setLocation(miX, miY);
                //System.out.println("No contiene el punto: " +p);
                miX++;
                if (miX >= limX) {
                    miY++;
//                    System.out.println(miY);
                    miX = misBounds.getX();
//                    miX = misBounds.getX();
//                    if (miY >= limY) {
//                        System.out.println("Limite: " + limY + " valor de y" + p.getY());
//                    } else {
//                        this.p.setLocation(misBounds.getX(), miY);
//                    }
//                } else {
//                    this.p.setLocation(miX, miY);
                }
            } while (!myRegion.contains(p) && miY < limY);
//            }          
        }
        
        
        /**
         * Se obtiene el siguiente pixel del iterador.
         * 
         * @return objeto color refernte al pixel de la imagen
         */
        @Override
        public Color next() {
            /* En primer lugar, aumentaré la coordenada X */
//            double miX = p.getX();
//            double miY = p.getY();
//            miX++;
//            /* Si la cordenada supera a X + ancho, se hara cero y se
//            restará 1 a la coordenada Y; en caso contrario, se quedará
//            como esta
//            */
//            if (miX >= limX) {
//                miY++;
////                if(miY>=limY){
////                   throw new NoSuchElementException("No more pixels"); 
////                }
//                if (miY >= limY) {
//                    System.out.println("NOPE2");
//                } else {
//                    System.out.println("chivato2");
//                    this.p.setLocation(misBounds.getX(), miY);
//                }
//            } else {
//                this.p.setLocation(miX, miY);
//            }
//            
//            color = new Color(source.getRGB((int)p.getX(), (int)p.getY()));
//            if(!(myRegion.contains(p)) && this.hasNext())

            // Busco el primer punto de mi region
            buscarSiguiente();
            // Una vez lo tenga, saco el color al que apunte ese punto
            color = new Color(source.getRGB((int)p.getX(), (int)p.getY()));
//            buscarSiguiente();
            
            return color;
        }
        
        /**
         * Obtiene la coordenada del pixel
         * 
         * @return coordenada x
         */

        public int getX() {
            return (int)p.getX();
        }

        /**
         * Obtiene la ordenada del pixel
         * 
         * @return ordenada y
         */
        public int getY() {
            return (int)p.getY();
        }
    }    
    

    
    
}
