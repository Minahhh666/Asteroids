package hellofx;

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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Instructions {
	public void start(Stage stage) {
	        // Create instructions title
	        Text instructionsTitle = new Text("Instructions");
	        instructionsTitle.setFont(Font.font("OCR A Extended", FontWeight.BOLD, 80));
	        instructionsTitle.setFill(Color.ORANGE);
	        instructionsTitle.setStroke(Color.YELLOW);
	        instructionsTitle.setStrokeWidth(1);

	        // Create instruction content
	        Text instructionsContent = new Text("PRESS UP TO APPLY THRUST\n\n"
	                + "PRESS LEFT TO TURN LEFT\n\n"
	                + "PRESS RIGHT TO TURN RIGHT\n\n"
	                + "PRESS SPACE TO FIRE\n\n"
	                + "PRESS DOWN TO PERFORM HYPERSPACE JUMP\n\n"
	                );

	        instructionsContent.setFont(Font.font("OCR A Extended", FontWeight.BOLD, 30));
	        instructionsContent.setFill(Color.PALEGREEN);

	        VBox instruction = new VBox(20);
	   
	        
	        // Create back button
	        Button back = new Button();	        
//	        set style for button
	        back.setText("Back");
	        back.setStyle("-fx-background-color: grey;");
	        back.setTextFill(Color.WHITE);
	        back.setFont(Font.font("OCR A Extended", FontWeight.BOLD, 30));
//	        set mouse event
	        back.setOnMouseEntered((MouseEvent event) -> {
	        	back.setStyle("-fx-background-color: blue;"); // change color when hovered
	            });
	        back.setOnMouseExited((MouseEvent event) -> {
	        	back.setStyle("-fx-background-color: grey;"); // change color when hovered
            });
	        back.setOnMouseClicked(event -> {
	        	 try {
	                 new Asteroids().start(stage);
	             } catch (Exception e) {
	                 e.printStackTrace();
	             }
	        });

	        // Create layout
	        VBox instructions = new VBox(20);
	        instructions.getChildren().addAll(instructionsTitle, instructionsContent, back);
	        instructions.setAlignment(Pos.CENTER);

	        // set a background
	        Image image = new Image("BG.jpg");
	        BackgroundImage backgroundImage = new BackgroundImage(image,
	            BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, 
	            BackgroundPosition.DEFAULT, 
	            new BackgroundSize(800, 600, false, false, false, true));
	        Background background = new Background(backgroundImage);
	        instructions.setBackground(background);
	        
	        // Create and show the scene
	        Scene instructionsScene = new Scene(instructions, 800, 600);
	        stage.setScene(instructionsScene);
	        stage.show();
	    }
}
