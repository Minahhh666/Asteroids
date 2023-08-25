package hellofx;

import java.io.Serializable;

//basic object for the high scores that are loaded, display,arrange, or saved 
public class Score implements Comparable<Score>,Serializable {
	    
		private static final long serialVersionUID = 1L;
	    private String name;
	    private int value;
	    
	    public Score(String name, int value) {
	        this.name = name;
	        this.value = value;
	    }
	    
	    public String getName() {
	        return name;
	    }
	    
	    public int getValue() {
	        return value;
	    }
	    
	    @Override
	    public int compareTo(Score other) {
	        return other.getValue() - this.value; // sort in descending order
	    }
}

