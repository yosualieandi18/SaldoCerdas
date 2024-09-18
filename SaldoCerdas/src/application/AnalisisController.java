package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AnalisisController {

	public PieChart pieChart;
	public PieChart pieChart1;

	public Label pemasukanTotal;
	public Label pengeluaranTotal;
	public Label totalSemua;
	public Label statusKeuangan;

	User user = new User();

	private static final String DB_URL = "jdbc:mysql://localhost:3306/saldocerdas";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";

	public void initialize() {
		updatePieChart();
		updatePieCharts();
		totalKeseluruhan();

	}

	private ObservableList<PieChart.Data> getDataFromDatabase() {
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		double totalAmount = 0.0;
		Set<String> uniqueCategories = new HashSet<>();

		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			String query = "SELECT kategori, jumlah FROM pemasukan WHERE iduser = " + "'" + user.getIduser() + "'";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query);
					ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					String category = resultSet.getString("kategori");

					if (uniqueCategories.add(category)) {
						double amount = resultSet.getDouble("jumlah");
						totalAmount += amount;
						pieChartData.add(new PieChart.Data(category, amount));
					}

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (PieChart.Data data : pieChartData) {
			double percentage = (data.getPieValue() / totalAmount) * 100;
			String originalLabel = data.getName();
			data.setName(originalLabel + " (" + String.format("%.2f%%", percentage) + ")");

		}
		return pieChartData;
	}

	private ObservableList<PieChart.Data> getDataFromDatabases() {
		ObservableList<PieChart.Data> pieChartDatas = FXCollections.observableArrayList();
		double totalAmount = 0.0;
		Set<String> uniqueCategories = new HashSet<>();

		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			String query = "SELECT kategori, jumlah FROM pengeluaran WHERE iduser = " + "'" + user.getIduser() + "'";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query);
					ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					String category = resultSet.getString("kategori");

					if (uniqueCategories.add(category)) {
						double amount = resultSet.getDouble("jumlah");
						totalAmount += amount;
						pieChartDatas.add(new PieChart.Data(category, amount));
					}

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (PieChart.Data data : pieChartDatas) {
			double percentage = (data.getPieValue() / totalAmount) * 100;
			String originalLabel = data.getName();
			data.setName(originalLabel + " (" + String.format("%.2f%%", percentage) + ")");

		}

		return pieChartDatas;
	}

	private void updatePieChart() {
		ObservableList<PieChart.Data> data = getDataFromDatabase();
		pieChart.setData(data);
		for (PieChart.Data entry : data) {
			entry.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				// Handle click events if needed
				System.out.println(entry.getName() + ": " + entry.getPieValue() + " - " + entry.getName());
				showSuccessAlert(entry.getName() + ": " + entry.getPieValue());
			});
		}
	}

	private void updatePieCharts() {
		ObservableList<PieChart.Data> data = getDataFromDatabases();
		pieChart1.setData(data);
		for (PieChart.Data entry : data) {
			entry.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				// Handle click events if needed
				System.out.println(entry.getName() + ": " + entry.getPieValue() + " - " + entry.getName());
				showSuccessAlert(entry.getName() + ": " + entry.getPieValue());

			});
		}
	}

	public void totalKeseluruhan() {

		try {
			DatabaseConnector.connect();
			Connection connection = DatabaseConnector.connect();
			Statement statement = connection.createStatement();

			ResultSet resultSetPemasukan = statement
					.executeQuery("SELECT SUM(jumlah) FROM pemasukan WHERE iduser = " + "'" + user.getIduser() + "'");
			double totalPemasukan = 0;
			if (resultSetPemasukan.next()) {
				totalPemasukan = resultSetPemasukan.getDouble(1);
				pemasukanTotal.setText("Rp " + totalPemasukan);
			}
			resultSetPemasukan.close();

			ResultSet resultSetPengeluaran = statement
					.executeQuery("SELECT SUM(jumlah) FROM pengeluaran WHERE iduser = " + "'" + user.getIduser() + "'");
			double totalPengeluaran = 0;
			if (resultSetPengeluaran.next()) {
				totalPengeluaran = resultSetPengeluaran.getDouble(1);
				pengeluaranTotal.setText("Rp " + totalPengeluaran);
			}
			resultSetPengeluaran.close();

			double selisih = totalPemasukan - totalPengeluaran;

			totalSemua.setText("Rp " + selisih);

			if (totalPemasukan > totalPengeluaran) {
				statusKeuangan.setText("SURPLUS");
				statusKeuangan.setStyle("-fx-text-fill : #2ebf30");
			} else if (totalPengeluaran > totalPemasukan) {
				statusKeuangan.setText("DEFISIT");
				statusKeuangan.setStyle("-fx-text-fill : #e40000");
			} else {
				statusKeuangan.setText("");
			}

			statement.close();
			connection.close();
		} catch (SQLException e) {
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
		alert.setTitle("Info Data");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

}
