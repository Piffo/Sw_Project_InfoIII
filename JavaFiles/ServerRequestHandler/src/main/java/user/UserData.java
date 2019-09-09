package user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dataManager.DBConnector;
import dataManager.Queries;
/**
 * 
 * La classe UserData implementa l'interfaccia UserQuery per riuscire ad ottenere lo stato
 * dell'utente che risulta essere connesso al server.
 * Questa classe � implementata come un oggetto Singleton
 * 
 * 
 * @author Paganessi Andrea - Piffari Michele - Villa Stefano
 * @version 1.0
 * @since 2018/2019
 */
public final class UserData implements UserQuery {

	
	private static UserData instance;

	private UserData() {
	}

	public static UserData getInstance() {
		if (instance == null) {
			instance = new UserData();
		}
		return instance;
	}

	/**
	 * @param user: utente il quale � attualmente connesso al server e utilizza le api del server stesso
	 * @return LoginStatus-Success se la connessione � andata a buon fine (user e password sono corretti)
	 * @return LoginStatus-WRONG_PWD se la password � sbagliata
	 * @return LoginStatus-WRONG_USERNAME se il nome utente � sbagliato
	 */
	public LoginStatus login(User user) {
		try {
			PreparedStatement stmt;
			// Check username in database
			
			stmt = DBConnector.getDBConnector().prepareStatement(Queries.queryCheckUsername);
			stmt.setString(1, user.getUsername());
			ResultSet rs = stmt.executeQuery();
			if (!rs.next()) {
				return LoginStatus.WRONG_USERNAME;
			}
			if (!(rs.getInt("RESULT") == 1)) {
				return LoginStatus.WRONG_USERNAME;
			}

			// Check correctness of tuple
			stmt = DBConnector.getDBConnector().prepareStatement(Queries.queryCheckUserAndPwd);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			rs = stmt.executeQuery();
			if (rs.next() && rs.getInt("RESULT") == 1) {
				return LoginStatus.SUCCESS;
			} else {
				return LoginStatus.WRONG_PWD;
			}

			// Debug all DB
			// System.out.println("Query username: " + user.getUsername());
			// System.out.println("Query psw: " + user.getPassword());
		} catch (SQLException e) {
			System.out.println("error: " + e.toString());
			e.printStackTrace();
		}
		return LoginStatus.UNSUCCES;
	}
	
	/**
	 * @param username Stringa la quale descrive l'username (ID) dell'utente connesso
	 * @return true se l'utente esiste, falso se non esiste
	 */
	public boolean exist(String username) {
		PreparedStatement stmt = DBConnector.getDBConnector().prepareStatement(Queries.checkUserExistance);

		try {
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if (rs.next() && rs.getInt(1) == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
	/**
	 * @param user Stringa la quale descrive l'username (ID) dell'utente connesso
	 * @return ArrayList<String> il quale contiene il percorso dove l'utente si trova
	 */
	public ArrayList<String> pathOfUsers(String user) {
		ArrayList<String> usersForNotifications = new ArrayList<String>();
		PreparedStatement stmt = DBConnector.getDBConnector().prepareStatement(Queries.queryForUserNotifications);
		try {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				if(rs.getString(3).contains(user)){
					usersForNotifications.add(rs.getString(3));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usersForNotifications;
	}

}
