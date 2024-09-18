package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.UUID;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SignUpController {

	public TextField usernameField;
	public PasswordField pinField;
	public DatePicker dateField;
	public TextField emailField;
	public Label masukLabel;

	
	public void masukAction(MouseEvent event) throws IOException {

		masukLabel.setOnMouseClicked(es -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("SignIn.fxml"));
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

	public void handleSignUp(ActionEvent event) throws IOException {
		String username = usernameField.getText();
		String email = emailField.getText();

		String pin = pinField.getText();

		String uniqueID = UUID.randomUUID().toString();
		String iduser = "USSC" + uniqueID.substring(1, 8);

		boolean isValid = true;
		StringBuilder errorMessage = new StringBuilder();

		try {

			if (usernameField.getText().isEmpty()) {
				isValid = false;
				errorMessage.append("- Username tidak boleh kosong.\n");
			} else {
				if (isUsernameExists(username)) {
					isValid = false;
					errorMessage.append("- Username sudah digunakan, silahkan ganti!\n");
				}
			}

			if (!emailField.getText().endsWith("@gmail.com")) {
				isValid = false;
				errorMessage.append("- Email harus diakhiri dengan @gmail.com.\n");
			}

			if (dateField.getValue() == null) {
				isValid = false;

				errorMessage.append("- Harap pilih tanggal.\n");
			}

			if (pinField.getText().isEmpty()) {
				isValid = false;
				errorMessage.append("- Pin tidak boleh kosong.\n");
			} else {

				if (pinField.getText().length() != 4) {
					isValid = false;
					errorMessage.append("- Pin harus berjumlah 4 karakter.\n");
				}

			}

			if (!isValid) {
				showErrorAlert(errorMessage.toString());
			} else {

				FXMLLoader loader = new FXMLLoader(getClass().getResource("SignIn.fxml"));
				String tanggallahir = dateField.getValue().toString();
				Parent root = loader.load();

				Scene scene = new Scene(root);
				String css = this.getClass().getResource("application.css").toExternalForm();
				scene.getStylesheets().add(css);

				Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				primaryStage.setScene(scene);
				primaryStage.show();

				DatabaseConnector.insertDataUser(iduser, username, email, tanggallahir, pin);
				showSuccessAlert("Akun anda berhasil terdaftar!");

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public boolean isUsernameExists(String username) {
        boolean exists = false;

        try {
			Connection connection = DatabaseConnector.connect();
            String sql = "SELECT * FROM user WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                exists = true;
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exists;
    }
	
	private void showSuccessAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void showErrorAlert(String errorMessage) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(errorMessage);
		alert.showAndWait();
	}

}
