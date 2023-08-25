package hellofx;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

//all game objects with basic functions
public class Sprite {
	Vector position;
	Vector velocity;
	double rotation; // degrees
	Rectangle boundary;
	Polygon polygon;
	double elapsedTime; // seconds
	
	public Sprite() {
		this.position = new Vector();
		this.velocity = new Vector();
		this.rotation = 0;
		this.boundary = new Rectangle();
		this.elapsedTime = 0;
	}
	
	public Sprite(Double[] shape) {
		this();
		setPolygon(shape);
	}
	
//	set the appearance and the boundary for collision
	public void setPolygon(Double[] shape) {
		this.polygon = new Polygon();
		this.polygon.getPoints().addAll(shape);
		this.polygon.setFill(Color.RED);
		this.polygon.setStroke(Color.WHITE);
		this.polygon.setStrokeWidth(2.0);
		Bounds bounds = this.polygon.getBoundsInLocal();
		this.boundary.setSize(bounds.getWidth(), bounds.getHeight());
	}
	
//	set color for objects
	public void setColor(Color fill,Color stroke) {
		
		this.polygon.setFill(fill);
		this.polygon.setStroke(stroke);

	}
	
//	get the size of boundary
	public double getSize() {
		Bounds bounds = this.polygon.getBoundsInLocal();
		return bounds.getWidth();
	}
	
//	get the collision boundary for the objects
	public Rectangle getBoundary() {
		this.boundary.setPosition(this.position.x, this.position.y);
		return this.boundary;
	}
	
//	check if two objects overlap
	public boolean overlaps(Sprite other) {
		return this.getBoundary().overlaps(other.getBoundary());
	}
	
//	function to allow objects to reappear on the other side of screen when it is out of screen
	public void wrap(double screenWidth, double screenHeight) {
		Bounds bounds = this.polygon.getBoundsInLocal();
		double halfWidth = bounds.getWidth()/2;
		double halfHeight = bounds.getHeight()/2;
		if (this.position.x + halfWidth < 0)
			this.position.x = screenWidth + halfWidth;
		if (this.position.x > screenWidth + halfWidth)
			this.position.x = -halfWidth;
		if (this.position.y + halfHeight < 0)
			this.position.y = screenHeight + halfHeight;
		if (this.position.y > screenHeight + halfHeight)
			this.position.y = -halfHeight;
	}
	
//	method that keeps track of time to update the position,velocity of an object
	public void update(double deltaTime) {
		// increase elapsed time for this sprite
		this.elapsedTime += deltaTime;
		// update position according to velocity
		this.position.add(this.velocity.x * deltaTime, this.velocity.y * deltaTime);
		// wrap around screen
		this.wrap(800, 600);
	}
	
//	display the object on the screen
	public void render(GraphicsContext context) {
	    context.save();

//	    temporarily adjust the point of translation to middle of object for accurate transition
	    Bounds bounds = this.polygon.getBoundsInLocal();
	    context.translate(this.position.x, this.position.y);
	    context.rotate(this.rotation);
	    context.translate(-bounds.getWidth() / 2, -bounds.getHeight() / 2);

	    double[] xPoints = new double[this.polygon.getPoints().size() / 2];
	    double[] yPoints = new double[this.polygon.getPoints().size() / 2];

	    for (int i = 0, j = 0; i < this.polygon.getPoints().size(); i += 2, j++) {
	        xPoints[j] = this.polygon.getPoints().get(i);
	        yPoints[j] = this.polygon.getPoints().get(i + 1);
	    }

	    context.setFill(this.polygon.getFill());
	    context.setStroke(this.polygon.getStroke());
	    context.setLineWidth(this.polygon.getStrokeWidth());
	    context.fillPolygon(xPoints, yPoints, xPoints.length);
	    context.strokePolygon(xPoints, yPoints, xPoints.length);

	    context.restore();
	}

}
