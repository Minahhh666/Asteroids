package hellofx;

import javafx.scene.paint.Color;

//basic class to create all objects in game
public class Rectangle {
	// (x,y) represents top-left corner of Rectangle
	private double x;
	private double y;
	private double width;
	private double height;
	
	public Rectangle() {
		this.setPosition(0, 0);
		this.setSize(1, 1);
	}
	
	public Rectangle(double x, double y, double w, double h) {
		this.setPosition(x, y);
		this.setSize(w, h);
	}
	
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
		
	}
	
	public void setSize(double w, double h) {
		this.width = w;
		this.height = h;
	}
	
//	check if two objects overlap
	public boolean overlaps(Rectangle other) {
		boolean noOverlap = this.x + this.width < other.x ||
				other.x + other.width < this.x ||
				this.y + this.height < other.y ||
				other.y + other.height < this.y;
		
		return !noOverlap;
	}

}
