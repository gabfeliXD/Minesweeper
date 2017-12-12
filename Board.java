import java.util.Random;

public class Board {
    private int totalMines = 10;
    private int boardWidth = 10;
    private int boardHeight = 10;
    private int[][] mineField;
    private Random rand = new Random();
    
    //Creates the board
    public void setBoard(){
        
        createBoard();
         
        setMines();
        
        setNumbers();
    }
    
    //Initialize the board
    public void createBoard(){
        mineField = new int[boardWidth][boardHeight];
        
         for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardHeight; j++) {
                mineField[i][j] = 0;
            }
        }
    }
    
    //Set the mines in random positions
    public void setMines(){
        for (int i = 0; i < totalMines; i++){
            int xMine = rand.nextInt(boardWidth);
            int yMine = rand.nextInt(boardHeight);
            
            if (mineField[xMine][yMine] != -1){
                mineField[xMine][yMine] = -1;
            }else{
                i -= 1;
            }
        }
    }
    
    //Set the numbers adjacent to the mines 
    public void setNumbers(){
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardHeight; j++) {
                if(mineField[i][j] == -1){
                    
                    for (int h = i - 1; h < i + 1; h++) {
                        for (int v = j - 1; v < j + 1; v++) {
                    
                            if(mineField[h][v] != -1){
                                mineField[h][v] += 1;
                            }
                        }
                    }
                }
            }
        }
    }
    
    
    
    
}
