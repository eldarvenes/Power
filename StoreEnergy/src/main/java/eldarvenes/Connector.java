package eldarvenes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {

	private static String URL;
	private static String LOGIN;
	private static String PASSWORD;
	private static String DBNAME;
	private static Connector connector;
	private static Connection connection;

	private Connector() {
		loadProperties();
	}

	public synchronized static Connector getInstance() {
		if (connector == null) {
			connector = new Connector();
		}
		return connector;
	}

	public static Connection getConnection() {
		if (connection == null) {
			Connection c = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				c = DriverManager.getConnection(URL + DBNAME, LOGIN, PASSWORD);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return c;
		}
		return connection;
	}

	private void loadProperties() {
		Properties prop = new Properties();
		try {			
			prop.load(Connector.class.getClassLoader()
					.getResourceAsStream("configuration.properties"));
			URL = prop.getProperty("DatabaseURL");
			LOGIN = prop.getProperty("DatabaseUser");
			PASSWORD = prop.getProperty("DatabasePassword");
			DBNAME = prop.getProperty("DatabaseName");
		} catch (Exception e) {

		}
	}
}