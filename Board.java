import java.util.Random;

public class Board {
    public static int totalMines = 10;
    public static int horizontalTiles = 10;
    public static int verticalTiles = 10;
    public static int[][] mineField;
    public static Random rand = new Random();
    
    //Creates the board
    public static void setBoard(){
        
        createBoard();
         
        setMines();
        
        setNumbers();
    }
    
    //Initialize the board
    public static void createBoard(){
        mineField = new int[horizontalTiles][verticalTiles];
        
        for (int i = 0; i < horizontalTiles; i++) {
            for (int j = 0; j < verticalTiles; j++) {
                mineField[i][j] = 0;
            }
        }
    }
    
    //Set the mines in random positions
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
}
