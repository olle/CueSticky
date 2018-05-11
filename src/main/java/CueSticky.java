import extra.ExtendedReader;
import extra.ExtendedWriter;
import extra.XThread;

/**
 * CueSticky.java - Projektuppgift HT 2004
 *
 * En samlingsklass som startpunkt för spelet och hantering och delegering
 * av uppstart, spelordning, poängräkning samt avslut.
 *
 * @version 1.2
 * @author Olof Törnström
 * @author Peter Ankerstål
 * @date 2004-11-12
 */
public class CueSticky {

	// INSTANSVARIABLER -------------------------------------------------------	
    private CSBall[]    gameBalls;
    private CSVector[]  gamePockets;
    private double      gameXDim;
    private double      gameYDim;
    private int         numShots;
    private int         points;
    private CSPicture   gamePicture;

    // KONSTRUKTORER ----------------------------------------------------------
    
    /** Standardkonstruktor för ett nytt spel med de traditionella dimen-
     *  sionerna på spelplanen 2:1 */
    public CueSticky() {
        this(2.0, 1.0);
    }
    
    /** Utökad konstruktor för valfri dimension på spelplanen */
    public CueSticky(double gameXDim, double gameYDim) {
        this.gameXDim = gameXDim;
        this.gameYDim = gameYDim;
        numShots = 0;
        points   = 0;
        gamePicture = new CSPicture(gameXDim, gameYDim);
        gamePockets = new CSVector[6];
        for (int i = 0; i < 6; i++) {
            CSVector newV = new CSVector(CSGameRules.X_HOLES_TABLE[i],
                                         CSGameRules.Y_HOLES_TABLE[i]);
            gamePockets[i] = newV;
        }
    }

    // MAIN-METODEN -----------------------------------------------------------

    /** Main-metod startpunkt för spelet CueSticky */
    public static void main(String[] args) {
    
        /* Initiera nytt spelobjekt */
        CueSticky theGame = new CueSticky();
        initGame(theGame);

        /* Huvudloop för programexekvering */
        while (true) {
            
            /* Hantera menyklick nytt spel */
            if (theGame.gamePicture.newGameCmd()) {
                initGame(theGame);
            }
            
            /* Hantera menyklick ladda spel */
            if (theGame.gamePicture.openGameCmd()) {
                initFromFile(theGame);
            }
            
            /* Hantera menyklick spara spel */
            if (theGame.gamePicture.saveGameCmd()) {
                saveToFile(theGame);
            }
        
            /* Ett klick med musen på spelytan omvandlas till en stöt */
            if (theGame.gamePicture.nyStot()) {
                theGame.addShot();
                CSVector clickDiff = theGame.gameBalls[0].getP().sub(
                    new CSVector(theGame.gamePicture.getStotX(),
                                 theGame.gamePicture.getStotY()));
                theGame.gameBalls[0].setV(clickDiff.conMult(8.3543));
            }
            
            /* Låt tråden sova */
            XThread.delay(interval);
            
            /* Visa händelseförlopp för spelet */
            theGame.move();
        }
    }

    // PUBLIKA METODER --------------------------------------------------------

    /** Flyttar bollar ett deltaT tidssteg framåt i tiden och undersöker för
     *  var och en av alla bollar, om någon den hamnat i ett hål, om bollarna
     *  kolliderar  samt om bollarna studsar mot någon vall. Respektive fall
     *  hanteras och poäng och bonus eller spelslut noteras. */
    public void move() {
        /* För var och en av bollarna som är med i spelet */
        for (int i = 0; i < getNumBalls(); i++) {
            /* Flytta aktuell boll */
            gameBalls[i].move(deltaT, alpha);           
            
            /* Undersök om bollen hamnat i något hål */
            if (gameBalls[i].inPocket(gamePockets)) {
                if (i == 0) {
                    removePoint();
                    gamePicture.setLabelText("Oops! Penalty point. "
                        + printStatus());
                    gameBalls[0].getV().set(0, 0);
                    gameBalls[0].getP().set(0.3, 0.5);
                } else if (gameBalls.length - 1 < 2) {
                    addPoint();
                    addBonus();
                    CSBall[] tmpBalls = new CSBall[gameBalls.length - 1];
                    System.arraycopy(gameBalls, 0, tmpBalls, 0, i);
                    System.arraycopy(gameBalls, i + 1, tmpBalls, i, tmpBalls.length - i);
                    setBalls(tmpBalls);
                    gamePicture.setLabelText("You win! Adding bonus! " + printStatus());
                    break;
                } else {                    
                    addPoint();
                    CSBall[] tmpBalls = new CSBall[gameBalls.length - 1];
                    System.arraycopy(gameBalls, 0, tmpBalls, 0, i);
                    System.arraycopy(gameBalls, i + 1, tmpBalls, i, tmpBalls.length - i);
                    setBalls(tmpBalls);
                    gamePicture.setLabelText("Ball in pocket " + printStatus());
                    break;
                }
            }
            
            /* Undersök om bollarna kolliderar */
            for (int n = 0; n < getNumBalls(); n++) {
                if (n != i) {
                    gameBalls[i].collission(gameBalls[n]);
                }
            }
            /* Undersök om bollen studsar mot en vägg */
            gameBalls[i].wall(getGameXDim(), getGameYDim());
        }        
        /* Rita om spelytan */
        gamePicture.draw(gameBalls);
    }

    /** Returnerar en boll ur listan på bollar som spelet för tillfället har.
     *  @param ballIndex är alltså ett tal från 0 (för den vita bollen) och
     *  någon av de andra bollarna 1 - n. */
    public CSBall getBall(int ballIndex) {
        return gameBalls[ballIndex];
    }
    
    /** Returnerar en array med spelets aktuella bollar */
    public CSBall[] getBalls() {
        return gameBalls;
    }
    
    /** Bestäm listan av bollar */
    public void setBalls(CSBall[] balls) {
        gameBalls = balls;
    }
    
    /** Ger antalet bollar i spelet för närvarande */
    public int getNumBalls() {
        return gameBalls.length;
    }

    /** Ger spelets dimension i x-led */
    public double getGameXDim() {
        return gameXDim;
    }
    
    /** Ger spelets dimension i y-led */
    public double getGameYDim() {
        return gameYDim;
    }
    
    /** Lägger till en poäng */
    public void addPoint() {
        points++;
    }
    
    /** Drar av en poäng */
    public void removePoint() {
        points--;
    }    
    
    /** Ger aktuell poängställning */
    public int getPoints() {
        return points;
    }
    
    /** Beräknar bonus */
    public void addBonus() {
        points = (int) (points / (0.003 * numShots));
    }
    
    /** Ger aktuellt antal skott */
    public int getNumShots() {
        return numShots;
    }
    
    /** Lägger till ett skott */
    public void addShot() {
        numShots++;
    }
    
    // PRIVATA METODER --------------------------------------------------------

    /** Ger aktuell status för spelet med poäng, skott och antal bollar kvar.
     */
    private String printStatus() {
        String status = " Points: " + getPoints()
                      + " Shots: " + getNumShots()
                      + " Balls: " + (getNumBalls() - 1);
        return status;
    }

    /** Initiera ett spel av typen 9-ball enligt konstanterna i klassen 
     *  CSGameRules */    
    private static void initGame(CueSticky theGame) {
        theGame.points = 0;
        theGame.numShots = 0;
        theGame.gameBalls = new CSBall[CSGameRules.X_BALLS_NINE_BALL.length];
        for (int i = 0; i < theGame.gameBalls.length; i++) {
            CSBall newBall = new CSBall(new CSVector(CSGameRules.X_BALLS_NINE_BALL[i],
                                                     CSGameRules.Y_BALLS_NINE_BALL[i]),
                                        new CSVector(0, 0),
                                        CSGameRules.BALL_RADIUS);
            theGame.gameBalls[i] = newBall;
        }
        theGame.gamePicture.setLabelText("Aim and break!");
    }
    
    /** Sparar ett pågående spel till spelfilen för senare fortsatt spel.
     *  Metoden skriver i klartext till en fil enligt ett standardiserat
     *  sätt där var och en av variablerna ges på enskilda rader:
     *  rad 1 - poäng
     *  rad 2 - antal stötar
     *  rad 3 - antal bollar "i"
     *  rad 4+i - bollen i's x position
     *  rad 4+i+1 - bollen i's y position 
     *  Vidare följer parvisa rader för i antal bollar. Detta protokoll
     *  måste sedan följas vid återinläsning av ett spel. */  
    private static void saveToFile(CueSticky theGame) {
        ExtendedWriter out = ExtendedWriter.getFileWriter(CSGameRules.SAVE_FILE);
        out.println(theGame.points);
        out.println(theGame.numShots);
        out.println(theGame.gameBalls.length);
        for (int i = 0; i < theGame.gameBalls.length; i++) {
            out.println(theGame.gameBalls[i].getP().getX());
            out.println(theGame.gameBalls[i].getP().getY());
        }
        out.flush();
        out.close();
    }
    
    /** Laddar sparade bollar från fil och initierar ett nytt spel från de
     *  sparade positionerna */
    private static void initFromFile(CueSticky theGame) {
        ExtendedReader in = ExtendedReader.getFileReader(CSGameRules.SAVE_FILE);
        theGame.points = in.readInt();
        theGame.numShots = in.readInt();
        int i = in.readInt();
        CSBall[] loadBalls = new CSBall[i];
        for (int t = 0; t < i; t++) {
            CSBall newBall = new CSBall(new CSVector(in.readDouble(), in.readDouble()),
                                        new CSVector(0, 0),
                                        CSGameRules.BALL_RADIUS);
            loadBalls[t] = newBall;
        }
        theGame.gameBalls = loadBalls;
        String status = " Points: " + theGame.points
                      + " Shots: " + theGame.numShots
                      + " Balls: " + (i - 1);
        theGame.gamePicture.setLabelText(status);
        in.close();
    }

    // PRIVATA KONSTANTER -----------------------------------------------------
    private static final int interval = 5;
    private static final double deltaT = 0.006;
    private static final double alpha = 0.9974878; 
}
