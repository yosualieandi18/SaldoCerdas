package application;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Main extends Application {

	private static Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws IOException {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Splash.fxml"));
			Parent root = loader.load();

			Scene scene = new Scene(root);

			String css = this.getClass().getResource("application.css").toExternalForm();
			scene.getStylesheets().add(css);
			primaryStage.setScene(scene);

			primaryStage.setTitle("Saldo Cerdas");
			primaryStage.show();

			FadeTransition fadeIn = new FadeTransition(Duration.millis(4000), root);
			fadeIn.setFromValue(0.0);
			fadeIn.setToValue(1.0);
			fadeIn.play();

			fadeIn.setOnFinished(event -> {
				primaryStage.close();
				loadRegister(primaryStage);
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void loadRegister(Stage stage) {
		try {
			FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("Register.fxml"));
			Parent root = mainLoader.load();
			Scene scene = new Scene(root);

			String css = this.getClass().getResource("application.css").toExternalForm();
			scene.getStylesheets().add(css);
			

			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
