import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/** CSPicture.java - Projektuppgift HT 2004
 *
 * En klass för främst grafisk hantering av biljardbordet i projektuppgiften
 * "biljardspel" ht04. Modifierad främst för att få full koddokumentation och
 * bättre kod enligt "the java coding convention" men även för att rätta till
 * och specialdesigna spelet efter önskemål. OBS! klassen härstammar helt från
 * den givna klassfilen Picture.java.
 *
 * @version 1.2
 * @modified Olof Törnström
 * @modified Peter Ankerstål
 * @date 2004-11-11
 */
public class CSPicture extends JFrame {
	
	// INSTANSVARIABLER -------------------------------------------------------
    private boolean clickRecorded;  // Har ny stöt gjorts?
    private double  stot_x, stot_y; // Vilka är stötkoordinaterna 
    private double  boardMaxX;      // bordets stolek som flyttal
    private double  boardMaxY;		
    private int     xPicture;       // bordets stolek i pixlar
    private int     yPicture;
    private CSDuk   duk;
	private int     pixelScale = 370; /* antal pixlar per hel skalenhet */
    private int     panelHeight = 40; /* antal pixlar i höjd för statuspanelen */
    
    private JMenuBar    menuBar;
    private JMenu       fileMenu;
    private JMenuItem   newItm;
    private JMenuItem   openItm;
    private JMenuItem   saveItm;
    private boolean     newClicked  = false;
    private boolean     openClicked = false;
    private boolean     saveClicked = false;
    
    private JPanel      statusBar;
    private JLabel      statusText;
    
	// KONSTRUKTORER ----------------------------------------------------------

	/** Standardkonstruktor för ett biljardbord med bestämd standardstorlek
	 *  mätt i skalenheter */
    public CSPicture () {
        this(2.0,1.0);
    }

	/** Specialkonstruktor för initiering av ett biljardbord med valfri
	 *  storlek */
    public CSPicture(double x_size, double y_size) {
        clickRecorded = false;
		stot_x = 0.0;
		stot_y = 0.0;
        boardMaxX = x_size;
        boardMaxY = y_size;
        xPicture = (int) x_size * pixelScale;
        yPicture = (int) y_size * pixelScale;
        //yPicture += panelHeight; /* Adderar i höjd för statuspanelen */

        // Initiera duken och fönstret utan att visa det
        duk = new CSDuk(boardMaxX, boardMaxY, xPicture, yPicture);
        duk.setPreferredSize(new Dimension(xPicture, yPicture));
		duk.setBackground(Color.green);
		
		/** Lägger till muslyssnare som reagerar på musklick över duken och
		 *  sparar koordinaterna samt flaggar att ett klick har gjorts. */
		duk.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				stot_x = e.getX() * boardMaxX / xPicture;
				stot_y = e.getY() * boardMaxY / yPicture;
				clickRecorded = true;
			}
		});

        /* Lägg till duken till CSPicture/JFramens content pane */
		getContentPane().add(duk, BorderLayout.NORTH);        
		setResizable(false);
        
        /* Sätt upp menyn och listeners för den */
        menuBar  = new JMenuBar();
        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);
        newItm = new JMenuItem("New Game");
        newItm.setAccelerator(KeyStroke.getKeyStroke(
                    KeyEvent.VK_N, ActionEvent.META_MASK));
        newItm.getAccessibleContext().setAccessibleDescription(
                    "Start at new game");
        newItm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newClicked = true;
            }
        });
        fileMenu.add(newItm);
        openItm = new JMenuItem("Open...");
        openItm.setAccelerator(KeyStroke.getKeyStroke(
                    KeyEvent.VK_O, ActionEvent.META_MASK));
        openItm.getAccessibleContext().setAccessibleDescription(
                    "Open a saved game from disk");
        openItm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openClicked = true;
            }
        });
        fileMenu.add(openItm);
        fileMenu.addSeparator();
        saveItm = new JMenuItem("Save Game");
        saveItm.setAccelerator(KeyStroke.getKeyStroke(
                    KeyEvent.VK_S, ActionEvent.META_MASK));
        saveItm.getAccessibleContext().setAccessibleDescription(
                    "Save this game to disk");
        saveItm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveClicked = true;
            }
        });
        fileMenu.add(saveItm);
        setJMenuBar(menuBar);
        
        /* Sätt upp och lägg till panel för poäng och statusmeddelanden */
        statusBar  = new JPanel();
        statusText = new JLabel("Welcome to CueSticky!");
        statusBar.setSize(xPicture, panelHeight);
        statusBar.add(statusText);
        getContentPane().add(statusBar, BorderLayout.SOUTH);
        
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

	// PUBLIKA METODER --------------------------------------------------------

    /** Ritar upp biljarbordet och bollarna från en lista av aktuella bollar i
     *  spel */
    public void draw(CSBall[] currBalls) {
        
        duk.set_walls(CSGameRules.WALL_WIDTH_N_HEIGHT);
        duk.set_holes(CSGameRules.X_HOLES_TABLE,
                      CSGameRules.Y_HOLES_TABLE,
                      CSGameRules.HOLE_RADIUS);
        
        double[] xBalls = new double[currBalls.length];
        double[] yBalls = new double[currBalls.length];
        
        for (int n = 0; n < currBalls.length; n++) {
            xBalls[n] = currBalls[n].getP().getX();
            yBalls[n] = currBalls[n].getP().getY();      
        }
        
        duk.set_balls(xBalls, yBalls, currBalls[0].getRadius());
        
        if (!isVisible()) {
            setVisible(true);
        }
        
        duk.repaint();
        
    }

	/** Ritar upp ytan och gör den synlig efter initiering */
    public void draw(double[] boll_x, double[] boll_y, double boll_r,
                     double[] hal_x, double[] hal_y, double hal_r,
					 double wall_w) {
		
		// Bestämmer egenskaper för vallar, bollar och hål
        duk.set_walls(wall_w);
		duk.set_balls(boll_x, boll_y, boll_r);
		duk.set_holes(hal_x, hal_y, hal_r);

		// Visa den grafiska borsytan om den inte redan syns
        if (!isVisible()) {
            setVisible(true);
        }
		
		// Rita om bordsytan för att visa uppdateringar
        duk.repaint();
    }

    /** Returnera om new-kommandot i menyn är klickat eller ej */
    public boolean newGameCmd() {
        if (newClicked) {
            newClicked = false;
            return true;
        }
        return newClicked;
    }
    
    /** Returnera om open-kommandot i menyn är klickat eller ej */
    public boolean openGameCmd() {
        if (openClicked) {
            openClicked = false;
            return true;
        }
        return openClicked;
    }
    
    /** Returnera om save-kommandot i menyn är klickat eller ej */
    public boolean saveGameCmd() {
        if (saveClicked) {
            saveClicked = false;
            return true;
        }
        return saveClicked;
    }
    
    /** Sätter texten i statusraden */
    public void setLabelText(String text) {
        statusText.setText(text);
    }

	/** Avgör om en ny stöt har gjorts genom att undersöka flaggan för om ett
	 *  musklick har registrerats */
    public boolean nyStot() {
        return clickRecorded;
    }

	/** Läser av x-koordinaten för musklicket */ 
    public double getStotX() {
        clickRecorded = false;
        return stot_x;
    }
	
	/** Läser av y-koordinaten för musklicket */ 
    public double getStotY() {
        clickRecorded = false;
        return stot_y;
    }

	/** Anger om fönstret fortfarande är öppet eller inte */
    public boolean isClosed() {
		return !isVisible();
    }
}
