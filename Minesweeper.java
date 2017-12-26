import java.util.Random;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Minesweeper extends Application{
    
    //LOGIGAL STUFF
    
    public static int totalMines = 10;
    public static int horizontalTiles = 10;
    public static int verticalTiles = 10;
    public static int[][] mineField;
    public static Random rand = new Random();
    public static int[][] flagsMap;
    public static int flags = totalMines;
    
    //Creates the board
    public static void setBoard(){
        
        createBoard();
         
        setMines();
        
        setNumbers();

	initializeFlagSystem();
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

    //Initialize the flag counter
    public static void initializeFlagSystem(){
	flagsMap = new int[horizontalTiles][verticalTiles];

	for (int x = 0; x < horizontalTiles; x++) {
        	for (int y = 0; y < verticalTiles; y++) {
            		flagsMap[x][y] = 0;
	    }
        }
	    
    }    
    
    //Main
    public static void main(String[] args) {
        launch(args);    
    }
    
    Stage stage;
    Scene welcomeScene, minesweeperScene, highscoreScene;
    
    String unrevealedStyle = "-fx-background-color: black; -fx-text-fill: transparent;";
    String revealedStyle = "";
    String flagStyle  = "-fx-background-color: red; -fx-text-fill: transparent;";
    
    Button[][] buttons;
    
    Label flagsHUD;
    Button resetButton;
    Boolean Arusure = true;
    Label Timer;
    Label Name;

    Timeline timeline;
    long startTime;
                    
    String name = "";
    String time = "";
    
    @Override
    public void start(Stage primaryStage) throws Exception {
    stage = primaryStage;
    stage.setTitle("Minesweeper");
    stage.setWidth(360);
    stage.setHeight(500);
    stage.setResizable(false);
    
    
    setWelcomeScene();
   
    stage.setScene(welcomeScene);
    stage.show();
    }
    
    //Constuct the first scene
    public void setWelcomeScene(){
        
        VBox menu = new VBox(30);
        menu.setAlignment(Pos.CENTER);
        Label title = new Label("Minesweeper");
        Button play = new Button("Play");
        play.setOnAction(e -> {
                setBoard();
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Congatulations!");
		window.setMinWidth(250);
		
		Label label = new Label();
		label.setText("Write Your Name! (Maximum of 3 characters)");
		TextField textField = new TextField ();
		Button closeButton = new Button("Play!");
		closeButton.setOnAction(a -> {
		    window.close();
		    if(textField.getText().length() > 3){
			name = textField.getText().substring(0,3);
		    }else{

		    name = textField.getText();

		    }
		    setMinesweeperScene();
		    stage.setScene(minesweeperScene);
		    });
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, textField, closeButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);  
		window.showAndWait();
        });
        Button seeScores = new Button("See Highscores");
        setHighscoreScene();
        seeScores.setOnAction(e -> stage.setScene(highscoreScene));
        
        Button quit = new Button("Exit Game");
        quit.setOnAction(e -> stage.close());
        
        menu.getChildren().addAll(title, play, seeScores, quit);
        welcomeScene = new Scene(menu);
          
    }

    //construct the scene where the thing will truly happen
    public void setMinesweeperScene(){
        startTime = System.currentTimeMillis();

        HBox menu = new HBox(10);
        menu.setPadding(new Insets(20, 20, 5, 20));
	menu.setSpacing(30);
	menu.setAlignment(Pos.CENTER);
        HBox back = new HBox(10);
	back.setAlignment(Pos.CENTER);
        back.setPadding(new Insets(5, 20, 20, 20));
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setHgap(2);
        grid.setVgap(2);

        buttons = new Button[horizontalTiles][verticalTiles];

        for (int x = 0; x < horizontalTiles; x++) {
            for (int y = 0; y < verticalTiles; y++) {
                Button button = new Button();
                buttons[x][y] = button;
                grid.add(button, x, y);


                button.setMinWidth(30);
                button.setMinHeight(30);
                String index = String.valueOf(mineField[x][y]);
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
        
        resetButton = new Button("Back to Menu"); 
        
        flagsHUD = new Label("Flags:\n   " + String.valueOf(flags));
        
        Timer = new Label("Time: \n   " + String.valueOf(0) + " : "+ String.valueOf(0));

	Name = new Label("Name:\n   " + name);
	        
        Arusure = true;
	resetButton.setOnAction(e -> {
		
		if(Arusure != true){

           		 setWelcomeScene();
			stage.setScene(welcomeScene);
		}
		else{
			Stage window = new Stage();
			
			window.initModality(Modality.APPLICATION_MODAL);
			window.setTitle("Are you Sure?");
			window.setMinWidth(250);
			
			Label label = new Label();
			label.setText("Do you really want to quit?");

			Button noButton = new Button("No");
			Button yesButton = new Button("Yes");
			noButton.setOnAction(a -> {
			    window.close();
			    });
			
			yesButton.setOnAction(b -> {
			    window.close();
           		    setWelcomeScene();
			    stage.setScene(welcomeScene);
			    });

			VBox layout = new VBox(10);
			layout.getChildren().addAll(label,noButton, yesButton);
			layout.setAlignment(Pos.CENTER);
			
			Scene scene = new Scene(layout);
			window.setScene(scene);  
			window.showAndWait();
	      }
        });
        
        Button winButton = new Button("win");
        winButton.setOnAction(e -> win());

        menu.getChildren().addAll(flagsHUD, Timer, Name);
	back.getChildren().addAll(resetButton, winButton);
        
        timeline = new Timeline(new KeyFrame(
        Duration.millis(1000),
            ae -> displaySeconds(startTime)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        
        BorderPane border = new BorderPane();
        border.setCenter(grid);
        border.setTop(menu);
	border.setBottom(back);
        minesweeperScene = new Scene(border);
 
    }
    
    public void setHighscoreScene(){
        VBox highscores = new VBox(30);
        
        Label title = new Label("Highscores:");
        
        highscores.getChildren().add(title);
        
        Button backToMenu = new Button("Back to Menu");
        backToMenu.setOnAction(e -> {
			stage.setScene(welcomeScene);
        });
        highscores.getChildren().add(backToMenu);
        
        highscores.setAlignment(Pos.CENTER);
        
        highscoreScene = new Scene(highscores);
    }
    
    public void setFlag(int hIndex, int vIndex){	
		if(flagsMap[hIndex][vIndex] == 0 && flags <= totalMines && flags > 0){
			flagsMap[hIndex][vIndex] = 1;	
			buttons[hIndex][vIndex].setStyle(flagStyle);
			flags -= 1;
		}
		else if(flagsMap[hIndex][vIndex] == 1){
			flagsMap[hIndex][vIndex] = 0;	
			buttons[hIndex][vIndex].setStyle(unrevealedStyle);
			flags += 1;
		}
                
	flagsHUD.setText("Flags:\n   " + String.valueOf(flags));
    }   

    public void reveal(String text, int hIndex, int vIndex){
        
        if(flagsMap[hIndex][vIndex] != 1){
            if (!"-1".equals(text)){

                buttons[hIndex][vIndex].setStyle(revealedStyle);
                buttons[hIndex][vIndex].setDisable(true);

                if("0".equals(text)){

                    buttons[hIndex][vIndex].setText(" ");

                    for (int h = hIndex - 1; h <= hIndex + 1; h++) {
                        for (int v = vIndex - 1; v <= vIndex + 1; v++) {

                            boolean inBoundsX = (h >= 0) && (h < horizontalTiles);
                            boolean inBoundsY = (v >= 0) && (v < verticalTiles);

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
        
        for (int x = 0; x < horizontalTiles; x++) {
            for (int y = 0; y < verticalTiles; y++) {
                if (buttons[x][y].getStyle().equals(revealedStyle)){
                    revealedOnes += 1;
                }
            }
        }
        
        if(totalMines == (horizontalTiles * verticalTiles - revealedOnes)){
            win();
        }
    }
    
    public void lost(){
        for (int x = 0; x < horizontalTiles; x++) {
            for (int y = 0; y < verticalTiles; y++) {
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

	Arusure = false;
	resetButton.setText("Try Again!");
        }
  
    }
    
    public void win(){
        time = Timer.getText().replace("Time:\n ","").replace(" : ","");
        System.out.println(name + " " +time);

    }
       
    public void displaySeconds(long startTime){
                long elapsedTime = System.currentTimeMillis() - startTime;
                long elapsedSeconds = elapsedTime / 1000;
                long secondsDisplay = elapsedSeconds % 60;
                long elapsedMinutes = elapsedSeconds / 60;
        
                Timer.setText("Time:\n " + String.valueOf(elapsedMinutes) + " : "+ String.valueOf(secondsDisplay));          
    }
     
}