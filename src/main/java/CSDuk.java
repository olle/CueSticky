import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/** CSDuk.java - Projektuppgift HT 2004
 *
 * En specialskriven JPanel-klass som implementerar uppritning av ett biljard-
 * bord. Modifierad fr att f full koddokumentation och bttre kod enligt
 * "the java coding convention", samt fr att rtta till de fel som fanns i
 * koden. OBS! klassen hrstammar helt ifrn den givna Duk.java-klassen.
 *
 * @version 1.2
 * @modified Olof Trnstrm
 * @modified Peter Ankerstl
 * @date 2004-11-11
 */
public class CSDuk extends JPanel {

	// INSTANSVARIABLER -------------------------------------------------------	
    double[] boll_x;
    double[] boll_y;
    double   boll_r;
    
    double[] hal_x;
    double[] hal_y;
    double   hal_r;
    
    double   vall_w;
    
    double boardMaxX;
    double boardMaxY;
    int    xPicture;
    int    yPicture;

	// KONSTRUKTORER ----------------------------------------------------------
		
	/** Konstruktor fr initiering av en CSDuk med en viss storlek */
    public CSDuk(double sizeX, double sizeY, int xPix, int yPix) {
		boardMaxX = sizeX;
		boardMaxY = sizeY;
		xPicture = xPix;
		yPicture = yPix;
    }

	// PUBLIKA METODER --------------------------------------------------------
    
	/** Bestmmer bollarnas egenskaper genom att ta tre listor fr dess x-
	 *  koordinater, y-koordinater och radier. */
	void set_balls(double[] bollX, double[] bollY, double bollR) {
		boll_x = bollX;
		boll_y = bollY;
        boll_r = bollR;
    }
	
	/** Stter hlens egenskaper genom tre listor fr hlens x-koordinater,
	 *  y-koordinater och radier. */
    public void set_holes(double[] halX, double[] halY, double halR) {
		hal_x = halX;
		hal_y = halY;
        hal_r = halR;
    }
    
    /** Stter tjockleken p vallarna */
    public void set_walls(double wallWidth) {
        vall_w = wallWidth;
    }
		
	/** verlagrad metod fr uppritning av komponenten. Ritar upp aktuella
	 *  positioner p bollarna */
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int drawX, drawY, drawR,ii;
		int wallWidth;
		int wallHeight;
		
		// Ritar ramytan (vallar)
        g.setColor(new Color(142, 89, 33));
        g.fillRect(0, 0, xPicture, yPicture);

		// Ritar duken innanfr vallarna
		g.setColor(new Color(24, 155, 81));
		wallWidth =  (int) ((vall_w * xPicture) / boardMaxX);
		wallHeight = (int) ((vall_w * yPicture) / boardMaxY);
		g.fillRect(0 + wallWidth, 0 + wallWidth,
                   xPicture - wallWidth * 2, yPicture - wallWidth * 2);
		
        // Ritar ut hlen med radien drawR
        drawR = (int) ((hal_r * xPicture) / boardMaxX); 
        for (ii = 0; ii < hal_x.length; ii++) {
            g.setColor(Color.black);
			// Berkna relativa positioner frn bordets skala
			drawX = (int) ((hal_x[ii] * xPicture) / boardMaxX);
			drawY = (int) ((hal_y[ii] * yPicture) / boardMaxY);
            // Rita hlen p bordet
			g.fillOval(drawX - drawR, drawY - drawR, drawR * 2, drawR * 2);
        }

        // Ny radie gller fr bollarna
        drawR = (int) ((boll_r * xPicture) / boardMaxX); 

        // Ritar den vita bollen, som alltid har index 0
        g.setColor(Color.white);
        drawX = (int) ((boll_x[0] * xPicture) / boardMaxX);
        drawY = (int) ((boll_y[0] * yPicture) / boardMaxY);
        g.fillOval(drawX - drawR, drawY - drawR, drawR * 2, drawR * 2);
		
        // Ritar alla frgade bollar
        for (ii = 1; ii < boll_x.length; ii++) {
            g.setColor(new Color(214, 68, 34));
			drawX = (int) ((boll_x[ii] * xPicture) / boardMaxX);
			drawY = (int) ((boll_y[ii] * yPicture) / boardMaxY);
			g.fillOval(drawX - drawR, drawY - drawR, drawR * 2, drawR * 2);
            g.setColor(Color.white);
            // g.drawString("" + ii, drawX - 4, drawY + 4);
        }
    }
}
