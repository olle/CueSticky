
/**
 * CSCSVector.java - Projektuppgift HT2004
 * 
 * En vektor-klass som hanterar den grundläggande aritmetiken för vektorer.
 * Implementerad för att användas i projektuppgiften "biljardspel" HT2004.
 *
 * @version 1.2
 * @author Peter Ankerstål
 * @author Olof Törnström
 * @date 2004-11-12
 */
public class CSVector {

    // INSTANSVARIABLER -------------------------------------------------------
    private double x, y;

    // KONSTRUKTORER ----------------------------------------------------------
    /** Standardkontruktorn */
    public CSVector() {
        x = 0;
        y = 0;
    }

    /**  Skapar vektor med invärden a och b */ 
    public CSVector(double a, double b) {
        x = a;
        y = b;
    }
    
    /** Sätter vektorn till a, b */ 
    public void set(double a, double b) {
        x = a;
        y = b;
    }    

    /** Sätter vektorns x-värde */
    public void setX(double a) {
        x = a; 
    }

    /** Sätter vektorn y-värde */
    public void setY(double b) {
        y = b;
    }

    /** Retunerar vektorns x-värde */
    public double getX() {
        return x;
    }

    /** Retunerar vektorns y-värde */
    public double getY() {
        return y;
    }  

    /** Retunerar skalärprodukten av vektorn och v */
    public double dotProd(CSVector v) {
        return v.getX() * x + v.getY() * y;
    } 

    /** Adderar Vektorn och v */ 
    public CSVector add(CSVector v) {
	return new CSVector((v.getX() + x), (v.getY() + y));
    }

    /** Subtraherar Vektorn och v */
    public CSVector sub(CSVector v) {
        return new CSVector((x - v.getX()), (y - v.getY()));
    }

    /** Kalyklerar absolutbeloppet av vektorn */ 
    public double abs() {
        return Math.sqrt( x * x + y * y);
    }

    /** Multiplicerar vektorn med en konstant c */
    public CSVector conMult(double c) {
	return new CSVector((c * x),(c * y)); 
    }

    /** Ger vektorns värden i text, för lagring av spel i spelfil */
    public String toString() {
        return ("" + x + " " + y);
    }
}
