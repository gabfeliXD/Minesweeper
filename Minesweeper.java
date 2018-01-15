/*
Aqui é onde a magia realmente acontece, estão prontos crianças?

Uma coisa que eu recomendo é vc verem essa playlist aq

https://www.youtube.com/playlist?list=PL6gx4Cwl9DGBzfXLWLSYVy8EbTdpGbUIG

que fala sobre javafx
infelizmente está em inglês, mas tem aulas de qualidade e tal. Não precisa ver tudo, 
eu pox exemplo só vi até a aula 12, mas eu recomendaria... Vai que o cara lá mostre coisas que façam vcs terem ideias sei la.

Bem, só de verem as aulas lá vcs já vão ter uma ideia do que é que está acontecendo aqui
*/

//importando coisas
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

//No java fx eu tenho que fazer a minha classe principal extender a classe Application, 
//e como muita coisa aq, eu n sei pq funciona, só funciona
public class Minesweeper extends Application{
    
    
    //Main
    public static void main(String[] args) {
        launch(args);
	    // eu boto esse método ai, e ai eu boto as coisas no método Start la embaixo
    }
    
	/*
	Um "stage" é tipo uma janela, como se fosse uma janela de navegador mesmo.
	Enquanto a "scene" é como se fosse a guia, ou aba, n sei como vcs chamam isso. 
	De qualquer forma, a "stage" é as coisas que ficam dentro da janela, ou da "Scene".
	
	Buttons e Labels são auto explicativos
	
	*/
    private Stage stage;
    private Scene welcomeScene, minesweeperScene, rankingsScene;
    
	//Esses são os estilos, no javafx, eu posso estizar as coisas usando CSS, com um poquinho de estudo sobre CSS vcs conseguem customizar criar os próprios estilos de vcs de boa 
    private final String unrevealedStyle = "-fx-background-color: black; -fx-text-fill: transparent;";
    private final String revealedStyle = "";
    private final String flagStyle  = "-fx-background-color: red; -fx-text-fill: transparent;";
    private Button[][] buttons;
    
    private Label flagsHUD;
    private Button resetButton;
    private Boolean canAsk; // Isso aqi é pra ver se é pra aparecer aqueles pop-upsihos pergutando
    private Label Timer;
    private Label Name;
    
	//Isso aqui é tem a ver com o tempo, com o contaor de tempo do jogo.
    private Timeline timeline;
    private long startTime;
    
	//Isso aqui tem a ver com 
    private String name = "";
    private String time = "";
    
    Button RankingToMenu;
    Label HelloRankings;
    
	//Esse aqui é o método onde a magia acontece
    @Override
    public void start(Stage primaryStage) throws Exception {
    stage = primaryStage; // Aqui eu to botando o stage que eu cirei ( que se chama stage, criatividade mais de 8000) pra ser o stage inicial
    stage.setTitle("Minesweeper"); // Botando o título
    stage.setWidth(480); // botando o tamanho horizontal
    stage.setHeight(520); // e o vertical, eu n sei a medida mais acho que é em pixels
    stage.setResizable(false); // Aqui eu boto pra n o stage n poder nem maximizar e nem ser
    
    setWelcomeScene(); //aqui eu configuro a scene "welcomeScene" (adiciono as coisas (botões, labels e outras coihinhas)) 

    stage.setScene(welcomeScene); //Aqui eu boto a scene "welcomeScene" no meu stage
    stage.show(); // aqui é pra fazer  stage aparecer 
    }
    
    //Esse é o método 
    public void setWelcomeScene(){
        setHighscoreScene(); //
        VBox menu = new VBox(30); //Isso aqui é uma VBox, que é um layout, eu boto as coisas nele e ai boto ele na scene, esse numero ai é o espaçamento entre as coisas
        menu.setAlignment(Pos.CENTER); // configurando pra ficar no centro do layout
        Label title = new Label("Minesweeper"); //Inicializando a Label e botando texo nela
        title.setStyle("-fx-font-size: 60"); //aumentando a fonte
        Button play = new Button("Play"); //Inicializando um botão e botando texto nele
	    /*
	    isso aqui é como eu coloco o que vai acontecer
	    Nessa estrutura:
	    nomeBotão.setOnAction(e -> {
	    
	    coisas
	    
	    });
	    
	    */
        play.setOnAction(e -> {
		
		/*
		Essa ação cria um novo stage, uma nova janela chamado "window", 
		essa stage ai ter um layout VBox, que é um layout vertical. Nesse layout, vai ter uma label, um textfield, e um botão.
		
		essa janela vai ser epecial, como uma janela de pop up em que vc n pode clicar fora dela
                */
		Board.create();
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL); //isso aqui é pra que eu n possa clicar em outra janela enquanto essa estiver aberta 
		window.setTitle("Congatulations!");
		window.setWidth(350);
                window.setHeight(150);

		Label label = new Label();
		label.setText("Write Your Name! (Maximum of 3 characters)");
		TextField textField = new TextField (); // isso é um TextField, ele serve pra eu botar texto
                textField.setMaxWidth(50);
                
		Button closeButton = new Button("Play!"); // crindo botao
		//colocando a ação pra esse botão
		closeButton.setOnAction(a -> {
			/*Essa ação vai fechar a janela de pop up, 
			enquanto pega o texto dentro do textfield, e se ele for maior de 3 caracteres
			o texto fica restrito a 3 caracteres
			*/
		    window.close();
		    if(textField.getText().length() > 3){
				if(textField.getText() != ""){
					name = textField.getText().substring(0,3);
				}else{
					name = "   ";
				}
		    }else{

		    name = textField.getText();

		    }
		    setMinesweeperScene();
		    stage.setScene(minesweeperScene);
		    });
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, textField, closeButton); //Assim é como eu boto as coisas no layout, se eu fosse botar uma coisa só iria ser só add e não addAll
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout); // quando eu inicializo uma scene, eu já tenho que boar o layout que eu quero nela
                
                window.setResizable(false);
		window.setScene(scene);  
		window.showAndWait();//diferentemente do show sozinho, o show e wait faz o stage esperar por input, use isso com o initModality pra janelas de pop up
        });
        Button seeScores = new Button("See Highscores");
        
        seeScores.setOnAction(e -> {
            
            
            stage.setScene(rankingsScene);
        });
        
        Button quit = new Button("Exit Game");
        quit.setOnAction((ActionEvent e) -> {
            stage.close(); // nomedostage.close(); faz com que o negócio feche
	    Ranking.writeFile(); //usando método da classe ranking, eu só posso fazer isso pq o métoo é static, e as classes estão na mesma pasta
        });
        
        menu.getChildren().addAll(title, play, seeScores, quit);
        welcomeScene = new Scene(menu);
          
    }

    //Scene onde vai realmente ter o campo minado
    public void setMinesweeperScene(){
        startTime = System.currentTimeMillis() + 1000;//isso aqui é pra ver o tempo inicial do sistema, e como ele pega em milissegundos eu adiciono 1000 pra ficar em segundos
	    //a distância desse tempo pro tempo "atual", vai ser o tempo que vc ta la

        HBox topMenu = new HBox(10); //Diferentemente do VBox, esse é um layout é horizontal
        topMenu.setPadding(new Insets(20, 20, 5, 20)); //O pagging é tipo uma margem, um espaço entre as beiradas do stage e os conteúdos do layout
	topMenu.setSpacing(30); // Isso é o spacing, eu fui burro aq, era só botar o 30 como o argumento de incialização da HBox
	topMenu.setAlignment(Pos.CENTER);
        
        HBox bottomMenu = new HBox(10);
	bottomMenu.setAlignment(Pos.CENTER);
        bottomMenu.setPadding(new Insets(5, 20, 20, 20));
        
        GridPane grid = new GridPane();// isso aq é outro layout, um gridpane, como o nome a diz eu tenho varias coisas em grid
        grid.setPadding(new Insets(20, 20, 20, 20)); 
        grid.setHgap(2); //espaçamento horizontal entre as coisas
        grid.setVgap(2); //espaçamento vertical
        grid.setAlignment(Pos.CENTER);

        buttons = new Button[Board.getHTiles()][Board.getVTiles()];// inicializando o array de botões
 
        for (int x = 0; x < Board.getHTiles(); x++) {
            for (int y = 0; y < Board.getVTiles(); y++) {
		//Esse loop cria os botões, congigura a ação deles, e bot eles no array, pra que eu tenha como me referir a eles depois
                Button button = new Button();
                buttons[x][y] = button;
                grid.add(button, x, y); // no gridpane eu adiciono as coisas em um x e no y


                button.setMinWidth(30);
                button.setMinHeight(30);
		    //aqui eu botei uma altura e largura mímimas pro botão n ficar menor 
		    
		    
                String index = String.valueOf(Board.getContent(x, y)); //botando o texto dos botões com base nos numeros do aray la do Board
                button.setText(index);

                button.setStyle(unrevealedStyle);

                final int xx = x;//aq é devido a umas frescuras do java 
                final int yy = y;//se vc quiser botar uma variável pra outro método tem q fazer esse paranauezinho aq
		    
                button.setOnAction(e -> reveal(button.getText(), xx, yy));
		 
		    //aqui é outra maneira de configurar a ação, nessa maneira eu posso colocar tanto o botão principal como o direito
		    //mas é bom eu mesmo assim fazer o setOnAction, pq depois eu posso usar um método especialmente pra acionar os botões
		    //mesmo sem ter clicado neles
                button.setOnMouseClicked((MouseEvent event) -> {
	        MouseButton button1 = event.getButton();

                    if (button1 == MouseButton.PRIMARY) {//se for o esquerdo
                        reveal(button.getText(), xx, yy);
                    } else if (button1 == MouseButton.SECONDARY) {//se for o direito
                        setFlag(xx, yy);
                    }
                });
            }
        }
        
        resetButton = new Button("Back to Menu"); 
        
        flagsHUD = new Label("Flags:\n   " + String.valueOf(Board.getFlagCounter()));
        
        Timer = new Label("Time: \n   " + String.valueOf(0) + " : "+ String.valueOf(0));

	Name = new Label("Name:\n   " + name);
	        
        canAsk = true;//essa variavel vai ver se eu vou mostrar a janlinha la "Tem certeza?"
        
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
	    //bem, eu n sei como caralhos isso funciona, eu só sei que funciona... Aff  me chama de Script Kiddie n pfv. Eu só sei que pondo isso o tempo meio que começa a correr, mas eu ainda preciso formatar ele pa poder mostrar
        
        BorderPane border = new BorderPane();//isso é um border pane, nele eu posso anexar outros layouts
        border.setCenter(grid);
        border.setTop(topMenu);
	border.setBottom(bottomMenu);
        minesweeperScene = new Scene(border);
 
    }
    
	//Scene pra mostrar os rankings
    public void setHighscoreScene(){
        Ranking.update(); //usando método da classe ranking
        
        VBox ranking = new VBox(20);
        
        HelloRankings = new Label("Highscores:");
	Label explainStuff = new Label("Rank - Name - Time");
        ranking.getChildren().addAll(HelloRankings, explainStuff);
            for(int i = 0; i < 10; i++){//adicionando 10 labels dizendo o tempo e o nome dos 10 primeiros colocados
                Label label = new Label();
		
                label.setText(Ranking.formatWinner(Ranking.getWinner(i), i));
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
    //Esse método vai ser se tem bandeira, se n tiver ele vai por, se tiver ele vai tirar
    //Eu falo por bandeira e tal mas é só mudar o estilo do botão, e quando eu aperto ele eu vejo se tem ou n bandeira, vc ja deve ter visto isso
    public void setFlag(int hIndex, int vIndex){	
		if(!Board.haveFlag(hIndex, vIndex) && Board.getFlagCounter() <= Board.getTotalMines() && Board.getFlagCounter() > 0){
			Board.placeFlag(hIndex, vIndex);
			buttons[hIndex][vIndex].setStyle(flagStyle); //mudando o estilo
			Board.updateFlagCounter(-1);
		}
		else if(Board.haveFlag(hIndex, vIndex)){
			Board.removeFlag(hIndex, vIndex);		
			buttons[hIndex][vIndex].setStyle(unrevealedStyle); //mudando o estilo
			Board.updateFlagCounter(+1);
		}
                
	flagsHUD.setText("Flags:\n   " + String.valueOf(Board.getFlagCounter())); //Muda o texto dizendo a quantidade
    }   
	
	//quando eu clico em um botão la eu executo iso aq
    public void reveal(String text, int hIndex, int vIndex){
        
        if(!Board.haveFlag(hIndex, vIndex)){
            if (!"-1".equals(text)){

                buttons[hIndex][vIndex].setStyle(revealedStyle);
                buttons[hIndex][vIndex].setDisable(true);// desativando os botões

                if("0".equals(text)){

                    buttons[hIndex][vIndex].setText(" ");

                    for (int h = hIndex - 1; h <= hIndex + 1; h++) {
                        for (int v = vIndex - 1; v <= vIndex + 1; v++) {

                            boolean inBoundsX = (h >= 0) && (h < Board.getHTiles());
                            boolean inBoundsY = (v >= 0) && (v < Board.getVTiles());

                            if(inBoundsX && inBoundsY){
                                buttons[h][v].fire();//foi por isso que eu disse que era bom por o setOnAction, pq esse metodo fire executa o método setOnAction do botão (teoria minha)
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
    
    public void checkVictory(){//Toda vez q vc clica, esse método vai ver se vc venceu ou n, infelizmente eu nunca testei ele pq eu nunca venci e sou preguiçoso demais pra escrever um bot pra vencer isso.
        int revealedOnes = 0;//ele ve as casas que eu ja revelei, e ve se esse numero e igual ao (numero total - numero de minas)
        
        for (int x = 0; x < Board.getHTiles(); x++) {
            for (int y = 0; y < Board.getVTiles(); y++) {
                if (buttons[x][y].getStyle().equals(revealedStyle)){
                    revealedOnes += 1;
                }
            }
        }
        
        if(Board.getTotalMines()== (Board.getHTiles() * Board.getVTiles() - revealedOnes)){
            addPlayerInRank();
        }
    }
    //esse método aq bota a imagemzinha das minas no botão
    public void lost(){
        for (int x = 0; x < Board.getHTiles(); x++) {
            for (int y = 0; y < Board.getVTiles(); y++) {
		    //muda o estilo dos botões e desativa eles tudo
                buttons[x][y].setStyle(revealedStyle);
                buttons[x][y].setDisable(true);
                
                if("-1".equals(buttons[x][y].getText())){
			//botando imagemzinh da mina onde tem mina
                    Image image = new Image(getClass().getResourceAsStream("Mine.png"));
                    buttons[x][y].setText("");
                    buttons[x][y].setGraphic(new ImageView(image));
                }
                if("0".equals(buttons[x][y].getText())){
                    buttons[x][y].setText(""); //eu n lembro pq tem iso aq, mas de acordo com minhas habilidades em engenharia reversa, eu posso dizer que se tiver 0 no botão, esse 0 vai virar nada
                }
            }
	
		//bota pra n aparecer janelinha de pop up e muda o nome do botão-kun
	canAsk = false;
	resetButton.setText("Try Again!");
        }
  	addPlayerInRank();
    }
    
    public void addPlayerInRank(){//esse método bota o vencedor no aquivo de ranking	
        time = Timer.getText().replace("Time:  \n ","").replace(" : ","").replace("Time:", "").replace("\n", "").replace(":","").replace(" ", "");
	//n me pergunte sobre isso aq em cima, só funciona
	    	
        Ranking.append(name, time);
    }
       
	//esse método aqui é pra formatar o tempo pra eu poder exibir ele, também foi copiado e colado
    public void displaySeconds(long startTime){
                long elapsedTime = System.currentTimeMillis() - startTime;
                long elapsedSeconds = elapsedTime / 1000;
                long secondsDisplay = elapsedSeconds % 60;
                long elapsedMinutes = elapsedSeconds / 60;
        
                Timer.setText("Time:\n" + String.valueOf(elapsedMinutes) + " : "+ String.valueOf(secondsDisplay));          
    }

     
}
