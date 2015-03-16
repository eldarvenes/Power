import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SendReport implements Job {

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {

			String SELECT = "select id,timestamp,kwh from power order by id desc limit 1";
			Connector.getInstance();
			Connection connection = Connector.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(SELECT);

			if (rs.next()) {
				String text = ("id: " + rs.getString(1) + " timestamp: "
						+ rs.getString(2) + " kwh: " + rs.getString(3));

				EmailMessage emailMessage = new EmailMessage();
				emailMessage.setFrom("eldarvenes@gmail.com");
				emailMessage.setTo("eldarvenes@gmail.com");
				emailMessage.setSubject("Strømavlesing");
				emailMessage.setText(text);

				SendMailTLS sendMail = new SendMailTLS(emailMessage);
				sendMail.sendEmail();

			}
			stmt.close();
			connection.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}
}
