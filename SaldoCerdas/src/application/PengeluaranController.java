package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PengeluaranController implements Initializable {

	@FXML
	private ComboBox<String> pengeluaranBox;
	public PieChart pieChart;
	ObservableList<PieChart.Data> pieChartData = null;

	public TextField hargaText;
	public TextArea catatanText;
	public DatePicker tanggalPengeluaran;
	public TextArea chartDisplay;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		pieChartData = FXCollections.observableArrayList();
		ObservableList<String> itemsPengeluaran = FXCollections.observableArrayList("Makanan", "Sosial", "Pribadi",
				"Transportasi", "Budaya", "Rumahtangga", "Pakaian", "Hadiah", "Pendidikan", "Kesehatan", "Kecantikan",
				"Lainnya");

		pengeluaranBox.setItems(itemsPengeluaran);
	}

	public void pemasukanAction(ActionEvent event) throws IOException {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Pemasukan.fxml"));
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

	public void OnAdd(ActionEvent event) {

		String catatan = catatanText.getText();

		boolean isValid = true;
		StringBuilder errorMessage = new StringBuilder();

		String uniqueID = UUID.randomUUID().toString();
		String idpengeluaran = "PNG" + uniqueID.substring(1, 8);

		try {
			if (pengeluaranBox.getValue() == null) {
				isValid = false;

				errorMessage.append("- Harap pilih kategori pengeluaran.\n");
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

			if (tanggalPengeluaran.getValue() == null) {
				isValid = false;

				errorMessage.append("- Harap pilih tanggal pengeluaran.\n");
			}

			if (!isValid) {
				showErrorAlert(errorMessage.toString());
			} else {
				String kategori = pengeluaranBox.getValue().toString();
				String tanggal = tanggalPengeluaran.getValue().toString();
				Integer jumlah = Integer.parseInt(hargaText.getText());
				User user = new User();
				String iduser = user.getIduser();

				DatabaseConnector.insertDataPengeluaran(idpengeluaran, iduser, kategori, jumlah, catatan, tanggal);
				showSuccessAlert("Data pengeluaran berhasil di add!");

				pengeluaranBox.setValue(null);
				tanggalPengeluaran.setValue(null);
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

	private void showSuccessAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");
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

}
