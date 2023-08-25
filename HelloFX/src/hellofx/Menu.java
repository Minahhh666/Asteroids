package hellofx;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Menu {
	  public void start(Stage stage) {
	        // Create menu title
	        Text title = new Text("Asteroids");
	        title.setFont(Font.font("OCR A Extended", FontWeight.BOLD, 100));
	        title.setFill(Color.RED);
	        title.setStroke(Color.WHITE);
	        title.setStrokeWidth(2);

	
	        
	        // new game button
	        Button newGame = new Button();	        
//	        set style for button
	        newGame.setText("New Game");
	        newGame.setStyle("-fx-background-color: Grey;");
	        newGame.setTextFill(Color.WHITE);
	        newGame.setFont(Font.font("OCR A Extended", FontWeight.BOLD, 30));
//	        set mouse event
	        newGame.setOnMouseEntered((MouseEvent event) -> {
	            	newGame.setStyle("-fx-background-color: rgb(255,180,0);"); // change color when hovered
	            });
	        newGame.setOnMouseExited((MouseEvent event) -> {
            	newGame.setStyle("-fx-background-color: grey;"); // change color when hovered
            });
	        newGame.setOnMouseClicked((MouseEvent event) -> {
	            try {
	                new Asteroids().startGame(stage);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        });	        	            
	        
	        // high scores button
	        Button highScores = new Button();	        
//	        set style for button
	        highScores.setText("high Scores");
	        highScores.setStyle("-fx-background-color: Grey;");
	        highScores.setTextFill(Color.WHITE);
	        highScores.setFont(Font.font("OCR A Extended", FontWeight.BOLD, 30));
//	        set mouse event
	        highScores.setOnMouseEntered((MouseEvent event) -> {
	        	highScores.setStyle("-fx-background-color: red;"); // change color when hovered
	            });
	        highScores.setOnMouseExited((MouseEvent event) -> {
	        	highScores.setStyle("-fx-background-color: grey;"); // change color when hovered
            });
	        highScores.setOnMouseClicked((MouseEvent event) -> {
	        	 try {
		                new HighScores().start(stage);
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
	        });

	        // instruction button
	        Button instructions = new Button();	        
//	        set style for button
	        instructions.setText("Instructions");
	        instructions.setStyle("-fx-background-color: Grey;");
	        instructions.setTextFill(Color.WHITE);
	        instructions.setFont(Font.font("OCR A Extended", FontWeight.BOLD, 30));
//	        set mouse event
	        instructions.setOnMouseEntered((MouseEvent event) -> {
	        	instructions.setStyle("-fx-background-color: brown;"); // change color when hovered
	            });
	        instructions.setOnMouseExited((MouseEvent event) -> {
	        	instructions.setStyle("-fx-background-color: grey;"); // change color when hovered
            });
	        instructions.setOnMouseClicked((MouseEvent event) -> {
	        	try {
	                new Instructions().start(stage);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        });	        
	              
	     // Add some space between title and buttons
	        Region space = new Region();
	        space.setMinHeight(30);

//	        put all together on a VBox
	        VBox menu = new VBox(20);
	        menu.setSpacing(30);
	        menu.getChildren().addAll(title, space, newGame, highScores, instructions);
	        menu.setAlignment(Pos.CENTER);

	        
	              
	        // set a background
	        Image image = new Image("BG.jpg");
	        BackgroundImage backgroundImage = new BackgroundImage(image,
	            BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, 
	            BackgroundPosition.DEFAULT, 
	            new BackgroundSize(800, 600, false, false, false, true));
	        Background background = new Background(backgroundImage);
	        menu.setBackground(background);

	        
	        // Create and show the scene
	        Scene menuScene = new Scene(menu, 800, 600);
	        stage.setScene(menuScene);
	        stage.show();
	    }
	  
}
