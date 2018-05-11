
/**
 * CSGameRules.java - Projektuppgift HT 2004
 *
 * En klass med konstanter som definierar generella egenskaper för biljard-
 * spelet och även unika egenskaper för spel av olika typer. Speltyperna är
 * implementerade enligt de listor med uppställningen för en viss typ av spel
 * och bollarnas x- och y-koordinater. Stöd finns för speltyperna<br>
 *  <ul><b>Speltyper:</b>
 *  <li>9-ball</ul>
 *
 * @version 1.2 
 * @author Olof Törnström
 * @author Peter Ankerstål
 * @date 2004-11-11
 */
public class CSGameRules {

	// PUBLIKA VARIABLER ------------------------------------------------------

    /** Filnamn för sparat spel på disk */
    public static final String   SAVE_FILE = "cuestickygame.sav";

    /** Tjockleken på biljardbordets vallar */
    public static final double   WALL_WIDTH_N_HEIGHT = 0.050;
    
    /** Hålens radie */
    public static final double   HOLE_RADIUS = 0.0485;
    
    /** Bollarnas radie. Alla bollar använder samma radie */
    public static final double   BALL_RADIUS = 0.029;

    /** X-koordinater för uppställningen av bollar i spelet 9-ball */
    public static final double[] X_BALLS_NINE_BALL = {0.3, 1.4, 1.4, 1.4, 1.347, 1.347, 1.453, 1.453, 1.506, 1.294};
    
    /** Y-koordinater för uppställningen av bollar i spelet 9-ball */
    public static final double[] Y_BALLS_NINE_BALL = {0.5, 0.437, 0.5, 0.563, 0.47, 0.53, 0.47, 0.53, 0.5, 0.5};
    
    /** X-koordinater för hålen på biljardbordet */
    public static final double[] X_HOLES_TABLE = {0 + WALL_WIDTH_N_HEIGHT + (WALL_WIDTH_N_HEIGHT / 3),
                                                  0 + WALL_WIDTH_N_HEIGHT + (WALL_WIDTH_N_HEIGHT / 3),
                                                  1, 1,
                                                  2 - WALL_WIDTH_N_HEIGHT - (WALL_WIDTH_N_HEIGHT / 3),
                                                  2 - WALL_WIDTH_N_HEIGHT - (WALL_WIDTH_N_HEIGHT / 3)};

    /** Y-koordinater för hålen på biljardbordet */
    public static final double[] Y_HOLES_TABLE = {0 + WALL_WIDTH_N_HEIGHT + (WALL_WIDTH_N_HEIGHT / 3),
                                                  1 - WALL_WIDTH_N_HEIGHT - (WALL_WIDTH_N_HEIGHT / 3),
                                                  0 + WALL_WIDTH_N_HEIGHT,
                                                  1 - WALL_WIDTH_N_HEIGHT,
                                                  0 + WALL_WIDTH_N_HEIGHT + (WALL_WIDTH_N_HEIGHT / 3),
                                                  1 - WALL_WIDTH_N_HEIGHT - (WALL_WIDTH_N_HEIGHT / 3)};
}
