package eldarvenes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GeneratePowerReport {

	public String getUsageLastDay() throws SQLException {

		String SELECT = "SELECT (select kwh FROM power where timestamp = curdate()) - (select kwh FROM power where timestamp = (curdate() - interval 1 day))";
		Connector.getInstance();
		Connection connection = Connector.getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(SELECT);
		String kwhBruktSisteDøgn = "";

		if (rs.next()) {
			kwhBruktSisteDøgn = (rs.getString(1));
		}
		stmt.close();
		connection.close();

		return kwhBruktSisteDøgn;
	}

	public String getCurrentKwhStatus() throws SQLException {

		String SELECT = "select kwh from power order by id desc limit 1";
		Connector.getInstance();
		Connection connection = Connector.getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(SELECT);

		String currentKwh = "";

		if (rs.next()) {
			currentKwh = (rs.getString(1));

		}
		stmt.close();
		connection.close();

		return currentKwh;
	}

}