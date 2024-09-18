package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

import application.AnalisisController;
import application.SignInController;
import javafx.scene.layout.AnchorPane;

public class PemasukanController implements Initializable {

	@FXML
	private ComboBox<String> pemasukanBox;

	public TextField hargaText;
	public TextArea catatanText;
	public DatePicker tanggalPemasukan;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		ObservableList<String> itemsPemasukan = FXCollections.observableArrayList("Gaji", "Kiriman", "Uang Jajan",
				"Hasil Usaha", "Investasi", "Bonus", "Lainnya");
		pemasukanBox.setItems(itemsPemasukan);
	}

	public void OnAdd(ActionEvent event) {

		String catatan = catatanText.getText();

		boolean isValid = true;
		StringBuilder errorMessage = new StringBuilder();

		String uniqueID = UUID.randomUUID().toString();
		String idpemasukan = "PMS" + uniqueID.substring(1, 8);
		

		try {

			if (pemasukanBox.getValue() == null) {
				isValid = false;

				errorMessage.append("- Harap pilih kategori pemasukan.\n");
			}

			if (hargaText.getText().isEmpty()) {
				isValid = false;
				errorMessage.append("- Jumlah tidak boleh kosong.\n");
			} else {
				Integer jumlah = Integer.parseInt(hargaText.getText());
				if (jumlah <= 0) {
					isValid = false;
					errorMessage.append("- Jumlah tidak boleh lebih kecil dari atau sama dengan 0.\n");
				}
			}

			if (tanggalPemasukan.getValue() == null) {
				isValid = false;

				errorMessage.append("- Harap pilih tanggal pemasukan.\n");
			}

			if (!isValid) {
				showErrorAlert(errorMessage.toString());
			} else {
				String kategori = pemasukanBox.getValue().toString();
				String tanggal = tanggalPemasukan.getValue().toString();
				Integer jumlah = Integer.parseInt(hargaText.getText());
				User user = new User();
				String iduser = user.getIduser();

				DatabaseConnector.insertDataPemasukan(idpemasukan, iduser, kategori, jumlah, catatan, tanggal);
//				showAddAlert("Apakah data pemasukan sudah sesuai?");
				showSuccessAlert("Data pemasukan berhasil di add!");

				pemasukanBox.setValue(null);
				tanggalPemasukan.setValue(null);
				hargaText.setText("");
				catatanText.setText("");

			}

		} catch (Exception e) {
			showErrorAlert("Masukkan jumlah dalam bentuk angka");
			e.printStackTrace();
		}

	}

	public void HomeAction(ActionEvent event) throws IOException {

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

	}

	public void pengeluaranAction(ActionEvent event) throws IOException {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Pengeluaran.fxml"));
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

	}

	public void analisisAction(ActionEvent event) throws IOException {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Analisis.fxml"));
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

	}
	
	public void profileAction(ActionEvent event) throws IOException {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
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
	}

	private void showAddAlert(String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Konfirmasi Data Transaksi");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();

	}

	private void showErrorAlert(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void showSuccessAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

}
