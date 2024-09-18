package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Pemasukan {
	private String kategori;
	private int jumlah;
	private String catatan;
	private String tanggal;
	private String idpemasukan;

	public Pemasukan(String kategori, int jumlah, String catatan, String tanggal, String idpemasukan) {
		super();
		this.kategori = kategori;
		this.jumlah = jumlah;
		this.catatan = catatan;
		this.tanggal = tanggal;
		this.idpemasukan = idpemasukan;
	}

	public String getKategori() {
		return kategori;
	}

	public void setKategori(String kategori) {
		this.kategori = kategori;
	}

	public int getJumlah() {
		return jumlah;
	}

	public void setJumlah(int jumlah) {
		this.jumlah = jumlah;
	}

	public String getCatatan() {
		return catatan;
	}

	public void setCatatan(String catatan) {
		this.catatan = catatan;
	}

	public String getTanggal() {
		return tanggal;
	}

	public void setTanggal(String tanggal) {
		this.tanggal = tanggal;
	}

	public String getIdpemasukan() {
		return idpemasukan;
	}

	public void setIdpemasukan(String idpemasukan) {
		this.idpemasukan = idpemasukan;
	}

}
