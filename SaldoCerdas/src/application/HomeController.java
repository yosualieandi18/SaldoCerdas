package application;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class HomeController {

	@FXML
	public Label usernameLabel;
	private ObservableList<Pemasukan> dataPemasukan;
	private ObservableList<Pengeluaran> dataPengeluaran;

	public TableView<Pemasukan> pemasukanTable;
	public TableColumn<Pemasukan, String> kategoriPemasukan;
	public TableColumn<Pemasukan, Integer> jumlahPemasukan;
	public TableColumn<Pemasukan, String> catatanPemasukan;
	public TableColumn<Pemasukan, String> tanggalPemasukan;

	public TableView<Pengeluaran> pengeluaranTable;
	public TableColumn<Pengeluaran, String> kategoriPengeluaran;
	public TableColumn<Pengeluaran, Integer> jumlahPengeluaran;
	public TableColumn<Pengeluaran, String> catatanPengeluaran;
	public TableColumn<Pengeluaran, String> tanggalPengeluaran;

	public Button deletePemasukan;
	public Button deletePengeluaran;
	
	User user = new User();

	@FXML
	public void initialize() {
		kategoriPemasukan.setCellValueFactory(new PropertyValueFactory<>("kategori"));
		jumlahPemasukan.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
		catatanPemasukan.setCellValueFactory(new PropertyValueFactory<>("catatan"));
		tanggalPemasukan.setCellValueFactory(new PropertyValueFactory<>("tanggal"));

		kategoriPengeluaran.setCellValueFactory(new PropertyValueFactory<>("kategori"));
		jumlahPengeluaran.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
		catatanPengeluaran.setCellValueFactory(new PropertyValueFactory<>("catatan"));
		tanggalPengeluaran.setCellValueFactory(new PropertyValueFactory<>("tanggal"));

		dataPemasukan = FXCollections.observableArrayList();
		pemasukanTable.setItems(dataPemasukan);

		dataPengeluaran = FXCollections.observableArrayList();
		pengeluaranTable.setItems(dataPengeluaran);

		showPemasukanData();
		showPengeluaranData();
		deletePemasukanAction();
		deletePengeluaranAction();
		
		
	    usernameLabel.setText("" + user.getUsername());
	}
	


	public void showPemasukanData() {
		getPemasukanData();
		pemasukanTable.setItems(dataPemasukan);

	}

	private void getPemasukanData() {
		String url = "jdbc:mysql://localhost:3306/saldocerdas";
		String username = "root";
		String password = "";

		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			String query = "SELECT * FROM pemasukan WHERE iduser = " + "'"+ user.getIduser()+"'";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				String kategori = resultSet.getString("kategori");
				int jumlah = resultSet.getInt("jumlah");
				String catatan = resultSet.getString("catatan");
				String tanggal = resultSet.getString("tanggal");
				String idpemasukan = resultSet.getString("idpemasukan");

				Pemasukan pemasukan = new Pemasukan(kategori, jumlah, catatan, tanggal, idpemasukan);
				dataPemasukan.add(pemasukan);
			}

			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void showPengeluaranData() {
		getPengeluaranData();
		pengeluaranTable.setItems(dataPengeluaran);
	}

	private void getPengeluaranData() {
		String url = "jdbc:mysql://localhost:3306/saldocerdas";
		String username = "root";
		String password = "";

		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			String query = "SELECT * FROM pengeluaran WHERE iduser = " + "'" + user.getIduser() + "'";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {

				String kategori = resultSet.getString("kategori");
				String idUser = resultSet.getString("iduser");
				int jumlah = resultSet.getInt("jumlah");
				String catatan = resultSet.getString("catatan");
				String tanggal = resultSet.getString("tanggal");
				String idpengeluaran = resultSet.getString("idpengeluaran");

				Pengeluaran pengeluaran = new Pengeluaran(kategori, idUser, jumlah, catatan, tanggal, idpengeluaran);
				dataPengeluaran.add(pengeluaran);
			}

			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deletePemasukanAction() {
		Pemasukan selectedPemasukan = pemasukanTable.getSelectionModel().getSelectedItem();

		if (selectedPemasukan != null) {
			DatabaseConnector.hapusPemasukan(selectedPemasukan.getIdpemasukan());

			pemasukanTable.getItems().remove(selectedPemasukan);
			showSuccessAlert("Delete data pemasukan berhasil");
			pemasukanTable.refresh();
		}

	}

	public void deletePengeluaranAction() {
		Pengeluaran selectedPengeluaran = pengeluaranTable.getSelectionModel().getSelectedItem();

		if (selectedPengeluaran != null) {
			DatabaseConnector.hapusPengeluaran(selectedPengeluaran.getIdpengeluaran());

			pengeluaranTable.getItems().remove(selectedPengeluaran);
			showSuccessAlert("Delete data pengeluaran berhasil");
			pengeluaranTable.refresh();
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
	
	private void showSuccessAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
