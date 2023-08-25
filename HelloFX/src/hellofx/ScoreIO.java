package hellofx;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.text.Text;

public class ScoreIO {
	   private ArrayList<Score> scores; 
	    
	    public ScoreIO() {
	        scores = new ArrayList<>();
	    }
//	    method to load highScores from fiel
	    public ArrayList<Score> loadScoresFromFile() {
	        try {
	            FileInputStream fis = new FileInputStream("HighScores.txt");
//	            read it as object
	            ObjectInputStream ois = new ObjectInputStream(fis);
	            scores = (ArrayList<Score>) ois.readObject();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return scores;
	    }
	    
//	    rearrange the highScores
	    public void updateScores(ArrayList<Score> scores) {  
	        scores.sort((s1, s2) -> s2.getValue() - s1.getValue()); // sort the scores in descending order
	        scores = new ArrayList<>(scores.subList(0, Math.min(scores.size(), 10))); // keep only the top 10 scores
	        System.out.println("scores updated");
	    }
	    
//	    display the score
	    public void displayScores(Text pName,Text pScore,ArrayList<Score> scores) {

	    	StringBuilder sb1 = new StringBuilder();
	    	StringBuilder sb2 = new StringBuilder();
	    	for (int i = 0; i < scores.size(); i++) {
	            Score score = scores.get(i);
	            sb1.append((i + 1) + ". " + score.getName()+"\n");
	            sb2.append(score.getValue()+"\n");

	    	}
	    	pName.setText(sb1.toString());
	    	pScore.setText(sb2.toString());
	    	}
	    
//	    Check if player score is high enough to be saved in high scores list by comparing to the last score in the list
//	    if there is less than 10 high scores in the list, automatically return true
	    public Boolean isHighScore(ArrayList<Score> scores,int playerScore) {
	    	if (scores.size()>=10) {
	    	int lastScoreValue = scores.get(scores.size() - 1).getValue();
	    	return (playerScore>lastScoreValue);
	    	}else {
	    		return true;
	    	}
	    }
	    
//	    save the new highScores back to txt
	    public void saveScoresToFile(ArrayList<Score> scores) {
	        try {
	            FileOutputStream fis = new FileOutputStream("HighScores.txt");
	            ObjectOutputStream ois = new ObjectOutputStream(fis);
	            ois.writeObject(scores);
	            System.out.println("HighScore are saved");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
//	    add the user high score to the loaded highScores list
	    public void addScores(String name,int value,ArrayList<Score> scores) {
	    	Score score = new Score(name, value);
	        scores.add(score);
	    }
	    

	    
	}









