
package minesweeper;


import javafx.application.Application;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


public class Minesweeper extends Application{
    
    Stage stage;
    String unrevealedStyle = "-fx-background-color: black; -fx-text-fill: transparent;";
    String revealedStyle = "";
    Button[][] buttons;
    
    public static void main(String[] args) {
        Board.setBoard();
        launch(args);      
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
    stage = primaryStage;
    
    HBox menu = new HBox();
    
    GridPane grid = new GridPane();
    grid.setPadding(new Insets(20, 20, 20, 20));
    grid.setHgap(2);
    grid.setVgap(2);
    
    BorderPane border = new BorderPane();
    border.setCenter(grid);

    
    
    buttons = new Button[Board.horizontalTiles][Board.verticalTiles];

    for (int x = 0; x < Board.horizontalTiles; x++) {
        for (int y = 0; y < Board.verticalTiles; y++) {
            Button button = new Button();
            buttons[x][y] = button;
            grid.add(button, x, y);
            
            
            button.setMinWidth(30);
            button.setMinHeight(30);
            String index = String.valueOf(Board.mineField[x][y]);
            button.setText(index);
            
            button.setStyle(unrevealedStyle);
            
            final int xx = x;
            final int yy = y;
            button.setOnAction(e -> reveal(button.getText(), xx, yy));
        }
    }
    
    
    

    ScrollPane scrollPane = new ScrollPane(grid);

    stage.setScene(new Scene(scrollPane));
    stage.show();
    }
    
    public void reveal(String text, int hIndex, int vIndex){
        if (!"-1".equals(text)){
            
            buttons[hIndex][vIndex].setStyle(revealedStyle);
            buttons[hIndex][vIndex].setDisable(true);
            
            if("0".equals(text)){
                
                buttons[hIndex][vIndex].setText(" ");

                for (int h = hIndex - 1; h <= hIndex + 1; h++) {
                    for (int v = vIndex - 1; v <= vIndex + 1; v++) {

                        boolean inBoundsX = (h >= 0) && (h < Board.horizontalTiles);
                        boolean inBoundsY = (v >= 0) && (v < Board.verticalTiles);

                        if(inBoundsX && inBoundsY){
                            buttons[h][v].fire();
                        }

                    }
                }
            }
            
            checkVictory();
            
        }else{
            lose();
        }
    }
    
    public void checkVictory(){
        int revealedOnes = 0;
        
        for (int x = 0; x < Board.horizontalTiles; x++) {
            for (int y = 0; y < Board.verticalTiles; y++) {
                if (buttons[x][y].getStyle().equals(revealedStyle)){
                    revealedOnes += 1;
                }
            }
        }
        
        if(Board.totalMines == (Board.horizontalTiles * Board.verticalTiles - revealedOnes)){
            win();
        }
    }
    
    public void lose(){
        for (int x = 0; x < Board.horizontalTiles; x++) {
            for (int y = 0; y < Board.verticalTiles; y++) {
                buttons[x][y].setStyle(revealedStyle);
                buttons[x][y].setDisable(true);
                
                if("-1".equals(buttons[x][y].getText())){
                    Image image = new Image(getClass().getResourceAsStream("Mine.png"));
                    buttons[x][y].setText("");
                    buttons[x][y].setGraphic(new ImageView(image));
                }
                if("0".equals(buttons[x][y].getText())){
                    buttons[x][y].setText("");
                }
            }
        }
    }
    
    public void win(){
        System.out.println("Vc venceu!");
    }
    
    
    
}
