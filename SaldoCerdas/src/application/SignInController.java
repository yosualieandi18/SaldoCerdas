package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignInController {

	@FXML
	public TextField usernameField;

	@FXML
	private PasswordField pinField;

	public Label daftarLabel;

	public void login(ActionEvent event) throws IOException {
		String username = usernameField.getText();
		String pin = pinField.getText();
		User.loggedInUsername = username;

		if (validateLogin(username, pin)) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
				Parent root = loader.load();

				Scene scene = new Scene(root);

				String css = this.getClass().getResource("application.css").toExternalForm();
				scene.getStylesheets().add(css);

				Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				primaryStage.setScene(scene);
				primaryStage.show();

			} catch (Exception e) {
				e.printStackTrace();
			}
			showSuccessAlert("Login Berhasil!");
			 

		} else {
			showErrorAlert("Login Gagal!");
		}

	}

	public void daftarAction(MouseEvent event) throws IOException {

		daftarLabel.setOnMouseClicked(es -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
				Parent root = loader.load();

				Scene scene = new Scene(root);
				String css = this.getClass().getResource("application.css").toExternalForm();
				scene.getStylesheets().add(css);

				Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

	private boolean validateLogin(String username, String pin) {
		String jdbcUrl = "jdbc:mysql://localhost:3306/saldocerdas";
		String dbUser = "root";
		String dbPassword = "";

		try {
			Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
			String query = "SELECT * FROM user WHERE username=? AND pin=?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				preparedStatement.setString(1, username);
				preparedStatement.setString(2, pin);
				ResultSet resultSet = preparedStatement.executeQuery();
				return resultSet.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void showSuccessAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Berhasil");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void showErrorAlert(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Gagal");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

}
