package application;

public class Pengeluaran {
	private String kategori;
	private String idUser;
	private int jumlah;
	private String catatan;
	private String tanggal;
	private String idpengeluaran;

	public Pengeluaran(String kategori, String idUser, int jumlah, String catatan, String tanggal, String idpengeluaran) {
		super();
		this.kategori = kategori;
		this.idUser = idUser;
		this.jumlah = jumlah;
		this.catatan = catatan;
		this.tanggal = tanggal;
		this.idpengeluaran = idpengeluaran;
	}

	public String getKategori() {
		return kategori;
	}

	public void setKategori(String kategori) {
		this.kategori = kategori;
	}
	
	public String getIdUser() {
		User user = new User();
		return user.getIduser();
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
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

	public String getIdpengeluaran() {
		return idpengeluaran;
	}

	public void setIdpengeluaran(String idpengeluaran) {
		this.idpengeluaran = idpengeluaran;
	}

}
