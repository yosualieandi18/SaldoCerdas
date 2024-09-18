package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnector {

	public static String jdbcUrl = "jdbc:mysql://localhost:3306/saldocerdas";
	public static String dbUser = "root";
	public static String dbPassword = "";

	public static Connection connect() throws SQLException {
		return DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
	}
	
	public static String getUsername(String username) {
        String query = "SELECT username FROM user WHERE username = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/saldocerdas", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return username;
    }

	public static String getEmail(String email) {
        String query = "SELECT email FROM user WHERE username = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/saldocerdas", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return email;
    }
	
	public static String getDOB(String tanggallahir) {
        String query = "SELECT tanggallahir FROM user WHERE username = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/saldocerdas", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, tanggallahir);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("tanggallahir");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tanggallahir;
    }
	
	public static String getIdUser(String iduser) {
        String query = "SELECT iduser FROM user WHERE username = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/saldocerdas", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, iduser);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("iduser");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return iduser;
    }
	
	public static void insertDataUser(String iduser, String username, String email, String tanggallahir, String pin) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connect();

			String sql = "INSERT INTO user (iduser,username, email, tanggallahir, pin) VALUES (?,?, ?, ?, ?)";
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, iduser);
			preparedStatement.setString(2, username);
			preparedStatement.setString(3, email);
			preparedStatement.setString(4, tanggallahir);
			preparedStatement.setString(5, pin);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void insertDataPemasukan(String idpemasukan,String iduser, String kategori, Integer jumlah, String catatan,
			String tanggal) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connect();

			String sql = "INSERT INTO pemasukan (idpemasukan,iduser, kategori, jumlah, catatan, tanggal) VALUES (?, ?, ?, ?, ?, ?)";
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, idpemasukan);
			preparedStatement.setString(2, iduser);
			preparedStatement.setString(3, kategori);
			preparedStatement.setInt(4, jumlah);
			preparedStatement.setString(5, catatan);
			preparedStatement.setString(6, tanggal);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void insertDataPengeluaran(String idpengeluaran,String iduser, String kategori, Integer jumlah, String catatan,
			String tanggal) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connect();

			String sql = "INSERT INTO pengeluaran (idpengeluaran, iduser, kategori, jumlah, catatan, tanggal) VALUES (?, ?, ?, ?, ?, ?)";
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, idpengeluaran);
			preparedStatement.setString(2, iduser);
			preparedStatement.setString(3, kategori);
			preparedStatement.setInt(4, jumlah);
			preparedStatement.setString(5, catatan);
			preparedStatement.setString(6, tanggal);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void hapusPemasukan(String idpemasukan) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connect();
			String sql = "DELETE FROM pemasukan WHERE idpemasukan = ?";
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, idpemasukan);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void hapusPengeluaran(String idpengeluaran) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connect();
			String sql = "DELETE FROM pengeluaran WHERE idpengeluaran = ?";
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, idpengeluaran);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
