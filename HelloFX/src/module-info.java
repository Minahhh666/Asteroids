module HelloFX {
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.controls;
	requires javafx.base;
	
	
	
	
	opens hellofx to javafx.fxml;
    exports hellofx;

}