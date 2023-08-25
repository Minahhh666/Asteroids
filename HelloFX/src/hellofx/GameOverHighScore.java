package hellofx;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameOverHighScore {
	private int score;
	
    public void start(Stage stage) {
    	
    	
      
        
//        Add a Game Over title
        Text newHighScore = new Text("High Score");
        newHighScore.setFont(Font.font("OCR A Extended", FontWeight.BOLD, 80));
        newHighScore.setFill(Color.DARKGREEN);
        newHighScore.setStroke(Color.ORANGE);
        newHighScore.setStrokeWidth(1);
        
//        display the score
        Text highScores= new Text(String.valueOf(score));
        highScores.setFont(Font.font("OCR A Extended", FontWeight.BOLD, 80));
        highScores.setFill(Color.DARKRED);
        highScores.setStroke(Color.DARKRED);
        highScores.setStrokeWidth(1);
        
//        textfield to allow user entering name
        TextField name = new TextField();
        name.setFont(Font.font("OCR A Extended", FontWeight.BOLD, 30));
//        limit the user input length to 10 characters to maintain the high score layout
        name.setTextFormatter(new TextFormatter<String>(change -> 
        change.getControlNewText().length() <= 10 ? change : null));
        name.setMaxWidth(300); 
        name.setMaxHeight(30); 
        
//        save button
        Button save = new Button("Save");
        save.setStyle("-fx-background-color: rgb(160,220,255);");
        save.setTextFill(Color.WHITE);
        save.setFont(Font.font("OCR A Extended", FontWeight.BOLD, 30));
//        set mouse event
        save.setOnMouseEntered((MouseEvent event) -> {
        	save.setStyle("-fx-background-color: darkblue;"); // change color when hovered
            });
        save.setOnMouseExited((MouseEvent event) -> {
        	save.setStyle("-fx-background-color: rgb(160,220,255);"); // change color when hovered
        });
//        when clicked save, load the highScores.txt, add new score, rearrange it, and save it back to the txt
//        then back to menu
        save.setOnMouseClicked((MouseEvent event) -> {
        	ScoreIO scoreIO = new ScoreIO();
        	ArrayList<Score> highScoresInfo = scoreIO.loadScoresFromFile();
        	scoreIO.addScores(name.getText(),score,highScoresInfo);
        	scoreIO.updateScores(highScoresInfo);
        	scoreIO.saveScoresToFile(highScoresInfo);
        	System.out.print("Score saved");
        	Menu menu = new Menu();
            menu.start(stage);
            
        });
        
        HBox saveInfo = new HBox();
        saveInfo.getChildren().addAll(name,save);
        saveInfo.setAlignment(Pos.CENTER);    
         
//        back button if user do not want to save their score
        Button back = new Button();	        
        back.setText("Back");
        back.setStyle("-fx-background-color: grey;");
        back.setTextFill(Color.WHITE);
        back.setFont(Font.font("OCR A Extended", FontWeight.BOLD, 30));
//        set mouse event
        back.setOnMouseEntered((MouseEvent event) -> {
        	back.setStyle("-fx-background-color: orange;"); // change color when hovered
            });
        back.setOnMouseExited((MouseEvent event) -> {
        	back.setStyle("-fx-background-color: grey;"); // change color when hovered
        });
        back.setOnMouseClicked((MouseEvent event) -> {
        	 try {
                 new Menu().start(stage);
             } catch (Exception e) {
                 e.printStackTrace();
             }
        });
        
     // Create layout
        VBox GameOverHighScore = new VBox(50);
        GameOverHighScore.getChildren().addAll(newHighScore, highScores, saveInfo, back);
        GameOverHighScore.setAlignment(Pos.CENTER);
        
        // set a background
        Image image = new Image("GameOverHighScore.png");
        BackgroundImage backgroundImage = new BackgroundImage(image,
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
            BackgroundPosition.CENTER, 
            new BackgroundSize(100, 100, true, true, true, false));
        Background background = new Background(backgroundImage);
        GameOverHighScore.setBackground(background);
        
        Scene gameOverScene = new Scene(GameOverHighScore, 800, 600);
        stage.setScene(gameOverScene);
        stage.show();
    }
    
    public void highScore(int playerScore) {
    	score = playerScore;
    }
    
}
