package dataManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * DBConnector, classe singletong, la quale contiene i metodi per stabilire la connessione al databse.
 * 
 * @author Paganessi Andrea - Piffari Michele - Villa Stefano
 * @version 1.0
 * @since 2018/2019
 */

public final class DBConnector implements QueryPerformer{

	//Configuration parameters for the generation of the IAM Database Authentication token
	private static final String RDS_INSTANCE_HOSTNAME = "db3.co7xbajucw51.us-east-2.rds.amazonaws.com";
	private static final int RDS_INSTANCE_PORT = 1521;
	private static final String DB_USER = "gNOkUhDOCM";
	private static final String DB_PASSWORD = "A?T>Q_oBA=K]->gv+sTTZg?VJZkaF";
	@SuppressWarnings("unused")
	private static final String JDBC_URL = "jdbc:oracle://" + RDS_INSTANCE_HOSTNAME + ":" + RDS_INSTANCE_PORT;

 

	private static DBConnector instance = null;
	private Connection conn;
	private Statement stmt;

	private DBConnector()  {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			//get the connection
			conn = getDBConnectionUsingIam();
			//verify the connection is successful
			stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}

	public static DBConnector getDBConnector() {
		if(instance == null)
			instance = new DBConnector();
		return instance;
	}
	
	/**
	 * 
	 * @param sql Query che vogliamo eseguire
	 * @return PreparamedStatment oggetto per parsare il risultato delle queries
	 */
	public PreparedStatement prepareStatement(final String sql) {
		try {
			return conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	

	@SuppressWarnings("unused")
	private void close() {
		// Close the connection
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			instance = null;
		}
	}


	/**
	 * Questo metodo ritorna una connessione all'istanza del database utilizzando IAM Database Authentication
	 * @return
	 * @throws Exception
	 */
	private static Connection getDBConnectionUsingIam() throws Exception {
		// setSslProperties();
		//return DriverManager.getConnection(JDBC_URL, setMySqlConnectionProperties());
		//jdbc:oracle:thin:@dbinfo3.ckadinof62hi.eu-west-3.rds.amazonaws.com:1521:dbinfo3", "gNOkUhDOCM", "A?T>Q_oBA=K]->gv+sTTZg?VJZkaF"
		return DriverManager.getConnection("jdbc:oracle:thin:@db3.co7xbajucw51.us-east-2.rds.amazonaws.com:1521:db3", "gNOkUhDOCM", "A?T>Q_oBA=K]->gv+sTTZg?VJZkaF");
	}


	/**
	 * Questo metodo setta le propriet� di mysql connection le quali includono IAM Database Authentication token
	 * come password
	 * @return
	 */
	@SuppressWarnings("unused")
	private static Properties setMySqlConnectionProperties() {
		Properties mysqlConnectionProperties = new Properties();
		//mysqlConnectionProperties.setProperty("verifyServerCertificate","true");
		//mysqlConnectionProperties.setProperty("useSSL", "true");
		mysqlConnectionProperties.setProperty("user",DB_USER);
		mysqlConnectionProperties.setProperty("password", DB_PASSWORD);
		return mysqlConnectionProperties;
	}
}
