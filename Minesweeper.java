import javafx.application.Application;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

public class Minesweeper extends Application{
    
    Stage stage;
    Scene welcomeScene, minesweeperScene;
    String unrevealedStyle = "-fx-background-color: black; -fx-text-fill: transparent;";
    String revealedStyle = "";
    String flagStyle  = "-fx-background-color: red; -fx-text-fill: transparent;";
    Button[][] buttons;
    Label flagsHUD;
    
    public static void main(String[] args) {
        Board.setBoard();
        launch(args);    
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
    stage = primaryStage;
    stage.setTitle("Minesweeper");
    
    setWelcomeScene();
    
    stage.setResizable(false);
    stage.setScene(welcomeScene);
    stage.show();
    }
    
    public void setWelcomeScene(){
        VBox menu = new VBox();
        Label title = new Label("Minesweeper");
        Button play = new Button("Play");
        play.setOnAction(e -> {    
            setMinesweeperScene();
            stage.setScene(minesweeperScene);
        });
        Button seeScores = new Button("See Highscores");
        Button quit = new Button("Exit Game");
        quit.setOnAction(e -> stage.close());
        
        menu.getChildren().addAll(title, play, seeScores, quit);
        welcomeScene = new Scene(menu);            
    }

    public void setMinesweeperScene(){
        HBox menu = new HBox(10);
        //menu.setPadding(new Insets(10, 10, 10, 10));

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setHgap(2);
        grid.setVgap(2);


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
                button.setOnMouseClicked((MouseEvent event) -> {
                    MouseButton button1 = event.getButton();
                    if (button1 == MouseButton.PRIMARY) {
                        reveal(button.getText(), xx, yy);
                    } else if (button1 == MouseButton.SECONDARY) {
                        setFlag(xx, yy);
                    }
                });
            }
        }

        flagsHUD = new Label(String.valueOf(Board.flags)); 
        menu.getChildren().add(flagsHUD);

        BorderPane border = new BorderPane();
        border.setCenter(grid);
        border.setTop(menu);

        minesweeperScene = new Scene(border);
    }
    
    public void setFlag(int hIndex, int vIndex){	
		if(Board.flagsMap[hIndex][vIndex] == 0 && Board.flags < Board.totalMines){
			Board.flagsMap[hIndex][vIndex] = 1;	
			buttons[hIndex][vIndex].setStyle(flagStyle);
			Board.flags += 1;
		}
		else if(Board.flagsMap[hIndex][vIndex] == 1){
			Board.flagsMap[hIndex][vIndex] = 0;	
			buttons[hIndex][vIndex].setStyle(unrevealedStyle);
			Board.flags -= 1;
		}
                
	flagsHUD.setText(String.valueOf(Board.flags));
    }   

    public void reveal(String text, int hIndex, int vIndex){
        
        if(Board.flagsMap[hIndex][vIndex] != 1){
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
                lost();
            }
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
    
    public void lost(){
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
        
        boolean choice = displayLostBox("Minesweeper", "You lost... want to try again?");
        
        if(choice){
            Board.setBoard();
            setMinesweeperScene();
            stage.setScene(minesweeperScene);
        }
        else{
            stage.setScene(welcomeScene);
        }
    }
    
    public void win(){
        System.out.println("Vc venceu!");
    }
   
    static boolean answer;
    
    public static boolean displayLostBox(String title, String message){ 
        Stage window = new Stage();
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        
        Label label = new Label();
        label.setText(message);
        Button no = new Button("Nah");
        Button yes = new Button("Sure!");
        no.setOnAction(e -> {
            window.close();
            answer = false;
            
        });
        
        yes.setOnAction(e -> {
            window.close();
            answer = true;
        });
        
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, yes, no);
        layout.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.initStyle(StageStyle.UNDECORATED);
        window.showAndWait();
        
        return answer;   
    }
    
}