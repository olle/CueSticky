
/**
 * CSBall.java - Projektuppgift HT2004
 *
 * Klassen boll är själva bollen och den håller reda på när den krockar med
 * vallar och andra bollar.
 *
 * @version 1.2
 * @author Peter Ankerstål
 * @author Olof Törnström
 * @date 2004-11-12
 */
public class CSBall {

	// INSTANSVARIABLER -------------------------------------------------------
	private double radius;
	private double mass;
	private CSVector p;
	private CSVector v;

	// KONSTRUKTORER ----------------------------------------------------------

	/**
	 * Standardkonstruktor för klassen skapar en boll enligt radien som anges
	 * CSGameRules
	 */
	public CSBall() {
		radius = CSGameRules.BALL_RADIUS;
		mass = radius * radius;
		p = new CSVector(0, 0);
		v = new CSVector(0, 0);
	}

	/** Alternativ konstruktor med valfri positions och hastighetsvektor */
	public CSBall(CSVector p, CSVector v) {
		radius = CSGameRules.BALL_RADIUS;
		mass = radius * radius;
		this.p = p;
		this.v = v;
	}

	/**
	 * Alternativ konstruktor för valfri positions och hastighetsvektor samt radie
	 */
	public CSBall(CSVector p, CSVector v, double r) {
		radius = r;
		mass = radius * radius;
		this.p = p;
		this.v = v;
	}

	/** Ger bollens positionsvektor */
	public CSVector getP() {
		return p;
	}

	/** Ger bollens radie */
	public double getRadius() {
		return radius;
	}

	/** Bestämmer bollens positionsvektor */
	public void setP(CSVector p) {
		this.p = p;
	}

	/** Ger bollens hastighetsvektor */
	public CSVector getV() {
		return v;
	}

	/** Bestämmer bollens hastighetsvektor */
	public void setV(CSVector v) {
		this.v = v;
	}

	/**
	 * Flyttar bollen deltaT med trögheten alpha enligt dess hastighets- vektor.
	 */
	public void move(double deltaT, double alpha) {
		p = p.add(v.conMult(deltaT));
		v = v.conMult(alpha);
	}

	/**
	 * Undersker om en kollision mellan bollen och en annan boll har inträffat och
	 * korrigerar hastighetsvektorn enligt detta
	 */
	public void collission(CSBall b) {
		CSVector sDiff = b.getP().sub(p);
		CSVector vDiff = b.getV().sub(v);
		if (vDiff.dotProd(sDiff) < 0 && p.sub(b.getP()).abs() < (b.getRadius() + radius)) {

			double massCo = 2 * b.mass / (mass + b.mass);
			double dotQ = vDiff.dotProd(sDiff) / sDiff.dotProd(sDiff);
			v = v.add(sDiff.conMult(dotQ).conMult(massCo));
			b.setV(b.getV().sub(sDiff.conMult(dotQ).conMult(massCo)));
		}
	}

	/**
	 * Undersöker om en kollision mellan bollen och någon vall har inträffat och
	 * korrigerar hastighetsvektorn enligt detta
	 */
	public void wall(double xMax, double yMax) {
		double xMaxWall = xMax - CSGameRules.WALL_WIDTH_N_HEIGHT;
		double xMinWall = 0 + CSGameRules.WALL_WIDTH_N_HEIGHT;
		double yMaxWall = yMax - CSGameRules.WALL_WIDTH_N_HEIGHT;
		double yMinWall = 0 + CSGameRules.WALL_WIDTH_N_HEIGHT;

		if (p.getY() - radius < yMinWall && v.getY() < 0) {
			v.setY(-v.getY());
		} else if (p.getY() + radius > yMaxWall && v.getY() > 0) {
			v.setY(-v.getY());
		} else if (p.getX() - radius < xMinWall && v.getX() < 0) {
			v.setX(-v.getX());
		} else if (p.getX() + radius > xMaxWall && v.getX() > 0) {
			v.setX(-v.getX());
		}
	}

	/** Undersöker om bollen är i ett hål på bordet eller ej */
	public boolean inPocket(CSVector[] pockets) {
		boolean result = false;
		for (int i = 0; i < pockets.length; i++) {
			if (p.sub(pockets[i]).abs() < CSGameRules.HOLE_RADIUS) {
				result = true;
			}
		}
		return result;
	}
}
