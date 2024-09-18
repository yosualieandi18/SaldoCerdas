package application;

public class User {
	
	public static String loggedInUsername;
	public String username = DatabaseConnector.getUsername(loggedInUsername);
	public String iduser = DatabaseConnector.getIdUser(username);
	public String email = DatabaseConnector.getEmail(username);
	public String tanggallahir = DatabaseConnector.getDOB(username);
	public String pin;
	
	public String getIduser() {
		return iduser;
	}
	public void setIduser(String iduser) {
		this.iduser = iduser;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTanggallahir() {
		return tanggallahir;
	}
	public void setTanggallahir(String tanggallahir) {
		this.tanggallahir = tanggallahir;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	
	

}
