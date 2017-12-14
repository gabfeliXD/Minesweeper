import javafx.application.Application;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;


public class Minesweeper extends Application{
    
    Stage stage;
    
    public static void main(String[] args) {
        launch(args);
        Board.createBoard();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
    stage = primaryStage;
    GridPane grid = new GridPane();
    grid.setPadding(new Insets(20, 20, 20, 20));
    grid.setHgap(2);
    grid.setVgap(2);
    
    Button[][] buttons = new Button[Board.horizontalTiles][Board.verticalTiles];

    for (int x = 0; x < Board.horizontalTiles; x++) {
        for (int y = 0; y < Board.verticalTiles; y++) {
            Button button = new Button();
            buttons[x][y] = button;
            grid.add(button, x, y);
            
            
            button.setMinWidth(30);
            button.setMinHeight(30);
            
            String index = String.valueOf(Board.mineField[x][y]);
            button.setText(index);
            //button.setStyle("-fx-background-color: black; ");
            //button.setId(index);
            //button.setOnAction(e -> );
        }
    }
    
    
    

    ScrollPane scrollPane = new ScrollPane(grid);

    stage.setScene(new Scene(scrollPane));
    stage.show();
    }


    
}
