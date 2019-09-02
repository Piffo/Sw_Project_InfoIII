package algorithmReservationHandler;

import java.awt.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dataManager.DBConnector;
import dataManager.Queries;
import user.User;
import user.UserLocalizationInfo;

import java.util.*;

import book.Book;

public class Algorithm {
	
	static <K,V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
	    SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
	        new Comparator<Map.Entry<K,V>>() {
	            public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
	                int res = e1.getValue().compareTo(e2.getValue());
	                return res != 0 ? res : 1;
	            }
	        }
	    );
	    sortedEntries.addAll(map.entrySet());
	    return sortedEntries;
	}
	
	@SuppressWarnings("unchecked")
	public static TreeMap<User,Double> step_0(User L, Book book) {
		Book bookRequested = null;
		for (Book b : L.getChasingBooks()) {
			if(b.getBCID().equals(book.getBCID())) {
				bookRequested = b;
				break;
			}
		}
		TreeMap<User, Double> distancePrenotantiFromReader = new TreeMap<User, Double>();
		if(bookRequested == null) {
			return null;
		} else {
			ArrayList<User> prenotantiForSpecificBook = bookRequested.getPrenotanti();
			for (User u : prenotantiForSpecificBook) {
				distancePrenotantiFromReader.put(u, u.computeDistance(L));
			}
			distancePrenotantiFromReader = (TreeMap<User, Double>) entriesSortedByValues(distancePrenotantiFromReader);
		}
		return distancePrenotantiFromReader;
	}
	
	public static void step_1(User L, User nearestUser) {
		// Raggi si sovrappongono --> scambio direttamente
		boolean isOverlapping = VerificaPuntoIncontro(L, nearestUser);
		
		if(isOverlapping) {
			// Incontro fisico: notificare utente L e utente nearestUser
		} else {
			// Cerco tutti gli utenti che si trovano tra lettore e prenotante
			double radiusUserSearchArea = 0.5 * L.computeDistance(nearestUser);
			ArrayList<User> allUsers = getAllUsers();
			ArrayList<User> handToHandUsers = new ArrayList<User>();
			for (User u : allUsers) {
				if(u.computeDistance(L) <= radiusUserSearchArea || u.computeDistance(nearestUser) <= radiusUserSearchArea) {
					handToHandUsers.add(u);
				}
			}
						
			// Ordino hand to hand user per distanza dal reader (ovvero il Lettore L)
			TreeMap<User, Double> distanceUsersFromReader = new TreeMap<User, Double>();
			for (User u : handToHandUsers) {
				distanceUsersFromReader.put(u, u.computeDistance(L));
			}
			distanceUsersFromReader = (TreeMap<User, Double>) entriesSortedByValues(distanceUsersFromReader);
			
			// Creo il link tra gli utenti che hanno l'area di azione in comune
			ArrayList<User> userPath = new ArrayList<User>();
			User u = handToHandUsers.get(0);
			userPath.add(u);
			boolean overlappingFound = false;
			
			ArrayList<User> handToHandUsersCopy = new ArrayList<User>();
			Collections.copy(handToHandUsersCopy, handToHandUsers);
			handToHandUsersCopy.remove(0);
			while(true) {
				overlappingFound = false;
				
				for(User uu: handToHandUsersCopy) {
					if(VerificaPuntoIncontro(u, uu)) {
						userPath.add(uu);
						u = uu;
						handToHandUsersCopy.remove(uu);
						overlappingFound = true;
						break;
					}
				}
				
				if(!overlappingFound || handToHandUsersCopy.isEmpty()) {
					break;
				}
			}
		}
	}
	
	private static ArrayList<User> getAllUsers() {
		PreparedStatement stmtState = DBConnector.getDBConnector().prepareStatement(Queries.allUsersQuery);
		try {
			ResultSet rs = stmtState.executeQuery();
			ArrayList<User> users = new ArrayList<User>();
			User u;
			while(rs.next()) {
				u = new User();
				u.setUsername(rs.getString(1));
				u.setFirstName(rs.getString(2));
				u.setLastName(rs.getString(3));
				u.setLatitude(rs.getDouble(7));
				u.setLongitude(rs.getDouble(8));
				u.setActionArea(rs.getDouble(9));
				users.add(u);
			}
			return users;	
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<User>();
		}
	}
	
	//TODO: rename this function
	private static boolean VerificaPuntoIncontro(User L, User p) {
		
		double r_L = L.getActionArea();
		double r_p = p.getActionArea();
		double distance = L.computeDistance(p) - r_L - r_p;
		boolean isOverlapping = distance <= 0;
		return isOverlapping;
	}
	
	public static User getUserFromUsername(String username) {
		PreparedStatement stmt = DBConnector.getDBConnector().prepareStatement(Queries.getUserInformationsQuery);
		User u = null;
		try {
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				u = new User();
				u.setUsername(rs.getString(1));
				u.setFirstName(rs.getString(2));
				u.setLastName(rs.getString(3));
				u.setLatitude(rs.getDouble(7));
				u.setLongitude(rs.getDouble(8));
				u.setActionArea(rs.getDouble(9));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return u;
	}
	
	public static UserLocalizationInfo userLocalization(String username) {
		UserLocalizationInfo userLocalization = new UserLocalizationInfo();
		
		// Research data of the user that required the book owned by someone throw a reservation
		PreparedStatement stmt = DBConnector.getDBConnector().prepareStatement(Queries.readerLocationQuery);

		try {
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				userLocalization.lat = rs.getDouble(1);
				userLocalization.longit = rs.getDouble(2); 
				userLocalization.radius = rs.getDouble(3);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return userLocalization;
	}
	
	public static String retrievingReader(Book ownedBook) {
		// Research data of reader that own the book requested by other user throw a reservation
		PreparedStatement stmt = DBConnector.getDBConnector().prepareStatement(Queries.readerBookQuery);
		String readerUsername = null;
		
		try {
			stmt.setString(1, ownedBook.getBCID());
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				readerUsername = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return readerUsername;
	}
}