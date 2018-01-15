// Aqui é onde as coisa "lógicas" acontecem
import java.util.Random;

public class Board{
    private static int totalMines = 10; // O número total de minas
   private static int horizontalTiles = 10; // O tamanho horizontal do campo
    private static int verticalTiles = 10; // O tamanho vertical do campo
    private static int flagCounter = totalMines; // Inicializando a variável que vai contar as "bandeiras"
    
    private static int[][] mineField; // Esse array vai ser responsável por me mostrar onde estão as minhas minas e os meus múmeros
    private static Random rand = new Random();
    private static boolean[][] flagMap; // Esse array vai ser responsável por me mostrar onde estão as minhas bandeiras
    
    //Esse método é o responsável por chamar os outros métodos e criar definitivamente o campo
    public static void create(){
        
        initialize();
         
        setMines();
        
        setNumbers();

	initializeFlagSystem();
    }
    
    //Esse método vai inicalizar o mau array de minas, botando zeros em todos os espaços
    public static void initialize(){
        mineField = new int[horizontalTiles][verticalTiles];
        
        for (int i = 0; i < horizontalTiles; i++) {
            for (int j = 0; j < verticalTiles; j++) {
                mineField[i][j] = 0;
            }
        }
    }
    
    //Esse método vai gerar conjuntos aleatóios de x e y, que vão significar a posição das minas
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
    
    /*Esse método percorrer todo o array, e se verificar que tem alguma mina, 
    ele vai adicionar os números nos espaços adjacentes, caso não tenham outras minas nesses espaços
	*/  
    public static void setNumbers(){
        
        for (int i = 0; i < horizontalTiles; i++) {
            for (int j = 0; j < verticalTiles; j++) {
                //Verificando o array
                if(mineField[i][j] == -1){
                    //Caso encontre uma mina
                    for (int h = i - 1; h <= i + 1; h++) {
                        for (int v = j - 1; v <= j + 1; v++) {  
                            //Ele vai percorrer desde a posição (mina-1, mina-1) até a (mina+1, mina+1)
				/*(Xmina-1, Ymina-1)->OOO
						      OXO
						      OOO<-(Xmina+1, Ymina+1)*/
                            boolean inBoundsX = (h >= 0) && (h < horizontalTiles);
                            boolean inBoundsY = (v >= 0) && (v < verticalTiles);
                            //Então ele vai verificar se o valor ainda fica destro do array, pro caso de
				/*(Xmina-1, Ymina-1)->0OOO
						      0XOO
						      0OOO<-(Xmina+1, Ymina+1)
				
				X -  mina
				O - espaço dentro do array (entre 0 e array.length()-1)
				0 - espaço fora do array
				*/
                            if(inBoundsX && inBoundsY && mineField[h][v] != -1){
                                mineField[h][v] += 1;
				    /*Se tiver tudo certo e não tiver uma mina perto (não quer dizer que não se colocará
				    numeros se duas minas estiverem perto, mas sim que checará antes de se por o número 
				    pra n sobreescrever a posição da mina pondo o numero em cima dela*/
                            }          
                        }
                    }
                }                          
            }
        }
    }

    //Esse método vai preencher todas as posições das bandeiras com "false" o que significa que não tem bandeiras lá
    public static void initializeFlagSystem(){
        flagCounter = totalMines;
	flagMap = new boolean[horizontalTiles][verticalTiles];

	for (int x = 0; x < horizontalTiles; x++) {
        	for (int y = 0; y < verticalTiles; y++) {
            		flagMap[x][y] = false;
	    }
        }
	    
    }

    
    //Getters e Setters
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
