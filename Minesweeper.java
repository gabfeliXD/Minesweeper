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
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class Minesweeper extends Application{
    
    
    //Main
    public static void main(String[] args) {
        launch(args);    
    }
    
    private Stage stage;
    private Scene welcomeScene, minesweeperScene, rankingsScene;
    
    private final String unrevealedStyle = "-fx-background-color: black; -fx-text-fill: transparent;";
    private final String revealedStyle = "";
    private final String flagStyle  = "-fx-background-color: red; -fx-text-fill: transparent;";
    private Button[][] buttons;
    
    private Label flagsHUD;
    private Button resetButton;
    private Boolean canAsk;
    private Label Timer;
    private Label Name;

    private Timeline timeline;
    private long startTime;
                    
    private String name = "";
    private String time = "";
    
    Button RankingToMenu;
    Label HelloRankings;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
    stage = primaryStage;
    stage.setTitle("Minesweeper");
    stage.setWidth(480);
    stage.setHeight(520);
    stage.setResizable(false);
    
    setWelcomeScene();

    stage.setScene(welcomeScene);
    stage.show();
    }
    
    //Constuct the first scene
    public void setWelcomeScene(){
        setHighscoreScene();
        VBox menu = new VBox(30);
        menu.setAlignment(Pos.CENTER);
        Label title = new Label("Minesweeper");
        title.setStyle("-fx-font-size: 60");
        Button play = new Button("Play");
        play.setOnAction(e -> {
                Board.create();
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
        
        seeScores.setOnAction(e -> {
            
            
            stage.setScene(rankingsScene);
        });
        
        Button quit = new Button("Exit Game");
        quit.setOnAction((ActionEvent e) -> {
            stage.close();
        });
        
        menu.getChildren().addAll(title, play, seeScores, quit);
        welcomeScene = new Scene(menu);
          
    }

    //construct the scene where the thing will truly happen
    public void setMinesweeperScene(){
        startTime = System.currentTimeMillis();

        HBox topMenu = new HBox(10);
        topMenu.setPadding(new Insets(20, 20, 5, 20));
	topMenu.setSpacing(30);
	topMenu.setAlignment(Pos.CENTER);
        
        HBox bottomMenu = new HBox(10);
	bottomMenu.setAlignment(Pos.CENTER);
        bottomMenu.setPadding(new Insets(5, 20, 20, 20));
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setHgap(2);
        grid.setVgap(2);
        grid.setAlignment(Pos.CENTER);

        buttons = new Button[Board.getHTiles()][Board.getVTiles()];

        for (int x = 0; x < Board.getHTiles(); x++) {
            for (int y = 0; y < Board.getVTiles(); y++) {
                Button button = new Button();
                buttons[x][y] = button;
                grid.add(button, x, y);


                button.setMinWidth(30);
                button.setMinHeight(30);
                String index = String.valueOf(Board.getContent(x, y));
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
        
        flagsHUD = new Label("Flags:\n   " + String.valueOf(Board.getFlagCounter()));
        
        Timer = new Label("Time: \n   " + String.valueOf(0) + " : "+ String.valueOf(0));

	Name = new Label("Name:\n   " + name);
	        
        canAsk = true;
        
	resetButton.setOnAction(e -> {
		
		if(canAsk != true){

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
        

        topMenu.getChildren().addAll(flagsHUD, Timer, Name);
	bottomMenu.getChildren().add(resetButton);
        
        
        timeline = new Timeline(new KeyFrame(Duration.millis(1000),ae -> displaySeconds(startTime)));  
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        
        BorderPane border = new BorderPane();
        border.setCenter(grid);
        border.setTop(topMenu);
	border.setBottom(bottomMenu);
        minesweeperScene = new Scene(border);
 
    }
    
    public void setHighscoreScene(){
        Ranking.update();
        
        VBox ranking = new VBox(20);
        
        HelloRankings = new Label("Highscores:");
        ranking.getChildren().add(HelloRankings);
            for(int i = 0; i < 10; i++){
                Label label = new Label();
                label.setText(Ranking.getWinner(i));
                ranking.getChildren().add(label);
            }
        RankingToMenu = new Button("Back to Menu");
        RankingToMenu.setOnAction(e -> {
			stage.setScene(welcomeScene);
        });
        ranking.getChildren().add(RankingToMenu);
        
        ranking.setAlignment(Pos.CENTER);
        
        rankingsScene = new Scene(ranking);
        
    }
    
    public void setFlag(int hIndex, int vIndex){	
		if(!Board.haveFlag(hIndex, vIndex) && Board.getFlagCounter() <= Board.getTotalMines() && Board.getFlagCounter() > 0){
			Board.placeFlag(hIndex, vIndex);
			buttons[hIndex][vIndex].setStyle(flagStyle);
			Board.updateFlagCounter(-1);
		}
		else if(Board.haveFlag(hIndex, vIndex)){
			Board.removeFlag(hIndex, vIndex);		
			buttons[hIndex][vIndex].setStyle(unrevealedStyle);
			Board.updateFlagCounter(+1);
		}
                
	flagsHUD.setText("Flags:\n   " + String.valueOf(Board.getFlagCounter()));
    }   

    public void reveal(String text, int hIndex, int vIndex){
        
        if(!Board.haveFlag(hIndex, vIndex)){
            if (!"-1".equals(text)){

                buttons[hIndex][vIndex].setStyle(revealedStyle);
                buttons[hIndex][vIndex].setDisable(true);

                if("0".equals(text)){

                    buttons[hIndex][vIndex].setText(" ");

                    for (int h = hIndex - 1; h <= hIndex + 1; h++) {
                        for (int v = vIndex - 1; v <= vIndex + 1; v++) {

                            boolean inBoundsX = (h >= 0) && (h < Board.getHTiles());
                            boolean inBoundsY = (v >= 0) && (v < Board.getVTiles());

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
        
        for (int x = 0; x < Board.getHTiles(); x++) {
            for (int y = 0; y < Board.getVTiles(); y++) {
                if (buttons[x][y].getStyle().equals(revealedStyle)){
                    revealedOnes += 1;
                }
            }
        }
        
        if(Board.getTotalMines()== (Board.getHTiles() * Board.getVTiles() - revealedOnes)){
            win();
        }
    }
    
    public void lost(){
        for (int x = 0; x < Board.getHTiles(); x++) {
            for (int y = 0; y < Board.getVTiles(); y++) {
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

	canAsk = false;
	resetButton.setText("Try Again!");
        }
  
    }
    
    public void win(){
        time = Timer.getText().replace("Time:\n ","").replace(" : ","");
        Ranking.append(name, time);
        System.out.println(name + " " + time);
    }
       
    public void displaySeconds(long startTime){
                long elapsedTime = System.currentTimeMillis() - startTime;
                long elapsedSeconds = elapsedTime / 1000;
                long secondsDisplay = elapsedSeconds % 60;
                long elapsedMinutes = elapsedSeconds / 60;
        
                Timer.setText("Time:\n " + String.valueOf(elapsedMinutes) + " : "+ String.valueOf(secondsDisplay));          
    }
     
}