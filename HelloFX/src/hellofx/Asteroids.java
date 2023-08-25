package hellofx;


import java.util.ArrayList;
import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Asteroids extends Application {
    public final static int SCREEN_WIDTH = 800;
    public final static int SCREEN_HEIGHT = 600;
    private BorderPane root;
    

    public static void main(String[] args) {
        launch(args);
    }
    private int level;
    private int score;
    private Boolean invincibility = false;
    private Boolean isAlien = false;
    private long hSJend = System.nanoTime();
    private long hSJstart;
    private long alienShootEnd = System.nanoTime();
    private long alienShootStart;
    private long alienEnd = System.nanoTime();
    private long alienStart;
    private AnimationTimer gameloop;
    private int life = 3;
	private int initialScore = 0;
	private Boolean safeHyperSpace;
    
	
//	method to split asteroids into smaller asteroids
	private void splitAsteroid(Sprite rock, ArrayList<Sprite> List) {
    	Double[] MediumAstpoints = new Double[]{0.0, 0.0,
    		    15.0, 1.0,
    		    25.0, 0.0,
    		    30.0, 10.0,
    		    25.0, 20.0,
    		    15.0, 25.0,
    		    5.0, 20.0,
    		    0.0, 10.0
    	};    	   	
    	Double[] SmallAstpoints = new Double[]{0.0, 0.0,
    		    7.5, 0.5,
    		    12.5, 0.0,
    		    15.0, 5.0,
    		    12.5, 10.0,
    		    7.5, 12.5,
    		    2.5, 10.0,
    		    0.0, 5.0
    	};
    	Sprite Mediumasteroid = new Sprite(MediumAstpoints);
    	Sprite Smallasteroid = new Sprite(SmallAstpoints);
		double mediumSize = Mediumasteroid.getSize();
		double smallSize = Smallasteroid.getSize();
//		check the size to decide which one to split into
//		set random position,velocity, and angle
		if (rock.getSize() > mediumSize) {
			Sprite asteroid1 = new Sprite(MediumAstpoints);
			Sprite asteroid2 = new Sprite(MediumAstpoints);
			double x = rock.position.x;
			double y = rock.position.y;
			asteroid1.position.set(x,y);
			asteroid2.position.set(x,y);
			double angle1 = rock.velocity.getAngle()+180 * Math.random()-90;
			asteroid1.velocity.setLength(rock.velocity.getLength()+20 * Math.random());
			asteroid1.velocity.setAngle(angle1);
			List.add(asteroid1);
			double angle2 = rock.velocity.getAngle()+180 * Math.random()-90;
			asteroid2.velocity.setLength(rock.velocity.getLength()+20 * Math.random());
			asteroid2.velocity.setAngle(angle2);
			List.add(asteroid2);
		}
		else if(rock.getSize() > smallSize && rock.getSize() <= mediumSize) {
			Sprite asteroid1 = new Sprite(SmallAstpoints);
			Sprite asteroid2 = new Sprite(SmallAstpoints);
			double x = rock.position.x;
			double y = rock.position.y;
			asteroid1.position.set(x,y);
			asteroid2.position.set(x,y);
			double angle1 = rock.velocity.getAngle()+180 * Math.random()-90;
			asteroid1.velocity.setLength(rock.velocity.getLength()+20 * Math.random());
			asteroid1.velocity.setAngle(angle1);
			List.add(asteroid1);
			double angle2 = rock.velocity.getAngle()+180 * Math.random()-90;
			asteroid2.velocity.setLength(rock.velocity.getLength()+20 * Math.random());
			asteroid2.velocity.setAngle(angle2);
			List.add(asteroid2);
		}
	}
    
//    spawn asteroids at the start of a new level
	private void spawnAsteroid(int level,ArrayList<Sprite> List) {
		Double[] BigAstpoints = new Double[]{
				0.0, 0.0,
			    30.0, 2.0,
			    50.0, 0.0,
			    60.0, 20.0,
			    50.0, 40.0,
			    30.0, 50.0,
			    10.0, 40.0,
			    0.0, 20.0
		};	    
    for(int n=0; n<level; n++) {
		Sprite asteroid = new Sprite(BigAstpoints);;
		double x = 800 * Math.random();
		double y = 600 * Math.random();
		asteroid.position.set(x,y);
		double angle = 360 * Math.random();
		asteroid.velocity.setLength(50);
		asteroid.velocity.setAngle(angle);
		List.add(asteroid);
		}
    }
    
//    print a message on the screen
    private void Message(String message,int duration) {
        Text text = new Text(270, 300, message);
        text.setFont(Font.font("OCR A Extended", FontWeight.BOLD, 50));
        text.setFill(Color.WHITE);
        text.setStroke(Color.GOLD);
        text.setStrokeWidth(2);
        
        root.getChildren().add(text);
        PauseTransition pause = new PauseTransition(Duration.seconds(duration));
        pause.setOnFinished(e -> root.getChildren().remove(text));
        pause.play();
    }
 
    
//    set the ship to be immune from collision
    private void invincible(Sprite ship) {
    invincibility = true;
	PauseTransition pause = new PauseTransition(Duration.seconds(60));
    pause.setOnFinished(e -> invincibility = false);
    pause.play();
    
//    set a 3 seconds flashing effect to represent invincibility
	Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0.2), f -> ship.setColor(Color.YELLOW,Color.YELLOW)),
            new KeyFrame(Duration.seconds(0.4), f -> ship.setColor(Color.GREY,Color.CYAN))
      );
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
    
	PauseTransition pause2 = new PauseTransition(Duration.seconds(3.2));
    pause2.setOnFinished(e ->timeline.stop());
    pause2.play();
    }
    

    	

    
    public void start(Stage stage) throws Exception {
        new Menu().start(stage);
    }
    

	public void startGame(Stage mainStage) {
		

//	    create the mainstage
		mainStage.setTitle("Asteroids");		
		root = new BorderPane();
		root.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		Scene mainScene = new Scene(root);
		mainStage.setScene(mainScene);
		
//		add background images
		Canvas canvas = new Canvas(800,600);
		GraphicsContext context = canvas.getGraphicsContext2D();
		Image backgroundImage = new Image("BG2.jpg");
		context.drawImage(backgroundImage, 0, 0,800,600);
		root.setCenter(canvas);
		
		// handle continuous inputs (as long as key is pressed)
		ArrayList<String> keyPressedList = new ArrayList<String>();
		// handle discrete inputs (once per key press)
		ArrayList<String> keyJustPressedList = new ArrayList<String>();
		
		mainScene.setOnKeyPressed(
				(KeyEvent event) -> {
					String keyName = event.getCode().toString();
					// avoid adding duplicates to List
					if (!keyPressedList.contains(keyName)) {
						keyPressedList.add(keyName);
						keyJustPressedList.add(keyName);
					}
				}
		);
		
		mainScene.setOnKeyReleased(
				(KeyEvent event) -> {
					String keyName = event.getCode().toString();
					if (keyPressedList.contains(keyName))
						keyPressedList.remove(keyName);
				}
		);
		
//		create a spaceship object
		Double[] Shippoints = new Double[]{0.0, 8.0,
				0.0, 22.0,
				8.0, 18.5,
				13.0, 19.0,
				9.0, 30.0,
				25.0, 18.0,
				35.0, 15.0,
				25.0, 12.0,
				9.0, 0.0,
				13.0, 11.0,
				8.0,11.5};
		Sprite spaceship = new Sprite(Shippoints);
		spaceship.setColor(Color.GREY,Color.CYAN);
		spaceship.position.set(100, 300);
		spaceship.velocity.set(50, 0); 

//		create an alien object
		Double[] alienpoints = new Double[] {50.0, 40.0,
				24.0, 6.0,
				20.0, 10.0,
				27.6, 21.9,
				22.0, 31.9,
				24.0, 36.0,
				20.0, 40.0,
				24.0, 44.0,
				22.0, 49.1,
				27.6, 58.1,
				20.0, 70.0,
				24.0,74.0};
		Sprite alien = new Sprite(alienpoints);
		alien.setColor(Color.WHITE, Color.WHITE);
		alien.position.set(0, 0);
		alien.velocity.set(0, 0);
	
		
		ArrayList<Sprite> alienBulletList = new ArrayList<Sprite>();
		ArrayList<Sprite> bulletList = new ArrayList<Sprite>();
		ArrayList<Sprite> asteroidList = new ArrayList<Sprite>();
		
//		set the initial level,asteroids, and info
		level = 1;
		spawnAsteroid(level,asteroidList);
		score = 0;
				
		 gameloop = new AnimationTimer() {
			 
			public void handle(long nanotime) {
				// process user input
				if (keyPressedList.contains("LEFT"))
					spaceship.rotation -= 5;
				if (keyPressedList.contains("RIGHT"))
					spaceship.rotation += 5;
//				thrust
				if (keyPressedList.contains("UP")) {
					 double changeX = Math.cos(Math.toRadians(spaceship.rotation));
				     double changeY = Math.sin(Math.toRadians(spaceship.rotation));
				     spaceship.velocity.add(changeX*3, changeY*3);
				}
//				hyperspace
				if (keyPressedList.contains("DOWN")) {
//					check if there is at least a 3sec gap between hyperspace jump
					hSJstart = System.nanoTime();
					if (hSJstart - hSJend>= 3000000000L) { 
					safeHyperSpace = false;
					while (!safeHyperSpace) {
//					set a random position to ship and check if it collides with other asteroids or alien
//					if it does, change a position and check again
					var jumpX = Math.random() * (Asteroids.SCREEN_WIDTH - 50);
		            var jumpY = Math.random() * (Asteroids.SCREEN_HEIGHT - 50);
		            spaceship.position.set(jumpX, jumpY);
		            for (int asteroidNum = 0; asteroidNum < asteroidList.size(); asteroidNum++) {
						Sprite asteroid = asteroidList.get(asteroidNum);
						if (spaceship.overlaps(asteroid)) {
							break;
						}
						if (spaceship.overlaps(alien)) {
							break;
						}
						safeHyperSpace = true;
					}   
					}
					hSJend = System.nanoTime();
					}				
				}
					
//				fire
				if(keyJustPressedList.contains("SPACE")) {
					Double[] Bulletpoints = new Double[]{-2.5, -2.5,
						    2.5, -2.5,
						    2.5, 2.5,
						    -2.5, 2.5};
				
					Sprite bullet = new Sprite(Bulletpoints);
					bullet.position.set(spaceship.position.x, spaceship.position.y);
					bullet.velocity.setLength(400);
					bullet.setColor(Color.GREY,Color.CYAN);
					bullet.velocity.setAngle(spaceship.rotation);
					bulletList.add(bullet);
				}
				
//				Alien appearing
				if (!isAlien) {
				alienStart = System.nanoTime();
				var time = Math.random();
//				check if there is a gap of at least 10sec between next spawning and the time alien was destroyed
				if(alienStart-alienEnd>=(10000000000L+ time*10000000000L)) {
					Double x,y;
					double appearPosition = Math.random();
			        int appear = (int) Math.round(appearPosition) % 2;
			        if (appear==0) {
			        x = 0.0;
			        y = 600 * Math.random();
			        alien.position.set(x, y);
			        }
			        else {
			        	x = 800 * Math.random();
				        y = 0.0;
				        alien.position.set(x, y);
			        }
			        Double a = 200 * (Math.random()*2-1);
			        Double b = 200 * (Math.random()*2-1);			        
			        alien.velocity.set(a, b);
			        isAlien = true;
				}
				}
				
//				Alien autoshooting
				if(isAlien) {
				alienShootStart = System.nanoTime();
				if (alienShootStart - alienShootEnd>= 3000000000L) { 
					Double[] Bulletpoints = new Double[]{-2.5, -2.5,2.5, -2.5,2.5, 2.5,-2.5, 2.5};
					for(var i=0;i<3;i++) {
						Sprite bullet = new Sprite(Bulletpoints);
						bullet.position.set(alien.position.x, alien.position.y);
						bullet.setColor(Color.BLACK, Color.YELLOW);
						bullet.velocity.setLength(200);
						bullet.velocity.setAngle(-spaceship.rotation*Math.random());
						alienBulletList.add(bullet);
					}
				alienShootEnd = System.nanoTime();
				}
				}
				
				
				
				// after processing user input, clear justPressedList
				keyJustPressedList.clear();
				
				if(isAlien) {
				alien.update(1/60.0);
				}
				spaceship.update(1/60.0);
				for(Sprite asteroid : asteroidList)
					asteroid.update(1/60.0);
				
				// update bullets; destroy if 2 seconds passed
				for (int n=0; n<bulletList.size(); n++) {
					Sprite bullet = bulletList.get(n);
					bullet.update(1/60.0);
					if (bullet.elapsedTime > 2)
						bulletList.remove(n);
				}
				
				// update alienBullets; destroy if 2 seconds passed
				if(isAlien) {
				for (int n=0; n<alienBulletList.size(); n++) {
					Sprite bullet = alienBulletList.get(n);
					bullet.update(1/60.0);
					if (bullet.elapsedTime > 2)
						alienBulletList.remove(n);
				}
				}
				
//				when spaceship bullet overlaps asteroid, remove both
//				when spaceship bullet overlaps alien, remove alien and set the boolean to false
				for (int bulletNum = 0; bulletNum < bulletList.size(); bulletNum++) {
					Sprite bullet = bulletList.get(bulletNum);
					for (int asteroidNum = 0; asteroidNum < asteroidList.size(); asteroidNum++) {
						Sprite asteroid = asteroidList.get(asteroidNum);
						if (bullet.overlaps(asteroid)) {
							bulletList.remove(bulletNum);
							asteroidList.remove(asteroidNum);
							splitAsteroid(asteroid, asteroidList);
							score += 100;
						}
					}
				}
				for (int bulletNum = 0; bulletNum < bulletList.size(); bulletNum++) {
					Sprite bullet = bulletList.get(bulletNum);
					if ((bullet.overlaps(alien))&&(isAlien)) {
						bulletList.remove(bulletNum);
						alien.position.set(0, 0);						
						alien.velocity.set(0, 0);
						score += 200;
						isAlien = false;
						alienEnd = System.nanoTime();
				}
				}
				
//				when alien bullet overlaps spaceship, lose one life
				for (int alienBulletNum = 0; alienBulletNum < alienBulletList.size(); alienBulletNum++) {
					Sprite bullet = alienBulletList.get(alienBulletNum);
						if (bullet.overlaps(spaceship)) {
							alienBulletList.remove(alienBulletNum);;
							life --;
//							if player still has life, lose one live and reset to middle position with invincibility
							if(life != 0) {
							invincible(spaceship);
							spaceship.position.set(400, 300);
							spaceship.velocity.setLength(0);
							}
//							if player runs out of life, game over
							else {
								Message("GAME OVER",3);
								gameloop.stop();
								ScoreIO scoreIO = new ScoreIO();
								ArrayList<Score> highScoresInfo = scoreIO.loadScoresFromFile();
								PauseTransition pause = new PauseTransition(Duration.seconds(3));
						        pause.setOnFinished(e ->{ 	
//						        if player scored a high score, enter high score page
								if (scoreIO.isHighScore(highScoresInfo,score)) {
									try {
										GameOverHighScore gameOverHighScore= new GameOverHighScore();
										gameOverHighScore.highScore(score);
						                gameOverHighScore.start(mainStage);					                
						            } catch (Exception ex) {
						                ex.printStackTrace();
						            }
								}
//								otherwise, back to menu
								else {
									try {
						                new Menu().start(mainStage);
						            } catch (Exception ex) {
						                ex.printStackTrace();
						            }
								}
						    });
						    pause.play();
						}
					}
				}
				
				
				//check if ship overlaps with asteroids or alien
				if (!invincibility ) {
				for (int asteroidNum = 0; asteroidNum < asteroidList.size(); asteroidNum++) {
					Sprite asteroid = asteroidList.get(asteroidNum);
					
					if ((spaceship.overlaps(asteroid))||(spaceship.overlaps(alien))) {
						life --;
//						if player still has life, lose one live and reset to middle position with invincibility
						if(life != 0) {
						invincible(spaceship);
						spaceship.position.set(400, 300);
						spaceship.velocity.setLength(0);
						}
//						if player runs out of life, game over
						else {
							Message("GAME OVER",3);
							gameloop.stop();
							ScoreIO scoreIO = new ScoreIO();
							ArrayList<Score> highScoresInfo = scoreIO.loadScoresFromFile();
							PauseTransition pause = new PauseTransition(Duration.seconds(3));
					        pause.setOnFinished(e ->{ 	
//					        if player scored a high score, enter high score page
							if (scoreIO.isHighScore(highScoresInfo,score)) {
								try {
									GameOverHighScore gameOverHighScore= new GameOverHighScore();
									gameOverHighScore.highScore(score);
					                gameOverHighScore.start(mainStage);					                
					            } catch (Exception ex) {
					                ex.printStackTrace();
					            }
							}
//							otherwise, back to menu
							else {
								try {
					                new Menu().start(mainStage);
					            } catch (Exception ex) {
					                ex.printStackTrace();
					            }
							}
							
					        });
					        pause.play();
						}
					}
				}
			}
//				update the background image
				context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		        context.drawImage(backgroundImage, 0, 0, 800, 600);
				
				spaceship.render(context);

//				render all objects onto the screen
				for (Sprite bullet: bulletList)
					bullet.render(context);
				if(isAlien) {
					alien.render(context);
				for (Sprite alienBullet: alienBulletList)
					alienBullet.render(context);
				}
				for (Sprite asteroid: asteroidList)
					asteroid.render(context);
				
//				draw life on screen
				context.setFill(Color.WHITE);
				context.setStroke(Color.GREEN);
				context.setFont(new Font("Arial Black", 40));
				context.setLineWidth(3);
				String textLive = "Life: " + life;
				int textLiveX = 50;
				int textLiveY = 50;
				context.fillText(textLive, textLiveX, textLiveY);
				context.strokeText(textLive, textLiveX, textLiveY);
				
				// draw score on screen
				context.setFill(Color.WHITE);
				context.setStroke(Color.GREEN);
				context.setFont(new Font("Arial Black", 40));
				context.setLineWidth(3);
				String text = "Score: " + score;
				int textX = 400;
				int textY = 50;
				context.fillText(text, textX, textY);
				context.strokeText(text, textX, textY);
				
				
				
				
//				increase level when all asteroids are destroyed
				if (asteroidList.isEmpty()) {
					level++;			
					spawnAsteroid(level,asteroidList);
					Message("LEVEL++",1);					
					invincible(spaceship);
				}
				
//				increase life every 2000 points
				if (score-initialScore>=2000) {
					life++;
					initialScore = score;
					Message("LIFE++",1);
				}
			}
		};
		
		gameloop.start();
//		invincible at the start of the game to prevent collision with newly spawn asteroids
		invincible(spaceship);
		
		mainStage.show();
	}
}