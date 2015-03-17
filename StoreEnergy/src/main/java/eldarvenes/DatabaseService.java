package eldarvenes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
//TODO slett klasse
public class DatabaseService {

	private Connection connection = null;
	public boolean isConnected = false;
	
	public DatabaseService() {

	}

	public void connectToDatabase() throws ClassNotFoundException, SQLException {
		//Class.forName("org.h2.Driver");
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://33.33.33.11/powerdb?" +
                "user=power&password=power");
		//connection = DriverManager.getConnection("jdbc:h2:d:\\database;AUTO_SERVER=TRUE");
		isConnected = true;
	}

	public void disconnectFromDatabase() throws SQLException {
		connection.close();
		isConnected = false;
	}

	public void dropDb() throws SQLException {
		Statement stmt = connection.createStatement();
		stmt.execute("DROP ALL OBJECTS");
	}

	public void createDatabase() throws SQLException {
		Statement stmt = connection.createStatement();
		//stmt.execute("create table if not exists power(id IDENTITY primary key auto_increment, timestamp TIMESTAMP ,kwh DOUBLE(13))");
		stmt.execute("CREATE TABLE t (c CHAR(20) CHARACTER SET utf8 COLLATE utf8_bin)");
	}

	public void insertKwh(Date timestamp, double kwh) throws SQLException {

		String INSERT_RECORD = "insert into power(timestamp, kwh) values(?, ?)";

		PreparedStatement pstmt = connection.prepareStatement(INSERT_RECORD);
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(
				new java.util.Date().getTime());
		pstmt.setTimestamp(1, sqlDate);
		pstmt.setDouble(2, kwh);
		pstmt.executeUpdate();
	}	
}
