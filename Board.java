import java.util.Random;

public class Board{
    private static int totalMines = 10;
    private static int horizontalTiles = 10;
    private static int verticalTiles = 10;
    private static int flagCounter = totalMines;
    
    private static int[][] mineField;
    private static Random rand = new Random();
    private static boolean[][] flagMap;
    
    //Creates the board
    public static void create(){
        
        initialize();
         
        setMines();
        
        setNumbers();

	initializeFlagSystem();
    }
    
    //Xreates the Board
    public static void initialize(){
        mineField = new int[horizontalTiles][verticalTiles];
        
        for (int i = 0; i < horizontalTiles; i++) {
            for (int j = 0; j < verticalTiles; j++) {
                mineField[i][j] = 0;
            }
        }
    }
    
    //Set mines in random positions
    public static void setMines(){
        for (int i = 0; i < totalMines; i++){
            
            int xMine = rand.nextInt(horizontalTiles);
            int yMine = rand.nextInt(verticalTiles);
            
            if (mineField[xMine][yMine] != -1){
                mineField[xMine][yMine] = -1;
            }else{
                i -= 1;
            }
        }
    }
    
    //Set the numbers adjacent to the mines 
    public static void setNumbers(){
        
        for (int i = 0; i < horizontalTiles; i++) {
            for (int j = 0; j < verticalTiles; j++) {
                
                if(mineField[i][j] == -1){
                    
                    for (int h = i - 1; h <= i + 1; h++) {
                        for (int v = j - 1; v <= j + 1; v++) {  
                            
                            boolean inBoundsX = (h >= 0) && (h < horizontalTiles);
                            boolean inBoundsY = (v >= 0) && (v < verticalTiles);
                            
                            if(inBoundsX && inBoundsY && mineField[h][v] != -1){
                                mineField[h][v] += 1;
                            }          
                        }
                    }
                }                          
            }
        }
    }

    //Initialize the flags
    public static void initializeFlagSystem(){
	flagMap = new boolean[horizontalTiles][verticalTiles];

	for (int x = 0; x < horizontalTiles; x++) {
        	for (int y = 0; y < verticalTiles; y++) {
            		flagMap[x][y] = false;
	    }
        }
	    
    }

    
    
    public static int getTotalMines() {
        return totalMines;
    }

    public static int getHTiles() {
        return horizontalTiles;
    }

    public static int getVTiles() {
        return verticalTiles;
    }
    
    public static int getFlagCounter() {
        return flagCounter;
    }

    public static void updateFlagCounter(int flag) {
       Board.flagCounter += flag;
    }

    public static int getContent(int x, int y) {
        return mineField[x][y];
    }

    public static boolean haveFlag(int x, int y) {
        return flagMap[x][y];
    }
    
    public static void placeFlag(int x, int y) {
        flagMap[x][y] = true;
    }
    
    public static void removeFlag(int x, int y) {
        flagMap[x][y] = false;
    }
    
}