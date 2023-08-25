package hellofx;

//class to set velocity and angle of an object
public class Vector {
	double x;
	double y;
	
	public Vector() {
		this.set(0, 0);
	}
	
	public Vector(double x, double y) {
		this.set(x, y);
	}
	
	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return(this.x);
	}
	public double getY() {
		return(this.y);
	}
	public void add(double dx, double dy) {
		this.x += dx;
		this.y += dy;
	}
	
	public void multiply(double m) {
		this.x *= m;
		this.y *= m;
	}
	
//	use pythagoras theorem to calculate the length of a triangle which is the result velocity
	public double getLength() {
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}
	
//	set the velocity
	public void setLength(double L) {
		double currentLength = this.getLength();
		//     assume current angle is 0 if length is 0
		if (currentLength == 0) {
			this.set(L, 0);
		}
		else { 
			this.multiply(L/currentLength);
		}
	}
	
//	get the angle of an object currently facing
	public double getAngle() {
		return Math.toDegrees(Math.atan2(this.y, this.x));
	}
	
//	set the angle for an object
	public void setAngle(double angleDegrees) {
		double L = this.getLength();
		double angleRadians = Math.toRadians(angleDegrees);
		this.x = L * Math.cos(angleRadians);
		this.y = L * Math.sin(angleRadians);
	}
}
