package dataManager;

/**
 * 
 * Repository statico di tutte le query utlizzate all'interno del codice.
 * @author Paganessi Andrea - Piffari Michele - Villa Stefano
 * @version 1.0
 * @since 2018/2019
 *
 */
public class Queries {
	
	public static String readerBookQuery 			= "SELECT USERNAME FROM Possesso Where BCID = ?";
	public static String readerLocationQuery 		= "SELECT RESIDENZALAT, RESIDENZALONG, RAGGIOAZIONE FROM Utente Where USERNAME = ?";
	public static String allUsersQuery 				= "SELECT * FROM UTENTE";
	public static String underReadingBookQuery 		= "SELECT COUNT(BCID) FROM Possesso WHERE BCID = ? AND DATAFINE IS NULL AND LUOGORILASCIO IS NULL";
	
	public static String searchByTitleQuery 		= "SELECT L.bcid, L.titolo, L.autore, L.datapubblicazione, L.isbn, L.genere, P.username as actualOwner FROM Libro L LEFT JOIN Possesso P on l.bcid = p.bcid where l.Titolo = ?";
	public static String searchByAuthorQuery 		= "SELECT L.bcid, L.titolo, L.autore, L.datapubblicazione, L.isbn, L.genere, P.username as actualOwner FROM Libro L LEFT JOIN Possesso P on l.bcid = p.bcid where l.Autore = ?";
	public static String searchByTitleAndAuthorQuery = "SELECT L.bcid, L.titolo, L.autore, L.datapubblicazione, L.isbn, L.genere, P.username as actualOwner FROM Libro L LEFT JOIN Possesso P on l.bcid = p.bcid where l.Titolo = ? AND l.Autore = ?";
	
	public static String queryCheckUserAndPwd 		= "SELECT COUNT(USERNAME) AS RESULT FROM UTENTE WHERE USERNAME = ? AND PASSWORD = ?";
	public static String queryCheckUsername 		= "SELECT COUNT(USERNAME) AS RESULT FROM UTENTE WHERE USERNAME = ?";
	public static String checkUserExistance 		= "SELECT Count(Username) AS Result FROM Utente Where Username = ?";
	public static String bcidAvailableQuery 		= "SELECT Count(BCID) AS Result FROM Libro Where BCID = ?";
	public static String insertBookQuery 			= "INSERT INTO LIBRO (BCID, TITOLO, AUTORE, DATAPUBBLICAZIONE, ISBN, PROPRIETARIO, GENERE) VALUES (?,?,?,?,?,?,?)";
	public static String getUserInformationsQuery 	= "SELECT * FROM Utente WHERE Username = ?";
	public static String getBooksOwnedBy 			= "SELECT BCID,USERNAME FROM Possesso WHERE Username = ?";
	public static String insertNewReservationQuery 	= "INSERT INTO PRENOTAZIONE (USERNAME, BCID) VALUES (?, ?)";
	public static String getUserInfoByJoin 			= "SELECT * FROM Prenotazione P INNER JOIN Utente U ON P.Username = U.Username WHERE BCID = ?";
	public static String storePath 					= "INSERT INTO PASSAGGIO (UTENTI, ID) VALUES(?, ?)";
	public static String getId 						= "SELECT MAX(ID) AS LAST_ID FROM PRENOTAZIONE";
	public static String queryForUserNotifications 	= "SELECT Titolo, Autore, Utenti FROM PASSAGGIO PA JOIN PRENOTAZIONE PR ON PR.ID = PA.ID JOIN LIBRO L ON PR.BCID = L.BCID";
	
}
