package hellofx;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HighScores {
	public void start(Stage stage) {
	// Create high Scores title
    Text highScoresTitle = new Text("High Scores");
    highScoresTitle.setFont(Font.font("OCR A Extended", FontWeight.BOLD, 70));
    highScoresTitle.setFill(Color.PINK);
    highScoresTitle.setStroke(Color.MAGENTA);
    highScoresTitle.setStrokeWidth(1);

    // Load high scores and display it
    Text highScoresName = new Text();
    Text highScoresScore = new Text();
    ScoreIO scoreIO = new ScoreIO();
    ArrayList<Score> highScoresInfo = scoreIO.loadScoresFromFile();
    scoreIO.displayScores(highScoresName,highScoresScore,highScoresInfo);
//    formatting the style
    highScoresName.setFont(Font.font("OCR A Extended", FontWeight.BOLD, 30));
    highScoresName.setFill(Color.CYAN);

    highScoresScore.setFont(Font.font("OCR A Extended", FontWeight.BOLD, 30));
    highScoresScore.setFill(Color.CYAN);


    HBox nameScore = new HBox(40);
    nameScore.getChildren().addAll(highScoresName,highScoresScore);
    nameScore.setAlignment(Pos.CENTER);

    // Create back button
    Button back = new Button();	        
//    set style for button
    back.setText("Back");
    back.setStyle("-fx-background-color: Grey;");
    back.setTextFill(Color.WHITE);
    back.setFont(Font.font("OCR A Extended", FontWeight.BOLD, 30));
//    set mouse event
    back.setOnMouseEntered((MouseEvent event) -> {
    	back.setStyle("-fx-background-color: green;"); // change color when hovered
        });
    back.setOnMouseExited((MouseEvent event) -> {
    	back.setStyle("-fx-background-color: grey;"); // change color when hovered
    });
    back.setOnMouseClicked((MouseEvent event) -> {
    	 try {
             new Asteroids().start(stage);
         } catch (Exception e) {
             e.printStackTrace();
         }
    });
  
 // Add some space between title and buttons
    Region space = new Region();
    space.setMinHeight(20);

    // Create layout
    VBox highScores = new VBox(20);
    highScores.getChildren().addAll(highScoresTitle,space, nameScore, back);
    highScores.setAlignment(Pos.CENTER);

    // set a background
    Image image = new Image("BG.jpg");
    BackgroundImage backgroundImage = new BackgroundImage(image,
        BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, 
        BackgroundPosition.DEFAULT, 
        new BackgroundSize(800, 600, false, false, false, true));
    Background background = new Background(backgroundImage);
    highScores.setBackground(background);
    
    // Create and show the scene
    Scene highScoresScene = new Scene(highScores, 800, 600);
    stage.setScene(highScoresScene);
    stage.show();
}
}
