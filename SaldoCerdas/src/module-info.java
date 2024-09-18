module SaldoCerdas {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires java.sql;
	requires java.base;
	requires jdk.compiler;
	requires jdk.javadoc;
	requires java.desktop;
	
	opens application to javafx.graphics, javafx.fxml;
	exports application;
}
